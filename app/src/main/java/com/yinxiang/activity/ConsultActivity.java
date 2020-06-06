package com.yinxiang.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.StringCallback;
import com.yinxiang.R;
import com.yinxiang.databinding.ActivityConsultBinding;
import com.yinxiang.databinding.ActivityNoticeManageBinding;

import org.json.JSONObject;

import okhttp3.Call;

public class ConsultActivity extends BaseActivity {
    private ActivityConsultBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_consult);

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        personSettingsQuestion();
    }

    private void personSettingsQuestion() {
        SendRequest.personSettingsQuestion(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optInt("code") == 200) {

                    } else {
                        ToastUtils.showShort(ConsultActivity.this, jsonObject.optString("msg"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}

