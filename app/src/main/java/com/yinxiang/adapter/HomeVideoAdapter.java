package com.yinxiang.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.GlideLoader;
import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.StringCallback;
import com.okhttp.utils.APIUrls;
import com.yinxiang.MyApplication;
import com.yinxiang.R;
import com.yinxiang.activity.MyFollowActivity;
import com.yinxiang.databinding.ItemHomeVideoLayoutBinding;
import com.yinxiang.model.HomeVideos;
import com.yinxiang.view.OnClickListener;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;


public class HomeVideoAdapter extends BaseRecyclerAdapter<HomeVideos.DataBeanX.DataBean, ItemHomeVideoLayoutBinding> {
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public HomeVideoAdapter(Context context) {
        super(context);
    }

    public HomeVideos.DataBeanX.DataBean getItem(int position) {
        if (mList != null && mList.size() > 0) {
            return mList.get(position);
        } else {
            return null;
        }
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_home_video_layout;
    }

    @Override
    protected void onBindItem(final ItemHomeVideoLayoutBinding binding, final HomeVideos.DataBeanX.DataBean dataBean, final int position) {
        if (mList != null && mList.size() > 0) {
            binding.userName.setText(dataBean.getTourist_name() + "");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = CommonUtil.getDuration(mContext, dataBean.getCreated_at(), df.format(new Date()));
            binding.tvTime.setText("发布于" + time);
            GlideLoader.LoderCircleImage(mContext, dataBean.getVideo(), binding.userIcon);
            GlideLoader.LoderVideoImage(mContext, dataBean.getVideo(), binding.imgThumb);
            GlideLoader.LoderVideoCenterCropImage(mContext, "", binding.background);

            binding.tvFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    homePagePersonFollow(binding.tvFollow, dataBean);
                }
            });
            binding.ivLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    homePageVideosAssist(binding.ivLike, dataBean);
                }
            });
            binding.userIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(v, dataBean);
                    }
                }
            });
            binding.ivComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(v, dataBean);
                    }
                }
            });
            binding.ivShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(v, dataBean);
                    }
                }
            });
            binding.ivWorksPk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(v, dataBean);
                    }
                }
            });
            binding.ivRelay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(v, dataBean);
                    }
                }
            });
            binding.tvReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(v, dataBean);
                    }
                }
            });
            binding.tvElection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(v, dataBean);
                    }
                }
            });
        }

    }

    private void homePagePersonFollow(TextView tvFollow, final HomeVideos.DataBeanX.DataBean dataBean) {
        SendRequest.homePagePersonFollow(MyApplication.getInstance().getUserInfo().getData().getId(), dataBean.getTourist_id(), APIUrls.url_homePagePersonFollow, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    if (!CommonUtil.isBlank(response)) {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.optInt("code") == 200) {
//                            dataBean.setAttention(dataBean.getAttention() != -1 ? -1 : 0);
//                            if (dataBean.getAttention() != -1) {
//                                ToastUtils.showShort(MyFollowActivity.this, "已关注");
//                            }
//                            adapter.notifyItemChanged(followUserData.getData().getData().indexOf(dataBean));
                        } else {
                            ToastUtils.showShort(mContext, jsonObject.optString("msg"));
                        }
                    } else {
                        ToastUtils.showShort(mContext, "请求失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShort(mContext, "请求失败");
                }

            }
        });
    }


    private void homePageVideosAssist(final ImageView ivLike, final HomeVideos.DataBeanX.DataBean dataBean) {
        String url = ivLike.isSelected() ? APIUrls.url_homePageVideosCancelAssist : APIUrls.url_homePageVideosAssist;
        SendRequest.homePageVideosAssist(MyApplication.getInstance().getUserInfo().getData().getId(), dataBean.getId(), url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    if (!CommonUtil.isBlank(response)) {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.optInt("code") == 200) {
                            ivLike.setSelected(!ivLike.isSelected());
                        } else {
                            ToastUtils.showShort(mContext, jsonObject.optString("msg"));
                        }
                    } else {
                        ToastUtils.showShort(mContext, "请求失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShort(mContext, "请求失败");
                }

            }
        });
    }
}
