package com.yinxiang.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;

import androidx.databinding.DataBindingUtil;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.GlideLoader;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayer;
import com.yinxiang.R;
import com.yinxiang.databinding.ActivityCompetitionDetailBinding;
import com.yinxiang.model.HomeActives;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        binding.tvDesc.setText(Html.fromHtml(dataBean.getDesc()));
//        binding.webView.getSettings().setJavaScriptEnabled(true);
//        binding.webView.getSettings().setSupportZoom(true);
//        binding.webView.getSettings().setBuiltInZoomControls(true);
//        binding.webView.setWebViewClient(new ArticleWebViewClient());
//        binding.webView.setWebViewClient(new WebViewClient() {
//
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//
//
//
//            }
//
//
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//            }
//        });
//        binding.webView.loadData(dataBean.getDesc(), "text/html; charset=UTF-8", "UTF-8");
        GlideLoader.LoderVideoImage(this, dataBean.getImg(), binding.thumbnails);
        binding.progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
//                if (mPlayer != null) {
//                    mPlayer.seekTo(seekBar.getProgress());
//                    if (isCompleted) {
//                        inSeek = false;
//                    } else {
//                        inSeek = true;
//                    }
//                }
            }
        });

        if (!CommonUtil.isBlank(dataBean.getVideo())) {
            try {
                JSONArray jsonArray = new JSONArray(dataBean.getVideo());
                if (jsonArray.length() > 0) {
                    JSONObject jsonObject = jsonArray.optJSONObject(0);
                    String url = jsonObject.optString("download_link");
                    if (url.startsWith("http")) {
                        initPlayerView(url);
                    } else {
                        initPlayerView(GlideLoader.domain + url);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

//    private class ArticleWebViewClient extends WebViewClient {
//
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            super.onPageFinished(view, url);
//            //重置webview中img标签的图片大小
////            imgReset();
//        }
//
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            view.loadUrl(url);
//            return true;
//        }
//    }
//
//    /**
//     * 对图片进行重置大小，宽度就是手机屏幕宽度，高度根据宽度比便自动缩放
//     **/
//    private void imgReset() {
//        binding.webView.loadUrl("javascript:(function(){" +
//                "var objs = document.getElementsByTagName('img'); " +
//                "for(var i=0;i<objs.length;i++)  " +
//                "{"
//                + "var img = objs[i];   " +
//                "    img.style.maxWidth = '100%'; img.style.height = 'auto';  " +
//                "}" +
//                "})()");
//    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        if (mVodPlayer != null && mVodPlayer.isPlaying()) {
            mVodPlayer.pause();
            binding.videoPlay.setSelected(false);
            binding.videoPlay.setImageResource(R.drawable.video_play);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (progressUpdateTimer != null) {
            progressUpdateTimer.removeMessages(UpdatePlayer);
            progressUpdateTimer = null;
        }
        mVodPlayer = null;
        super.onDestroy();
    }

    /**
     * =====================================视频播放器===========================================
     */

    /**
     * SDK player 相关
     */
    private TXVodPlayer mVodPlayer = null;

    private void initPlayerView(String videoUrl) {

        binding.videoView.setLogMargin(12, 12, 110, 60);
        binding.videoView.showLog(false);

        if (mVodPlayer == null) {
            mVodPlayer = new TXVodPlayer(this);
        }

        mVodPlayer.setVodListener(new ITXVodPlayListener() {
            @Override
            public void onPlayEvent(TXVodPlayer txVodPlayer, int event, Bundle param) {
                if (event != TXLiveConstants.PLAY_EVT_PLAY_PROGRESS) {
//                    String playEventLog = "TXVodPlayer onPlayEvent event: " + event + ", " + param.getString(TXLiveConstants.EVT_DESCRIPTION);
//                    TXCLog.d(TAG, playEventLog);
                }
                switch (event) {
                    case TXLiveConstants.PLAY_EVT_VOD_PLAY_PREPARED://视频播放开始
                        binding.loading.setVisibility(View.GONE);
                        binding.videoPlay.setSelected(true);
                        binding.videoPlay.setImageResource(R.drawable.video_pause);
                        binding.thumbnails.animate().alpha(0).setDuration(200).start();

                        break;
                    case TXLiveConstants.PLAY_EVT_RCV_FIRST_I_FRAME://切换软硬解码器后，重新seek位置

                        break;
                    case TXLiveConstants.PLAY_EVT_PLAY_END:

                        binding.videoPlay.setSelected(false);
                        binding.videoPlay.setImageResource(R.drawable.video_play);

                        break;
                    case TXLiveConstants.PLAY_EVT_PLAY_PROGRESS:
                        int progress = param.getInt(TXLiveConstants.EVT_PLAY_PROGRESS_MS);
                        int duration = param.getInt(TXLiveConstants.EVT_PLAY_DURATION_MS);

                        binding.currentDuration.setText(CommonUtil.Formatter.formatTime((int) (progress)));
                        binding.totalDuration.setText(CommonUtil.Formatter.formatTime((int) (duration)));
                        binding.progress.setProgress((int) progress);
                        binding.progress.setMax((int) duration);
                        break;
                    case TXLiveConstants.PLAY_EVT_PLAY_BEGIN: {
//                        updatePlayState(SuperPlayerConst.PLAYSTATE_PLAYING);
                        break;
                    }
                    default:
                        break;
                }
                if (event < 0) {// 播放点播文件失败
                    mVodPlayer.stopPlay(true);

                }
            }

            @Override
            public void onNetStatus(TXVodPlayer txVodPlayer, Bundle bundle) {

            }
        });
        mVodPlayer.setPlayListener(new ITXLivePlayListener() {
            @Override
            public void onPlayEvent(int i, Bundle bundle) {

            }

            @Override
            public void onNetStatus(Bundle bundle) {

            }
        });
        mVodPlayer.setPlayerView(binding.videoView);
        mVodPlayer.setAutoPlay(true);
        mVodPlayer.setLoop(true);
        mVodPlayer.enableHardwareDecode(true); // 是否使用硬解码
        mVodPlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);// player 渲染角度
        mVodPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);//player 渲染模式
        int result = mVodPlayer.startPlay(videoUrl); // result返回值：0 success;  -1 empty url; -2 invalid url; -3 invalid playType;
        if (result == 0) {
//            mBinding.videoPlayLayout.videoPlay.setSelected(true);
//            showVideoProgressInfo();
            hidePlayerView();
        }

        binding.videoContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.progressLayoutView.setVisibility(binding.progressLayoutView.isShown() ? View.GONE : View.VISIBLE);
                if (binding.progressLayoutView.isShown()) {
                    hidePlayerView();
                }
            }
        });
        binding.videoPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hidePlayerView();
                if (mVodPlayer != null) {
                    if (mVodPlayer.isPlaying()) {
                        mVodPlayer.pause();
                        binding.videoPlay.setSelected(false);
                        binding.videoPlay.setImageResource(R.drawable.video_play);
                    } else {
                        mVodPlayer.resume();
                        binding.videoPlay.setSelected(true);
                        binding.videoPlay.setImageResource(R.drawable.video_pause);
                    }
                }
            }
        });


    }

    private void hidePlayerView() {
        if (progressUpdateTimer != null) {
            progressUpdateTimer.removeMessages(UpdatePlayer);
            progressUpdateTimer.sendEmptyMessageDelayed(UpdatePlayer, 3000);
        }
    }

    private final int UpdatePlayer = 100;
    @SuppressLint("HandlerLeak")
    private Handler progressUpdateTimer = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UpdatePlayer:
                    binding.progressLayoutView.setVisibility(View.GONE);
                    break;
            }
        }
    };


