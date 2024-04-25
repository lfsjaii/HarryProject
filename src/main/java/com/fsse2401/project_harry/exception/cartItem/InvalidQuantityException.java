package com.fsse2401.project_harry.exception.cartItem;

import com.fsse2401.project_harry.data.product.entity.ProductEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidQuantityException extends RuntimeException{
    public InvalidQuantityException(ProductEntity product, Integer quantity) {
        super(String.format("Invalid quantity - quantity : %d, stock : %d", quantity, product.getStock()));
    }
}
