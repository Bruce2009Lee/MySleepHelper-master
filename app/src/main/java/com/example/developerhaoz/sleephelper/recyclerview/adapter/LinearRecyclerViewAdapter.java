package com.example.developerhaoz.sleephelper.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.developerhaoz.sleephelper.R;


/**
 * Created by lizhonglian on 2017/12/16.
 */

public class LinearRecyclerViewAdapter extends RecyclerView.Adapter<LinearRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private LayoutInflater mInflater;

    public LinearRecyclerViewAdapter(Context context, OnItemClickListener listener) {
        super();
        this.context = context;
        mOnItemClickListener = listener;
        mInflater = LayoutInflater.from(context);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        // TODO: 声明组件
        TextView textView;

        public ViewHolder(View view) {
            super(view);
            // TODO: 注册组件,view.findViewById(R.id.xxx)
            textView = (TextView) view.findViewById(R.id.tv_linear);
        }

    }


    @Override
    public LinearRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new LinearRecyclerViewAdapter.ViewHolder(mInflater.inflate(R.layout.layout_linear_recycler_view, parent, false));
    }

    @Override
    public void onBindViewHolder(LinearRecyclerViewAdapter.ViewHolder holder, final int position) {

        holder.textView.setText("China");
        if (null != mOnItemClickListener ){
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 30;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onItemLongClick(int position);
    }
}
