package com.example.developerhaoz.sleephelper.recyclerview.widget;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.developerhaoz.sleephelper.R;

/**
 * Created by lizhonglian on 2017/12/31.
 */

public class FastConfig {

    private static volatile FastConfig sInstance;
    private static Context mContext;

    public static FastConfig getInstance(@Nullable Context context) {
        if (sInstance == null) {
            synchronized (FastConfig.class) {
                if (sInstance == null) {
                    sInstance = new FastConfig(context);
                }
            }
        }
        return sInstance;
    }

    private FastConfig(@Nullable Context context) {
        if (context == null) {
            throw new NullPointerException(FastConstant.EXCEPTION_FAST_CONFIG_CONTEXT_NOT_NULL);
        }
    }

}
