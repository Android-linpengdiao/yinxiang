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
import com.yinxiang.adapter.MyFansAdapter;
import com.yinxiang.databinding.ActivityMyFansBinding;
import com.yinxiang.model.FansUserData;
import com.yinxiang.view.OnClickListener;

import okhttp3.Call;

public class MyFansActivity extends BaseActivity implements View.OnClickListener {
    private ActivityMyFansBinding binding;
    private MyFansAdapter adapter;
    private FansUserData fansUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_fans);
        binding.back.setOnClickListener(this);

        adapter = new MyFansAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                FansUserData.DataBeanX.DataBean dataBean = (FansUserData.DataBeanX.DataBean) object;
                setFollow(dataBean);
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
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        binding.swipeRefreshLayout.setRefreshing(true);
        SendRequest.personInformFans(getUserInfo().getData().getId(), 10, new GenericsCallback<FansUserData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
                ToastUtils.showShort(MyFansActivity.this, "" + e.getMessage());
            }

            @Override
            public void onResponse(FansUserData response, int id) {
                fansUserData = response;
                binding.swipeRefreshLayout.setRefreshing(false);
                if (response.getCode() == 200 && response.getData() != null && response.getData().getData() != null) {
                    adapter.refreshData(response.getData().getData());
                } else {
                    ToastUtils.showShort(MyFansActivity.this, response.getMsg());
                }
            }

        });
    }

    private void setFollow(final FansUserData.DataBeanX.DataBean dataBean) {
//        String url = dataBean.isAttention() ? APIUrls.url_centerUnFollow : APIUrls.url_centerFollow;
//        SendRequest.centerFollow(getUserInfo().getData().getId(), dataBean.getId(), url, new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//                try {
//                    if (!CommonUtil.isBlank(response)) {
//                        JSONObject jsonObject = new JSONObject(response);
//                        if (jsonObject.optInt("code") == 200) {
//                            dataBean.setAttention(!dataBean.isAttention());
//                            if (dataBean.isAttention()) {
//                                ToastUtils.showShort(MyFansActivity.this, "已关注");
//                            }
//                            adapter.notifyItemChanged(fansUserData.getData().getData().indexOf(dataBean));
//                        } else {
//                            ToastUtils.showShort(MyFansActivity.this, jsonObject.optString("msg"));
//                        }
//                    } else {
//                        ToastUtils.showShort(MyFansActivity.this, "请求失败");
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    ToastUtils.showShort(MyFansActivity.this, "请求失败");
//                }
//
//            }
//        });
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