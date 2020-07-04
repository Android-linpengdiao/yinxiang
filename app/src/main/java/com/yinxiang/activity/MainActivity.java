package com.yinxiang.activity;


import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import androidx.databinding.DataBindingUtil;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baselibrary.MessageBus;
import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.GlideLoader;
import com.baselibrary.utils.ToastUtils;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.impl.cache.DataCacheManager;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.okhttp.callbacks.StringCallback;
import com.okhttp.utils.OkHttpUtils;
import com.yinxiang.R;
import com.yinxiang.databinding.ActivityMainBinding;
import com.yinxiang.fragment.ChannelFragment;
import com.yinxiang.fragment.FriendFragment;
import com.yinxiang.fragment.HomeFragment;
import com.yinxiang.utils.MD5;
import com.yinxiang.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener,
        NavigationView.OnNavigationItemSelectedListener, HomeFragment.OnFragmentInteractionListener {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;

    private ActionBarDrawerToggle mDrawerToggle;

    private FragmentManager mFragmentManager;
    public static Fragment mCurrentFragment;

    private ImageView userIcon;
    private TextView userName;
    private TextView userTouristId;
    private TextView userAddr;
    private TextView userLevel;
    private ImageView isVip;
    private TextView fanNumber;
    private TextView followNumber;

    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        addActivity(this);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, null, R.string.app_name, R.string.app_name);
        binding.drawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        intHeaderView();

        initNim();

    }

    private void initNim() {
        register(getUserInfo().getData().getPhone(), getUserInfo().getData().getPhone(), "123456");
        doLogin();

    }

    // api
    private static final String API_NAME_REGISTER = "createDemoUser";

    // header
    private static final String HEADER_KEY_APP_KEY = "appkey";
    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String HEADER_USER_AGENT = "User-Agent";

    // request
    private static final String REQUEST_USER_NAME = "username";
    private static final String REQUEST_NICK_NAME = "nickname";
    private static final String REQUEST_PASSWORD = "password";

    /**
     * 向应用服务器创建账号（注册账号）
     * 由应用服务器调用WEB SDK接口将新注册的用户数据同步到云信服务器
     */
    public void register(String account, String nickName, String password) {
        String url = "https://app.netease.im/api/createDemoUser";
//        password = MD5.getStringMD5(password);
        try {
            nickName = URLEncoder.encode(nickName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Map<String, String> headers = new HashMap<>(1);
        String appKey = readAppKey();
        headers.put(HEADER_CONTENT_TYPE, "application/x-www-form-urlencoded; charset=utf-8");
        headers.put(HEADER_USER_AGENT, "nim_demo_android");
        headers.put(HEADER_KEY_APP_KEY, appKey);

        StringBuilder body = new StringBuilder();
        body.append(REQUEST_USER_NAME).append("=").append(account.toLowerCase()).append("&")
                .append(REQUEST_NICK_NAME).append("=").append(nickName).append("&")
                .append(REQUEST_PASSWORD).append("=").append(password);
        String bodyString = body.toString();
        Log.i(TAG, "register: appKey = " + appKey);
        Log.i(TAG, "register: bodyString = " + bodyString);
        Map<String, String> map = new HashMap<>();
        map.put(REQUEST_USER_NAME, account.toLowerCase());
        map.put(REQUEST_NICK_NAME, nickName);
        map.put(REQUEST_PASSWORD, password);

        OkHttpUtils.getInstance()
                .post()
                .headers(headers)
                .params(map)
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                    }
                });

