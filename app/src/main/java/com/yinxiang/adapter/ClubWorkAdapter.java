package com.yinxiang.adapter;

import android.content.Context;
import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.GlideLoader;
import com.yinxiang.R;
import com.yinxiang.databinding.ItemWorksLayoutBinding;
import com.yinxiang.model.ClubWorkData;
import com.yinxiang.model.WorkData;
import com.yinxiang.view.OnClickListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ClubWorkAdapter extends BaseRecyclerAdapter<ClubWorkData.DataBean, ItemWorksLayoutBinding> {

    private boolean selection = false;

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setSelection(boolean selection) {
        this.selection = selection;
    }

    public ClubWorkAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_works_layout;
    }

    @Override
    protected void onBindItem(ItemWorksLayoutBinding binding, final ClubWorkData.DataBean dataBean, final int position) {
        if (mList != null && mList.size() > 0) {
            binding.workName.setText(dataBean.getName());
//            binding.userName.setText(dataBean.getTourist().getName());
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = CommonUtil.getDuration(mContext, dataBean.getCreated_at(), df.format(new Date()));
            binding.tvTime.setText(time);
            GlideLoader.LoderRoundedImage(mContext, dataBean.getImg(), binding.cover, 10);
//            GlideLoader.LoderCircleImage(mContext, dataBean.getTourist().getAvatar(), binding.userIcon);
            if (selection) {
                binding.selectionView.setBackground(position != 0 ? mContext.getResources().getDrawable(R.drawable.button_white_t) : mContext.getResources().getDrawable(R.drawable.button_t));
                binding.selectionView.setEnabled(position != 0 ? false : true);
            }
            binding.selectionView.setOnClickListener(new View.OnClickListener() {
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
