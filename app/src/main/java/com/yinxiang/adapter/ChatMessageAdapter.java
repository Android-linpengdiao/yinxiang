package com.yinxiang.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.GlideLoader;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.api.model.session.SessionCustomization;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.yinxiang.R;
import com.yinxiang.databinding.ItemMessageLayoutBinding;
import com.yinxiang.view.OnClickListener;


public class ChatMessageAdapter extends BaseRecyclerAdapter<RecentContact, ItemMessageLayoutBinding> {

    private boolean selection = false;

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setSelection(boolean selection) {
        this.selection = selection;
    }

    public ChatMessageAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_message_layout;
    }

    @Override
    protected void onBindItem(ItemMessageLayoutBinding binding, final RecentContact recentContact, final int position) {
        if (mList != null && mList.size() > 0) {
            binding.tvTitle.setText(recentContact.getFromNick());
            binding.tvDesc.setText(recentContact.getContent());
            binding.tvTime.setText(CommonUtil.getDateToString(String.valueOf(recentContact.getTime())));
            binding.messageNews.setVisibility(recentContact.getUnreadCount()>0?View.VISIBLE:View.GONE);
            binding.messageNews.setText(String.valueOf(recentContact.getUnreadCount()));
            GlideLoader.LoderLoadCircleImage(mContext, recentContact.getContactId(), binding.ivIcon);
            binding.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NimUIKit.startChatting(mContext,
                            recentContact.getContactId(),
                            SessionTypeEnum.P2P, getRobotCustomization(), null);

                }
            });
        }

    }

    private static SessionCustomization robotCustomization;

    private static SessionCustomization getRobotCustomization() {
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
