package com.xuanpeng.mvvmsamplechapter1.model;

/**
 * Created by xuanpeng on 15/11/11.
 */
public class Error extends java.lang.Error {
    private int code;

    public Error(String detailMessage, int code) {
        super(detailMessage);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
