package com.fsse2401.project_harry.exception.transaction;

import com.fsse2401.project_harry.data.user.entity.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(Integer tid, UserEntity userEntity){
        super(String.format("Transaction Not Found tid:%s firebase:%s",tid,userEntity));
    }
}
