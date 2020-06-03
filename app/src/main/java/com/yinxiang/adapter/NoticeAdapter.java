package com.yinxiang.adapter;

import android.content.Context;
import android.view.View;

import com.yinxiang.R;
import com.yinxiang.databinding.ItemNoticeLayoutBinding;
import com.yinxiang.model.NoticeData;
import com.yinxiang.view.OnClickListener;


public class NoticeAdapter extends BaseRecyclerAdapter<NoticeData.DataBean, ItemNoticeLayoutBinding> {

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public NoticeAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_notice_layout;
    }

    @Override
    protected void onBindItem(final ItemNoticeLayoutBinding binding, final NoticeData.DataBean dataBean, final int position) {
        if (mList != null && mList.size() > 0) {
            binding.tvTitle.setText(dataBean.getTitle());
            binding.tvDesc.setText(dataBean.getDesc());
            binding.tvTime.setText(dataBean.getUpdated_at());
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
