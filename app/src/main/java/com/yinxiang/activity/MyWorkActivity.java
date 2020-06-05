package com.yinxiang.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.yinxiang.R;
import com.yinxiang.adapter.WorkAdapter;
import com.yinxiang.databinding.ActivityMyWorkBinding;
import com.yinxiang.databinding.ActivitySelectionWorkPkBinding;
import com.yinxiang.model.FollowUserData;
import com.yinxiang.view.OnClickListener;

import okhttp3.Call;

public class MyWorkActivity extends BaseActivity implements View.OnClickListener {

    private ActivityMyWorkBinding binding;
    private WorkAdapter workAdapter;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_work);

        if (getIntent().hasExtra("id")) {
            id = getIntent().getIntExtra("type", 0);
            if (id == getUserInfo().getData().getId()) {
                binding.title.setText("我的作品");
            }else {
                binding.title.setText("社团作品");
            }
        }

        binding.back.setOnClickListener(this);

        workAdapter = new WorkAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        workAdapter.refreshData(CommonUtil.getImageListString());

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

//    private void initData() {
//        SendRequest.personInformFollows(getUserInfo().getData().getId(), 10, new GenericsCallback<FollowUserData>(new JsonGenericsSerializator()) {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//                binding.swipeRefreshLayout.setRefreshing(false);
//                ToastUtils.showShort(MyFollowActivity.this, "" + e.getMessage());
//            }
//
//            @Override
//            public void onResponse(FollowUserData response, int id) {
//                followUserData = response;
//                binding.swipeRefreshLayout.setRefreshing(false);
//                if (response.getCode() == 200 && response.getData() != null && response.getData().getData() != null) {
//                    adapter.refreshData(response.getData().getData());
//                } else {
//                    ToastUtils.showShort(MyFollowActivity.this, response.getMsg());
//                }
//            }
//
//        });
//    }
}