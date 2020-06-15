package com.yinxiang.activity;

import android.databinding.DataBindingUtil;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.baselibrary.UserInfo;
import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.GlideLoader;
import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.callbacks.StringCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.okhttp.utils.APIUrls;
import com.yinxiang.MyApplication;
import com.yinxiang.R;
import com.yinxiang.adapter.UserHomeWorkAdapter;
import com.yinxiang.adapter.WorkAdapter;
import com.yinxiang.databinding.ActivityUserHomeBinding;
import com.yinxiang.model.HomeVideos;
import com.yinxiang.model.WorkData;
import com.yinxiang.view.GridItemDecoration;

import org.json.JSONObject;

import java.util.List;

import okhttp3.Call;

public class UserHomeActivity extends BaseActivity implements View.OnClickListener {
    private ActivityUserHomeBinding binding;
    private UserHomeWorkAdapter adapter;
    private int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_home);

        uid = getIntent().getIntExtra("uid", 0);
        binding.back.setOnClickListener(this);
        binding.tvIsFollow.setOnClickListener(this);

        adapter = new UserHomeWorkAdapter(this);
        binding.recyclerView.setNestedScrollingEnabled(false);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        GridItemDecoration.Builder builder = new GridItemDecoration.Builder(this);
        builder.color(R.color.transparent);
        builder.size(CommonUtil.dip2px(this, 15));
        binding.recyclerView.addItemDecoration(new GridItemDecoration(builder));
        binding.recyclerView.setAdapter(adapter);

        initData();

    }

    private void initData() {
        SendRequest.personInformInfo(uid, new GenericsCallback<UserInfo>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(UserInfo response, int id) {
                if (response.getCode() == 200 && response.getData() != null) {
                    initView(response);
                }
            }

        });
        homePagePersonIsFollow();
        centerSelfWork();

    }

    private void homePagePersonIsFollow() {
        SendRequest.homePagePersonIsFollow(MyApplication.getInstance().getUserInfo().getData().getId(), uid, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    if (!CommonUtil.isBlank(response)) {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.optInt("code") == 200 &&
                                !CommonUtil.isBlank(jsonObject.optJSONObject("data").optString("id"))) {
                            binding.tvIsFollow.setSelected(!binding.tvIsFollow.isSelected());
                            binding.tvIsFollow.setText(binding.tvIsFollow.isSelected() ? "已关注" : "关注");
                        }
                    } else {
                        ToastUtils.showShort(UserHomeActivity.this, "请求失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShort(UserHomeActivity.this, "请求失败");
                }

            }
        });
    }

    private void homePagePersonFollow() {
        String url = binding.tvIsFollow.isSelected() ? APIUrls.url_homePagePersonUnFollow : APIUrls.url_homePagePersonFollow;
        SendRequest.homePagePersonFollow(MyApplication.getInstance().getUserInfo().getData().getId(), uid, url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    if (!CommonUtil.isBlank(response)) {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.optInt("code") == 200) {
                            binding.tvIsFollow.setSelected(!binding.tvIsFollow.isSelected());
                            binding.tvIsFollow.setText(binding.tvIsFollow.isSelected() ? "已关注" : "关注");
                        } else {
                            ToastUtils.showShort(UserHomeActivity.this, jsonObject.optString("msg"));
                        }
                    } else {
                        ToastUtils.showShort(UserHomeActivity.this, "请求失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShort(UserHomeActivity.this, "请求失败");
                }

            }
        });
    }

    private void centerSelfWork() {
        SendRequest.personInformWorks(uid, 10, new GenericsCallback<WorkData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(WorkData response, int id) {
                if (response.getCode() == 200 && response.getData() != null && response.getData().getData() != null) {
                    adapter.refreshData(response.getData().getData());
                } else {
                    ToastUtils.showShort(UserHomeActivity.this, response.getMsg());
                }
            }

        });
    }

    private void initView(UserInfo userInfo) {

        binding.tvIsFollow.setOnClickListener(this);

        binding.userName.setText(userInfo.getData().getName());
        binding.userAge.setText(String.valueOf(userInfo.getData().getAge()));
        binding.userDesc.setText(userInfo.getData().getDesc());
        binding.touristId.setText("引享号：" + userInfo.getData().getTourist_id());
        binding.userAddr.setText(userInfo.getData().getAddr());
        binding.isVip.setVisibility(userInfo.getData().getIs_vip() == 1 ? View.VISIBLE : View.GONE);
        binding.userLevel.setText("Lv." + userInfo.getData().getLevel());
        binding.fanNumber.setText(String.valueOf(userInfo.getData().getFan_number()));
        binding.followNumber.setText(String.valueOf(userInfo.getData().getFollow_number()));
        GlideLoader.LoderCircleImage(UserHomeActivity.this, userInfo.getData().getAvatar(), binding.userIcon);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_is_follow:
                if (!CommonUtil.isBlank(uid)) {
                    homePagePersonFollow();
                }
                break;
        }
    }
}