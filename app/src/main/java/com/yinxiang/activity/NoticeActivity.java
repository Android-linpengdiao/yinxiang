package com.yinxiang.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.yinxiang.R;
import com.yinxiang.adapter.NoticeAdapter;
import com.yinxiang.databinding.ActivityNoticeBinding;
import com.yinxiang.model.NoticeData;

import java.util.ArrayList;
import java.util.List;

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

        List<NoticeData.DataBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            NoticeData.DataBean dataBean = new NoticeData.DataBean();
            list.add(dataBean);
        }
        adapter.refreshData(list);

        binding.swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
//        binding.swipeRefreshLayout.setRefreshing(true);
//        initData();

    }

    private void initData() {
        SendRequest.commonMessage(new GenericsCallback<NoticeData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(NoticeData response, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
                if (response.getCode() == 200 && response.getData() != null) {
                    adapter.refreshData(response.getData());
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
