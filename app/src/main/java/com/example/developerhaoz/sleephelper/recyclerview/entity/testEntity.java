package com.example.developerhaoz.sleephelper.recyclerview.entity;

/**
 * Created by lizhonglian on 2018/1/31.
 */

public class testEntity {

    /**
     * code : 0
     * data : {"id":1,"name":"aaa","passwd":"11111"}
     * date : 2018-02-03 21:58:23
     * msg : 请求成功
     */

    private int code;
    public DataBean data;
    private String date;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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
