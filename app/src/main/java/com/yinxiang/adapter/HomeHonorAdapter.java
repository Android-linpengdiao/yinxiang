package com.yinxiang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.baselibrary.utils.GlideLoader;
import com.yinxiang.R;
import com.yinxiang.activity.UserHomeActivity;
import com.yinxiang.databinding.ItemHomeHonorLayoutBinding;
import com.yinxiang.model.HonourData;
import com.yinxiang.view.OnClickListener;


public class HomeHonorAdapter extends BaseRecyclerAdapter<HonourData.DataBeanX.DataBean, ItemHomeHonorLayoutBinding> {
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
    protected void onBindItem(final ItemHomeHonorLayoutBinding binding, HonourData.DataBeanX.DataBean dataBean, final int position) {
        if (mList != null && mList.size() > 0) {
//            GlideLoader.LoderCircleImage(mContext, dataBean, binding.userIcon);
            binding.tvRank.setText("");
            if (position == 0){
                binding.tvRank.setBackground(mContext.getResources().getDrawable(R.drawable.icon_num01));
            }else if (position == 1){
                binding.tvRank.setBackground(mContext.getResources().getDrawable(R.drawable.icon_num02));
            }else if (position == 2){
                binding.tvRank.setBackground(mContext.getResources().getDrawable(R.drawable.icon_num03));
            }else {
                binding.tvRank.setBackground(mContext.getResources().getDrawable(R.drawable.transparent));
                binding.tvRank.setText("No."+(position+1));
            }
            binding.tvFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.tvFollow.setSelected(!binding.tvFollow.isSelected());
                    binding.tvFollow.setText(binding.tvFollow.isSelected()?"已关注":"关注");
                }
            });
            binding.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, UserHomeActivity.class);
//                    intent.putExtra("uid", dataBean.getId());
                    intent.putExtra("uid", 0);
                    intent.putExtra("isFollow", false);
                    mContext.startActivity(intent);
                }
            });
        }

    }
}
