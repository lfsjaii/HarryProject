package com.fsse2401.project_harry.repository;

import com.fsse2401.project_harry.data.cartitem.entity.CartItemEntity;
import com.fsse2401.project_harry.data.product.entity.ProductEntity;
import com.fsse2401.project_harry.data.user.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends CrudRepository<CartItemEntity, Integer> {
    Optional<CartItemEntity> findByProductPidAndUserFirebaseUid(Integer pid, String uid);
    Integer deleteByProduct_PidAndUser_FirebaseUid(Integer pid, String firebase);
    List<CartItemEntity> findAllByUser(UserEntity userEntity);
    void deleteAllByUser_FirebaseUid(String firebaseUid);


}
