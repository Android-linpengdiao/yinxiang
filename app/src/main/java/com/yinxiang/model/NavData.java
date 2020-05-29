package com.yinxiang.model;

import java.io.Serializable;
import java.util.List;

public class NavData implements Serializable {

    /**
     * code : 200
     * msg : 成功
     * data : [{"id":6,"created_at":"2019-10-14 16:03:00","updated_at":"2020-03-22 16:23:50","name":"音乐","sort":1,"image":null},{"id":1,"created_at":"2019-09-23 23:40:00","updated_at":"2020-03-22 16:24:56","name":"棋类","sort":2,"image":null},{"id":5,"created_at":"2019-10-14 16:02:00","updated_at":"2020-03-22 16:23:58","name":"故事","sort":3,"image":null},{"id":3,"created_at":"2019-10-08 13:43:00","updated_at":"2020-03-22 16:25:23","name":"运动","sort":4,"image":null},{"id":4,"created_at":"2019-10-14 16:02:00","updated_at":"2020-03-22 16:24:08","name":"美术","sort":5,"image":null},{"id":2,"created_at":"2019-09-23 23:40:00","updated_at":"2020-03-22 16:25:30","name":"书法","sort":6,"image":null},{"id":7,"created_at":"2020-03-22 16:25:56","updated_at":"2020-03-22 16:25:56","name":"曲艺","sort":7,"image":null},{"id":8,"created_at":"2020-03-22 16:26:12","updated_at":"2020-03-22 16:26:12","name":"方言","sort":8,"image":null},{"id":10,"created_at":"2020-03-22 16:34:00","updated_at":"2020-05-23 13:32:08","name":"国学","sort":9,"image":"navs/May2020/DyYFB85UhAc3B4lxwzgS.jpg"},{"id":9,"created_at":"2020-03-22 16:27:06","updated_at":"2020-03-22 16:27:06","name":"心声","sort":10,"image":null}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

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

    public static class DataBean implements Serializable {
        /**
         * id : 6
         * created_at : 2019-10-14 16:03:00
         * updated_at : 2020-03-22 16:23:50
         * name : 音乐
         * sort : 1
         * image : null
         */

        private int id;
        private String created_at;
        private String updated_at;
        private String name;
        private int sort;
        private String image;
        private int status = 0;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
