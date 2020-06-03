package com.yinxiang.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yinxiang.R;
import com.yinxiang.databinding.ActivityNoticeManageBinding;

public class NoticeManageActivity extends BaseActivity {
    private ActivityNoticeManageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notice_manage);

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.ivLikeNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.ivLikeNotice.setSelected(!binding.ivLikeNotice.isSelected());
            }
        });
        binding.ivCommentNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.ivCommentNotice.setSelected(!binding.ivCommentNotice.isSelected());
            }
        });
    }
}
