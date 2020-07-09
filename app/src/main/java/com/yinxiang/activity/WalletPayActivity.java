package com.yinxiang.activity;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;

import android.view.View;
import android.widget.RadioGroup;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.callbacks.StringCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.yinxiang.R;
import com.yinxiang.adapter.CoinAdapter;
import com.yinxiang.databinding.ActivityWalletPayBinding;
import com.yinxiang.model.WalletSetData;
import com.yinxiang.utils.PayManager;
import com.yinxiang.view.OnClickListener;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

public class WalletPayActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "WalletPayActivity";
    private ActivityWalletPayBinding binding;
    private CoinAdapter adapter;
    private String type = "wechat";
    private WalletSetData.DataBean dataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wallet_pay);

        binding.back.setOnClickListener(this);
        binding.tvConfirm.setOnClickListener(this);

        adapter = new CoinAdapter(this);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                if (object instanceof WalletSetData.DataBean) {
                    dataBean = (WalletSetData.DataBean) object;
                }
            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });
        binding.radioGroupView.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_button_wchat:
                        type = "wechat";
                        break;
                    case R.id.radio_button_alipay:
                        type = "alipay";
                        break;
                    default:
                        break;
                }
            }
        });
        initData();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_confirm:
                if (dataBean != null && dataBean.getSelected() == 1) {
                    cashPay();
                } else {
                    ToastUtils.showShort(WalletPayActivity.this, "请选择充值金额");
                }
                break;
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

    private void cashPay() {
        SendRequest.cashPay(getUserInfo().getData().getId(), type, "wallet", dataBean.getMoney(), dataBean.getWallet_token(), new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.optInt("code") == 200) {
                        JSONObject data = object.optJSONObject("data");
                        String msg = data.optString("content");
                        if (type.equals("wechat")) {
                            ToastUtils.showShort(WalletPayActivity.this, "微信支付暂未开通");
                        } else if (type.equals("alipay")) {
                            PayManager.aliPay(WalletPayActivity.this, msg, new PayManager.PayListener() {
                                @Override
                                public void onSuccess() {
                                    personInformInfo();
                                    paySuccess();
                                }

                                @Override
                                public void onFail() {
                                    payFail();

                                }

                                @Override
                                public void onCancel() {
                                    ToastUtils.showShort(WalletPayActivity.this, "取消支付");
                                }
                            });
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 支付成功
     */
    private void paySuccess() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("flag", true);
//        openActivity(PayResultActivity.class, bundle);
        finish();
    }

    //支付失败
    private void payFail() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("flag", false);
//        openActivity(PayResultActivity.class, bundle);
        finish();

    }
}
