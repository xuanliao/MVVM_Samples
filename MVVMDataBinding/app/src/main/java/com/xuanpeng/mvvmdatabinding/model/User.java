package com.xuanpeng.mvvmdatabinding.model;

/**
 * Created by xuanpeng on 15/11/9.
 */
public class User {
    private Integer  id;
    private String name;
    private String email;
    private String token;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
