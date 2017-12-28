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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.developerhaoz.sleephelper.R;

/**
 * Created by lizhonglian on 2017/12/19.
 */

public class DFragment extends Fragment {

    private TextView mTextView;
    private ImageView mImageView;
    private Button mbtn_change,mbtn_reset;

    public static DFragment newInstance(String title){
        DFragment cFragment = new DFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        cFragment.setArguments(bundle);
        return cFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_c,container,false);

        mTextView = (TextView) view.findViewById(R.id.tv_frag);
        mImageView = (ImageView) view.findViewById(R.id.iv_frag_c);
        Log.d("AFragment","----onCreateView----");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (null != getArguments()){
            mTextView.setText(getArguments().getString("title"));
        }
        Glide.with(getActivity())
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1514301008362&di=420c54ccd8b30428cb77a0c0a0890d8a&imgtype=0&src=http%3A%2F%2Fg.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F14ce36d3d539b600c4946958e350352ac75cb783.jpg")
                .into(mImageView);

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
