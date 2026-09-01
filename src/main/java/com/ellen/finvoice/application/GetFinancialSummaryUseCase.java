package com.ellen.finvoice.application;

import com.ellen.finvoice.domain.Transaction;
import com.ellen.finvoice.domain.TransactionRepository;
import com.ellen.finvoice.domain.TransactionType;

import java.math.BigDecimal;
import java.util.List;

public class GetFinancialSummaryUseCase {

    private final TransactionRepository transactionRepository;

    public GetFinancialSummaryUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public FinancialSummary execute() {
        List<Transaction> transactions = transactionRepository.findAll();

        BigDecimal totalIncome = transactions.stream()
                .filter(transaction -> transaction.getType() == TransactionType.INCOME)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpense = transactions.stream()
                .filter(transaction -> transaction.getType() == TransactionType.EXPENSE)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal balance = totalIncome.subtract(totalExpense);

        return new FinancialSummary(totalIncome, totalExpense, balance);
    }

    public record FinancialSummary(
            BigDecimal totalIncome,
            BigDecimal totalExpense,
            BigDecimal balance
    ) {
    }
}