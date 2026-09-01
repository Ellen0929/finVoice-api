package com.ellen.finvoice.infrastructure.audio;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class AudioTranscriptionService {

    private final String whisperExecutable;
    private final String whisperModel;

    public AudioTranscriptionService(
            @Value("${finvoice.whisper.executable}") String whisperExecutable,
            @Value("${finvoice.whisper.model}") String whisperModel
    ) {
        this.whisperExecutable = whisperExecutable;
        this.whisperModel = whisperModel;
    }

    public String transcribe(MultipartFile audioFile) throws IOException, InterruptedException {

        Path tempDirectory = Files.createTempDirectory("finvoice-audio-");

        Path originalAudio = tempDirectory.resolve("input-audio");
        Path wavAudio = tempDirectory.resolve("input.wav");
        Path transcriptBase = tempDirectory.resolve("transcript");
        Path transcriptFile = tempDirectory.resolve("transcript.txt");

        try {
            audioFile.transferTo(originalAudio);

            convertToWav(originalAudio, wavAudio);

            ProcessBuilder whisperProcessBuilder = new ProcessBuilder(
                    whisperExecutable,
                    "-m", whisperModel,
                    "-f", wavAudio.toString(),
                    "-l", "pt",
                    "-otxt",
                    "-of", transcriptBase.toString()
            );

            whisperProcessBuilder.redirectErrorStream(true);

            Process whisperProcess = whisperProcessBuilder.start();

            String processOutput = new String(
                    whisperProcess.getInputStream().readAllBytes()
            );

            int exitCode = whisperProcess.waitFor();

            if (exitCode != 0) {
                throw new IllegalStateException(
                        "Whisper transcription failed: " + processOutput
                );
            }

            if (!Files.exists(transcriptFile)) {
                throw new IllegalStateException(
                        "Whisper did not generate the transcription file."
                );
            }

            return Files.readString(transcriptFile).trim();

        } finally {
            deleteTemporaryFiles(tempDirectory);
        }
    }

    private void convertToWav(Path inputAudio, Path outputAudio)
            throws IOException, InterruptedException {

        ProcessBuilder ffmpegProcessBuilder = new ProcessBuilder(
                "ffmpeg",
                "-y",
                "-i", inputAudio.toString(),
                "-ar", "16000",
                "-ac", "1",
                outputAudio.toString()
        );

        ffmpegProcessBuilder.redirectErrorStream(true);

        Process ffmpegProcess = ffmpegProcessBuilder.start();

        String processOutput = new String(
                ffmpegProcess.getInputStream().readAllBytes()
        );

        int exitCode = ffmpegProcess.waitFor();

        if (exitCode != 0) {
            throw new IllegalStateException(
                    "Audio conversion failed: " + processOutput
            );
        }
    }

    private void deleteTemporaryFiles(Path directory) {

        try {
            if (Files.exists(directory)) {
                try (var files = Files.walk(directory)) {
                    List<Path> paths = files
                            .sorted((first, second) -> second.compareTo(first))
                            .toList();

                    for (Path path : paths) {
                        Files.deleteIfExists(path);
                    }
                }
            }
        } catch (IOException ignored) {
        }
    }
}
