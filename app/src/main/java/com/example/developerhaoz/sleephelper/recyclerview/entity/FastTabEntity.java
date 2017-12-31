package com.example.developerhaoz.sleephelper.recyclerview.entity;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

/**
 * Created by lizhonglian on 2017/12/31.
 */

public class FastTabEntity implements CustomTabEntity {

    /**
     *
     * mTitle: Tab的title
     * mSelectedIcon：Tab selected后的ICON
     * mUnSelectedIcon：Tab unSelected前的ICON
     * mFragment：Tab所依附的Fragment
     *
     */
    public String mTitle;
    public int mSelectedIcon;
    public int mUnSelectedIcon;
    public Fragment mFragment;

    public FastTabEntity(String title, int unSelectedIcon, int selectedIcon, Fragment fragment) {
        this.mTitle = title;
        this.mSelectedIcon = selectedIcon;
        this.mUnSelectedIcon = unSelectedIcon;
        this.mFragment = fragment;
    }

    public FastTabEntity(int unSelectedIcon, int selectedIcon, Fragment fragment) {
        mSelectedIcon = selectedIcon;
        mUnSelectedIcon = unSelectedIcon;
        mFragment = fragment;
    }

    @Override
    public String getTabTitle() {
        if (TextUtils.isEmpty(mTitle)) {
            return "";
        }
        return mTitle;
    }

    @Override
    public int getTabSelectedIcon() {
        return mSelectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return mUnSelectedIcon;
    }

}
