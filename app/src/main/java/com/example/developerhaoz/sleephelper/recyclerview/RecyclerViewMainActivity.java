package com.example.developerhaoz.sleephelper.recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.developerhaoz.sleephelper.R;
import com.example.developerhaoz.sleephelper.util.AppConstants;
import com.example.developerhaoz.sleephelper.util.SpUtils;


public class RecyclerViewMainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";

    private Button btn_pu,btn_linear,btn_mix,btn_customDialog,btn_popWindow,btn_fragment;
    private Button btn_iosDialog,btn_glide,btn_mp3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 判断是否是第一次开启应用
        boolean isFirstOpen = SpUtils.getBoolean(this, AppConstants.FIRST_OPEN);
        Log.d(TAG, "onCreate: " + isFirstOpen);
        // 如果是第一次启动，则先进入功能引导页
        if (!isFirstOpen) {
            Intent intent = new Intent(this, IntroTestActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_recycler_view_main);

        initView();
    }

    public void initView(){

        btn_pu = (Button) findViewById(R.id.btn_pu);
        btn_linear = (Button) findViewById(R.id.btn_linear);
        btn_mix = (Button) findViewById(R.id.btn_mix);
        btn_customDialog = (Button) findViewById(R.id.btn_customdialog);
        btn_popWindow = (Button) findViewById(R.id.btn_popwindow);
        btn_fragment = (Button) findViewById(R.id.btn_fragment);
        btn_iosDialog = (Button) findViewById(R.id.btn_ios_dial);
        btn_glide = (Button) findViewById(R.id.btn_glide);
        btn_mp3 = (Button) findViewById(R.id.btn_mp3);


        btn_pu.setOnClickListener(this);
        btn_linear.setOnClickListener(this);
        btn_mix.setOnClickListener(this);
        btn_customDialog.setOnClickListener(this);
        btn_popWindow.setOnClickListener(this);
        btn_fragment.setOnClickListener(this);
        btn_iosDialog.setOnClickListener(this);
        btn_glide.setOnClickListener(this);
        btn_mp3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent = null;

        switch (v.getId()){
            case R.id.btn_pu:
                intent = new Intent(RecyclerViewMainActivity.this,PuRecyclerViewActivity.class);
                break;
            case R.id.btn_linear:
                intent = new Intent(RecyclerViewMainActivity.this,LinearRecyclerViewActivity.class);
                break;
            case R.id.btn_mix:
                intent = new Intent(RecyclerViewMainActivity.this,MixRecyclerViewActivity.class);
                break;
            case R.id.btn_customdialog:
                intent = new Intent(RecyclerViewMainActivity.this,CustomDialogActivity.class);
                break;
            case R.id.btn_popwindow:
                intent = new Intent(RecyclerViewMainActivity.this,PopupWindowActivity.class);
                break;
            case R.id.btn_fragment:
                intent = new Intent(RecyclerViewMainActivity.this,ContainerActivity.class);
                break;
            case R.id.btn_ios_dial:
                intent = new Intent(RecyclerViewMainActivity.this,iOSDialogActivity.class);
                break;
            case R.id.btn_glide:
                intent = new Intent(RecyclerViewMainActivity.this,GlideTestActivity.class);
                break;
            case R.id.btn_mp3:
                intent = new Intent(RecyclerViewMainActivity.this,MP3PlayerActivity.class);
                break;

        }
        startActivity(intent);

    }
}
