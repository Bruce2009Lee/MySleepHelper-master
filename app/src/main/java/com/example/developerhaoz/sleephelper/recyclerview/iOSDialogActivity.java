package com.example.developerhaoz.sleephelper.recyclerview;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.developerhaoz.sleephelper.R;
import com.hss01248.lib.MyDialogListener;
import com.hss01248.lib.StytledDialog;

public class iOSDialogActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_dial1,btn_dial2,btn_dial3,btn_dial4,btn_dial5,btn_dial6;

    Dialog gloablDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);

        initView(iOSDialogActivity.this);
    }

    public void initView(Context context){

        btn_dial1 = (Button) findViewById(R.id.btn_activity);
        btn_dial2 = (Button) findViewById(R.id.btn_dial2);
        btn_dial3 = (Button) findViewById(R.id.btn_dial3);
        btn_dial4 = (Button) findViewById(R.id.btn_dial4);
        btn_dial5 = (Button) findViewById(R.id.btn_dial5);
        btn_dial6 = (Button) findViewById(R.id.btn_dial6);

        btn_dial1.setOnClickListener(this);
        btn_dial2.setOnClickListener(this);
        btn_dial3.setOnClickListener(this);
        btn_dial4.setOnClickListener(this);
        btn_dial5.setOnClickListener(this);
        btn_dial6.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String msg = "this is message";
        switch (v.getId()){
            case  R.id.btn_activity:
                StytledDialog.showProgressDialog(iOSDialogActivity.this,msg,true,true);
                break;
            case  R.id.btn_dial2:
                gloablDialog = StytledDialog.showProgressDialog(getApplicationContext(),msg,true,true);
                break;
            case  R.id.btn_dial3:
                StytledDialog.showMdAlert(this, "title", msg, "sure", "cancle", "think about", true, 	true, new MyDialogListener() {
                    @Override
                    public void onFirst(DialogInterface dialog) {
                        Toast.makeText(iOSDialogActivity.this,"onFirst",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSecond(DialogInterface dialog) {
                        Toast.makeText(iOSDialogActivity.this,"onSecond",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onThird(DialogInterface dialog) {
                        Toast.makeText(iOSDialogActivity.this,"onThird",Toast.LENGTH_SHORT).show();
                    }


                });
                break;
            case  R.id.btn_dial4:
                StytledDialog.showIosAlert(this, "title", msg, "sure", "cancle", "think about", true, true, new MyDialogListener() {
                    @Override
                    public void onFirst(DialogInterface dialog) {
                        Toast.makeText(iOSDialogActivity.this,"onFirst",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSecond(DialogInterface dialog) {
                        Toast.makeText(iOSDialogActivity.this,"onSecond",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onThird(DialogInterface dialog) {
                        Toast.makeText(iOSDialogActivity.this,"onThird",Toast.LENGTH_SHORT).show();
                    }


                });
                break;
            case  R.id.btn_dial5:
                StytledDialog.showProgressDialog(getApplicationContext(),msg,true,true);
                break;
            case  R.id.btn_dial6:
                StytledDialog.showProgressDialog(getApplicationContext(),msg,true,true);
                break;
        }
    }

    @Override
    public void onBackPressed() {

        if (null != gloablDialog && gloablDialog.isShowing()){
            gloablDialog.dismiss();
        }else {
            super.onBackPressed();
        }

    }
}
