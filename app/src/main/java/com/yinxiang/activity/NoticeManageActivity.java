package com.yinxiang.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.StringCallback;
import com.yinxiang.R;
import com.yinxiang.databinding.ActivityNoticeManageBinding;

import org.json.JSONObject;

import okhttp3.Call;

public class NoticeManageActivity extends BaseActivity {
    private ActivityNoticeManageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notice_manage);

        binding.ivLikeNotice.setSelected(getUserInfo().getData().getLike_notice() == 1 ? true : false);
        binding.ivCommentNotice.setSelected(getUserInfo().getData().getComment_notice() == 1 ? true : false);

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.ivLikeNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personSettingsNotice("like_notice", binding.ivLikeNotice.isSelected() ? 2 : 1);
            }
        });
        binding.ivCommentNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personSettingsNotice("comment_notice", binding.ivCommentNotice.isSelected() ? 2 : 1);
            }
        });
    }

    private void personSettingsNotice(final String key, final int value) {
        SendRequest.personSettingsNotice(getUserInfo().getData().getId(), key, value, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optInt("code") == 200) {
                        if (key.equals("like_notice")) {
                            binding.ivLikeNotice.setSelected(value == 1 ? true : false);
                        } else if (key.equals("comment_notice")) {
                            binding.ivCommentNotice.setSelected(value == 1 ? true : false);
                        }
                        personInformInfo();
                    } else {
                        ToastUtils.showShort(NoticeManageActivity.this, jsonObject.optString("msg"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
