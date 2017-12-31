package com.example.developerhaoz.sleephelper.recyclerview;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.developerhaoz.sleephelper.R;
import com.example.developerhaoz.sleephelper.recyclerview.entity.Info;

import java.util.ArrayList;
import java.util.List;

public class MP3PlayerActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private LinearLayout linearLayout_local,linearLayout_recent,linearLayout_love;
    private NavigationView homeNavigationView;

    private DrawerLayout homeDrawerLayout;

    /**
     * 模拟请求后得到的数据
     */
    List<Info> mList = new ArrayList<>();

    /**
     * 轮播图
     */
    CycleViewPager mCycleViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp3_player);

        toolbar = (Toolbar) findViewById(R.id.home_activity_toolbar);
        setSupportActionBar(toolbar);
        translucentStatusBar();

        initData();
        initView();
    }

    public void initData(){
        mList.add(new Info("标题1", "http://img2.3lian.com/2014/c7/25/d/40.jpg"));
        mList.add(new Info("标题2", "http://img2.3lian.com/2014/c7/25/d/41.jpg"));
        mList.add(new Info("标题3", "http://imgsrc.baidu.com/forum/pic/item/b64543a98226cffc8872e00cb9014a90f603ea30.jpg"));
        mList.add(new Info("标题4", "http://imgsrc.baidu.com/forum/pic/item/261bee0a19d8bc3e6db92913828ba61eaad345d4.jpg"));
    }

    public void initView(){
        linearLayout_local = (LinearLayout) findViewById(R.id.ll_local);
        linearLayout_recent = (LinearLayout) findViewById(R.id.ll_recent);
        linearLayout_love = (LinearLayout) findViewById(R.id.ll_love);
        homeDrawerLayout = (DrawerLayout) findViewById(R.id.home_dl_main);
        homeNavigationView = (NavigationView) findViewById(R.id.nav_view_main);

        toolbar.setNavigationIcon(R.drawable.menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                homeDrawerLayout,
                toolbar,
                R.string.app_name,
                R.string.app_name);

        homeDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //轮播图
        mCycleViewPager = (CycleViewPager) findViewById(R.id.cycle_view);
        //设置选中和未选中时的图片
        mCycleViewPager.setIndicators(R.mipmap.ad_select, R.mipmap.ad_unselect);
        //设置轮播间隔时间
        mCycleViewPager.setDelay(2000);
        mCycleViewPager.setData(mList, mAdCycleViewListener);

        linearLayout_local.setOnClickListener(this);
        linearLayout_recent.setOnClickListener(this);
        linearLayout_love.setOnClickListener(this);

        homeNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                homeDrawerLayout.closeDrawers();

                switch (item.getItemId()){
                   case  R.id.ll_nav_theme:
                       break;
                    case  R.id.ll_nav_night:
                        break;
                    case  R.id.ll_nav_about:
                        break;
                    case  R.id.ll_nav_quit:
                        break;

                }
                Toast.makeText(MP3PlayerActivity.this,"onSelected " + item.getItemId(),Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    /**
     * 实现5.0以上状态栏透明(默认状态是半透明)
     */
    private void translucentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

    }

    @Override
    public void onClick(View v) {

        Intent intent = null;
        switch (v.getId()){
            case R.id.ll_local:
                intent = new Intent(MP3PlayerActivity.this,LocalMusicActivity.class);
                break;
            case R.id.ll_recent:
                intent = new Intent(MP3PlayerActivity.this,RecentMusicActivity.class);
                break;
            case R.id.ll_love:
                intent = new Intent(MP3PlayerActivity.this,FavoriteMusicActivity.class);
                break;
        }
        startActivity(intent);
    }

    /**
     * 轮播图点击监听
     */
    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener =
            new CycleViewPager.ImageCycleViewListener() {

                @Override
                public void onImageClick(Info info, int position, View imageView) {

                    if (mCycleViewPager.isCycle()) {
                        position = position - 1;
                    }
                    Toast.makeText(MP3PlayerActivity.this, info.getTitle() +
                            "选择了--" + position, Toast.LENGTH_LONG).show();
                }
            };
}
