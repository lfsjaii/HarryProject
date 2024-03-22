package com.fsse2401.project_harry.api;

import com.fsse2401.project_harry.data.cartitem.dto.response.CartItemSuccessDto;
import com.fsse2401.project_harry.data.transaction.domainObject.TransactionResponseData;
import com.fsse2401.project_harry.data.transaction.dto.response.TransactionResponseDto;
import com.fsse2401.project_harry.service.TransactionService;
import com.fsse2401.project_harry.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionApi {
    private  final TransactionService transactionService;

    @Autowired
    public TransactionApi(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/prepare")
    public TransactionResponseDto createNewTransaction(JwtAuthenticationToken jwtToken) {
        TransactionResponseData transactionResponseData = transactionService.createTransaction(JwtUtil.getFirebaseUserData(jwtToken));
        return new TransactionResponseDto(transactionResponseData);
    }

    @GetMapping("/{tid}")
    public TransactionResponseDto getAllTransaction(JwtAuthenticationToken jwtToken, @PathVariable Integer tid){
        TransactionResponseData transactionResponseData = transactionService.getAllTransaction(JwtUtil.getFirebaseUserData(jwtToken),tid);
        TransactionResponseDto transactionResponseDto = new TransactionResponseDto(transactionResponseData);
        return transactionResponseDto;
    }

    @PatchMapping("/{tid}/pay")
    public TransactionResponseDto updateTransactionStatus(JwtAuthenticationToken jwtToken, @PathVariable Integer tid) {
        {
            TransactionResponseData transactionResponseData = transactionService.updateTransactionStatus(JwtUtil.getFirebaseUserData(jwtToken),tid);
            TransactionResponseDto transactionResponseDto = new TransactionResponseDto(transactionResponseData);
            return transactionResponseDto;
        }
    }

    @PatchMapping("/{tid}/finish")
    public TransactionResponseDto successTransaction (@PathVariable Integer tid, JwtAuthenticationToken jwtToken){
        TransactionResponseData transactionResponseData = transactionService.successTransaction(JwtUtil.getFirebaseUserData(jwtToken), tid);
        TransactionResponseDto transactionResponseDto = new TransactionResponseDto(transactionResponseData);
        return transactionResponseDto;
    }
}
