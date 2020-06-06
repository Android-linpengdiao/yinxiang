package com.yinxiang.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.ToastUtils;
import com.yinxiang.R;
import com.yinxiang.adapter.WorkAdapter;
import com.yinxiang.databinding.ActivitySelectionWorkPkBinding;
import com.yinxiang.view.OnClickListener;

public class SelectionWorkPKActivity extends BaseActivity implements View.OnClickListener {

    private ActivitySelectionWorkPkBinding binding;
    private WorkAdapter workAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_selection_work_pk);

        binding.back.setOnClickListener(this);
        binding.tvConfirm.setOnClickListener(this);

        workAdapter = new WorkAdapter(this);
        workAdapter.setSelection(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(workAdapter);
//        workAdapter.refreshData(CommonUtil.getImageListString());
        workAdapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                ToastUtils.showShort(SelectionWorkPKActivity.this, "已选中");
            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_confirm:
                openActivity(MyWorkPKActivity.class);
                finish();
                break;
        }
    }
}