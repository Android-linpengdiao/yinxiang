package com.yinxiang.model;

import java.io.Serializable;
import java.util.List;

public class ClubData implements Serializable {

    /**
     * code : 200
     * msg : 成功
     * data : [{"id":11,"created_at":"2020-06-14 00:17:00","updated_at":"2020-06-14 12:30:50","name":"创建社团测试","logo":"upload/20200614121731JXdDl.jpg","license":"upload/20200614121737cErPt.jpg","idcard_front":"upload/20200614121741axFHa.jpg","idcard_back":"upload/20200614121745Pxq09.jpg","phone":"13521614827","tourist_id":3,"tourist_name":"信用度","desc":"创建社团测试","status":2,"refuse":"审核拒绝。您的社团名称涉及敏感词汇不合规。","join":2,"join_token":0,"dissolve":null,"dissolve_time":null}]
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

    public static class DataBean implements Serializable{
        /**
         * id : 11
         * created_at : 2020-06-14 00:17:00
         * updated_at : 2020-06-14 12:30:50
         * name : 创建社团测试
         * logo : upload/20200614121731JXdDl.jpg
         * license : upload/20200614121737cErPt.jpg
         * idcard_front : upload/20200614121741axFHa.jpg
         * idcard_back : upload/20200614121745Pxq09.jpg
         * phone : 13521614827
         * tourist_id : 3
         * tourist_name : 信用度
         * desc : 创建社团测试
         * status : 2
         * refuse : 审核拒绝。您的社团名称涉及敏感词汇不合规。
         * join : 2
         * join_token : 0
         * dissolve : null
         * dissolve_time : null
         */

        private int id;
        private String created_at;
        private String updated_at;
        private String name;
        private String logo;
        private String license;
        private String idcard_front;
        private String idcard_back;
        private String phone;
        private int tourist_id;
        private String tourist_name;
        private String desc;
        private int status;
        private String refuse;
        private int join;
        private boolean is_join;
        private int join_token;
        private Object dissolve;
        private Object dissolve_time;
        private int selected;

        public boolean isIs_join() {
            return is_join;
        }

        public void setIs_join(boolean is_join) {
            this.is_join = is_join;
        }

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

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getLicense() {
            return license;
        }

        public void setLicense(String license) {
            this.license = license;
        }

        public String getIdcard_front() {
            return idcard_front;
        }

        public void setIdcard_front(String idcard_front) {
            this.idcard_front = idcard_front;
        }

        public String getIdcard_back() {
            return idcard_back;
        }

        public void setIdcard_back(String idcard_back) {
            this.idcard_back = idcard_back;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getTourist_id() {
            return tourist_id;
        }

        public void setTourist_id(int tourist_id) {
            this.tourist_id = tourist_id;
        }

        public String getTourist_name() {
            return tourist_name;
        }

        public void setTourist_name(String tourist_name) {
            this.tourist_name = tourist_name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getRefuse() {
            return refuse;
        }

        public void setRefuse(String refuse) {
            this.refuse = refuse;
        }

        public int getJoin() {
            return join;
        }

        public void setJoin(int join) {
            this.join = join;
        }

        public int getJoin_token() {
            return join_token;
        }

        public void setJoin_token(int join_token) {
            this.join_token = join_token;
        }

        public Object getDissolve() {
            return dissolve;
        }

        public void setDissolve(Object dissolve) {
            this.dissolve = dissolve;
        }

        public Object getDissolve_time() {
            return dissolve_time;
        }

        public void setDissolve_time(Object dissolve_time) {
            this.dissolve_time = dissolve_time;
        }

        public int getSelected() {
            return selected;
        }

        public void setSelected(int selected) {
            this.selected = selected;
        }
    }
}
