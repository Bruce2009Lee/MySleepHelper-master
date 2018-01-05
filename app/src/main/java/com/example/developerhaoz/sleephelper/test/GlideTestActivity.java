package com.example.developerhaoz.sleephelper.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.developerhaoz.sleephelper.R;

public class GlideTestActivity extends AppCompatActivity {

    private Button mButton;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_test);

        mButton = (Button) findViewById(R.id.btn_glide_get);
        imageView = (ImageView) findViewById(R.id.iV_glide_get);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(GlideTestActivity.this)
                        .load("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=4054570188,2524119844&fm=27&gp=0.jpg")
                        .placeholder(R.drawable.meizhi_1)
                        .error(R.drawable.meizhi_2)
                        .into(imageView);
            }
        });
    }
}
