package com.fsse2401.project_harry.data.cartitem.domainObject;

import com.fsse2401.project_harry.data.cartitem.entity.CartItemEntity;
import com.fsse2401.project_harry.data.product.domainObject.ProductResponseData;
import com.fsse2401.project_harry.data.user.domainObject.UserResponseData;


import java.math.BigDecimal;

public class GetCartItemResponseData {
    private Integer cid;
    private ProductResponseData product;
    private UserResponseData user;
    private Integer quantity;

    public GetCartItemResponseData(CartItemEntity entity) {
        this.cid = entity.getCid();
        this.product = new ProductResponseData(entity.getProduct());
        this.user = new UserResponseData(entity.getUser());
        this.quantity = entity.getQuantity();
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public ProductResponseData getProduct() {
        return product;
    }

    public void setProduct(ProductResponseData product) {
        this.product = product;
    }

    public UserResponseData getUser() {
        return user;
    }

    public void setUser(UserResponseData user) {
        this.user = user;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    //    private Integer pid;
//    private String name;
//    private String imageUrl;
//    private BigDecimal price;
//    private Integer cartQuantity;
//    private Integer stock;
//
//    public GetCartItemResponseData(CartItemEntity entity) {
//        this.pid = entity.getProduct().getPid();
//        this.name = entity.getProduct().getName();
//        this.imageUrl = entity.getProduct().getImageUrl();
//        this.price = entity.getProduct().getPrice();
//        this.cartQuantity = entity.getQuantity();
//        this.stock = entity.getProduct().getStock();
//    }
}
