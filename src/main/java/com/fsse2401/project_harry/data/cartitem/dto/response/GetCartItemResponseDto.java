package com.fsse2401.project_harry.data.cartitem.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fsse2401.project_harry.data.cartitem.domainObject.GetCartItemResponseData;

import java.math.BigDecimal;

public class GetCartItemResponseDto {
    private Integer pid;
    private String name;
    @JsonProperty("image_url")
    private String imageUrl;
    private BigDecimal price;
    @JsonProperty("cart_quantity")
    private Integer cartQuantity;
    private Integer stock;

    public GetCartItemResponseDto(GetCartItemResponseData data) {
        this.pid = data.getPid();
        this.name = data.getName();
        this.imageUrl = data.getImageUrl();
        this.price = data.getPrice();
        this.cartQuantity = data.getCartQuantity();
        this.stock = data.getStock();
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
