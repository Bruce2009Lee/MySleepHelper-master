package com.example.developerhaoz.sleephelper.recyclerview.entity;

import android.support.annotation.DrawableRes;

/**
 * Created by lizhonglian on 2017/12/31.
 */

public interface CustomTabEntity {

    String getTabTitle();

    @DrawableRes
    int getTabSelectedIcon();

    @DrawableRes
    int getTabUnselectedIcon();
}
