package com.yinxiang.adapter;

import android.content.Context;
import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.GlideLoader;
import com.yinxiang.R;
import com.yinxiang.databinding.ItemSpreadLayoutBinding;
import com.yinxiang.databinding.ItemWorksLayoutBinding;
import com.yinxiang.model.ClubWorkData;
import com.yinxiang.model.SpreadSetData;
import com.yinxiang.view.OnClickListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SpreadSetAdapter extends BaseRecyclerAdapter<SpreadSetData.DataBean, ItemSpreadLayoutBinding> {

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public SpreadSetAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_spread_layout;
    }

    @Override
    protected void onBindItem(ItemSpreadLayoutBinding binding, final SpreadSetData.DataBean dataBean, final int position) {
        if (mList != null && mList.size() > 0) {
            binding.spreadView.setText(dataBean.getTitle());
            binding.spreadView.setSelected(dataBean.getSelected() == 0 ? false : true);
            binding.spreadView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        dataBean.setSelected(dataBean.getSelected() == 0 ? 1 : 0);
                        binding.spreadView.setSelected(dataBean.getSelected() == 0 ? false : true);
                        for (int i = 0; i < mList.size(); i++) {
                            if (dataBean != mList.get(i)) {
                                mList.get(i).setSelected(0);
                            }
                        }
                        onClickListener.onClick(v, dataBean);
                        notifyDataSetChanged();
                    }
                }
            });
        }

    }
}
