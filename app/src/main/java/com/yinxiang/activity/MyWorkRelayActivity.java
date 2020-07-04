package com.yinxiang.activity;

import android.content.Intent;
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
import com.yinxiang.adapter.WorkRelayAdapter;
import com.yinxiang.databinding.ActivityMyWorkRelayBinding;
import com.yinxiang.model.WorkRelayData;
import com.yinxiang.view.OnClickListener;

import okhttp3.Call;

public class MyWorkRelayActivity extends BaseActivity implements View.OnClickListener {

    private ActivityMyWorkRelayBinding binding;
    private WorkRelayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_work_relay);

        binding.back.setOnClickListener(this);

        adapter = new WorkRelayAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                Intent intent = new Intent(MyWorkRelayActivity.this, WorkDetailActivity.class);
                if (object instanceof WorkRelayData.DataBeanX.DataBean.FollowerBean) {
                    WorkRelayData.DataBeanX.DataBean.FollowerBean followerBean = (WorkRelayData.DataBeanX.DataBean.FollowerBean) object;
                    intent.putExtra("workId", followerBean.getId());
                } else if (object instanceof WorkRelayData.DataBeanX.DataBean.FollowableBean) {
                    WorkRelayData.DataBeanX.DataBean.FollowableBean followableBean = (WorkRelayData.DataBeanX.DataBean.FollowableBean) object;
                    intent.putExtra("workId", followableBean.getId());
                }
                startActivity(intent);
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
        SendRequest.homepageVideosSelfRelay(getUserInfo().getData().getId(), 10, new GenericsCallback<WorkRelayData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(WorkRelayData response, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
                if (response.getCode() == 200 ) {
                    if(response.getData() != null && response.getData().getData() != null){
                        adapter.refreshData(response.getData().getData());
                    }
                } else {
                    ToastUtils.showShort(MyWorkRelayActivity.this, response.getMsg());
                }
            }

        });
    }
}
