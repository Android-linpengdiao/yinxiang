package com.yinxiang.activity;

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
        binding.ivLike.setOnClickListener(this);
        binding.ivComment.setOnClickListener(this);
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
            case R.id.iv_like:
                if (worksDetail != null) {
                    videosAssist(binding.ivLike, workId);
                }
                break;
            case R.id.iv_comment:
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

    private void initData() {
        if (getIntent().hasExtra("workId")) {
            workId = getIntent().getIntExtra("workId", 0);
            SendRequest.worksDetail(workId, new GenericsCallback<WorksDetail>(new JsonGenericsSerializator()) {
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
                            if (jsonObject.optJSONObject("data").optBoolean("canVote")) {
                                ToastUtils.showShort(getApplication(), "以为TA投一票");
                            } else {
                                ToastUtils.showShort(getApplication(), "今日以为TA投一票，明日再来为TA投一票");
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

    private void videosAssist(ImageView ivLike, int video_id) {
        String url = ivLike.isSelected() ? APIUrls.url_homePageVideosCancelAssist : APIUrls.url_homePageVideosAssist;
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
                            ivLike.setSelected(!ivLike.isSelected());
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
        binding.surfaceView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mPlayer.isPlaying()) {
                    binding.videoPlay.animate().alpha(1).start();
                    mPlayer.pause();
                } else {
                    binding.videoPlay.animate().alpha(0).setDuration(200).start();
                    mPlayer.play();
                }
            }
        });


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
}