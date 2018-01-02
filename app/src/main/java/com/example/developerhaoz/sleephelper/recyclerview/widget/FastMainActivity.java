package com.example.developerhaoz.sleephelper.recyclerview.widget;

import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.example.developerhaoz.sleephelper.R;
import com.example.developerhaoz.sleephelper.recyclerview.BaseActivity;
import com.example.developerhaoz.sleephelper.recyclerview.entity.FastTabEntity;
import com.example.developerhaoz.sleephelper.recyclerview.inter.IFastMainView;
import com.flyco.tablayout.CommonTabLayout;

import java.util.List;

/**
 * Created by lizhonglian on 2018/1/2.
 */

public abstract class FastMainActivity extends BaseActivity implements IFastMainView {
    @Override
    public boolean isSwipeEnable() {
        return false;
    }

    @Nullable
    @Override
    public List<FastTabEntity> getTabList() {
        return null;
    }

    @Override
    public void setTabLayout(CommonTabLayout tabLayout) {

    }

    @Override
    public void setViewPager(ViewPager mViewPager) {

    }

    @Override
    public void onTabSelect(int position) {

    }

    @Override
    public void onTabReselect(int position) {

    }

    @Override
    public int getContentLayout() {
        return  R.layout.fast_activity_main;
    }
}
