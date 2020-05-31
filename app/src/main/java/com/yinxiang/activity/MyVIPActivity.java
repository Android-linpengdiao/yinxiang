package com.yinxiang.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.yinxiang.R;
import com.yinxiang.adapter.WorkRelayAdapter;
import com.yinxiang.databinding.ActivityMyVipBinding;
import com.yinxiang.databinding.ActivityMyWorkRelayBinding;

import java.util.Random;

public class MyVIPActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "MyVIPActivity";
    private ActivityMyVipBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_vip);

        binding.back.setOnClickListener(this);

        int isVip = (new Random()).nextInt(2);
        binding.viewLayoutVip.setVisibility(isVip == 1 ? View.VISIBLE : View.GONE);
        binding.viewLayoutTopUp.setVisibility(isVip == 0 ? View.VISIBLE : View.GONE);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}

