package com.example.developerhaoz.sleephelper.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RetrofitTestActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = RetrofitTestActivity.class.getName();

    private Button btn_get, btn_post, btn_delete, btn_postAdd;
    private TextView tv_content;
    private EditText userName, userPasswd, userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_test);

        initView();
    }

    private void initView() {
        btn_get = (Button) findViewById(R.id.send_get);
        btn_post = (Button) findViewById(R.id.send_post);
        btn_delete = (Button) findViewById(R.id.send_delete);
        btn_postAdd = (Button) findViewById(R.id.send_post_add);
        tv_content = (TextView) findViewById(R.id.tv_content_back);
        userName = (EditText) findViewById(R.id.user_name);
        userPasswd = (EditText) findViewById(R.id.user_passwd);
        userID = (EditText) findViewById(R.id.user_id);

        btn_get.setOnClickListener(this);
        btn_post.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_postAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String tmpUserName = userName.getText().toString();
        String tmpUserPassed = userPasswd.getText().toString();
        switch (v.getId()) {
            case R.id.send_get:
                sendGet();
                break;
            case R.id.send_post:

                String tmpUserID = userID.getText().toString();
                if (!TextUtils.isEmpty(tmpUserName) && !TextUtils.isEmpty(tmpUserPassed) && !TextUtils.isEmpty(tmpUserID)) {
                    sendPost(Integer.parseInt(userID.getText().toString()), tmpUserName, tmpUserPassed);
                } else {
                    Toast.makeText(getApplication(), "请输入用户名密码", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.send_delete:
                sendDelete(9);
                break;
            case R.id.send_post_add:
                if (!TextUtils.isEmpty(tmpUserName) && !TextUtils.isEmpty(tmpUserPassed)) {
                    sendPostAdd(tmpUserName, tmpUserPassed);
                } else {
                    Toast.makeText(getApplication(), "请输入用户名密码", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    private void sendGet() {

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
                if (0 == userBack.getCode() && "success".equals(userBack.getMsg()) && null != userBack.data) {
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

    private void sendPost(int id, String name, String passwd) {

        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("http://192.168.1.2:8080/")
                .build();
        GetRequestInterface request = retrofit.create(GetRequestInterface.class);

        Call<testEntity> call = request.updateUserById(id, name, passwd);

        call.enqueue(new Callback<testEntity>() {

            @Override
            public void onResponse(Call<testEntity> call, Response<testEntity> response) {
                Log.e(TAG, "onResponse: " + response.body().getData());

                testEntity userBack = response.body();
                if (0 == userBack.getCode() && "success".equals(userBack.getMsg())) {
                    Toast.makeText(getApplication(), "更新成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplication(), "更新失败", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<testEntity> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplication(), "网络错误" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void sendDelete(int id) {

        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("http://192.168.1.2:8080/")
                .build();
        GetRequestInterface request = retrofit.create(GetRequestInterface.class);

        Call<testEntity> call = request.deleteUserById(id);

        call.enqueue(new Callback<testEntity>() {

            @Override
            public void onResponse(Call<testEntity> call, Response<testEntity> response) {
                Log.e(TAG, "onResponse: " + response.body().getData());

                testEntity userBack = response.body();
                if (0 == userBack.getCode() && "success".equals(userBack.getMsg())) {
                    Toast.makeText(getApplication(), "删除成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplication(), "删除失败", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<testEntity> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplication(), "网络错误" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void sendPostAdd(String name, String passwd) {

        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("http://192.168.1.2:8080/")
                .build();
        GetRequestInterface request = retrofit.create(GetRequestInterface.class);

        Call<testEntity> call = request.addUser(name, passwd);

        call.enqueue(new Callback<testEntity>() {

            @Override
            public void onResponse(Call<testEntity> call, Response<testEntity> response) {
                Log.e(TAG, "onResponse: " + response.body().getData());

                testEntity userBack = response.body();
                if (0 == userBack.getCode() && "success".equals(userBack.getMsg())) {
                    Toast.makeText(getApplication(), "增加用户成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplication(), "增加用户失败", Toast.LENGTH_SHORT).show();
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

        /**
         * 通过id获取一个user信息
         *
         * @param id
         * @return
         */
        @GET("users/{id}")
        Call<testEntity> getUserById(@Path("id") int id);

        /**
         * 通过ID更新一个user的信息
         *
         * @param id
         * @param name
         * @param passwd
         * @return http://localhost:8080/users/?name=www&passwd=222
         */
        @PUT("users/{id}")
        Call<testEntity> updateUserById(@Path("id") int id, @Query("name") String name, @Query("passwd") String passwd);

        /**
         * 通过ID删除一个user
         *
         * @param id
         * @return http://localhost:8080/users/6
         */
        @DELETE("users/{id}")
        Call<testEntity> deleteUserById(@Path("id") int id);

        /**
         * 指定name，passwd，增加一个user
         *
         * @param name
         * @param passwd
         * @return http://localhost:8080/users/11?name=rr&passwd=444
         */
        @POST("users/")
        Call<testEntity> addUser(@Query("name") String name, @Query("passwd") String passwd);
    }
}
