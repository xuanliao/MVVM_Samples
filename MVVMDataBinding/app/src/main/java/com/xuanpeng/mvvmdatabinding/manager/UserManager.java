package com.xuanpeng.mvvmdatabinding.manager;

import com.xuanpeng.mvvmdatabinding.model.User;

/**
 * Created by xuanpeng on 15/11/10.
 */
public class UserManager {
    private static UserManager ourInstance = new UserManager();

    public static UserManager getInstance() {
        return ourInstance;
    }

    private User user = null;

    private UserManager() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
