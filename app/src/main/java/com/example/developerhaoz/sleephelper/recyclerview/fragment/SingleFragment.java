package com.example.developerhaoz.sleephelper.recyclerview.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.developerhaoz.sleephelper.R;
import com.example.developerhaoz.sleephelper.database.DBManager;
import com.example.developerhaoz.sleephelper.recyclerview.MusicPopMenuWindow;
import com.example.developerhaoz.sleephelper.recyclerview.adapter.RecyclerViewAdapter;
import com.example.developerhaoz.sleephelper.recyclerview.entity.MusicInfo;
import com.example.developerhaoz.sleephelper.util.AppConstants;
import com.example.developerhaoz.sleephelper.util.SideBar;
import com.example.developerhaoz.sleephelper.util.SpUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lizhonglian on 2018/1/3.
 */

public class SingleFragment extends Fragment {

    private static final String TAG = "SingleFragment";

    private RelativeLayout playModeRl;
    private ImageView playModeIv;
    private TextView playModeTv;
    private RecyclerView recyclerView;
    public  RecyclerViewAdapter recyclerViewAdapter;
    private SideBar sideBar;
    private TextView sideBarPreTv;

    private List<MusicInfo> musicInfoList = new ArrayList<>();
    private DBManager dbManager;
    private View view;
    private Context context;
    private UpdateReceiver mReceiver;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        dbManager = DBManager.getInstance(this.context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_single,container,false);

        Collections.sort(musicInfoList);

        recyclerView = (RecyclerView) view.findViewById(R.id.local_recycler_view);
        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(),musicInfoList);

        playModeRl = (RelativeLayout) view.findViewById(R.id.local_music_playmode_rl);
        playModeIv = (ImageView) view.findViewById(R.id.local_music_playmode_iv);
        playModeTv = (TextView) view.findViewById(R.id.local_music_playmode_tv);

        sideBarPreTv = (TextView) view.findViewById(R.id.local_music_siderbar_pre_tv);
        sideBar = (SideBar)view.findViewById(R.id.local_music_siderbar);
        sideBar.setTextView(sideBarPreTv);
        sideBar.setOnListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String letter) {
                //该字母首次出现的位置
                int position = recyclerViewAdapter.getPositionForSection(letter.charAt(0));
                if(position != -1){
                    recyclerView.smoothScrollToPosition(position);
                }
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onOpenMenuClick(int position) {
                MusicInfo musicInfo = musicInfoList.get(position);
                showPopFormBottom(musicInfo);
                Toast.makeText(getActivity(),"打开了menu",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteMenuClick(View content, int position) {
                MusicInfo musicInfo = musicInfoList.get(position);
//                deleteOperate(swipeView,position,context);
                Toast.makeText(getActivity(),"点击了删除",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onContentClick(int position) {
                SpUtils.setShared(AppConstants.KEY_LIST,AppConstants.LIST_ALLMUSIC);
                Toast.makeText(getActivity(),"点击了menu",Toast.LENGTH_SHORT).show();

            }
        });

        return view;


    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: " );
        updateView();
    }

    public void updateView(){
        musicInfoList = dbManager.getAllMusicFromMusicTable();
        Collections.sort(musicInfoList);
        recyclerViewAdapter.updateMusicInfoList(musicInfoList);
        Log.d(TAG, "updateView: musicInfoList.size() = " + musicInfoList.size());
        if (musicInfoList.size() == 0){
//            sideBar.setVisibility(View.GONE);
            playModeRl.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
        }else {
//            sideBar.setVisibility(View.VISIBLE);
            playModeRl.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
//        initDefaultPlayModeView();
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    private class UpdateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
//            recyclerViewAdapter.notifyDataSetChanged();
        }
    }

    public void showPopFormBottom(MusicInfo musicInfo) {
        MusicPopMenuWindow menuPopupWindow = new MusicPopMenuWindow(getActivity(),musicInfo,view,AppConstants.ACTIVITY_LOCAL);

        //设置Popupwindow显示位置（从底部弹出）
        menuPopupWindow.showAtLocation(view, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();

        //当弹出Popupwindow时，背景变半透明
        params.alpha=0.7f;
        getActivity().getWindow().setAttributes(params);

        //设置Popupwindow关闭监听，当Popupwindow关闭，背景恢复1f
        menuPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
                params.alpha=1f;
                getActivity().getWindow().setAttributes(params);
            }
        });
        menuPopupWindow.setOnDeleteUpdateListener(new MusicPopMenuWindow.OnDeleteUpdateListener() {
            @Override
            public void onDeleteUpdate() {
//                updateView();
            }
        });

    }
}
