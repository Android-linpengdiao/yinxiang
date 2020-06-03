package com.yinxiang.activity;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.baselibrary.MessageBus;
import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.GlideLoader;
import com.baselibrary.utils.ToastUtils;
import com.yinxiang.R;
import com.yinxiang.databinding.ActivityMainBinding;
import com.yinxiang.fragment.ChannelFragment;
import com.yinxiang.fragment.FriendFragment;
import com.yinxiang.fragment.HomeFragment;
import com.yinxiang.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener,
        NavigationView.OnNavigationItemSelectedListener, HomeFragment.OnFragmentInteractionListener {

    private ActivityMainBinding binding;

    private ActionBarDrawerToggle mDrawerToggle;

    private FragmentManager mFragmentManager;
    public static Fragment mCurrentFragment;

    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mDrawerToggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, null, R.string.app_name, R.string.app_name);
        binding.drawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        binding.navView.setNavigationItemSelectedListener(this);
        View headerView = binding.navView.getHeaderView(0);
        ImageView userIcon = headerView.findViewById(R.id.user_icon);
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
        GlideLoader.LoderCircleImage(this, CommonUtil.getImageListString().get(0), userIcon);

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
                intent.putExtra("id", getUserInfo().getData().getId());
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
                index = 2;
                openActivity(ReleaseActivity.class);
            }
        });
        binding.radioButtonMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 4;
                binding.drawerLayout.openDrawer(Gravity.LEFT);
            }
        });


    }

    private static final String TAG = "MainActivity";
    
    @Override
    protected void onResume() {
        super.onResume();
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
        },500);
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
