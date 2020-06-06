package com.yinxiang.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.baselibrary.UserInfo;
import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.callbacks.StringCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.yinxiang.R;
import com.yinxiang.activity.ClubDetailActivity;
import com.yinxiang.activity.CreateClubActivity;
import com.yinxiang.adapter.ClubAdapter;
import com.yinxiang.databinding.FragmentChannelClubBinding;
import com.yinxiang.model.ClubData;
import com.yinxiang.view.OnClickListener;

import org.json.JSONObject;

import okhttp3.Call;

public class ChannelClubFragment extends BaseFragment implements View.OnClickListener {

    private FragmentChannelClubBinding binding;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ClubAdapter adapter;

    private OnFragmentInteractionListener mListener;

    public ChannelClubFragment() {

    }


    public static ChannelClubFragment newInstance(String param1, String param2) {
        ChannelClubFragment fragment = new ChannelClubFragment();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_channel_club, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) binding.viewLayout.getLayoutParams();
            layoutParams.topMargin = CommonUtil.getStatusBarHeight(getActivity()) + CommonUtil.dip2px(getActivity(), 60);
        }

        binding.createClubView.setOnClickListener(this);

        adapter = new ClubAdapter(getActivity());
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                if (object instanceof  ClubData.DataBean) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("dataBean", (ClubData.DataBean)object);
                    openActivity(ClubDetailActivity.class, bundle);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_club_view:
                openActivity(CreateClubActivity.class);
                break;
        }
    }

    private void initData() {
        SendRequest.channelClubStatus(1, new GenericsCallback<ClubData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(ClubData response, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
                if (response != null && response.getCode() == 200 && response.getData() != null) {
                    adapter.refreshData(response.getData());
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
