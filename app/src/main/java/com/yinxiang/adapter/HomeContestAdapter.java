package com.yinxiang.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.baselibrary.utils.GlideLoader;
import com.yinxiang.R;
import com.yinxiang.databinding.ItemHomeContestLayoutBinding;
import com.yinxiang.model.WorkPKData;
import com.yinxiang.view.OnClickListener;


public class HomeContestAdapter extends BaseRecyclerAdapter<WorkPKData.DataBeanX.DataBean, ItemHomeContestLayoutBinding> {
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
    protected void onBindItem(final ItemHomeContestLayoutBinding binding, WorkPKData.DataBeanX.DataBean dataBean, final int position) {
        if (mList != null && mList.size() > 0) {
            binding.activeName.setText(dataBean.getActive_name());
            binding.compareActiveName.setText(dataBean.getCompare_active_name());
            binding.voteNum.setText(dataBean.getVote_num() + "票");
            binding.compareNum.setText(dataBean.getCompare_num() + "票");
            GlideLoader.LoderCircleImage(mContext, dataBean.getContent_video(), binding.contentImg);
            GlideLoader.LoderCircleImage(mContext, dataBean.getCompare_video(), binding.compareImg);
            binding.rivalWorkView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(v, position);
                    }
                }
            });
            binding.myWorkView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(v, position);
                    }
                }
            });
        }

    }
}
