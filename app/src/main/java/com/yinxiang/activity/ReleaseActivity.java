package com.yinxiang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.baselibrary.utils.GlideLoader;
import com.cjt2325.cameralibrary.CameraActivity;
import com.cjt2325.cameralibrary.JCameraView;
import com.yinxiang.R;
import com.yinxiang.databinding.ActivityReleaseBinding;

public class ReleaseActivity extends BaseActivity implements View.OnClickListener {

    private ActivityReleaseBinding binding;
    private final static int REQUEST_WXCAMERA = 100;
    private String videoPath;
    private String coverPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewData(R.layout.activity_release);
        binding.close.setOnClickListener(this);
        binding.releaseVideoView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.release_video_view:
                int type = JCameraView.BUTTON_STATE_ONLY_RECORDER;
                int minTime = 10;
                int maxTime = 180;
                CameraActivity.startCameraActivity(ReleaseActivity.this, minTime, maxTime, "#44bf19", type, REQUEST_WXCAMERA);
                break;
            case R.id.close:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_WXCAMERA:
                    if (data!=null){
                         videoPath = data.getStringExtra("videoPath");
                         coverPath = data.getStringExtra("coverPath");
                        GlideLoader.LoderImage(ReleaseActivity.this,coverPath,binding.cover,10);
                    }

                    break;
            }
        }
    }
}
