package com.yinxiang.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;

import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alivc.player.AliVcMediaPlayer;
import com.alivc.player.MediaPlayer;
import com.baselibrary.manager.DialogManager;
import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.GlideLoader;
import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.callbacks.StringCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.okhttp.utils.APIUrls;
import com.yinxiang.MyApplication;
import com.yinxiang.R;
import com.yinxiang.databinding.ActivityWorkDetailBinding;
import com.yinxiang.model.CommentData;
import com.yinxiang.model.WorksDetail;
import com.yinxiang.view.CommentListPopupWindow;
import com.yinxiang.view.ElectionPopupWindow;
import com.yinxiang.view.LiveClickListener;
import com.yinxiang.view.OnClickListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;

public class WorkDetailActivity extends BaseActivity implements View.OnClickListener {

    private ActivityWorkDetailBinding binding;
    private int workId;
    private WorksDetail worksDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_work_detail);
        setStatusBarHeight();
        binding.playerBack.setOnClickListener(this);
        binding.deleteView.setOnClickListener(this);
        binding.ivWorksTuiguan.setOnClickListener(this);
        binding.ivWorksPk.setOnClickListener(this);
        binding.ivRelay.setOnClickListener(this);
        binding.tvLike.setOnClickListener(this);
        binding.tvComment.setOnClickListener(this);
        binding.ivShare.setOnClickListener(this);
        binding.tvElection.setOnClickListener(this);
        binding.userIcon.setOnClickListener(this);
        binding.tvFollow.setOnClickListener(this);
        binding.tvReport.setOnClickListener(this);
        initData();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        initData();

    }

    @Override
    public void onClick(View v) {
        Bundle bundle;
        switch (v.getId()) {
            case R.id.delete_view:
                if (worksDetail != null) {
                    DialogManager.showConfirmDialog(WorkDetailActivity.this, "确定删除该作品", new DialogManager.Listener() {
                        @Override
                        public void onItemLeft() {

                        }

                        @Override
                        public void onItemRight() {
                            delWorks();
                        }
                    });
                }
                break;
            case R.id.iv_works_tuiguan:
                bundle = new Bundle();
                bundle.putInt("videoId", workId);
                openActivity(MyWorkTuiguangActivity.class, bundle);
                break;
            case R.id.iv_works_pk:
                if (worksDetail != null) {
                    bundle = new Bundle();
                    bundle.putInt("videoId", workId);
                    bundle.putInt("activeId", worksDetail.getData().getActive_id());
                    openActivity(SelectionWorkPKActivity.class, bundle);
                }
                break;
            case R.id.iv_relay:
                bundle = new Bundle();
                bundle.putInt("videoId", workId);
                openActivity(SelectionWorkRelayActivity.class, bundle);
                break;
            case R.id.tv_like:
                if (worksDetail != null) {
                    videosAssist(binding.tvLike, workId);
                }
                break;
            case R.id.tv_comment:
                videosComment(workId);
                break;
            case R.id.iv_share:
                shareView(WorkDetailActivity.this, new OnClickListener() {
                    @Override
                    public void onClick(View view, Object object) {

                    }

                    @Override
                    public void onLongClick(View view, Object object) {

                    }
                });
                break;
            case R.id.tv_election:
                videosVoteSet(workId);
                break;
            case R.id.user_icon:
                Intent intent = new Intent(WorkDetailActivity.this, UserHomeActivity.class);
                intent.putExtra("uid", worksDetail.getData().getTourist_id());
                startActivity(intent);
                break;
            case R.id.tv_follow:
                if (worksDetail != null) {
                    personFollow(binding.tvFollow, worksDetail.getData().getTourist().getId());
                }
                break;
            case R.id.tv_report:
                bundle = new Bundle();
                bundle.putInt("videoId", workId);
                openActivity(ReportActivity.class, bundle);
                break;
            case R.id.player_back:
                finish();
                break;
        }
    }

    private void delWorks() {
        SendRequest.delWorks(getUserInfo().getData().getId(), workId, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    if (!CommonUtil.isBlank(response)) {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.optInt("code") == 200) {
                            ToastUtils.showShort(WorkDetailActivity.this, "删除成功");
                            Intent intent = new Intent();
                            intent.putExtra("workId", workId);
                            setResult(RESULT_OK, intent);
                            finish();
                        } else {
                            ToastUtils.showShort(WorkDetailActivity.this, jsonObject.optString("msg"));
                        }
                    } else {
                        ToastUtils.showShort(WorkDetailActivity.this, "请求失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShort(WorkDetailActivity.this, "请求失败");
                }
            }
        });
    }

    private void initData() {
        if (getIntent().hasExtra("workId")) {
            workId = getIntent().getIntExtra("workId", 0);
            SendRequest.worksDetail(getUserInfo().getData().getId(), workId, new GenericsCallback<WorksDetail>(new JsonGenericsSerializator()) {
                @Override
                public void onError(Call call, Exception e, int id) {

                }

                @Override
                public void onResponse(WorksDetail response, int id) {
                    if (response != null && response.getCode() == 200) {
                        worksDetail = response;
                        initView(response);
                    }
                }
            });
        } else {
            finish();
        }
    }

    /**
     * ================================ 投票 ===========================================
     */

    private void videosComment(final int video_id) {
        SendRequest.homePageVideosComment(100, video_id, new GenericsCallback<CommentData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(CommentData response, int id) {
                if (response != null && response.getCode() == 200 && response.getData() != null) {
                    if (commentListPopupWindow != null) {
                        commentListPopupWindow.setCommentData(response);
                    } else {
                        CommentView(response, video_id);
                    }
                } else {
                    ToastUtils.showShort(WorkDetailActivity.this, response.getMsg());
                }
            }

        });

    }

    private void videosCreateComment(final int video_id, String content) {
        SendRequest.homePageVideosCreateComment(getUserInfo().getData().getId(), video_id, content, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optInt("code") == 200) {
                        videosComment(video_id);
                    } else {
                        ToastUtils.showShort(WorkDetailActivity.this, jsonObject.optString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private CommentListPopupWindow commentListPopupWindow;

    private void CommentView(CommentData commentData, final int video_id) {
        commentListPopupWindow = new CommentListPopupWindow(WorkDetailActivity.this);
        commentListPopupWindow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                videosCreateComment(video_id, (String) object);
            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });
        commentListPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        commentListPopupWindow.setCommentData(commentData);
    }

    /**
     * ================================ 投票 ===========================================
     */

    private void videosVoteSet(final int workId) {
        SendRequest.homePageVideosVoteSet(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    if (!CommonUtil.isBlank(response)) {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.optInt("code") == 200) {
                            Election(workId, jsonObject.optJSONObject("data").optString("wallet_token"));
                        } else {
                            ToastUtils.showShort(getApplication(), jsonObject.optString("msg"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void Election(final int workId, final String wallet_token) {
        ElectionPopupWindow electionPopupWindow = new ElectionPopupWindow(getApplication());
        electionPopupWindow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                switch (view.getId()) {
                    case R.id.tv_election:
                        homePageVideosVote(workId, 1);
                        break;
                    case R.id.tv_election_coin:
                        DialogManager.showPayDialog(WorkDetailActivity.this, "为TA投三票", "确认支付" + wallet_token + "金币为TA投三票?", String.valueOf(getUserInfo().getData().getWallet_token()), new com.baselibrary.view.OnClickListener() {
                            @Override
                            public void onClick(View view, Object object) {
                                switch (view.getId()) {
                                    case R.id.tv_confirm:
                                        if (getUserInfo().getData().getWallet_token() < Integer.parseInt(wallet_token)) {
                                            ToastUtils.showShort(getApplication(), "你的余额不足");
                                            return;
                                        }
                                        homePageVideosVote(workId, 2);
                                        break;
                                    case R.id.tv_cancel:

                                        break;
                                    case R.id.tv_coin:
                                        openActivity(MyWalletActivity.class);
                                        break;
                                }
                            }

                            @Override
                            public void onLongClick(View view, Object object) {

                            }
                        });
                        break;
                }
            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });
        electionPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    private void homePageVideosVote(int workId, int free) {
        SendRequest.homePageVideosVote(getUserInfo().getData().getId(), workId, free, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    if (!CommonUtil.isBlank(response)) {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.optInt("code") == 200) {
                            personInformInfo();
                            if (jsonObject.optJSONObject("data").optBoolean("canVote")) {
                                ToastUtils.showShort(getApplication(), "以为TA投" + (free == 1 ? "一" : "三") + "票");
                                if (worksDetail != null) {
                                    worksDetail.getData().setPre_votes(worksDetail.getData().getPre_votes() + (free == 1 ? 1 : 3));
                                    binding.tvElection.setText(String.valueOf(worksDetail.getData().getPre_votes()));
                                }
                            } else {
                                ToastUtils.showShort(getApplication(), "今日以为TA投" + (free == 1 ? "一" : "三") + "票，明日再来为TA投" + (free == 1 ? "一" : "三") + "票");
                            }
                        } else {
                            ToastUtils.showShort(getApplication(), jsonObject.optString("msg"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        stop();
        destroy();
        stopUpdateTimer();
        progressUpdateTimer = null;
        super.onDestroy();
    }

    private void initView(WorksDetail dataBean) {

        binding.userName.setText(dataBean.getData().getTourist().getName());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = CommonUtil.getDuration(getApplication(), dataBean.getData().getCreated_at(), df.format(new Date()));
        binding.tvTime.setText("发布于" + time);
        binding.tvName.setText(dataBean.getData().getName());
//        binding.tvFollow.setText(dataBean.getData().isIs_person_follow() ? "已关注" : "关注");
//        binding.ivLike.setSelected(dataBean.getData().isIs_assist());
        binding.tvElection.setText(String.valueOf(dataBean.getData().getPre_votes()));

        binding.tvLike.setSelected(dataBean.getData().isIs_assist());
        binding.tvLike.setText(dataBean.getData().getAssist_num() > 0 ? String.valueOf(dataBean.getData().getAssist_num()) : "赞");

        binding.deleteView.setVisibility(dataBean.getData().getTourist_id() == getUserInfo().getData().getId() ? View.VISIBLE : View.GONE);
        binding.ivWorksTuiguan.setVisibility(dataBean.getData().getTourist_id() == getUserInfo().getData().getId() ? View.VISIBLE : View.GONE);
        if (dataBean.getData().getActive_id() > 0) {
            binding.ivWorksPk.setVisibility(dataBean.getData().getTourist_id() == getUserInfo().getData().getId() ? View.GONE : View.VISIBLE);
        }
        binding.ivRelay.setVisibility(dataBean.getData().getTourist_id() == getUserInfo().getData().getId() ? View.GONE : View.VISIBLE);
        binding.tvElection.setVisibility(dataBean.getData().getActive_id() > 0 ? View.VISIBLE : View.GONE);

        GlideLoader.LoderCircleImage(this, dataBean.getData().getTourist().getAvatar(), binding.userIcon);
        GlideLoader.LoderVideoImage(this, dataBean.getData().getImg(), binding.thumbnails);


        binding.progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mPlayer != null) {
                    mPlayer.seekTo(seekBar.getProgress());
                    if (isCompleted) {
                        inSeek = false;
                    } else {
                        inSeek = true;
                    }
                }
            }
        });
        playVideo(dataBean.getData().getVideo());
    }

    private void personFollow(TextView tvFollow, int follow_id) {
        String url = tvFollow.isSelected() ? APIUrls.url_homePagePersonUnFollow : APIUrls.url_homePagePersonFollow;
        SendRequest.homePagePersonFollow(MyApplication.getInstance().getUserInfo().getData().getId(), follow_id, url, new StringCallback() {
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
                                ToastUtils.showShort(getApplication(), jsonObject.optString("msg"));
                            }
                        } else {
                            ToastUtils.showShort(getApplication(), jsonObject.optString("msg"));
                        }
                    } else {
                        ToastUtils.showShort(getApplication(), "请求失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShort(getApplication(), "请求失败");
                }

            }
        });
    }

    private void videosAssist(TextView tvLike, int video_id) {
        String url = tvLike.isSelected() ? APIUrls.url_homePageVideosCancelAssist : APIUrls.url_homePageVideosAssist;
        SendRequest.homePageVideosAssist(MyApplication.getInstance().getUserInfo().getData().getId(), video_id, url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    if (!CommonUtil.isBlank(response)) {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.optInt("code") == 200) {
                            worksDetail.getData().setAssist_num(tvLike.isSelected() ? worksDetail.getData().getAssist_num() - 1 : worksDetail.getData().getAssist_num() + 1);
                            tvLike.setText(worksDetail.getData().getAssist_num() > 0 ? String.valueOf(worksDetail.getData().getAssist_num()) : "赞");
                            tvLike.setSelected(!tvLike.isSelected());
                        } else {
                            ToastUtils.showShort(getApplication(), jsonObject.optString("msg"));
                        }
                    } else {
                        ToastUtils.showShort(getApplication(), "请求失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShort(getApplication(), "请求失败");
                }

            }
        });
    }


    /**
     * ================================ 播放器 ================================================
     */

    private void showVideoProgressInfo() {
        if (mPlayer != null && !inSeek) {
            int curPosition = mPlayer.getCurrentPosition();
            int duration = mPlayer.getDuration();
            int bufferPosition = mPlayer.getBufferPosition();
            binding.progress.setMax(duration);
            binding.progress.setSecondaryProgress(bufferPosition);
            binding.progress.setProgress(curPosition);
        }
        startUpdateTimer();
    }

    private void startUpdateTimer() {
        progressUpdateTimer.removeMessages(0);
        progressUpdateTimer.sendEmptyMessageDelayed(0, 1000);
    }

    private void stopUpdateTimer() {
        progressUpdateTimer.removeMessages(0);
    }

    private Handler progressUpdateTimer = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            showVideoProgressInfo();
        }
    };

    private void playVideo(String videoUrl) {
        binding.surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            public void surfaceCreated(SurfaceHolder holder) {
                holder.setType(SurfaceHolder.SURFACE_TYPE_GPU);
                holder.setKeepScreenOn(true);
                // 对于从后台切换到前台,需要重设surface;部分手机锁屏也会做前后台切换的处理
                if (mPlayer != null) {
                    mPlayer.setVideoSurface(holder.getSurface());
                }

            }

            public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
                if (mPlayer != null) {
                    mPlayer.setSurfaceChanged();
                }
            }

            public void surfaceDestroyed(SurfaceHolder holder) {
            }
        });
