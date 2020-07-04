package com.yinxiang.adapter;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.View;

import com.yinxiang.R;
import com.yinxiang.databinding.ItemAreaListLayoutBinding;
import com.yinxiang.model.CityData;
import com.yinxiang.view.OnClickListener;


public class AreaFirstChildAdapter extends BaseRecyclerAdapter<CityData.FirstChildrenBean, ItemAreaListLayoutBinding> {

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public AreaFirstChildAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_area_list_layout;
    }

    private static final String TAG = "AreaFirstChildAdapter";

    @Override
    protected void onBindItem(final ItemAreaListLayoutBinding binding, final CityData.FirstChildrenBean cityData, final int position) {
        if (mList != null && mList.size() > 0) {
            binding.name.setText(cityData.getName());
            binding.name.setVisibility(cityData.getName().equals("市辖区") ? View.GONE : View.VISIBLE);
            binding.recyclerView.setVisibility(cityData.getName().equals("市辖区") ? View.VISIBLE : View.GONE);
            if (!cityData.getName().equals("市辖区")) {
                binding.name.setSelected(cityData.getStatus() == 0 ? false : true);
                binding.icon.setVisibility(cityData.getStatus() == 0 ? View.GONE : View.VISIBLE);
                binding.viewLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onClickListener != null) {
                            cityData.setStatus(cityData.getStatus() == 0 ? 1 : 0);
                            binding.name.setSelected(cityData.getStatus() == 0 ? false : true);
                            onClickListener.onClick(v, cityData);

                            for (int i = 0; i < mList.size(); i++) {
                                if (cityData != mList.get(i)) {
                                    mList.get(i).setStatus(0);
                                }
                            }
                        }
                    }
                });
            } else {
                AreaSecondChildAdapter secondChildAdapter = new AreaSecondChildAdapter(mContext);
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                binding.recyclerView.setAdapter(secondChildAdapter);
                secondChildAdapter.refreshData(cityData.getChildren());
                secondChildAdapter.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view, Object object) {
                        CityData.FirstChildrenBean.SecondChildrenBean secondChildrenBean = (CityData.FirstChildrenBean.SecondChildrenBean) object;
                        CityData.FirstChildrenBean firstChildrenBean = new CityData.FirstChildrenBean();
                        firstChildrenBean.setId(secondChildrenBean.getId());
                        firstChildrenBean.setName(secondChildrenBean.getName());
                        firstChildrenBean.setParentId(secondChildrenBean.getParentId());
                        firstChildrenBean.setStatus(secondChildrenBean.getStatus());
                        onClickListener.onClick(view, firstChildrenBean);
                    }

                    @Override
                    public void onLongClick(View view, Object object) {

                    }
                });
            }
        }

    }
}
