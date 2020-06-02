package com.yinxiang.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.ToastUtils;
import com.yinxiang.R;
import com.yinxiang.adapter.AssociationAdapter;
import com.yinxiang.databinding.ActivitySelectionAssociationBinding;
import com.yinxiang.model.NavData;
import com.yinxiang.view.OnClickListener;

import java.util.ArrayList;
import java.util.List;


public class SelectionAssociationActivity extends BaseActivity implements View.OnClickListener {

    private ActivitySelectionAssociationBinding binding;
    private AssociationAdapter adapter;
    private NavData.DataBean dataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_selection_association);

        binding.back.setOnClickListener(this);
        binding.confirm.setOnClickListener(this);

        adapter = new AssociationAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        NavData navData = new NavData();
        List<NavData.DataBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            NavData.DataBean dataBean = new NavData.DataBean();
            dataBean.setName("街舞艺术交流群" + i);
            list.add(dataBean);
        }
        navData.setData(list);
        adapter.refreshData(navData.getData());
        adapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                if (object instanceof NavData.DataBean)
                    dataBean = (NavData.DataBean) object;
            }

            @Override
            public void onLongClick(View view, Object object) {

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
                    Intent intent = new Intent();
                    intent.putExtra("associationType", dataBean.getName());
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    ToastUtils.showShort(SelectionAssociationActivity.this, "选择社团");
                }

                break;
        }
    }
}