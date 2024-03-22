package com.fsse2401.project_harry.repository;

import com.fsse2401.project_harry.data.product.entity.ProductEntity;
import com.fsse2401.project_harry.data.transaction_product.entity.TransactionProductEntity;
import org.springframework.data.repository.CrudRepository;

public interface TransactionProductRepository  extends CrudRepository<TransactionProductEntity, Integer> {
}
