package com.example.developerhaoz.sleephelper.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.developerhaoz.sleephelper.R;

public class PopupWindowActivity extends AppCompatActivity {

    private Button mbtnPop;
    private TextView mbtn_good,mbtn_notBad,mbtn_bad;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_window);

        mbtnPop = (Button) findViewById(R.id.btn_pop);

        mbtnPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1 = getLayoutInflater().inflate(R.layout.layout_pop,null);

                mbtn_good = (TextView) view1.findViewById(R.id.tv_good);
                mbtn_notBad = (TextView) view1.findViewById(R.id.tv_notbad);
                mbtn_bad = (TextView) view1.findViewById(R.id.tv_bad);


                popupWindow = new PopupWindow(view1,mbtnPop.getWidth(),ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.showAsDropDown(mbtnPop);


                mbtn_good.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(PopupWindowActivity.this,"点击了好",Toast.LENGTH_SHORT).show();
                        popupWindow.dismiss();
                    }
                });
                mbtn_notBad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(PopupWindowActivity.this,"点击了还行",Toast.LENGTH_SHORT).show();
                        popupWindow.dismiss();
                    }
                });
                mbtn_bad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(PopupWindowActivity.this,"点击了不好",Toast.LENGTH_SHORT).show();
                        popupWindow.dismiss();
                    }
                });
            }
        });

    }
}
