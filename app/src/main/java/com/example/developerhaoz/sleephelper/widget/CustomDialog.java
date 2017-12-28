package com.example.developerhaoz.sleephelper.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.developerhaoz.sleephelper.R;

/**
 * Created by lizhonglian on 2017/12/18.
 */

public class CustomDialog extends Dialog implements View.OnClickListener{


    private TextView tvTitle,tvContent,tvCancel,tvInsist;

    private String title,message,cancel,insist;

    private IOnCancelClickListener mCancelListener;
    private IOnInsistClickListener mInsistListener;

    public String getTitle() {
        return title;
    }

    public CustomDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public CustomDialog setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getCancel() {
        return cancel;
    }

    public CustomDialog setCancel(String cancel,IOnCancelClickListener listener) {
        this.cancel = cancel;
        this.mCancelListener = listener;
        return this;
    }

    public String getInsist() {
        return insist;
    }

    public CustomDialog setInsist(String insist,IOnInsistClickListener listener) {
        this.insist = insist;
        this.mInsistListener = listener;
        return this;
    }

    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    public CustomDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected CustomDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_custom_dialog);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvContent = (TextView) findViewById(R.id.tv_content);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvInsist = (TextView) findViewById(R.id.tv_insist);

        if (!TextUtils.isEmpty(title)){
            tvTitle.setText(title);
        }
        if (!TextUtils.isEmpty(message)){
            tvContent.setText(message);
        }
        if (!TextUtils.isEmpty(cancel)){
            tvCancel.setText(cancel);
        }
        if (!TextUtils.isEmpty(insist)){
            tvInsist.setText(insist);
        }

        tvCancel.setOnClickListener(this);
        tvInsist.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_cancel:
                if (null != mCancelListener){
                    mCancelListener.onCancel(this);
                }
                break;
            case R.id.tv_insist:
                if (null != mInsistListener){
                    mInsistListener.onInsist(this);
                }
                break;
        }
    }

    public interface IOnCancelClickListener{
        void onCancel(CustomDialog dialog);
    }

    public interface IOnInsistClickListener{
        void onInsist(CustomDialog dialog);
    }
}
