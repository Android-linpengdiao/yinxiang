package com.yinxiang.activity;


import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import androidx.databinding.DataBindingUtil;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.baselibrary.UserInfo;
import com.baselibrary.utils.LogUtil;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
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
import com.umeng.analytics.MobclickAgent;
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
    private ImageView userVip;
    private ImageView isVip;
    private TextView fanNumber;
    private TextView followNumber;

    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        addActivity(this);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, null, R.string.app_name, R.string.app_name);
        binding.drawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        intHeaderView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);

        if (getUserId() > 0) {
            doLogin();
            intHeaderData(getUserInfo());
            personInformInfo();
        }else {
            intHeaderData(null);
        }

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                MessageBus.Builder builder = new MessageBus.Builder();
//                MessageBus messageBus = builder
//                        .codeType(MessageBus.msgId_hiddenChanged)
//                        .param1(index)
//                        .build();
//                EventBus.getDefault().post(messageBus);
//            }
//        }, 100);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public void doLogin() {
        LoginInfo info = new LoginInfo(getUserInfo().getData().getYunxin_accid(), getUserInfo().getData().getYunxin_token());
        RequestCallback<LoginInfo> callback = new RequestCallback<LoginInfo>() {

            @Override
            public void onSuccess(LoginInfo param) {
                LogUtil.i(TAG, "onSuccess: " + param.getToken());
                LogUtil.i(TAG, "onSuccess: " + param.getAppKey());
                LogUtil.i(TAG, "onSuccess: " + param.getAccount());
                NimUIKit.setAccount(param.getAccount());
                // 构建缓存
                DataCacheManager.buildDataCacheAsync();
            }

            @Override
            public void onFailed(int code) {
                LogUtil.i(TAG, "onFailed: " + code);
            }

            @Override
            public void onException(Throwable exception) {
                LogUtil.i(TAG, "onException: " + exception.getMessage());
            }
            // 可以在此保存LoginInfo到本地，下次启动APP做自动登录用
        };
        NIMClient.getService(AuthService.class).login(info)
                .setCallback(callback);
    }

    private void intHeaderView() {
        binding.navView.setNavigationItemSelectedListener(this);
        View headerView = binding.navView.getHeaderView(0);
        View userInfoView = headerView.findViewById(R.id.userInfoView);
        userIcon = headerView.findViewById(R.id.user_icon);
        userName = headerView.findViewById(R.id.user_name);
        userTouristId = headerView.findViewById(R.id.user_tourist_id);
        userAddr = headerView.findViewById(R.id.user_addr);
        userLevel = headerView.findViewById(R.id.user_level);
        userVip = headerView.findViewById(R.id.user_vip);
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
                if (getUserId(true) > 0) {
                    openActivity(MyFansActivity.class);
                }
            }
        });
        myFollowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getUserId(true) > 0) {
                    openActivity(MyFollowActivity.class);
                }
            }
        });
        myWorkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getUserId(true) > 0) {
                    Intent intent = new Intent(MainActivity.this, MyWorkActivity.class);
                    intent.putExtra("uid", getUserInfo().getData().getId());
                    startActivity(intent);
                }
            }
        });
        myWorkPKView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getUserId(true) > 0) {
                    openActivity(MyWorkPKActivity.class);
                }
            }
        });
        myWorkRelayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getUserId(true) > 0) {
                    openActivity(MyWorkRelayActivity.class);
                }
            }
        });
        myCompetitionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getUserId(true) > 0) {
                    openActivity(MyCompetitionActivity.class);
                }
            }
        });
        myWalletView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getUserId(true) > 0) {
                    openActivity(MyWalletActivity.class);
                }
            }
        });
        myVIPView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getUserId(true) > 0) {
                    openActivity(MyVIPActivity.class);
                }
            }
        });
        tvEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getUserId(true) > 0) {
                    openActivity(EditorActivity.class);
                }
            }
        });
        tvSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(SettingsActivity.class);
            }
        });
        userInfoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getUserId(true) > 0) {
                    openActivity(EditorActivity.class);
                }
            }
        });

        binding.radioGroupView.setOnCheckedChangeListener(this);
        initDefaultFragment();

        binding.radioButtonRelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getUserId(true) > 0) {
                    openActivity(ReleaseActivity.class);
                }
            }
        });
        binding.radioButtonMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getUserId(true) > 0) {
                    binding.drawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });
    }

    private void intHeaderData(UserInfo userInfo) {
        if (userInfo!=null) {
            userName.setText(userInfo.getData().getName() + "");
            userTouristId.setText("引享号：" + userInfo.getData().getTourist_id());
            userAddr.setText(userInfo.getData().getAddr() + "");
            userLevel.setText("Lv." + userInfo.getData().getCredit());
            userVip.setVisibility(userInfo.getData().getIs_vip() == 1 ? View.VISIBLE : View.GONE);
            fanNumber.setText(String.valueOf(userInfo.getData().getFan_number()));
            isVip.setVisibility(userInfo.getData().getIs_vip() == 1 ? View.VISIBLE : View.GONE);
            followNumber.setText(String.valueOf(userInfo.getData().getFollow_number()));
            GlideLoader.LoderCircleImage(this, userInfo.getData().getAvatar(), userIcon);
        }else {
            userName.setText("");
            userTouristId.setText("引享号：");
            userAddr.setText("");
            userLevel.setText("Lv.");
            userVip.setVisibility(View.GONE);
            fanNumber.setText(String.valueOf(0));
            isVip.setVisibility(View.GONE);
            followNumber.setText(String.valueOf(0));
            GlideLoader.LoderCircleImage(this, "", userIcon);
        }
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
                if (getUserId(true) > 0) {
                    replaceContentFragment(FriendFragment.class);
                }
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
