package com.yinxiang.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.FileUtils;
import com.baselibrary.utils.GlideLoader;
import com.baselibrary.utils.ToastUtils;
import com.media.MediaActivity;
import com.media.image.ImageModel;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.callbacks.StringCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.yinxiang.R;
import com.yinxiang.databinding.ActivityCreateClubBinding;
import com.yinxiang.model.ClubData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import okhttp3.Call;

public class CreateClubActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "CreateClubActivity";
    private ActivityCreateClubBinding binding;

    private static final int REQUEST_DESC = 100;
    private static final int REQUEST_LOGO = 200;
    private static final int REQUEST_LICENSE = 300;
    private static final int REQUEST_IDCARD_FRONT = 400;
    private static final int REQUEST_IDCARD_BACK = 500;

    private String logo;
    private String license;
    private String idcard_front;
    private String idcard_back;
    private String phone;
    private String authCode;
    private String desc;
    private String name;

    private ClubData clubData;

    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_club);

        binding.back.setOnClickListener(this);
        binding.etClubName.setOnClickListener(this);
        binding.ivClubLogo.setOnClickListener(this);
        binding.ivLicense.setOnClickListener(this);
        binding.ivIdcardFront.setOnClickListener(this);
        binding.ivIdcardBack.setOnClickListener(this);
        binding.etPhone.setOnClickListener(this);
        binding.tvSendCode.setOnClickListener(this);
        binding.tvConfirm.setOnClickListener(this);

        initData(2);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_idcard_back:
                openMedia(REQUEST_IDCARD_BACK);
                break;
            case R.id.iv_idcard_front:
                openMedia(REQUEST_IDCARD_FRONT);
                break;
            case R.id.iv_license:
                openMedia(REQUEST_LICENSE);
                break;
            case R.id.iv_club_logo:
                openMedia(REQUEST_LOGO);
                break;
            case R.id.tv_send_code:
                createClubAuthCode();
                break;
            case R.id.et_phone:

                break;
            case R.id.et_club_name:
                intent = new Intent(CreateClubActivity.this, EditTextActivity.class);
                intent.putExtra("type", "clubDesc");
                startActivityForResult(intent, REQUEST_DESC);
                break;
            case R.id.tv_confirm:
                name = binding.etClubName.getText().toString().trim();
                phone = binding.etPhone.getText().toString().trim();
                authCode = binding.etCode.getText().toString().trim();
                desc = name;

                if (CommonUtil.isBlank(name)) {
                    ToastUtils.showShort(CreateClubActivity.this, "请输入社团名称");
                    return;
                }
                if (CommonUtil.isBlank(logo)) {
                    ToastUtils.showShort(CreateClubActivity.this, "请上传社团LOGO");
                    return;
                }
                if (CommonUtil.isBlank(license)) {
                    ToastUtils.showShort(CreateClubActivity.this, "上传您企业营业执照");
                    return;
                }
                if (CommonUtil.isBlank(idcard_front)) {
                    ToastUtils.showShort(CreateClubActivity.this, "上传您的身份证正面照片");
                    return;
                }
                if (CommonUtil.isBlank(idcard_back)) {
                    ToastUtils.showShort(CreateClubActivity.this, "上传您的身份证背面照片");
                    return;
                }
                if (CommonUtil.isBlank(phone)) {
                    ToastUtils.showShort(CreateClubActivity.this, "请输入手机号码");
                    return;
                }
                if (clubData == null && CommonUtil.isBlank(authCode)) {
                    ToastUtils.showShort(CreateClubActivity.this, "请输入短信验证码");
                    return;
                }
                if (clubData != null && clubData.getData().get(0) != null) {
                    channelEditClub(clubData.getData().get(0).getId(),name, logo, license, idcard_front, idcard_back, phone, authCode, desc);
                } else {
                    channelCreateClub(name, logo, license, idcard_front, idcard_back, phone, authCode, desc);
                }
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private void initView(int status, ClubData.DataBean dataBean) {
        if (dataBean.getStatus() == 2) {
            binding.codeView.setVisibility(View.GONE);
            binding.clubStatusView.setVisibility(View.VISIBLE);
            binding.clubStatus.setText(dataBean.getRefuse());
            binding.tvConfirm.setText("立即修改");
            binding.etPhone.setEnabled(false);
        } else if (dataBean.getStatus() == 3) {
            binding.codeView.setVisibility(View.GONE);
            binding.bottomView.setVisibility(View.GONE);
            binding.clubStatusView.setVisibility(View.VISIBLE);
            binding.clubStatus.setText("正在审核中，请您耐心等候");
            binding.etClubName.setEnabled(false);
            binding.etPhone.setEnabled(false);
            binding.ivClubLogo.setEnabled(false);
            binding.ivIdcardBack.setEnabled(false);
            binding.ivIdcardFront.setEnabled(false);
            binding.ivLicense.setEnabled(false);
        }
        binding.etClubName.setText(dataBean.getName());
        binding.etPhone.setText(dataBean.getPhone());
        GlideLoader.LoderImage(CreateClubActivity.this, dataBean.getLogo(), binding.ivClubLogo, 8);
        GlideLoader.LoderImage(CreateClubActivity.this, dataBean.getLicense(), binding.ivLicense, 8);
        GlideLoader.LoderImage(CreateClubActivity.this, dataBean.getIdcard_front(), binding.ivIdcardFront, 8);
        GlideLoader.LoderImage(CreateClubActivity.this, dataBean.getIdcard_back(), binding.ivIdcardBack, 8);

        if (clubData != null) {
            logo = dataBean.getLogo();
            license = dataBean.getLicense();
            idcard_front = dataBean.getIdcard_front();
            idcard_back = dataBean.getIdcard_back();
        }

    }

    private void initData(final int status) {
        SendRequest.channelClub(getUserInfo().getData().getId(), status, new GenericsCallback<ClubData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(ClubData response, int id) {
                if (response != null && response.getCode() == 200 && response.getData() != null) {
                    if (response.getData().size() > 0) {
                        clubData = response;
                        initView(status, response.getData().get(0));
                    } else {
                        if (status == 2) {
                            initData(3);
                        }
                    }
                }
            }

        });
    }

    private void createClubAuthCode() {
        String phone = binding.etPhone.getText().toString().trim();
        if (phone.length() < 11) {
            ToastUtils.showShort(CreateClubActivity.this, "手机号码不正确");
            return;
        }
        SendRequest.phoneCode(phone, "club.create", new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject json = new JSONObject(response);
                    if (json.optInt("code") == 200) {
                        binding.tvSendCode.setEnabled(false);
                        timer = new CountDownTimer(60000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                binding.tvSendCode.setText(millisUntilFinished / 1000 + "");
                            }

                            @Override
                            public void onFinish() {
                                binding.tvSendCode.setEnabled(true);
                                binding.tvSendCode.setText("获取验证码");
                            }
                        }.start();
                        ToastUtils.showShort(CreateClubActivity.this, "验证码发送成功");
                    } else {
                        String msg = json.optString("msg");
                        ToastUtils.showShort(getApplication(), msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void channelCreateClub(String name, String logo, String license, String idcard_front, String idcard_back, String phone, String authCode, String desc) {
        SendRequest.channelCreateClub(getUserInfo().getData().getId(), name, logo, license, idcard_front, idcard_back, phone, authCode, desc, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!CommonUtil.isBlank(response)) {
                        if (jsonObject.optInt("code") == 200) {
                            ToastUtils.showShort(CreateClubActivity.this, "已提交审核");
                            finish();
                        } else {
                            ToastUtils.showShort(CreateClubActivity.this, jsonObject.optString("msg"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShort(CreateClubActivity.this, "请求失败");
                }
            }
        });
    }

    private void channelEditClub(int clubId,String name, String logo, String license, String idcard_front, String idcard_back, String phone, String authCode, String desc) {
        SendRequest.channelEditClub(getUserInfo().getData().getId(),clubId, name, logo, license, idcard_front, idcard_back, phone, authCode, desc, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!CommonUtil.isBlank(response)) {
                        if (jsonObject.optInt("code") == 200) {
                            ToastUtils.showShort(CreateClubActivity.this, "已提交审核");
                            finish();
                        } else {
                            ToastUtils.showShort(CreateClubActivity.this, jsonObject.optString("msg"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShort(CreateClubActivity.this, "请求失败");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                String resultJson = data.getStringExtra("resultJson");
                if (!CommonUtil.isBlank(resultJson)) {
                    try {
                        JSONObject object = new JSONObject(resultJson);
                        if (object.optString("type").equals(ImageModel.TYPE_IMAGE)) {
                            JSONArray files = object.optJSONArray("imageList");
                            if (files.length() > 0) {
                                uploadFile(requestCode, String.valueOf(files.get(0)));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    uploadFile(requestCode, outputImage.getPath());
                }
            }
        }
    }


    private void uploadFile(final int requestCode, String file) {
        SendRequest.fileUpload(file, file.substring(file.lastIndexOf("/") + 1), new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject object = new JSONObject(response);
                    String url = object.optString("data");
                    if (requestCode == REQUEST_LOGO) {
                        logo = url;
                        GlideLoader.LoderImage(CreateClubActivity.this, url, binding.ivClubLogo, 8);
                    } else if (requestCode == REQUEST_LICENSE) {
                        license = url;
                        GlideLoader.LoderImage(CreateClubActivity.this, url, binding.ivLicense, 8);
                    } else if (requestCode == REQUEST_IDCARD_FRONT) {
                        idcard_front = url;
                        GlideLoader.LoderImage(CreateClubActivity.this, url, binding.ivIdcardFront, 8);
                    } else if (requestCode == REQUEST_IDCARD_BACK) {
                        idcard_back = url;
                        GlideLoader.LoderImage(CreateClubActivity.this, url, binding.ivIdcardBack, 8);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void openMedia(final int requestCode) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(CreateClubActivity.this);
        dialog.setTitle("");
        dialog.setItems(R.array.media_list_dialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Intent intent = new Intent(CreateClubActivity.this, MediaActivity.class);
                        intent.putExtra("type", ImageModel.TYPE_IMAGE);
                        intent.putExtra("number", 1);
                        startActivityForResult(intent, requestCode);
                        break;
                    case 1:
                        openCamera(requestCode);
                        break;
                    case 2:

                        break;
                }
            }
        });
        dialog.show();
    }

    private File outputImage;

    private void openCamera(int requestCode) {
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
            startActivityForResult(intent, requestCode);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

}
