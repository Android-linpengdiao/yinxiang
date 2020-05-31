package com.yinxiang.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.GlideLoader;
import com.yinxiang.R;
import com.yinxiang.adapter.MemberAdapter;
import com.yinxiang.adapter.PagerAdapter;
import com.yinxiang.databinding.ActivityClubDetailBinding;
import com.yinxiang.databinding.ActivityCreateClubBinding;
import com.yinxiang.fragment.ClubDescFragment;
import com.yinxiang.fragment.ClubMemberFragment;
import com.yinxiang.fragment.ClubWorkFragment;
import com.yinxiang.fragment.HomeContestFragment;
import com.yinxiang.fragment.HomeHonorFragment;
import com.yinxiang.fragment.HomeVideoFragment;
import com.yinxiang.view.OnClickListener;

public class ClubDetailActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "ClubDetailActivity";
    private ActivityClubDetailBinding binding;
    private int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_club_detail);

        if (getIntent().getExtras() != null) {
            uid = getIntent().getExtras().getInt("uid");
        }
        Log.i(TAG, "onCreate: " + uid);

        binding.back.setOnClickListener(this);

        GlideLoader.LoderLoadImage(this, CommonUtil.getImageListString().get(2), binding.ivClubLogo, 100);
        GlideLoader.LoderBlurImage(this, CommonUtil.getImageListString().get(2), binding.ivClubCover);


        if (uid % 2 == 0) {
            initCreateView();
        } else {
            initMemberView();
        }

    }

    private void initMemberView() {
        binding.clubMemberView.setVisibility(View.VISIBLE);
        PagerAdapter mainHomePagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mainHomePagerAdapter.addFragment("社团作品", new ClubWorkFragment());
        mainHomePagerAdapter.addFragment("社团简介", new ClubDescFragment());
        mainHomePagerAdapter.addFragment("社团成员", new ClubMemberFragment());
        binding.viewPager.setAdapter(mainHomePagerAdapter);
        binding.viewPager.setOffscreenPageLimit(2);
        binding.viewPager.setCurrentItem(0);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    private void initCreateView() {
        binding.clubCreateView.setVisibility(View.VISIBLE);
        MemberAdapter adapter = new MemberAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {

            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });
        adapter.refreshData(CommonUtil.getImageListString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
