package com.yinxiang.adapter;

import android.content.Context;
import android.view.View;

import com.baselibrary.utils.GlideLoader;
import com.yinxiang.R;
import com.yinxiang.databinding.ItemClubLayoutBinding;
import com.yinxiang.databinding.ItemCoinLayoutBinding;
import com.yinxiang.model.ClubData;
import com.yinxiang.view.OnClickListener;


public class ClubAdapter extends BaseRecyclerAdapter<ClubData.DataBean, ItemClubLayoutBinding> {
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public ClubAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_club_layout;
    }

    @Override
    protected void onBindItem(final ItemClubLayoutBinding binding, final ClubData.DataBean dataBean, final int position) {
        if (mList != null && mList.size() > 0) {
            binding.name.setText(dataBean.getName());
            GlideLoader.LoderCircleImage(mContext, dataBean.getLogo(), binding.icon);
            binding.viewLayout.setOnClickListener(new View.OnClickListener() {
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
