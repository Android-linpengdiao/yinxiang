package com.yinxiang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.baselibrary.utils.GlideLoader;
import com.yinxiang.R;
import com.yinxiang.activity.WorkDetailActivity;
import com.yinxiang.databinding.ItemUserHomeWorkLayoutBinding;

public class UserHomeWorkAdapter extends BaseRecyclerAdapter<String, ItemUserHomeWorkLayoutBinding> {

    public UserHomeWorkAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_user_home_work_layout;
    }

    @Override
    protected void onBindItem(final ItemUserHomeWorkLayoutBinding binding, final String dataBean, final int position) {
        if (mList != null && mList.size() > 0) {
            GlideLoader.LoderLoadImage(mContext, dataBean, binding.cover, 10);
            binding.viewLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, WorkDetailActivity.class);
//                    intent.putExtra("id", dataBean.getId());
                    mContext.startActivity(intent);
                }
            });
        }

    }
}
