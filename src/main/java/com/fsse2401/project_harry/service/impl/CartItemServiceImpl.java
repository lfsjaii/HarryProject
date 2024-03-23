package com.fsse2401.project_harry.service.impl;

import com.fsse2401.project_harry.data.cartitem.domainObject.GetCartItemResponseData;
import com.fsse2401.project_harry.data.cartitem.entity.CartItemEntity;
import com.fsse2401.project_harry.data.product.entity.ProductEntity;
import com.fsse2401.project_harry.data.user.domainObject.FirebaseUserData;
import com.fsse2401.project_harry.data.user.entity.UserEntity;
import com.fsse2401.project_harry.exception.cartItem.CartItemNotFoundException;
import com.fsse2401.project_harry.exception.cartItem.CartItemRemoveException;
import com.fsse2401.project_harry.exception.cartItem.InvalidQuantityException;
import com.fsse2401.project_harry.exception.product.ProductNotFoundException;
import com.fsse2401.project_harry.exception.user.UserNotFoundException;
import com.fsse2401.project_harry.repository.CartItemRepository;
import com.fsse2401.project_harry.service.CartItemService;
import com.fsse2401.project_harry.service.ProductService;
import com.fsse2401.project_harry.service.UserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {
    private final UserService userService;
    private final ProductService productService;
    private final CartItemRepository cartItemRepository;
    private final Logger logger = LoggerFactory.getLogger(CartItemServiceImpl.class);

    @Autowired
    public CartItemServiceImpl(UserService userService, ProductService productService, CartItemRepository cartItemRepository) {
        this.userService = userService;
        this.productService = productService;
        this.cartItemRepository = cartItemRepository;
    }

    //Add a new item to cart
    @Override
    public boolean putCartItem(FirebaseUserData firebaseUserData,
                            Integer pid, Integer quantity) {
        try {
            Optional<CartItemEntity> cartItemEntityOptional = cartItemRepository.findByProductPidAndUserFirebaseUid(pid, firebaseUserData.getFirebaseUid());
            if(cartItemEntityOptional.isEmpty()) {
                UserEntity userEntity = userService.getEntityByFirebaseUserData(firebaseUserData);
                ProductEntity productEntity = productService.getEntityByPid(pid);
                if(!productService.isValidQuantity(productEntity,quantity)) {
                    throw new InvalidQuantityException(productEntity,quantity);
                }
                CartItemEntity cartItemEntity = new CartItemEntity(productEntity, userEntity, quantity);
                cartItemRepository.save(cartItemEntity);
            } else {
                CartItemEntity cartItemEntity = cartItemEntityOptional.get();
                if(!productService.isValidQuantity(cartItemEntity.getProduct(),quantity + cartItemEntity.getQuantity())) {
                    throw new InvalidQuantityException(cartItemEntity.getProduct(), quantity + cartItemEntity.getQuantity());
                }
                cartItemEntity.setQuantity(cartItemEntity.getQuantity() + quantity);
                cartItemRepository.save(cartItemEntity);
            }
            return true;
        } catch (ProductNotFoundException ex) {
            logger.warn("Product not found");
            throw ex;
        } catch (DataAccessException ex) {
            logger.error("Database operation failed: " + ex.getMessage());
            throw new RuntimeException("Database operation failed", ex);
        } catch (Exception ex) {
            logger.warn("Put CartItem Failed! " + ex.getMessage());
            throw ex;
        }
    }
    @Override
    public List<GetCartItemResponseData> getUserCartItem(FirebaseUserData firebaseUserData) {
        try {
            UserEntity userEntity = userService.getEntityByFirebaseUserData(firebaseUserData);

            List<GetCartItemResponseData> responseDataList = new ArrayList<>();
            for (CartItemEntity cartItemEntity : getAllUserEntity(userEntity)) {
                responseDataList.add(new GetCartItemResponseData(cartItemEntity));
            }
            return responseDataList;
        } catch (UserNotFoundException ex) {
            logger.warn("User not found: " + ex.getMessage());
            throw ex;
        } catch (DataAccessException ex) {
            logger.error("Database operation failed: " + ex.getMessage());
            throw new RuntimeException("Database operation failed", ex);
        }
    }

    @Override
    public GetCartItemResponseData upDateCartItem(Integer pid, Integer quantity, FirebaseUserData firebaseUserData) {
        try {
            CartItemEntity cartItemEntity = getEntityByPidAndFirebaseUid(pid, firebaseUserData.getFirebaseUid());
            if (!productService.isValidQuantity(cartItemEntity.getProduct(), quantity)) {
                throw new InvalidQuantityException(cartItemEntity.getProduct(), quantity);
            }
            cartItemEntity.setQuantity(quantity);
            return new GetCartItemResponseData(cartItemRepository.save(cartItemEntity));
        } catch (UserNotFoundException ex) {
            logger.warn("User not found: " + ex.getMessage());
            throw ex;
        } catch (DataAccessException ex) {
            logger.error("Database operation failed: " + ex.getMessage());
            throw new RuntimeException("Database operation failed", ex);

        } catch (Exception ex) {
            logger.warn("CartItem update quantity failed: " + ex.getMessage());
            throw ex;
        }
    }

    @Override
    @Transactional
    public boolean deleteCartItemByPid(FirebaseUserData firebaseUserData, Integer pid) {
        try {
            int count = cartItemRepository.deleteByProduct_PidAndUser_FirebaseUid(pid, firebaseUserData.getFirebaseUid());
            if (count <= 0) {
                throw new CartItemRemoveException(pid, firebaseUserData.getFirebaseUid());
            }
            return true;
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            throw ex;
        }
    }

    @Override
    public List<CartItemEntity> getEntityListByUser(UserEntity userEntity) {
        return cartItemRepository.findAllByUser(userEntity);
    }

    public CartItemEntity getEntityByPidAndFirebaseUid(Integer pid, String firebaseUid) {
        return cartItemRepository.findByProductPidAndUserFirebaseUid(pid,firebaseUid).orElseThrow(()-> new CartItemNotFoundException(pid, firebaseUid));
    }

    @Override
    public List<CartItemEntity> getAllUserEntity(UserEntity userEntity) {
        return cartItemRepository.findAllByUser(userEntity);
    }

    @Override
    public void emptyUserCart(String firebaseUid) {
        cartItemRepository.deleteAllByUser_FirebaseUid(firebaseUid);
    }
}
