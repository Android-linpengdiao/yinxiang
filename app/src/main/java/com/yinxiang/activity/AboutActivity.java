package com.yinxiang.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.StringCallback;
import com.yinxiang.R;
import com.yinxiang.databinding.ActivityAboutBinding;

import org.json.JSONObject;

import okhttp3.Call;

public class AboutActivity extends BaseActivity implements View.OnClickListener {
    private ActivityAboutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about);
        binding.back.setOnClickListener(this);

        personSettingsAbout();

    }

    private void personSettingsAbout() {
        SendRequest.personSettingsAbout(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optInt("code") == 200) {
                        JSONObject object = jsonObject.optJSONObject("data");
                        binding.content.setText(object.optString("desc"));
                    } else {
                        ToastUtils.showShort(AboutActivity.this, jsonObject.optString("msg"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
