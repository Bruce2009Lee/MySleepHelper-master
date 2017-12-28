package com.example.developerhaoz.sleephelper.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.developerhaoz.sleephelper.R;

public class EventActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_inner,btn_anno,btn_class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        btn_inner = (Button) findViewById(R.id.btn_inner);
        btn_anno = (Button) findViewById(R.id.btn_anno);
        btn_class = (Button) findViewById(R.id.btn_class);

        //内部类实现点击事件
        btn_inner.setOnClickListener(new MyClickListener());

        //匿名内部类
        btn_anno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EventActivity.this,"匿名内部类实现点击事件",Toast.LENGTH_SHORT).show();
            }
        });

        btn_class.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_class:
                Toast.makeText(EventActivity.this,"Activity 实现点击事件",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    class MyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_inner:
                    Toast.makeText(EventActivity.this,"内部类实现点击事件",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
