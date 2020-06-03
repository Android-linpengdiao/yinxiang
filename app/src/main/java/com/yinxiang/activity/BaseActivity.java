package com.yinxiang.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;

import com.baselibrary.Constants;
import com.baselibrary.UserInfo;
import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.MsgCache;
import com.baselibrary.utils.PermissionUtils;
import com.baselibrary.utils.StatusBarUtil;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.yinxiang.R;
import com.yinxiang.manager.TencentHelper;
import com.yinxiang.manager.WXManager;
import com.yinxiang.view.OnClickListener;
import com.yinxiang.view.SharePopupWindow;

import okhttp3.Call;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarDarkTheme(true);
    }

    public void setStatusBarDarkTheme(boolean dark) {
        if (!StatusBarUtil.setStatusBarDarkTheme(this, dark)) {
            StatusBarUtil.setStatusBarColor(this, dark ? R.color.black : R.color.white);
        }
    }

    // 5.0版本以上
    @SuppressLint("NewApi")
    public void setStatusBarHeight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            if (findViewById(R.id.status_bar) != null) {
                findViewById(R.id.status_bar).setVisibility(View.VISIBLE);
                int statusBarHeight = CommonUtil.getStatusBarHeight(getApplication());
                findViewById(R.id.status_bar).getLayoutParams().height = statusBarHeight;
            }
        }
    }

    public SharePopupWindow shareView(final Activity activity, final OnClickListener onClickListener) {
        SharePopupWindow sharePopupWindow = new SharePopupWindow(activity);
        sharePopupWindow.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view, Object object) {
                switch (view.getId()) {
                    case R.id.shareWx:
                        // scene 0代表好友   1代表朋友圈
                        WXManager.send(activity, 0);

                        break;
                    case R.id.shareWxMoment:
                        WXManager.send(activity, 1);

                        break;
                    case R.id.shareQQ:
                        TencentHelper.shareToQQ(activity, "https://www.baidu.com/", "title", "desc", null, new IUiListener() {
                            @Override
                            public void onComplete(Object o) {
                            }

                            @Override

                            public void onError(UiError uiError) {

                            }

                            @Override
                            public void onCancel() {

                            }
                        });

                        break;
                    case R.id.shareWeibo:

                        break;
                }
            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });
        sharePopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        return sharePopupWindow;
    }

    public void openActivity(Class<?> mClass) {
        openActivity(mClass, null);
    }

    public void openActivity(Class<?> mClass, Bundle mBundle) {
        Intent intent = new Intent(this, mClass);
        if (mBundle != null) {
            intent.putExtras(mBundle);
        }
        startActivity(intent);
    }

    public void baseInfo() {
        SendRequest.baseInfo(getUserInfo().getData().getId(), new GenericsCallback<UserInfo>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(UserInfo response, int id) {
                if (response.getCode() == 200 && response.getData() != null) {
                    setUserInfo(response);
                }
            }

        });
    }

    public void setUserInfo(UserInfo userInfo) {
        MsgCache.get(this).put(Constants.USER_INFO, userInfo);
    }

    public UserInfo getUserInfo() {
        UserInfo userinfo = (UserInfo) MsgCache.get(this).getAsObject(Constants.USER_INFO);
        if (!CommonUtil.isBlank(userinfo)) {
            return userinfo;
        }
        return new UserInfo();
    }

    public <T extends ViewDataBinding> T getViewData(int layoutId) {
        return DataBindingUtil.setContentView(this, layoutId);
    }

    public boolean checkPermissionsAll(String type, int code) {
        if (Build.VERSION.SDK_INT >= 23) {
            boolean isAllGranted = PermissionUtils.checkPermissionAllGranted(this, type);
            if (!isAllGranted) {
                PermissionUtils.requestPermissions(this, type, code);
                return false;
            }
        }
        return true;
    }
}
