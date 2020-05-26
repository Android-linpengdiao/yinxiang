package com.yinxiang;


import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.GlideLoader;
import com.yinxiang.activity.BaseActivity;
import com.yinxiang.databinding.ActivityMainBinding;
import com.yinxiang.fragment.ChannelFragment;
import com.yinxiang.fragment.FriendFragment;
import com.yinxiang.fragment.HomeFragment;
import com.yinxiang.fragment.ReleaseFragment;
import com.yinxiang.utils.ViewUtils;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener,
        NavigationView.OnNavigationItemSelectedListener, HomeFragment.OnFragmentInteractionListener {

    private ActivityMainBinding binding;

    private ActionBarDrawerToggle mDrawerToggle;

    private FragmentManager mFragmentManager;
    public static Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setStatusBarHeight();

        mDrawerToggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, null, R.string.app_name, R.string.app_name);
        binding.drawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        binding.navView.setNavigationItemSelectedListener(this);
        View headerView = binding.navView.getHeaderView(0);
        ImageView userIcon = headerView.findViewById(R.id.user_icon);
        GlideLoader.LoderClipImage(this, CommonUtil.getImageListString().get(0),userIcon);

        binding.radioGroupView.setOnCheckedChangeListener(this);
        initDefaultFragment();

        binding.radioButtonMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawerLayout.openDrawer(Gravity.LEFT);
            }
        });


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
                replaceContentFragment(HomeFragment.class);
                break;
            case R.id.radio_button_channel:
                replaceContentFragment(ChannelFragment.class);
                break;
            case R.id.radio_button_release:
                replaceContentFragment(ReleaseFragment.class);
                break;
            case R.id.radio_button_friend:
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
            mFragmentManager.beginTransaction().hide(mCurrentFragment).show(fragment).commitAllowingStateLoss();
        } else {
            mFragmentManager.beginTransaction().hide(mCurrentFragment).add(R.id.nav_host_fragment, fragment).commitAllowingStateLoss();
        }
        mCurrentFragment = fragment;
        return mCurrentFragment;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        binding.drawerLayout.openDrawer(Gravity.LEFT);
    }
}
