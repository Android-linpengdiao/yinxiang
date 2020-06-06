package com.yinxiang.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yinxiang.R;
import com.yinxiang.databinding.FragmentClubDescBinding;

public class ClubDescFragment extends BaseFragment {

    private FragmentClubDescBinding binding;

    private static final String ARG_DESC = "desc";


    private String desc;

    private OnFragmentInteractionListener mListener;

    public ClubDescFragment() {

    }


    public static ClubDescFragment newInstance(String desc) {
        ClubDescFragment fragment = new ClubDescFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DESC, desc);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            desc = getArguments().getString(ARG_DESC);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_club_desc, container, false);

        binding.clubDesc.setText(desc);

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
