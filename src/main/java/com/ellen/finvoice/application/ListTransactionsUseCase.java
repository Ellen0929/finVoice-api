package com.ellen.finvoice.application;

import com.ellen.finvoice.domain.Category;
import com.ellen.finvoice.domain.Transaction;
import com.ellen.finvoice.domain.TransactionRepository;

import java.util.List;

public class ListTransactionsUseCase {

    private final TransactionRepository transactionRepository;

    public ListTransactionsUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> execute() {
        return transactionRepository.findAll();
    }

    public List<Transaction> executeByCategory(Category category) {
        return transactionRepository.findByCategory(category);
    }
}