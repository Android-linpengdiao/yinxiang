package com.yinxiang.activity;

import androidx.databinding.DataBindingUtil;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.View;

import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.yinxiang.R;
import com.yinxiang.adapter.CommentAdapter;
import com.yinxiang.databinding.ActivityCommentBinding;
import com.yinxiang.model.MessageData;

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
        completeRead(3);
        SendRequest.friendComment(getUserInfo().getData().getId(), 10, new GenericsCallback<MessageData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(MessageData response, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
                if (response.getCode() == 200 && response.getData() != null && response.getData().getData() != null) {
                    adapter.refreshData(response.getData().getData());
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
