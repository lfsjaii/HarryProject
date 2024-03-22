package com.fsse2401.project_harry.service.impl;

import com.fsse2401.project_harry.data.product.domainObject.ProductResponseData;
import com.fsse2401.project_harry.data.product.entity.ProductEntity;
import com.fsse2401.project_harry.data.transaction.entity.TransactionEntity;
import com.fsse2401.project_harry.data.transaction_product.entity.TransactionProductEntity;
import com.fsse2401.project_harry.exception.product.ProductNotFoundException;
import com.fsse2401.project_harry.exception.product.ProductNullPointerException;
import com.fsse2401.project_harry.repository.ProductRepository;
import com.fsse2401.project_harry.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //Get all products
    @Override
    public List<ProductResponseData> getAllProducts() {
        List<ProductResponseData> responseDataList = new ArrayList<>();

        try {
            for (ProductEntity entity : productRepository.findAll()) {
                responseDataList.add(new ProductResponseData(entity));
            }
            if (responseDataList.isEmpty()) {
                throw new ProductNullPointerException();
            }
            return responseDataList;
        } catch (ProductNullPointerException ex) {
            logger.warn("Product List is: " + ex.getMessage());
            throw ex;
        }
    }

    //Get Product By Product ID
    @Override
    public ProductResponseData getByPid(Integer pid) {
        try {
            if(pid == null) {
                throw new ProductNotFoundException();
            }
            ProductEntity entity = getEntityByPid(pid);
            return new ProductResponseData(entity);
        } catch (ProductNotFoundException ex) {
            logger.warn("Product with PID " + pid + " not found");
            throw ex;
        }
    }

    //Get Product Entity By ID
    @Override
    public ProductEntity getEntityByPid(Integer pid) {
        return productRepository.findById(pid).orElseThrow(ProductNotFoundException::new);
    }



    @Override
    public boolean isValidQuantity(ProductEntity entity, Integer quantity) {
        if(quantity < 1) {
            return false;
        } else if(quantity > entity.getStock()){
            return false;
        }
        return true;
    }
    @Override
    public void reduceStock(TransactionEntity transactionEntity){
        for (TransactionProductEntity transactionProductEntity : transactionEntity.getProductsHasInTransaction())
        {
            ProductEntity productEntity = getEntityByPid(transactionProductEntity.getPid());
            productEntity.setStock(productEntity.getStock() - transactionProductEntity.getQuantity());
            productRepository.save(productEntity);
        }
    }
    @Override
    public boolean deductStock(ProductEntity entity, int amount) {
        if(!isValidQuantity(entity,amount)) {
            return false;
        }

        entity.setStock(entity.getStock() - amount);
        productRepository.save(entity);
        return true;
    }
}
