package com.yinxiang.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;

import androidx.databinding.DataBindingUtil;

import android.content.SharedPreferences;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.baselibrary.manager.DialogManager;
import com.baselibrary.utils.CommonUtil;
import com.baselibrary.view.OnClickListener;
import com.yinxiang.R;
import com.yinxiang.databinding.ActivityWelcomeBinding;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class WelcomeActivity extends BaseActivity {
    private ActivityWelcomeBinding welcomeBinding;

    private String[] permissions = {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    private final int requestCode = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        welcomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);
        setStatusBarHeight();
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }

        appService();
    }

    private void appService() {
        SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);
        if (!sp.getBoolean("appService", false)) {
            DialogManager.showServiceDialog(WelcomeActivity.this, new OnClickListener() {
                @Override
                public void onClick(View view, Object object) {
                    switch (view.getId()) {
                        case R.id.tv_confirm:
                            sp.edit().putBoolean("appService", true).apply();
                            permissionsManager();
                            break;
                        case R.id.tv_cancel:
                            finish();
                            break;
                    }
                }

                @Override
                public void onLongClick(View view, Object object) {

                }
            });
        }
    }


    @AfterPermissionGranted(requestCode)
    private void permissionsManager() {
        if (EasyPermissions.hasPermissions(this, permissions)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
//                    if (CommonUtil.isBlank(getUserInfo().getData())) {
//                        intent = new Intent(WelcomeActivity.this, LoginActivity.class);
//                    } else {
//                        intent = new Intent(WelcomeActivity.this, MainActivity.class);
//                    }
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
            }, 1000);
        } else {
            EasyPermissions.requestPermissions(this, "请同意下面的权限", requestCode, permissions);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

}
