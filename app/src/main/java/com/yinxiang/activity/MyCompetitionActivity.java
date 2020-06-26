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
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.yinxiang.R;
import com.yinxiang.adapter.MyCompetitionAdapter;
import com.yinxiang.databinding.ActivityMyCompetitionBinding;
import com.yinxiang.model.ActivityData;
import com.yinxiang.model.WorkData;
import com.yinxiang.view.OnClickListener;

import okhttp3.Call;

public class MyCompetitionActivity extends BaseActivity implements View.OnClickListener {

    private ActivityMyCompetitionBinding binding;
    private MyCompetitionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_competition);

        binding.back.setOnClickListener(this);

        adapter = new MyCompetitionAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                if (object instanceof ActivityData.DataBeanX.DataBean) {
                    ActivityData.DataBeanX.DataBean dataBean = (ActivityData.DataBeanX.DataBean) object;
                    Bundle bundle = new Bundle();
                    bundle.putInt("workId", dataBean.getActive_id());
                    openActivity(JoinCompetitionDetailActivity.class, bundle);
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
        initData();

    }

    private void initData() {
        binding.swipeRefreshLayout.setRefreshing(true);
        SendRequest.personInformActive(getUserInfo().getData().getId(), new GenericsCallback<ActivityData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(ActivityData response, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
                if (response.getCode() == 200 && response.getData() != null && response.getData().getData() != null) {
                    adapter.refreshData(response.getData().getData());
                } else {
                    ToastUtils.showShort(MyCompetitionActivity.this, response.getMsg());
                }
            }

        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}