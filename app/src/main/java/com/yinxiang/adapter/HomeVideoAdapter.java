package com.yinxiang.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import com.baselibrary.utils.GlideLoader;
import com.yinxiang.R;
import com.yinxiang.databinding.ItemVideoLayoutBinding;
import com.yinxiang.view.OnClickListener;


public class HomeVideoAdapter extends BaseRecyclerAdapter<String, ItemVideoLayoutBinding> {
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public HomeVideoAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_video_layout;
    }

    @Override
    protected void onBindItem(final ItemVideoLayoutBinding binding, String dataBean, final int position) {
        if (mList != null && mList.size() > 0) {
            GlideLoader.LoderImage(mContext, dataBean, binding.imgThumb);
            binding.videoView.setVideoURI(Uri.parse("http://api.lgdama.com:10001/storage/video/d1d4437dc1644a5daaab2727249af25d.mp4"));
        }

    }
}
