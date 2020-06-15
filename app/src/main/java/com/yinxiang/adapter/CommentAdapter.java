package com.yinxiang.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.GlideLoader;
import com.yinxiang.R;
import com.yinxiang.activity.UserHomeActivity;
import com.yinxiang.databinding.ItemCommentLayoutBinding;
import com.yinxiang.model.MessageData;
import com.yinxiang.view.OnClickListener;


public class CommentAdapter extends BaseRecyclerAdapter<MessageData.DataBeanX.DataBean, ItemCommentLayoutBinding> {
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public CommentAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_comment_layout;
    }

    private static final String TAG = "CommentAdapter";
    @Override
    protected void onBindItem(final ItemCommentLayoutBinding binding, final MessageData.DataBeanX.DataBean dataBean, final int position) {
        if (mList != null && mList.size() > 0) {
            binding.tvTitle.setText(dataBean.getTourist().getName());
            binding.tvDesc.setText("评论了你的作品: " + dataBean.getBody());
            binding.tvTime.setText(dataBean.getUpdated_at());
            GlideLoader.LoderCircleImage(mContext, !CommonUtil.isBlank(dataBean.getTourist()) ? dataBean.getTourist().getAvatar() : "", binding.userIcon);
            GlideLoader.LoderImageUrl(mContext, !CommonUtil.isBlank(dataBean.getContent()) ? dataBean.getContent().getImg() : "", binding.cover, 2);
            binding.viewLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, UserHomeActivity.class);
                    intent.putExtra("uid", dataBean.getTourist().getId());
                    mContext.startActivity(intent);
                }
            });
        }

    }
}
