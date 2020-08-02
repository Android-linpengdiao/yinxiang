package com.yinxiang.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.yinxiang.R;
import com.yinxiang.view.MyImageAdapter;
import com.yinxiang.view.OnClickListener;

import java.util.List;

public class DialogUtil {

    private static DialogUtil instance;

    private DialogUtil() {

    }

    public static DialogUtil getInstance() {
        if (instance == null) {
            synchronized (DialogUtil.class) {
                if (instance == null) {
                    instance = new DialogUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 显示图片或者视频数据
     *
     * @param act       activity
     * @param imageList 图片数据
     */
    @SuppressLint("SetTextI18n")
    public void showMoreImageView(Activity act, List<String> imageList, final int current) {

        if (act != null) {

            final AlertDialog dialog = new AlertDialog.Builder(act, AlertDialog.THEME_HOLO_DARK).create();
            dialog.setCancelable(true);
            dialog.show();
            Window window = dialog.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            window.getDecorView().setBackgroundColor(act.getResources().getColor(R.color.transparent));
            window.setAttributes(lp);
            window.setContentView(R.layout.video_image_view);


            ImageView back = window.findViewById(R.id.back);
            final TextView nowNumTextView = window.findViewById(R.id.nowNumTextView);
            TextView allNumTextView = window.findViewById(R.id.allNumTextView);
            ViewPager viewPager = window.findViewById(R.id.viewPager);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });

            nowNumTextView.setText(String.valueOf(current+1));
            allNumTextView.setText("/"+imageList.size());

            MyImageAdapter adapter = new MyImageAdapter(imageList, act);
            viewPager.setAdapter(adapter);
            viewPager.setOffscreenPageLimit(1);
            viewPager.setCurrentItem(current);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int i) {
                    nowNumTextView.setText(String.valueOf(i+1));
                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });
            adapter.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view, Object object) {
                    dialog.cancel();
                }

                @Override
                public void onLongClick(View view, Object object) {

                }
            });

        }

    }


}
