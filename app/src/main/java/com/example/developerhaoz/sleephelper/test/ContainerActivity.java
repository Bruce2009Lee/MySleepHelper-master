package com.example.developerhaoz.sleephelper.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.developerhaoz.sleephelper.R;
import com.example.developerhaoz.sleephelper.fragment.AFragment;
import com.example.developerhaoz.sleephelper.fragment.BFragment;

public class ContainerActivity extends AppCompatActivity implements BFragment.onButtonClick{

    private AFragment aFragment;
    private TextView mtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        mtext = (TextView) findViewById(R.id.tv_frag_title);

        aFragment = AFragment.newInstance("我是来自Activity的参数");
        getFragmentManager().beginTransaction().add(R.id.fl_container,aFragment,"aFragment").commitAllowingStateLoss();

    }

    public void setData(String title){
        if (null != title){
            mtext.setText(title);
        }
    }

    @Override
    public void onButtonClick(String msg) {
         mtext.setText(msg);
    }
}
