package com.xuanpeng.mvvmsamplechapter1.viewmodel;

import android.databinding.Bindable;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.xuanpeng.mvvmsamplechapter1.BR;
import com.xuanpeng.mvvmsamplechapter1.R;
import com.xuanpeng.mvvmsamplechapter1.model.*;
import com.xuanpeng.mvvmsamplechapter1.model.Error;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by xuanpeng on 15/11/22.
 */
public class LoginViewModel extends BaseViewModel {

    //存储登录成功后的数据
    private User loginUser = null;

    /**
     * 登录请求Task，用于异步登录
     */
    private UserLoginTask mAuthTask = null;

    //登录成功后界面显示的toke
    private String token;

    @Bindable
    public String getToken() {
        return token;
    }

    protected void setToken(String token) {
        if (token.isEmpty()) {
            this.token = token;
        } else {
            //token 不为空的话，添加Hello World文本
            this.token = token+"\nHello World";
        }

        notifyPropertyChanged(BR.token);
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin(String email, String password, LoginAction action) {

        if (mAuthTask != null) {
            return;
        }

        action.before();

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

        if (!cancel) {
            //开始异步登录
            mAuthTask = new UserLoginTask(email, password, action);
            mAuthTask.execute((Void) null);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    private class UserLoginTask extends AsyncTask<Void, Void, User> {

        private final String mEmail;
        private final String mPassword;
        private final LoginAction mAction;
        UserLoginTask(String email, String password, LoginAction action) {
            mEmail = email;
            mPassword = password;
            mAction = action;
        }

        @Override
        protected User doInBackground(Void... params) {
            User user = null;
            try {
                // Simulate network access.
                Thread.sleep(2000);
                //模拟登录成功，返回user对象
                user = new User();
                user.setEmail(mEmail);
                user.setToken(md5(mEmail));
            } catch (InterruptedException e) {
                return null;
            }
            return user;
        }

        @Override
        protected void onPostExecute(final User user) {
            mAuthTask = null;

            Boolean hasError = false;
            Error error = null;


            if (user!=null) {
                LoginViewModel.this.loginUser = user;
                LoginViewModel.this.setToken(user.getToken());
            } else {
                error = new Error(getString(R.string.error_incorrect_password), LoginAction.LoginActionErrorPassword);
                hasError = true;

            }

            mAction.finished(!hasError, error);
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            mAction.onCancelled();
        }
    }

    public interface LoginAction {
        public static int LoginActionErrorEmail = -1001;
        public static int LoginActionErrorPassword = -1002;

        //登录前准备函数
        void before();

        //登录准备后函数
        void after(boolean isSuccess, Error error);

        //登录完成函数
        void finished(boolean isSuccess, Error error);

        //登录取消函数
        void onCancelled();
    }
}
