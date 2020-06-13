package com.yinxiang.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.yinxiang.R;
import com.yinxiang.adapter.HomeContestAdapter;
import com.yinxiang.adapter.WorkAdapter;
import com.yinxiang.databinding.ActivityMyWorkBinding;
import com.yinxiang.databinding.ActivityMyWorkPkBinding;
import com.yinxiang.databinding.ActivitySelectionWorkPkBinding;
import com.yinxiang.model.ClubWorkData;
import com.yinxiang.model.WorkPKData;
import com.yinxiang.model.WorkRelayData;
import com.yinxiang.view.OnClickListener;

import okhttp3.Call;

public class MyWorkPKActivity extends BaseActivity implements View.OnClickListener {

    private ActivityMyWorkPkBinding binding;
    private HomeContestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_work_pk);

        binding.back.setOnClickListener(this);

        adapter = new HomeContestAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                if (object instanceof WorkPKData.DataBeanX.DataBean) {
                    WorkPKData.DataBeanX.DataBean dataBean = (WorkPKData.DataBeanX.DataBean) object;
                    Intent intent = new Intent(MyWorkPKActivity.this, WorkDetailActivity.class);
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

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    private void initData() {
        SendRequest.homePageVideosSelfPk(getUserInfo().getData().getId(), 10, new GenericsCallback<WorkPKData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(WorkPKData response, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
                if (response.getCode() == 200 && response.getData() != null && response.getData().getData() != null) {
                    adapter.refreshData(response.getData().getData());
                } else {
                    ToastUtils.showShort(MyWorkPKActivity.this, response.getMsg());
                }
            }

        });
    }
}