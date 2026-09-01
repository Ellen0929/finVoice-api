package com.ellen.finvoice.infrastructure.http;

import com.ellen.finvoice.application.AiAssistantService;
import com.ellen.finvoice.infrastructure.audio.AudioTranscriptionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/voice")
public class VoiceController {

    private final AudioTranscriptionService audioTranscriptionService;
    private final AiAssistantService aiAssistantService;

    public VoiceController(
            AudioTranscriptionService audioTranscriptionService,
            AiAssistantService aiAssistantService
    ) {
        this.audioTranscriptionService = audioTranscriptionService;
        this.aiAssistantService = aiAssistantService;
    }

    @PostMapping(
            value = "/process",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public String processVoice(
            @RequestPart("file") MultipartFile file
    ) throws Exception {

        String transcription = audioTranscriptionService.transcribe(file);

        return aiAssistantService.ask(transcription);
    }
}
