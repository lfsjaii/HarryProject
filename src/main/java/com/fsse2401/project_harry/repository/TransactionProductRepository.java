package com.fsse2401.project_harry.repository;

import com.fsse2401.project_harry.data.transaction.entity.TransactionEntity;
import com.fsse2401.project_harry.data.transaction_product.entity.TransactionProductEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionProductRepository  extends CrudRepository<TransactionProductEntity, Integer> {
    List<TransactionProductEntity> findAllByTransaction(TransactionEntity transactionEntity);
}
