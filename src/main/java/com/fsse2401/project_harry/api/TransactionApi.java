package com.fsse2401.project_harry.api;

import com.fsse2401.project_harry.config.EnvConfig;
import com.fsse2401.project_harry.data.transaction.domainObject.TransactionResponseData;
import com.fsse2401.project_harry.data.transaction.dto.response.TransactionResponseDto;
import com.fsse2401.project_harry.data.transaction.dto.response.TransactionSuccessDto;
import com.fsse2401.project_harry.service.TransactionService;
import com.fsse2401.project_harry.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
@CrossOrigin({EnvConfig.DEV_BASE_URL, EnvConfig.PROD_S3_BASE_URL, EnvConfig.PROD_BASE_URL})
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
        return new TransactionResponseDto(transactionService.getAllTransaction(JwtUtil.getFirebaseUserData(jwtToken),tid));
    }

    @PatchMapping("/{tid}/pay")
    public TransactionSuccessDto payTransactionStatus(JwtAuthenticationToken jwtToken, @PathVariable Integer tid) {
        transactionService.payTransaction(JwtUtil.getFirebaseUserData(jwtToken),tid);
        return new TransactionSuccessDto();
    }

    @PatchMapping("/{tid}/finish")
    public TransactionResponseDto successTransaction(@PathVariable Integer tid, JwtAuthenticationToken jwtToken){
        return new TransactionResponseDto(transactionService.finishTransaction(JwtUtil.getFirebaseUserData(jwtToken),tid));
    }
}
