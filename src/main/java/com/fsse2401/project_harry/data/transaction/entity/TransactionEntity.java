package com.fsse2401.project_harry.data.transaction.entity;

import com.fsse2401.project_harry.data.cartitem.entity.CartItemEntity;
import com.fsse2401.project_harry.data.transaction_product.entity.TransactionProductEntity;
import com.fsse2401.project_harry.data.user.entity.UserEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "transaction")
public class TransactionEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer tid;
    @ManyToOne
    @JoinColumn(name = "buyer_uid",nullable = false)
    private UserEntity user;
    @Column(nullable = false)
    private LocalDateTime datetime;
    @Column(nullable = false)
    private String status;
    @Column(nullable = false)
    private BigDecimal total = BigDecimal.ZERO;
    @OneToMany(mappedBy = "transaction")
    List<TransactionProductEntity> productsHasInTransaction = new ArrayList<>();


    public TransactionEntity() {
    }

    public TransactionEntity(UserEntity user, String status, List<CartItemEntity> cartItemEntityList) {
        this.user = user;
        this.datetime = LocalDateTime.now();
        this.status = status;

        for (CartItemEntity cartItemEntity : cartItemEntityList)
        {
            total = total.add(cartItemEntity.getProduct().getPrice().multiply(new BigDecimal(cartItemEntity.getQuantity())));
        }
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
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

    public List<TransactionProductEntity> getProductsHasInTransaction() {
        return productsHasInTransaction;
    }

    public void setProductsHasInTransaction(List<TransactionProductEntity> productsHasInTransaction) {
        this.productsHasInTransaction = productsHasInTransaction;
    }
}
