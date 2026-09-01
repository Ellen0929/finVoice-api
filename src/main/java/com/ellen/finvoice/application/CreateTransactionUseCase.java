package com.ellen.finvoice.application;

import com.ellen.finvoice.domain.Category;
import com.ellen.finvoice.domain.Transaction;
import com.ellen.finvoice.domain.TransactionRepository;
import com.ellen.finvoice.domain.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class CreateTransactionUseCase {

    private final TransactionRepository transactionRepository;

    public CreateTransactionUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction execute(
            String description,
            BigDecimal amount,
            TransactionType type,
            Category category
    ) {
        Transaction transaction = new Transaction(
                UUID.randomUUID(),
                description,
                amount,
                type,
                category,
                LocalDateTime.now()
        );

        return transactionRepository.save(transaction);
    }
}