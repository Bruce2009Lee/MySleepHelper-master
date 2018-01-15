package com.example.developerhaoz.sleephelper.recyclerview;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;

import com.example.developerhaoz.sleephelper.R;
import com.example.developerhaoz.sleephelper.recyclerview.fragment.PlaybarFragment;
import com.example.developerhaoz.sleephelper.recyclerview.widget.FastMainActivity;

/**
 * Created by lizhonglian on 2018/1/6.
 */

public class PlayBarBaseActivity extends FastMainActivity {

    private PlaybarFragment playBarFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        show();
    }

    private void show(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (playBarFragment == null) {

            playBarFragment = PlaybarFragment.newInstance();
            ft.add(R.id.fragment_playbar, playBarFragment).commit();
        }else {
            ft.show(playBarFragment).commit();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
