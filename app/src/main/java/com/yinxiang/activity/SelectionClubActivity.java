package com.yinxiang.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.yinxiang.R;
import com.yinxiang.adapter.SelectionClubAdapter;
import com.yinxiang.databinding.ActivitySelectionClubBinding;
import com.yinxiang.model.ClubData;
import com.yinxiang.view.OnClickListener;

import okhttp3.Call;


public class SelectionClubActivity extends BaseActivity implements View.OnClickListener {

    private ActivitySelectionClubBinding binding;
    private SelectionClubAdapter adapter;
    private ClubData.DataBean dataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_selection_club);

        binding.back.setOnClickListener(this);
        binding.confirm.setOnClickListener(this);

        adapter = new SelectionClubAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                if (object instanceof ClubData.DataBean)
                    dataBean = (ClubData.DataBean) object;
                switch (view.getId()) {
                    case R.id.selection_view:

                        break;
                    case R.id.root_view:
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("dataBean", (ClubData.DataBean) object);
//                        openActivity(ClubDetailActivity.class, bundle);
                        break;
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
        binding.swipeRefreshLayout.setRefreshing(true);
        initData();
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
                    intent.putExtra("clubDataBean", dataBean);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    ToastUtils.showShort(SelectionClubActivity.this, "选择社团");
                }

                break;
        }
    }

    private void initData() {
        SendRequest.channelClub(getUserInfo().getData().getId(), 1, new GenericsCallback<ClubData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(ClubData response, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
                if (response != null && response.getCode() == 200 && response.getData() != null) {
                    adapter.refreshData(response.getData());
                }
            }

        });
    }
}