package com.yinxiang.activity;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.StringCallback;
import com.yinxiang.R;
import com.yinxiang.databinding.ActivityReportBinding;

import org.json.JSONObject;

import okhttp3.Call;

public class ReportActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private ActivityReportBinding binding;
    private int videoId;
    private int type = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report);

        if (getIntent().getExtras() != null) {
            videoId = getIntent().getExtras().getInt("videoId");
        }

        binding.back.setOnClickListener(this);
        binding.tvConfirm.setOnClickListener(this);
        binding.radioGroupView.setOnCheckedChangeListener(this);


    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_confirm:
                String content = binding.tvContent.getText().toString().trim();
                if (CommonUtil.isBlank(content)) {
                    ToastUtils.showShort(ReportActivity.this, "请输入举报描述文字");
                    return;
                }
                SendRequest.homePageVideosReport(getUserInfo().getData().getId(), videoId, type, content, new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            if (!CommonUtil.isBlank(response)) {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.optInt("code") == 200) {
                                    ToastUtils.showShort(getApplication(), "提交成功");
                                    finish();
                                } else {
                                    ToastUtils.showShort(getApplication(), jsonObject.optString("msg"));
                                }
                            } else {
                                ToastUtils.showShort(getApplication(), "请求失败");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils.showShort(getApplication(), "请求失败");
                        }
                    }
                });
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_button_0:
                type = 1;
                break;
            case R.id.radio_button_1:
                type = 2;
                break;
            case R.id.radio_button_2:
                type = 3;
                break;
            case R.id.radio_button_3:
                type = 4;
                break;
            default:
                break;
        }
    }
}