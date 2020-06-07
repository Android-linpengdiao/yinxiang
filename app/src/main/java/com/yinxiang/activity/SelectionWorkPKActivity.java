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
import com.okhttp.callbacks.StringCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.yinxiang.R;
import com.yinxiang.adapter.WorkAdapter;
import com.yinxiang.databinding.ActivitySelectionWorkPkBinding;
import com.yinxiang.model.WorkData;
import com.yinxiang.view.OnClickListener;

import org.json.JSONObject;

import okhttp3.Call;

public class SelectionWorkPKActivity extends BaseActivity implements View.OnClickListener {

    private ActivitySelectionWorkPkBinding binding;
    private WorkAdapter workAdapter;
    private int videoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_selection_work_pk);

        if (getIntent().getExtras() != null) {
            videoId = getIntent().getExtras().getInt("videoId");
        }else {
            finish();
        }

        binding.back.setOnClickListener(this);
        binding.tvConfirm.setOnClickListener(this);

        workAdapter = new WorkAdapter(this);
        workAdapter.setSelection(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(workAdapter);
        workAdapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                if (object instanceof WorkData.DataBeanX.DataBean){
                    WorkData.DataBeanX.DataBean dataBean = (WorkData.DataBeanX.DataBean) object;
                    homePageVideosCreatePk(dataBean.getId(),videoId);
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
            case R.id.tv_confirm:
//                openActivity(MyWorkPKActivity.class);
//                finish();
                break;
        }
    }

    private void initData() {
        SendRequest.personInformWorks(getUserInfo().getData().getId(), 10, new GenericsCallback<WorkData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(WorkData response, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
                if (response.getCode() == 200 && response.getData() != null && response.getData().getData() != null) {
                    workAdapter.refreshData(response.getData().getData());
                } else {
                    ToastUtils.showShort(SelectionWorkPKActivity.this, response.getMsg());
                }
            }

        });
    }

    private void homePageVideosCreatePk(int touristVideoId, int relaytVideoId) {
        SendRequest.homePageVideosCreatePk(getUserInfo().getData().getId(),touristVideoId, relaytVideoId, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!CommonUtil.isBlank(response)) {
                        if (jsonObject.optInt("code") == 200) {
                            openActivity(MyWorkPKActivity.class);
                            finish();
                        } else {
                            ToastUtils.showShort(SelectionWorkPKActivity.this, jsonObject.optString("msg"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShort(SelectionWorkPKActivity.this, "请求失败");
                }
            }
        });
    }
}