//        binding.surfaceView.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (mPlayer.isPlaying()) {
//                    binding.videoPlay.animate().alpha(1).start();
//                    mPlayer.pause();
//                } else {
//                    binding.videoPlay.animate().alpha(0).setDuration(200).start();
//                    mPlayer.play();
//                }
//            }
//        });

        binding.surfaceView.setOnTouchListener(new LiveClickListener(new LiveClickListener.ClickCallBack() {
            @Override
            public void oneClick() {
                if (mPlayer.isPlaying()) {
                    binding.videoPlay.animate().alpha(1).start();
                    mPlayer.pause();
                } else {
                    binding.videoPlay.animate().alpha(0).setDuration(200).start();
                    mPlayer.play();
                }
            }

            @Override
            public void doubleClick(int w, int y) {
                if (!binding.tvLike.isSelected() && worksDetail != null) {
                    videosAssist(binding.tvLike, workId);
                }
                int liveAnimateImgWidth = 180;
                ImageView likeImg = new ImageView(getApplication());
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(CommonUtil.dip2px(getApplication(), liveAnimateImgWidth), CommonUtil.dip2px(getApplication(), liveAnimateImgWidth));
                params.leftMargin = w - CommonUtil.dip2px(getApplication(), liveAnimateImgWidth) * 1 / 2;
                params.topMargin = y - CommonUtil.dip2px(getApplication(), liveAnimateImgWidth) * 5 / 6;
                likeImg.setPadding(10, 10, 10, 10);
                likeImg.setLayoutParams(params);
                likeImg.setImageResource(R.drawable.likefill_pre);
                binding.liveAnimateView.addView(likeImg);
                startAnimatorStyleOne(likeImg);
            }
        }));


        mPlayer = new AliVcMediaPlayer(WorkDetailActivity.this, binding.surfaceView);
        mPlayer.setCirclePlay(true);

        mPlayer.setPreparedListener(new MediaPlayer.MediaPlayerPreparedListener() {
            @Override
            public void onPrepared() {
                binding.surfaceView.setBackgroundColor(Color.TRANSPARENT);
                binding.loadingView.setVisibility(View.GONE);
                mPlayer.play();
            }
        });
