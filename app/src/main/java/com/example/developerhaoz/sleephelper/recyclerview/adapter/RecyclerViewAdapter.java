package com.example.developerhaoz.sleephelper.recyclerview.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.developerhaoz.sleephelper.R;
import com.example.developerhaoz.sleephelper.database.DBManager;
import com.example.developerhaoz.sleephelper.recyclerview.PlayMusicService;
import com.example.developerhaoz.sleephelper.recyclerview.entity.MusicInfo;
import com.example.developerhaoz.sleephelper.util.AppConstants;
import com.example.developerhaoz.sleephelper.util.SpUtils;

import java.util.List;

/**
 * Created by lizhonglian on 2018/1/12.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements SectionIndexer {

    private static final String TAG = RecyclerViewAdapter.class.getName();

    private List<MusicInfo> musicInfoList;
    private Context context;
    private DBManager dbManager;
    private OnItemClickListener onItemClickListener ;


    public RecyclerViewAdapter(Context context, List<MusicInfo> musicInfoList) {
        this.context = context;
        this.musicInfoList = musicInfoList;
        this.dbManager = DBManager.getInstance(context);
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getItemCount(); i++) {
            char firstChar = musicInfoList.get(i).getFirstLetter().charAt(0);
            if (firstChar == sectionIndex) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return musicInfoList.get(position).getFirstLetter().charAt(0);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        View swipeContent;
        LinearLayout contentLl;
        TextView musicIndex;
        TextView musicName;
        TextView musicSinger;
        TextView letterIndex;
        ImageView menuIv;
        Button deleteBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            this.swipeContent = (View) itemView.findViewById(R.id.swipemenu_layout);
            this.contentLl = (LinearLayout) itemView.findViewById(R.id.local_music_item_ll);
            this.musicName = (TextView) itemView.findViewById(R.id.local_music_name);
            this.musicIndex = (TextView) itemView.findViewById(R.id.local_index);
            this.musicSinger = (TextView) itemView.findViewById(R.id.local_music_singer);
            this.letterIndex = (TextView) itemView.findViewById(R.id.indext_head_tv);
            this.menuIv = (ImageView) itemView.findViewById(R.id.local_music_item_never_menu);
            this.deleteBtn = (Button) itemView.findViewById(R.id.swip_delete_menu_btn);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.local_music_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
        Log.d(TAG, "onBindViewHolder: position = " + position);

        final MusicInfo musicInfo = musicInfoList.get(position);
        holder.musicName.setText(musicInfo.getName());
        holder.musicIndex.setText("" + (position + 1));
        holder.musicSinger.setText(musicInfo.getSinger());

        //
        int appbg = SpUtils.getAttrColorValue(R.attr.colorAccent,0xFFFA7298,context);
        int defaultTvColor = SpUtils.getAttrColorValue(R.attr.text_color,R.color.grey700,context);

        if (musicInfo.getId() == SpUtils.getIntShared(AppConstants.KEY_ID)){
            holder.musicName.setTextColor(appbg);
            holder.musicIndex.setTextColor(appbg);
            holder.musicSinger.setTextColor(appbg);
        }else {
            holder.musicName.setTextColor(defaultTvColor);
            holder.musicIndex.setTextColor(context.getResources().getColor(R.color.grey700));
            holder.musicSinger.setTextColor(context.getResources().getColor(R.color.grey700));
        }

        int section = getSectionForPosition(position);
        int firstPosition = getPositionForSection(section);

        if (firstPosition == position){
            holder.letterIndex.setVisibility(View.VISIBLE);
            holder.letterIndex.setText("" + musicInfo.getFirstLetter());
        }else{

            holder.letterIndex.setVisibility(View.GONE);
        }

        holder.contentLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: 播放 " + musicInfo.getName());

                String path = dbManager.getMusicPath(musicInfo.getId());
                Intent intent = new Intent(PlayMusicService.PLAYER_MANAGER_ACTION);
                intent.putExtra(AppConstants.COMMAND, AppConstants.COMMAND_PLAY);
                intent.putExtra(AppConstants.KEY_PATH, path);

                context.sendBroadcast(intent);

                SpUtils.setShared(AppConstants.KEY_ID,musicInfo.getId());
                notifyDataSetChanged();
                if (onItemClickListener != null)
                    onItemClickListener.onContentClick(position);
            }
        });

        holder.menuIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null)
                    onItemClickListener.onOpenMenuClick(position);
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null)
                    onItemClickListener.onDeleteMenuClick(holder.swipeContent,holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
//        Log.d(TAG,"musicInfoList.size():" + musicInfoList.size());

        return musicInfoList.size();
    }

    public void updateMusicInfoList(List<MusicInfo> musicInfoList) {
        this.musicInfoList.clear();
        this.musicInfoList.addAll(musicInfoList);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onOpenMenuClick(int position);
        void onDeleteMenuClick(View content,int position);
        void onContentClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener ;
    }
}
