package com.example.developerhaoz.sleephelper.test;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.example.developerhaoz.sleephelper.R;
import com.example.developerhaoz.sleephelper.recyclerview.adapter.PuRecyclerViewAdapter;


public class PuRecyclerViewActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pu_recycler_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_pu);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.addItemDecoration(new MyDecoration());
        mRecyclerView.setAdapter(new PuRecyclerViewAdapter(PuRecyclerViewActivity.this, new PuRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(PuRecyclerViewActivity.this,"点击了" + position, Toast.LENGTH_SHORT).show();
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
            int gap = getResources().getDimensionPixelOffset(R.dimen.dividerHeight);
            outRect.set(gap,gap,gap,gap);
        }
    }
}
