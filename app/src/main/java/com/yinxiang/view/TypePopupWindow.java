package com.yinxiang.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.baselibrary.utils.BasePopupWindow;
import com.baselibrary.utils.CommonUtil;
import com.yinxiang.R;
import com.yinxiang.adapter.TypeAdapter;
import com.yinxiang.model.NavData;

import java.util.ArrayList;
import java.util.List;

public class TypePopupWindow extends BasePopupWindow {

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public TypePopupWindow(Context context) {
        super(context);
    }

    @Override
    protected int animationStyle() {
        return R.style.HomeTypePopupAnimation;
    }

    @Override
    protected View initContentView() {
        View contentView = LayoutInflater.from(context).inflate(R.layout.view_type_popup_layout, null, false);
        View viewLayout = contentView.findViewById(R.id.view_layout);
        RecyclerView recyclerView = contentView.findViewById(R.id.recyclerView);
        TypeAdapter adapter = new TypeAdapter(context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        NavData navData = new NavData();
        List<NavData.DataBean> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            NavData.DataBean dataBean = new NavData.DataBean();
            dataBean.setName("音乐活动" + i);
            dataBean.setStatus(i == 0 ? 1 : 0);
            list.add(dataBean);
        }
        navData.setData(list);
        adapter.refreshData(navData.getData());
        adapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                if (onClickListener != null)
                    onClickListener.onClick(view, object);
                dismiss();
            }

            @Override
            public void onLongClick(View view, Object object) {

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