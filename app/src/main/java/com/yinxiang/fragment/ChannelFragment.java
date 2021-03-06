package com.yinxiang.fragment;

import androidx.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yinxiang.R;
import com.yinxiang.adapter.PagerAdapter;
import com.yinxiang.databinding.FragmentChannelBinding;

public class ChannelFragment extends BaseFragment implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private static final String TAG = "ChannelFragment";
    private FragmentChannelBinding binding;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ChannelVideoFragment channelVideoFragment;
    private ChannelClubFragment channelClubFragment;

    public static ChannelFragment newInstance(String param1, String param2) {
        ChannelFragment fragment = new ChannelFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_channel, container, false);
        setStatusBarHeight(binding.getRoot(), Color.TRANSPARENT);

        PagerAdapter mainHomePagerAdapter = new PagerAdapter(getChildFragmentManager());
        channelVideoFragment = new ChannelVideoFragment();
        mainHomePagerAdapter.addFragment("热门优选", channelVideoFragment);
        channelClubFragment = new ChannelClubFragment();
        mainHomePagerAdapter.addFragment("社团学院", channelClubFragment);
        binding.viewPager.setAdapter(mainHomePagerAdapter);
        binding.viewPager.setOffscreenPageLimit(2);
        binding.viewPager.setCurrentItem(0);
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        binding.tvVideoType.setOnClickListener(this);
        binding.viewPager.setOnPageChangeListener(this);

        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_video_type:

                break;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.i(TAG, "onHiddenChanged: ");
        if (channelVideoFragment!=null) {
            channelVideoFragment.onHiddenSurfaceViewChanged(hidden);
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume: ");
        if (channelVideoFragment!=null) {
            channelVideoFragment.onHiddenSurfaceViewChanged(false);
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i(TAG, "onPause: ");
        if (channelVideoFragment!=null) {
            channelVideoFragment.onHiddenSurfaceViewChanged(true);
        }
        super.onPause();
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        setStatusBarHeight(binding.getRoot(), position == 0 ? Color.TRANSPARENT : getResources().getColor(R.color.colorPrimary));
        binding.topView.setBackgroundColor(position == 0 ? getResources().getColor(R.color.transparent) : getResources().getColor(R.color.colorPrimary));
        binding.tvVideoType.setVisibility(position != 0 ? View.INVISIBLE : View.VISIBLE);
        if (position != 0) {
            channelVideoFragment.onPause();
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
