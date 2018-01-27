package com.example.developerhaoz.sleephelper.recyclerview.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.developerhaoz.sleephelper.R;
import com.example.developerhaoz.sleephelper.database.DBManager;
import com.example.developerhaoz.sleephelper.recyclerview.PlayMusicService;
import com.example.developerhaoz.sleephelper.recyclerview.entity.MusicInfo;
import com.example.developerhaoz.sleephelper.recyclerview.entity.PlayListInfo;
import com.example.developerhaoz.sleephelper.util.AppConstants;
import com.example.developerhaoz.sleephelper.util.SpUtils;

import java.util.List;

/**
 * Created by lizhonglian on 2018/1/21.
 */

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {

    private static final String TAG = "PlaylistAdapter";

    private List<MusicInfo> musicInfoList;
    private Context context;
    private DBManager dbManager;
    private PlayListInfo playListInfo;
    private PlaylistAdapter.OnItemClickListener onItemClickListener ;

    public PlaylistAdapter(Context context, PlayListInfo playListInfo,List<MusicInfo> musicInfoList) {
        this.context = context;
        this.playListInfo = playListInfo;
        this.dbManager = DBManager.getInstance(context);
        this.musicInfoList = musicInfoList;
    }

    static class ViewHolder extends RecyclerViewAdapter.ViewHolder{

        View swipeContent;
        LinearLayout contentLl;
        TextView musicIndex;
        TextView musicName;
        TextView musicSinger;
        ImageView menuIv;
        Button deleteBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            this.swipeContent = (View) itemView.findViewById(R.id.swipemenu_layout);
            this.contentLl = (LinearLayout) itemView.findViewById(R.id.local_music_item_ll);
            this.musicName = (TextView) itemView.findViewById(R.id.local_music_name);
            this.musicIndex = (TextView) itemView.findViewById(R.id.local_index);
            this.musicSinger = (TextView) itemView.findViewById(R.id.local_music_singer);
            this.menuIv = (ImageView) itemView.findViewById(R.id.local_music_item_never_menu);
            this.deleteBtn = (Button) itemView.findViewById(R.id.swip_delete_menu_btn);
        }
    }

    @Override
    public PlaylistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.local_music_item,parent,false);
        PlaylistAdapter.ViewHolder viewHolder = new PlaylistAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final PlaylistAdapter.ViewHolder holder,final int position) {

        final MusicInfo musicInfo = musicInfoList.get(position);

        holder.musicName.setText(musicInfo.getName());
        holder.musicIndex.setText("" + (position + 1));
        holder.musicSinger.setText(musicInfo.getSinger());

        if (musicInfo.getId() == SpUtils.getIntShared(AppConstants.KEY_ID)){
            holder.musicName.setTextColor(context.getResources().getColor(R.color.colorAccent));
            holder.musicIndex.setTextColor(context.getResources().getColor(R.color.colorAccent));
            holder.musicSinger.setTextColor(context.getResources().getColor(R.color.colorAccent));
        }else {
            holder.musicName.setTextColor(context.getResources().getColor(R.color.grey700));
            holder.musicIndex.setTextColor(context.getResources().getColor(R.color.grey700));
            holder.musicSinger.setTextColor(context.getResources().getColor(R.color.grey700));
        }

        holder.contentLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: 播放 "+musicInfo.getName());

                String path = dbManager.getMusicPath(musicInfo.getId());
                Intent intent = new Intent(PlayMusicService.PLAYER_MANAGER_ACTION);
                intent.putExtra(AppConstants.COMMAND, AppConstants.COMMAND_PLAY);
                intent.putExtra(AppConstants.KEY_PATH, path);
                context.sendBroadcast(intent);
                SpUtils.setShared(AppConstants.KEY_ID,musicInfo.getId());
                SpUtils.setShared(AppConstants.KEY_LIST,AppConstants.LIST_PLAYLIST);
                SpUtils.setShared(AppConstants.KEY_LIST_ID,playListInfo.getId());
                notifyDataSetChanged();
            }
        });

        holder.menuIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onOpenMenuClick(position);
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onDeleteMenuClick(holder.swipeContent,holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
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
    }

    public void setOnItemClickListener(PlaylistAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener ;
    }
}
