package com.yinxiang.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.baselibrary.utils.BasePopupWindow;
import com.baselibrary.utils.CommonUtil;
import com.yinxiang.R;

public class ElectionPopupWindow extends BasePopupWindow {

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public ElectionPopupWindow(Context context) {
        super(context);
    }

    @Override
    protected int animationStyle() {
        return R.style.PopupAnimation;
    }

    @Override
    protected View initContentView() {
        View contentView = LayoutInflater.from(context).inflate(R.layout.view_election_popup_layout, null, false);
        View viewLayout = contentView.findViewById(R.id.view_layout);
        View tvElection = contentView.findViewById(R.id.tv_election);
        View tvElectionCoin = contentView.findViewById(R.id.tv_election_coin);
        tvElection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (onClickListener != null) {
                    onClickListener.onClick(v, null);
                }
            }
        });
        tvElectionCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (onClickListener != null) {
                    onClickListener.onClick(v, null);
                }
            }
        });
        viewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
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