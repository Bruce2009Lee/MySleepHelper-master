package com.example.developerhaoz.sleephelper.recyclerview.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.developerhaoz.sleephelper.R;

/**
 * Created by lizhonglian on 2018/1/3.
 */

public class SingerFragment extends Fragment {

    private static final String TAG = "SingerFragment";


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

//        view = inflater.inflate(R.layout.fragment_singer,container,false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
