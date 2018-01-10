package com.example.developerhaoz.sleephelper.recyclerview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import com.example.developerhaoz.sleephelper.database.DBManager;
import com.example.developerhaoz.sleephelper.recyclerview.fragment.PlaybarFragment;
import com.example.developerhaoz.sleephelper.util.AppConstants;
import com.example.developerhaoz.sleephelper.util.SpUtils;

import java.io.File;

/**
 * Created by lizhonglian on 2018/1/9.
 */

public class PlayerManagerReceiver extends BroadcastReceiver {

    private static final String TAG = PlayerManagerReceiver.class.getName();
    public static final String ACTION_UPDATE_UI_ADAPTER = "com.lijunyan.blackmusic.receiver.PlayerManagerReceiver:action_update_ui_adapter_broad_cast";

    public static int status = AppConstants.STATUS_STOP;

    private MediaPlayer mediaPlayer;
    private DBManager dbManager;

    private int playMode;
    private int threadNumber;
    private Context context;

    public PlayerManagerReceiver() {
    }

    public PlayerManagerReceiver(Context context) {
        super();
        this.context = context;
        dbManager = DBManager.getInstance(context);
        mediaPlayer = new MediaPlayer();

        Log.d(TAG, "create");

        initMediaPlayer();
    }

    private void initMediaPlayer() {

//        NumberRandom(); // 改变线程号,使旧的播放线程停止

        int musicId = SpUtils.getIntShared(AppConstants.KEY_ID);
        int current = SpUtils.getIntShared(AppConstants.KEY_CURRENT);

        Log.d(TAG, "initMediaPlayer musicId = " + musicId);

        // 如果是没取到当前正在播放的音乐ID，则从数据库中获取第一首音乐的播放信息初始化
        if (musicId == -1) {
            return;
        }
        String path = dbManager.getMusicPath(musicId);
        if (path == null) {
            Log.e(TAG, "initMediaPlayer: path == null");
            return;
        }
        if (current == 0) {
            status = AppConstants.STATUS_STOP; // 设置播放状态为停止
        }else {
            status = AppConstants.STATUS_PAUSE; // 设置播放状态为暂停
        }

        Log.d(TAG, "initMediaPlayer status = " + status);

        SpUtils.setShared(AppConstants.KEY_ID,musicId);
        SpUtils.setShared(AppConstants.KEY_PATH,path);

        UpdateUI();
    }

    private void UpdateUI() {

        Intent playBarintent = new Intent(PlaybarFragment.ACTION_UPDATE_UI_PlayBar);    //接收广播为MusicUpdateMain
        playBarintent.putExtra(AppConstants.STATUS, status);
        context.sendBroadcast(playBarintent);

        Intent intent = new Intent(ACTION_UPDATE_UI_ADAPTER);    //接收广播为所有歌曲列表的adapter
        context.sendBroadcast(intent);

    }



    @Override
    public void onReceive(Context context, Intent intent) {

        int cmd = intent.getIntExtra(AppConstants.COMMAND,AppConstants.COMMAND_INIT);

        switch (cmd) {
            case AppConstants.COMMAND_INIT:	//已经在创建的时候初始化了，可以撤销了
                Log.d(TAG, "COMMAND_INIT");
                break;
            case AppConstants.COMMAND_PLAY:

                Log.d(TAG, "COMMAND_PLAY");

                status = AppConstants.STATUS_PLAY;
                String musicPath = intent.getStringExtra(AppConstants.KEY_PATH);
                if (musicPath!=null) {
                    playMusic(musicPath);
                }else {
                    mediaPlayer.start();
                }
                break;
            case AppConstants.COMMAND_PAUSE:
                mediaPlayer.pause();
                status = AppConstants.STATUS_PAUSE;
                break;
            case AppConstants.COMMAND_STOP: //本程序停止状态都是删除当前播放音乐触发
//                NumberRandom();
                status = AppConstants.STATUS_STOP;
                if(mediaPlayer!=null) {
                    mediaPlayer.stop();
                }
//                initStopOperate();
                break;
            case AppConstants.COMMAND_PROGRESS://拖动进度
                int curProgress = intent.getIntExtra(AppConstants.KEY_CURRENT, 0);
                //异步的，可以设置完成监听来获取真正定位完成的时候
                mediaPlayer.seekTo(curProgress);
                break;
            case AppConstants.COMMAND_RELEASE:
//                NumberRandom();
                status = AppConstants.STATUS_STOP;
                if(mediaPlayer!=null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
                break;
        }
        UpdateUI();

    }

    private void playMusic(String musicPath) {
//        NumberRandom();
        if (mediaPlayer!=null) {
            mediaPlayer.release();
        }
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d(TAG, "playMusic onCompletion: ");
//                NumberRandom();				//切换线程
//                onComplete();     //调用音乐切换模块，进行相应操作
                UpdateUI(); 				//更新界面
            }
        });

        try {
            File file = new File(musicPath);
            if(!file.exists()){
                Toast.makeText(context,"歌曲文件不存在，请重新扫描",Toast.LENGTH_SHORT).show();
                SpUtils.playNextMusic(context);
                return;
            }
            mediaPlayer.setDataSource(musicPath);   //设置MediaPlayer数据源
            mediaPlayer.prepare();
            mediaPlayer.start();

//            new UpdateUIThread(this, context, threadNumber).start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
