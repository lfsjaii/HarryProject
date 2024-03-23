package com.fsse2401.project_harry.data.transaction.domainObject;

import com.fsse2401.project_harry.data.cartitem.status.TransactionStatus;
import com.fsse2401.project_harry.data.transaction.entity.TransactionEntity;
import com.fsse2401.project_harry.data.transaction_product.domainObject.TransactionProductResponseData;
import com.fsse2401.project_harry.data.transaction_product.entity.TransactionProductEntity;
import com.fsse2401.project_harry.data.user.domainObject.UserResponseData;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TransactionResponseData {
    private Integer tid;
    private UserResponseData user;
    private LocalDateTime dateTime;
    private TransactionStatus status;
    private BigDecimal total;
    private List<TransactionProductResponseData> transactionProductList = new ArrayList<>();

    public TransactionResponseData(TransactionEntity entity, List<TransactionProductEntity> transactionProductEntityList) {
        this.tid = entity.getTid();
        this.user = new UserResponseData(entity.getUser());
        this.dateTime = entity.getDatetime();
        this.status = entity.getStatus();
        this.total = entity.getTotal();
        for (TransactionProductEntity transactionProductEntity : transactionProductEntityList) {
            TransactionProductResponseData transactionProductResponseData = new TransactionProductResponseData(transactionProductEntity);
            this.transactionProductList.add(transactionProductResponseData);
        }
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public UserResponseData getUser() {
        return user;
    }

    public void setUser(UserResponseData user) {
        this.user = user;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<TransactionProductResponseData> getTransactionProductList() {
        return transactionProductList;
    }

    public void setTransactionProductList(List<TransactionProductResponseData> transactionProductList) {
        this.transactionProductList = transactionProductList;
    }
}
