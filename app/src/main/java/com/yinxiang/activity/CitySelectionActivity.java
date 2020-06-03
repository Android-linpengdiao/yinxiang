package com.yinxiang.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.yinxiang.R;
import com.yinxiang.adapter.AreaAdapter;
import com.yinxiang.adapter.AreaFirstChildAdapter;
import com.yinxiang.databinding.ActivityCitySelectionBinding;
import com.yinxiang.manager.CityManager;
import com.yinxiang.model.CityData;
import com.yinxiang.view.OnClickListener;

import java.util.List;

public class CitySelectionActivity extends BaseActivity implements View.OnClickListener {

    private ActivityCitySelectionBinding mBinding;
    private List<CityData> areaList;
    private AreaAdapter areaAdapter;
    private CityData areaData;

    private AreaFirstChildAdapter areaFirstChildAdapter;
    private CityData.FirstChildrenBean areaFirstData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_city_selection);

        mBinding.back.setOnClickListener(this);
        mBinding.areaFirst.setOnClickListener(this);
        mBinding.areaSecond.setOnClickListener(this);
        mBinding.areaFirst.setSelected(true);

        areaAdapter = new AreaAdapter(this);
        mBinding.areaFirstRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.areaFirstRecyclerView.setAdapter(areaAdapter);
        areaList = CityManager.getInstance().getAreaFirst();
        areaAdapter.refreshData(areaList);
        areaAdapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                areaData = (CityData) object;

                areaAdapter.notifyDataSetChanged();
                mBinding.areaFirst.setText(areaData.getStatus() == 0 ? "请选择" : areaData.getName());
                mBinding.areaFirst.setSelected(true);
                mBinding.areaSecond.setSelected(false);
                mBinding.areaSecond.setVisibility(areaData.getStatus() == 0 ? View.GONE : View.VISIBLE);


                mBinding.areaSecond.setText("请选择");
                if (areaData.getStatus() == 1) {
                    areaFirstChildAdapter.refreshData(areaData.getChildren());
                }
            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });

        areaFirstChildAdapter = new AreaFirstChildAdapter(this);
        mBinding.areaSecondRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.areaSecondRecyclerView.setAdapter(areaFirstChildAdapter);
        areaFirstChildAdapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                areaFirstData = (CityData.FirstChildrenBean) object;

                areaFirstChildAdapter.notifyDataSetChanged();
                mBinding.areaSecond.setText(areaFirstData.getStatus() == 0 ? "请选择" : areaFirstData.getName());

                mBinding.areaFirst.setSelected(false);
                mBinding.areaSecond.setSelected(true);
                mBinding.areaSecond.setVisibility(areaData.getStatus() == 0 ? View.GONE : View.VISIBLE);
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

                break;
            case R.id.area_first:
                mBinding.areaFirstRecyclerView.setVisibility(View.VISIBLE);
                mBinding.areaSecondRecyclerView.setVisibility(View.GONE);
                mBinding.areaFirst.setSelected(true);
                mBinding.areaSecond.setSelected(false);
                break;
            case R.id.area_second:
                mBinding.areaFirstRecyclerView.setVisibility(View.GONE);
                mBinding.areaSecondRecyclerView.setVisibility(View.VISIBLE);
                mBinding.areaFirst.setSelected(false);
                mBinding.areaSecond.setSelected(true);
                break;
        }
    }
}
