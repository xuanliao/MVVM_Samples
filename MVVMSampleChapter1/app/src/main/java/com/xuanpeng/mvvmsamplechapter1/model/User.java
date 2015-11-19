package com.xuanpeng.mvvmsamplechapter1.model;

/**
 * Created by xuanpeng on 15/11/19.
 */
public class User {
    private String email;//用户名email
    private String token;//登陆返回的token

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
