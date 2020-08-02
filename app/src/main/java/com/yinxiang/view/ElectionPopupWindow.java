package com.yinxiang.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.baselibrary.utils.BasePopupWindow;
import com.baselibrary.utils.CommonUtil;
import com.yinxiang.R;

public class ElectionPopupWindow extends BasePopupWindow {

    private OnClickListener onClickListener;
    private String votes;
    private String wallet_token;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public ElectionPopupWindow(Context context) {
        super(context);
    }

    public void setWallet(String wallet_token, String votes) {
        this.votes = votes;
        this.wallet_token = wallet_token;
        tvElectionCoin.setText("投" + votes + "票");
        tvElectionVotes.setText("消耗" + wallet_token + "金币");
    }

    @Override
    protected int animationStyle() {
        return R.style.PopupAnimation;
    }

    private TextView tvElectionCoin;
    private TextView tvElectionVotes;
    @Override
    protected View initContentView() {
        View contentView = LayoutInflater.from(context).inflate(R.layout.view_election_popup_layout, null, false);
        View viewLayout = contentView.findViewById(R.id.view_layout);
        View tvElection = contentView.findViewById(R.id.tv_election);
         tvElectionCoin = contentView.findViewById(R.id.tv_election_coin);
         tvElectionVotes = contentView.findViewById(R.id.tv_election_coin_votes);
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