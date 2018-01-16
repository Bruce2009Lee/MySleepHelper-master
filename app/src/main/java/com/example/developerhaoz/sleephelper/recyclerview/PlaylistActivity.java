package com.example.developerhaoz.sleephelper.recyclerview;

import android.os.Bundle;

import com.example.developerhaoz.sleephelper.R;

/**
 * Created by lizhonglian on 2018/1/16.
 */

public class PlaylistActivity extends PlayBarBaseActivity {

    private static final String TAG = PlaylistActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);
    }
}
