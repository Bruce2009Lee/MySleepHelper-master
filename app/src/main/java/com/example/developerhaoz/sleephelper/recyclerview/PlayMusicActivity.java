package com.example.developerhaoz.sleephelper.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.developerhaoz.sleephelper.R;
import com.example.developerhaoz.sleephelper.database.DBManager;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        dbManager = DBManager.getInstance(PlayMusicActivity.this);

        initView();
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

        Animation operatingAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);

        if (operatingAnim != null) {
            bgIv.startAnimation(operatingAnim);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                break;
            case R.id.iv_play:
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
}
