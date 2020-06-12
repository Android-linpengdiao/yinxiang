package com.yinxiang.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.GlideLoader;
import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.StringCallback;
import com.yinxiang.R;
import com.yinxiang.databinding.ActivityClubMessageBinding;
import com.yinxiang.databinding.ActivityClubMessageDetailBinding;
import com.yinxiang.model.ClubMessageData;

import org.json.JSONObject;

import okhttp3.Call;

public class ClubMessageDetailActivity extends BaseActivity implements View.OnClickListener {

    private ActivityClubMessageDetailBinding binding;
    private ClubMessageData.DataBeanX.DataBean dataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_club_message_detail);

        binding.back.setOnClickListener(this);

        if (getIntent().hasExtra("dataBean")) {
            dataBean = (ClubMessageData.DataBeanX.DataBean) getIntent().getSerializableExtra("dataBean");
            binding.tvTitle.setText(dataBean.getTourist().getName());
            binding.tvDesc.setText("申请加入" + dataBean.getClub().getName());
            GlideLoader.LoderCircleImage(this, dataBean.getTourist().getAvatar(), binding.userIcon);
            binding.tvAgree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    friendClubAction(1);
                }
            });
            binding.tvDisagree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    friendClubAction(2);
                }
            });
        } else {
            finish();
        }
    }

    private void friendClubAction(final int status) {
        SendRequest.friendClubAction(dataBean.getId(), status, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    if (!CommonUtil.isBlank(response)) {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.optInt("code") == 200) {
                            ToastUtils.showShort(ClubMessageDetailActivity.this, status == 1 ? "已同意" : "已拒绝");
                            finish();
                        } else {
                            ToastUtils.showShort(ClubMessageDetailActivity.this, jsonObject.optString("msg"));
                        }
                    } else {
                        ToastUtils.showShort(ClubMessageDetailActivity.this, "请求失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShort(ClubMessageDetailActivity.this, "请求失败");
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
