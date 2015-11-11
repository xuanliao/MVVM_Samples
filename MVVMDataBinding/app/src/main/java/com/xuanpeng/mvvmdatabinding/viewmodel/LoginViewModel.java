package com.xuanpeng.mvvmdatabinding.viewmodel;

import android.databinding.Bindable;
import android.text.TextUtils;

import com.xuanpeng.mvvmdatabinding.BR;
import com.xuanpeng.mvvmdatabinding.R;
import com.xuanpeng.mvvmdatabinding.model.Error;

/**
 * Created by xuanpeng on 15/11/9.
 */
public class LoginViewModel extends BaseViewModel {
    private String email;
    private String password;

    public LoginViewModel() {
    }

    public LoginViewModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin(LoginAction action) {

        action.before();

        // Store values at the time of the login attempt.
        String email = this.getEmail();
        String password = this.getPassword();

        boolean cancel = false;
        Error error = null;//存储错误信息

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            error = new Error(getString(R.string.error_invalid_password), LoginAction.LoginActionErrorPassword);
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            error = new Error(getString(R.string.error_field_required), LoginAction.LoginActionErrorEmail);
            cancel = true;
        } else if (!isEmailValid(email)) {
            error = new Error(getString(R.string.error_invalid_email), LoginAction.LoginActionErrorEmail);
            cancel = true;
        }

        action.after(!cancel, error);
    }


    public interface LoginAction {
        public static int LoginActionErrorEmail = -1001;
        public static int LoginActionErrorPassword = -1002;

        void before();

        void after(boolean isSuccess, Error error);
    }

}
