package com.xuanpeng.mvvmsamplechapter1;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xuanpeng.mvvmsamplechapter1.model.*;
import com.xuanpeng.mvvmsamplechapter1.model.Error;
import com.xuanpeng.mvvmsamplechapter1.viewmodel.LoginViewModel;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    // 显示UI组件
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Button mEmailSignInButton;
    private TextView mTokenTextView;

    //两个状态值，用于监听有没有文本输入
    private  boolean mhasEmailText = false;
    private  boolean mhasPasswordText = false;

    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //初始化ViewModel
        viewModel = new LoginViewModel();

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mEmailView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mhasEmailText = !TextUtils.isEmpty(s);
                updateSignButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mhasPasswordText = !TextUtils.isEmpty(s);
                updateSignButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        mEmailSignInButton.setVisibility(View.GONE);
        mTokenTextView = (TextView) findViewById(R.id.token_show_text_view);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

    }


    private void attemptLogin() {
        viewModel.attemptLogin(mEmailView.getText().toString(), mPasswordView.getText().toString(),
                new LoginViewModel.LoginAction() {
                    @Override
                    public void before() {
                        // Reset errors.
                        mEmailView.setError(null);
                        mPasswordView.setError(null);
                    }

                    @Override
                    public void after(boolean isSuccess, com.xuanpeng.mvvmsamplechapter1.model.Error error) {
                        if (isSuccess) {
                            // Show a progress spinner, and kick off a background task to
                            // perform the user login attempt.
                            showProgress(true);
                        } else  {
                            // There was an error; don't attempt login and focus the first
                            // form field with an error.
                            if (error.getCode() == LoginActionErrorEmail) {
                                mEmailView.setError(error.getMessage());
                                mEmailView.requestFocus();
                            } else  {
                                mPasswordView.setError(error.getMessage());
                                mPasswordView.requestFocus();
                            }
                        }
                    }

                    @Override
                    public void finished(boolean isSuccess, Error error) {
                        showProgress(false);
                        if (isSuccess) {
                            mTokenTextView.setText(LoginActivity.this.viewModel.getToken());
                        } else {
                            mPasswordView.setError(error.getMessage());
                            mPasswordView.requestFocus();
                        }
                    }

                    @Override
                    public void onCancelled() {
                        showProgress(false);
                    }
                });

    }


    /**
     * 根据两个状态值来控制登录按钮的显示
     */
    private void updateSignButton() {
        mEmailSignInButton.setVisibility(mhasEmailText&&mhasPasswordText ? View.VISIBLE : View.GONE);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}

