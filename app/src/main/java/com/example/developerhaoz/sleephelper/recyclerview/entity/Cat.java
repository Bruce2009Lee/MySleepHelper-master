package com.example.developerhaoz.sleephelper.recyclerview.entity;

import android.util.Log;

/**
 * Created by lizhonglian on 2018/1/26.
 */

public class Cat {
    /**
     * catAge : 2
     * catName : 18
     * id : 8
     */

    private int catAge;
    private String catName;
    private int id;

    public void show() {
        Log.d("CatInfo","" + id);
        Log.d("CatInfo","" + catName);
        Log.d("CatInfo","" + catAge);
    }

    public int getCatAge() {
        return catAge;
    }

    public void setCatAge(int catAge) {
        this.catAge = catAge;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
