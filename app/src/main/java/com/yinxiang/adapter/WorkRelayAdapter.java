package com.yinxiang.adapter;

import android.content.Context;
import android.view.View;

import com.baselibrary.utils.GlideLoader;
import com.yinxiang.R;
import com.yinxiang.databinding.ItemHomeContestLayoutBinding;
import com.yinxiang.databinding.ItemWorkRelayLayoutBinding;
import com.yinxiang.view.OnClickListener;


public class WorkRelayAdapter extends BaseRecyclerAdapter<String, ItemWorkRelayLayoutBinding> {
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public WorkRelayAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_work_relay_layout;
    }

    @Override
    protected void onBindItem(final ItemWorkRelayLayoutBinding binding, String dataBean, final int position) {
        if (mList != null && mList.size() > 0) {
            GlideLoader.LoderCircleImage(mContext, dataBean, binding.userIcon1);
            GlideLoader.LoderCircleImage(mContext, dataBean, binding.userIcon2);
            binding.rivalWorkView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener!=null){
                        onClickListener.onClick(v,position);
                    }
                }
            });binding.myWorkView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener!=null){
                        onClickListener.onClick(v,position);
                    }
                }
            });
        }

    }
}
