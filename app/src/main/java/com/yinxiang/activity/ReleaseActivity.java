package com.yinxiang.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

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
import com.okhttp.callbacks.StringCallback;
import com.yinxiang.R;
import com.yinxiang.databinding.ActivityReleaseBinding;
import com.yinxiang.model.ClubData;
import com.yinxiang.model.HomeActives;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import okhttp3.Call;
import okhttp3.Request;

public class ReleaseActivity extends BaseActivity implements View.OnClickListener {

    private ActivityReleaseBinding binding;
    private final static int REQUEST_WXCAMERA = 100;
    private static final int REQUEST_VIDEO = 200;
    private final static int REQUEST_CTYPE = 300;
    private final static int REQUEST_ATYPE = 400;
    private String videoPath;
    private String coverPath;
    private HomeActives.DataBean homeDataBean;
    private ClubData.DataBean clubDataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewData(R.layout.activity_release);
        binding.close.setOnClickListener(this);
        binding.releaseVideoView.setOnClickListener(this);
        binding.tvCompetition.setOnClickListener(this);
        binding.tvAssociation.setOnClickListener(this);
        binding.tvConfirm.setOnClickListener(this);

        int typeId = (new Random()).nextInt(2);
        Log.i(TAG, "onCreate: " + typeId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_confirm:
                publishWork(CommonUtil.getVideoCoverListString().get(0), CommonUtil.getVideoListString().get(0), homeDataBean != null ? homeDataBean.getId() : 0, clubDataBean != null ? clubDataBean.getId() : 0);
                break;
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
                startActivityForResult(new Intent(ReleaseActivity.this, SelectionClubActivity.class), REQUEST_ATYPE);
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
                        homeDataBean = (HomeActives.DataBean) data.getSerializableExtra("homeActives");
                        if (homeDataBean!=null) {
                            binding.tvCompetition.setText(homeDataBean.getTitle() + "");
                        }
                    }
                    break;
                case REQUEST_ATYPE:
                    if (data != null) {
                        clubDataBean = (ClubData.DataBean) data.getSerializableExtra("clubDataBean");
                        if (clubDataBean != null) {
                            binding.tvAssociation.setText(clubDataBean.getName());
                        }
                    }
                    break;
            }
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
                        ToastUtils.showShort(ReleaseActivity.this, "封面上传失败");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private static final String TAG = "ReleaseActivity";

    private void createSecurityToken(final String coverUrl) {
        SendRequest.createSecurityToken(new StringCallback() {

            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                LoadingManager.showLoadingDialog(ReleaseActivity.this);
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                LoadingManager.hideLoadingDialog(ReleaseActivity.this);
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
                LoadingManager.updateProgress(ReleaseActivity.this, String.format(Constants.str_updata_wait, temp + "%"));
            }
        });

        LoadingManager.showProgress(ReleaseActivity.this, String.format(Constants.str_updata_wait, "0%"));
        final OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                LoadingManager.hideProgress(ReleaseActivity.this);
                String videoUrl = "http://" + request.getBucketName() + ".oss-cn-beijing.aliyuncs.com/" + request.getObjectKey();
                Log.i(TAG, "onSuccess: " + videoUrl);
//                publishWork(coverUrl, videoUrl);
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                LoadingManager.hideProgress(ReleaseActivity.this);
                ToastUtils.showShort(ReleaseActivity.this, "上传视频失败");
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
        LoadingManager.OnDismissListener(ReleaseActivity.this, new LoadingManager.OnDismissListener() {
            @Override
            public void onDismiss() {
                task.cancel(); // Cancel the task
            }
        });

        // task.cancel(); // Cancel the task
        // task.waitUntilFinished(); // Wait till the task is finished
    }

    private void publishWork(String coverUrl, String videoUrl, int active_id, int club_id) {
        SendRequest.publishWork(getUserInfo().getData().getId(), coverUrl, videoUrl, active_id, club_id, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showShort(ReleaseActivity.this, "发布失败");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optInt("code") == 200) {
                        ToastUtils.showShort(ReleaseActivity.this, "发布成功");
                        finish();
                    } else {
                        ToastUtils.showShort(ReleaseActivity.this, "发布失败 :" + jsonObject.optString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.showShort(ReleaseActivity.this, "发布失败");
                }
            }
        });
    }
}
