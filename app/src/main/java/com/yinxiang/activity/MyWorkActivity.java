package com.yinxiang.activity;

import android.content.Intent;

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
import com.yinxiang.adapter.WorkAdapter;
import com.yinxiang.databinding.ActivityMyWorkBinding;
import com.yinxiang.model.WorkData;
import com.yinxiang.view.OnClickListener;

import okhttp3.Call;

public class MyWorkActivity extends BaseActivity implements View.OnClickListener {

    private ActivityMyWorkBinding binding;
    private WorkAdapter workAdapter;
    private int id;
    private WorkData workData;
    private static final int REQUEST_WORK = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_work);

        binding.back.setOnClickListener(this);

        workAdapter = new WorkAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(workAdapter);
        workAdapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                if (object instanceof WorkData.DataBeanX.DataBean) {
                    WorkData.DataBeanX.DataBean dataBean = (WorkData.DataBeanX.DataBean) object;
                    Intent intent = new Intent(MyWorkActivity.this, WorkDetailActivity.class);
                    intent.putExtra("workId", dataBean.getId());
                    startActivityForResult(intent, REQUEST_WORK);
                }
            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });

        binding.title.setText("我的作品");
        id = getIntent().getIntExtra("uid", 0);
        binding.swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initUserData(id);
            }
        });
        binding.swipeRefreshLayout.setRefreshing(true);
        initUserData(id);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    private void initUserData(int id) {
        SendRequest.personInformWorks(id, 10, new GenericsCallback<WorkData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(WorkData response, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
                workData = response;
                if (response.getCode() == 200 && response.getData() != null && response.getData().getData() != null) {
                    workAdapter.refreshData(response.getData().getData());
                } else {
                    ToastUtils.showShort(MyWorkActivity.this, response.getMsg());
                }
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_WORK:
                    if (data != null) {
                        int workId = data.getIntExtra("workId", 0);
                        if (workData != null && workData.getCode() == 200 && workData.getData() != null && workData.getData().getData().size() > 0) {
                            for (int i = 0; i < workData.getData().getData().size(); i++) {
                                if (workData.getData().getData().get(i).getId() == workId) {
                                    workData.getData().getData().remove(workData.getData().getData().get(i));
                                    workAdapter.notifyDataSetChanged();
                                    break;
                                }
                            }
                        }
                    }
                    break;
            }
        }
    }
}