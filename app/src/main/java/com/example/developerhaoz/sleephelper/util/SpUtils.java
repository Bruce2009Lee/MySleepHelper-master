package com.example.developerhaoz.sleephelper.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.developerhaoz.sleephelper.R;
import com.example.developerhaoz.sleephelper.database.DBManager;
import com.example.developerhaoz.sleephelper.recyclerview.ThemeActivity;
import com.example.developerhaoz.sleephelper.recyclerview.entity.MusicInfo;

import java.util.ArrayList;
import java.util.List;

import static com.example.developerhaoz.sleephelper.common.SleepApplication.getContext;

/**
 * Created by lyy on 2017/10/24.
 */

public class SpUtils {

    private static final String spFileName = "app";

    private static final String TAG = SpUtils.class.getName();

    //设置主题
    public static void setTheme(Context context, int position) {

        int preSelect = getTheme(context);
        SharedPreferences sharedPreferences = context.getSharedPreferences("theme", Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt("theme_select", position).commit();
        Toast.makeText(context,"save " + position,Toast.LENGTH_SHORT).show();
        if (preSelect != 8) {
            sharedPreferences.edit().putInt("pre_theme_select", preSelect).commit();
        }
    }

    //得到主题
    public static int getTheme(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("theme", Context.MODE_PRIVATE);
        Toast.makeText(context,"read " + sharedPreferences.getInt("theme_select", 0),Toast.LENGTH_SHORT).show();
        return sharedPreferences.getInt("theme_select", 0);

    }

    //得到主题
    public static int getMyThemeStyle(Context context) {
        int themeId = SpUtils.getTheme(context);
        switch (themeId){
            default:
            case 0:
                return R.style.BiLiPinkTheme;
            case 1:
                return R.style.ZhiHuBlueTheme;
            case 2:
                return R.style.KuAnGreenTheme;
            case 3:
                return R.style.CloudRedTheme;
            case 4:
                return R.style.TengLuoPurpleTheme;
            case 5:
                return R.style.SeaBlueTheme;
            case 6:
                return R.style.GrassGreenTheme;
            case 7:
                return R.style.CoffeeBrownTheme;
            case 8:
                return R.style.LemonOrangeTheme;
        }
    }

    //获取当前播放列表
    public static List<MusicInfo> getCurPlayList(Context context){

        DBManager dbManager = DBManager.getInstance(context);
        int playList = SpUtils.getIntShared(AppConstants.KEY_LIST);
        List<MusicInfo> musicInfoList = new ArrayList<>();

        switch (playList){
            case AppConstants.LIST_ALLMUSIC:
                musicInfoList = dbManager.getAllMusicFromMusicTable();
                break;
            case AppConstants.LIST_MYLOVE:
                musicInfoList = dbManager.getAllMusicFromTable(AppConstants.LIST_MYLOVE);
                break;
            case AppConstants.LIST_LASTPLAY:
                musicInfoList = dbManager.getAllMusicFromTable(AppConstants.LIST_LASTPLAY);
                break;
            case AppConstants.LIST_PLAYLIST:
                int listId = SpUtils.getIntShared(AppConstants.KEY_LIST_ID);
                musicInfoList = dbManager.getMusicListByPlaylist(listId);
                break;
            case AppConstants.LIST_SINGER:
                String singerName = SpUtils.getStringShared(AppConstants.KEY_LIST_ID);
                if (singerName == null){
                    musicInfoList = dbManager.getAllMusicFromMusicTable();
                }else {
                    musicInfoList = dbManager.getMusicListBySinger(singerName);
                }
                break;
            case AppConstants.LIST_ALBUM:
                String albumName = SpUtils.getStringShared(AppConstants.KEY_LIST_ID);
                if (albumName == null){
                    musicInfoList = dbManager.getAllMusicFromMusicTable();
                }else {
                    musicInfoList = dbManager.getMusicListByAlbum(albumName);
                }
                break;
            case AppConstants.LIST_FOLDER:
                String folderName = SpUtils.getStringShared(AppConstants.KEY_LIST_ID);
                if (folderName == null){
                    musicInfoList = dbManager.getAllMusicFromMusicTable();
                }else {
                    musicInfoList = dbManager.getMusicListByFolder(folderName);
                }
                break;
        }
        return musicInfoList;
    }

    public static void playNextMusic(Context context){

        //获取下一首ID
        DBManager dbManager = DBManager.getInstance(context);
        int playMode = SpUtils.getIntShared(AppConstants.KEY_MODE);
        int musicId = SpUtils.getIntShared(AppConstants.KEY_ID);
        List<MusicInfo> musicList = getCurPlayList(context);
        ArrayList<Integer> musicIdList =new ArrayList<>();
        for (MusicInfo info : musicList){
            musicIdList.add(info.getId());
        }
        musicId = dbManager.getNextMusic(musicIdList,musicId,playMode);
        SpUtils.setShared(AppConstants.KEY_ID,musicId);
        if (musicId == -1) {

            //TODO
            /*Intent intent = new Intent(MusicPlayerService.PLAYER_MANAGER_ACTION);
            intent.putExtra(AppConstants.COMMAND, AppConstants.COMMAND_STOP);
            context.sendBroadcast(intent);*/
            Toast.makeText(context, "歌曲不存在",Toast.LENGTH_LONG).show();
            return;
        }

        //获取播放歌曲路径
        String path = dbManager.getMusicPath(musicId);
        Log.d(TAG,"next path ="+path);

        /*//发送播放请求
        Log.d(TAG,"next  id = "+musicId+"path = "+ path);
        Intent intent = new Intent(MusicPlayerService.PLAYER_MANAGER_ACTION);
        intent.putExtra(AppConstants.COMMAND, AppConstants.COMMAND_PLAY);
        intent.putExtra(AppConstants.KEY_PATH, path);
        context.sendBroadcast(intent);*/
    }

    // 获取下一首歌曲(id)
    public int getNextMusic(ArrayList<Integer> musicList, int id, int playMode) {
        if (id == -1) {
            return -1;
        }
        //找到当前id在列表的第几个位置（i+1）
        int index = musicList.indexOf(id);
        if (index == -1) {
            return -1;
        }
        // 如果当前是最后一首
        switch (playMode) {
            case AppConstants.PLAYMODE_SEQUENCE:
                if ((index + 1) == musicList.size()) {
                    id = musicList.get(0);
                } else {
                    ++index;
                    id = musicList.get(index);
                }
                break;
            case AppConstants.PLAYMODE_SINGLE_REPEAT:
                break;
            case AppConstants.PLAYMODE_RANDOM:
//                id = getRandomMusic(musicList, id);
                break;
        }
        return id;
    }

    public static String getString(Context context, String strKey) {
        SharedPreferences setPreferences = context.getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        String result = setPreferences.getString(strKey, "");
        return result;
    }

    public static String getString(Context context, String strKey, String strDefault) {
        SharedPreferences setPreferences = context.getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        String result = setPreferences.getString(strKey, strDefault);
        return result;
    }

    public static void putString(Context context, String strKey, String strData) {
        SharedPreferences activityPreferences = context.getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putString(strKey, strData);
        editor.commit();
    }

    // 设置sharedPreferences
    public static void setShared(String key,int value){
        SharedPreferences pref = getContext().getSharedPreferences("music",getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void setShared(String key,String value){
        SharedPreferences pref = getContext().getSharedPreferences("music",getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static Boolean getBoolean(Context context, String strKey) {
        SharedPreferences setPreferences = context.getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        Boolean result = setPreferences.getBoolean(strKey, false);
        return result;
    }

    public static Boolean getBoolean(Context context, String strKey, Boolean strDefault) {
        SharedPreferences setPreferences = context.getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        Boolean result = setPreferences.getBoolean(strKey, strDefault);
        return result;
    }


    public static void putBoolean(Context context, String strKey, Boolean strData) {
        SharedPreferences activityPreferences = context.getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putBoolean(strKey, strData);
        editor.commit();
    }

    public static int getInt(Context context, String strKey) {
        SharedPreferences setPreferences = context.getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        int result = setPreferences.getInt(strKey, -1);
        return result;
    }

    public static int getInt(Context context, String strKey, int strDefault) {
        SharedPreferences setPreferences = context.getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        int result = setPreferences.getInt(strKey, strDefault);
        return result;
    }

    public static void putInt(Context context, String strKey, int strData) {
        SharedPreferences activityPreferences = context.getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putInt(strKey, strData);
        editor.commit();
    }

    public static long getLong(Context context, String strKey) {
        SharedPreferences setPreferences = context.getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        long result = setPreferences.getLong(strKey, -1);
        return result;
    }

    public static long getLong(Context context, String strKey, long strDefault) {
        SharedPreferences setPreferences = context.getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        long result = setPreferences.getLong(strKey, strDefault);
        return result;
    }

    public static void putLong(Context context, String strKey, long strData) {
        SharedPreferences activityPreferences = context.getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putLong(strKey, strData);
        editor.commit();
    }

    public static String getStringShared(String key) {
        SharedPreferences pref = getContext().getSharedPreferences("music", getContext().MODE_PRIVATE);
        String value;
        value = pref.getString(key,null);
        return value;
    }

    // 获取sharedPreferences
    public static int getIntShared(String key) {
        SharedPreferences pref = getContext().getSharedPreferences("music", getContext().MODE_PRIVATE);
        int value;
        if (key.equals(AppConstants.KEY_CURRENT)){
            value = pref.getInt(key, 0);
        }else{
            value = pref.getInt(key, -1);
        }
        return value;
    }
}
