package com.fsse2401.project_harry.service.impl;

import com.fsse2401.project_harry.data.cartitem.entity.CartItemEntity;
import com.fsse2401.project_harry.data.transaction.domainObject.TransactionResponseData;
import com.fsse2401.project_harry.data.transaction.entity.TransactionEntity;
import com.fsse2401.project_harry.data.user.domainObject.FirebaseUserData;
import com.fsse2401.project_harry.data.user.entity.UserEntity;
import com.fsse2401.project_harry.exception.transaction.PrepareTransactionException;
import com.fsse2401.project_harry.exception.transaction.TransactionNotFoundException;
import com.fsse2401.project_harry.repository.TransactionRepository;
import com.fsse2401.project_harry.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    private  final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
    private final UserService userService;
    private final CartItemService cartItemService;
    private final TransactionProductService transactionProductService;
    private final TransactionRepository transactionRepository;
    private final ProductService productService;

    @Autowired
    public TransactionServiceImpl(UserService userService, CartItemService cartItemService, TransactionProductService transactionProductService, TransactionRepository transactionRepository, ProductService productService) {
        this.userService = userService;
        this.cartItemService = cartItemService;
        this.transactionProductService = transactionProductService;
        this.transactionRepository = transactionRepository;
        this.productService = productService;
    }
    @Override
    public TransactionResponseData createTransaction(FirebaseUserData firebaseUserData) {
        try{
            UserEntity userEntity = userService.getEntityByFirebaseUserData(firebaseUserData);
            List<CartItemEntity> cartItemEntityList = cartItemService.getAllUserEntity(userEntity);
            if(cartItemEntityList.isEmpty()) {
                throw new PrepareTransactionException();
            }
            TransactionEntity transactionEntity = new TransactionEntity(userEntity, "PREPARE", cartItemEntityList);
            transactionRepository.save(transactionEntity);
            return new TransactionResponseData(transactionProductService.createNewTransactionProduct(cartItemEntityList,transactionEntity));
        } catch (Exception ex) {
            logger.warn("Transaction is Empty");
            throw ex;
        }
    }
    @Override
    public TransactionResponseData getAllTransaction(FirebaseUserData firebaseUserData, Integer tid) {
        try {
            UserEntity userEntity = userService.getEntityByFirebaseUserData(firebaseUserData);
            TransactionEntity transactionEntity = transactionRepository.findByTidAndUser(tid, userEntity).get();
            TransactionResponseData transactionResponseData = new TransactionResponseData(transactionEntity);
            return transactionResponseData;
//      return new TransactionResponseData(transactionRepository.findByTidAndUser(tid, userEntity).orElseThrow());
        }catch (TransactionNotFoundException ex) {
            logger.warn("get Transaction Failed" + ex.getMessage());
            throw ex;
        }
    }
    @Override
    public TransactionResponseData updateTransactionStatus(FirebaseUserData firebaseUserData, Integer tid) {
        UserEntity userEntity = userService.getEntityByFirebaseUserData(firebaseUserData);
        TransactionEntity transactionEntity = transactionRepository.findByTidAndUser(tid, userEntity).orElseThrow(PrepareTransactionException::new);
        transactionEntity.setStatus("PROCESSING");
        productService.reduceStock(transactionEntity);
        transactionRepository.save(transactionEntity);
        return new TransactionResponseData(transactionEntity);
    }


    @Override
    public TransactionResponseData successTransaction(FirebaseUserData firebaseUserData, Integer tid){
        UserEntity userEntity = userService.getEntityByFirebaseUserData(firebaseUserData);
        TransactionEntity transactionEntity = transactionRepository.findByTidAndUser(tid, userEntity).orElseThrow(PrepareTransactionException::new);
        transactionEntity.setStatus("SUCCESS");
        cartItemService.deleteAllCartItemsforUser(userEntity);
        return new TransactionResponseData(transactionRepository.save(transactionEntity));
    }



}

