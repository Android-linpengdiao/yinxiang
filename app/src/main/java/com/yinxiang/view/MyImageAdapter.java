package com.yinxiang.view;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.baselibrary.utils.GlideLoader;
import com.bumptech.glide.Glide;

import java.util.List;

public class MyImageAdapter extends PagerAdapter {
    private List<String> imageUrls;
    private Activity activity;

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public MyImageAdapter(List<String> imageUrls, Activity activity) {
        this.imageUrls = imageUrls;
        this.activity = activity;
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String url = imageUrls.get(position);
        ImageView photoView = new ImageView(activity);
        photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Glide.with(activity).load(GlideLoader.domain + url).into(photoView);
        container.addView(photoView);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onClick(view, null);
                }
            }
        });
        return photoView;
    }

    @Override
    public int getCount() {
        return imageUrls != null ? imageUrls.size() : 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
