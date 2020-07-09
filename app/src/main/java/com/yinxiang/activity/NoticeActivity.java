package com.yinxiang.activity;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;

import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.yinxiang.R;
import com.yinxiang.adapter.NoticeAdapter;
import com.yinxiang.databinding.ActivityNoticeBinding;
import com.yinxiang.model.NoticeData;

import okhttp3.Call;

public class NoticeActivity extends BaseActivity implements View.OnClickListener {

    private ActivityNoticeBinding binding;
    private NoticeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notice);
        binding.back.setOnClickListener(this);
        adapter = new NoticeAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        binding.swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        binding.swipeRefreshLayout.setRefreshing(true);
        initData();

    }

    private void initData() {
        completeRead(1);
        SendRequest.friendSystem(10, new GenericsCallback<NoticeData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(NoticeData response, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
                if (response.getCode() == 200) {
                    if (response.getData() != null && response.getData().getData() != null) {
                        adapter.refreshData(response.getData().getData());
                    }
                } else {
                    ToastUtils.showShort(NoticeActivity.this, response.getMsg());
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
