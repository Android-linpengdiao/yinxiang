package com.yinxiang.adapter;

import android.content.Context;
import android.view.View;

import com.yinxiang.R;
import com.yinxiang.databinding.ItemCoinLayoutBinding;
import com.yinxiang.databinding.ItemWalletLayoutBinding;
import com.yinxiang.model.WalletSetData;
import com.yinxiang.view.OnClickListener;


public class CoinAdapter extends BaseRecyclerAdapter<WalletSetData.DataBean, ItemCoinLayoutBinding> {
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
    protected void onBindItem(final ItemCoinLayoutBinding binding, final WalletSetData.DataBean dataBean, final int position) {
        if (mList != null && mList.size() > 0) {
            binding.tvMoney.setText(dataBean.getMoney());
            binding.tvCoin.setText(dataBean.getWallet_token() + "金币");
            binding.viewLayout.setSelected(dataBean.getSelected() == 0 ? false : true);
            binding.viewLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        dataBean.setSelected(dataBean.getSelected() == 0 ? 1 : 0);
                        binding.viewLayout.setSelected(dataBean.getSelected() == 0 ? false : true);
                        for (int i = 0; i < mList.size(); i++) {
                            if (dataBean != mList.get(i)) {
                                mList.get(i).setSelected(0);
                            }
                        }
                        onClickListener.onClick(v, dataBean);
                        notifyDataSetChanged();
                    }
                }
            });
        }

    }
}
