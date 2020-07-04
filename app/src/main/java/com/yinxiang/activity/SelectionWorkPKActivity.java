package com.yinxiang.activity;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.callbacks.StringCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.yinxiang.R;
import com.yinxiang.adapter.SelectionWorkPKAdapter;
import com.yinxiang.databinding.ActivitySelectionWorkPkBinding;
import com.yinxiang.model.WorkData;
import com.yinxiang.view.OnClickListener;

import org.json.JSONObject;

import okhttp3.Call;

public class SelectionWorkPKActivity extends BaseActivity implements View.OnClickListener {

    private ActivitySelectionWorkPkBinding binding;
    private SelectionWorkPKAdapter workAdapter;
    private int videoId;
    private int activeId;
    private WorkData.DataBeanX.DataBean dataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_selection_work_pk);

        if (getIntent().getExtras() != null) {
            videoId = getIntent().getExtras().getInt("videoId");
            activeId = getIntent().getExtras().getInt("activeId");
        } else {
            finish();
        }

        binding.back.setOnClickListener(this);
        binding.tvConfirm.setOnClickListener(this);
        binding.tvRelease.setOnClickListener(this);
        binding.workPKView.setOnClickListener(this);
        binding.workReleaseWorkView.setOnClickListener(this);

        workAdapter = new SelectionWorkPKAdapter(this);
        workAdapter.setActiveId(activeId);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(workAdapter);
        workAdapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                if (object instanceof WorkData.DataBeanX.DataBean) {
                    dataBean = (WorkData.DataBeanX.DataBean) object;
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

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_release:
                openActivity(ReleaseActivity.class);
                break;
            case R.id.tv_confirm:
                if (dataBean != null) {
                    homePageVideosCreatePk(dataBean.getId(), videoId);
                } else {
                    ToastUtils.showShort(SelectionWorkPKActivity.this, "请选择你的作品");
                }
                break;
        }
    }

    private void initData() {
        SendRequest.personInformWorks(getUserInfo().getData().getId(), 100, new GenericsCallback<WorkData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(WorkData response, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
                if (response.getCode() == 200 && response.getData() != null && response.getData().getData() != null) {
                    workAdapter.refreshData(response.getData().getData());
                    if (response.getData().getData().size() > 0) {
                        for (int i = 0; i < response.getData().getData().size(); i++) {
                            if (response.getData().getData().get(i).getActive_id() == activeId) {
                                binding.workPKView.setVisibility(View.VISIBLE);
                                binding.workReleaseWorkView.setVisibility(View.GONE);
                            }
                        }
                    }
                } else {
                    ToastUtils.showShort(SelectionWorkPKActivity.this, response.getMsg());
                }
            }

        });
    }

    private void homePageVideosCreatePk(int touristVideoId, int relaytVideoId) {
        SendRequest.homePageVideosCreatePk(getUserInfo().getData().getId(), touristVideoId, relaytVideoId, new StringCallback() {
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