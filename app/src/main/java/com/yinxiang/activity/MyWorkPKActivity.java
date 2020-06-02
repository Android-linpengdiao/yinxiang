package com.yinxiang.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.yinxiang.R;
import com.yinxiang.adapter.HomeContestAdapter;
import com.yinxiang.adapter.WorkAdapter;
import com.yinxiang.databinding.ActivityMyWorkBinding;
import com.yinxiang.databinding.ActivityMyWorkPkBinding;
import com.yinxiang.databinding.ActivitySelectionWorkPkBinding;
import com.yinxiang.view.OnClickListener;

public class MyWorkPKActivity extends BaseActivity implements View.OnClickListener {

    private ActivityMyWorkPkBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_work_pk);

        binding.back.setOnClickListener(this);

        HomeContestAdapter adapter = new HomeContestAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        adapter.refreshData(CommonUtil.getImageListString());
        adapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                openActivity(WorkDetailActivity.class);
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
        }
    }
}