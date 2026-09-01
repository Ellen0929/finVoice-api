package com.ellen.finvoice.infrastructure.persistence.repository;

import com.ellen.finvoice.domain.Category;
import com.ellen.finvoice.domain.Transaction;
import com.ellen.finvoice.domain.TransactionRepository;
import com.ellen.finvoice.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TransactionRepositoryAdapter implements TransactionRepository {

    private final JpaTransactionRepository jpaTransactionRepository;

    public TransactionRepositoryAdapter(JpaTransactionRepository jpaTransactionRepository) {
        this.jpaTransactionRepository = jpaTransactionRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        TransactionEntity entity = toEntity(transaction);
        TransactionEntity savedEntity = jpaTransactionRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public List<Transaction> findAll() {
        return jpaTransactionRepository.findAll()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<Transaction> findByCategory(Category category) {
        return jpaTransactionRepository.findByCategory(category)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Optional<Transaction> findById(UUID id) {
        return jpaTransactionRepository.findById(id)
                .map(this::toDomain);
    }

    private TransactionEntity toEntity(Transaction transaction) {
        return new TransactionEntity(
                transaction.getId(),
                transaction.getDescription(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getCategory(),
                transaction.getCreatedAt()
        );
    }

    private Transaction toDomain(TransactionEntity entity) {
        return new Transaction(
                entity.getId(),
                entity.getDescription(),
                entity.getAmount(),
                entity.getType(),
                entity.getCategory(),
                entity.getCreatedAt()
        );
    }
}