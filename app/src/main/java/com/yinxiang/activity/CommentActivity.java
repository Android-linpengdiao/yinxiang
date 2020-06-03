package com.yinxiang.activity;

import android.databinding.DataBindingUtil;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.yinxiang.R;
import com.yinxiang.adapter.CommentAdapter;
import com.yinxiang.databinding.ActivityCommentBinding;
import com.yinxiang.model.LikeData;
import com.yinxiang.model.MessageData;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class CommentActivity extends BaseActivity implements View.OnClickListener {

    private ActivityCommentBinding binding;
    private CommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_comment);
        binding.back.setOnClickListener(this);

        adapter = new CommentAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        List<MessageData.DataBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MessageData.DataBean dataBean = new MessageData.DataBean();
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
        SendRequest.centerDiscuss(getUserInfo().getData().getId(), new GenericsCallback<MessageData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(MessageData response, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
                if (response.getCode() == 200 && response.getData() != null) {
                    adapter.refreshData(response.getData());
                } else {
                    ToastUtils.showShort(CommentActivity.this, response.getMsg());
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
