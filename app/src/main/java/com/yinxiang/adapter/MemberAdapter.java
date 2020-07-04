package com.yinxiang.adapter;

import android.content.Context;
import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.GlideLoader;
import com.yinxiang.R;
import com.yinxiang.databinding.ItemMemberLayoutBinding;
import com.yinxiang.model.ClubMember;
import com.yinxiang.view.OnClickListener;


public class MemberAdapter extends BaseRecyclerAdapter<ClubMember.DataBeanX.DataBean, ItemMemberLayoutBinding> {
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public MemberAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_member_layout;
    }

    @Override
    protected void onBindItem(final ItemMemberLayoutBinding binding, final ClubMember.DataBeanX.DataBean dataBean, final int position) {
        if (mList != null && mList.size() > 0) {
            GlideLoader.LoderCircleImage(mContext, !CommonUtil.isBlank(dataBean.getTourist())?dataBean.getTourist().getAvatar():"", binding.icon);
            binding.viewLayout.setOnClickListener(new View.OnClickListener() {
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
