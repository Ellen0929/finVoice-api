# FinVoice API

FinVoice is an AI-powered financial assistant built with **Java, Spring Boot and Spring AI**.

The API can manage financial transactions through REST endpoints, natural language and voice commands. The AI understands the user's intent and uses **Tool Calling** to execute real Java operations and persist data in PostgreSQL.

## Project Flow

A voice command such as:

> "I spent 50 reais at the supermarket."

follows this flow:

```text
                 AUDIO INPUT
                      │
                      ▼
               ┌─────────────┐
               │   FFmpeg    │
               │ Audio Prep  │
               └─────────────┘
                      │
                      ▼
               ┌─────────────┐
               │ whisper.cpp │
               │ Speech→Text │
               └─────────────┘
                      │
                      ▼
          "I spent R$50 at the
               supermarket"
                      │
                      ▼
               ┌─────────────┐
               │  Spring AI  │
               │ ChatClient  │
               └─────────────┘
                      │
                      ▼
               ┌─────────────┐
               │   Ollama    │
               │  Qwen3:4b   │
               └─────────────┘
                      │
                      ▼
               ┌─────────────┐
               │Tool Calling │
               └─────────────┘
                      │
          ┌───────────┼───────────┐
          ▼           ▼           ▼
       Create        List       Summary
    Transaction  Transactions   Balance
          │
          ▼
      Java Use Cases
          │
          ▼
      PostgreSQL
```

The AI is responsible for understanding the user's intent, while the Java application remains responsible for business logic and database operations.

## Features

- Create and list financial transactions
- Filter transactions by category
- Calculate income, expenses and balance
- Natural-language financial commands
- Spring AI Tool Calling
- Local speech-to-text
- Portuguese and English interaction

## Technologies

- Java 21
- Spring Boot
- Spring AI
- Spring Data JPA
- PostgreSQL
- Maven
- Ollama
- Qwen3:4b
- whisper.cpp
- FFmpeg
- Postman

## Free & Local AI

This project was intentionally developed using **free and local AI solutions**, without requiring paid AI APIs.

**Ollama + Qwen3:4b** handle natural-language understanding and Tool Calling.

**whisper.cpp + Whisper Base** handle speech-to-text locally.

**FFmpeg** prepares audio files before transcription.

This keeps the project free to run and experiment with. The main trade-off is performance, since local LLM response time depends on the computer hardware.

## Tool Calling

The AI can use application tools such as:

```text
createTransaction
listTransactions
getFinancialSummary
```

For example:

```text
"I spent 120 reais on lunch"
```

can be interpreted as:

```text
description → lunch
amount      → 120.00
type        → EXPENSE
category    → FOOD
```

The AI then calls the Java tool responsible for saving the transaction in PostgreSQL.

## Main Endpoints

```http
POST /api/transactions
GET  /api/transactions
GET  /api/transactions/category/{category}
GET  /api/transactions/summary

GET  /api/ai/ask?message={message}

POST /api/voice/process
```

## Running the Project

Requirements:

```text
Java 21
PostgreSQL
Ollama
FFmpeg
whisper.cpp
```

Download the local LLM:

```bash
ollama pull qwen3:4b
```

Configure the environment variables:

```text
DB_PASSWORD
WHISPER_EXECUTABLE
WHISPER_MODEL
```

Then:

```bash
./mvnw clean compile
./mvnw spring-boot:run
```

The API runs at:

```text
http://localhost:8080
```

## Tests Performed

During development, the following flows were successfully validated:

- REST transaction creation and persistence
- Transaction listing and category filtering
- Financial summary calculation
- Spring AI integration with Ollama
- Financial queries through Tool Calling
- Transaction creation from natural language
- Local Portuguese speech-to-text
- Audio transcription through the Spring Boot API

The complete voice-to-AI flow is implemented, but local LLM processing can take longer depending on the available hardware.

## Future Improvements

- Cloud AI provider for faster and more robust responses
- Text-to-Speech responses
- Spring Security and JWT
- Swagger/OpenAPI
- Automated tests
- Docker
- Web interface

A future version can keep **Ollama as the free local option** while also supporting a more powerful paid AI provider for production environments.

## Project Context

Developed for the DIO challenge:

**"Desenvolvendo sua API Inteligente com Reconhecimento de Fala e Spring Boot"**

The project explores **Spring AI, Tool Calling, speech recognition and AI integration with real application business logic**.

## Author

**Ellen Nascimento**
