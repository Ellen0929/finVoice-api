package com.ellen.finvoice.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository {

    Transaction save(Transaction transaction);

    List<Transaction> findAll();

    List<Transaction> findByCategory(Category category);

    Optional<Transaction> findById(UUID id);
}