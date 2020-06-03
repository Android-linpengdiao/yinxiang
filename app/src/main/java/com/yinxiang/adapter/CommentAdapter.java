package com.yinxiang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.baselibrary.utils.GlideLoader;
import com.yinxiang.R;
import com.yinxiang.activity.UserHomeActivity;
import com.yinxiang.databinding.ItemCommentLayoutBinding;
import com.yinxiang.model.MessageData;
import com.yinxiang.view.OnClickListener;


public class CommentAdapter extends BaseRecyclerAdapter<MessageData.DataBean, ItemCommentLayoutBinding> {
    private OnClickListener onClickListener;

    private int type = 0;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public CommentAdapter(Context context) {
        super(context);
    }

    public CommentAdapter(Context context, int type) {
        super(context);
        this.type = type;
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_comment_layout;
    }

    @Override
    protected void onBindItem(final ItemCommentLayoutBinding binding, final MessageData.DataBean dataBean, final int position) {
        if (mList != null && mList.size() > 0) {
            binding.cover.setVisibility(type == 2 ? View.GONE : View.VISIBLE);
            binding.tvTitle.setText(dataBean.getTourist().getName());
            if (type == 1) {
                binding.tvDesc.setText("赞了你视频");
                binding.tvTime.setText(dataBean.getUpdated_at());
            } else if (type == 2) {
                binding.tvDesc.setText("关注了你");
                binding.tvTime.setText(dataBean.getUpdated_at());
            } else if (type == 3) {
                binding.tvDesc.setText(dataBean.getBody());
                binding.tvTime.setText("评论了你的作品" + dataBean.getUpdated_at());
            }
            GlideLoader.LoderImage(mContext, dataBean.getTourist().getAvatar(), binding.userIcon, 100);
            GlideLoader.LoderImage(mContext, dataBean.getContents().getImg(), binding.cover, 2);
            binding.viewLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, UserHomeActivity.class);
                    intent.putExtra("uid", dataBean.getTourist().getId());
                    intent.putExtra("isFollow", false);
                    mContext.startActivity(intent);
                }
            });
        }

    }
}
