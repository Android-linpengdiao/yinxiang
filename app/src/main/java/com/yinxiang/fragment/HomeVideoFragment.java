package com.yinxiang.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.alivc.player.AliVcMediaPlayer;
import com.alivc.player.MediaPlayer;
import com.baselibrary.MessageBus;
import com.baselibrary.manager.DialogManager;
import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.ToastUtils;
import com.dingmouren.layoutmanagergroup.viewpager.OnViewPagerListener;
import com.dingmouren.layoutmanagergroup.viewpager.ViewPagerLayoutManager;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.callbacks.StringCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.yinxiang.R;
import com.yinxiang.activity.ReleaseActivity;
import com.yinxiang.activity.ReportActivity;
import com.yinxiang.activity.SelectionCompetitionActivity;
import com.yinxiang.activity.SelectionWorkPKActivity;
import com.yinxiang.activity.SelectionWorkRelayActivity;
import com.yinxiang.activity.UserHomeActivity;
import com.yinxiang.adapter.HomeVideoAdapter;
import com.yinxiang.databinding.FragmentHomeVideoBinding;
import com.yinxiang.model.CommentData;
import com.yinxiang.model.HomeActives;
import com.yinxiang.model.HomeVideos;
import com.yinxiang.view.CommentListPopupWindow;
import com.yinxiang.view.ElectionPopupWindow;
import com.yinxiang.view.OnClickListener;
import com.yinxiang.view.TypePopupWindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class HomeVideoFragment extends BaseFragment {

    private static final String TAG = "HomeVideoFragment";
    private FragmentHomeVideoBinding binding;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private HomeVideoAdapter adapter;

    private ViewPagerLayoutManager mLayoutManager;

    private OnFragmentInteractionListener mListener;

    public static HomeVideoFragment newInstance(String param1, String param2) {
        HomeVideoFragment fragment = new HomeVideoFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_video, container, false);
        adapter = new HomeVideoAdapter(getActivity());
        mLayoutManager = new ViewPagerLayoutManager(getActivity(), OrientationHelper.VERTICAL);
        binding.recyclerView.setLayoutManager(mLayoutManager);
        binding.recyclerView.setAdapter(adapter);
        adapter.refreshData(CommonUtil.getVideoCoverListString());
        adapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                switch (view.getId()) {
                    case R.id.iv_works_pk:
                        openActivity(SelectionWorkPKActivity.class);
                        break;
                    case R.id.iv_relay:
                        openActivity(SelectionWorkRelayActivity.class);
                        break;
                    case R.id.iv_comment:
                        CommentView();
                        break;
                    case R.id.iv_share:
                        shareView(getActivity(), new OnClickListener() {
                            @Override
                            public void onClick(View view, Object object) {

                            }

                            @Override
                            public void onLongClick(View view, Object object) {

                            }
                        });
                        break;
                    case R.id.tv_election:
                        Election();
                        break;
                    case R.id.tv_report:
                        openActivity(ReportActivity.class);
                        break;
                    case R.id.user_icon:
                        openActivity(UserHomeActivity.class);
                        break;
                }
            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });
        binding.type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeView();
            }
        });

        initListener();

        EventBus.getDefault().register(this);

        SendRequest.homePageVideos(1, 10, new GenericsCallback<HomeVideos>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(HomeVideos response, int id) {
                if (response.getCode() == 200 && response.getData() != null) {
//                    adapter.refreshData(response.getData());
                } else {
                    ToastUtils.showShort(getActivity(), response.getMsg());
                }
            }

        });

        SendRequest.homePageVideos(1, 10, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optInt("code") == 200) {

                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        });

        return binding.getRoot();
    }

    private void typeView() {
        TypePopupWindow typePopupWindow = new TypePopupWindow(getActivity());
        typePopupWindow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {

            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });
        typePopupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    private void Election() {
        ElectionPopupWindow electionPopupWindow = new ElectionPopupWindow(getActivity());
        electionPopupWindow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                switch (view.getId()) {
                    case R.id.tv_election:
                        ToastUtils.showShort(getActivity(), "以为TA投一票");
                        break;
                    case R.id.tv_election_coin:
                        DialogManager.showPayDialog(getActivity(), "为TA投三票", "确认支付10金币为TA投三票?", new com.baselibrary.view.OnClickListener() {
                            @Override
                            public void onClick(View view, Object object) {
                                switch (view.getId()) {
                                    case R.id.tv_confirm:

                                        break;
                                    case R.id.tv_cancel:

                                        break;
                                    case R.id.tv_coin:

                                        break;
                                }
                            }

                            @Override
                            public void onLongClick(View view, Object object) {

                            }
                        });
                        break;
                }
            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });
        electionPopupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    private void CommentView() {
        CommentListPopupWindow commentListPopupWindow = new CommentListPopupWindow(getActivity());
        commentListPopupWindow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {

            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });
        commentListPopupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        CommentData commentData = new CommentData();
        List<CommentData.DataBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CommentData.DataBean dataBean = new CommentData.DataBean();
            list.add(dataBean);
        }
        commentData.setData(list);
        commentListPopupWindow.setCommentData(commentData);
    }

    public void onHiddenSurfaceViewChanged(boolean hidden) {
        Log.i(TAG, "onHiddenSurfaceViewChanged: ");
        if (hidden) {
            pause();
            if (mSurfaceView != null) {
                mSurfaceView.setVisibility(View.GONE);
            }
        } else {
            if (mSurfaceView != null) {
                mSurfaceView.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.i(TAG, "onHiddenChanged: ");
        if (hidden) {
            pause();
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume: ");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i(TAG, "onPause: ");
        pause();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMainMessage(MessageBus messageBus) {
        if (messageBus.getCodeType().equals(messageBus.msgId_hiddenChanged)) {
            int index = (int) messageBus.getParam1();
            Log.i(TAG, "onResume getMainMessage: " + index);
            if (mSurfaceView != null) {
                mSurfaceView.setVisibility(index == 0 ? View.VISIBLE : View.GONE);
                Log.i(TAG, "onResume getMainMessage: " + mSurfaceView.isShown());
            }
        }

    }

    private void initListener() {
        mLayoutManager.setOnViewPagerListener(new OnViewPagerListener() {

            @Override
            public void onInitComplete() {
                Log.e(TAG, "onInitComplete");
                playVideo(0, false);
            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
                Log.e(TAG, "释放位置:" + position + " 下一页:" + isNext);
                int index = 0;
                if (isNext) {
                    index = 0;
                } else {
                    index = 1;
                }
                releaseVideo(index);
            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {
                Log.e(TAG, "选中位置:" + position + "  是否是滑动到底部:" + isBottom);
                playVideo(position, isBottom);
            }


        });
    }

    private SurfaceView mSurfaceView;
    private ImageView imgPlay;

    private void playVideo(int position, boolean isBottom) {
        if (isBottom && mPlayer != null) {

        }
        View itemView = binding.recyclerView.getChildAt(0);
        mSurfaceView = itemView.findViewById(R.id.surfaceView);
        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            public void surfaceCreated(SurfaceHolder holder) {
//                holder.setType(SurfaceHolder.SURFACE_TYPE_GPU);
                holder.setKeepScreenOn(true);
                // 对于从后台切换到前台,需要重设surface;部分手机锁屏也会做前后台切换的处理
                if (mPlayer != null) {
                    mPlayer.setVideoSurface(holder.getSurface());
                }

            }

            public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
                if (mPlayer != null) {
                    mPlayer.setSurfaceChanged();
                }
            }

            public void surfaceDestroyed(SurfaceHolder holder) {
            }
        });
        imgPlay = itemView.findViewById(R.id.img_play);
        final ImageView imgThumb = itemView.findViewById(R.id.img_thumb);
        final ImageView background = itemView.findViewById(R.id.background);
        final ProgressBar loading = itemView.findViewById(R.id.loading);
        mSurfaceView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mPlayer.isPlaying()) {
                    imgPlay.animate().alpha(1f).start();
                    mPlayer.pause();
                } else {
                    imgPlay.animate().alpha(0f).start();
                    mPlayer.play();
                }
            }
        });


        destroy();
        mPlayer = new AliVcMediaPlayer(getContext(), mSurfaceView);
        mPlayer.setCirclePlay(true);

        mPlayer.setPreparedListener(new MediaPlayer.MediaPlayerPreparedListener() {
            @Override
            public void onPrepared() {
//                mSurfaceView.setBackgroundColor(Color.TRANSPARENT);
                loading.setVisibility(View.GONE);
                mPlayer.play();
            }
        });
