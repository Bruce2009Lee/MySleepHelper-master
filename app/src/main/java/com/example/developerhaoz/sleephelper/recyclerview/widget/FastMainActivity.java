package com.example.developerhaoz.sleephelper.recyclerview.widget;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }

    /**
     * 实现5.0以上状态栏透明(默认状态是半透明)
     */
    public void translucentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

    }
}
