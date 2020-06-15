package com.yinxiang.adapter;

import android.content.Context;
import android.view.View;

import com.baselibrary.utils.GlideLoader;
import com.yinxiang.R;
import com.yinxiang.databinding.ItemHomeContestLayoutBinding;
import com.yinxiang.databinding.ItemWorkRelayLayoutBinding;
import com.yinxiang.model.WorkRelayData;
import com.yinxiang.view.OnClickListener;


public class WorkRelayAdapter extends BaseRecyclerAdapter<WorkRelayData.DataBeanX.DataBean, ItemWorkRelayLayoutBinding> {
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
    protected void onBindItem(final ItemWorkRelayLayoutBinding binding, final WorkRelayData.DataBeanX.DataBean dataBean, final int position) {
        if (mList != null && mList.size() > 0) {
            binding.followerName.setText(dataBean.getFollower().getName());
            binding.followableName.setText(dataBean.getFollowable().getName());
            GlideLoader.LoderCircleImageUrl(mContext, dataBean.getFollower().getImg(), binding.followerImg);
            GlideLoader.LoderCircleImageUrl(mContext, dataBean.getFollowable().getImg(), binding.followableImg);
            binding.followerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(v, dataBean.getFollower());
                    }
                }
            });
            binding.followableView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(v, dataBean.getFollowable());
                    }
                }
            });
        }

    }
}
