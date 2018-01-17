package com.example.developerhaoz.sleephelper.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.developerhaoz.sleephelper.R;
import com.example.developerhaoz.sleephelper.database.DBManager;
import com.example.developerhaoz.sleephelper.recyclerview.adapter.HomeListViewAdapter;
import com.example.developerhaoz.sleephelper.recyclerview.entity.Info;
import com.example.developerhaoz.sleephelper.recyclerview.entity.PlayListInfo;
import com.example.developerhaoz.sleephelper.test.CycleViewPager;
import com.example.developerhaoz.sleephelper.util.ActivityStackUtil;
import com.example.developerhaoz.sleephelper.util.AppConstants;
import com.example.developerhaoz.sleephelper.util.SpUtils;

import java.util.ArrayList;
import java.util.List;

import static com.example.developerhaoz.sleephelper.recyclerview.ThemeActivity.CHANGE;

public class MP3PlayerActivity extends PlayBarBaseActivity implements View.OnClickListener {

    private static final String TAG = MP3PlayerActivity.class.getName();
    public static MP3PlayerActivity instance;

    private Toolbar toolbar;
    private LinearLayout linearLayout_local, linearLayout_recent, linearLayout_love;
    private LinearLayout myListTitleLl;
    private NavigationView homeNavigationView;

    private TextView localSong;
    private TextView latelySong;
    private TextView favSong;

    private ImageView myPLArrowIv;
    private ListView listView;

    private HomeListViewAdapter adapter;

    private DBManager dbManager;
    private List<PlayListInfo> playListInfos;

    private DrawerLayout homeDrawerLayout;

    private boolean isOpenMyPL = false; //标识我的歌单列表打开状态



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
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("爱音乐");
        }

        instance = MP3PlayerActivity.this;

        dbManager = DBManager.getInstance(MP3PlayerActivity.this);

        //是否沉浸式展示
//        translucentStatusBar();

        initData();
        initView();
    }

    public void initData() {

        mList.add(new Info("标题1", "http://img2.3lian.com/2014/c7/25/d/40.jpg"));
        mList.add(new Info("标题2", "http://img2.3lian.com/2014/c7/25/d/41.jpg"));
        mList.add(new Info("标题3", "http://imgsrc.baidu.com/forum/pic/item/b64543a98226cffc8872e00cb9014a90f603ea30.jpg"));
        mList.add(new Info("标题4", "http://imgsrc.baidu.com/forum/pic/item/261bee0a19d8bc3e6db92913828ba61eaad345d4.jpg"));

    }

    public void initView() {

        linearLayout_local = (LinearLayout) findViewById(R.id.ll_local);
        localSong = (TextView) findViewById(R.id.tv_local_songs);
        linearLayout_recent = (LinearLayout) findViewById(R.id.ll_recent);
        latelySong = (TextView) findViewById(R.id.tv_recent_songs);
        linearLayout_love = (LinearLayout) findViewById(R.id.ll_love);
        favSong = (TextView) findViewById(R.id.tv_love_songs);
        homeDrawerLayout = (DrawerLayout) findViewById(R.id.home_dl_main);
        homeNavigationView = (NavigationView) findViewById(R.id.nav_view_main);
        myListTitleLl = (LinearLayout) findViewById(R.id.home_my_list_title_ll);
        myPLArrowIv = (ImageView) findViewById(R.id.home_my_pl_arror_iv);
        listView = (ListView) findViewById(R.id.home_my_list_lv);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationIcon(R.drawable.drawer_menu);
        }

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

                Intent intent = null;

                switch (item.getItemId()) {
                    case R.id.menu_me_item_theme:
                        intent = new Intent(MP3PlayerActivity.this, ThemeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_me_item_night:
                        break;
                    case R.id.menu_me_item_about:
                        intent = new Intent(MP3PlayerActivity.this, AboutMeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_me_item_logout:
                        finish();
                        Intent intentBroadcast = new Intent(PlayMusicService.PLAYER_MANAGER_ACTION);
                        intentBroadcast.putExtra(AppConstants.COMMAND, AppConstants.COMMAND_RELEASE);
                        sendBroadcast(intentBroadcast);

                        Intent stopIntent = new Intent(MP3PlayerActivity.this,PlayMusicService.class);
                        stopService(stopIntent);
                        break;

                }
                return true;
            }
        });

        playListInfos = dbManager.getMyPlayList();
        adapter = new HomeListViewAdapter(playListInfos,this,dbManager);
        listView.setAdapter(adapter);

        myListTitleLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MP3PlayerActivity.this,"点击了 List",Toast.LENGTH_SHORT).show();

                if (isOpenMyPL){

                    isOpenMyPL = false;
                    myPLArrowIv.setImageResource(R.drawable.arrow_right);
                    listView.setVisibility(View.GONE);
                }else {
                    isOpenMyPL = true;
                    myPLArrowIv.setImageResource(R.drawable.arrow_down);
                    listView.setVisibility(View.VISIBLE);
                    /*playListInfos = dbManager.getMyPlayList();
                    adapter = new HomeListViewAdapter(playListInfos,MP3PlayerActivity.this,dbManager);
                    listView.setAdapter(adapter);*/
                }

            }
        });

        int songNum = dbManager.getMusicCount(AppConstants.LIST_ALLMUSIC);
        Log.d(TAG, "songNum :" + songNum);

        if (0 != songNum) {
            localSong.setText("" + songNum);
        }

    }

    @Override
    public void onClick(View v) {

        Intent intent = null;
        switch (v.getId()) {
            case R.id.ll_local:
                intent = new Intent(MP3PlayerActivity.this, LocalMusicActivity.class);
                break;
            case R.id.ll_recent:
                intent = new Intent(MP3PlayerActivity.this, RecentMusicActivity.class);
                break;
            case R.id.ll_love:
                intent = new Intent(MP3PlayerActivity.this, FavoriteMusicActivity.class);
                break;
        }
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        if (homeDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            homeDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
                    Toast.makeText(MP3PlayerActivity.this, info.getTitle() + "选择了--" + position, Toast.LENGTH_LONG).show();
                }
            };


    @Override
    protected void onResume() {
        super.onResume();

        int songNum = dbManager.getMusicCount(AppConstants.LIST_ALLMUSIC);
        int themeID = SpUtils.getTheme(this);
        setTheme(themeID);
        Log.d(TAG,"onResume" + themeID);
        if (0 != songNum) {
            localSong.setText("" + songNum);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG,"onRestart");
        super.onRestart();
    }
}
