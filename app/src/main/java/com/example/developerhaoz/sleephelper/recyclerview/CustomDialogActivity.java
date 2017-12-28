package com.example.developerhaoz.sleephelper.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.developerhaoz.sleephelper.R;
import com.example.developerhaoz.sleephelper.widget.CustomDialog;

public class CustomDialogActivity extends AppCompatActivity {

    private Button mBtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_dialog);

        mBtn = (Button) findViewById(R.id.btn_cdialog);

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog customDialog = new CustomDialog(CustomDialogActivity.this);
                customDialog.setTitle("亲情提示")
                        .setMessage("确定退出？")
                        .setCancel("取消",new CustomDialog.IOnCancelClickListener() {
                            @Override
                            public void onCancel(CustomDialog dialog) {
                                Toast.makeText(CustomDialogActivity.this,"点击了取消",Toast.LENGTH_SHORT).show();
                            }
                        }).setInsist("确定",new CustomDialog.IOnInsistClickListener() {
                    @Override
                    public void onInsist(CustomDialog dialog) {
                        Toast.makeText(CustomDialogActivity.this,"点击了确定",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }).show();
                customDialog.setCancelable(false);
            }
        });
    }
}
