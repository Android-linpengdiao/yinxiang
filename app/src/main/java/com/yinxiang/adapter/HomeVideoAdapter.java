package com.yinxiang.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.baselibrary.utils.GlideLoader;
import com.yinxiang.R;
import com.yinxiang.databinding.ItemHomeVideoLayoutBinding;
import com.yinxiang.model.HomeVideos;
import com.yinxiang.view.OnClickListener;


public class HomeVideoAdapter extends BaseRecyclerAdapter<HomeVideos.DataBeanX.DataBean, ItemHomeVideoLayoutBinding> {
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public HomeVideoAdapter(Context context) {
        super(context);
    }

    public HomeVideos.DataBeanX.DataBean getItem(int position){
        if (mList != null && mList.size() > 0) {
            return mList.get(position);
        }else {
            return null;
        }
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_home_video_layout;
    }

    @Override
    protected void onBindItem(final ItemHomeVideoLayoutBinding binding, final HomeVideos.DataBeanX.DataBean dataBean, final int position) {
        if (mList != null && mList.size() > 0) {
            binding.tvFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.tvFollow.setSelected(!binding.tvFollow.isSelected());
                    binding.tvFollow.setText(binding.tvFollow.isSelected() ? "已关注" : "关注");
                }
            });
            binding.ivLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.ivLike.setSelected(!binding.ivLike.isSelected());
                }
            });
            binding.userIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(v, dataBean);
                    }
                }
            });
            binding.ivComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(v, dataBean);
                    }
                }
            });
            binding.ivShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(v, dataBean);
                    }
                }
            });
            binding.ivWorksPk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(v, dataBean);
                    }
                }
            });
            binding.ivRelay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(v, dataBean);
                    }
                }
            });
            binding.tvReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(v, dataBean);
                    }
                }
            });
            binding.tvElection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(v, dataBean);
                    }
                }
            });
            GlideLoader.LoderCircleImage(mContext, dataBean.getVideo(), binding.userIcon);
            GlideLoader.LoderVideoImage(mContext, dataBean.getVideo(), binding.imgThumb);
            GlideLoader.LoderVideoCenterCropImage(mContext, "", binding.background);
        }

    }
}
