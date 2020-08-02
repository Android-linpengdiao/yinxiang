package com.yinxiang.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.yinxiang.view.LiveClickListener;
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
            binding.userName.setText(dataBean.getTourist().getName());
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = CommonUtil.getDuration(mContext, dataBean.getCreated_at(), df.format(new Date()));
            binding.tvTime.setText("发布于" + time);
            binding.tvName.setText(dataBean.getName());
            binding.tvFollow.setText(dataBean.isIs_person_follow() ? "已关注" : "关注");
            binding.tvLike.setSelected(dataBean.isIs_assist());
            binding.tvLike.setText(dataBean.getAssist_num() > 0 ? String.valueOf(dataBean.getAssist_num()) : "赞");
            binding.tvElection.setText(String.valueOf(dataBean.getPre_votes()));
            GlideLoader.LoderCircleImage(mContext, dataBean.getTourist().getAvatar(), binding.userIcon);
            GlideLoader.LoderVideoCenterCropImage(mContext, dataBean.getImg(), binding.imgThumb);
//            GlideLoader.LoderVideoCenterCropImage(mContext, "", binding.background);

            binding.tvFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getUserId(true) > 0) {
                        homePagePersonFollow(binding.tvFollow, dataBean);
                    }
                }
            });
            binding.tvLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getUserId(true) > 0) {
                        homePageVideosAssist(binding.tvLike, dataBean);
                    }
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
            binding.tvComment.setOnClickListener(new View.OnClickListener() {
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
            binding.videoView.setOnTouchListener(new LiveClickListener(new LiveClickListener.ClickCallBack() {
                @Override
                public void oneClick() {
                    if (onClickListener != null) {
                        onClickListener.onClick(binding.videoView, dataBean);
                    }
                }

                @Override
                public void doubleClick(int w, int y) {
                    if (getUserId() > 0) {
                        if (!binding.tvLike.isSelected()) {
                            homePageVideosAssist(binding.tvLike, dataBean);
                        }
                    }
                    int liveAnimateImgWidth = 180;
                    ImageView likeImg = new ImageView(mContext);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(CommonUtil.dip2px(mContext, liveAnimateImgWidth), CommonUtil.dip2px(mContext, liveAnimateImgWidth));
                    params.leftMargin = w - CommonUtil.dip2px(mContext, liveAnimateImgWidth) * 1 / 2;
                    params.topMargin = y - CommonUtil.dip2px(mContext, liveAnimateImgWidth) * 5 / 6;
                    likeImg.setPadding(10, 10, 10, 10);
                    likeImg.setLayoutParams(params);
                    likeImg.setImageResource(R.drawable.likefill_pre);
                    binding.liveAnimateView.addView(likeImg);
                    startAnimatorStyleOne(likeImg);
                }
            }));
        }

    }

    private void homePagePersonFollow(final TextView tvFollow, final HomeVideos.DataBeanX.DataBean dataBean) {
        String url = tvFollow.isSelected() ? APIUrls.url_homePagePersonUnFollow : APIUrls.url_homePagePersonFollow;
        SendRequest.homePagePersonFollow(MyApplication.getInstance().getUserInfo().getData().getId(), dataBean.getTourist_id(), url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    if (!CommonUtil.isBlank(response)) {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.optInt("code") == 200) {
                            if (jsonObject.optInt("code") == 200) {
                                tvFollow.setSelected(!tvFollow.isSelected());
                                tvFollow.setText(tvFollow.isSelected() ? "已关注" : "关注");
                            } else {
                                ToastUtils.showShort(mContext, jsonObject.optString("msg"));
                            }
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


    private void homePageVideosAssist(final TextView tvLike, final HomeVideos.DataBeanX.DataBean dataBean) {
        String url = tvLike.isSelected() ? APIUrls.url_homePageVideosCancelAssist : APIUrls.url_homePageVideosAssist;
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
                            dataBean.setAssist_num(tvLike.isSelected() ? dataBean.getAssist_num() - 1 : dataBean.getAssist_num() + 1);
                            tvLike.setText(dataBean.getAssist_num() > 0 ? String.valueOf(dataBean.getAssist_num()) : "赞");
                            tvLike.setSelected(!tvLike.isSelected());
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


    /**
     * AnimatorSet实现组合动画
     * AnimatorSet可以指定动画同时或按顺序执行
     */
    private void startAnimatorStyleOne(final ImageView liveAnimateImg) {
        //实现旋转动画
        ObjectAnimator rotationAnimaotr = ObjectAnimator.ofFloat(liveAnimateImg, "rotation", 60f, 0f, 0f);
        //缩放动画
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(liveAnimateImg, "scaleX", 1f, 0.5f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(liveAnimateImg, "scaleY", 1f, 0.5f);
        //透明度动画
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(liveAnimateImg, "alpha", 0.1f, 1f);
        //然后通过AnimatorSet把几种动画组合起来
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleXAnimator).with(scaleYAnimator)
                .with(alphaAnimator);
        //设置动画时间
        animatorSet.setDuration(100);
        //开始动画
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                liveAnimateImg.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        stopAnimatorStyleOne(liveAnimateImg);
                    }
                }, 500);
            }
        });
    }

    private void stopAnimatorStyleOne(final ImageView liveAnimateImg) {
        //缩放动画
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(liveAnimateImg, "scaleX", 0.5f, 1f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(liveAnimateImg, "scaleY", 0.5f, 1f);
        //透明度动画
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(liveAnimateImg, "alpha", 1f, 0.1f);
        //然后通过AnimatorSet把几种动画组合起来
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleXAnimator).with(scaleYAnimator)
                .with(alphaAnimator);
        //设置动画时间
        animatorSet.setDuration(300);
        //开始动画
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                liveAnimateImg.setVisibility(View.GONE);
            }
        });
    }
}
