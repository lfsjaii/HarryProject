package com.fsse2401.project_harry.api;

import com.fsse2401.project_harry.data.cartitem.domainObject.GetCartItemResponseData;
import com.fsse2401.project_harry.data.cartitem.dto.response.CartItemSuccessDto;
import com.fsse2401.project_harry.data.cartitem.dto.response.GetCartItemResponseDto;
import com.fsse2401.project_harry.service.CartItemService;
import com.fsse2401.project_harry.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartItemApi {
    private final CartItemService cartItemService;

    @Autowired
    public CartItemApi(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PutMapping("/{pid}/{quantity}")
    public CartItemSuccessDto putCartItem(JwtAuthenticationToken jwtToken,
                            @PathVariable Integer pid,
                            @PathVariable Integer quantity) {
        cartItemService.putCartItem(JwtUtil.getFirebaseUserData(jwtToken),pid,quantity);
        return new CartItemSuccessDto();
    }

    @GetMapping
    public List<GetCartItemResponseDto> getUserCartItems(JwtAuthenticationToken jwtToken)
    {
        List<GetCartItemResponseData> cartItemResponseDataList = cartItemService.getUserCartItem(JwtUtil.getFirebaseUserData(jwtToken));

        List<GetCartItemResponseDto> cartItemResponseDtoList = new ArrayList<>();
        for (GetCartItemResponseData cartItemResponseData : cartItemResponseDataList)
        {
            GetCartItemResponseDto cartItemResponseDto = new GetCartItemResponseDto(cartItemResponseData);
            cartItemResponseDtoList.add(cartItemResponseDto);
        }

        return cartItemResponseDtoList;
    }

    @PatchMapping("/{pid}/{quantity}")
    public GetCartItemResponseDto updateCartQuantity(@PathVariable Integer pid, @PathVariable Integer quantity, JwtAuthenticationToken jwtToken){

        return new GetCartItemResponseDto(cartItemService.upDateCartItem(pid, quantity, JwtUtil.getFirebaseUserData(jwtToken)));

    }

    @DeleteMapping("/{pid}")
    public CartItemSuccessDto deleteCartItemByPid(@PathVariable Integer pid, JwtAuthenticationToken jwtToken){

        cartItemService.deleteCartItemByPid(JwtUtil.getFirebaseUserData(jwtToken), pid);
        return new CartItemSuccessDto();
    }
}
