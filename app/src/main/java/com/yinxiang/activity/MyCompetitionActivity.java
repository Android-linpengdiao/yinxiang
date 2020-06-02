package com.yinxiang.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.yinxiang.R;
import com.yinxiang.adapter.MyCompetitionAdapter;
import com.yinxiang.databinding.ActivityMyCompetitionBinding;
import com.yinxiang.view.OnClickListener;

public class MyCompetitionActivity extends BaseActivity implements View.OnClickListener {

    private ActivityMyCompetitionBinding binding;
    private MyCompetitionAdapter adapter;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_competition);

        binding.back.setOnClickListener(this);

        adapter = new MyCompetitionAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                Bundle bundle = new Bundle();
                bundle.putInt("type",(int)object);
                openActivity(JoinCompetitionDetailActivity.class,bundle);
            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });
        adapter.refreshData(CommonUtil.getImageListString());

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}