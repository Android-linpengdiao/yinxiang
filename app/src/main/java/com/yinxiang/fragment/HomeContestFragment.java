package com.yinxiang.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.ToastUtils;
import com.dingmouren.layoutmanagergroup.viewpager.OnViewPagerListener;
import com.dingmouren.layoutmanagergroup.viewpager.ViewPagerLayoutManager;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.callbacks.StringCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.yinxiang.R;
import com.yinxiang.activity.WorkDetailActivity;
import com.yinxiang.adapter.HomeContestAdapter;
import com.yinxiang.adapter.HomeVideoAdapter;
import com.yinxiang.databinding.FragmentHomeContestBinding;
import com.yinxiang.databinding.FragmentHomeVideoBinding;
import com.yinxiang.model.HomeActives;
import com.yinxiang.model.HomeVideos;
import com.yinxiang.model.WorkPKData;
import com.yinxiang.view.OnClickListener;
import com.yinxiang.view.TypePopupWindow;

import java.util.ArrayList;

import okhttp3.Call;

public class HomeContestFragment extends BaseFragment implements View.OnClickListener {

    private FragmentHomeContestBinding binding;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private HomeContestAdapter adapter;
    private HomeActives homeActives;
    private HomeActives.DataBean dataBean;

    private OnFragmentInteractionListener mListener;

    public HomeContestFragment() {

    }


    public static HomeContestFragment newInstance(String param1, String param2) {
        HomeContestFragment fragment = new HomeContestFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_contest, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) binding.viewLayout.getLayoutParams();
            layoutParams.topMargin = CommonUtil.getStatusBarHeight(getActivity()) + CommonUtil.dip2px(getActivity(), 60);
        }

        binding.type.setOnClickListener(this);

        adapter = new HomeContestAdapter(getActivity());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                openActivity(WorkDetailActivity.class);
            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });

        SendRequest.homePageActives(1, new GenericsCallback<HomeActives>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(HomeActives response, int id) {
                homeActives = response;
                if (response.getCode() == 200 && response.getData() != null && response.getData().size() > 0) {
                    response.getData().get(0).setSelected(1);
                    homePageVideosPK(response.getData().get(0));
                }
            }

        });

        binding.swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                homePageVideosPK(dataBean);
            }
        });

        return binding.getRoot();
    }

    private void homePageVideosPK(HomeActives.DataBean dataBean) {
        this.dataBean = dataBean;
        binding.swipeRefreshLayout.setRefreshing(true);
        SendRequest.homePageVideosPK(dataBean.getId(), 10, new GenericsCallback<WorkPKData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(WorkPKData response, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
                if (response != null && response.getCode() == 200) {
                    if (response.getData() != null && response.getData().getData() != null && response.getData().getData().size() > 0) {
                        binding.recyclerView.setVisibility(View.VISIBLE);
                        adapter.refreshData(response.getData().getData());
                    } else {
                        adapter.refreshData(new ArrayList<WorkPKData.DataBeanX.DataBean>());
                        binding.recyclerView.setVisibility(View.GONE);
                    }
                } else {
                    ToastUtils.showShort(getActivity(), response.getMsg());
                }
            }

        });
    }

    private void homePageVideosVote(int video_id, int free) {
        SendRequest.homePageVideosVote(getUserInfo().getData().getId(), video_id, free, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {

            }
        });
    }

    private void typeView(HomeActives response) {
        TypePopupWindow typePopupWindow = new TypePopupWindow(getActivity());
        typePopupWindow.setHomeActives(response);
        typePopupWindow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                if (object instanceof HomeActives.DataBean) {
                    homePageVideosPK((HomeActives.DataBean) object);
                }

            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });
        typePopupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.type:
                typeView(homeActives);
                break;
        }
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
