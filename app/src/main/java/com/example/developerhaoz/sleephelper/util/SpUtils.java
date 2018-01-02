package com.example.developerhaoz.sleephelper.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.developerhaoz.sleephelper.R;
import com.example.developerhaoz.sleephelper.recyclerview.ThemeActivity;

/**
 * Created by lyy on 2017/10/24.
 */

public class SpUtils {

    private static final String spFileName = "app";

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
}
