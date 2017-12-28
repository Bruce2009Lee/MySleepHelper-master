package com.example.developerhaoz.sleephelper.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.developerhaoz.sleephelper.R;
import com.example.developerhaoz.sleephelper.recyclerview.ContainerActivity;

/**
 * Created by lizhonglian on 2017/12/19.
 */

public class BFragment extends Fragment {

    private TextView mTextView;
    private Button mbtn_message;
    private onButtonClick mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_b,container,false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (onButtonClick)context;
        }catch (ClassCastException e) {
            throw new ClassCastException("必须实现onButtonClick接口");
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        
        super.onViewCreated(view, savedInstanceState);
        mTextView = (TextView) view.findViewById(R.id.tv_frag);
        mbtn_message = (Button) view.findViewById(R.id.btn_message);

        mbtn_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClick("来自BFragment的消息");
            }
        });

    }

    public interface onButtonClick{
        void onButtonClick(String msg);
    }
}
