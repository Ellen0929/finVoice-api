package com.ellen.finvoice.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {

    private UUID id;
    private String description;
    private BigDecimal amount;
    private TransactionType type;
    private Category category;
    private LocalDateTime createdAt;

    public Transaction(
            UUID id,
            String description,
            BigDecimal amount,
            TransactionType type,
            Category category,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public Category getCategory() {
        return category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}