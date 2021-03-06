package com.yinxiang.activity;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;

import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.yinxiang.R;
import com.yinxiang.adapter.CompetitionAdapter;
import com.yinxiang.databinding.ActivitySelectionCompetitionBinding;
import com.yinxiang.model.HomeActives;
import com.yinxiang.view.OnClickListener;

import okhttp3.Call;

public class SelectionCompetitionActivity extends BaseActivity implements View.OnClickListener {

    private ActivitySelectionCompetitionBinding binding;
    private CompetitionAdapter adapter;
    private HomeActives.DataBean dataBean;

    private final static int REQUEST_CTYPE = 300;

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
                    Intent intent = new Intent(SelectionCompetitionActivity.this, CompetitionDetailActivity.class);
                    intent.putExtra("homeActives", dataBean);
                    startActivityForResult(intent, REQUEST_CTYPE);
                }
            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });

        binding.swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        initData();
    }

    private void initData() {
        binding.swipeRefreshLayout.setRefreshing(true);
        SendRequest.homePageActives(1, new GenericsCallback<HomeActives>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(HomeActives response, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
                if (response.getCode() == 200 && response.getData() != null) {
                    adapter.refreshData(response.getData());
                } else {
                    ToastUtils.showShort(SelectionCompetitionActivity.this, response.getMsg());
                }
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CTYPE:
                    if (data != null && dataBean != null) {
                        dataBean.setSelected(dataBean.getSelected() == 1 ? 0 : 1);
                        if (adapter.getList() != null & adapter.getList().size() > 0) {
                            for (int i = 0; i < adapter.getList().size(); i++) {
                                if (dataBean.getId() != adapter.getList().get(i).getId()) {
                                    adapter.getList().get(i).setSelected(0);
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.confirm:
                if (dataBean != null && dataBean.getSelected() == 1) {
                    Intent intent = new Intent();
                    intent.putExtra("homeActives", dataBean);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    ToastUtils.showShort(SelectionCompetitionActivity.this, "请选择分类");
                }

                break;
        }
    }
}
