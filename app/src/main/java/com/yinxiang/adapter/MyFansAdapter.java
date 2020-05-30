package com.yinxiang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.baselibrary.utils.GlideLoader;
import com.yinxiang.R;
import com.yinxiang.activity.UserHomeActivity;
import com.yinxiang.databinding.ItemFriendsLayoutBinding;
import com.yinxiang.model.FansUserData;
import com.yinxiang.view.OnClickListener;


public class MyFansAdapter extends BaseRecyclerAdapter<FansUserData.DataBeanX.DataBean, ItemFriendsLayoutBinding> {

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public MyFansAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_friends_layout;
    }

    @Override
    protected void onBindItem(final ItemFriendsLayoutBinding binding, final FansUserData.DataBeanX.DataBean dataBean, final int position) {
        if (mList != null && mList.size() > 0) {
            binding.tvTitle.setText(dataBean.getName());
            binding.tvDesc.setText("粉丝：" + dataBean.getLiker());
            binding.tvFollowers.setText(dataBean.isAttention()?"已关注":"关注");
            GlideLoader.LoderCircleImage(mContext, dataBean.getAvatar(), binding.userIcon);
            binding.tvFollowers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(v, dataBean);
                    }
                }
            });
            binding.viewLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, UserHomeActivity.class);
                    intent.putExtra("uid", dataBean.getId());
                    intent.putExtra("isFollow", dataBean.isAttention());
                    mContext.startActivity(intent);
                }
            });
        }

    }
}
