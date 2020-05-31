package com.yinxiang.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.yinxiang.R;
import com.yinxiang.adapter.CoinAdapter;
import com.yinxiang.databinding.ActivityWalletPayBinding;
import com.yinxiang.view.GridItemDecoration;

public class WalletPayActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "WalletPayActivity";
    private ActivityWalletPayBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wallet_pay);

        binding.back.setOnClickListener(this);

        CoinAdapter adapter = new CoinAdapter(this);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
//        GridItemDecoration.Builder builder = new GridItemDecoration.Builder(this);
//        builder.color(R.color.transparent);
//        builder.size(CommonUtil.dip2px(this, 15));
//        binding.recyclerView.addItemDecoration(new GridItemDecoration(builder));
        binding.recyclerView.setAdapter(adapter);
        Log.i(TAG, "onCreate: "+CommonUtil.getImageListString().subList(0, 6).size());
        adapter.refreshData(CommonUtil.getImageListString().subList(0, 6));

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
