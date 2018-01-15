package com.example.developerhaoz.sleephelper.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.developerhaoz.sleephelper.R;
import com.example.developerhaoz.sleephelper.recyclerview.entity.MusicInfo;
import com.example.developerhaoz.sleephelper.recyclerview.entity.PlayListInfo;
import com.example.developerhaoz.sleephelper.util.AppConstants;

/**
 * Created by lizhonglian on 2018/1/12.
 */

public class MusicPopMenuWindow extends PopupWindow {

    private static final String TAG = MusicPopMenuWindow.class.getName();
    private View view;
    private Activity activity;
    private TextView nameTv;

    private LinearLayout addLl;
    private LinearLayout loveLl;

    private LinearLayout deleteLl;
    private LinearLayout cancelLl;
    private MusicInfo musicInfo;
    private PlayListInfo playListInfo;
    private int witchActivity = AppConstants.ACTIVITY_LOCAL;
    private View parentView;

    public MusicPopMenuWindow(Activity activity, MusicInfo musicInfo,View parentView,int witchActivity) {
        super(activity);
        this.activity = activity;
        this.musicInfo = musicInfo;
        this.parentView = parentView;
        this.witchActivity = witchActivity;
        initView();
    }

    private void initView(){

        this.view = LayoutInflater.from(activity).inflate(R.layout.pop_window_menu, null);
        this.setContentView(this.view);

        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        this.setFocusable(true);
        // 设置外部可点击
        this.setOutsideTouchable(true);

        // 设置弹出窗体的背景
        this.setBackgroundDrawable(activity.getResources().getDrawable(R.color.colorWhite));

        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.pop_window_animation);

        // 添加OnTouchListener监听判断获取触屏位置，如果在选择框外面则销毁弹出框
        this.view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                int height = view.getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

        nameTv = (TextView)view.findViewById(R.id.popwin_name_tv);

        addLl = (LinearLayout) view.findViewById(R.id.popwin_add_rl);
        loveLl = (LinearLayout) view.findViewById(R.id.popwin_love_ll);
        deleteLl = (LinearLayout) view.findViewById(R.id.popwin_delete_ll);
        cancelLl = (LinearLayout) view.findViewById(R.id.popwin_cancel_ll);

        nameTv.setText("歌曲： " + musicInfo.getName());

        addLl.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                dismiss();

                Toast.makeText(activity,"popwindow点击了 add",Toast.LENGTH_SHORT).show();

                /*AddPlaylistWindow addPlaylistWindow = new AddPlaylistWindow(activity,musicInfo);
                addPlaylistWindow.showAtLocation(parentView, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
                WindowManager.LayoutParams params = activity.getWindow().getAttributes();
                //当弹出Popupwindow时，背景变半透明
                params.alpha=0.7f;
                activity.getWindow().setAttributes(params);
                //设置Popupwindow关闭监听，当Popupwindow关闭，背景恢复1f
                addPlaylistWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
                        params.alpha=1f;
                        activity.getWindow().setAttributes(params);
                    }
                });*/
            }
        });

        loveLl.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Toast.makeText(activity,"popwindow点击了 love",Toast.LENGTH_SHORT).show();
                /*
                MyMusicUtil.setMusicMylove(activity,musicInfo.getId());
                dismiss();
                View view = LayoutInflater.from(activity).inflate(R.layout.my_love_toast,null);
                Toast toast = new Toast(activity);
                toast.setView(view);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();*/
                dismiss();
            }
        });

        deleteLl.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Toast.makeText(activity,"popwindow点击了 delete",Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        cancelLl.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private OnDeleteUpdateListener onDeleteUpdateListener;

    public void setOnDeleteUpdateListener(OnDeleteUpdateListener onDeleteUpdateListener){
        this.onDeleteUpdateListener = onDeleteUpdateListener;
    }

    public interface OnDeleteUpdateListener {
        void onDeleteUpdate();
    }


}
