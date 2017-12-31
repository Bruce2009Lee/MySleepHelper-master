package com.example.developerhaoz.sleephelper.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.developerhaoz.sleephelper.R;


/**
 * Created by lizhonglian on 2017/12/16.
 */

public class MixRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private LayoutInflater mInflater;

    public MixRecyclerViewAdapter(Context context, OnItemClickListener listener) {
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

    class ViewHolder2 extends RecyclerView.ViewHolder{
        // TODO: 声明组件
        ImageView imageView;

        public ViewHolder2(View view) {
            super(view);
            // TODO: 注册组件,view.findViewById(R.id.xxx)
            imageView = (ImageView) view.findViewById(R.id.iv_mix);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if ( position%2 != 0){
            return 0;
        }else {
            return 1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == 0){
            return new MixRecyclerViewAdapter.ViewHolder(mInflater.inflate(R.layout.layout_linear_recycler_view, parent, false));
        }else {
            return new MixRecyclerViewAdapter.ViewHolder2(mInflater.inflate(R.layout.layout_mix_recycler_view, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (getItemViewType(position)  == 0){
            ((ViewHolder)holder).textView.setText("China");
            ((ViewHolder)holder).textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(position);
                }
            });
        }else {
            ((ViewHolder2)holder).imageView.setImageResource(R.drawable.yang4);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(position);
            }
        });
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
