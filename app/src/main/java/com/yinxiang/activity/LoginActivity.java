package com.yinxiang.activity;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import com.baselibrary.UserInfo;
import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.yinxiang.MyApplication;
import com.yinxiang.R;
import com.yinxiang.databinding.ActivityLoginBinding;

import okhttp3.Call;


public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";
    private ActivityLoginBinding loginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        loginBinding.login.setOnClickListener(this);
        loginBinding.register.setOnClickListener(this);
        loginBinding.forgotPassword.setOnClickListener(this);

        loginBinding.ivShowPassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (loginBinding.ivShowPassword.isSelected()) {
                    loginBinding.ivShowPassword.setSelected(false);
                    //从密码可见模式变为密码不可见模式
                    loginBinding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    loginBinding.ivShowPassword.setSelected(true);
                    //从密码不可见模式变为密码可见模式
                    loginBinding.etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                loginBinding.etPassword.setSelection(loginBinding.etPassword.getText().length());
            }

        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        finishAllActivity();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                passwordLogin();
                break;
            case R.id.register:
                openActivity(RegisterActivity.class);
                break;
            case R.id.forgot_password:
                openActivity(ForgotPasswordActivity.class);
                break;
        }
    }

    /**
     * ****************************** 密码登录 **********************************
     */

    private void passwordLogin() {
        String phone = loginBinding.etPhone.getText().toString().trim();
        String password = loginBinding.etPassword.getText().toString().trim();

        if (phone.length() < 11) {
            ToastUtils.showShort(LoginActivity.this, "手机号码不正确");
            return;
        }

        if (password.length() < 6) {
            ToastUtils.showShort(LoginActivity.this, "密码不能小于6位");
            return;
        }
        SendRequest.login(phone, password, new GenericsCallback<UserInfo>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(UserInfo response, int id) {
                if (response.getCode() == 200) {
                    MyApplication.getInstance().setUserInfo(response);
                    openActivity(MainActivity.class);
                    finish();
                } else {
                    ToastUtils.showShort(LoginActivity.this, response.getMsg());
                }
            }

        });
    }
}
