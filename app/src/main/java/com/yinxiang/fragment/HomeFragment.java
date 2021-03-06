package com.yinxiang.fragment;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baselibrary.utils.GlideLoader;
import com.yinxiang.R;
import com.yinxiang.activity.SearchActivity;
import com.yinxiang.adapter.PagerAdapter;
import com.yinxiang.databinding.FragmentHomeBinding;

public class HomeFragment extends BaseFragment implements View.OnClickListener, ViewPager.OnPageChangeListener, HomeVideoFragment.OnFragmentInteractionListener {

    private static final String TAG = "HomeFragment";
    private FragmentHomeBinding binding;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private HomeVideoFragment homeVideoFragment;
    private HomeContestFragment homeContestFragment;
    private HomeHonorFragment homeHonorFragment;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {

    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        setStatusBarHeight(binding.getRoot(), Color.TRANSPARENT);

        PagerAdapter mainHomePagerAdapter = new PagerAdapter(getChildFragmentManager());
        homeVideoFragment = new HomeVideoFragment();
        mainHomePagerAdapter.addFragment("活动环宇", homeVideoFragment);
        homeContestFragment = new HomeContestFragment();
        mainHomePagerAdapter.addFragment("竞技PK", homeContestFragment);
        homeHonorFragment = new HomeHonorFragment();
        mainHomePagerAdapter.addFragment("荣誉在线", homeHonorFragment);
        binding.viewPager.setAdapter(mainHomePagerAdapter);
        binding.viewPager.setOffscreenPageLimit(2);
        binding.viewPager.setCurrentItem(0);
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        binding.userIcon.setOnClickListener(this);
        binding.ivSearch.setOnClickListener(this);
        binding.viewPager.setOnPageChangeListener(this);
        if (getUserId()>0) {
            GlideLoader.LoderCircleImage(getActivity(), getUserInfo().getData().getAvatar(), binding.userIcon);
        }

        return binding.getRoot();
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_icon:
                if (mListener != null) {
                    mListener.onFragmentInteraction(null);
                }
                break;
            case R.id.iv_search:
                openActivity(SearchActivity.class);
                break;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.i(TAG, "onHiddenChanged: ");
        if (homeVideoFragment != null) {
            homeVideoFragment.onHiddenSurfaceViewChanged(hidden);
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume: ");
        if (homeVideoFragment != null) {
            homeVideoFragment.onHiddenSurfaceViewChanged(false);
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i(TAG, "onPause: ");
        if (homeVideoFragment != null) {
            homeVideoFragment.onHiddenSurfaceViewChanged(true);
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
        if (position != 0) {
            homeVideoFragment.onPause();
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
