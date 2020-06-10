package com.yinxiang.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.yinxiang.R;
import com.yinxiang.adapter.CoinAdapter;
import com.yinxiang.databinding.ActivityWalletPayBinding;
import com.yinxiang.model.WalletRecordData;
import com.yinxiang.model.WalletSetData;
import com.yinxiang.view.GridItemDecoration;

import okhttp3.Call;

public class WalletPayActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "WalletPayActivity";
    private ActivityWalletPayBinding binding;
    private CoinAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wallet_pay);

        binding.back.setOnClickListener(this);

        adapter = new CoinAdapter(this);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
//        GridItemDecoration.Builder builder = new GridItemDecoration.Builder(this);
//        builder.color(R.color.transparent);
//        builder.size(CommonUtil.dip2px(this, 15));
//        binding.recyclerView.addItemDecoration(new GridItemDecoration(builder));
        binding.recyclerView.setAdapter(adapter);
        initData();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    private void initData() {
        SendRequest.personWalletSet(new GenericsCallback<WalletSetData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(WalletSetData response, int id) {
                if (response.getCode() == 200 && response.getData() != null) {
                    adapter.refreshData(response.getData());
                } else {
                    ToastUtils.showShort(WalletPayActivity.this, response.getMsg());
                }
            }

        });
    }
}
