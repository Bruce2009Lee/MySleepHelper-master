package com.example.developerhaoz.sleephelper.test;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.developerhaoz.sleephelper.R;
import com.example.developerhaoz.sleephelper.recyclerview.adapter.LinearRecyclerViewAdapter;

public class LinearRecyclerViewActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_recycler_view);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_linear);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(LinearRecyclerViewActivity.this,LinearLayoutManager.VERTICAL,false));
        mRecyclerView.addItemDecoration(new MyDecoration());
        mRecyclerView.setAdapter(new LinearRecyclerViewAdapter(LinearRecyclerViewActivity.this, new LinearRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(LinearRecyclerViewActivity.this,"点击了" + position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(int position) {

            }
        }));

    }

    class MyDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(0,0,0,getResources().getDimensionPixelOffset(R.dimen.dividerHeight));
        }
    }
}
