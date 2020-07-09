package com.yinxiang.adapter;

import android.content.Context;

import com.baselibrary.utils.CommonUtil;
import com.yinxiang.R;
import com.yinxiang.databinding.ItemWalletLayoutBinding;
import com.yinxiang.model.WalletRecordData;
import com.yinxiang.view.OnClickListener;


public class WalletAdapter extends BaseRecyclerAdapter<WalletRecordData.DataBeanX.RecordBean.DataBean, ItemWalletLayoutBinding> {
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
    protected void onBindItem(final ItemWalletLayoutBinding binding, final WalletRecordData.DataBeanX.RecordBean.DataBean dataBean, final int position) {
        if (mList != null && mList.size() > 0) {
            binding.tvTitle.setText(CommonUtil.getWalletRecordType(dataBean.getType()));
            binding.tvCoin.setText(dataBean.getTrans_type() == 1 ? "-" + dataBean.getWallet_record() : "+" + dataBean.getWallet_record());
            binding.tvTime.setText(dataBean.getCreated_at());
        }

    }
}
