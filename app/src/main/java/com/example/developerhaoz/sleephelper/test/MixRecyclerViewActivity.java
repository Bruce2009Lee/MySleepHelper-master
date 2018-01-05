package com.example.developerhaoz.sleephelper.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.developerhaoz.sleephelper.R;
import com.example.developerhaoz.sleephelper.recyclerview.adapter.MixRecyclerViewAdapter;

public class MixRecyclerViewActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mix_recycler_view);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_mix);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MixRecyclerViewActivity.this));
        mRecyclerView.setAdapter(new MixRecyclerViewAdapter(MixRecyclerViewActivity.this, new MixRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MixRecyclerViewActivity.this,"点击了"+position ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(int position) {

            }
        }));
    }
}
