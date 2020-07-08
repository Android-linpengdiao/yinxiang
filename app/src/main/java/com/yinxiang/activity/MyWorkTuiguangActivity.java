package com.yinxiang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.yinxiang.R;
import com.yinxiang.adapter.SpreadSetAdapter;
import com.yinxiang.adapter.UserHomeWorkAdapter;
import com.yinxiang.databinding.ActivityMyWorkTuiguangBinding;
import com.yinxiang.model.ClubData;
import com.yinxiang.model.SpreadData;
import com.yinxiang.model.SpreadSetData;
import com.yinxiang.model.VipSetData;
import com.yinxiang.view.GridItemDecoration;
import com.yinxiang.view.OnClickListener;

import okhttp3.Call;

public class MyWorkTuiguangActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MyWorkTuiguangActivity";
    private ActivityMyWorkTuiguangBinding binding;
    private int videoId;
    private int type = 100;
    private SpreadSetAdapter adapter;
    private SpreadSetData.DataBean dataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_work_tuiguang);

        if (getIntent().getExtras() != null) {
            videoId = getIntent().getExtras().getInt("videoId");
        } else {
            finish();
        }

        binding.back.setOnClickListener(this);
        binding.tvConfirm.setOnClickListener(this);

        adapter = new SpreadSetAdapter(this);
        binding.recyclerView.setNestedScrollingEnabled(false);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        GridItemDecoration.Builder builder = new GridItemDecoration.Builder(this);
        builder.color(R.color.transparent);
        builder.size(CommonUtil.dip2px(this, 15));
        binding.recyclerView.addItemDecoration(new GridItemDecoration(builder));
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                if (object instanceof SpreadSetData.DataBean)
                    dataBean = (SpreadSetData.DataBean) object;
                binding.walletRecord.setText(String.valueOf(dataBean.getWallet_token()));
            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });
        cashSpreadSet();

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_confirm:
                if (dataBean != null) {
                    cashSpread();
                }
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private void cashSpreadSet() {
        SendRequest.cashSpreadSet(new GenericsCallback<SpreadSetData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(SpreadSetData response, int id) {
                if (response != null && response.getData() != null) {
                    adapter.refreshData(response.getData());
                    binding.walletRecord.setText(response.getData().size() > 0 ? String.valueOf(response.getData().get(0).getWallet_token()) : "0");
                }

            }
        });
    }

    private void cashSpread() {
        SendRequest.cashSpread(getUserInfo().getData().getId(), videoId, dataBean.getWallet_token(), new GenericsCallback<SpreadData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(SpreadData response, int id) {
                if (response.getCode() == 200) {
                    ToastUtils.showShort(MyWorkTuiguangActivity.this, "推广成功");
                } else {
                    ToastUtils.showShort(MyWorkTuiguangActivity.this, response.getMsg());
                }

            }

        });
    }

}