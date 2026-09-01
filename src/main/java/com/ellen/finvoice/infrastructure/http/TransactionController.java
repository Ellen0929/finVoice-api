package com.ellen.finvoice.infrastructure.http;

import com.ellen.finvoice.application.CreateTransactionUseCase;
import com.ellen.finvoice.application.ListTransactionsUseCase;
import com.ellen.finvoice.domain.Category;
import com.ellen.finvoice.infrastructure.http.request.CreateTransactionRequest;
import com.ellen.finvoice.infrastructure.http.response.TransactionResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.ellen.finvoice.application.GetFinancialSummaryUseCase;
import com.ellen.finvoice.infrastructure.http.response.FinancialSummaryResponse;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

        private final CreateTransactionUseCase createTransactionUseCase;
        private final ListTransactionsUseCase listTransactionsUseCase;
        private final GetFinancialSummaryUseCase getFinancialSummaryUseCase;

        public TransactionController(
                        CreateTransactionUseCase createTransactionUseCase,
                        ListTransactionsUseCase listTransactionsUseCase,
                        GetFinancialSummaryUseCase getFinancialSummaryUseCase) {
                this.createTransactionUseCase = createTransactionUseCase;
                this.listTransactionsUseCase = listTransactionsUseCase;
                this.getFinancialSummaryUseCase = getFinancialSummaryUseCase;
        }

        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        public TransactionResponse createTransaction(
                        @Valid @RequestBody CreateTransactionRequest request) {
                var transaction = createTransactionUseCase.execute(
                                request.description(),
                                request.amount(),
                                request.type(),
                                request.category());

                return TransactionResponse.from(transaction);
        }

        @GetMapping
        public List<TransactionResponse> listTransactions() {
                return listTransactionsUseCase.execute()
                                .stream()
                                .map(TransactionResponse::from)
                                .toList();
        }

        @GetMapping("/category/{category}")
        public List<TransactionResponse> listTransactionsByCategory(
                        @PathVariable Category category) {
                return listTransactionsUseCase.executeByCategory(category)
                                .stream()
                                .map(TransactionResponse::from)
                                .toList();
        }

        @GetMapping("/summary")
        public FinancialSummaryResponse getFinancialSummary() {
                var summary = getFinancialSummaryUseCase.execute();
                return FinancialSummaryResponse.from(summary);
        }
}