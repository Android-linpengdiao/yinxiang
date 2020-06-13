package com.yinxiang.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.yinxiang.R;
import com.yinxiang.adapter.WalletAdapter;
import com.yinxiang.databinding.ActivityMyWalletBinding;
import com.yinxiang.model.FansUserData;
import com.yinxiang.model.WalletRecordData;

import okhttp3.Call;

public class MyWalletActivity extends BaseActivity implements View.OnClickListener {

    private ActivityMyWalletBinding binding;
    private WalletAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_wallet);

        binding.back.setOnClickListener(this);
        binding.walletPay.setOnClickListener(this);

        adapter = new WalletAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
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
        binding.coin.setText(String.valueOf(getUserInfo().getData().getWallet_token()));
        binding.swipeRefreshLayout.setRefreshing(true);
        SendRequest.personWalletRecord(getUserInfo().getData().getId(), 10, new GenericsCallback<WalletRecordData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
                ToastUtils.showShort(MyWalletActivity.this, "" + e.getMessage());
            }

            @Override
            public void onResponse(WalletRecordData response, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
//                if (response.getCode() == 200 && response.getData() != null && response.getData().getData() != null) {
////                    adapter.refreshData(response.getData().getData());
//                } else {
//                    ToastUtils.showShort(MyWalletActivity.this, response.getMsg());
//                }
            }

        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wallet_pay:
                openActivity(WalletPayActivity.class);
                break;
            case R.id.back:
                finish();
                break;
        }
    }
}
