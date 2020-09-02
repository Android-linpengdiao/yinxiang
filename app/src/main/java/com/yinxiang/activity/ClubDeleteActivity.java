package com.yinxiang.activity;

import androidx.databinding.DataBindingUtil;
import okhttp3.Call;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.StringCallback;
import com.yinxiang.R;
import com.yinxiang.databinding.ActivityClubDeleteBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class ClubDeleteActivity extends BaseActivity implements View.OnClickListener {

    private ActivityClubDeleteBinding binding;
    private int clubId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_club_delete);

        clubId = getIntent().getIntExtra("clubId",0);

        binding.back.setOnClickListener(this);
        binding.tvConfirm.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_confirm:
                channelEissolveClub();
                break;
        }
    }

    private void channelEissolveClub() {
        String dissolve = binding.dissolve.getText().toString().trim();
        if (CommonUtil.isBlank(dissolve)) {
            ToastUtils.showShort(ClubDeleteActivity.this, "请输入解散理由");
            return;
        }
        SendRequest.channelEissolveClub(getUserId(true),clubId,dissolve, CommonUtil.getDateToString(String.valueOf(System.currentTimeMillis())), new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject json = new JSONObject(response);
                    if (json.optInt("code") == 200) {
                        ToastUtils.showShort(ClubDeleteActivity.this, "已解散社团");
                        finish();
                    } else {
                        String msg = json.optString("msg");
                        ToastUtils.showShort(getApplication(), msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
