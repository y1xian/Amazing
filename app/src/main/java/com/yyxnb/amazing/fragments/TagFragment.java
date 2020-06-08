package com.yyxnb.amazing.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yyxnb.amazing.R;
import com.yyxnb.arch.base.IFragment;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class TagFragment extends Fragment implements IFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tag, container, false);
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }
}