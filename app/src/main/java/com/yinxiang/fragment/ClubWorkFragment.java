package com.yinxiang.fragment;

import android.content.Context;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.yinxiang.R;
import com.yinxiang.activity.WorkDetailActivity;
import com.yinxiang.adapter.ClubWorkAdapter;
import com.yinxiang.databinding.FragmentClubWorkBinding;
import com.yinxiang.model.ClubWorkData;
import com.yinxiang.view.OnClickListener;

import okhttp3.Call;

public class ClubWorkFragment extends BaseFragment {

    private FragmentClubWorkBinding binding;
    private ClubWorkAdapter workAdapter;

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

        workAdapter = new ClubWorkAdapter(getActivity());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setNestedScrollingEnabled(false);
        binding.recyclerView.setAdapter(workAdapter);
        workAdapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                if (object instanceof ClubWorkData.DataBean) {
                    ClubWorkData.DataBean dataBean = (ClubWorkData.DataBean) object;
                    Intent intent = new Intent(getActivity(), WorkDetailActivity.class);
                    intent.putExtra("workId", dataBean.getId());
                    startActivity(intent);
                }
            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });

        binding.swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        binding.swipeRefreshLayout.setRefreshing(true);
        initData();

        return binding.getRoot();
    }

    private void initData() {
        SendRequest.channelClubContent(cid, 10, new GenericsCallback<ClubWorkData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(ClubWorkData response, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
                if (response.getCode() == 200 && response.getData() != null) {
                    workAdapter.refreshData(response.getData());
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
