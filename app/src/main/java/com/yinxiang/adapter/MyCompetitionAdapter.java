package com.yinxiang.adapter;

import android.content.Context;
import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.GlideLoader;
import com.yinxiang.R;
import com.yinxiang.databinding.ItemMyCompetitionLayoutBinding;
import com.yinxiang.model.ActivityData;
import com.yinxiang.view.OnClickListener;


public class MyCompetitionAdapter extends BaseRecyclerAdapter<ActivityData.DataBeanX.DataBean, ItemMyCompetitionLayoutBinding> {

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public MyCompetitionAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_my_competition_layout;
    }

    @Override
    protected void onBindItem(ItemMyCompetitionLayoutBinding binding, final ActivityData.DataBeanX.DataBean dataBean, final int position) {
        if (mList != null && mList.size() > 0) {
            binding.tvName.setText(dataBean.getName());
            binding.tvTime.setText(dataBean.getUpdated_at());
            binding.tvLevel.setText("No." + (position + 1));
            binding.tvStatus.setText(dataBean.getStatus() == 1 ? "初赛进行中" : dataBean.getStatus() == 2 ? "复赛进行中" : "决赛进行中");
            binding.tvVotes.setText((dataBean.getStatus() == 1 ? dataBean.getPre_votes() : dataBean.getStatus() == 2 ? dataBean.getFinal_votes() : dataBean.getRematch_votes()) + "票");
            GlideLoader.LoderRoundedImage(mContext, dataBean.getImg(), binding.cover, 10);
            binding.rootView.setOnClickListener(new View.OnClickListener() {
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
