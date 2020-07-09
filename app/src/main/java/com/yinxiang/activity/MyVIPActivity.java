package com.yinxiang.activity;

import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.callbacks.StringCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.yinxiang.R;
import com.yinxiang.databinding.ActivityMyVipBinding;
import com.yinxiang.model.VipSetData;
import com.yinxiang.utils.PayManager;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

public class MyVIPActivity extends BaseActivity implements View.OnClickListener {
    private ActivityMyVipBinding binding;
    private String type = "wechat";
    private VipSetData vipSetData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_vip);

        binding.back.setOnClickListener(this);
        binding.tvConfirm.setOnClickListener(this);
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

        if (getUserInfo().getData().getIs_vip() == 1) {
            binding.viewLayoutVip.setVisibility(View.VISIBLE);
        } else if (getUserInfo().getData().getIs_vip() == 2) {
            binding.viewLayoutTopUp.setVisibility(View.VISIBLE);
            initData();
        }

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_confirm:
                if (vipSetData != null) {
                    cashPay();
                }
                break;
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
                    vipSetData = response;
                    binding.tvMoney.setText(String.valueOf(response.getData().getMoney()));
                } else {
                    ToastUtils.showShort(MyVIPActivity.this, response.getMsg());
                }
            }

        });
    }

    private void cashPay() {
        SendRequest.cashPay(getUserInfo().getData().getId(), type, "vip", vipSetData.getData().getMoney(), 0, new StringCallback() {
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
                            ToastUtils.showShort(MyVIPActivity.this, "微信支付暂未开通");
                        } else if (type.equals("alipay")) {
                            PayManager.aliPay(MyVIPActivity.this, msg, new PayManager.PayListener() {
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
                                    ToastUtils.showShort(MyVIPActivity.this, "取消支付");
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

