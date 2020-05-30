package com.yinxiang.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.baselibrary.utils.GlideLoader;
import com.baselibrary.utils.PermissionUtils;
import com.cjt2325.cameralibrary.CameraActivity;
import com.cjt2325.cameralibrary.JCameraView;
import com.media.MediaActivity;
import com.media.image.ImageModel;
import com.yinxiang.R;
import com.yinxiang.databinding.ActivityReleaseBinding;

public class ReleaseActivity extends BaseActivity implements View.OnClickListener {

    private ActivityReleaseBinding binding;
    private final static int REQUEST_WXCAMERA = 100;
    private static final int REQUEST_VIDEO = 200;
    private final static int REQUEST_CTYPE = 300;
    private final static int REQUEST_ATYPE = 400;
    private String videoPath;
    private String coverPath;
    private String competitionType;
    private String associationType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewData(R.layout.activity_release);
        binding.close.setOnClickListener(this);
        binding.releaseVideoView.setOnClickListener(this);
        binding.tvCompetition.setOnClickListener(this);
        binding.tvAssociation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.release_video_view:
                AlertDialog.Builder dialog = new AlertDialog.Builder(ReleaseActivity.this);
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
                break;
            case R.id.tv_competition:
                startActivityForResult(new Intent(ReleaseActivity.this, SelectionCompetitionActivity.class), REQUEST_CTYPE);
                break;
            case R.id.tv_association:
                startActivityForResult(new Intent(ReleaseActivity.this, SelectionAssociationActivity.class), REQUEST_ATYPE);
                break;
            case R.id.close:
                finish();
                break;
        }
    }

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
                    PermissionUtils.openAppDetails(ReleaseActivity.this, "储存");
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
                    PermissionUtils.openAppDetails(ReleaseActivity.this, "储存和相机");
                }
                break;
        }
    }

    private void openMedia() {
        Intent intent = new Intent(ReleaseActivity.this, MediaActivity.class);
        intent.putExtra("type", ImageModel.TYPE_VIDEO);
        startActivityForResult(intent, REQUEST_VIDEO);
    }

    private void openCamera() {
        int type = JCameraView.BUTTON_STATE_ONLY_RECORDER;
        int minTime = 10;
        int maxTime = 180;
        CameraActivity.startCameraActivity(ReleaseActivity.this, minTime, maxTime, "#44bf19", type, REQUEST_WXCAMERA);

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
                        GlideLoader.LoderLoadImage(ReleaseActivity.this, coverPath, binding.cover, 10);
                    }
                    break;
                case REQUEST_VIDEO:
                    if (data != null) {
                        videoPath = data.getStringExtra("video");
                        coverPath = data.getStringExtra("cover");
                        GlideLoader.LoderLoadImage(ReleaseActivity.this, coverPath, binding.cover, 10);
                    }
                    break;
                case REQUEST_CTYPE:
                    if (data != null) {
                        competitionType = data.getStringExtra("competitionType");
                        binding.tvCompetition.setText(competitionType);
                    }
                    break;
                case REQUEST_ATYPE:
                    if (data != null) {
                        associationType = data.getStringExtra("associationType");
                        binding.tvAssociation.setText(associationType);
                    }
                    break;
            }
        }
    }
}
