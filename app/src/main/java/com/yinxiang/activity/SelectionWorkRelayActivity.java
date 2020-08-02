package com.yinxiang.activity;

import androidx.databinding.DataBindingUtil;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.callbacks.StringCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.yinxiang.R;
import com.yinxiang.adapter.SelectionWorkRelayAdapter;
import com.yinxiang.adapter.WorkAdapter;
import com.yinxiang.databinding.ActivitySelectionWorkRelayBinding;
import com.yinxiang.model.WorkData;
import com.yinxiang.view.OnClickListener;

import org.json.JSONObject;

import okhttp3.Call;

public class SelectionWorkRelayActivity extends BaseActivity implements View.OnClickListener {

    private ActivitySelectionWorkRelayBinding binding;
    private SelectionWorkRelayAdapter workAdapter;
    private int videoId;
    private WorkData workData;
    private WorkData.DataBeanX.DataBean dataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_selection_work_relay);

        if (getIntent().getExtras() != null) {
            videoId = getIntent().getExtras().getInt("videoId");
        } else {
            finish();
        }

        binding.back.setOnClickListener(this);
        binding.tvConfirm.setOnClickListener(this);

        workAdapter = new SelectionWorkRelayAdapter(this);
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
            case R.id.tv_confirm:
                if (workData.getData() != null && workData.getData().getData() != null && workData.getData().getData().size() > 0) {
                    if (dataBean != null) {
                        homePageVideosRelay(dataBean.getId(), videoId);
                    } else {
                        ToastUtils.showShort(SelectionWorkRelayActivity.this, "请选择你的作品");
                    }
                } else {
                    openActivity(ReleaseActivity.class);
                }
                break;
        }
    }

    private void initData() {
        binding.swipeRefreshLayout.setRefreshing(true);
        SendRequest.personInformWorks(getUserInfo().getData().getId(), 10, new GenericsCallback<WorkData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(WorkData response, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
                if (response.getCode() == 200 && response.getData() != null && response.getData().getData() != null) {
                    workData = response;
                    workAdapter.refreshData(response.getData().getData());
                    binding.tvConfirm.setText(response.getData().getData().size() > 0 ? "开始接力" : "发布作品");
                    binding.releaseHintView.setVisibility(response.getData().getData().size() > 0 ? View.GONE : View.VISIBLE);
                } else {
                    ToastUtils.showShort(SelectionWorkRelayActivity.this, response.getMsg());
                }
            }

        });
    }

    private void homePageVideosRelay(int touristVideoId, int relaytVideoId) {
        SendRequest.homePageVideosRelay(touristVideoId, relaytVideoId, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!CommonUtil.isBlank(response)) {
                        if (jsonObject.optInt("code") == 200) {
                            openActivity(MyWorkRelayActivity.class);
                            finish();
                        } else {
                            ToastUtils.showShort(SelectionWorkRelayActivity.this, jsonObject.optString("msg"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShort(SelectionWorkRelayActivity.this, "请求失败");
                }
            }
        });
    }
}