package com.fsse2401.project_harry.repository;

import com.fsse2401.project_harry.data.product.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;


public interface ProductRepository extends CrudRepository<ProductEntity, Integer> {
}
