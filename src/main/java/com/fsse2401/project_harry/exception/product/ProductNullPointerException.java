package com.fsse2401.project_harry.exception.product;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNullPointerException extends RuntimeException{
    public ProductNullPointerException() {
        super("Null Value");
    }
}
