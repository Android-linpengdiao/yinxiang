package com.yinxiang.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.yinxiang.R;
import com.yinxiang.adapter.WorkAdapter;
import com.yinxiang.databinding.ActivitySelectionWorkPkBinding;
import com.yinxiang.databinding.ActivitySelectionWorkRelayBinding;

public class SelectionWorkRelayActivity extends BaseActivity implements View.OnClickListener {

    private ActivitySelectionWorkRelayBinding binding;
    private WorkAdapter workAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_selection_work_relay);

        binding.back.setOnClickListener(this);
        binding.tvConfirm.setOnClickListener(this);

        workAdapter = new WorkAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(workAdapter);
//        workAdapter.refreshData(CommonUtil.getImageListString());

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_confirm:
                openActivity(MyWorkRelayActivity.class);
                finish();
                break;
        }
    }
}