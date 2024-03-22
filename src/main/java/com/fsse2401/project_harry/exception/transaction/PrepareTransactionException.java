package com.fsse2401.project_harry.exception.transaction;

import com.fsse2401.project_harry.data.user.entity.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.Format;

@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class PrepareTransactionException extends RuntimeException{
}
