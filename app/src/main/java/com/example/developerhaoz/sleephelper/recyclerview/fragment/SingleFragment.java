package com.example.developerhaoz.sleephelper.recyclerview.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.developerhaoz.sleephelper.database.DBManager;
import com.example.developerhaoz.sleephelper.recyclerview.entity.MusicInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhonglian on 2018/1/3.
 */

public class SingleFragment extends Fragment {

    private static final String TAG = "SingleFragment";

    private RelativeLayout playModeRl;
    private ImageView playModeIv;
    private TextView playModeTv;
    private RecyclerView recyclerView;
//    public  RecyclerViewAdapter recyclerViewAdapter;

    private List<MusicInfo> musicInfoList = new ArrayList<>();
    private DBManager dbManager;
    private View view;
    private Context context;
    private UpdateReceiver mReceiver;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);


    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private class UpdateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
//            recyclerViewAdapter.notifyDataSetChanged();
        }
    }
}
