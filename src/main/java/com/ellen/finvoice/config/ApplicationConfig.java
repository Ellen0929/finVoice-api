package com.ellen.finvoice.config;

import com.ellen.finvoice.application.CreateTransactionUseCase;
import com.ellen.finvoice.application.GetFinancialSummaryUseCase;
import com.ellen.finvoice.application.ListTransactionsUseCase;
import com.ellen.finvoice.domain.TransactionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public CreateTransactionUseCase createTransactionUseCase(
            TransactionRepository transactionRepository
    ) {
        return new CreateTransactionUseCase(transactionRepository);
    }

    @Bean
    public ListTransactionsUseCase listTransactionsUseCase(
            TransactionRepository transactionRepository
    ) {
        return new ListTransactionsUseCase(transactionRepository);
    }

    @Bean
    public GetFinancialSummaryUseCase getFinancialSummaryUseCase(
            TransactionRepository transactionRepository
    ) {
        return new GetFinancialSummaryUseCase(transactionRepository);
    }
}