//    private void showVideoProgressInfo() {
//        if (mPlayer != null && !inSeek) {
//            int curPosition = mPlayer.getCurrentPosition();
//            int duration = mPlayer.getDuration();
//            int bufferPosition = mPlayer.getBufferPosition();
//            binding.currentDuration.setText(CommonUtil.Formatter.formatTime(curPosition));
//            binding.totalDuration.setText(CommonUtil.Formatter.formatTime(duration));
//            binding.progress.setMax(duration);
//            binding.progress.setSecondaryProgress(bufferPosition);
//            binding.progress.setProgress(curPosition);
//        }
//        startUpdateTimer();
//    }
//
//    private void startUpdateTimer() {
//        if (progressUpdateTimer != null) {
//            progressUpdateTimer.removeMessages(0);
//            progressUpdateTimer.sendEmptyMessageDelayed(0, 1000);
//        }
//    }
//
//    private void stopUpdateTimer() {
//        if (progressUpdateTimer != null) {
//            progressUpdateTimer.removeMessages(0);
//        }
//    }
//
//    private Handler progressUpdateTimer = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            showVideoProgressInfo();
//        }
//    };
//    String url1 = CommonUtil.getVideoListString().get(1);
//
//    private void playVideo() {
//        binding.surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
//            public void surfaceCreated(SurfaceHolder holder) {
//                holder.setType(SurfaceHolder.SURFACE_TYPE_GPU);
//                holder.setKeepScreenOn(true);
//                // 对于从后台切换到前台,需要重设surface;部分手机锁屏也会做前后台切换的处理
//                if (mPlayer != null) {
//                    mPlayer.setVideoSurface(holder.getSurface());
//                }
//
//            }
//
//            public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
//                if (mPlayer != null) {
//                    mPlayer.setSurfaceChanged();
//                }
//            }
//
//            public void surfaceDestroyed(SurfaceHolder holder) {
//            }
//        });
//        binding.videoPlay.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (mPlayer.isPlaying()) {
//                    binding.videoPlay.setImageResource(R.drawable.video_play);
//                    mPlayer.pause();
//                } else {
//                    binding.videoPlay.setImageResource(R.drawable.video_pause);
//                    mPlayer.play();
//                }
//            }
//        });
//
//
//        mPlayer = new AliVcMediaPlayer(CompetitionDetailActivity.this, binding.surfaceView);
//        mPlayer.setCirclePlay(true);
//
//        mPlayer.setPreparedListener(new MediaPlayer.MediaPlayerPreparedListener() {
//            @Override
//            public void onPrepared() {
//                binding.surfaceView.setBackgroundColor(Color.TRANSPARENT);
//                binding.loading.setVisibility(View.GONE);
//                mPlayer.play();
//            }
//        });
////        mPlayer.setPcmDataListener(new MyPcmDataListener(this));
////        mPlayer.setCircleStartListener(new MyCircleStartListener(this));
//        mPlayer.setFrameInfoListener(new MediaPlayer.MediaPlayerFrameInfoListener() {
//            @Override
//            public void onFrameInfoListener() {
//                binding.videoPlay.setImageResource(R.drawable.video_pause);
//                binding.thumbnails.animate().alpha(0).setDuration(200).start();
//                showVideoProgressInfo();
//            }
//        });
//        mPlayer.setErrorListener(new MediaPlayer.MediaPlayerErrorListener() {
//            @Override
//            public void onError(int i, String s) {
//                ToastUtils.showShort(getApplicationContext(), "网络连接似乎出现问题，请重试");
//            }
//        });
//        mPlayer.setCompletedListener(new MediaPlayer.MediaPlayerCompletedListener() {
//            @Override
//            public void onCompleted() {
//                isCompleted = true;
//                showVideoProgressInfo();
//                stopUpdateTimer();
//            }
//        });
//        mPlayer.setSeekCompleteListener(new MediaPlayer.MediaPlayerSeekCompleteListener() {
//            @Override
//            public void onSeekCompleted() {
//                inSeek = false;
//            }
//        });
//        mPlayer.setStoppedListener(new MediaPlayer.MediaPlayerStoppedListener() {
//            @Override
//            public void onStopped() {
//                binding.videoPlay.setImageResource(R.drawable.video_play);
//            }
//        });
//        mPlayer.enableNativeLog();
//        if (mPlayer != null) {
//            mPlayer.setVideoScalingMode(com.alivc.player.MediaPlayer.VideoScalingMode.VIDEO_SCALING_MODE_SCALE_TO_FIT);
//        }
//        mPlayer.prepareToPlay(url1);
//        binding.loading.setVisibility(View.VISIBLE);
//
//    }
//
//    private boolean inSeek = false;
//    private boolean isCompleted = false;
//
//    private AliVcMediaPlayer mPlayer;
//
//    private void start() {
//        if (mPlayer != null) {
//            mPlayer.prepareToPlay(url1);
//        }
//    }
//
//    private void pause() {
//        if (mPlayer != null) {
//            mPlayer.pause();
//        }
//    }
//
//    private void stop() {
//        if (mPlayer != null) {
//            mPlayer.stop();
//        }
//    }
//
//    private void resume() {
//        if (mPlayer != null) {
//            mPlayer.play();
//        }
//    }
//
//    private void destroy() {
//        if (mPlayer != null) {
//            mPlayer.stop();
//            mPlayer.destroy();
//        }
//    }
//
//    private void replay() {
//        stop();
//        start();
//    }


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
