package com.yinxiang.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.yinxiang.R;
import com.yinxiang.adapter.WorkRelayAdapter;
import com.yinxiang.databinding.ActivityMyVipBinding;
import com.yinxiang.databinding.ActivityMyWorkRelayBinding;
import com.yinxiang.model.VipSetData;
import com.yinxiang.model.WalletSetData;

import java.util.Random;

import okhttp3.Call;

public class MyVIPActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "MyVIPActivity";
    private ActivityMyVipBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_vip);

        binding.back.setOnClickListener(this);

        if (getUserInfo().getData().getIs_vip() == 1) {
            binding.viewLayoutVip.setVisibility(View.VISIBLE);
        } else if (getUserInfo().getData().getIs_vip() == 2) {
            binding.viewLayoutTopUp.setVisibility(View.VISIBLE);
            initData();
        }

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    private void initData() {
        SendRequest.personVipSet(new GenericsCallback<VipSetData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(VipSetData response, int id) {
                if (response.getCode() == 200 && response.getData() != null) {
                    binding.tvMoney.setText(String.valueOf(response.getData().getMoney()));
                } else {
                    ToastUtils.showShort(MyVIPActivity.this, response.getMsg());
                }
            }

        });
    }
}

