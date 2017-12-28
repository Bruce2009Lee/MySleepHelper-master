package com.example.developerhaoz.sleephelper.recyclerview;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.developerhaoz.sleephelper.R;

public class MP3PlayerActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private LinearLayout linearLayout_local,linearLayout_recent,linearLayout_love;
    private NavigationView homeNavigationView;

    private DrawerLayout homeDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp3_player);

        toolbar = (Toolbar) findViewById(R.id.home_activity_toolbar);
        setSupportActionBar(toolbar);
        translucentStatusBar();


        initView();
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
}
