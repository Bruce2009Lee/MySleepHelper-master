package com.example.developerhaoz.sleephelper.recyclerview;

/**
 * Created by lizhonglian on 2017/12/28.
 */

public class Info {

    private String url;
    private String title;

    public Info(String title, String url) {
        this.url = url;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
