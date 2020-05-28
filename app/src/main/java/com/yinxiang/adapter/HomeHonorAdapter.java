package com.yinxiang.adapter;

import android.content.Context;

import com.baselibrary.utils.GlideLoader;
import com.yinxiang.R;
import com.yinxiang.databinding.ItemHomeContestLayoutBinding;
import com.yinxiang.databinding.ItemHomeHonorLayoutBinding;
import com.yinxiang.view.OnClickListener;


public class HomeHonorAdapter extends BaseRecyclerAdapter<String, ItemHomeHonorLayoutBinding> {
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public HomeHonorAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_home_honor_layout;
    }

    @Override
    protected void onBindItem(final ItemHomeHonorLayoutBinding binding, String dataBean, final int position) {
        if (mList != null && mList.size() > 0) {
            GlideLoader.LoderImage(mContext, dataBean, binding.userIcon,100);
        }

    }
}
