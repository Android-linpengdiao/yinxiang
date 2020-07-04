package com.yinxiang.model;

import java.util.List;

public class FriendsData {

    /**
     * code : 200
     * msg : 成功
     * data : [{"id":6,"name":"青春有你制作人","phone":"18303032844","avatar":"upload/20200606055631F8mzM.jpg","password":"$2y$10$Bl9QrPD7oLzkCXSb9ZwcLOzIoW4NxGxOHqQLRckkZ.XYaWv0Aq2PC","remember_token":null,"created_at":"2020-06-05 16:02:31","updated_at":"2020-07-02 13:26:16","tourist_id":"27915679","sex":1,"cancel":1,"reg":"","desc":"这条街最亮的星","addr":"北京市 朝阳区","like_notice":1,"comment_notice":2,"is_vip":2,"vip_expire":null,"wallet_token":0,"fan_number":15,"follow_number":2,"level":1,"age":0}]
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

    public static class DataBean {
        /**
         * id : 6
         * name : 青春有你制作人
         * phone : 18303032844
         * avatar : upload/20200606055631F8mzM.jpg
         * password : $2y$10$Bl9QrPD7oLzkCXSb9ZwcLOzIoW4NxGxOHqQLRckkZ.XYaWv0Aq2PC
         * remember_token : null
         * created_at : 2020-06-05 16:02:31
         * updated_at : 2020-07-02 13:26:16
         * tourist_id : 27915679
         * sex : 1
         * cancel : 1
         * reg :
         * desc : 这条街最亮的星
         * addr : 北京市 朝阳区
         * like_notice : 1
         * comment_notice : 2
         * is_vip : 2
         * vip_expire : null
         * wallet_token : 0
         * fan_number : 15
         * follow_number : 2
         * level : 1
         * age : 0
         */

        private int id;
        private String name;
        private String phone;
        private String avatar;
        private String password;
        private Object remember_token;
        private String created_at;
        private String updated_at;
        private String tourist_id;
        private int sex;
        private int cancel;
        private String reg;
        private String desc;
        private String addr;
        private int like_notice;
        private int comment_notice;
        private int is_vip;
        private Object vip_expire;
        private int wallet_token;
        private int fan_number;
        private int follow_number;
        private int level;
        private int age;

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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Object getRemember_token() {
            return remember_token;
        }

        public void setRemember_token(Object remember_token) {
            this.remember_token = remember_token;
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

        public String getTourist_id() {
            return tourist_id;
        }

        public void setTourist_id(String tourist_id) {
            this.tourist_id = tourist_id;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getCancel() {
            return cancel;
        }

        public void setCancel(int cancel) {
            this.cancel = cancel;
        }

        public String getReg() {
            return reg;
        }

        public void setReg(String reg) {
            this.reg = reg;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public int getLike_notice() {
            return like_notice;
        }

        public void setLike_notice(int like_notice) {
            this.like_notice = like_notice;
        }

        public int getComment_notice() {
            return comment_notice;
        }

        public void setComment_notice(int comment_notice) {
            this.comment_notice = comment_notice;
        }

        public int getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(int is_vip) {
            this.is_vip = is_vip;
        }

        public Object getVip_expire() {
            return vip_expire;
        }

        public void setVip_expire(Object vip_expire) {
            this.vip_expire = vip_expire;
        }

        public int getWallet_token() {
            return wallet_token;
        }

        public void setWallet_token(int wallet_token) {
            this.wallet_token = wallet_token;
        }

        public int getFan_number() {
            return fan_number;
        }

        public void setFan_number(int fan_number) {
            this.fan_number = fan_number;
        }

        public int getFollow_number() {
            return follow_number;
        }

        public void setFollow_number(int follow_number) {
            this.follow_number = follow_number;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
