package com.yinxiang.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.baselibrary.Constants;
import com.baselibrary.UserInfo;
import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.MsgCache;
import com.baselibrary.utils.PermissionUtils;
import com.baselibrary.utils.StatusBarUtil;
import com.baselibrary.utils.ToastUtils;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.yinxiang.MyApplication;
import com.yinxiang.R;
import com.yinxiang.manager.TencentHelper;
import com.yinxiang.manager.WXManager;
import com.yinxiang.model.VideosVoteSet;
import com.yinxiang.view.OnClickListener;
import com.yinxiang.view.SharePopupWindow;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

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

    public SharePopupWindow shareView(final Activity activity, String url, String title, String desc, final OnClickListener onClickListener) {

        String shareUrl = "http://share.yinxiangcn.cn/?video=" + url + "#/";

        SharePopupWindow sharePopupWindow = new SharePopupWindow(activity);
        sharePopupWindow.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view, Object object) {
                switch (view.getId()) {
                    case R.id.shareWx:
                        // scene 0代表好友   1代表朋友圈
                        WXManager.send(activity, shareUrl, title, desc, 0);

                        break;
                    case R.id.shareWxMoment:
                        WXManager.send(activity, shareUrl, title, desc, 1);

                        break;
                    case R.id.shareQQ:
                        TencentHelper.shareToQQ(activity, shareUrl, title, desc, null, new IUiListener() {
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
                        download(url);
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

    public void download(String url) {
        if (!CommonUtil.isBlank(url)) {
            File outputFile = new File(Environment.getExternalStorageDirectory() + File.separator + "download" + File.separator + url.substring(url.lastIndexOf("/") + 1));
            BaseDownloadTask task = FileDownloader.getImpl().create(url)
                    .setPath(outputFile.getPath())
                    .setCallbackProgressTimes(1000)
                    .setListener(new FileDownloadListener() {
                        @Override
                        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                            ToastUtils.showShort(MyApplication.getInstance(),"开始下载");
                        }

                        @Override
                        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                        }

                        @Override
                        protected void completed(BaseDownloadTask task) {
                            MyApplication.getInstance().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + task.getPath())));
                            ToastUtils.showShort(MyApplication.getInstance(),"下载完成");
                        }

                        @Override
                        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        }

                        @Override
                        protected void error(BaseDownloadTask task, Throwable e) {
                        }

                        @Override
                        protected void warn(BaseDownloadTask task) {
                            ToastUtils.showShort(MyApplication.getInstance(),"正在下载");
                        }
                    });
            task.start();

        }
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

    public void personInformInfo() {
        SendRequest.personInformInfo(getUserInfo().getData().getId(), new GenericsCallback<UserInfo>(new JsonGenericsSerializator()) {
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

    public void completeRead(int type) {
        SendRequest.completeRead(getUserInfo().getData().getId(), type, new GenericsCallback<UserInfo>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(UserInfo response, int id) {

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

    public int getUserId() {
        return getUserId(false);
    }

    public int getUserId(boolean login) {
        if (getUserInfo().getData() != null) {
            return getUserInfo().getData().getId();
        } else {
            if (login) {
                openActivity(LoginActivity.class);
            }
            return 0;
        }
    }

    public VideosVoteSet getVideosVoteSet() {
        VideosVoteSet videosVoteSet = (VideosVoteSet) MsgCache.get(this).getAsObject(Constants.VOTE_SET);
        if (!CommonUtil.isBlank(videosVoteSet)) {
            return videosVoteSet;
        }
        return new VideosVoteSet();
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

    private static List<Activity> activityStack = new ArrayList<Activity>();

    public void addActivity(Activity aty) {
        activityStack.add(aty);
    }

    public void removeActivity(Activity aty) {
        activityStack.remove(aty);
    }

    public static void finishActivity(Class aty) {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i) && null != aty
                    && aty.getSimpleName().equals(activityStack.get(i).getClass().getSimpleName())) {
                activityStack.get(i).finish();
            }
        }
    }

    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }
}
