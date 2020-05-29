package com.yinxiang.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.yinxiang.R;
import com.yinxiang.databinding.ActivityCompetitionDetailBinding;

public class CompetitionDetailActivity extends BaseActivity implements View.OnClickListener {

    private ActivityCompetitionDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_competition_detail);

        binding.back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
}