//        mPlayer.setPcmDataListener(new MyPcmDataListener(this));
//        mPlayer.setCircleStartListener(new MyCircleStartListener(this));
        mPlayer.setFrameInfoListener(new MediaPlayer.MediaPlayerFrameInfoListener() {
            @Override
            public void onFrameInfoListener() {
                binding.thumbnails.animate().alpha(0).setDuration(200).start();
                showVideoProgressInfo();
            }
        });
        mPlayer.setErrorListener(new MediaPlayer.MediaPlayerErrorListener() {
            @Override
            public void onError(int i, String s) {
                ToastUtils.showShort(getApplicationContext(), "网络连接似乎出现问题，请重试");
            }
        });
        mPlayer.setCompletedListener(new MediaPlayer.MediaPlayerCompletedListener() {
            @Override
            public void onCompleted() {
                isCompleted = true;
                showVideoProgressInfo();
                stopUpdateTimer();
            }
        });
        mPlayer.setSeekCompleteListener(new MediaPlayer.MediaPlayerSeekCompleteListener() {
            @Override
            public void onSeekCompleted() {
                inSeek = false;
            }
        });
        mPlayer.setStoppedListener(new MediaPlayer.MediaPlayerStoppedListener() {
            @Override
            public void onStopped() {
                binding.videoPlay.animate().alpha(1).start();
            }
        });
        mPlayer.enableNativeLog();
        if (mPlayer != null) {
            mPlayer.setVideoScalingMode(com.alivc.player.MediaPlayer.VideoScalingMode.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
        }
        mPlayer.prepareToPlay(videoUrl);
        binding.loadingView.setVisibility(View.VISIBLE);

    }

    private boolean inSeek = false;
    private boolean isCompleted = false;

    private AliVcMediaPlayer mPlayer;

    private void start() {
//        if (mPlayer != null) {
//            mPlayer.prepareToPlay();
//        }
    }

    private void pause() {
        if (mPlayer != null) {
            mPlayer.pause();
        }
    }

    private void stop() {
        if (mPlayer != null) {
            mPlayer.stop();
        }
    }

    private void resume() {
        if (mPlayer != null) {
            mPlayer.play();
        }
    }

    private void destroy() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.destroy();
        }
    }

    private void replay() {
        stop();
        start();
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