package com.yinxiang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.baselibrary.utils.GlideLoader;
import com.yinxiang.R;
import com.yinxiang.activity.ClubMessageDetailActivity;
import com.yinxiang.databinding.ItemClubMessageLayoutBinding;
import com.yinxiang.model.ClubMessageData;
import com.yinxiang.view.OnClickListener;


public class ClubMessageAdapter extends BaseRecyclerAdapter<ClubMessageData.DataBeanX.DataBean, ItemClubMessageLayoutBinding> {

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public ClubMessageAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_club_message_layout;
    }

    @Override
    protected void onBindItem(final ItemClubMessageLayoutBinding binding, final ClubMessageData.DataBeanX.DataBean dataBean, final int position) {
        if (mList != null && mList.size() > 0) {
            if (dataBean.getClub()!=null) {
                binding.tvDesc.setText("申请加入" + dataBean.getClub().getName());
            }
            binding.tvAgree.setText("同意");
            binding.tvAgree.setText(dataBean.getStatus() == 1 ? "已同意" : dataBean.getStatus() == 1 ? "已拒绝" : "同意");
            if (dataBean.getTourist()!=null) {
                binding.tvTitle.setText(dataBean.getTourist().getName());
                GlideLoader.LoderCircleImage(mContext, dataBean.getTourist().getAvatar(), binding.userIcon);
            }
            binding.viewLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ClubMessageDetailActivity.class);
                    intent.putExtra("dataBean", dataBean);
                    mContext.startActivity(intent);
                }
            });
        }

    }
}
