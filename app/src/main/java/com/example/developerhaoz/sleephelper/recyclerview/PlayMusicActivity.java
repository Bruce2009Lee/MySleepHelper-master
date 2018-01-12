package com.example.developerhaoz.sleephelper.recyclerview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.developerhaoz.sleephelper.R;
import com.example.developerhaoz.sleephelper.database.DBManager;
import com.example.developerhaoz.sleephelper.recyclerview.fragment.PlaybarFragment;
import com.example.developerhaoz.sleephelper.util.AppConstants;
import com.example.developerhaoz.sleephelper.util.SpUtils;

import java.util.Locale;

public class PlayMusicActivity extends AppCompatActivity implements AdapterView.OnClickListener{

    private static final String TAG = PlayMusicActivity.class.getName();

    private DBManager dbManager;

    private ImageView backIv;
    private ImageView playIv;
    private ImageView menuIv;
    private ImageView preIv;
    private ImageView nextIv;
    private ImageView modeIv;
    private ImageView bgIv;

    private TextView curTimeTv;
    private TextView totalTimeTv;

    private TextView musicNameTv;
    private TextView singerNameTv;

    private SeekBar seekBar;
    private PlayReceiver mReceiver;


    private int mProgress;
    private int duration;
    private int current;

    Animation operatingAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        setStyle();

        dbManager = DBManager.getInstance(PlayMusicActivity.this);

