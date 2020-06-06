package com.yinxiang.adapter;

import android.content.Context;
import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.GlideLoader;
import com.yinxiang.R;
import com.yinxiang.databinding.ItemSelectionClubLayoutBinding;
import com.yinxiang.model.ClubData;
import com.yinxiang.view.OnClickListener;


public class SelectionClubAdapter extends BaseRecyclerAdapter<ClubData.DataBean, ItemSelectionClubLayoutBinding> {
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public SelectionClubAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_selection_club_layout;
    }

    @Override
    protected void onBindItem(final ItemSelectionClubLayoutBinding binding, final ClubData.DataBean dataBean, final int position) {
        if (mList != null && mList.size() > 0) {
            binding.clubName.setText(dataBean.getName());
            binding.selectionView.setText(dataBean.getSelected() == 0 ? "选择" : "已选择");
            binding.selectionView.setSelected(dataBean.getSelected() == 0 ? false : true);
            GlideLoader.LoderCircleImage(mContext, dataBean.getLogo(), binding.clubIcon);
            binding.selectionView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        dataBean.setSelected(dataBean.getSelected() == 0 ? 1 : 0);
                        binding.selectionView.setSelected(dataBean.getSelected() == 0 ? false : true);
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
            binding.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener!=null){
                        onClickListener.onClick(v,dataBean);
                    }
                }
            });
        }

    }
}
