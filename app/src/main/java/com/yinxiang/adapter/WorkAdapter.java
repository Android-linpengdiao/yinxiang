package com.yinxiang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.baselibrary.utils.GlideLoader;
import com.yinxiang.R;
import com.yinxiang.activity.WorkDetailActivity;
import com.yinxiang.databinding.ItemWorksLayoutBinding;
import com.yinxiang.view.OnClickListener;


public class WorkAdapter extends BaseRecyclerAdapter<String, ItemWorksLayoutBinding> {

    private boolean selection = false;

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setSelection(boolean selection) {
        this.selection = selection;
    }

    public WorkAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_works_layout;
    }

    @Override
    protected void onBindItem(ItemWorksLayoutBinding binding, final String str, final int position) {
        if (mList != null && mList.size() > 0) {
            GlideLoader.LoderRoundedImage(mContext, str, binding.cover, 10);
            GlideLoader.LoderCircleImage(mContext, str, binding.userIcon);
            if (selection) {
//                binding.cardView.setSelected(position != 0 ? false : true);
                binding.selectionView.setBackground(position != 0 ? mContext.getResources().getDrawable(R.drawable.button_white_t) : mContext.getResources().getDrawable(R.drawable.button_t));
            }
            binding.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(v, mList.get(position));
                    }
                }
            });
        }

    }
}
