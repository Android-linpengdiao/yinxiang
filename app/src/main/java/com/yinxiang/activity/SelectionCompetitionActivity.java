package com.yinxiang.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.yinxiang.R;
import com.yinxiang.adapter.CompetitionAdapter;
import com.yinxiang.databinding.ActivitySelectionCompetitionBinding;
import com.yinxiang.model.HomeActives;
import com.yinxiang.model.NavData;
import com.yinxiang.view.OnClickListener;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class SelectionCompetitionActivity extends BaseActivity implements View.OnClickListener {

    private ActivitySelectionCompetitionBinding binding;
    private CompetitionAdapter adapter;
    private HomeActives.DataBean dataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_selection_competition);

        binding.back.setOnClickListener(this);
        binding.confirm.setOnClickListener(this);

        adapter = new CompetitionAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                if (object instanceof HomeActives.DataBean) {
                    dataBean = (HomeActives.DataBean) object;
//                    Intent intent = new Intent();
//                    intent.putExtra("homeActives", dataBean);
//                    setResult(RESULT_OK, intent);
//                    finish();
                }
            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });
        SendRequest.homePageActives(2,new GenericsCallback<HomeActives>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(HomeActives response, int id) {
                if (response.getCode() == 200 && response.getData() != null) {
                    for (int i = 0; i < response.getData().size(); i++) {
                        adapter.refreshData(response.getData());
                    }
                } else {
                    ToastUtils.showShort(SelectionCompetitionActivity.this, response.getMsg());
                }
            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.confirm:
                if (dataBean != null && dataBean.getStatus() == 1) {
//                    Intent intent = new Intent();
//                    intent.putExtra("competitionType", dataBean.getName());
//                    setResult(RESULT_OK, intent);
//                    finish();

                    openActivity(CompetitionDetailActivity.class);

                } else {
                    ToastUtils.showShort(SelectionCompetitionActivity.this, "请选择分类");
                }

                break;
        }
    }
}
