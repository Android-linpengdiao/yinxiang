package com.yinxiang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.yinxiang.R;
import com.yinxiang.databinding.ActivityMyWorkTuiguangBinding;
import com.yinxiang.model.VipSetData;

import okhttp3.Call;

public class MyWorkTuiguangActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MyWorkTuiguangActivity";
    private ActivityMyWorkTuiguangBinding binding;
    private int videoId;
    private int type = 100;

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
        binding.radioGroupView.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_button_0:
                        type = 100;
                        binding.walletRecord.setText(String.valueOf(type));
                        break;
                    case R.id.radio_button_1:
                        type = 200;
                        binding.walletRecord.setText(String.valueOf(type));
                        break;
                    case R.id.radio_button_2:
                        type = 400;
                        binding.walletRecord.setText(String.valueOf(type));
                        break;
                    default:
                        break;
                }
            }
        });
        binding.walletRecord.setText(String.valueOf(type));

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_confirm:
                cashSpread();
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private void cashSpread() {
        switch (binding.radioGroupView.getCheckedRadioButtonId()) {
            case R.id.radio_button_0:
                type = 100;
                break;
            case R.id.radio_button_1:
                type = 200;
                break;
            case R.id.radio_button_2:
                type = 400;
                break;
            default:
                break;
        }
        SendRequest.cashSpread(getUserInfo().getData().getId(),videoId, type, new GenericsCallback<VipSetData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(VipSetData response, int id) {

            }

        });
    }

}