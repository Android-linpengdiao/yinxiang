package com.yinxiang.fragment;

import android.app.Activity;
import android.content.Context;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baselibrary.utils.ToastUtils;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.api.model.session.SessionCustomization;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.yinxiang.R;
import com.yinxiang.activity.ClubDetailActivity;
import com.yinxiang.adapter.ContactsAdapter;
import com.yinxiang.adapter.ContactsClubAdapter;
import com.yinxiang.adapter.ContactsFansAdapter;
import com.yinxiang.adapter.ContactsFollowAdapter;
import com.yinxiang.databinding.FragmentContactsBinding;
import com.yinxiang.model.ClubData;
import com.yinxiang.model.FansUserData;
import com.yinxiang.model.FollowUserData;
import com.yinxiang.model.FriendsData;
import com.yinxiang.view.OnClickListener;

import okhttp3.Call;

public class ContactsFragment extends BaseFragment implements View.OnClickListener {

    private FragmentContactsBinding binding;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ContactsAdapter adapterFriend;
    private ContactsFansAdapter adapterFans;
    private ContactsFollowAdapter adapterFollow;
    private ContactsClubAdapter adapterClub;

    public ContactsFragment() {

    }

    public static ContactsFragment newInstance(String param1, String param2) {
        ContactsFragment fragment = new ContactsFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contacts, container, false);

        binding.friendView.setOnClickListener(this);
        binding.fansView.setOnClickListener(this);
        binding.followView.setOnClickListener(this);
        binding.clubView.setOnClickListener(this);

        adapterFriend = new ContactsAdapter(getActivity());
        binding.friendRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.friendRecyclerView.setNestedScrollingEnabled(false);
        binding.friendRecyclerView.setAdapter(adapterFriend);
        adapterFriend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                if (object instanceof FriendsData.DataBean) {
                    FriendsData.DataBean dataBean = (FriendsData.DataBean) object;
                    NimUIKit.startChatting(getActivity(), dataBean.getPhone(), SessionTypeEnum.P2P, getRobotCustomization(), null);

                }
            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });

        adapterFans = new ContactsFansAdapter(getActivity());
        binding.fansRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.fansRecyclerView.setNestedScrollingEnabled(false);
        binding.fansRecyclerView.setAdapter(adapterFans);
        adapterFans.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                if (object instanceof FansUserData.DataBeanX.DataBean) {
                    FansUserData.DataBeanX.DataBean dataBean = (FansUserData.DataBeanX.DataBean) object;
                    NimUIKit.startChatting(getActivity(), dataBean.getPhone(), SessionTypeEnum.P2P, getRobotCustomization(), null);

                }
            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });

        adapterFollow = new ContactsFollowAdapter(getActivity());
        binding.followRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.followRecyclerView.setNestedScrollingEnabled(false);
        binding.followRecyclerView.setAdapter(adapterFollow);
        adapterFollow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                if (object instanceof FollowUserData.DataBeanX.DataBean) {
                    FollowUserData.DataBeanX.DataBean dataBean = (FollowUserData.DataBeanX.DataBean) object;
                    NimUIKit.startChatting(getActivity(), dataBean.getPhone(), SessionTypeEnum.P2P, getRobotCustomization(), null);

                }
            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });

        adapterClub = new ContactsClubAdapter(getActivity());
        binding.clubRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.clubRecyclerView.setNestedScrollingEnabled(false);
        binding.clubRecyclerView.setAdapter(adapterClub);
        adapterClub.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                if (object instanceof ClubData.DataBean) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("dataBean", (ClubData.DataBean)object);
                    openActivity(ClubDetailActivity.class, bundle);
                }
            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });

        initData();

        return binding.getRoot();
    }

    private void initData() {
        SendRequest.myFriends(getUserInfo().getData().getId(), new GenericsCallback<FriendsData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(FriendsData response, int id) {
                if (response.getCode() == 200 && response.getData() != null && response.getData() != null) {
                    adapterFriend.refreshData(response.getData());
                    binding.friendNumber.setText(String.valueOf(response.getData().size()));
                } else {
                    ToastUtils.showShort(getActivity(), response.getMsg());
                }
            }

        });
        SendRequest.personInformFans(getUserInfo().getData().getId(), 10, new GenericsCallback<FansUserData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(FansUserData response, int id) {
                if (response.getCode() == 200 && response.getData() != null && response.getData().getData() != null) {
                    adapterFans.refreshData(response.getData().getData());
                    binding.fansNumber.setText(String.valueOf(response.getData().getData().size()));
                } else {
                    ToastUtils.showShort(getActivity(), response.getMsg());
                }
            }

        });
        SendRequest.personInformFollows(getUserInfo().getData().getId(), 10, new GenericsCallback<FollowUserData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(FollowUserData response, int id) {
                if (response.getCode() == 200 && response.getData() != null && response.getData().getData() != null) {
                    adapterFollow.refreshData(response.getData().getData());
                    binding.followNumber.setText(String.valueOf(response.getData().getData().size()));
                } else {
                    ToastUtils.showShort(getContext(), response.getMsg());
                }
            }

        });
        SendRequest.channelClub(getUserInfo().getData().getId(), 1, new GenericsCallback<ClubData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(ClubData response, int id) {
                if (response != null && response.getCode() == 200 && response.getData() != null) {
                    adapterClub.refreshData(response.getData());
                    binding.clubNumber.setText(String.valueOf(response.getData().size()));
                }
            }

        });
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
            case R.id.friend_view:
                binding.ivFriendMore.setSelected(!binding.ivFriendMore.isSelected());
                binding.friendRecyclerView.setVisibility(binding.ivFriendMore.isSelected() ? View.VISIBLE : View.GONE);
                break;
            case R.id.fans_view:
                binding.ivFansMore.setSelected(!binding.ivFansMore.isSelected());
                binding.fansRecyclerView.setVisibility(binding.ivFansMore.isSelected() ? View.VISIBLE : View.GONE);
                break;
            case R.id.follow_view:
                binding.ivFollowMore.setSelected(!binding.ivFollowMore.isSelected());
                binding.followRecyclerView.setVisibility(binding.ivFollowMore.isSelected() ? View.VISIBLE : View.GONE);
                break;
            case R.id.club_view:
                binding.ivClubMore.setSelected(!binding.ivClubMore.isSelected());
                binding.clubRecyclerView.setVisibility(binding.ivClubMore.isSelected() ? View.VISIBLE : View.GONE);
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
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
