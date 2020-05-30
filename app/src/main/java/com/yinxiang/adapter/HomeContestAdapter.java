package com.yinxiang.adapter;

import android.content.Context;
import android.net.Uri;

import com.baselibrary.utils.GlideLoader;
import com.yinxiang.R;
import com.yinxiang.databinding.ItemHomeContestLayoutBinding;
import com.yinxiang.view.OnClickListener;


public class HomeContestAdapter extends BaseRecyclerAdapter<String, ItemHomeContestLayoutBinding> {
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public HomeContestAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_home_contest_layout;
    }

    @Override
    protected void onBindItem(final ItemHomeContestLayoutBinding binding, String dataBean, final int position) {
        if (mList != null && mList.size() > 0) {
            GlideLoader.LoderCircleImage(mContext, dataBean, binding.userIcon1);
            GlideLoader.LoderCircleImage(mContext, dataBean, binding.userIcon2);
        }

    }
}
