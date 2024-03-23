package com.fsse2401.project_harry.service;

import com.fsse2401.project_harry.data.cartitem.dto.response.CartItemSuccessDto;
import com.fsse2401.project_harry.data.transaction.domainObject.TransactionResponseData;
import com.fsse2401.project_harry.data.user.domainObject.FirebaseUserData;

public interface TransactionService {
    TransactionResponseData createTransaction(FirebaseUserData firebaseUserData);

    TransactionResponseData getAllTransaction(FirebaseUserData firebaseUserData, Integer tid);

    boolean payTransaction(FirebaseUserData firebaseUserData, Integer tid);

    TransactionResponseData finishTransaction(FirebaseUserData firebaseUserData, Integer tid);
}
