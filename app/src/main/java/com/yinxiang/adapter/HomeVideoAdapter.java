package com.yinxiang.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.baselibrary.utils.GlideLoader;
import com.yinxiang.R;
import com.yinxiang.databinding.ItemHomeVideoLayoutBinding;
import com.yinxiang.view.OnClickListener;


public class HomeVideoAdapter extends BaseRecyclerAdapter<String, ItemHomeVideoLayoutBinding> {
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public HomeVideoAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_home_video_layout;
    }

    @Override
    protected void onBindItem(final ItemHomeVideoLayoutBinding binding, final String dataBean, final int position) {
        if (mList != null && mList.size() > 0) {
            binding.ivLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.ivLike.setSelected(!binding.ivLike.isSelected());
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
            GlideLoader.LoderCircleImage(mContext, dataBean, binding.userIcon);
            GlideLoader.LoderImage(mContext, dataBean, binding.imgThumb);
            String url1 = "http://api.lgdama.com:10001/storage/video/db236d54a02442ae9ba0d8c4911dba17.mp4";
            String url2 = "http://api.lgdama.com:10001/storage/video/dc0d36f301784ffd8896ce673f6e6ba1.mp4";
            binding.videoView.setVideoURI(Uri.parse(position % 2 == 0 ? url1 : url2));
        }

    }
}
