package com.example.developerhaoz.sleephelper.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.developerhaoz.sleephelper.R;

/**
 * Created by lizhonglian on 2017/12/19.
 */

public class AFragment extends Fragment {

    private TextView mTextView;
    private BFragment bFragment;
    private AFragment aFragment;
    private Button mbtn_change,mbtn_reset;

    public static AFragment newInstance(String title){
        AFragment  aFragment = new AFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        aFragment.setArguments(bundle);
        return aFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_a,container,false);

        mTextView = (TextView) view.findViewById(R.id.tv_frag);
        mbtn_change = (Button) view.findViewById(R.id.btn_change);
        mbtn_reset = (Button) view.findViewById(R.id.btn_reset);
        Log.d("AFragment","----onCreateView----");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (null != getArguments()){
            mTextView.setText(getArguments().getString("title"));
        }

        mbtn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == bFragment){
                    bFragment = new BFragment();
                }

                Fragment fragment = getFragmentManager().findFragmentByTag("aFragment");
                if (null != fragment){
                    getFragmentManager().beginTransaction().hide(fragment).add(R.id.fl_container,bFragment).addToBackStack(null).commitAllowingStateLoss();
                }else {
//                    getFragmentManager().beginTransaction().replace(R.id.fl_container,bFragment).addToBackStack(null).commitAllowingStateLoss();
                }
            }
        });

        mbtn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText("我是AFragment的参数");

//                if (null == aFragment){
//                    aFragment = new AFragment();
//                }
//                getFragmentManager().beginTransaction().replace(R.id.fl_container,aFragment).commitAllowingStateLoss();
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
