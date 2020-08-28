package com.yinxiang.fragment;

import android.content.Context;
import android.content.Intent;

import androidx.databinding.DataBindingUtil;

import android.net.Uri;
import android.os.Bundle;

import androidx.recyclerview.widget.OrientationHelper;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Environment;
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
import com.baselibrary.utils.FileUtils;
import com.baselibrary.utils.SharedPreferencesUtils;
import com.baselibrary.utils.ToastUtils;
import com.dingmouren.layoutmanagergroup.viewpager.OnViewPagerListener;
import com.dingmouren.layoutmanagergroup.viewpager.ViewPagerLayoutManager;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.callbacks.StringCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.yinxiang.R;
import com.yinxiang.activity.MyWalletActivity;
import com.yinxiang.activity.ReportActivity;
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
import com.yinxiang.view.LoadingView;
import com.yinxiang.view.OnClickListener;
import com.yinxiang.view.TypePopupWindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;


public class HomeVideoFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "HomeVideoFragment";
    private FragmentHomeVideoBinding binding;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private HomeVideoAdapter adapter;
    private HomeActives homeActives;
    private HomeActives.DataBean HomeActivesDataBean;
    private HomeVideos homeVideos;
    private HomeVideos.DataBeanX.DataBean dataBean;

    private ViewPagerLayoutManager mLayoutManager;

    private OnFragmentInteractionListener mListener;

    private boolean isShow = false;

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
        adapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                Bundle bundle;
                switch (view.getId()) {
                    case R.id.iv_works_pk:
                        if (getUserId(true) > 0) {
                            if (object instanceof HomeVideos.DataBeanX.DataBean) {
                                dataBean = (HomeVideos.DataBeanX.DataBean) object;
                                bundle = new Bundle();
                                bundle.putInt("videoId", dataBean.getId());
                                bundle.putInt("activeId", dataBean.getActive_id());
                                openActivity(SelectionWorkPKActivity.class, bundle);
                            }
                        }
                        break;
                    case R.id.iv_relay:
                        if (getUserId(true) > 0) {
                            if (object instanceof HomeVideos.DataBeanX.DataBean) {
                                dataBean = (HomeVideos.DataBeanX.DataBean) object;
                                bundle = new Bundle();
                                bundle.putInt("videoId", dataBean.getId());
                                openActivity(SelectionWorkRelayActivity.class, bundle);
                            }
                        }
                        break;
                    case R.id.tv_comment:
                        if (getUserId(true) > 0) {
                            if (object instanceof HomeVideos.DataBeanX.DataBean) {
                                dataBean = (HomeVideos.DataBeanX.DataBean) object;
                                commentListPopupWindow = null;
                                homePageVideosComment(dataBean.getId());
                            }
                        }
                        break;
                    case R.id.iv_share:
                        if (object instanceof HomeVideos.DataBeanX.DataBean) {
                            dataBean = (HomeVideos.DataBeanX.DataBean) object;
                            shareView(getActivity(),dataBean.getVideo(),dataBean.getName(),dataBean.getName(), new OnClickListener() {
                                @Override
                                public void onClick(View view, Object object) {

                                }

                                @Override
                                public void onLongClick(View view, Object object) {

                                }
                            });
                        }
                        break;
                    case R.id.tv_election:
                        if (getUserId(true) > 0) {
                            if (object instanceof HomeVideos.DataBeanX.DataBean) {
                                dataBean = (HomeVideos.DataBeanX.DataBean) object;
                                homePageVideosVoteSet(dataBean.getId());
                            }
                        }
                        break;
                    case R.id.tv_report:
                        if (getUserId(true) > 0) {
                            if (object instanceof HomeVideos.DataBeanX.DataBean) {
                                dataBean = (HomeVideos.DataBeanX.DataBean) object;
                                bundle = new Bundle();
                                bundle.putInt("videoId", dataBean.getId());
                                openActivity(ReportActivity.class, bundle);
                            }
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
                    case R.id.videoView:
                        if (mVodPlayer != null) {
                            if (mVodPlayer.isPlaying()) {
                                mVodPlayer.pause();
                                if (imgPlay != null) {
                                    imgPlay.animate().alpha(0.6f).start();
                                }
                            } else {
                                mVodPlayer.resume();
                                if (imgPlay != null) {
                                    imgPlay.animate().alpha(0f).start();
                                }
                            }
                        }

                        break;
                }
            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });
        binding.type.setOnClickListener(this);

        initListener();

        EventBus.getDefault().register(this);

        SendRequest.homePageActives(1, new GenericsCallback<HomeActives>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(HomeActives response, int id) {
                homeActives = response;
                if (response.getCode() == 200 && response.getData() != null && response.getData().size() > 0) {
                    response.getData().get(0).setSelected(1);
                    homePageVideosActive(response.getData().get(0));
                }
            }

        });

        binding.swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (HomeActivesDataBean != null) {
                    homePageVideosActive(HomeActivesDataBean);
                }
            }
        });

        return binding.getRoot();
    }

    private void homePageVideosActive(HomeActives.DataBean dataBean) {
        binding.swipeRefreshLayout.setRefreshing(true);
        HomeActivesDataBean = dataBean;
        SharedPreferencesUtils.getInstance().setActivityId(dataBean.getId());
        SendRequest.homePageVideosActive(getUserId(), dataBean.getId(), 10, new GenericsCallback<HomeVideos>(new JsonGenericsSerializator()) {
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
                        homeVideos = response;
                        binding.recyclerView.setVisibility(View.VISIBLE);
                        adapter.refreshData(response.getData().getData());
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
        SendRequest.homePageVideosCreateComment(getUserId(), video_id, content, new StringCallback() {
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
                            Election(workId, jsonObject.optJSONObject("data").optString("wallet_token"), jsonObject.optJSONObject("data").optString("votes"));
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
        SendRequest.homePageVideosVote(getUserId(), workId, free, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    if (!CommonUtil.isBlank(response)) {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.optInt("code") == 200) {
                            personInformInfo();
                            if (jsonObject.optJSONObject("data").optBoolean("canVote")) {
                                ToastUtils.showShort(getActivity(), "以为TA投" + (free == 1 ? "1" : getVideosVoteSet().getData().getVotes()) + "票");
                                if (homeVideos != null && dataBean != null) {
                                    dataBean.setPre_votes(dataBean.getPre_votes() + (free == 1 ? 1 : getVideosVoteSet().getData().getVotes()));
                                    if (homeVideos.getData().getData().indexOf(dataBean) != -1) {
                                        adapter.notifyItemChanged(homeVideos.getData().getData().indexOf(dataBean));
                                    }
                                }
                            } else {
                                ToastUtils.showShort(getActivity(), "今日以为TA投" + (free == 1 ? "1" : getVideosVoteSet().getData().getVotes()) + "票，明日再来为TA投" + (free == 1 ? "1" : getVideosVoteSet().getData().getVotes()) + "票");
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

    private void typeView(HomeActives response) {
        TypePopupWindow typePopupWindow = new TypePopupWindow(getActivity());
        typePopupWindow.setHomeActives(response);
        typePopupWindow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                if (object instanceof HomeActives.DataBean) {
                    homePageVideosActive((HomeActives.DataBean) object);
                }

            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });
        typePopupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    private void Election(final int workId, final String wallet_token, final String votes) {
        ElectionPopupWindow electionPopupWindow = new ElectionPopupWindow(getActivity());
        electionPopupWindow.setWallet(wallet_token, votes);
        electionPopupWindow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                switch (view.getId()) {
                    case R.id.tv_election:
                        homePageVideosVote(workId, 1);
                        break;
                    case R.id.tv_election_coin:
                        DialogManager.showPayDialog(getActivity(), "为TA投" + votes + "票", "确认支付" + wallet_token + "金币为TA投" + votes + "票?", String.valueOf(getUserInfo().getData().getWallet_token()), new com.baselibrary.view.OnClickListener() {
                            @Override
                            public void onClick(View view, Object object) {
                                switch (view.getId()) {
                                    case R.id.tv_confirm:
                                        if (getUserInfo().getData().getWallet_token() < Integer.parseInt(wallet_token)) {
                                            ToastUtils.showShort(getActivity(), "你的余额不足");
                                            return;
                                        }
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
            case R.id.type:
                typeView(homeActives);
                break;
        }
    }

    public void onHiddenSurfaceViewChanged(boolean hidden) {
        Log.i(TAG, "onHiddenSurfaceViewChanged: ");
        if (hidden) {
            pause();
        }else {
            isShow = true;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.i(TAG, "onHiddenChanged: ");
        if (hidden) {
            pause();
        }else {
            isShow = true;
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onResume() {
        isShow = true;
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
//            if (mSurfaceView != null) {
//                mSurfaceView.setVisibility(index == 0 ? View.VISIBLE : View.GONE);
//                Log.i(TAG, "onResume getMainMessage: " + mSurfaceView.isShown());
//            }
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


    private void releaseVideo(int index) {
        Log.i(TAG, "releaseVideo: ");
        View itemView = binding.recyclerView.getChildAt(index);
        if (itemView != null) {
            ImageView imgThumb = itemView.findViewById(R.id.img_thumb);
            ImageView imgPlay = itemView.findViewById(R.id.img_play);
            imgThumb.animate().alpha(1).start();
            imgPlay.animate().alpha(0f).start();
        }
        if (mVodPlayer!= null) {
            mVodPlayer.pause();
        }

    }

    /**
     * SDK player 相关
     */
    private TXVodPlayer mVodPlayer = null;
    private ImageView imgPlay;

    private void playVideo(int position, boolean isBottom) {

        Log.i(TAG, "playVideo: " + position);
        View itemView = binding.recyclerView.getChildAt(0);
        TXCloudVideoView videoView = itemView.findViewById(R.id.videoView);
        imgPlay = itemView.findViewById(R.id.img_play);
        final ImageView imgThumb = itemView.findViewById(R.id.img_thumb);
//        final ImageView background = itemView.findViewById(R.id.background);
        final LoadingView loading = itemView.findViewById(R.id.loadingView);

        videoView.setLogMargin(12, 12, 110, 60);
        videoView.showLog(false);

        if (mVodPlayer == null) {
            mVodPlayer = new TXVodPlayer(getActivity());
        } else if (mVodPlayer != null) {
            mVodPlayer.pause();
        }

        mVodPlayer.setVodListener(new ITXVodPlayListener() {
            @Override
            public void onPlayEvent(TXVodPlayer txVodPlayer, int event, Bundle param) {
                if (event != TXLiveConstants.PLAY_EVT_PLAY_PROGRESS) {
//                    String playEventLog = "TXVodPlayer onPlayEvent event: " + event + ", " + param.getString(TXLiveConstants.EVT_DESCRIPTION);
//                    TXCLog.d(TAG, playEventLog);
                }
                switch (event) {
                    case TXLiveConstants.PLAY_EVT_VOD_PLAY_PREPARED://视频播放开始
//                        mBinding.videoPlayLayout.videoPlay.setSelected(true);
                        loading.setVisibility(View.GONE);
                        imgThumb.animate().alpha(0).setDuration(200).start();
                        if (!isShow){
                            mVodPlayer.pause();
                        }
                        break;
                    case TXLiveConstants.PLAY_EVT_RCV_FIRST_I_FRAME://切换软硬解码器后，重新seek位置

                        break;
                    case TXLiveConstants.PLAY_EVT_PLAY_END:

//                        mBinding.videoPlayLayout.videoPlay.setSelected(false);

                        break;
                    case TXLiveConstants.PLAY_EVT_PLAY_PROGRESS:
                        int progress = param.getInt(TXLiveConstants.EVT_PLAY_PROGRESS_MS);
                        int duration = param.getInt(TXLiveConstants.EVT_PLAY_DURATION_MS);

//                        mBinding.videoPlayLayout.currentDuration.setText(CommonUtil.Formatter.formatTime((int) (progress)));
//                        mBinding.videoPlayLayout.totalDuration.setText(CommonUtil.Formatter.formatTime((int) (duration)));
//                        mBinding.videoPlayLayout.progress.setProgress((int) progress);
//                        mBinding.videoPlayLayout.progress.setMax((int) duration);
                        break;
                    case TXLiveConstants.PLAY_EVT_PLAY_BEGIN: {
//                        updatePlayState(SuperPlayerConst.PLAYSTATE_PLAYING);
                        break;
                    }
                    default:
                        break;
                }
                if (event < 0) {// 播放点播文件失败
                    mVodPlayer.stopPlay(true);

                }
            }

            @Override
            public void onNetStatus(TXVodPlayer txVodPlayer, Bundle bundle) {

            }
        });
        mVodPlayer.setPlayListener(new ITXLivePlayListener() {
            @Override
            public void onPlayEvent(int i, Bundle bundle) {
            }

            @Override
            public void onNetStatus(Bundle bundle) {

            }
        });
        mVodPlayer.setPlayerView(videoView);
        mVodPlayer.setAutoPlay(true);
        mVodPlayer.setLoop(true);
        mVodPlayer.enableHardwareDecode(true); // 是否使用硬解码
        mVodPlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);// player 渲染角度
        mVodPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);//player 渲染模式
        int result = mVodPlayer.startPlay(adapter.getItem(position).getVideo()); // result返回值：0 success;  -1 empty url; -2 invalid url; -3 invalid playType;
        if (result == 0) {
//            mBinding.videoPlayLayout.videoPlay.setSelected(true);
//            showVideoProgressInfo();
//            hidePlayerView();
//            addWatchNumber(id);
        }

//        mBinding.videoPlayLayout.videoContainer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mBinding.videoPlayLayout.videoPlay.setVisibility(mBinding.videoPlayLayout.videoPlay.isShown() ? View.GONE : View.VISIBLE);
//                if (mBinding.videoPlayLayout.videoPlay.isShown()) {
//                    hidePlayerView();
//                }
//            }
//        });
//        mBinding.videoPlayLayout.videoPlay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hidePlayerView();
//                if (mVodPlayer != null) {
//                    if (mVodPlayer.isPlaying()) {
//                        mVodPlayer.pause();
//                        mBinding.videoPlayLayout.videoPlay.setSelected(false);
//                    } else {
//                        mVodPlayer.resume();
//                        mBinding.videoPlayLayout.videoPlay.setSelected(true);
//                    }
//                }
//            }
//        });


    }


    private void pause() {
        isShow = false;
        if (mVodPlayer != null) {
            mVodPlayer.pause();
            imgPlay.setSelected(false);
        }
        if (imgPlay != null) {
            imgPlay.animate().alpha(0.6f).start();
        }
    }


//    private AliVcMediaPlayer mPlayer;
//    private SurfaceView mSurfaceView;
//    private ImageView imgPlay;
//
//    private void playVideo(int position, boolean isBottom) {
//        if (isBottom && mPlayer != null) {
//
//        }
//        View itemView = binding.recyclerView.getChildAt(0);
//        mSurfaceView = itemView.findViewById(R.id.surfaceView);
//        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
//            public void surfaceCreated(SurfaceHolder holder) {
////                holder.setType(SurfaceHolder.SURFACE_TYPE_GPU);
//                holder.setKeepScreenOn(true);
//                // 对于从后台切换到前台,需要重设surface;部分手机锁屏也会做前后台切换的处理
//                if (mPlayer != null) {
//                    mPlayer.setVideoSurface(holder.getSurface());
//                }
//
//            }
//
//            public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
//                if (mPlayer != null) {
//                    mPlayer.setSurfaceChanged();
//                }
//            }
//
//            public void surfaceDestroyed(SurfaceHolder holder) {
//            }
//        });
//        imgPlay = itemView.findViewById(R.id.img_play);
//        final ImageView imgThumb = itemView.findViewById(R.id.img_thumb);
//        final ImageView background = itemView.findViewById(R.id.background);
//        final LoadingView loading = itemView.findViewById(R.id.loadingView);
//
//        destroy();
//        mPlayer = new AliVcMediaPlayer(getContext(), mSurfaceView);
//        //开启缓存
//        String sdDir = FileUtils.getMediaPath() + "save_cache";
//        mPlayer.setPlayingCache(true, sdDir, 60 * 60 /*时长, s */, 300 /*大小，MB*/);
//        mPlayer.setCirclePlay(true);
//
//        mPlayer.setPreparedListener(new MediaPlayer.MediaPlayerPreparedListener() {
//            @Override
//            public void onPrepared() {
////                mSurfaceView.setBackgroundColor(Color.TRANSPARENT);
//                loading.setVisibility(View.GONE);
//                if (isShow) {
//                    mPlayer.play();
//                }
//            }
//        });
//        mPlayer.setFrameInfoListener(new MediaPlayer.MediaPlayerFrameInfoListener() {
//            @Override
//            public void onFrameInfoListener() {
//                background.setVisibility(View.GONE);
//                imgThumb.animate().alpha(0).setDuration(200).start();
//            }
//        });
//        mPlayer.setStoppedListener(new MediaPlayer.MediaPlayerStoppedListener() {
//            @Override
//            public void onStopped() {
//                imgPlay.animate().alpha(0.6f).start();
//            }
//        });
////        mPlayer.setPcmDataListener(new MyPcmDataListener(this));
////        mPlayer.setCircleStartListener(new MyCircleStartListener(this));
////        mPlayer.setErrorListener(new MyErrorListener(this));
////        mPlayer.setCompletedListener(new MyCompletedListener(this));
////        mPlayer.setSeekCompleteListener(new MySeekCompleteListener(this));
//        mPlayer.enableNativeLog();
//        if (mPlayer != null) {
//            mPlayer.setVideoScalingMode(MediaPlayer.VideoScalingMode.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
//        }
//        if (adapter.getItem(position) != null) {
//            mPlayer.prepareToPlay(adapter.getItem(position).getVideo());
//        }
//
//    }
//
//    private void releaseVideo(int index) {
//        View itemView = binding.recyclerView.getChildAt(index);
//        if (itemView != null) {
//            ImageView imgThumb = itemView.findViewById(R.id.img_thumb);
//            ImageView imgPlay = itemView.findViewById(R.id.img_play);
//            imgThumb.animate().alpha(1.0f).start();
//            imgPlay.animate().alpha(0f).start();
//        }
//        stop();
//    }
//
//    private void start() {
////        if (mPlayer != null) {
////            mPlayer.prepareToPlay(url);
////        }
//    }
//
//    private void pause() {
//        if (mPlayer != null) {
//            mPlayer.pause();
//        }
//        if (imgPlay != null) {
//            imgPlay.animate().alpha(0.6f).start();
//        }
//    }
//
//    private void stop() {
//        if (mPlayer != null) {
//            mPlayer.stop();
//        }
//    }
//
//    private void resume() {
//        if (mPlayer != null) {
//            mPlayer.play();
//        }
//    }
//
//    private void destroy() {
//        if (mPlayer != null) {
//            mPlayer.stop();
//            mPlayer.destroy();
//        }
//    }
//
//    private void replay() {
//        stop();
//        start();
//    }

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
        Log.i(TAG, "onDetach: ");
        mListener = null;
        super.onDetach();
//        destroy();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
