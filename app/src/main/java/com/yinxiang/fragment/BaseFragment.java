package com.yinxiang.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;

import com.baselibrary.Constants;
import com.baselibrary.UserInfo;
import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.MsgCache;
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

import java.io.File;

import okhttp3.Call;

public class BaseFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setStatusBarDarkTheme(boolean dark) {
        if (!StatusBarUtil.setStatusBarDarkTheme(getActivity(), dark)) {
            StatusBarUtil.setStatusBarColor(getActivity(), dark ? R.color.black : R.color.white);
        }
    }

    // 5.0版本以上
    @SuppressLint("NewApi")
    public void setStatusBarHeight(View view, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = getActivity().getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
            if (view.findViewById(R.id.status_bar) != null) {
                view.findViewById(R.id.status_bar).setVisibility(View.VISIBLE);
                view.findViewById(R.id.status_bar).setBackgroundColor(color);
                int statusBarHeight = CommonUtil.getStatusBarHeight(getActivity());
                view.findViewById(R.id.status_bar).getLayoutParams().height = statusBarHeight;
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
        sharePopupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        return sharePopupWindow;
    }

    public void openActivity(Class<?> mClass) {
        openActivity(mClass, null);
    }

    public void openActivity(Class<?> mClass, Bundle mBundle) {
        Intent intent = new Intent(getActivity(), mClass);
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
        MsgCache.get(getActivity()).put(Constants.USER_INFO, userInfo);
    }

    public UserInfo getUserInfo() {
        UserInfo userinfo = (UserInfo) MsgCache.get(getActivity()).getAsObject(Constants.USER_INFO);
        if (!CommonUtil.isBlank(userinfo)) {
            return userinfo;
        }
        return new UserInfo();
    }
}
