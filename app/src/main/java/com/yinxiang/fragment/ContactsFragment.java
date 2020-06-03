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
import com.yinxiang.R;
import com.yinxiang.adapter.ContactsAdapter;
import com.yinxiang.adapter.MessageAdapter;
import com.yinxiang.databinding.FragmentContactsBinding;
import com.yinxiang.databinding.FragmentMessageBinding;

public class ContactsFragment extends BaseFragment implements View.OnClickListener {

    private FragmentContactsBinding binding;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

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

        ContactsAdapter adapterFriend = new ContactsAdapter(getActivity());
        binding.friendRecyclerView.setNestedScrollingEnabled(false);
        binding.friendRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.friendRecyclerView.setNestedScrollingEnabled(false);
        binding.friendRecyclerView.setAdapter(adapterFriend);
        adapterFriend.refreshData(CommonUtil.getImageListString());

        ContactsAdapter adapterFans = new ContactsAdapter(getActivity());
        binding.fansRecyclerView.setNestedScrollingEnabled(false);
        binding.fansRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.fansRecyclerView.setNestedScrollingEnabled(false);
        binding.fansRecyclerView.setAdapter(adapterFans);
        adapterFans.refreshData(CommonUtil.getImageListString());

        ContactsAdapter adapterFollow = new ContactsAdapter(getActivity());
        binding.followRecyclerView.setNestedScrollingEnabled(false);
        binding.followRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.followRecyclerView.setNestedScrollingEnabled(false);
        binding.followRecyclerView.setAdapter(adapterFollow);
        adapterFollow.refreshData(CommonUtil.getImageListString());

        ContactsAdapter adapterClub = new ContactsAdapter(getActivity());
        binding.clubRecyclerView.setNestedScrollingEnabled(false);
        binding.clubRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.clubRecyclerView.setNestedScrollingEnabled(false);
        binding.clubRecyclerView.setAdapter(adapterClub);

        adapterClub.refreshData(CommonUtil.getImageListString());

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
