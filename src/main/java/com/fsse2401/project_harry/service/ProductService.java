package com.fsse2401.project_harry.service;

import com.fsse2401.project_harry.data.product.domainObject.ProductResponseData;
import com.fsse2401.project_harry.data.product.entity.ProductEntity;
import com.fsse2401.project_harry.data.transaction.entity.TransactionEntity;

import java.util.List;

public interface ProductService {
    List<ProductResponseData> getAllProducts();

    ProductResponseData getByPid(Integer pid);

    ProductEntity getEntityByPid(Integer pid);

    boolean isValidQuantity(ProductEntity entity, Integer quantity);

    void reduceStock(TransactionEntity transactionEntity);

    boolean deductStock(ProductEntity entity, int amount);
}