        initView();
        initPlayMode();
        initTitle();
        initPlayIv();
        register();

    }

    private void register() {

        mReceiver = new PlayReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PlaybarFragment.ACTION_UPDATE_UI_PlayBar);
        registerReceiver(mReceiver, intentFilter);
    }

    private void initView(){
        backIv = (ImageView) findViewById(R.id.iv_back);
        playIv = (ImageView) findViewById(R.id.iv_play);
        menuIv = (ImageView) findViewById(R.id.iv_menu);
        preIv = (ImageView) findViewById(R.id.iv_prev);
        nextIv = (ImageView) findViewById(R.id.iv_next);
        modeIv = (ImageView) findViewById(R.id.iv_mode);
        bgIv = (ImageView) findViewById(R.id.iv_disc_rotate);

        curTimeTv = (TextView) findViewById(R.id.tv_current_time);
        totalTimeTv = (TextView) findViewById(R.id.tv_total_time);
        musicNameTv = (TextView) findViewById(R.id.tv_title);
        singerNameTv = (TextView) findViewById(R.id.tv_artist);
        seekBar = (SeekBar) findViewById(R.id.activity_play_seekbar);
        backIv.setOnClickListener(this);
        playIv.setOnClickListener(this);
        menuIv.setOnClickListener(this);
        preIv.setOnClickListener(this);
        nextIv.setOnClickListener(this);
        modeIv.setOnClickListener(this);

        operatingAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        operatingAnim.setFillAfter(true);
        operatingAnim.setFillBefore(false);

    }

    private void initPlayMode() {
        int playMode = SpUtils.getIntShared(AppConstants.KEY_MODE);
        if (playMode == -1) {
            playMode = 0;
        }
        modeIv.setImageLevel(playMode);
    }

    private void initTitle() {
        int musicId = SpUtils.getIntShared(AppConstants.KEY_ID);
        if (musicId == -1) {
            musicNameTv.setText("听听音乐");
            singerNameTv.setText("好音质");
        } else {
            musicNameTv.setText(dbManager.getMusicInfo(musicId).get(1));
            singerNameTv.setText(dbManager.getMusicInfo(musicId).get(2));
        }
    }

    private void initPlayIv(){
        int status = PlayerManagerReceiver.status;
        switch (status) {
            case AppConstants.STATUS_STOP:
                playIv.setSelected(false);
                break;
            case AppConstants.STATUS_PLAY:
                playIv.setSelected(true);
                break;
            case AppConstants.STATUS_PAUSE:
                playIv.setSelected(false);
                break;
            case AppConstants.STATUS_RUN:
                playIv.setSelected(true);
                break;
        }
    }

    class PlayReceiver extends BroadcastReceiver {

        int status;

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: ");

            initTitle();
            status = intent.getIntExtra(AppConstants.STATUS, 0);
            current = intent.getIntExtra(AppConstants.KEY_CURRENT, 0);
            duration = intent.getIntExtra(AppConstants.KEY_DURATION, 100);

            Log.d(TAG, "status: "+ status);
            Log.d(TAG, "current: "+ current);
            Log.d(TAG, "duration: "+ duration);

            switch (status) {
                case AppConstants.STATUS_STOP:
                    playIv.setSelected(false);
                    break;
                case AppConstants.STATUS_PLAY:
                    playIv.setSelected(true);
                    seekBar.setMax(duration);
                    seekBar.setProgress(current);
                    totalTimeTv.setText(formatTime(duration));
                    curTimeTv.setText(formatTime(current));
                    break;
                case AppConstants.STATUS_PAUSE:
                    playIv.setSelected(false);
                    break;
                case AppConstants.STATUS_RUN:
                    playIv.setSelected(true);
                    seekBar.setMax(duration);
                    seekBar.setProgress(current);
                    totalTimeTv.setText(formatTime(duration));
                    curTimeTv.setText(formatTime(current));
                    break;
                default:
                    break;
            }


        }
    }

    private void setStyle() {
        if (Build.VERSION.SDK_INT >= 21) {

            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private String formatTime(long time) {
        return formatTime("mm:ss", time);
    }

    public static String formatTime(String pattern, long milli) {
        int m = (int) (milli / DateUtils.MINUTE_IN_MILLIS);
        int s = (int) ((milli / DateUtils.SECOND_IN_MILLIS) % 60);
        String mm = String.format(Locale.getDefault(), "%02d", m);
        String ss = String.format(Locale.getDefault(), "%02d", s);
        return pattern.replace("mm", mm).replace("ss", ss);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_play:
                play();
                break;
            case R.id.iv_menu:
                break;
            case R.id.iv_prev:
                break;
            case R.id.iv_next:
                break;
            case R.id.iv_mode:
                break;
        }
    }

    private void play() {
        int musicId;
        musicId = SpUtils.getIntShared(AppConstants.KEY_ID);

        if (musicId == -1 || musicId == 0) {
            musicId = dbManager.getFirstId(AppConstants.LIST_ALLMUSIC);
            Intent intent = new Intent(AppConstants.MP_FILTER);
            intent.putExtra(AppConstants.COMMAND, AppConstants.COMMAND_STOP);
            sendBroadcast(intent);
            Toast.makeText(PlayMusicActivity.this, "歌曲不存在", Toast.LENGTH_SHORT).show();
            return;
        }

        //如果当前媒体在播放音乐状态，则图片显示暂停图片，按下播放键，则发送暂停媒体命令，图片显示播放图片。以此类推。
        if (PlayerManagerReceiver.status == AppConstants.STATUS_PAUSE) {
            Intent intent = new Intent(PlayMusicService.PLAYER_MANAGER_ACTION);
            intent.putExtra(AppConstants.COMMAND, AppConstants.COMMAND_PLAY);

            int tmpCurrent = SpUtils.getIntShared("KEY_CURRENT");
            int tmpDuration = SpUtils.getIntShared("KEY_DURATION");
            intent.putExtra(AppConstants.KEY_CURRENT, tmpCurrent);
            intent.putExtra(AppConstants.KEY_DURATION, tmpDuration);

            if (operatingAnim != null) {
                bgIv.startAnimation(operatingAnim);
            }

            sendBroadcast(intent);
        } else if (PlayerManagerReceiver.status == AppConstants.STATUS_PLAY) {

            Intent intent = new Intent(PlayMusicService.PLAYER_MANAGER_ACTION);
            intent.putExtra(AppConstants.COMMAND, AppConstants.COMMAND_PAUSE);

            SpUtils.setShared("KEY_CURRENT",current);
            SpUtils.setShared("KEY_DURATION",duration);

            if (operatingAnim != null) {
                operatingAnim.cancel();
            }

            sendBroadcast(intent);
        } else {

            //为停止状态时发送播放命令，并发送将要播放歌曲的路径
            String path = dbManager.getMusicPath(musicId);
            Intent intent = new Intent(PlayMusicService.PLAYER_MANAGER_ACTION);
            intent.putExtra(AppConstants.COMMAND, AppConstants.COMMAND_PLAY);
            intent.putExtra(AppConstants.KEY_PATH, path);

            Log.i(TAG, "onClick: path = " + path);

            if (operatingAnim != null) {
                bgIv.startAnimation(operatingAnim);
            }

            sendBroadcast(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}
