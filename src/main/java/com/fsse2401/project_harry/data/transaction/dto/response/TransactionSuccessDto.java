package com.fsse2401.project_harry.data.transaction.dto.response;

public class TransactionSuccessDto {
    private String result;

    public TransactionSuccessDto() {
        setResult("SUCCESS");
    }

    public String getResult() {
        return result;
    }

    private void setResult(String result) {
        this.result = result;
    }
}
