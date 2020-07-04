package com.yinxiang.view;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.baselibrary.utils.BasePopupWindow;
import com.baselibrary.utils.CommonUtil;
import com.yinxiang.R;
import com.yinxiang.adapter.TypeAdapter;
import com.yinxiang.model.HomeActives;

public class TypePopupWindow extends BasePopupWindow {

    private HomeActives homeActives;
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public TypePopupWindow(Context context) {
        super(context);
    }


    public void setHomeActives(HomeActives homeActives) {
        this.homeActives = homeActives;
        if (homeActives != null && homeActives.getCode() == 200 && homeActives.getData() != null) {
            adapter.refreshData(homeActives.getData());
        }
    }

    @Override
    protected int animationStyle() {
        return R.style.HomeTypePopupAnimation;
    }

    private TypeAdapter adapter;

    @Override
    protected View initContentView() {
        View contentView = LayoutInflater.from(context).inflate(R.layout.view_type_popup_layout, null, false);
        View viewLayout = contentView.findViewById(R.id.view_layout);
        RecyclerView recyclerView = contentView.findViewById(R.id.recyclerView);
        adapter = new TypeAdapter(context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
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