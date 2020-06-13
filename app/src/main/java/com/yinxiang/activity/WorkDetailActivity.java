package com.yinxiang.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.SeekBar;

import com.alivc.player.AliVcMediaPlayer;
import com.alivc.player.MediaPlayer;
import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.GlideLoader;
import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.yinxiang.R;
import com.yinxiang.databinding.ActivityWorkDetailBinding;
import com.yinxiang.model.CommentData;
import com.yinxiang.model.WorksDetail;
import com.yinxiang.view.CommentListPopupWindow;
import com.yinxiang.view.OnClickListener;

import okhttp3.Call;

public class WorkDetailActivity extends BaseActivity implements View.OnClickListener {

    private ActivityWorkDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_work_detail);
        setStatusBarHeight();
        binding.playerBack.setOnClickListener(this);
        binding.ivComment.setOnClickListener(this);
        binding.ivLike.setOnClickListener(this);
        binding.ivShare.setOnClickListener(this);
        binding.userInfoView.setOnClickListener(this);
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
        switch (v.getId()) {
            case R.id.iv_comment:
                CommentView();
                break;
            case R.id.iv_like:
                binding.ivLike.setSelected(!binding.ivLike.isSelected());
                break;
            case R.id.player_back:
                finish();
                break;
            case R.id.user_info_view:
                openActivity(UserHomeActivity.class);
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
        }
    }

    private void initData() {
        if (getIntent().hasExtra("workId")) {
            SendRequest.worksDetail(getIntent().getIntExtra("workId", 0), new GenericsCallback<WorksDetail>(new JsonGenericsSerializator()) {
                @Override
                public void onError(Call call, Exception e, int id) {

                }

                @Override
                public void onResponse(WorksDetail response, int id) {
                    if (response != null && response.getCode() == 200){
                        initView(response);
                    }
                }
            });
        } else {
            finish();
        }
    }

    private void CommentView() {
        CommentListPopupWindow commentListPopupWindow = new CommentListPopupWindow(WorkDetailActivity.this);
        commentListPopupWindow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {

            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });
        commentListPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        CommentData commentData = new CommentData();
        commentListPopupWindow.setCommentData(commentData);
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

    private void initView(WorksDetail response) {
        binding.userName.setText(response.getData().getName());
        GlideLoader.LoderVideoImage(this,response.getData().getImg(), binding.thumbnails);
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

        playVideo(response.getData().getVideo());
    }

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
                binding.loading.setVisibility(View.GONE);
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
        binding.loading.setVisibility(View.VISIBLE);

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