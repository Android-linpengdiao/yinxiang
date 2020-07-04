package com.yinxiang.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;

import com.baselibrary.utils.CommonUtil;
import com.netease.nim.uikit.api.model.session.SessionCustomization;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.yinxiang.MyApplication;
import com.yinxiang.model.CityData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class IMManager {

    private static IMManager mInstance;
    private Context context;

    public synchronized static IMManager getInstance() {
        if (mInstance == null) {
            synchronized (IMManager.class) {
                if (mInstance == null) {
                    mInstance = new IMManager();
                }
            }
        }
        return mInstance;
    }

    public IMManager() {
        this.context = MyApplication.getInstance();
    }

    private SessionCustomization robotCustomization;

    private SessionCustomization getRobotCustomization() {
        if (robotCustomization == null) {
            robotCustomization = new SessionCustomization() {

                // 由于需要Activity Result， 所以重载该函数。
                @Override
                public void onActivityResult(final Activity activity, int requestCode, int resultCode, Intent data) {
                    super.onActivityResult(activity, requestCode, resultCode, data);

                }

                @Override
                public MsgAttachment createStickerAttachment(String category, String item) {
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
