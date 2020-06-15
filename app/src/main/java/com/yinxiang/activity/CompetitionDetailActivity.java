package com.yinxiang.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.alivc.player.AliVcMediaPlayer;
import com.alivc.player.MediaPlayer;
import com.baselibrary.MessageBus;
import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.GlideLoader;
import com.baselibrary.utils.ToastUtils;
import com.yinxiang.R;
import com.yinxiang.databinding.ActivityCompetitionDetailBinding;
import com.yinxiang.model.HomeActives;

import java.util.Date;

public class CompetitionDetailActivity extends BaseActivity implements View.OnClickListener {

    private ActivityCompetitionDetailBinding binding;
    private HomeActives.DataBean dataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_competition_detail);

        if (getIntent().hasExtra("homeActives")) {
            dataBean = (HomeActives.DataBean) getIntent().getSerializableExtra("homeActives");
        } else {
            finish();
        }

        binding.back.setOnClickListener(this);
        binding.tvJoin.setOnClickListener(this);
        binding.playerBack.setOnClickListener(this);
        binding.fullscreen.setOnClickListener(this);
        initView();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_join:
                Intent intent = new Intent();
                intent.putExtra("homeActives", dataBean);
                setResult(RESULT_OK, intent);
                finish();
                break;
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

    private void initView() {
        binding.title.setText(dataBean.getTitle());
        binding.tvTitle.setText(dataBean.getTitle());
        binding.tvDesc.setText(dataBean.getDesc());
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
        if (progressUpdateTimer != null) {
            progressUpdateTimer.removeMessages(0);
            progressUpdateTimer.sendEmptyMessageDelayed(0, 1000);
        }
    }

    private void stopUpdateTimer() {
        if (progressUpdateTimer != null) {
            progressUpdateTimer.removeMessages(0);
        }
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


        mPlayer = new AliVcMediaPlayer(CompetitionDetailActivity.this, binding.surfaceView);
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
            mPlayer.setVideoScalingMode(com.alivc.player.MediaPlayer.VideoScalingMode.VIDEO_SCALING_MODE_SCALE_TO_FIT);
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
        layoutParams.height = CommonUtil.dip2px(CompetitionDetailActivity.this, 211);
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
