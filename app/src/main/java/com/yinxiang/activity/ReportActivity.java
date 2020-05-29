package com.yinxiang.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.yinxiang.R;
import com.yinxiang.databinding.ActivityReportBinding;

public class ReportActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private ActivityReportBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report);

        binding.back.setOnClickListener(this);
        binding.radioGroupView.setOnCheckedChangeListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_button_0:
                break;
            case R.id.radio_button_1:
                break;
            case R.id.radio_button_2:
                break;
            case R.id.radio_button_3:
                break;
            default:
                break;
        }
    }
}