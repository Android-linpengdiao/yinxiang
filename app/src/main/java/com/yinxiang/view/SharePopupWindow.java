package com.yinxiang.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.baselibrary.utils.BasePopupWindow;
import com.baselibrary.utils.CommonUtil;
import com.yinxiang.R;

public class SharePopupWindow extends BasePopupWindow {

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public SharePopupWindow(Context context) {
        super(context);
    }

    @Override
    protected int animationStyle() {
        return R.style.PopupAnimation;
    }


    @Override
    protected View initContentView() {
        View contentView = LayoutInflater.from(context).inflate(R.layout.view_share_popup_layout, null, false);
        View viewLayout = contentView.findViewById(R.id.view_layout);
        View shareWx = contentView.findViewById(R.id.shareWx);
        View shareWxMoment = contentView.findViewById(R.id.shareWxMoment);
        View shareQQ = contentView.findViewById(R.id.shareQQ);
        View shareWeibo = contentView.findViewById(R.id.shareWeibo);

        viewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        shareWx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onClickListener.onClick(v, null);
            }
        });
        shareWxMoment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onClickListener.onClick(v, null);
            }
        });
        shareQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onClickListener.onClick(v, null);
            }
        });
        shareWeibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onClickListener.onClick(v, null);
            }
        });

        return contentView;
    }

    @Override
    protected int initWidth() {
        return CommonUtil.getScreenWidth(context);
    }

    @Override
    protected int initHeight() {
        return -1;
    }
}