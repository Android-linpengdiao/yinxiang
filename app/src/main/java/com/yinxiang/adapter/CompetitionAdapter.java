package com.yinxiang.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.yinxiang.R;
import com.yinxiang.activity.CompetitionDetailActivity;
import com.yinxiang.databinding.ItemCompetitionLayoutBinding;
import com.yinxiang.model.HomeActives;
import com.yinxiang.model.NavData;
import com.yinxiang.view.OnClickListener;


public class CompetitionAdapter extends BaseRecyclerAdapter<HomeActives.DataBean, ItemCompetitionLayoutBinding> {
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public CompetitionAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_competition_layout;
    }

    @Override
    protected void onBindItem(final ItemCompetitionLayoutBinding binding, final HomeActives.DataBean dataBean, final int position) {
        if (mList != null && mList.size() > 0) {
            binding.name.setText(dataBean.getTitle());
            binding.name.setSelected(dataBean.getSelected() == 1 ? true : false);
            binding.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(v, dataBean);
                    }
                }
            });
        }

    }
}
