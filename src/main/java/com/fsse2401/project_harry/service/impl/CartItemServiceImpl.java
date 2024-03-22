package com.fsse2401.project_harry.service.impl;

import com.fsse2401.project_harry.data.cartitem.domainObject.GetCartItemResponseData;
import com.fsse2401.project_harry.data.cartitem.entity.CartItemEntity;
import com.fsse2401.project_harry.data.product.entity.ProductEntity;
import com.fsse2401.project_harry.data.user.domainObject.FirebaseUserData;
import com.fsse2401.project_harry.data.user.entity.UserEntity;
import com.fsse2401.project_harry.exception.cartItem.CartItemNotFoundException;
import com.fsse2401.project_harry.exception.cartItem.PutCartItemException;
import com.fsse2401.project_harry.exception.product.ProductNotFoundException;
import com.fsse2401.project_harry.repository.CartItemRepository;
import com.fsse2401.project_harry.service.CartItemService;
import com.fsse2401.project_harry.service.ProductService;
import com.fsse2401.project_harry.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Override
    public boolean putCartItem(FirebaseUserData firebaseUserData,
                            Integer pid, Integer quantity) {
        try {
            UserEntity userEntity = userService.getEntityByFirebaseUserData(firebaseUserData);
            ProductEntity productEntity = productService.getEntityByPid(pid);
            if (existsByProductAndUser(productEntity, userEntity)) {
                CartItemEntity cartItemEntity = getByProductAndUser(productEntity,userEntity);
                if(!productService.isValidQuantity(productEntity, cartItemEntity.getQuantity())) {
                    throw new PutCartItemException();
                }
                cartItemEntity.setQuantity(cartItemEntity.getQuantity() + quantity);
                cartItemRepository.save(cartItemEntity);
            } else {
                if(!productService.isValidQuantity(productEntity, quantity)) {
                    throw new PutCartItemException();
                }
                CartItemEntity cartItemEntity = new CartItemEntity(productEntity, userEntity, quantity);
                cartItemRepository.save(cartItemEntity);
            }
            return true;
        } catch (ProductNotFoundException | PutCartItemException ex) {
            logger.warn("Put CartItem Failed!" + ex.getMessage());
            throw ex;
        }
    }
    @Override
    public List<GetCartItemResponseData> getUserCartItem(FirebaseUserData firebaseUserData) {
        UserEntity userEntity = userService.getEntityByFirebaseUserData(firebaseUserData);

        List<CartItemEntity> cartItemEntityList = cartItemRepository.findAllByUser(userEntity);
        List<GetCartItemResponseData> cartItemResponseDataList = new ArrayList<>();

        for (CartItemEntity cartItemEntity : cartItemEntityList)
        {
            GetCartItemResponseData cartItemResponseData = new GetCartItemResponseData(cartItemEntity);
            cartItemResponseDataList.add(cartItemResponseData);
        }
        return cartItemResponseDataList;
    }

    @Override
    public GetCartItemResponseData upDateCartItem(Integer pid, Integer quantity, FirebaseUserData firebaseUserData){
        UserEntity userEntity = userService.getEntityByFirebaseUserData(firebaseUserData);
        ProductEntity productEntity = productService.getEntityByPid(pid);

        Optional<CartItemEntity> optionalCartItemEntity = cartItemRepository.findByProductAndUser(productEntity, userEntity);
        CartItemEntity cartItemEntity;
        if (optionalCartItemEntity.isEmpty()){
            cartItemEntity = new CartItemEntity(productEntity, userEntity, quantity);
        } else {
            cartItemEntity = optionalCartItemEntity.get();
            cartItemEntity.setQuantity(quantity);
        }
        return new GetCartItemResponseData(cartItemRepository.save(cartItemEntity));

    }

    @Override
    public boolean deleteCartItemByPid(FirebaseUserData firebaseUserData, Integer pid){
        UserEntity userEntity = userService.getEntityByFirebaseUserData(firebaseUserData);
        ProductEntity productEntity = productService.getEntityByPid(pid);

        Optional<CartItemEntity> optionalCartItemEntity = cartItemRepository.findByProductAndUser(productEntity, userEntity);
        if (optionalCartItemEntity.isEmpty()){
            return false;
        } else {
            CartItemEntity cartItemEntity = optionalCartItemEntity.get();
            cartItemRepository.delete(cartItemEntity);
        }
        return true;
    }
    @Override
    public List<CartItemEntity> getAllUserEntity(UserEntity userEntity) {
        return cartItemRepository.findAllByUser(userEntity);
    }




    public boolean existsByProductAndUser(ProductEntity product,UserEntity user) {
        return cartItemRepository.existsByProductAndUser(product,user);
    }


    public CartItemEntity getByProductAndUser(ProductEntity product, UserEntity user) {
        return cartItemRepository.findByProductAndUser(product,user).orElseThrow(CartItemNotFoundException::new);
    }
@Override
    public void deleteAllCartItemsforUser(UserEntity userEntity)
    {
        cartItemRepository.deleteAll(cartItemRepository.findAllByUser(userEntity));
    }
}
