package com.yinxiang.adapter;

import android.content.Context;

import com.yinxiang.R;
import com.yinxiang.databinding.ItemWalletLayoutBinding;
import com.yinxiang.model.WalletRecordData;
import com.yinxiang.view.OnClickListener;


public class WalletAdapter extends BaseRecyclerAdapter<WalletRecordData, ItemWalletLayoutBinding> {
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public WalletAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_wallet_layout;
    }

    @Override
    protected void onBindItem(final ItemWalletLayoutBinding binding, final WalletRecordData dataBean, final int position) {
        if (mList != null && mList.size() > 0) {

        }

    }
}
