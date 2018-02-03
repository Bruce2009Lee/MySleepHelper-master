package com.example.developerhaoz.sleephelper.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.developerhaoz.sleephelper.R;
import com.example.developerhaoz.sleephelper.recyclerview.entity.testEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class RetrofitTestActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = RetrofitTestActivity.class.getName();

    private Button btn_get, btn_post;
    private TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_test);

        initView();
    }

    private void initView() {
        btn_get = (Button) findViewById(R.id.send_get);
        btn_post = (Button) findViewById(R.id.send_post);
        tv_content = (TextView) findViewById(R.id.tv_content_back);

        btn_get.setOnClickListener(this);
        btn_post.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.send_get:
                request();
                break;
            case R.id.send_post:
                break;

        }
    }

    private void request() {

        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("http://192.168.1.2:8080/")
                .build();
        GetRequestInterface request = retrofit.create(GetRequestInterface.class);

        Call<testEntity> call = request.getUserById(1);

        call.enqueue(new Callback<testEntity>() {

            @Override
            public void onResponse(Call<testEntity> call, Response<testEntity> response) {
                Log.e(TAG, "onResponse: " + response.body().getData());

                testEntity userBack = response.body();
                if (0 == userBack.getCode() && null != userBack.data) {
                    Toast.makeText(getApplication(), "user name:" + userBack.data.getName(), Toast.LENGTH_SHORT).show();
                    tv_content.setText("user name: " + userBack.data.getName());
                } else {
                    Toast.makeText(getApplication(), "body 为空", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<testEntity> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplication(), "网络错误" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public interface GetRequestInterface {

        @GET("users/{id}")
        Call<testEntity> getUserById(@Path("id") int id);
    }
}
