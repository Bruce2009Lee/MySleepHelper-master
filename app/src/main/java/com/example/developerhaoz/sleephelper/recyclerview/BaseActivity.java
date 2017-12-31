package com.example.developerhaoz.sleephelper.recyclerview;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.developerhaoz.sleephelper.R;
import com.example.developerhaoz.sleephelper.recyclerview.inter.IBasisView;
import com.example.developerhaoz.sleephelper.util.ActivityStackUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.developerhaoz.sleephelper.R.style.BiLiPinkTheme;

public class BaseActivity extends AppCompatActivity implements IBasisView {

    protected Activity mContext;
    protected View mContentView;
    protected Unbinder mUnBinder;

    protected boolean mIsViewLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mContentView = View.inflate(mContext, getContentLayout(), null);
        setContentView(mContentView);
        mUnBinder = ButterKnife.bind(this);

        initTheme();

        mIsViewLoaded = true;
    }

    private void initTheme(){

        //TODO:
//            int themeId = MyMusicUtil.getTheme(BaseActivity.this);
        int themeId = BiLiPinkTheme;
        switch (themeId){
            default:
            case 0:
                setTheme(BiLiPinkTheme);
                break;
            case 1:
                setTheme(R.style.ZhiHuBlueTheme);
                break;
            case 2:
                setTheme(R.style.KuAnGreenTheme);
                break;
            case 3:
                setTheme(R.style.CloudRedTheme);
                break;
            case 4:
                setTheme(R.style.TengLuoPurpleTheme);
                break;
            case 5:
                setTheme(R.style.SeaBlueTheme);
                break;
            case 6:
                setTheme(R.style.GrassGreenTheme);
                break;
            case 7:
                setTheme(R.style.CoffeeBrownTheme);
                break;
            case 8:
                setTheme(R.style.LemonOrangeTheme);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        ActivityStackUtil.getInstance().pop(this, false);
    }

    @Override
    public int getContentLayout() {
        return 0;
    }

    @Override
    public int getContentBackground() {
        return 0;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void beforeSetContentView() {

    }

    @Override
    public void beforeInitView() {

        if (getContentBackground() > 0) {
            mContentView.setBackgroundResource(getContentBackground());
        }

    }

    @Override
    public void loadData() {

    }
}
