package com.yinxiang.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.FileUtils;
import com.baselibrary.utils.GlideLoader;
import com.baselibrary.utils.PermissionUtils;
import com.baselibrary.utils.ToastUtils;
import com.media.MediaActivity;
import com.media.image.ImageModel;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.StringCallback;
import com.yinxiang.R;
import com.yinxiang.databinding.ActivityEditorBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import okhttp3.Call;

public class EditorActivity extends BaseActivity implements View.OnClickListener {

    private ActivityEditorBinding binding;
    private static final int REQUEST_NAME = 100;
    private static final int REQUEST_IMAGE = 200;
    private static final int REQUEST_CAMERA = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_editor);

        binding.back.setOnClickListener(this);
        binding.userIcon.setOnClickListener(this);
        binding.userName.setOnClickListener(this);
        binding.userDesc.setOnClickListener(this);
        binding.userSex.setOnClickListener(this);
        binding.cityView.setOnClickListener(this);

        initView(getUserInfo().getData().getAvatar(), getUserInfo().getData().getName());
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView(String avatar, String name) {
        binding.userName.setText(name);
        GlideLoader.LoderCircleImage(this, avatar, binding.userIcon);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.city_view:
                openActivity(CitySelectionActivity.class);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.user_icon:

                AlertDialog.Builder dialog = new AlertDialog.Builder(EditorActivity.this);
                dialog.setTitle("");
                dialog.setItems(R.array.media_list_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                if (checkPermissionsAll(PermissionUtils.STORAGE, REQUEST_IMAGE)) {
                                    Intent intent = new Intent(EditorActivity.this, MediaActivity.class);
                                    intent.putExtra("type", ImageModel.TYPE_IMAGE);
                                    intent.putExtra("number", 1);
                                    startActivityForResult(intent, REQUEST_IMAGE);
                                }
                                break;
                            case 1:
                                if (checkPermissionsAll(PermissionUtils.CAMERA, REQUEST_CAMERA)) {
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
            case R.id.user_name:
                intent = new Intent(EditorActivity.this, EditTextActivity.class);
                intent.putExtra("type", "userName");
                startActivityForResult(intent, REQUEST_NAME);
                break;
            case R.id.user_desc:
                intent = new Intent(EditorActivity.this, EditTextActivity.class);
                intent.putExtra("type", "userDesc");
                startActivityForResult(intent, REQUEST_NAME);
                break;
            case R.id.user_sex:
                setSexDialog();
                break;
        }
    }

    private void setSexDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(EditorActivity.this).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.getDecorView().setBackgroundColor(getResources().getColor(R.color.transparent));
        window.setContentView(R.layout.view_sex_dialog_alert);
        RadioGroup radioGroupView = window.findViewById(R.id.radio_group_view);
        radioGroupView.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_button_boy:
                        binding.userSex.setText("男");
                        break;
                    case R.id.radio_button_girl:
                        binding.userSex.setText("女");
                        break;
                    default:
                        break;
                }
            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean granted = true;
        switch (requestCode) {
            case REQUEST_IMAGE:
                for (int i = 0; i < PermissionUtils.storage.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        granted = false;
                        break;
                    }
                }
                if (granted) {
                    Intent intent = new Intent(EditorActivity.this, MediaActivity.class);
                    intent.putExtra("type", ImageModel.TYPE_IMAGE);
                    intent.putExtra("number", 1);
                    startActivityForResult(intent, REQUEST_IMAGE);
                } else {
                    PermissionUtils.openAppDetails(EditorActivity.this, "储存");
                }
                break;
            case REQUEST_CAMERA:
                for (int i = 0; i < PermissionUtils.camera.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        granted = false;
                        break;
                    }
                }
                if (granted) {
                    openCamera();
                } else {
                    PermissionUtils.openAppDetails(EditorActivity.this, "储存和相机");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_IMAGE:
                    if (data != null) {
                        String resultJson = data.getStringExtra("resultJson");
                        try {
                            JSONObject object = new JSONObject(resultJson);
                            if (object.optString("type").equals(ImageModel.TYPE_IMAGE)) {
                                JSONArray files = object.optJSONArray("imageList");
                                if (files.length() > 0) {
                                    uploadFile(String.valueOf(files.get(0)));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case REQUEST_CAMERA:
                    uploadFile(outputImage.getPath());
                    break;
                case REQUEST_NAME:
                    if (data != null) {
                        String name = data.getStringExtra("name");
                        initView(getUserInfo().getData().getAvatar(), name);
                    }
                    break;
            }
        }
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
                    editPersonal(url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void editPersonal(final String avatar) {
        SendRequest.editPersonal(getUserInfo().getData().getId(), getUserInfo().getData().getId(), avatar, getUserInfo().getData().getName(), new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    if (!CommonUtil.isBlank(response)) {
                        JSONObject jsonObject = new JSONObject(response);
                        ToastUtils.showShort(EditorActivity.this, jsonObject.optString("msg"));
                        if (jsonObject.optInt("code") == 200) {
                            baseInfo();
                            initView(avatar, getUserInfo().getData().getName());
                        }
                    } else {
                        ToastUtils.showShort(EditorActivity.this, "编辑失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShort(EditorActivity.this, "编辑失败");
                }

            }
        });
    }

    private File outputImage;

    private void openCamera() {
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = FileUtils.createTempFile(fileName);
        if (null != file && file.exists()) {
            outputImage = file;
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //系统7.0打开相机权限处理
            if (Build.VERSION.SDK_INT >= 24) {
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
                Uri uri = getApplication().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            }
            startActivityForResult(intent, REQUEST_CAMERA);
        }
    }
}