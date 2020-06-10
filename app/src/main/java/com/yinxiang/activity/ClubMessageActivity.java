package com.yinxiang.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.callbacks.StringCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.okhttp.utils.APIUrls;
import com.yinxiang.R;
import com.yinxiang.adapter.ClubMessageAdapter;
import com.yinxiang.adapter.MyFollowAdapter;
import com.yinxiang.databinding.ActivityClubMessageBinding;
import com.yinxiang.databinding.ActivityMyFollowBinding;
import com.yinxiang.model.ClubMessageData;
import com.yinxiang.model.FollowUserData;
import com.yinxiang.view.OnClickListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class ClubMessageActivity extends BaseActivity implements View.OnClickListener {

    private ActivityClubMessageBinding binding;
    private ClubMessageAdapter adapter;
    private ClubMessageData clubMessageData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_club_message);

        binding.back.setOnClickListener(this);

        adapter = new ClubMessageAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {

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

    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        binding.swipeRefreshLayout.setRefreshing(true);
        SendRequest.friendClub(getUserInfo().getData().getId(), 10, new GenericsCallback<ClubMessageData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
                ToastUtils.showShort(ClubMessageActivity.this, "" + e.getMessage());
            }

            @Override
            public void onResponse(ClubMessageData response, int id) {
                clubMessageData = response;
                binding.swipeRefreshLayout.setRefreshing(false);
                if (response.getCode() == 200 && response.getData() != null && response.getData().getData() != null) {
                    adapter.refreshData(response.getData().getData());
                } else {
                    ToastUtils.showShort(ClubMessageActivity.this, response.getMsg());
                }
            }

        });
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