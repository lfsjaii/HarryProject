package com.fsse2401.project_harry.data.transaction_product.domainObject;

import com.fsse2401.project_harry.data.product.domainObject.ProductResponseData;
import com.fsse2401.project_harry.data.transaction_product.entity.TransactionProductEntity;

import java.math.BigDecimal;

public class TransactionProductResponseData {
    private Integer tpid;
    private ProductResponseData productResponseData;
    private Integer quantity;
    private BigDecimal subtotal = BigDecimal.ZERO;

    public TransactionProductResponseData(TransactionProductEntity transactionProductEntity) {
        this.tpid = transactionProductEntity.getTpid();
        this.productResponseData = new ProductResponseData(transactionProductEntity);
        this.quantity = transactionProductEntity.getQuantity();
        subtotal = subtotal.add(new BigDecimal(quantity).multiply(productResponseData.getPrice()));
    }


    public Integer getTpid() {
        return tpid;
    }

    public void setTpid(Integer tpid) {
        this.tpid = tpid;
    }

    public ProductResponseData getProductResponseData() {
        return productResponseData;
    }

    public void setProductResponseData(ProductResponseData productResponseData) {
        this.productResponseData = productResponseData;
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
