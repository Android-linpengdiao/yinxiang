package com.yinxiang.fragment;

import android.content.Context;

import androidx.databinding.DataBindingUtil;

import android.net.Uri;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import okhttp3.Call;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baselibrary.utils.CommonUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.yinxiang.R;
import com.yinxiang.activity.ClubMessageActivity;
import com.yinxiang.activity.CommentActivity;
import com.yinxiang.activity.LikeActivity;
import com.yinxiang.activity.NoticeActivity;
import com.yinxiang.adapter.ChatMessageAdapter;
import com.yinxiang.databinding.FragmentMessageBinding;
import com.yinxiang.model.FriendRead;
import com.yinxiang.view.OnClickListener;

import java.util.List;

public class MessageFragment extends BaseFragment implements View.OnClickListener {

    private FragmentMessageBinding binding;
    private ChatMessageAdapter adapter;


    public static MessageFragment newInstance(String param1, String param2) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_message, container, false);

        binding.systemNoticeView.setOnClickListener(this);
        binding.likeView.setOnClickListener(this);
        binding.commentView.setOnClickListener(this);
        binding.groupMessageView.setOnClickListener(this);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ChatMessageAdapter(getActivity());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setNestedScrollingEnabled(false);
        adapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });

        return binding.getRoot();
    }


    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initData();
        }
    }

    private void initData() {
        getChatMessage();
        getWorkMessage();

    }

    private void getWorkMessage() {
        SendRequest.friendRead(getUserInfo().getData().getId(), new GenericsCallback<FriendRead>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(FriendRead response, int id) {
                if (response.getCode() == 200 && response.getData() != null) {
                    binding.systemNoticeNews.setVisibility(response.getData().getSystem().getNum() > 0 ? View.VISIBLE : View.GONE);
                    binding.systemNoticeNews.setText(String.valueOf(response.getData().getSystem().getNum()));

                    binding.likeNews.setVisibility(response.getData().getAssist().getNum() > 0 ? View.VISIBLE : View.GONE);
                    binding.likeNews.setText(String.valueOf(response.getData().getAssist().getNum()));

                    binding.commentNews.setVisibility(response.getData().getComment().getNum() > 0 ? View.VISIBLE : View.GONE);
                    binding.commentNews.setText(String.valueOf(response.getData().getComment().getNum()));

                    binding.groupMessageNews.setVisibility(response.getData().getClub().getNum() > 0 ? View.VISIBLE : View.GONE);
                    binding.groupMessageNews.setText(String.valueOf(response.getData().getClub().getNum()));
                }

            }
        });
    }

    private void getChatMessage() {
        NIMClient.getService(MsgService.class).queryRecentContacts()
                .setCallback(new RequestCallbackWrapper<List<RecentContact>>() {
                    @Override
                    public void onResult(int code, List<RecentContact> recents, Throwable e) {
                        // recents参数即为最近联系人列表（最近会话列表）
                        if (code == 200) {
                            adapter.refreshData(recents);
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_notice_view:
                openActivity(NoticeActivity.class);
                break;
            case R.id.like_view:
                openActivity(LikeActivity.class);
                break;
            case R.id.comment_view:
                openActivity(CommentActivity.class);
                break;
            case R.id.group_message_view:
                openActivity(ClubMessageActivity.class);
                break;
        }
    }
}
