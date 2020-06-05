package com.yinxiang.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.StringCallback;
import com.yinxiang.R;
import com.yinxiang.databinding.ActivityEditTextBinding;

import org.json.JSONObject;

import okhttp3.Call;

public class EditTextActivity extends BaseActivity implements View.OnClickListener {

    private ActivityEditTextBinding binding;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_text);

        binding.back.setOnClickListener(this);
        binding.tvConfirm.setOnClickListener(this);
        if (getIntent().hasExtra("type")) {
            type = getIntent().getStringExtra("type");
            if (type.equals("userName")) {
                binding.title.setText("昵称");
                binding.edContent.setHint("请输入您的昵称");
            } else if (type.equals("userDesc")) {
                binding.title.setText("简介");
                binding.edContent.setHint("请输入您的简介");
            } else if (type.equals("clubDesc")) {
                binding.title.setText("社团简介");
                binding.edContent.setHint("请输入您的社团简介");
            }
        } else {
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_confirm:
                final String content = binding.edContent.getText().toString().trim();
                if (CommonUtil.isBlank(content)) {
                    ToastUtils.showShort(EditTextActivity.this, "请输入内容");
                    return;
                }
                Intent intent = new Intent();
                if (type.equals("userName")) {
                    intent.putExtra("userName", content);
                } else if (type.equals("userDesc")) {
                    intent.putExtra("userDesc", content);
                } else if (type.equals("clubDesc")) {
                    intent.putExtra("clubDesc", content);

                }
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}