package com.example.developerhaoz.sleephelper.test;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.example.developerhaoz.sleephelper.R;
import com.example.developerhaoz.sleephelper.fragment.SlideFragment;
import com.example.developerhaoz.sleephelper.recyclerview.MP3PlayerActivity;
import com.example.developerhaoz.sleephelper.recyclerview.RecyclerViewMainActivity;
import com.example.developerhaoz.sleephelper.util.AppConstants;
import com.example.developerhaoz.sleephelper.util.SpUtils;
import com.github.paolorotolo.appintro.AppIntro;

import java.util.Timer;
import java.util.TimerTask;

public class IntroTestActivity extends AppIntro {


    private static final int PERMISSON_REQUESTCODE = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPermission();
    }

    private void initPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            checkSkip();
            return;
        }
        if (ContextCompat.checkSelfPermission(IntroTestActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(IntroTestActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSON_REQUESTCODE);
        }else {
            checkSkip();
        }
    }

    private void checkSkip() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startMusicActivity();
            }
        };
        timer.schedule(task, 1000);
    }

    private void startMusicActivity() {
        Intent intent = new Intent();
        intent.setClass(this, MP3PlayerActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {

        addSlide(SlideFragment.newInstance(R.layout.fragment_c));
        addSlide(SlideFragment.newInstance(R.layout.fragment_d));
        setSeparatorColor(getResources().getColor(R.color.colorAccent));
        setVibrateIntensity(30);
        setSkipText("跳过");
        setDoneText("完成");
    }

    @Override
    public void onSkipPressed() {
        Intent intent = new Intent(IntroTestActivity.this, RecyclerViewMainActivity.class);
        startActivity(intent);
        SpUtils.putBoolean(IntroTestActivity.this, AppConstants.FIRST_OPEN, true);
        finish();
    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onDonePressed() {
        Intent intent = new Intent(IntroTestActivity.this, RecyclerViewMainActivity.class);
        startActivity(intent);
        SpUtils.putBoolean(IntroTestActivity.this, AppConstants.FIRST_OPEN, true);
        finish();
    }

    @Override
    public void onSlideChanged() {

    }

}
