package com.yinxiang.activity;

import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.View;

import com.baselibrary.UserInfo;
import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.GlideLoader;
import com.baselibrary.utils.ToastUtils;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.api.model.session.SessionCustomization;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.callbacks.StringCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.okhttp.utils.APIUrls;
import com.yinxiang.MyApplication;
import com.yinxiang.R;
import com.yinxiang.adapter.UserHomeWorkAdapter;
import com.yinxiang.databinding.ActivityUserHomeBinding;
import com.yinxiang.model.WorkData;
import com.yinxiang.utils.DialogUtil;
import com.yinxiang.view.GridItemDecoration;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Call;

public class UserHomeActivity extends BaseActivity implements View.OnClickListener {
    private ActivityUserHomeBinding binding;
    private UserHomeWorkAdapter adapter;
    private int uid;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_home);

        uid = getIntent().getIntExtra("uid", 0);
        binding.back.setOnClickListener(this);
        binding.userIcon.setOnClickListener(this);
        binding.tvIsFollow.setOnClickListener(this);

        adapter = new UserHomeWorkAdapter(this);
        binding.recyclerView.setNestedScrollingEnabled(false);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        GridItemDecoration.Builder builder = new GridItemDecoration.Builder(this);
        builder.color(R.color.transparent);
        builder.size(CommonUtil.dip2px(this, 15));
        binding.recyclerView.addItemDecoration(new GridItemDecoration(builder));
        binding.recyclerView.setAdapter(adapter);

        initData();

    }

    private void initData() {
        SendRequest.personInformInfo(uid, new GenericsCallback<UserInfo>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(UserInfo response, int id) {
                if (response.getCode() == 200 && response.getData() != null) {
                    userInfo = response;
                    initView(response);
                }
            }

        });
        homePagePersonIsFollow();
        centerSelfWork();

    }

    private void homePagePersonIsFollow() {
        SendRequest.homePagePersonIsFollow(getUserId(), uid, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    if (!CommonUtil.isBlank(response)) {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.optInt("code") == 200 &&
                                !CommonUtil.isBlank(jsonObject.optJSONObject("data").optString("id"))) {
                            binding.tvIsFollow.setSelected(!binding.tvIsFollow.isSelected());
                            binding.tvIsFollow.setText(binding.tvIsFollow.isSelected() ? "已关注" : "关注");
                        }
                    } else {
                        ToastUtils.showShort(UserHomeActivity.this, "请求失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShort(UserHomeActivity.this, "请求失败");
                }

            }
        });
    }

    private void homePagePersonFollow() {
        String url = binding.tvIsFollow.isSelected() ? APIUrls.url_homePagePersonUnFollow : APIUrls.url_homePagePersonFollow;
        SendRequest.homePagePersonFollow(getUserInfo().getData().getId(), uid, url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    if (!CommonUtil.isBlank(response)) {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.optInt("code") == 200) {
                            binding.tvIsFollow.setSelected(!binding.tvIsFollow.isSelected());
                            binding.tvIsFollow.setText(binding.tvIsFollow.isSelected() ? "已关注" : "关注");
                        } else {
                            ToastUtils.showShort(UserHomeActivity.this, jsonObject.optString("msg"));
                        }
                    } else {
                        ToastUtils.showShort(UserHomeActivity.this, "请求失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShort(UserHomeActivity.this, "请求失败");
                }

            }
        });
    }

    private void centerSelfWork() {
        SendRequest.personInformWorks(uid, 100, new GenericsCallback<WorkData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(WorkData response, int id) {
                if (response.getCode() == 200 && response.getData() != null && response.getData().getData() != null) {
                    adapter.refreshData(response.getData().getData());
                } else {
                    ToastUtils.showShort(UserHomeActivity.this, response.getMsg());
                }
            }

        });
    }

    private void initView(UserInfo userInfo) {

        binding.tvIsFollow.setOnClickListener(this);
        binding.tvStartChat.setOnClickListener(this);

        binding.userName.setText(userInfo.getData().getName());
        binding.userAge.setText(String.valueOf(userInfo.getData().getAge()));
        binding.userDesc.setText(userInfo.getData().getDesc());
        binding.touristId.setText("引享号：" + userInfo.getData().getTourist_id());
        binding.userAddr.setText(userInfo.getData().getAddr());
        binding.userVip.setVisibility(userInfo.getData().getIs_vip() == 1 ? View.VISIBLE : View.GONE);
        binding.isVip.setVisibility(userInfo.getData().getIs_vip() == 1 ? View.VISIBLE : View.GONE);
        binding.userLevel.setText("Lv." + userInfo.getData().getLevel());
        binding.creditView.setText("Lv." + userInfo.getData().getCredit());
        binding.fanNumber.setText(String.valueOf(userInfo.getData().getFan_number()));
        binding.followNumber.setText(String.valueOf(userInfo.getData().getFollow_number()));
        GlideLoader.LoderCircleImage(UserHomeActivity.this, userInfo.getData().getAvatar(), binding.userIcon);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.user_icon:
                if (userInfo.getCode() == 200 && userInfo.getData() != null) {
                    DialogUtil.getInstance().showMoreImageView(UserHomeActivity.this, new ArrayList<>(Arrays.asList(userInfo.getData().getAvatar())), 0);
                }
                break;
            case R.id.tv_startChat:
                if (getUserId(true) > 0) {
                    if (userInfo.getCode() == 200 && userInfo.getData() != null) {
                        NimUIKit.startChatting(this,
                                !CommonUtil.isBlank(userInfo.getData().getYunxin_accid()) ? userInfo.getData().getYunxin_accid() : userInfo.getData().getPhone(),
                                SessionTypeEnum.P2P, getRobotCustomization(), null);
                    }
                }
                break;
            case R.id.tv_is_follow:
                if (!CommonUtil.isBlank(uid)) {
                    if (getUserId(true) > 0) {
                        homePagePersonFollow();
                    }
                }
                break;
        }
    }

    private static final String TAG = "UserHomeActivity";
    private static SessionCustomization robotCustomization;

    private static SessionCustomization getRobotCustomization() {
        if (robotCustomization == null) {
            robotCustomization = new SessionCustomization() {

                // 由于需要Activity Result， 所以重载该函数。
                @Override
                public void onActivityResult(final Activity activity, int requestCode, int resultCode, Intent data) {
                    super.onActivityResult(activity, requestCode, resultCode, data);
                    Log.i(TAG, "onActivityResult: ");
                }

                @Override
                public MsgAttachment createStickerAttachment(String category, String item) {
                    Log.i(TAG, "createStickerAttachment: ");
                    return null;
                }
            };
//            // 定制ActionBar右边的按钮，可以加多个
//            ArrayList<SessionCustomization.OptionsButton> buttons = new ArrayList<>();
//            SessionCustomization.OptionsButton cloudMsgButton = new SessionCustomization.OptionsButton() {
//
//                @Override
//                public void onClick(Context context, View view, String sessionId) {
////                    initPopuptWindow(context, view, sessionId, SessionTypeEnum.P2P);
//                }
//            };
////            cloudMsgButton.iconId = R.drawable.nim_ic_messge_history;
//            SessionCustomization.OptionsButton infoButton = new SessionCustomization.OptionsButton() {
//
//                @Override
//                public void onClick(Context context, View view, String sessionId) {
////                    RobotProfileActivity.start(context, sessionId); //打开聊天信息
//                }
//            };
//            infoButton.iconId = R.drawable.ic_camera;
//            infoButton.iconId = R.drawable.ic_camera;
////            buttons.add(cloudMsgButton);
//            buttons.add(infoButton);
//            robotCustomization.buttons = buttons;
        }
        return robotCustomization;
    }
}