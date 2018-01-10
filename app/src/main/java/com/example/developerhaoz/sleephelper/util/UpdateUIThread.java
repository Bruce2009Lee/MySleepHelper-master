package com.example.developerhaoz.sleephelper.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.developerhaoz.sleephelper.recyclerview.PlayerManagerReceiver;
import com.example.developerhaoz.sleephelper.recyclerview.fragment.PlaybarFragment;

/**
 * Created by lizhonglian on 2018/1/10.
 */

public class UpdateUIThread  extends Thread{

    private static final String TAG = UpdateUIThread.class.getName();
    private int threadNumber;
    private Context context;
    private PlayerManagerReceiver playerManagerReceiver;
    private int duration;
    private int curPosition;

    public UpdateUIThread(PlayerManagerReceiver playerManagerReceiver, Context context, int threadNumber) {
        Log.i(TAG, "UpdateUIThread: " );

        this.playerManagerReceiver = playerManagerReceiver;
        this.context = context;
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {
        try {
            while (playerManagerReceiver.getThreadNumber() == this.threadNumber){
                if (playerManagerReceiver.status == AppConstants.STATUS_STOP) {
                    break;
                }

                if (playerManagerReceiver.status == AppConstants.STATUS_PLAY ||
                        playerManagerReceiver.status == AppConstants.STATUS_PAUSE) {
                    if (!playerManagerReceiver.getMediaPlayer().isPlaying()) {
                        Log.i(TAG, "run: getMediaPlayer().isPlaying() = " + playerManagerReceiver.getMediaPlayer().isPlaying());
                        break;
                    }
                    duration = playerManagerReceiver.getMediaPlayer().getDuration();
                    curPosition = playerManagerReceiver.getMediaPlayer().getCurrentPosition();

                    Intent intent = new Intent(PlaybarFragment.ACTION_UPDATE_UI_PlayBar);
                    intent.putExtra(AppConstants.STATUS, AppConstants.STATUS_RUN);
                    intent.putExtra(AppConstants.KEY_DURATION, duration);
                    intent.putExtra(AppConstants.KEY_CURRENT, curPosition);
                    context.sendBroadcast(intent);
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
