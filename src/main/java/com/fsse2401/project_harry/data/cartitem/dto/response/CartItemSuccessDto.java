package com.fsse2401.project_harry.data.cartitem.dto.response;

public class CartItemSuccessDto {
    private String result;

    public CartItemSuccessDto() {
        result = "SUCCESS";
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
