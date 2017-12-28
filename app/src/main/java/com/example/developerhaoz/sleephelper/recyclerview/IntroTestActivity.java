package com.example.developerhaoz.sleephelper.recyclerview;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.developerhaoz.sleephelper.R;
import com.example.developerhaoz.sleephelper.fragment.SlideFragment;
import com.example.developerhaoz.sleephelper.util.AppConstants;
import com.example.developerhaoz.sleephelper.util.SpUtils;
import com.github.paolorotolo.appintro.AppIntro;

public class IntroTestActivity extends AppIntro {

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
