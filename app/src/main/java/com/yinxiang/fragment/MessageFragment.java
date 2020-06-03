package com.yinxiang.fragment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baselibrary.utils.CommonUtil;
import com.yinxiang.R;
import com.yinxiang.activity.ClubMessageActivity;
import com.yinxiang.activity.CommentActivity;
import com.yinxiang.activity.LikeActivity;
import com.yinxiang.activity.NoticeActivity;
import com.yinxiang.activity.UserHomeActivity;
import com.yinxiang.activity.WorkDetailActivity;
import com.yinxiang.adapter.MessageAdapter;
import com.yinxiang.adapter.WorkAdapter;
import com.yinxiang.databinding.FragmentClubWorkBinding;
import com.yinxiang.databinding.FragmentMessageBinding;
import com.yinxiang.view.OnClickListener;

public class MessageFragment extends BaseFragment implements View.OnClickListener {

    private FragmentMessageBinding binding;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MessageFragment() {

    }


    public static MessageFragment newInstance(String param1, String param2) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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
        MessageAdapter adapter = new MessageAdapter(getActivity());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setNestedScrollingEnabled(false);
        adapter.refreshData(CommonUtil.getImageListString());
        adapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
//                Intent intent = new Intent(getActivity(), UserHomeActivity.class);
//                intent.putExtra("uid", 0);
//                getActivity().startActivity(intent);
            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });

        return binding.getRoot();
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
