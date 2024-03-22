package com.fsse2401.project_harry.data.transaction_product.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fsse2401.project_harry.data.product.dto.response.ProductResponseDto;
import com.fsse2401.project_harry.data.transaction_product.domainObject.TransactionProductResponseData;

import java.math.BigDecimal;
@JsonPropertyOrder({"tpid", "productResponseDto","quantity","subtotal"})
public class TransactionProductResponseDto {
    private Integer tpid;
    @JsonProperty("product")
    private ProductResponseDto productResponseDto;
    private Integer quantity;
    private BigDecimal subtotal = BigDecimal.ZERO;

    public TransactionProductResponseDto(TransactionProductResponseData data) {
        this.tpid = data.getTpid();
        this.productResponseDto = new ProductResponseDto(data.getProductResponseData());
        this.quantity = data.getQuantity();
        this.subtotal = data.getSubtotal();
    }

    public Integer getTpid() {
        return tpid;
    }

    public void setTpid(Integer tpid) {
        this.tpid = tpid;
    }

    public ProductResponseDto getProductResponseDto() {
        return productResponseDto;
    }

    public void setProductResponseDto(ProductResponseDto productResponseDto) {
        this.productResponseDto = productResponseDto;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
