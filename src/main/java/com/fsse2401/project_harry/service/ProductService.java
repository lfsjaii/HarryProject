package com.fsse2401.project_harry.service;

import com.fsse2401.project_harry.data.product.domainObject.ProductResponseData;
import com.fsse2401.project_harry.data.product.entity.ProductEntity;

import java.util.List;

public interface ProductService {
    List<ProductResponseData> getAllProducts();

    ProductResponseData getByPid(Integer pid);

    ProductEntity getEntityByPid(Integer pid);

    boolean isValidQuantity(ProductEntity entity, Integer quantity);

    boolean isValidQuantity(Integer pid, Integer quantity);

    boolean deductStock(int pid, int amount);
}
