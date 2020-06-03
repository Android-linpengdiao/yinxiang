package com.yinxiang.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yinxiang.R;
import com.yinxiang.databinding.ActivityClubMessageBinding;
import com.yinxiang.databinding.ActivityClubMessageDetailBinding;

public class ClubMessageDetailActivity extends BaseActivity implements View.OnClickListener {

    private ActivityClubMessageDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_club_message_detail);

        binding.back.setOnClickListener(this);
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
