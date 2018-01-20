package com.example.developerhaoz.sleephelper.test;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.developerhaoz.sleephelper.R;
import com.example.developerhaoz.sleephelper.fragment.SlideFragment;
import com.example.developerhaoz.sleephelper.recyclerview.MP3PlayerActivity;
import com.example.developerhaoz.sleephelper.recyclerview.RecyclerViewMainActivity;
import com.example.developerhaoz.sleephelper.recyclerview.adapter.LinearRecyclerViewAdapter;
import com.example.developerhaoz.sleephelper.util.AppConstants;
import com.example.developerhaoz.sleephelper.util.SpUtils;
import com.example.developerhaoz.sleephelper.widget.CustomDialog;
import com.github.paolorotolo.appintro.AppIntro;
import com.hss01248.lib.MyDialogListener;
import com.hss01248.lib.StytledDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.task.PriorityExecutor;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class IntroTestActivity extends AppIntro {

    private static final String TAG = IntroTestActivity.class.getName();
    private static final int PERMISSON_REQUESTCODE = 1;


    /**
     * 可取消的任务
     */
    private Callback.Cancelable cancelable;

    /**
     * 进度条对话框
     */
    private ProgressDialog progressDialog;

    private String pathApk = "http://down.72g.com/upload/app/201407/201407150923238621.apk";


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            showUpdateDialog();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPermission();

        x.view().inject(this);//绑定注解
        initProgressDialog();
    }

    private void initPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            checkSkip();
            return;
        }
        if (ContextCompat.checkSelfPermission(IntroTestActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(IntroTestActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSON_REQUESTCODE);
        }else {
            getJSON();

            //TODO:
//            checkSkip();
        }
    }


    private void btnDownloadOnClick(){
        //设置请求参数
        RequestParams params = new RequestParams(pathApk);
        params.setAutoResume(false);//设置是否在下载是自动断点续传
        params.setAutoRename(false);//设置是否根据头信息自动命名文件
        params.setSaveFilePath("/sdcard/xutils/xUtils_1.avi");
        params.setExecutor(new PriorityExecutor(2, true));//自定义线程池,有效的值范围[1, 3], 设置为3时, 可能阻塞图片加载.
        params.setCancelFast(true);//是否可以被立即停止.

        //下面的回调都是在主线程中运行的,这里设置的带进度的回调
        cancelable = x.http().get(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onCancelled(CancelledException arg0) {
                Log.d(TAG, "取消"+Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {
                Log.d(TAG, "onError: 失败"+Thread.currentThread().getName());
                progressDialog.dismiss();
            }

            @Override
            public void onFinished() {
                Log.d(TAG, "完成,每次取消下载也会执行该方法"+Thread.currentThread().getName());
                progressDialog.dismiss();

                //如果点击了取消，就退出app
                if (cancelable.isCancelled()){
                    finish();
                }

            }

            @Override
            public void onSuccess(File arg0) {
                Log.d(TAG, "下载成功的时候执行"+Thread.currentThread().getName());
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                if (isDownloading) {
                    progressDialog.setProgress((int) (current*100/total));
                    Log.d(TAG, "下载中,会不断的进行回调:"+Thread.currentThread().getName());
                }
            }

            @Override
            public void onStarted() {
                Log.d(TAG, "开始下载的时候执行"+Thread.currentThread().getName());
                progressDialog.show();
            }

            @Override
            public void onWaiting() {
                Log.d(TAG, "等待,在onStarted方法之前执行"+Thread.currentThread().getName());
            }

        });
    }

    /*初始化对话框*/
    private void initProgressDialog() {

        //创建进度条对话框
        progressDialog = new ProgressDialog(this);
        //设置标题
        progressDialog.setTitle("下载文件");
        //设置信息
        progressDialog.setMessage("玩命下载中...");
        //设置显示的格式
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //设置按钮
        progressDialog.setButton(ProgressDialog.BUTTON_NEGATIVE, "暂停",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //点击取消正在下载的操作
                cancelable.cancel();
            }});
    }

    private void checkSkip() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startMusicActivity();
            }
        };
        timer.schedule(task, 5000);
    }

    private void showUpdateDialog(){

        CustomDialog updateDialog = new CustomDialog(IntroTestActivity.this);
        updateDialog.setCancelable(false);
        updateDialog.setMessage("发现新版本，确定更新吗？")
                .setTitle("温馨提示")
                .setCancel("取消", new CustomDialog.IOnCancelClickListener() {
                    @Override
                    public void onCancel(CustomDialog dialog) {
                        Toast.makeText(IntroTestActivity.this,"点击了取消",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .setInsist("确定", new CustomDialog.IOnInsistClickListener() {
                    @Override
                    public void onInsist(CustomDialog dialog) {
                        btnDownloadOnClick();
                        dialog.dismiss();
                    }
                }).show();
    }

    /**
     * 解析JSON
     */
    public void getJSON() {
        // 子线程访问，耗时操作
        new Thread() {
            public void run() {
                try {
                    // JSON地址
                    HttpURLConnection conn = (HttpURLConnection) new URL(
                            //模拟器一般有一个预留IP：10.0.2.2
                            "http://192.168.1.3:8080/zhonglian/update.json")
                            .openConnection();
                    //请求方式GRT
                    conn.setRequestMethod("GET");
                    //连接超时
                    conn.setConnectTimeout(2000);
                    //响应超时
                    conn.setReadTimeout(2000);
                    //连接
                    conn.connect();
                    //获取请求码
                    int responseCode = conn.getResponseCode();

                    //等于200说明请求成功
                    if(responseCode == 200){
                        //拿到他的输入流
                        InputStream in = conn.getInputStream();
                        String stream = IntroTestActivity.toStream(in);

                        Log.i("TAG", stream);
                        Log.i("TAG", "server : 200");

                        JSONObject jsonObject = new JSONObject(stream);
                        String versionName = jsonObject.getString("versionName");
                        Integer versionCode = jsonObject.getInt("versionCode");
                        String content = jsonObject.getString("content");
                        String url = jsonObject.getString("url");

                        checkUpdateInfo(versionCode);

                    }else if(responseCode == 503){
                        Log.d(TAG,"server :503");
                    }else {
                        Log.d(TAG,"server :404");
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }



    // 对外发放
    public static String toStream(InputStream in) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        // 长度
        int length = 0;
        byte[] buffer = new byte[1024];

        // -1代表读完了
        while ((length = in.read(buffer)) != -1) {
            out.write(buffer, 0, length);
        }

        // 读完关闭
        in.close();
        out.close();

        // 我们把返回的数据转换成String
        return out.toString();
    }

    /**
     * 获取APP版本号
     *
     * @return
     */
    private Integer getAppVersionNumber() {
        try {
            //PackageManager管理器
            PackageManager pm = getPackageManager();
            //获取相关信息
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            //版本号
            int version = packageInfo.versionCode;

            Log.i("版本信息", "本地版本号：" + version);

            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取APP版本号
     *
     * @return
     */
    private String getAppVersionName() {
        try {
            //PackageManager管理器
            PackageManager pm = getPackageManager();
            //获取相关信息
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            //版本名称
            String name = packageInfo.versionName;

            Log.i("版本信息", "本地版本名称：" + name);

            return name;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 升级弹框
     */
    private void checkUpdateInfo(Integer remoteVersion) {

        Integer localVersion = getAppVersionNumber();

        if (remoteVersion > localVersion){

            //发现新版本，通知更新
            handler.sendEmptyMessage(0);
        }else {
            checkSkip();
        }
    }

    private void startMusicActivity() {
        Intent intent = new Intent();
        intent.setClass(this, MP3PlayerActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {

        addSlide(SlideFragment.newInstance(R.layout.fragment_c));
        addSlide(SlideFragment.newInstance(R.layout.fragment_d));
        setSeparatorColor(getResources().getColor(R.color.colorAccent));
        setVibrateIntensity(30);
        setSkipText("跳过");
        setDoneText("完成");
    }

    @Override
    public void onSkipPressed() {
        Intent intent = new Intent(IntroTestActivity.this, RecyclerViewMainActivity.class);
        startActivity(intent);
        SpUtils.putBoolean(IntroTestActivity.this, AppConstants.FIRST_OPEN, true);
        finish();
    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onDonePressed() {
        Intent intent = new Intent(IntroTestActivity.this, RecyclerViewMainActivity.class);
        startActivity(intent);
        SpUtils.putBoolean(IntroTestActivity.this, AppConstants.FIRST_OPEN, true);
        finish();
    }

    @Override
    public void onSlideChanged() {

    }

}