//        NimHttpClient.getInstance().execute(url, headers, bodyString, new NimHttpClient.NimHttpCallback() {
//            @Override
//            public void onResponse(String response, int code, Throwable exception) {
//                if (code != 200 || exception != null) {
//                    String errMsg = exception != null ? exception.getMessage() : "null";
//                    LogUtil.e(TAG, "register failed : code = " + code + ", errorMsg = " + errMsg);
//                    if (callback != null) {
//                        callback.onFailed(code, errMsg);
//                    }
//                    return;
//                }
//
//                try {
//                    JSONObject resObj = JSONObject.parseObject(response);
//                    int resCode = resObj.getIntValue(RESULT_KEY_RES);
//                    if (resCode == RESULT_CODE_SUCCESS) {
//                        callback.onSuccess(null);
//                    } else {
//                        String error = resObj.getString(RESULT_KEY_ERROR_MSG);
//                        callback.onFailed(resCode, error);
//                    }
//                } catch (JSONException e) {
//                    callback.onFailed(-1, e.getMessage());
//                }
//            }
//        });
    }

    private String readAppKey() {
        try {
            ApplicationInfo appInfo = getPackageManager().
                    getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo != null) {
                return appInfo.metaData.getString("com.netease.nim.appKey");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void doLogin() {
        Log.i(TAG, "doLogin: ");
        LoginInfo info = new LoginInfo(getUserInfo().getData().getPhone(), "123456");
        RequestCallback<LoginInfo> callback = new RequestCallback<LoginInfo>() {

            @Override
            public void onSuccess(LoginInfo param) {
                Log.i(TAG, "onSuccess: " + param.getToken());
                Log.i(TAG, "onSuccess: " + param.getAppKey());
                Log.i(TAG, "onSuccess: " + param.getAccount());
                NimUIKit.setAccount(param.getAccount());
                // 构建缓存
                DataCacheManager.buildDataCacheAsync();
            }

            @Override
            public void onFailed(int code) {
                Log.i(TAG, "onFailed: " + code);
            }

            @Override
            public void onException(Throwable exception) {
                Log.i(TAG, "onException: " + exception.getMessage());
            }
            // 可以在此保存LoginInfo到本地，下次启动APP做自动登录用
        };
        NIMClient.getService(AuthService.class).login(info)
                .setCallback(callback);
    }

    private void intHeaderView() {
        binding.navView.setNavigationItemSelectedListener(this);
        View headerView = binding.navView.getHeaderView(0);
        userIcon = headerView.findViewById(R.id.user_icon);
        userName = headerView.findViewById(R.id.user_name);
        userTouristId = headerView.findViewById(R.id.user_tourist_id);
        userAddr = headerView.findViewById(R.id.user_addr);
        userLevel = headerView.findViewById(R.id.user_level);
        isVip = headerView.findViewById(R.id.is_vip);
        fanNumber = headerView.findViewById(R.id.fan_number);
        followNumber = headerView.findViewById(R.id.follow_number);
        View myFansView = headerView.findViewById(R.id.my_fans_view);
        View myFollowView = headerView.findViewById(R.id.my_follow_view);
        View myWorkView = headerView.findViewById(R.id.my_work_view);
        View myWorkPKView = headerView.findViewById(R.id.my_work_pk_view);
        View myWorkRelayView = headerView.findViewById(R.id.my_work_relay_view);
        View myCompetitionView = headerView.findViewById(R.id.my_competition_view);
        View myWalletView = headerView.findViewById(R.id.my_wallet_view);
        View myVIPView = headerView.findViewById(R.id.my_vip_view);
        View tvEditor = headerView.findViewById(R.id.tv_editor);
        View tvSetting = headerView.findViewById(R.id.tv_setting);
        View bottomView = headerView.findViewById(R.id.bottomView);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) bottomView.getLayoutParams();
        layoutParams.height = CommonUtil.getScreenHeight(getApplication());
        bottomView.setLayoutParams(layoutParams);

        myFansView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(MyFansActivity.class);
            }
        });
        myFollowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(MyFollowActivity.class);
            }
        });
        myWorkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyWorkActivity.class);
                intent.putExtra("uid", getUserInfo().getData().getId());
                startActivity(intent);
            }
        });
        myWorkPKView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(MyWorkPKActivity.class);
            }
        });
        myWorkRelayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(MyWorkRelayActivity.class);
            }
        });
        myCompetitionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(MyCompetitionActivity.class);
            }
        });
        myWalletView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(MyWalletActivity.class);
            }
        });
        myVIPView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(MyVIPActivity.class);
            }
        });
        tvEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(EditorActivity.class);
            }
        });
        tvSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(SettingsActivity.class);
            }
        });

        binding.radioGroupView.setOnCheckedChangeListener(this);
        initDefaultFragment();

        binding.radioButtonRelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(ReleaseActivity.class);
            }
        });
        binding.radioButtonMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }

    private void intHeaderData() {
        userName.setText(getUserInfo().getData().getName() + "");
        userTouristId.setText("引享号：" + getUserInfo().getData().getTourist_id());
        userAddr.setText(getUserInfo().getData().getAddr() + "");
        userLevel.setText("Lv." + getUserInfo().getData().getLevel());
        fanNumber.setText(String.valueOf(getUserInfo().getData().getFan_number()));
        isVip.setVisibility(getUserInfo().getData().getIs_vip() == 1 ? View.VISIBLE : View.GONE);
        followNumber.setText(String.valueOf(getUserInfo().getData().getFollow_number()));
        GlideLoader.LoderCircleImage(this, getUserInfo().getData().getAvatar(), userIcon);
    }

    @Override
    protected void onResume() {
        super.onResume();
        intHeaderData();
        Log.i(TAG, "onResume: ");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageBus.Builder builder = new MessageBus.Builder();
                MessageBus messageBus = builder
                        .codeType(MessageBus.msgId_hiddenChanged)
                        .param1(index)
                        .build();
                EventBus.getDefault().post(messageBus);
            }
        }, 100);
        personInformInfo();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                Toast.makeText(MainActivity.this, "nav_home", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_button_home:
                index = 0;
                replaceContentFragment(HomeFragment.class);
                break;
            case R.id.radio_button_channel:
                index = 1;
                replaceContentFragment(ChannelFragment.class);
                break;
            case R.id.radio_button_friend:
                index = 3;
                replaceContentFragment(FriendFragment.class);
                break;
            default:
                break;
        }
    }

    private void initDefaultFragment() {
        mFragmentManager = getSupportFragmentManager();
        mCurrentFragment = ViewUtils.createFragment(HomeFragment.class, true);
        mFragmentManager.beginTransaction().add(R.id.nav_host_fragment, mCurrentFragment).commit();
    }

    public Fragment replaceContentFragment(Class<?> mclass) {
        Fragment fragment = ViewUtils.createFragment(mclass, true);
        if (fragment.isAdded()) {
            mFragmentManager.beginTransaction().hide(mCurrentFragment).show(fragment).commit();
        } else {
            mFragmentManager.beginTransaction().hide(mCurrentFragment).add(R.id.nav_host_fragment, fragment).commit();
        }
        mCurrentFragment = fragment;
        return mCurrentFragment;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        binding.drawerLayout.openDrawer(Gravity.LEFT);
    }

    private long lastTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - lastTime > 2000) {
                lastTime = System.currentTimeMillis();
                ToastUtils.showShort(MainActivity.this, "再按一次退出应用");
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
