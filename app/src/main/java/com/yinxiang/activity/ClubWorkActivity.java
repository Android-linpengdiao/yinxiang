package com.yinxiang.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.yinxiang.R;
import com.yinxiang.adapter.ClubWorkAdapter;
import com.yinxiang.adapter.WorkAdapter;
import com.yinxiang.databinding.ActivityClubWorkBinding;
import com.yinxiang.databinding.ActivityMyWorkBinding;
import com.yinxiang.model.ClubWorkData;
import com.yinxiang.model.WorkData;
import com.yinxiang.view.OnClickListener;

import okhttp3.Call;

public class ClubWorkActivity extends BaseActivity implements View.OnClickListener {

    private ActivityClubWorkBinding binding;
    private ClubWorkAdapter workAdapter;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_club_work);

        binding.back.setOnClickListener(this);

        workAdapter = new ClubWorkAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(workAdapter);
        workAdapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                if (object instanceof ClubWorkData.DataBean) {
                    ClubWorkData.DataBean dataBean = (ClubWorkData.DataBean) object;
                    Intent intent = new Intent(ClubWorkActivity.this, WorkDetailActivity.class);
                    intent.putExtra("workId", dataBean.getId());
                    startActivity(intent);
                }
            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });

        if (getIntent().hasExtra("cid")) {
            id = getIntent().getIntExtra("cid", 0);
            binding.swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
            binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    initData(id);
                }
            });
            binding.swipeRefreshLayout.setRefreshing(true);
            initData(id);
        }

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    private void initData(int id) {
        SendRequest.channelClubContent(id, 10, new GenericsCallback<ClubWorkData>(new JsonGenericsSerializator()) {
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
                    ToastUtils.showShort(ClubWorkActivity.this, response.getMsg());
                }
            }

        });
    }
}