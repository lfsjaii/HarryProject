package com.fsse2401.project_harry.service.impl;

import com.fsse2401.project_harry.data.cartitem.entity.CartItemEntity;
import com.fsse2401.project_harry.data.cartitem.status.TransactionStatus;
import com.fsse2401.project_harry.data.transaction.domainObject.TransactionResponseData;
import com.fsse2401.project_harry.data.transaction.entity.TransactionEntity;
import com.fsse2401.project_harry.data.transaction_product.entity.TransactionProductEntity;
import com.fsse2401.project_harry.data.user.domainObject.FirebaseUserData;
import com.fsse2401.project_harry.data.user.entity.UserEntity;
import com.fsse2401.project_harry.exception.transaction.PayTransactionException;
import com.fsse2401.project_harry.exception.transaction.PrepareTransactionException;
import com.fsse2401.project_harry.exception.transaction.TransactionNotFoundException;
import com.fsse2401.project_harry.repository.TransactionRepository;
import com.fsse2401.project_harry.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
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
            List<CartItemEntity> cartItemEntityList = cartItemService.getEntityListByUser(userEntity);

            if(cartItemEntityList.isEmpty()) {
                throw new PrepareTransactionException("Cart is empty!");
            }

            TransactionEntity transactionEntity = new TransactionEntity(userEntity);
            transactionEntity = transactionRepository.save(transactionEntity);

            List<TransactionProductEntity> transactionProductEntityList = new ArrayList<>();
            BigDecimal total = BigDecimal.ZERO;

            for (CartItemEntity cartItemEntity : cartItemEntityList) {
                TransactionProductEntity transactionProductEntity = transactionProductService.createNewTransactionProduct(transactionEntity,cartItemEntity);
                transactionProductEntityList.add(transactionProductEntity);
                total = total.add(transactionProductEntity.getPrice().multiply(new BigDecimal(transactionProductEntity.getQuantity())));
            }

            transactionEntity.setTotal(total);
            transactionEntity = transactionRepository.save(transactionEntity);

            return new TransactionResponseData(transactionEntity, transactionProductEntityList);
        } catch (DataAccessException ex) {
            logger.error("Database operation failed: " + ex.getMessage());
            throw new RuntimeException("Database operation failed", ex);
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            throw ex;
        }
    }
    @Override
    public TransactionResponseData getAllTransaction(FirebaseUserData firebaseUserData, Integer tid) {
        try {
            TransactionEntity transactionEntity = getEntityByTidAndFirebaseUid(tid,firebaseUserData.getFirebaseUid());
            List<TransactionProductEntity> transactionProductEntityList = transactionProductService.getEntityListByTransaction(transactionEntity);
            return new TransactionResponseData(transactionEntity,transactionProductEntityList);
        }catch (TransactionNotFoundException ex) {
            logger.warn("Get transaction failed" + ex.getMessage());
            throw ex;
        }
    }
    @Override
    public boolean payTransaction(FirebaseUserData firebaseUserData, Integer tid) {
        try {
            TransactionEntity transactionEntity = getEntityByTidAndFirebaseUid(tid,firebaseUserData.getFirebaseUid());
            if(transactionEntity.getStatus() != TransactionStatus.PREPARE) {
                throw new PayTransactionException("Transaction status is not PREPARE");
            }

            List<TransactionProductEntity> transactionProductEntityList = transactionProductService.getEntityListByTransaction(transactionEntity);
            for (TransactionProductEntity transactionProductEntity : transactionProductEntityList) {
                if(!productService.isValidQuantity(transactionProductEntity.getPid(),transactionProductEntity.getQuantity())) {
                    throw new PayTransactionException("Not enough stock for product with ID: " + transactionProductEntity.getPid() + " stock: "+ transactionProductEntity.getStock());
                }
            }

            for (TransactionProductEntity transactionProductEntity : transactionProductEntityList) {
                productService.deductStock(transactionProductEntity.getPid(),transactionProductEntity.getQuantity());
            }
            transactionEntity.setStatus(TransactionStatus.PROCESSING);
            transactionRepository.save(transactionEntity);
            return true;
            } catch (TransactionNotFoundException ex) {
            logger.warn("Get transaction failed" + ex.getMessage());
            throw ex;
            } catch (PayTransactionException ex) {
                logger.warn("Pay transaction failed: " + ex.getMessage());
                throw ex;
            }
        }



    @Override
    @Transactional
    public TransactionResponseData finishTransaction(FirebaseUserData firebaseUserData, Integer tid) {
        try {
            TransactionEntity transactionEntity = getEntityByTidAndFirebaseUid(tid, firebaseUserData.getFirebaseUid());

            if (transactionEntity.getStatus() != TransactionStatus.PROCESSING) {
                throw new PayTransactionException("Transaction status is not PROCESSING");
            }

            cartItemService.emptyUserCart(firebaseUserData.getFirebaseUid());

            transactionEntity.setStatus(TransactionStatus.SUCCESS);
            transactionRepository.save(transactionEntity);

            return new TransactionResponseData(transactionEntity, transactionProductService.getEntityListByTransaction(transactionEntity));
        } catch (Exception ex) {
            logger.warn("Finish transaction failed: " + ex.getMessage());
            throw ex;
        }
    }

    public TransactionEntity getEntityByTidAndFirebaseUid(Integer tid, String firebaseUid) {
        return transactionRepository.findByTidAndUser_FirebaseUid(tid,firebaseUid).orElseThrow(()-> new TransactionNotFoundException(tid,firebaseUid));
    }

}

