package com.fsse2401.project_harry.data.transaction.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fsse2401.project_harry.data.transaction.domainObject.TransactionResponseData;
import com.fsse2401.project_harry.data.transaction_product.domainObject.TransactionProductResponseData;
import com.fsse2401.project_harry.data.transaction_product.dto.response.TransactionProductResponseDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TransactionResponseDto {
    private Integer tid;

    @JsonProperty("buyer_uid")
    private Integer buyerUid;

    private String dateTime;

    private String status;

    private BigDecimal total;

    @JsonProperty("items")
    List<TransactionProductResponseDto> productsHasInTransactionResponseDtoList = new ArrayList<>();

    public TransactionResponseDto(TransactionResponseData data) {
        this.tid = data.getTid();
        this.buyerUid = data.getBuyerUid();
        this.dateTime = data.getDateTime();
        this.status = data.getStatus();
        this.total = data.getTotal();
        for (TransactionProductResponseData transactionProductResponseData : data.getProductsHasInTransactionResponseList()) {
            productsHasInTransactionResponseDtoList.add(new TransactionProductResponseDto (transactionProductResponseData));
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<TransactionProductResponseDto> getProductsHasInTransactionResponseDtoList() {
        return productsHasInTransactionResponseDtoList;
    }

    public void setProductsHasInTransactionResponseDtoList(List<TransactionProductResponseDto> productsHasInTransactionResponseDtoList) {
        this.productsHasInTransactionResponseDtoList = productsHasInTransactionResponseDtoList;
    }
}