//        mPlayer.setPcmDataListener(new MyPcmDataListener(this));
//        mPlayer.setCircleStartListener(new MyCircleStartListener(this));
        mPlayer.setFrameInfoListener(new MediaPlayer.MediaPlayerFrameInfoListener() {
            @Override
            public void onFrameInfoListener() {
                background.setVisibility(View.GONE);
                imgThumb.animate().alpha(0).setDuration(200).start();
            }
        });
//        mPlayer.setErrorListener(new MyErrorListener(this));
//        mPlayer.setCompletedListener(new MyCompletedListener(this));
//        mPlayer.setSeekCompleteListener(new MySeekCompleteListener(this));
        mPlayer.setStoppedListener(new MediaPlayer.MediaPlayerStoppedListener() {
            @Override
            public void onStopped() {
                imgPlay.animate().alpha(1f).start();
            }
        });
        mPlayer.enableNativeLog();
        if (mPlayer != null) {
            mPlayer.setVideoScalingMode(com.alivc.player.MediaPlayer.VideoScalingMode.VIDEO_SCALING_MODE_SCALE_TO_FIT);
        }
        mPlayer.prepareToPlay(CommonUtil.getVideoListString().get(position));

    }

    private void releaseVideo(int index) {
        View itemView = binding.recyclerView.getChildAt(index);
        ImageView imgThumb = itemView.findViewById(R.id.img_thumb);
        ImageView imgPlay = itemView.findViewById(R.id.img_play);
        imgThumb.animate().alpha(1).start();
        imgPlay.animate().alpha(0f).start();
        stop();
    }

    private AliVcMediaPlayer mPlayer;

    private void start() {
//        if (mPlayer != null) {
//            mPlayer.prepareToPlay(url);
//        }
    }

    private void pause() {
        if (mPlayer != null) {
            mPlayer.pause();
        }
        if (imgPlay != null) {
            imgPlay.animate().alpha(1f).start();
        }
    }

    private void stop() {
        if (mPlayer != null) {
            mPlayer.stop();
        }
    }

    private void resume() {
        if (mPlayer != null) {
            mPlayer.play();
        }
    }

    private void destroy() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.destroy();
        }
    }

    private void replay() {
        stop();
        start();
    }

    public void onButtonPressed(Uri uri) {

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
        destroy();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
