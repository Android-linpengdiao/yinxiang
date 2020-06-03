package com.yinxiang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.yinxiang.R;
import com.yinxiang.activity.ClubMessageDetailActivity;
import com.yinxiang.activity.UserHomeActivity;
import com.yinxiang.databinding.ItemClubMessageLayoutBinding;
import com.yinxiang.databinding.ItemFriendsLayoutBinding;
import com.yinxiang.model.FollowUserData;
import com.yinxiang.view.OnClickListener;


public class ClubMessageAdapter extends BaseRecyclerAdapter<FollowUserData.DataBeanX.DataBean, ItemClubMessageLayoutBinding> {

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
    protected void onBindItem(final ItemClubMessageLayoutBinding binding, final FollowUserData.DataBeanX.DataBean dataBean, final int position) {
        if (mList != null && mList.size() > 0) {
//            binding.tvTitle.setText(dataBean.getName());
//            binding.tvDesc.setText("粉丝：" + dataBean.getLiker());
//            binding.tvFollowers.setText("已关注");
//            binding.tvFollowers.setText(dataBean.getAttention() != -1 ? "已关注" : "关注");
//            GlideLoader.LoderCircleImage(mContext, dataBean.getAvatar(), binding.userIcon);
//            binding.tvFollowers.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (onClickListener != null) {
//                        onClickListener.onClick(v, dataBean);
//                    }
//                }
//            });
            binding.viewLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ClubMessageDetailActivity.class);
//                    intent.putExtra("uid", dataBean.getId());
                    intent.putExtra("uid", 0);
                    mContext.startActivity(intent);
                }
            });
        }

    }
}
