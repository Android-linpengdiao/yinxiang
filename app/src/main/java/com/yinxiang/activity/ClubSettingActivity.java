package com.yinxiang.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.StringCallback;
import com.yinxiang.R;
import com.yinxiang.databinding.ActivityClubSettingBinding;

import org.json.JSONObject;

import okhttp3.Call;

public class ClubSettingActivity extends BaseActivity implements View.OnClickListener {

    private ActivityClubSettingBinding binding;
    private int cid;
    private int join = 2;
    private String joinToken = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_club_setting);

        if (getIntent().hasExtra("cid")) {
            cid = getIntent().getIntExtra("cid", 0);
        }

        binding.back.setOnClickListener(this);
        binding.tvConfirm.setOnClickListener(this);
        binding.radioGroupView.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_button_free:
                        join = 2;
                        binding.joinCoinView.setVisibility(View.GONE);
                        break;
                    case R.id.radio_button_coin:
                        join = 1;
                        binding.joinCoinView.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_confirm:
                channelJoinClubSet();
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private static final String TAG = "ClubSettingActivity";

    private void channelJoinClubSet() {
        joinToken = binding.etJoinCoin.getText().toString().trim();
        if (join == 1) {
            if (CommonUtil.isBlank(joinToken)) {
                ToastUtils.showShort(ClubSettingActivity.this, "请输入金额");
                return;
            } else if (Integer.parseInt(joinToken) <= 0) {
                ToastUtils.showShort(ClubSettingActivity.this, "请输入有效金额");
                return;
            }
        } else if (join == 2) {
            joinToken = "0";
        }
        SendRequest.channelJoinClubSet(getUserInfo().getData().getId(), cid, String.valueOf(join), joinToken, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    if (!CommonUtil.isBlank(response)) {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.optInt("code") == 200) {
                            finish();
                        } else {
                            ToastUtils.showShort(ClubSettingActivity.this, jsonObject.optString("msg"));
                        }
                    } else {
                        ToastUtils.showShort(ClubSettingActivity.this, "请求失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShort(ClubSettingActivity.this, "请求失败");
                }
            }
        });
    }
}

