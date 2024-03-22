package com.fsse2401.project_harry.service;

import com.fsse2401.project_harry.data.cartitem.entity.CartItemEntity;
import com.fsse2401.project_harry.data.transaction.entity.TransactionEntity;
import com.fsse2401.project_harry.data.transaction_product.entity.TransactionProductEntity;

import java.util.List;

public interface TransactionProductService {
    TransactionEntity createNewTransactionProduct(List<CartItemEntity> cartItemEntityList, TransactionEntity transactionEntity);
}
