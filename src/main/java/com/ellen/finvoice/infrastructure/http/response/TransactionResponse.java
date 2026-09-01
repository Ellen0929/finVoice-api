package com.ellen.finvoice.infrastructure.http.response;

import com.ellen.finvoice.domain.Category;
import com.ellen.finvoice.domain.Transaction;
import com.ellen.finvoice.domain.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionResponse(
        UUID id,
        String description,
        BigDecimal amount,
        TransactionType type,
        Category category,
        LocalDateTime createdAt
) {

    public static TransactionResponse from(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getDescription(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getCategory(),
                transaction.getCreatedAt()
        );
    }
}