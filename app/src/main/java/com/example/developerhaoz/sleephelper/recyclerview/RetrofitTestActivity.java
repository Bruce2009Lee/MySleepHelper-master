package com.example.developerhaoz.sleephelper.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.developerhaoz.sleephelper.R;
import com.example.developerhaoz.sleephelper.recyclerview.entity.Cat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class RetrofitTestActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = RetrofitTestActivity.class.getName();

    private Button btn_get,btn_post;
    private TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_test);

        initView();
    }

    private void initView(){
        btn_get = (Button) findViewById(R.id.send_get);
        btn_post = (Button) findViewById(R.id.send_post);
        tv_content = (TextView) findViewById(R.id.tv_content_back);

        btn_get.setOnClickListener(this);
        btn_post.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.send_get:
                request();
                break;
            case R.id.send_post:
                break;

        }
    }

    private void request(){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://192.168.1.2:8080/")
                .build();
        GetRequestInterface request = retrofit.create(GetRequestInterface.class);
        Call<Cat> call = request.getCatByName("18");

        call.enqueue(new Callback<Cat>() {
            @Override
            public void onResponse(Call<Cat> call, Response<Cat> response) {
                Log.e(TAG, "onResponse: " + response.body());

                Cat catBack = response.body();
                tv_content.setText("cat age: "+ catBack.getCatAge());
            }

            @Override
            public void onFailure(Call<Cat> call, Throwable t) {
                Toast.makeText(getApplication(),"网络错误",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public interface GetRequestInterface {

        //http://localhost:8080/cat/findByCatName?catName=18
        @GET("/cat/findByCatName")
        Call<Cat> getCatByName(@Query("catName") String catName);
    }
}
