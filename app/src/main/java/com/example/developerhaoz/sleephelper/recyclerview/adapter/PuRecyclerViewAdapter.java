package com.example.developerhaoz.sleephelper.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.developerhaoz.sleephelper.R;


/**
 * Created by lizhonglian on 2017/12/16.
 */

public class PuRecyclerViewAdapter extends RecyclerView.Adapter<PuRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private LayoutInflater mInflater;

    public PuRecyclerViewAdapter(Context context, OnItemClickListener listener) {
        super();
        this.context = context;
        mOnItemClickListener = listener;
        mInflater = LayoutInflater.from(context);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        // TODO: 声明组件
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            // TODO: 注册组件,view.findViewById(R.id.xxx)
            imageView = (ImageView) view.findViewById(R.id.iv_pu);
        }

    }


    @Override
    public PuRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new PuRecyclerViewAdapter.ViewHolder(mInflater.inflate(R.layout.layout_pu_recycler_view, parent, false));
    }

    @Override
    public void onBindViewHolder(PuRecyclerViewAdapter.ViewHolder holder, final int position) {

        if (position%2 != 0){
            holder.imageView.setImageResource(R.drawable.yang3);
        }else {
            holder.imageView.setImageResource(R.drawable.yang4);
        }

        if (null != mOnItemClickListener ){
            holder.imageView.setOnClickListener(new View.OnClickListener() {
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
