package com.yinxiang.adapter;

import android.content.Context;
import android.view.View;

import com.baselibrary.utils.GlideLoader;
import com.yinxiang.R;
import com.yinxiang.databinding.ItemContactsLayoutBinding;
import com.yinxiang.model.FansUserData;
import com.yinxiang.model.FollowUserData;
import com.yinxiang.view.OnClickListener;


public class ContactsFansAdapter extends BaseRecyclerAdapter<FansUserData.DataBeanX.DataBean, ItemContactsLayoutBinding> {

    private boolean selection = false;

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setSelection(boolean selection) {
        this.selection = selection;
    }

    public ContactsFansAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_contacts_layout;
    }

    @Override
    protected void onBindItem(ItemContactsLayoutBinding binding, final FansUserData.DataBeanX.DataBean dataBean, final int position) {
        if (mList != null && mList.size() > 0) {
            binding.userName.setText(dataBean.getName());
            GlideLoader.LoderCircleImage(mContext, dataBean.getAvatar(), binding.userIcon);
            binding.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(v, mList.get(position));
                    }
                }
            });
        }

    }
}
