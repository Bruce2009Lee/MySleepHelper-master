package com.example.developerhaoz.sleephelper.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.developerhaoz.sleephelper.R;
import com.example.developerhaoz.sleephelper.util.AppConstants;
import com.example.developerhaoz.sleephelper.util.SpUtils;


public class RecyclerViewMainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";

    private Button btn_pu,btn_linear,btn_mix,btn_customDialog,btn_popWindow,btn_fragment;
    private Button btn_iosDialog,btn_glide,btn_mp3,btn_tab_bar;

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
        btn_tab_bar = (Button) findViewById(R.id.btn_tab_bar);


        btn_pu.setOnClickListener(this);
        btn_linear.setOnClickListener(this);
        btn_mix.setOnClickListener(this);
        btn_customDialog.setOnClickListener(this);
        btn_popWindow.setOnClickListener(this);
        btn_fragment.setOnClickListener(this);
        btn_iosDialog.setOnClickListener(this);
        btn_glide.setOnClickListener(this);
        btn_mp3.setOnClickListener(this);
        btn_tab_bar.setOnClickListener(this);
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
            case R.id.btn_tab_bar:
                intent = new Intent(RecyclerViewMainActivity.this,TabbarActivity.class);
                break;

        }
        startActivity(intent);

    }


    /**
     * 得到轮播图的View
     * @param context
     * @param url
     * @return
     */
    public static View getImageView(Context context, String url) {

        RelativeLayout rl = new RelativeLayout(context);
        //添加一个ImageView，并加载图片
        ImageView imageView = new ImageView(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(layoutParams);
        //使用Glide来加载图片
        Glide.with(context).load(url).into(imageView);
        //在Imageview前添加一个半透明的黑色背景，防止文字和图片混在一起
        ImageView backGround = new ImageView(context);
        backGround.setLayoutParams(layoutParams);
        backGround.setBackgroundResource(R.color.cycle_image_bg);
        rl.addView(imageView);
        rl.addView(backGround);
        return rl;
    }
}
