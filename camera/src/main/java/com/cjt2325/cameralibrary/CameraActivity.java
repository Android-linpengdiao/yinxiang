package com.cjt2325.cameralibrary;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.cjt2325.cameralibrary.listener.ClickListener;
import com.cjt2325.cameralibrary.listener.ErrorListener;
import com.cjt2325.cameralibrary.listener.JCameraListener;
import com.cjt2325.cameralibrary.util.DeviceUtil;
import com.cjt2325.cameralibrary.util.FileUtil;
import com.vincent.videocompressor.VideoCompress;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

public class CameraActivity extends Activity {

    private static final String TAG = "CameraActivity";
    private JCameraView jCameraView;

    public static final String MAX_TIME = "max_time";
    public static final String MIN_TIME = "min_time";
    public static final String COLOR = "color";
    public static final String STATE_TYPE = "state_type";

    private static final int RESULT_VIDEO = 100;

    public static void startCameraActivity(Activity activity, int minTime, int maxTime, String color, int stateType, int requestCode) {
        Intent intent = new Intent(activity, CameraActivity.class);
        intent.putExtra(CameraActivity.MIN_TIME, minTime * 1000);
        intent.putExtra(CameraActivity.MAX_TIME, maxTime * 1000);
        intent.putExtra(CameraActivity.COLOR, color);
        intent.putExtra(CameraActivity.STATE_TYPE, stateType);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_camera);
        jCameraView = (JCameraView) findViewById(R.id.jcameraview);
        //设置视频保存路径
        jCameraView.setSaveVideoPath(getExternalFilesDir(null) + File.separator + "JCamera");
        jCameraView.setFeatures(getIntent().getIntExtra(STATE_TYPE, JCameraView.BUTTON_STATE_BOTH));
        jCameraView.setTip("拍摄时间90s-180s");
        jCameraView.setMediaQuality(JCameraView.MEDIA_QUALITY_MIDDLE);
        jCameraView.setDuration(getIntent().getIntExtra(MAX_TIME, 60 * 1000));
        jCameraView.setMinDuration(getIntent().getIntExtra(MIN_TIME, 3 * 1000));
        jCameraView.setColor(getIntent().getStringExtra(COLOR));
        jCameraView.setErrorLisenter(new ErrorListener() {
            @Override
            public void onError() {
                //错误监听
                Log.i(TAG, "onError: camera error");
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void AudioPermissionError() {
                Toast.makeText(CameraActivity.this, "给点录音权限可以?", Toast.LENGTH_SHORT).show();
            }
        });
        //JCameraView监听
        jCameraView.setJCameraLisenter(new JCameraListener() {
            @Override
            public void captureSuccess(Bitmap bitmap) {
                //获取图片bitmap
                Log.i(TAG, "captureSuccess: bitmap = " + bitmap.getWidth());
                String path = FileUtil.saveBitmap("JCamera", bitmap);
                Intent intent = new Intent();
                intent.putExtra("type", "capture");
                intent.putExtra("picturePath", path);
                intent.putExtra("thumbnailPath", path);
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void recordSuccess(final String url, Bitmap firstFrame) {

                //获取视频路径
                final String path = FileUtil.saveBitmap("JCamera", firstFrame);
                Log.i(TAG, " recordSuccess: url = " + url + ", Bitmap = " + path);

                final String destPath = getExternalFilesDir(null) + File.separator + "JCamera" + File.separator + "video_" + System.currentTimeMillis() + ".mp4";
                final VideoCompress.VideoCompressTask task = VideoCompress.compressVideoLow(url, destPath, new VideoCompress.CompressListener() {
                    @Override
                    public void onStart() {
                        LoadingManager.showProgress(CameraActivity.this, String.format(getResources().getString(R.string.str_updata_wait), "0.00%"));
                    }

                    @Override
                    public void onSuccess() {
                        LoadingManager.hideLoadingDialog(CameraActivity.this);
                        Intent intent = new Intent();
                        intent.putExtra("videoPath", destPath);
                        intent.putExtra("coverPath", path);
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                    @Override
                    public void onFail() {
                        LoadingManager.hideProgress(CameraActivity.this);
                    }

                    @Override
                    public void onProgress(float percent) {
                        Log.i(TAG, "onProgress: "+String.valueOf(percent) + "%");
                        DecimalFormat decimalFormat = new DecimalFormat("0.00");
                        String strPercent = decimalFormat.format(percent);
                        LoadingManager.updateProgress(CameraActivity.this, String.format(getResources().getString(R.string.str_updata_wait), strPercent + "%"));
                    }
                });
                LoadingManager.OnDismissListener(CameraActivity.this, new LoadingManager.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        task.cancel(true);
                    }
                });


            }
        });

        jCameraView.setLeftClickListener(new ClickListener() {
            @Override
            public void onClick() {
                try {
                    Class crop = Class.forName("com.media.MediaActivity");
                    Intent intent = new Intent(CameraActivity.this, crop);
                    intent.putExtra("type", "video");
                    startActivityForResult(intent, RESULT_VIDEO);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        jCameraView.setRightClickListener(new ClickListener() {
            @Override
            public void onClick() {
            }
        });

        Log.i(TAG, DeviceUtil.getDeviceModel());

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //全屏显示
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        jCameraView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        jCameraView.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_VIDEO:
                    if (data != null) {
                        String videoPath = data.getStringExtra("video");
                        String coverPath = data.getStringExtra("cover");
                        Intent intent = new Intent();
                        intent.putExtra("videoPath", videoPath);
                        intent.putExtra("coverPath", coverPath);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                    break;
            }
        }
    }

    private int width = 0;
    private int height = 0;

    private void getVideoInfo(String mUri) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            FileInputStream inputStream = new FileInputStream(new File(mUri).getAbsolutePath());
            retriever.setDataSource(inputStream.getFD());
            width = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
            height = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
        } catch (Exception ex) {
            ex.getMessage();
        } finally {
            retriever.release();
        }

    }
}

