package com.example.developerhaoz.sleephelper.recyclerview.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.developerhaoz.sleephelper.R;
import com.example.developerhaoz.sleephelper.database.DBManager;
import com.example.developerhaoz.sleephelper.recyclerview.ThemeActivity;
import com.example.developerhaoz.sleephelper.util.AppConstants;
import com.example.developerhaoz.sleephelper.util.SpUtils;

/**
 * Created by lizhonglian on 2018/1/3.
 */

public class PlaybarFragment extends Fragment {

    private static final String TAG = "PlayBarFragment";
    public static final String ACTION_UPDATE_UI_PlayBar = "com.example.developerhaoz.sleephelper.recyclerview.fragment.PlayBarFragment:action_update_ui_broad_cast";


    //TODO:
    public static int STATUS = AppConstants.STATUS_STOP;
    public static int STATUS_FROM_SERVICE = AppConstants.STATUS_STOP;


    private LinearLayout playBarLl;
    private ImageView playIv;
    private ImageView nextIv;
    private ImageView menuIv;
    private ImageView albmIv;
    private TextView musicNameTv;
    private TextView singerNameTv;
    private TextView defaultNameTv;
    private HomeReceiver mReceiver;
    private SeekBar seekBar;
    private DBManager dbManager;
    private Context context;

    public static synchronized PlaybarFragment newInstance() {
        return new PlaybarFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = DBManager.getInstance(getActivity());
        register();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_playbar, container, false);

        playBarLl = (LinearLayout) view.findViewById(R.id.home_activity_playbar_ll);
        seekBar = (SeekBar) view.findViewById(R.id.home_seekbar);
        playIv = (ImageView) view.findViewById(R.id.play_iv);
        /*menuIv = (ImageView) view.findViewById(R.id.play_menu_iv);
        nextIv = (ImageView) view.findViewById(R.id.next_iv);*/
        albmIv = (ImageView) view.findViewById(R.id.album_picture_iv);
        musicNameTv = (TextView) view.findViewById(R.id.home_music_name_tv);
        singerNameTv = (TextView) view.findViewById(R.id.home_singer_name_tv);

        setMusicName();
//        initPlayIv();
        setFragmentBb();

        initAction();

        return view;
    }


    private void initAction() {

        playBarLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO
                Intent intent = new Intent(getActivity(), ThemeActivity.class);
                startActivity(intent);
            }
        });

       /* playIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int musicId = SpUtils.getIntShared(AppConstants.KEY_ID);
                if (musicId == -1 || musicId == 0) {
                    Intent intent = new Intent(AppConstants.MP_FILTER);
                    intent.putExtra(Constant.COMMAND, AppConstants.COMMAND_STOP);
                    getActivity().sendBroadcast(intent);
                    Toast.makeText(getActivity(), "歌曲不存在",Toast.LENGTH_SHORT).show();
                    return;
                }
                //如果当前媒体在播放音乐状态，则图片显示暂停图片，按下播放键，则发送暂停媒体命令，图片显示播放图片。以此类推。
                if (STATUS_FROM_SERVICE == AppConstants.STATUS_PAUSE) {
                    Intent intent = new Intent(MusicPlayerService.PLAYER_MANAGER_ACTION);
                    intent.putExtra(AppConstants.COMMAND,AppConstants.COMMAND_PLAY);
                    getActivity().sendBroadcast(intent);
                }else if (STATUS_FROM_SERVICE == AppConstants.STATUS_PLAY) {
                    Intent intent = new Intent(MusicPlayerService.PLAYER_MANAGER_ACTION);
                    intent.putExtra(AppConstants.COMMAND, AppConstants.COMMAND_PAUSE);
                    getActivity().sendBroadcast(intent);
                }else {
                    //为停止状态时发送播放命令，并发送将要播放歌曲的路径
                    String path = dbManager.getMusicPath(musicId);
                    Intent intent = new Intent(MusicPlayerService.PLAYER_MANAGER_ACTION);
                    intent.putExtra(AppConstants.COMMAND, AppConstants.COMMAND_PLAY);
                    intent.putExtra(AppConstants.KEY_PATH, path);
                    Log.i(TAG, "onClick: path = "+path);
                    getActivity().sendBroadcast(intent);
                }
            }
        });*/

        /*nextIv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SpUtils.playNextMusic(getActivity());
            }
        });

        menuIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopFormBottom();
            }
        });*/

    }

    public void showPopFormBottom() {
        /*PlayingPopWindow playingPopWindow = new PlayingPopWindow(getActivity());
        //设置Popupwindow显示位置（从底部弹出）
        playingPopWindow.showAtLocation(view, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        //当弹出Popupwindow时，背景变半透明
        params.alpha=0.7f;
        getActivity().getWindow().setAttributes(params);

        //设置Popupwindow关闭监听，当Popupwindow关闭，背景恢复1f
        playingPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
                params.alpha=1f;
                getActivity().getWindow().setAttributes(params);
            }
        });*/

    }

    private void register() {
        mReceiver = new HomeReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_UPDATE_UI_PlayBar);
        getActivity().registerReceiver(mReceiver, intentFilter);
    }

    private void unRegister() {
        if (mReceiver != null) {
            getActivity().unregisterReceiver(mReceiver);
        }
    }

    private void setMusicName() {
        int musicId = SpUtils.getIntShared(AppConstants.KEY_ID);
        if (musicId == -1) {
            musicNameTv.setText("听听音乐");
            singerNameTv.setText("好音质");
        } else {
            musicNameTv.setText(dbManager.getMusicInfo(musicId).get(1));
            singerNameTv.setText(dbManager.getMusicInfo(musicId).get(2));
        }
    }

    private void initPlayIv() {


        int status = STATUS;
        switch (status) {
            case AppConstants.STATUS_STOP:
                playIv.setSelected(false);
                initPlayIv();
                break;
            case AppConstants.STATUS_PLAY:
                playIv.setSelected(true);
                break;
            case AppConstants.STATUS_PAUSE:
                playIv.setSelected(false);
                break;
            case AppConstants.STATUS_RUN:
                playIv.setSelected(true);
                break;
        }
    }

    public void setFragmentBb() {

        //获取播放控制栏颜色
        int defaultColor = 0xFFFFFF;
        int[] attrsArray = {R.attr.play_bar_color};
        TypedArray typedArray = context.obtainStyledAttributes(attrsArray);
        int color = typedArray.getColor(0, defaultColor);
        typedArray.recycle();
        playBarLl.setBackgroundColor(color);
    }

    class HomeReceiver extends BroadcastReceiver {

        int status;
        int duration;
        int current;

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: ");
//            setMusicName();
            status = intent.getIntExtra(AppConstants.STATUS, 0);
            current = intent.getIntExtra(AppConstants.KEY_CURRENT, 0);
            duration = intent.getIntExtra(AppConstants.KEY_DURATION, 100);
            switch (status) {
                case AppConstants.STATUS_STOP:
                    playIv.setSelected(false);
//                    seekBar.setProgress(0);
                    break;
                case AppConstants.STATUS_PLAY:
                    playIv.setSelected(true);
                    break;
                case AppConstants.STATUS_PAUSE:
                    playIv.setSelected(false);
                    break;
                case AppConstants.STATUS_RUN:
                    playIv.setSelected(true);
                   /* seekBar.setMax(duration);
                    seekBar.setProgress(current);*/
                    break;
                default:
                    break;
            }

        }
    }
}
