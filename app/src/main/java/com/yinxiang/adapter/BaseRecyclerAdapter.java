package com.yinxiang.adapter;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baselibrary.Constants;
import com.baselibrary.UserInfo;
import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.MsgCache;
import com.yinxiang.activity.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerAdapter<T, B extends ViewDataBinding> extends RecyclerView.Adapter {

    protected Context mContext;
    protected List<T> mList = new ArrayList<>();

    public BaseRecyclerAdapter(Context context) {
        this.mContext = context;
    }

    public BaseRecyclerAdapter(Context context, List<T> list) {
        this.mContext = context;
        this.mList = list;
    }

    public List<T> getList() {
        return mList;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void refreshData(List<T> list) {
        if (null != list) {
            this.mList = list;
            notifyDataSetChanged();
        }
    }

    public void loadMoreData(List<T> list) {
        if (null != list) {
            this.mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        B bing = DataBindingUtil.inflate(LayoutInflater.from(mContext), getLayoutResId(viewType), parent, false);
        return new RecyclerHolder(bing.getRoot());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        B binding = DataBindingUtil.getBinding(holder.itemView);
        final T t = mList.get(position);
        onBindItem(binding, t, position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    protected abstract @LayoutRes
    int getLayoutResId(int viewType);

    protected abstract void onBindItem(B binding, T t, int position);

    static class RecyclerHolder extends RecyclerView.ViewHolder {
        public RecyclerHolder(View itemView) {
            super(itemView);
        }
    }

    public void setUserInfo(UserInfo userInfo) {
        MsgCache.get(mContext).put(Constants.USER_INFO, userInfo);
    }

    public UserInfo getUserInfo() {
        UserInfo userinfo = (UserInfo) MsgCache.get(mContext).getAsObject(Constants.USER_INFO);
        if (!CommonUtil.isBlank(userinfo)) {
            return userinfo;
        }
        return new UserInfo();
    }

    public int getUserId() {
        return getUserId(false);
    }

    public int getUserId(boolean login) {
        if (getUserInfo().getData() != null) {
            return getUserInfo().getData().getId();
        } else {
            if (login) {
                Intent intent = new Intent(mContext, LoginActivity.class);
                mContext.startActivity(intent);
            }
            return 0;
        }
    }

}