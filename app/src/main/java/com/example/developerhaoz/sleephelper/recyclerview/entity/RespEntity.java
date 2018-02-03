package com.example.developerhaoz.sleephelper.recyclerview.entity;

import java.util.Date;
import java.util.List;

/**
 * Created by lizhonglian on 2018/1/31.
 */

public class RespEntity {

    public int code;
    public String msg;
    public List<DataBean> data;

    private String date;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static class DataBean {
        /**
         * id : 1
         * name : aaa
         * passwd : 11111
         */

        private int id;
        private String name;
        private String passwd;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPasswd() {
            return passwd;
        }

        public void setPasswd(String passwd) {
            this.passwd = passwd;
        }
    }
}
