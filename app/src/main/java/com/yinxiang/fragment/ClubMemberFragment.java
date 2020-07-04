package com.yinxiang.fragment;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.yinxiang.R;
import com.yinxiang.adapter.MemberAdapter;
import com.yinxiang.databinding.FragmentClubMenberBinding;
import com.yinxiang.model.ClubMember;
import com.yinxiang.view.OnClickListener;

import okhttp3.Call;

public class ClubMemberFragment extends BaseFragment {

    private FragmentClubMenberBinding binding;

    private static final String ARG_CID = "cid";

    private int cid;

    private OnFragmentInteractionListener mListener;
    private MemberAdapter adapter;

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

        adapter = new MemberAdapter(getActivity());
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

        channelClubMember();
        return binding.getRoot();
    }

    private void channelClubMember() {
        SendRequest.channelClubMember(cid, 10, new GenericsCallback<ClubMember>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(ClubMember response, int id) {
                if (response.getCode() == 200 && response.getData() != null && response.getData().getData() != null) {
                    adapter.refreshData(response.getData().getData());
                    binding.clubMemberNum.setText("...  共"+response.getData().getData().size()+"人");
                } else {
                    ToastUtils.showShort(getActivity(), response.getMsg());
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
