package com.example.developerhaoz.sleephelper.recyclerview;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by lizhonglian on 2018/1/9.
 */

public class PlayMusicService extends Service {

    private static final String TAG = PlayMusicService.class.getName();

    public static final String PLAYER_MANAGER_ACTION = "com.example.developerhaoz.sleephelper.recyclerview.player.action";

    private PlayerManagerReceiver mReceiver;

    public PlayMusicService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegister();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        register();
        Log.i(TAG,"PlayMusicService onCreate");
    }

    private void register() {

        mReceiver = new PlayerManagerReceiver(PlayMusicService.this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PLAYER_MANAGER_ACTION);
        registerReceiver(mReceiver, intentFilter);
        Log.i(TAG,"registerReceiver");
    }

    private void unRegister() {
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
    }
}
