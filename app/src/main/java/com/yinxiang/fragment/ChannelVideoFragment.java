package com.yinxiang.fragment;

import android.content.Context;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;

import android.net.Uri;
import android.os.Bundle;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
import com.yinxiang.activity.MyWalletActivity;
import com.yinxiang.activity.ReportActivity;
import com.yinxiang.activity.SelectionWorkPKActivity;
import com.yinxiang.activity.SelectionWorkRelayActivity;
import com.yinxiang.activity.UserHomeActivity;
import com.yinxiang.adapter.ChannelVideoAdapter;
import com.yinxiang.databinding.FragmentChannelVideoBinding;
import com.yinxiang.model.CommentData;
import com.yinxiang.model.HomeVideos;
import com.yinxiang.view.CommentListPopupWindow;
import com.yinxiang.view.ElectionPopupWindow;
import com.yinxiang.view.LoadingView;
import com.yinxiang.view.OnClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;

public class ChannelVideoFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "ChannelVideoFragment";
    private FragmentChannelVideoBinding binding;
    private ChannelVideoAdapter adapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ViewPagerLayoutManager mLayoutManager;

    private OnFragmentInteractionListener mListener;

    private boolean isShow = false;

    public static ChannelVideoFragment newInstance(String param1, String param2) {
        ChannelVideoFragment fragment = new ChannelVideoFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_channel_video, container, false);
        adapter = new ChannelVideoAdapter(getActivity());
        mLayoutManager = new ViewPagerLayoutManager(getActivity(), OrientationHelper.VERTICAL);
        binding.recyclerView.setLayoutManager(mLayoutManager);
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                HomeVideos.DataBeanX.DataBean dataBean;
                Bundle bundle;
                switch (view.getId()) {
                    case R.id.iv_works_pk:
                        if (object instanceof HomeVideos.DataBeanX.DataBean) {
                            dataBean = (HomeVideos.DataBeanX.DataBean) object;
                            bundle = new Bundle();
                            bundle.putInt("videoId", dataBean.getId());
                            bundle.putInt("activeId", dataBean.getActive_id());
                            openActivity(SelectionWorkPKActivity.class, bundle);
                        }
                        break;
                    case R.id.iv_relay:
                        if (object instanceof HomeVideos.DataBeanX.DataBean) {
                            dataBean = (HomeVideos.DataBeanX.DataBean) object;
                            bundle = new Bundle();
                            bundle.putInt("videoId", dataBean.getId());
                            openActivity(SelectionWorkRelayActivity.class, bundle);
                        }
                        break;
                    case R.id.tv_comment:
                        if (object instanceof HomeVideos.DataBeanX.DataBean) {
                            dataBean = (HomeVideos.DataBeanX.DataBean) object;
                            commentListPopupWindow = null;
                            homePageVideosComment(dataBean.getId());
                        }
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
                        if (object instanceof HomeVideos.DataBeanX.DataBean) {
                            dataBean = (HomeVideos.DataBeanX.DataBean) object;
                            homePageVideosVoteSet(dataBean.getId());
                        }
                        break;
                    case R.id.tv_report:
                        if (object instanceof HomeVideos.DataBeanX.DataBean) {
                            dataBean = (HomeVideos.DataBeanX.DataBean) object;
                            bundle = new Bundle();
                            bundle.putInt("videoId", dataBean.getId());
                            openActivity(ReportActivity.class, bundle);
                        }
                        break;
                    case R.id.user_icon:
                        if (object instanceof HomeVideos.DataBeanX.DataBean) {
                            dataBean = (HomeVideos.DataBeanX.DataBean) object;
                            Intent intent = new Intent(getActivity(), UserHomeActivity.class);
                            intent.putExtra("uid", dataBean.getTourist_id());
                            startActivity(intent);
                        }
                        break;
                    case R.id.surfaceView:
                        if (mPlayer.isPlaying()) {
                            mPlayer.pause();
                            if (imgPlay != null) {
                                imgPlay.animate().alpha(0.6f).start();
                            }
                        } else {
                            mPlayer.play();
                            if (imgPlay != null) {
                                imgPlay.animate().alpha(0f).start();
                            }
                        }
                        break;
                }
            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });

        initListener();

        EventBus.getDefault().register(this);

        binding.swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                homepageVideosHot();
            }
        });
        homepageVideosHot();

        return binding.getRoot();
    }

    private void homepageVideosHot() {
        binding.swipeRefreshLayout.setRefreshing(true);
        SendRequest.homepageVideosHot(getUserInfo().getData().getId(), 0, 10, new GenericsCallback<HomeVideos>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
                binding.recyclerView.setVisibility(View.GONE);
            }

            @Override
            public void onResponse(HomeVideos response, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
                if (response != null && response.getCode() == 200) {
                    if (response.getData() != null && response.getData().getData() != null && response.getData().getData().size() > 0) {
                        binding.recyclerView.setVisibility(View.VISIBLE);
                        adapter.refreshData(response.getData().getData());
                        binding.recyclerView.setVisibility(View.VISIBLE);
                    } else {
                        adapter.refreshData(new ArrayList<HomeVideos.DataBeanX.DataBean>());
                        binding.recyclerView.setVisibility(View.GONE);
                    }
                } else {
                    ToastUtils.showShort(getActivity(), response.getMsg());
                    binding.recyclerView.setVisibility(View.GONE);
                }
            }

        });
    }

    private void homePageVideosComment(final int video_id) {
        SendRequest.homePageVideosComment(100, video_id, new GenericsCallback<CommentData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(CommentData response, int id) {
                if (response != null && response.getCode() == 200 && response.getData() != null) {
                    if (commentListPopupWindow != null) {
                        commentListPopupWindow.setCommentData(response);
                    } else {
                        CommentView(response, video_id);
                    }
                } else {
                    ToastUtils.showShort(getActivity(), response.getMsg());
                }
            }

        });

    }

    private void homePageVideosCreateComment(final int video_id, String content) {
        SendRequest.homePageVideosCreateComment(getUserInfo().getData().getId(), video_id, content, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optInt("code") == 200) {
                        homePageVideosComment(video_id);
                    } else {
                        ToastUtils.showShort(getActivity(), jsonObject.optString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void homePageVideosVoteSet(final int workId) {
        SendRequest.homePageVideosVoteSet(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    if (!CommonUtil.isBlank(response)) {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.optInt("code") == 200) {
                            Election(workId, jsonObject.optJSONObject("data").optString("wallet_token"));
                        } else {
                            ToastUtils.showShort(getActivity(), jsonObject.optString("msg"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void homePageVideosVote(int workId, int free) {
        SendRequest.homePageVideosVote(getUserInfo().getData().getId(), workId, free, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    if (!CommonUtil.isBlank(response)) {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.optInt("code") == 200) {
                            if (jsonObject.optJSONObject("data").optBoolean("canVote")) {
                                ToastUtils.showShort(getActivity(), "以为TA投一票");
                            } else {
                                ToastUtils.showShort(getActivity(), "今日以为TA投一票，明日再来为TA投一票");
                            }
                        } else {
                            ToastUtils.showShort(getActivity(), jsonObject.optString("msg"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void Election(final int workId, final String wallet_token) {
        ElectionPopupWindow electionPopupWindow = new ElectionPopupWindow(getActivity());
        electionPopupWindow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                switch (view.getId()) {
                    case R.id.tv_election:
                        homePageVideosVote(workId, 1);
                        break;
                    case R.id.tv_election_coin:
                        DialogManager.showPayDialog(getActivity(), "为TA投三票", "确认支付" + wallet_token + "金币为TA投三票?", String.valueOf(getUserInfo().getData().getWallet_token()), new com.baselibrary.view.OnClickListener() {
                            @Override
                            public void onClick(View view, Object object) {
                                switch (view.getId()) {
                                    case R.id.tv_confirm:
                                        homePageVideosVote(workId, 2);
                                        break;
                                    case R.id.tv_cancel:

                                        break;
                                    case R.id.tv_coin:
                                        openActivity(MyWalletActivity.class);
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

    private CommentListPopupWindow commentListPopupWindow;

    private void CommentView(CommentData commentData, final int video_id) {
        commentListPopupWindow = new CommentListPopupWindow(getActivity());
        commentListPopupWindow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                homePageVideosCreateComment(video_id, (String) object);
            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });
        commentListPopupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        commentListPopupWindow.setCommentData(commentData);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
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
            isShow = false;
        }else {
            isShow = true;
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume: ");
        isShow = true;
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i(TAG, "onPause: ");
        pause();
        isShow = false;
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
        final LoadingView loading = itemView.findViewById(R.id.loadingView);
//        mSurfaceView.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (mPlayer.isPlaying()) {
//                    imgPlay.animate().alpha(1f).start();
//                    mPlayer.pause();
//                } else {
//                    imgPlay.animate().alpha(0f).start();
//                    mPlayer.play();
//                }
//            }
//        });


        destroy();
        mPlayer = new AliVcMediaPlayer(getContext(), mSurfaceView);
        mPlayer.setCirclePlay(true);

        mPlayer.setPreparedListener(new MediaPlayer.MediaPlayerPreparedListener() {
            @Override
            public void onPrepared() {
//                mSurfaceView.setBackgroundColor(Color.TRANSPARENT);
                loading.setVisibility(View.GONE);
                if (isShow) {
                    mPlayer.play();
                }
            }
        });
        mPlayer.setFrameInfoListener(new MediaPlayer.MediaPlayerFrameInfoListener() {
            @Override
            public void onFrameInfoListener() {
                background.setVisibility(View.GONE);
                imgThumb.animate().alpha(0).setDuration(200).start();
            }
        });
        mPlayer.setStoppedListener(new MediaPlayer.MediaPlayerStoppedListener() {
            @Override
            public void onStopped() {
                imgPlay.animate().alpha(0.6f).start();
            }
        });
//        mPlayer.setPcmDataListener(new MyPcmDataListener(this));
//        mPlayer.setCircleStartListener(new MyCircleStartListener(this));
//        mPlayer.setErrorListener(new MyErrorListener(this));
//        mPlayer.setCompletedListener(new MyCompletedListener(this));
//        mPlayer.setSeekCompleteListener(new MySeekCompleteListener(this));
        mPlayer.enableNativeLog();
        if (mPlayer != null) {
            mPlayer.setVideoScalingMode(com.alivc.player.MediaPlayer.VideoScalingMode.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
        }
        if (adapter.getItem(position) != null) {
            mPlayer.prepareToPlay(adapter.getItem(position).getVideo());
        }

    }

    private void releaseVideo(int index) {
        View itemView = binding.recyclerView.getChildAt(index);
        if (itemView != null) {
            ImageView imgThumb = itemView.findViewById(R.id.img_thumb);
            ImageView imgPlay = itemView.findViewById(R.id.img_play);
            imgThumb.animate().alpha(1).start();
            imgPlay.animate().alpha(0f).start();
        }
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
            imgPlay.animate().alpha(0.6f).start();
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
