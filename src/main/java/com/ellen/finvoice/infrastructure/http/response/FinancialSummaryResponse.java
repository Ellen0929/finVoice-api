package com.ellen.finvoice.infrastructure.http.response;

import com.ellen.finvoice.application.GetFinancialSummaryUseCase.FinancialSummary;

import java.math.BigDecimal;

public record FinancialSummaryResponse(
        BigDecimal totalIncome,
        BigDecimal totalExpense,
        BigDecimal balance
) {

    public static FinancialSummaryResponse from(FinancialSummary summary) {
        return new FinancialSummaryResponse(
                summary.totalIncome(),
                summary.totalExpense(),
                summary.balance()
        );
    }
}