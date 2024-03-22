package com.fsse2401.project_harry.service.impl;


import com.fsse2401.project_harry.data.cartitem.entity.CartItemEntity;
import com.fsse2401.project_harry.data.transaction.entity.TransactionEntity;
import com.fsse2401.project_harry.data.transaction_product.entity.TransactionProductEntity;

import com.fsse2401.project_harry.repository.TransactionProductRepository;
import com.fsse2401.project_harry.service.TransactionProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionProductServiceImpl implements TransactionProductService {
    private final TransactionProductRepository transactionProductRepository;

    @Autowired
    public TransactionProductServiceImpl(TransactionProductRepository transactionProductRepository) {
        this.transactionProductRepository = transactionProductRepository;
    }


    @Override
    public TransactionEntity createNewTransactionProduct(List<CartItemEntity> cartItemEntityList, TransactionEntity transactionEntity) {
        for (CartItemEntity cartItemEntity : cartItemEntityList) {
            transactionEntity.getProductsHasInTransaction().add(
                    transactionProductRepository.save(new TransactionProductEntity(transactionEntity, cartItemEntity)));
        }
        return transactionEntity;
    }
}
