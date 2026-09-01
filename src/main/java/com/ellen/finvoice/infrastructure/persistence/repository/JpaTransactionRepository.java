package com.ellen.finvoice.infrastructure.persistence.repository;

import com.ellen.finvoice.domain.Category;
import com.ellen.finvoice.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaTransactionRepository extends JpaRepository<TransactionEntity, UUID> {

    List<TransactionEntity> findByCategory(Category category);
}