package com.mcxtzhang.rxjava2demo.retrofit.model.bf.base;

public class ResultException extends RuntimeException {

    private String errCode = "-1";

    public ResultException(String errCode, String msg) {
        super(msg);
        this.errCode = errCode;
    }

    public String getErrCode() {
        return errCode;
    }
}