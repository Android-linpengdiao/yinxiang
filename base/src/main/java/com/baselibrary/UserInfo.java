package com.baselibrary;

import java.io.Serializable;

public class UserInfo implements Serializable {

    /**
     * code : 200
     * msg : 成功
     * data : {"id":3,"name":"信用度","phone":"13521614827","avatar":"users/default.png","password":"$2y$10$f9DWtaqMkrOuYkutlZgYUO3Df3LRngLlY2MT7.guig51I6lhid/4q","remember_token":null,"created_at":"2020-05-30T04:08:57.000000Z","updated_at":"2020-06-05T05:49:12.000000Z","tourist_id":"52185256","sex":2,"cancel":1,"reg":"","desc":"可以的","addr":"","like_notice":1,"comment_notice":1,"is_vip":2,"vip_expire":null,"wallet_token":0,"fan_number":0,"follow_number":0,"level":1,"age":0}
     */

    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * id : 3
         * name : 信用度
         * phone : 13521614827
         * avatar : users/default.png
         * password : $2y$10$f9DWtaqMkrOuYkutlZgYUO3Df3LRngLlY2MT7.guig51I6lhid/4q
         * remember_token : null
         * created_at : 2020-05-30T04:08:57.000000Z
         * updated_at : 2020-06-05T05:49:12.000000Z
         * tourist_id : 52185256
         * sex : 2
         * cancel : 1
         * reg :
         * desc : 可以的
         * addr :
         * like_notice : 1
         * comment_notice : 1
         * is_vip : 2
         * vip_expire : null
         * wallet_token : 0
         * fan_number : 0
         * follow_number : 0
         * level : 1
         * age : 0
         */

        private int id;
        private String name;
        private String phone;
        private String avatar;
        private String password;
        private String remember_token;
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
        private int vip_expire;
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

        public String getRemember_token() {
            return remember_token;
        }

        public void setRemember_token(String remember_token) {
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

        public int getVip_expire() {
            return vip_expire;
        }

        public void setVip_expire(int vip_expire) {
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
