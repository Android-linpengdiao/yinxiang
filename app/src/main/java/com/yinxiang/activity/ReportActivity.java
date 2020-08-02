package com.yinxiang.activity;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import com.baselibrary.Constants;
import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.ToastUtils;
import com.baselibrary.view.ProgressView;
import com.media.MediaActivity;
import com.media.image.ImageModel;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.callbacks.StringCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.okhttp.utils.APIUrls;
import com.okhttp.utils.OkHttpUtils;
import com.yinxiang.R;
import com.yinxiang.adapter.ImageAdapter;
import com.yinxiang.databinding.ActivityReportBinding;
import com.yinxiang.view.GridItemDecoration;
import com.yinxiang.view.OnClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

public class ReportActivity extends BaseActivity{
    private static final String TAG = "ReportActivity";
    private ActivityReportBinding binding;
    private int videoId;

    private ImageAdapter imageAdapter;
    private List<String> images = new ArrayList<>();
    private final static int REQUEST_MEDIA = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report);

        if (getIntent().getExtras() != null) {
            videoId = getIntent().getExtras().getInt("videoId");
        }

        images.add("");
        imageAdapter = new ImageAdapter(this);
        binding.imageRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        GridItemDecoration.Builder builder = new GridItemDecoration.Builder(this);
        builder.color(R.color.transparent);
        builder.size(CommonUtil.dip2px(this, 10));
        binding.imageRecyclerView.addItemDecoration(new GridItemDecoration(builder));
        binding.imageRecyclerView.setAdapter(imageAdapter);
        imageAdapter.refreshData(images);
        imageAdapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                if (object instanceof Integer && (int) object == 0) {
                    if (imageAdapter.getList().size() <= 5) {
                        Intent intent = new Intent(ReportActivity.this, MediaActivity.class);
                        intent.putExtra("number", 6 - imageAdapter.getList().size());
                        intent.putExtra("type", ImageModel.TYPE_IMAGE);
                        intent.putExtra("compressor", false);
                        startActivityForResult(intent, REQUEST_MEDIA);
                    } else {
                        ToastUtils.showShort(ReportActivity.this, "最多选五张");
                    }
                }

            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });
        binding.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = binding.tvContent.getText().toString().trim();
                if (!CommonUtil.isBlank(content)) {
                    if (images.size() > 1) {
                        uploadFiles(content);
                    } else {
                        homePageVideosReport(content, "");
                    }
                } else {
                    ToastUtils.showShort(ReportActivity.this, "请输入举报描述文字");
                }
            }
        });
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private void homePageVideosReport(String content, String img) {
        SendRequest.homePageVideosReport(getUserInfo().getData().getId(), videoId, img, content, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    if (!CommonUtil.isBlank(response)) {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.optInt("code") == 200) {
                            ToastUtils.showShort(getApplication(), "提交成功");
                            finish();
                        } else {
                            ToastUtils.showShort(getApplication(), jsonObject.optString("msg"));
                        }
                    } else {
                        ToastUtils.showShort(getApplication(), "请求失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShort(getApplication(), "请求失败");
                }
            }
        });
    }



    private ProgressView progressView;
    private int progress = 0;

    List<String> list = new ArrayList<>();

    private void uploadFiles(final String content) {
        list.addAll(images.subList(1, images.size()));
        progressView = new ProgressView(ReportActivity.this, R.style.ProgressDialogTheme);
        progress = 0;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressView.show();
                progressView.updateProgress((String.format(Constants.str_updata_wait, progress + "/" + list.size())));
            }
        });
        for (int i = 0; i < list.size(); i++) {
            final int finalI = i;
            OkHttpUtils.post()
                    .addFile("file", list.get(i).substring(list.get(i).lastIndexOf("/") + 1), new File(list.get(i)))
                    .url(APIUrls.url_fileUpload)
                    .build()
                    .execute(new StringCallback() {

                        @Override
                        public void onBefore(Request request, int id) {

                        }

                        @Override
                        public void onAfter(int id) {

                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                progress += 1;
                                JSONObject object = new JSONObject(response);
                                String url = object.optString("data");
                                list.set(finalI, url);
                                progressView.updateProgress((String.format(Constants.str_updata_wait, progress + "/" + list.size())));
                                if (progress == list.size()) {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (progressView != null) {
                                                        progressView.dismiss();
                                                    }
                                                }
                                            });
                                            homePageVideosReport(content, TextUtils.join(",", list));
                                        }
                                    }, 500);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void inProgress(float progress, long total, int id) {
                            super.inProgress(progress, total, id);
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_MEDIA:
                    if (data != null) {
                        String resultJson = data.getStringExtra("resultJson");
                        try {
                            JSONObject object = new JSONObject(resultJson);
                            if (object.optString("type").equals(ImageModel.TYPE_IMAGE)) {
                                JSONArray files = object.optJSONArray("imageList");
                                if (files.length() > 0) {
                                    for (int i = 0; i < files.length(); i++) {
                                        images.add(String.valueOf(files.get(i)));
                                    }
                                    binding.imageRecyclerView.setVisibility(View.VISIBLE);
                                    imageAdapter.refreshData(images);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}