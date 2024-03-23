package com.fsse2401.project_harry.data.transaction.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fsse2401.project_harry.data.cartitem.status.TransactionStatus;
import com.fsse2401.project_harry.data.transaction.domainObject.TransactionResponseData;
import com.fsse2401.project_harry.data.transaction_product.domainObject.TransactionProductResponseData;
import com.fsse2401.project_harry.data.transaction_product.dto.response.TransactionProductResponseDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionResponseDto {
    private Integer tid;

    @JsonProperty("buyer_uid")
    private Integer buyerUid;


    @JsonFormat(pattern = "yyyyMMdd'T'HH:mm:ss")
    private LocalDateTime dateTime;

    private TransactionStatus status;

    private BigDecimal total;

    @JsonProperty("items")
    List<TransactionProductResponseDto> item = new ArrayList<>();

    public TransactionResponseDto(TransactionResponseData data) {
        this.tid = data.getTid();
        this.buyerUid = data.getUser().getUid();
        this.dateTime = data.getDateTime();
        this.status = data.getStatus();
        this.total = data.getTotal();
        for (TransactionProductResponseData item : data.getTransactionProductList()) {
            this.item.add(new TransactionProductResponseDto(item));
        }
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getBuyerUid() {
        return buyerUid;
    }

    public void setBuyerUid(Integer buyerUid) {
        this.buyerUid = buyerUid;
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

    public List<TransactionProductResponseDto> getItem() {
        return item;
    }

    public void setItem(List<TransactionProductResponseDto> item) {
        this.item = item;
    }
}
