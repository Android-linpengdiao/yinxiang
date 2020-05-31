package com.yinxiang.adapter;

import android.content.Context;

import com.yinxiang.R;
import com.yinxiang.databinding.ItemCoinLayoutBinding;
import com.yinxiang.databinding.ItemWalletLayoutBinding;
import com.yinxiang.view.OnClickListener;


public class CoinAdapter extends BaseRecyclerAdapter<String, ItemCoinLayoutBinding> {
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public CoinAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_coin_layout;
    }

    @Override
    protected void onBindItem(final ItemCoinLayoutBinding binding, final String dataBean, final int position) {
        if (mList != null && mList.size() > 0) {

        }

    }
}
