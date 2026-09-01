package com.ellen.finvoice.infrastructure.ai;

import com.ellen.finvoice.application.CreateTransactionUseCase;
import com.ellen.finvoice.application.GetFinancialSummaryUseCase;
import com.ellen.finvoice.application.ListTransactionsUseCase;
import com.ellen.finvoice.domain.Category;
import com.ellen.finvoice.domain.TransactionType;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransactionTools {

    private final CreateTransactionUseCase createTransactionUseCase;
    private final ListTransactionsUseCase listTransactionsUseCase;
    private final GetFinancialSummaryUseCase getFinancialSummaryUseCase;

    public TransactionTools(
            CreateTransactionUseCase createTransactionUseCase,
            ListTransactionsUseCase listTransactionsUseCase,
            GetFinancialSummaryUseCase getFinancialSummaryUseCase
    ) {
        this.createTransactionUseCase = createTransactionUseCase;
        this.listTransactionsUseCase = listTransactionsUseCase;
        this.getFinancialSummaryUseCase = getFinancialSummaryUseCase;
    }

    @Tool(
            name = "createTransaction",
            description = "Creates a financial transaction with description, amount, type and category"
    )
    public String createTransaction(
            String description,
            BigDecimal amount,
            TransactionType type,
            Category category
    ) {
        var transaction = createTransactionUseCase.execute(
                description,
                amount,
                type,
                category
        );

        return "Transaction created successfully with id: " + transaction.getId();
    }

    @Tool(
            name = "listTransactions",
            description = "Lists all financial transactions"
    )
    public Object listTransactions() {
        return listTransactionsUseCase.execute();
    }

    @Tool(
            name = "getFinancialSummary",
            description = "Returns total income, total expenses and current balance"
    )
    public Object getFinancialSummary() {
        return getFinancialSummaryUseCase.execute();
    }
}
