package com.fsse2401.project_harry.data.cartitem.domainObject;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fsse2401.project_harry.data.cartitem.dto.response.GetCartItemResponseDto;
import com.fsse2401.project_harry.data.cartitem.entity.CartItemEntity;
import com.fsse2401.project_harry.data.product.entity.ProductEntity;

import java.math.BigDecimal;

public class GetCartItemResponseData {
    private Integer pid;
    private String name;
    private String imageUrl;
    private BigDecimal price;
    private Integer cartQuantity;
    private Integer stock;

    public GetCartItemResponseData(CartItemEntity entity) {
        this.pid = entity.getProduct().getPid();
        this.name = entity.getProduct().getName();
        this.imageUrl = entity.getProduct().getImageUrl();
        this.price = entity.getProduct().getPrice();
        this.cartQuantity = entity.getQuantity();
        this.stock = entity.getProduct().getStock();
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(Integer cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
