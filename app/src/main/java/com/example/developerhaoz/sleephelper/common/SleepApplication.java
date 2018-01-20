package com.example.developerhaoz.sleephelper.common;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.developerhaoz.sleephelper.recyclerview.PlayMusicService;

import org.xutils.x;

/**
 * 自定义的 Application
 *
 * Created by developerHaoz on 2017/5/3.
 */

public class SleepApplication extends Application {

    private static Context mContext;
    private static RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        Intent startIntent = new Intent(SleepApplication.this,PlayMusicService.class);
        startService(startIntent);
        Log.d("SleepApplicationLL ","onCreate");

        requestQueue = Volley.newRequestQueue(mContext);

        x.Ext.init(this);
        x.Ext.setDebug(false);
    }

    public static Context getContext(){
        return mContext;
    }

    public  RequestQueue getRequestQueue(){
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }
}