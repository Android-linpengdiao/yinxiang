package com.yinxiang.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.alivc.player.AliVcMediaPlayer;
import com.alivc.player.MediaPlayer;
import com.baselibrary.Constants;
import com.baselibrary.manager.LoadingManager;
import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.GlideLoader;
import com.baselibrary.utils.LogUtil;
import com.baselibrary.utils.PermissionUtils;
import com.baselibrary.utils.ToastUtils;
import com.cjt2325.cameralibrary.CameraActivity;
import com.cjt2325.cameralibrary.JCameraView;
import com.media.MediaActivity;
import com.media.image.ImageModel;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.callbacks.StringCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayer;
import com.yinxiang.R;
import com.yinxiang.databinding.ActivityJoinCompetitionDetailBinding;
import com.yinxiang.model.ActiveDetail;
import com.yinxiang.model.ClubData;
import com.yinxiang.model.HomeActives;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Request;

public class JoinCompetitionDetailActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "JoinCompetitionDetailAc";
    private ActivityJoinCompetitionDetailBinding binding;
    private int workId = 0;
    private ActiveDetail activeDetail;

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
        binding.tvUpload2.setOnClickListener(this);
        binding.tvUpload3.setOnClickListener(this);
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
                    activeDetail = response;
                    initView(response);
                    initPlayerView(activeDetail.getData().get(0).getVideo());
                } else {
                    ToastUtils.showShort(JoinCompetitionDetailActivity.this, response.getMsg());
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_upload_2:
            case R.id.tv_upload_3:
                if (activeDetail != null && activeDetail.getData().size() > 0) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(JoinCompetitionDetailActivity.this);
                    dialog.setTitle("");
                    dialog.setItems(R.array.media_list_dialog, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    if (checkPermissionsAll(PermissionUtils.STORAGE, REQUEST_VIDEO)) {
                                        openMedia();
                                    }
                                    break;
                                case 1:
                                    if (checkPermissionsAll(PermissionUtils.CAMERA, REQUEST_WXCAMERA)) {
                                        openCamera();
                                    }
                                    break;
                                case 2:

                                    break;
                            }
                        }
                    });
                    dialog.show();
                }
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
     *
     * @param activeDetail
     */


    private void initView(ActiveDetail activeDetail) {

        if (activeDetail.getData().size() > 0) {
            initChusaiView(activeDetail.getData().get(0));
        }
        if (activeDetail.getData().size() > 1) {
            initFusaiView(activeDetail.getData().get(1));
        }
        if (activeDetail.getData().size() > 2) {
            initJuesaiView(activeDetail.getData().get(2));
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
    }

    private void initChusaiView(ActiveDetail.DataBean dataBean) {
        binding.tvCompetitionTitle.setText(CommonUtil.getStatus(dataBean.getStatus()));
        binding.tvTitle1.setText(CommonUtil.getStatus(dataBean.getStatus()));
        binding.tvRank1.setText("排名No." + dataBean.getRank());
        binding.tvPoll1.setText("票数" + dataBean.getRank_vote());
        binding.ivPoint1.setSelected(true);
        GlideLoader.LoderImage(JoinCompetitionDetailActivity.this, dataBean.getImg(), binding.ivCover1, 10);
    }

    private void initFusaiView(ActiveDetail.DataBean dataBean) {
        binding.tvCompetitionTitle.setText(CommonUtil.getStatus(dataBean.getStatus()));
        binding.tvTitle2.setText(CommonUtil.getStatus(dataBean.getStatus()));
        binding.tvRank2.setText("排名No." + dataBean.getRank());
        binding.tvPoll2.setText("票数" + dataBean.getRank_vote());

        binding.ivPoint2.setSelected(true);
        if (dataBean.getStatus() == 6) {
            binding.tvUpload2.setVisibility(View.VISIBLE);
        } else {
            binding.tvRank2.setVisibility(View.VISIBLE);
            binding.tvPoll2.setVisibility(View.VISIBLE);
            binding.ivCover2.setVisibility(View.VISIBLE);
            GlideLoader.LoderImage(JoinCompetitionDetailActivity.this, dataBean.getImg(), binding.ivCover2, 10);
        }
    }

    private void initJuesaiView(ActiveDetail.DataBean dataBean) {
        binding.tvCompetitionTitle.setText(CommonUtil.getStatus(dataBean.getStatus()));
        binding.tvTitle3.setText(CommonUtil.getStatus(dataBean.getStatus()));
        binding.tvRank3.setText("排名No." + dataBean.getRank());
        binding.tvPoll3.setText("票数" + dataBean.getRank_vote());

        binding.ivPoint3.setSelected(true);
        if (dataBean.getStatus() == 6) {
            binding.tvUpload3.setVisibility(View.VISIBLE);
        } else {
            binding.tvRank3.setVisibility(View.VISIBLE);
            binding.tvPoll3.setVisibility(View.VISIBLE);
            binding.ivCover3.setVisibility(View.VISIBLE);
            GlideLoader.LoderImage(JoinCompetitionDetailActivity.this, dataBean.getImg(), binding.ivCover3, 10);
        }
    }


    /**
     * =====================================上传视频===========================================
     */

    private final static int REQUEST_WXCAMERA = 100;
    private static final int REQUEST_VIDEO = 200;
    private String videoPath;
    private String coverPath;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean granted = true;
        switch (requestCode) {
            case REQUEST_VIDEO:
                for (int i = 0; i < PermissionUtils.storage.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        granted = false;
                        break;
                    }
                }
                if (granted) {
                    openMedia();
                } else {
                    PermissionUtils.openAppDetails(JoinCompetitionDetailActivity.this, "储存");
                }
                break;
            case REQUEST_WXCAMERA:
                for (int i = 0; i < PermissionUtils.camera.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        granted = false;
                        break;
                    }
                }
                if (granted) {
                    openCamera();
                } else {
                    PermissionUtils.openAppDetails(JoinCompetitionDetailActivity.this, "储存和相机");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_WXCAMERA:
                    if (data != null) {
                        videoPath = data.getStringExtra("videoPath");
                        coverPath = data.getStringExtra("coverPath");
                        if (!CommonUtil.isBlank(videoPath) && !CommonUtil.isBlank(coverPath)) {
                            uploadFile(coverPath);
                        }
                    }
                    break;
                case REQUEST_VIDEO:
                    if (data != null) {
                        videoPath = data.getStringExtra("video");
                        coverPath = data.getStringExtra("cover");
                        if (!CommonUtil.isBlank(videoPath) && !CommonUtil.isBlank(coverPath)) {
                            uploadFile(coverPath);
                        }
                    }
                    break;
            }
        }
    }

    private void openMedia() {
        Intent intent = new Intent(JoinCompetitionDetailActivity.this, MediaActivity.class);
        intent.putExtra("type", ImageModel.TYPE_VIDEO);
        startActivityForResult(intent, REQUEST_VIDEO);
    }

    private void openCamera() {
        int type = JCameraView.BUTTON_STATE_ONLY_RECORDER;
        int minTime = 10;
        int maxTime = 180;
        CameraActivity.startCameraActivity(JoinCompetitionDetailActivity.this, minTime, maxTime, "#44bf19", type, REQUEST_WXCAMERA);

    }

    private void uploadFile(String file) {
        SendRequest.fileUpload(file, file.substring(file.lastIndexOf("/") + 1), new StringCallback() {

            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject object = new JSONObject(response);
                    String url = object.optString("data");
                    if (!CommonUtil.isBlank(url)) {
                        createSecurityToken(url);
                    } else {
                        ToastUtils.showShort(JoinCompetitionDetailActivity.this, "封面上传失败");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void createSecurityToken(final String coverUrl) {
        SendRequest.createSecurityToken(new StringCallback() {

            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                LoadingManager.showLoadingDialog(JoinCompetitionDetailActivity.this);
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                LoadingManager.hideLoadingDialog(JoinCompetitionDetailActivity.this);
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject object = jsonObject.optJSONObject("data");
                    if (jsonObject.optInt("code") == 200) {
                        String accessKeyId = object.optString("AccessKeyId");
                        String accessKeySecret = object.optString("AccessKeySecret");
                        String securityToken = object.optString("SecurityToken");
                        String expriedTime = object.optString("Expiration");
                        uploadVideo(accessKeyId, accessKeySecret, securityToken, coverUrl);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void uploadVideo(String AccessKeyId, String SecretKeyId, String SecurityToken, final String coverUrl) {

        String endpoint = "http://oss-cn-beijing.aliyuncs.com";

        //if null , default will be init
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // connction time out default 15s
        conf.setSocketTimeout(15 * 1000); // socket timeout，default 15s
        conf.setMaxConcurrentRequest(5); // synchronous request number，default 5
        conf.setMaxErrorRetry(2); // retry，default 2
        OSSLog.enableLog(); //write local log file ,path is SDCard_path\OSSLog\logs.csv

        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(AccessKeyId, SecretKeyId, SecurityToken);

        OSS oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider, conf);

        // Construct an upload request
        PutObjectRequest put = new PutObjectRequest("diandou-test", videoPath.substring(videoPath.lastIndexOf("/") + 1), videoPath);

        // You can set progress callback during asynchronous upload
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                LogUtil.d(TAG, "currentSize: " + currentSize + " totalSize: " + totalSize);
                String temp = "" + currentSize * 100 / totalSize;
                LoadingManager.updateProgress(JoinCompetitionDetailActivity.this, String.format(Constants.str_updata_wait, temp + "%"));
            }
        });

        LoadingManager.showProgress(JoinCompetitionDetailActivity.this, String.format(Constants.str_updata_wait, "0%"));
        final OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                LoadingManager.hideProgress(JoinCompetitionDetailActivity.this);
                String videoUrl = "http://" + request.getBucketName() + ".oss-cn-beijing.aliyuncs.com/" + request.getObjectKey();
                Log.i(TAG, "onSuccess: " + videoUrl);
                publishWork(coverUrl, videoUrl,
                        activeDetail.getData().get(0).getName(),
                        activeDetail.getData().get(0).getActive_id(),
                        activeDetail.getData().get(0).getClub_id());
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                LoadingManager.hideProgress(JoinCompetitionDetailActivity.this);
                ToastUtils.showShort(JoinCompetitionDetailActivity.this, "上传视频失败");
                // Request exception
                if (clientExcepion != null) {
                    // Local exception, such as a network exception
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // Service exception
                    LogUtil.d(TAG, "ErrorCode " + serviceException.getErrorCode());
                    LogUtil.d(TAG, "RequestId " + serviceException.getRequestId());
                    LogUtil.d(TAG, "HostId " + serviceException.getHostId());
                    LogUtil.d(TAG, "RawMessage " + serviceException.getRawMessage());
                }
            }

            @Override
            protected Object clone() throws CloneNotSupportedException {
                return super.clone();
            }
        });
        LoadingManager.OnDismissListener(JoinCompetitionDetailActivity.this, new LoadingManager.OnDismissListener() {
            @Override
            public void onDismiss() {
                task.cancel(); // Cancel the task
            }
        });

        // task.cancel(); // Cancel the task
        // task.waitUntilFinished(); // Wait till the task is finished
    }

    private void publishWork(String coverUrl, String videoUrl, String name, int active_id, int club_id) {
        SendRequest.publishWork(getUserInfo().getData().getId(), name, coverUrl, videoUrl, active_id, club_id, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showShort(JoinCompetitionDetailActivity.this, "发布失败");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optInt("code") == 200) {
                        ToastUtils.showShort(JoinCompetitionDetailActivity.this, "上传成功");
                        initData();
                    } else {
                        ToastUtils.showShort(JoinCompetitionDetailActivity.this, "上传失败 :" + jsonObject.optString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.showShort(JoinCompetitionDetailActivity.this, "上传失败");
                }
            }
        });
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
                    String playEventLog = "TXVodPlayer onPlayEvent event: " + event + ", " + param.getString(TXLiveConstants.EVT_DESCRIPTION);
                    TXCLog.d(TAG, playEventLog);
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
                Log.i(TAG, "onPlayEvent: " + i);
            }

            @Override
            public void onNetStatus(Bundle bundle) {

            }
        });
        mVodPlayer.setPlayerView(binding.videoView);
        mVodPlayer.setAutoPlay(true);
        mVodPlayer.setLoop(true);
        mVodPlayer.enableHardwareDecode(false); // 是否使用硬解码
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
//        progressUpdateTimer.removeMessages(0);
//        progressUpdateTimer.sendEmptyMessageDelayed(0, 1000);
//    }
//
//    private void stopUpdateTimer() {
//        progressUpdateTimer.removeMessages(0);
//    }
//
//    private Handler progressUpdateTimer = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            showVideoProgressInfo();
//        }
//    };
//
//    private void playVideo(String videoUrl) {
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
//        mPlayer = new AliVcMediaPlayer(JoinCompetitionDetailActivity.this, binding.surfaceView);
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
//            mPlayer.setVideoScalingMode(MediaPlayer.VideoScalingMode.VIDEO_SCALING_MODE_SCALE_TO_FIT);
//        }
//        Log.i(TAG, "playVideo: " + videoUrl);
//        mPlayer.prepareToPlay(videoUrl);
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
//        if (mPlayer != null && activeDetail != null) {
//            mPlayer.prepareToPlay("");
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
