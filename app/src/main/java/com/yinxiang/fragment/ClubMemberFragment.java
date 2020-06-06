package com.yinxiang.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baselibrary.utils.CommonUtil;
import com.yinxiang.R;
import com.yinxiang.activity.ClubDetailActivity;
import com.yinxiang.activity.WorkDetailActivity;
import com.yinxiang.adapter.ClubAdapter;
import com.yinxiang.adapter.MemberAdapter;
import com.yinxiang.adapter.WorkAdapter;
import com.yinxiang.databinding.FragmentClubMenberBinding;
import com.yinxiang.databinding.FragmentClubWorkBinding;
import com.yinxiang.view.GridItemDecoration;
import com.yinxiang.view.OnClickListener;

public class ClubMemberFragment extends BaseFragment {

    private FragmentClubMenberBinding binding;

    private static final String ARG_CID = "cid";

    private int cid;

    private OnFragmentInteractionListener mListener;

    public ClubMemberFragment() {

    }


    public static ClubMemberFragment newInstance(int cid) {
        ClubMemberFragment fragment = new ClubMemberFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CID, cid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cid = getArguments().getInt(ARG_CID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_club_menber, container, false);

        MemberAdapter adapter = new MemberAdapter(getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {

            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });
        adapter.refreshData(CommonUtil.getImageListString());

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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
