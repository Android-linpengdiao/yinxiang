package com.yinxiang.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.yinxiang.R;
import com.yinxiang.activity.MyWorkActivity;
import com.yinxiang.activity.WorkDetailActivity;
import com.yinxiang.adapter.HomeContestAdapter;
import com.yinxiang.adapter.WorkAdapter;
import com.yinxiang.databinding.FragmentClubWorkBinding;
import com.yinxiang.databinding.FragmentHomeContestBinding;
import com.yinxiang.model.WorkData;
import com.yinxiang.view.OnClickListener;

import okhttp3.Call;

public class ClubWorkFragment extends BaseFragment {

    private FragmentClubWorkBinding binding;

    private static final String ARG_CID = "cid";

    private int cid;

    private OnFragmentInteractionListener mListener;

    public ClubWorkFragment() {

    }


    public static ClubWorkFragment newInstance(int cid) {
        ClubWorkFragment fragment = new ClubWorkFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CID, cid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cid = getArguments().getInt(ARG_CID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_club_work, container, false);

        WorkAdapter workAdapter = new WorkAdapter(getActivity());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setNestedScrollingEnabled(false);
        binding.recyclerView.setAdapter(workAdapter);
        workAdapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                openActivity(WorkDetailActivity.class);
            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });

        binding.swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                channelClubContent();
            }
        });
        binding.swipeRefreshLayout.setRefreshing(true);
        channelClubContent();

        return binding.getRoot();
    }

    private void channelClubContent() {
        SendRequest.channelClubContent(cid, 10, new GenericsCallback<WorkData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(WorkData response, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
                if (response.getCode() == 200 && response.getData() != null && response.getData().getData() != null) {
//                    adapter.refreshData(response.getData().getData());
                } else {
                    ToastUtils.showShort(getActivity(), response.getMsg());
                }
            }

        });
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
