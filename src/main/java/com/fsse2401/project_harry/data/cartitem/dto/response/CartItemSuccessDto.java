package com.fsse2401.project_harry.data.cartitem.dto.response;

public class CartItemSuccessDto {
    private String result;

    public CartItemSuccessDto() {
        setResult("SUCCESS");
    }

    public String getResult() {
        return result;
    }

    private void setResult(String result) {
        this.result = result;
    }
}
