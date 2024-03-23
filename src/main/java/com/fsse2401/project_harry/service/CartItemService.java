package com.fsse2401.project_harry.service;

import com.fsse2401.project_harry.data.cartitem.domainObject.GetCartItemResponseData;
import com.fsse2401.project_harry.data.cartitem.entity.CartItemEntity;
import com.fsse2401.project_harry.data.user.domainObject.FirebaseUserData;
import com.fsse2401.project_harry.data.user.entity.UserEntity;

import java.util.List;

public interface CartItemService {
    boolean putCartItem(FirebaseUserData firebaseUserData,
                     Integer pid, Integer quantity);

    List<GetCartItemResponseData> getUserCartItem(FirebaseUserData firebaseUserData);

    GetCartItemResponseData upDateCartItem(Integer pid, Integer quantity, FirebaseUserData firebaseUserData);

    boolean deleteCartItemByPid(FirebaseUserData firebaseUserData, Integer pid);

    List<CartItemEntity> getEntityListByUser(UserEntity userEntity);

    List<CartItemEntity> getAllUserEntity(UserEntity userEntity);

    void emptyUserCart(String firebaseUid);
}
