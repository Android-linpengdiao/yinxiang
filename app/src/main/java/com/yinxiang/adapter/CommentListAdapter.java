package com.yinxiang.adapter;

import android.content.Context;
import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.GlideLoader;
import com.yinxiang.R;
import com.yinxiang.databinding.ItemCommentListLayoutBinding;
import com.yinxiang.model.CommentData;
import com.yinxiang.view.OnClickListener;


public class CommentListAdapter extends BaseRecyclerAdapter<CommentData.DataBeanX.DataBean, ItemCommentListLayoutBinding> {

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public CommentListAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_comment_list_layout;
    }

    @Override
    protected void onBindItem(final ItemCommentListLayoutBinding binding, final CommentData.DataBeanX.DataBean dataBean, final int position) {
        if (mList != null && mList.size() > 0) {
            binding.userName.setText(dataBean.getTourist().getName());
            binding.tvTime.setText(dataBean.getUpdated_at());
            binding.tvComment.setText(dataBean.getBody());
            GlideLoader.LoderCircleImage(mContext, dataBean.getTourist().getAvatar(), binding.userIcon);
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
