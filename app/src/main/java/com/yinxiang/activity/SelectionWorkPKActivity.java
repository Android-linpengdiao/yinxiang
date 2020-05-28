package com.yinxiang.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.yinxiang.R;
import com.yinxiang.adapter.WorkAdapter;
import com.yinxiang.databinding.ActivitySelectionWorkPkBinding;

public class SelectionWorkPKActivity extends BaseActivity implements View.OnClickListener {

    private ActivitySelectionWorkPkBinding binding;
    private WorkAdapter workAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_selection_work_pk);

        binding.back.setOnClickListener(this);

        workAdapter = new WorkAdapter(this);
        workAdapter.setSelection(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(workAdapter);
        workAdapter.refreshData(CommonUtil.getImageListString());

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}