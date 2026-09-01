package com.ellen.finvoice.infrastructure.http.request;

import com.ellen.finvoice.domain.Category;
import com.ellen.finvoice.domain.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateTransactionRequest(

        @NotBlank
        String description,

        @NotNull
        @DecimalMin(value = "0.01")
        BigDecimal amount,

        @NotNull
        TransactionType type,

        @NotNull
        Category category
) {
}