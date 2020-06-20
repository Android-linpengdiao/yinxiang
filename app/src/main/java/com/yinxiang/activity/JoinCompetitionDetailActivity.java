package com.yinxiang.activity;

import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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
import com.yinxiang.databinding.ActivityJoinCompetitionDetailBinding;
import com.yinxiang.model.ActiveDetail;
import com.yinxiang.model.ActivityData;

import okhttp3.Call;

public class JoinCompetitionDetailActivity extends BaseActivity implements View.OnClickListener {

    private ActivityJoinCompetitionDetailBinding binding;
    private int workId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join_competition_detail);

        if (getIntent().getExtras() != null) {
            workId = getIntent().getExtras().getInt("workId");
        }

        binding.back.setOnClickListener(this);
        binding.playerBack.setOnClickListener(this);
        binding.fullscreen.setOnClickListener(this);
        initData();

    }

    private void initData() {
        SendRequest.personInformActiveDetail(getUserInfo().getData().getId(), workId, new GenericsCallback<ActiveDetail>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(ActiveDetail response, int id) {
                if (response.getCode() == 200 && response.getData() != null) {
                    initView(response.getData().getStatus());
                } else {
                    ToastUtils.showShort(JoinCompetitionDetailActivity.this, response.getMsg());
                }
            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        playVideo();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.player_back:
                if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    smallScreen();
                }
                break;
            case R.id.fullscreen:
                toggleOrientation();
                break;
        }
    }

    /**
     * 比赛进程
     */


    private void initView(int status) {
        if (status == 1) {
            initChusaiView(status);
        } else if (status == 2) {
            initChusaiView(status);
            initFusaiView(status);
        } else if (status == 3) {
            initChusaiView(status);
            initFusaiView(status);
            initJuesaiView(status);
        }

        GlideLoader.LoderVideoImage(this, CommonUtil.getVideoCoverListString().get(1), binding.thumbnails);
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
    }

    private void initChusaiView(int status) {
        GlideLoader.LoderLoadImage(JoinCompetitionDetailActivity.this, CommonUtil.getImageListString().get(0), binding.ivCover1, 10);
        binding.tvTitle1.setText("初赛-已晋级");
        binding.tvRank1.setText("排名No.1");
        binding.tvPoll1.setText("票数1000w");
        binding.ivPoint1.setSelected(true);
        if (status == 1) {
            binding.tvTitle1.setText("初赛-已晋级");
            binding.tvCompetitionTitle.setText("初赛-已晋级");
        } else if (status > 1) {
            binding.tvTitle1.setText("初赛-已结束");
            binding.tvCompetitionTitle.setText("初赛-已结束");
        }
    }

    private void initFusaiView(int status) {
        binding.ivPoint2.setSelected(true);
        if (status == 2) {
            binding.tvTitle2.setText("复赛-已晋级");
            binding.tvCompetitionTitle.setText("复赛-已晋级");
            binding.tvUpload2.setVisibility(View.VISIBLE);

        } else if (status > 2) {

            binding.tvRank2.setVisibility(View.VISIBLE);
            binding.tvPoll2.setVisibility(View.VISIBLE);
            binding.ivCover2.setVisibility(View.VISIBLE);

            binding.tvTitle2.setText("复赛-已结束");
            binding.tvCompetitionTitle.setText("复赛-已结束");
            binding.tvRank2.setText("排名No.1");
            binding.tvPoll2.setText("票数1000w");
            GlideLoader.LoderLoadImage(JoinCompetitionDetailActivity.this, CommonUtil.getImageListString().get(1), binding.ivCover2, 10);
        }
    }

    private void initJuesaiView(int status) {
        binding.ivPoint3.setSelected(true);
        if (status == 3) {
            binding.tvTitle3.setText("决赛-已晋级");
            binding.tvCompetitionTitle.setText("决赛-已晋级");
            binding.tvUpload3.setVisibility(View.VISIBLE);

        } else if (status > 3) {

            binding.tvRank3.setVisibility(View.VISIBLE);
            binding.tvPoll3.setVisibility(View.VISIBLE);
            binding.ivCover3.setVisibility(View.VISIBLE);

            binding.tvTitle3.setText("决赛-已结束");
            binding.tvCompetitionTitle.setText("决赛-已结束");
            binding.tvRank3.setText("排名No.1");
            binding.tvPoll3.setText("票数1000w");
            GlideLoader.LoderLoadImage(JoinCompetitionDetailActivity.this, CommonUtil.getImageListString().get(2), binding.ivCover3, 10);
        }
    }


    /**
     * 视频播放器
     */


    private void showVideoProgressInfo() {
        if (mPlayer != null && !inSeek) {
            int curPosition = mPlayer.getCurrentPosition();
            int duration = mPlayer.getDuration();
            int bufferPosition = mPlayer.getBufferPosition();
            binding.currentDuration.setText(CommonUtil.Formatter.formatTime(curPosition));
            binding.totalDuration.setText(CommonUtil.Formatter.formatTime(duration));
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
    String url1 = CommonUtil.getVideoListString().get(1);

    private void playVideo() {
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
        binding.videoPlay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mPlayer.isPlaying()) {
                    binding.videoPlay.setImageResource(R.drawable.video_play);
                    mPlayer.pause();
                } else {
                    binding.videoPlay.setImageResource(R.drawable.video_pause);
                    mPlayer.play();
                }
            }
        });


        mPlayer = new AliVcMediaPlayer(JoinCompetitionDetailActivity.this, binding.surfaceView);
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
                binding.videoPlay.setImageResource(R.drawable.video_pause);
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
                binding.videoPlay.setImageResource(R.drawable.video_play);
            }
        });
        mPlayer.enableNativeLog();
        if (mPlayer != null) {
            mPlayer.setVideoScalingMode(MediaPlayer.VideoScalingMode.VIDEO_SCALING_MODE_SCALE_TO_FIT);
        }
        mPlayer.prepareToPlay(url1);
        binding.loading.setVisibility(View.VISIBLE);

    }

    private boolean inSeek = false;
    private boolean isCompleted = false;

    private AliVcMediaPlayer mPlayer;

    private void start() {
        if (mPlayer != null) {
            mPlayer.prepareToPlay(url1);
        }
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


    //------------------------------------全屏切换-------------------------------------------------

    private void toggleOrientation() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            smallScreen();
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            fullScreen();
        }
    }


    private void fullScreen() {
        binding.topView.setVisibility(View.GONE);
        binding.playerBack.setVisibility(View.VISIBLE);
        binding.fullscreen.setImageResource(R.drawable.zuixiaohua);
        hideNavigationBar();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) binding.videoContainer.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        binding.videoContainer.setLayoutParams(layoutParams);
    }

    private void smallScreen() {
        binding.topView.setVisibility(View.VISIBLE);
        binding.playerBack.setVisibility(View.GONE);
        binding.fullscreen.setImageResource(R.drawable.fullscreen);
        showNavigationBar();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) binding.videoContainer.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = CommonUtil.dip2px(JoinCompetitionDetailActivity.this, 211);
        binding.videoContainer.setLayoutParams(layoutParams);
    }

    public void showNavigationBar() {
        int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

        if (android.os.Build.VERSION.SDK_INT >= 19) {
            uiFlags |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;  //SYSTEM_UI_FLAG_IMMERSIVE_STICKY: hide navigation bars - compatibility: building API level is lower thatn 19, use magic number directly for higher API target level
        } else {
            uiFlags |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
        }
        getWindow().getDecorView().setSystemUiVisibility(uiFlags);
    }

    public void hideNavigationBar() {
        int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN; // hide status bar

        if (android.os.Build.VERSION.SDK_INT >= 19) {
            uiFlags |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;  //SYSTEM_UI_FLAG_IMMERSIVE_STICKY: hide navigation bars - compatibility: building API level is lower thatn 19, use magic number directly for higher API target level
        } else {
            uiFlags |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
        }
        getWindow().getDecorView().setSystemUiVisibility(uiFlags);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            smallScreen();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
