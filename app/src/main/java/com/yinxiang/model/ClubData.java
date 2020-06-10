package com.yinxiang.model;

import java.io.Serializable;
import java.util.List;

public class ClubData implements Serializable {

    /**
     * code : 200
     * msg : 成功
     * data : [{"id":2,"created_at":"2020-05-25T08:07:11.000000Z","updated_at":"2020-05-25T08:49:11.000000Z","name":"街舞社团","logo":"upload/20200525120121QkKOt.jpg","license":"upload/20200525120121QkKOt.jpg","idcard_front":"upload/20200525120121QkKOt.jpg","idcard_back":"upload/20200525120121QkKOt.jpg","phone":"18322233491","tourist_id":1,"tourist_name":"测试","desc":"街舞社团","status":1,"refuse":null,"join":1,"join_token":10,"dissolve":null,"dissolve_time":null},{"id":4,"created_at":"2020-05-25T08:34:24.000000Z","updated_at":"2020-05-25T08:34:24.000000Z","name":"街舞社团","logo":"upload/20200525120121QkKOt.jpg","license":"upload/20200525120121QkKOt.jpg","idcard_front":"upload/20200525120121QkKOt.jpg","idcard_back":"upload/20200525120121QkKOt.jpg","phone":"18322233491","tourist_id":1,"tourist_name":"测试","desc":"","status":3,"refuse":null,"join":2,"join_token":0,"dissolve":null,"dissolve_time":null},{"id":5,"created_at":"2020-06-05T15:37:00.000000Z","updated_at":"2020-06-05T15:44:29.000000Z","name":"新社团","logo":"upload/20200605111858YtmZU.jpg","license":"upload/20200605111858YtmZU.jpg","idcard_front":"upload/20200605111858YtmZU.jpg","idcard_back":"upload/20200605111858YtmZU.jpg","phone":"13521614827","tourist_id":3,"tourist_name":"信用度","desc":"街舞社团","status":1,"refuse":null,"join":2,"join_token":0,"dissolve":null,"dissolve_time":null}]
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
         * id : 2
         * created_at : 2020-05-25T08:07:11.000000Z
         * updated_at : 2020-05-25T08:49:11.000000Z
         * name : 街舞社团
         * logo : upload/20200525120121QkKOt.jpg
         * license : upload/20200525120121QkKOt.jpg
         * idcard_front : upload/20200525120121QkKOt.jpg
         * idcard_back : upload/20200525120121QkKOt.jpg
         * phone : 18322233491
         * tourist_id : 1
         * tourist_name : 测试
         * desc : 街舞社团
         * status : 1
         * refuse : null
         * join : 1
         * join_token : 10
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
        private int selected;
        private String refuse;
        private int join;  // 1收费入团 2免费入团
        private int join_token;
        private String dissolve;
        private String dissolve_time;

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

        public int getSelected() {
            return selected;
        }

        public void setSelected(int selected) {
            this.selected = selected;
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

        public String getDissolve() {
            return dissolve;
        }

        public void setDissolve(String dissolve) {
            this.dissolve = dissolve;
        }

        public String getDissolve_time() {
            return dissolve_time;
        }

        public void setDissolve_time(String dissolve_time) {
            this.dissolve_time = dissolve_time;
        }
    }
}
