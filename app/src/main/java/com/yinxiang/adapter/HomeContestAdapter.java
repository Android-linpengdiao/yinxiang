package com.yinxiang.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.Chronometer;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.GlideLoader;
import com.yinxiang.R;
import com.yinxiang.databinding.ItemHomeContestLayoutBinding;
import com.yinxiang.model.WorkPKData;
import com.yinxiang.view.Anticlockwise;
import com.yinxiang.view.OnClickListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class HomeContestAdapter extends BaseRecyclerAdapter<WorkPKData.DataBeanX.DataBean, ItemHomeContestLayoutBinding> {
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public HomeContestAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_home_contest_layout;
    }

    @Override
    protected void onBindItem(final ItemHomeContestLayoutBinding binding, final WorkPKData.DataBeanX.DataBean dataBean, final int position) {
        if (mList != null && mList.size() > 0) {
            binding.workName.setText(dataBean.getContent_name());
            binding.compareWorkName.setText(dataBean.getCompare_name());
            binding.voteNum.setText(dataBean.getVote_num() + "票");
            binding.compareVoteNum.setText(dataBean.getCompare_num() + "票");
            binding.compareVoteNum.setText(dataBean.getCompare_num() + "票");

            long time = CommonUtil.getStringToDate(dataBean.getEnded_at()) - System.currentTimeMillis();
            binding.pkTime.initTime(60);
//            binding.pkTime.setText(CommonUtil.getPKTime(String.valueOf(time)));
            binding.pkTime.reStart();
            GlideLoader.LoderCircleImage(mContext, dataBean.getContent_img(), binding.workImg);
            GlideLoader.LoderCircleImage(mContext, dataBean.getCompare_img(), binding.compareWorkImg);
            binding.workView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(v, dataBean);
                    }
                }
            });
            binding.compareWorkView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(v, dataBean);
                    }
                }
            });
            binding.workVote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(v, dataBean);
                    }
                }
            });
            binding.compareWorkVote.setOnClickListener(new View.OnClickListener() {
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
