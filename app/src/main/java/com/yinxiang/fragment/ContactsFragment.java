package com.yinxiang.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.yinxiang.R;
import com.yinxiang.activity.MyFansActivity;
import com.yinxiang.activity.MyFollowActivity;
import com.yinxiang.adapter.ContactsAdapter;
import com.yinxiang.adapter.ContactsClubAdapter;
import com.yinxiang.adapter.ContactsFansAdapter;
import com.yinxiang.adapter.ContactsFollowAdapter;
import com.yinxiang.adapter.MessageAdapter;
import com.yinxiang.databinding.FragmentContactsBinding;
import com.yinxiang.databinding.FragmentMessageBinding;
import com.yinxiang.model.ClubData;
import com.yinxiang.model.FansUserData;
import com.yinxiang.model.FollowUserData;

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

        adapterFans = new ContactsFansAdapter(getActivity());
        binding.fansRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.fansRecyclerView.setNestedScrollingEnabled(false);
        binding.fansRecyclerView.setAdapter(adapterFans);

        adapterFollow = new ContactsFollowAdapter(getActivity());
        binding.followRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.followRecyclerView.setNestedScrollingEnabled(false);
        binding.followRecyclerView.setAdapter(adapterFollow);

        adapterClub = new ContactsClubAdapter(getActivity());
        binding.clubRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.clubRecyclerView.setNestedScrollingEnabled(false);
        binding.clubRecyclerView.setAdapter(adapterClub);

        initData();

        return binding.getRoot();
    }

    private void initData() {
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
        SendRequest.channelClub(getUserInfo().getData().getId(),1, new GenericsCallback<ClubData>(new JsonGenericsSerializator()) {
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
}
