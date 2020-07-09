package com.yinxiang.model;

import java.util.List;

public class WalletRecordData {

    /**
     * code : 200
     * msg : 成功
     * data : {"record":{"current_page":1,"data":[{"id":2,"price":"0.00","created_at":"2020-06-14 13:05:49","updated_at":"2020-06-14 13:05:49","tourist_id":3,"trans_no":"202006141592111149541","type":1,"trans_type":1,"wallet_record":10},{"id":3,"price":"0.00","created_at":"2020-06-14 13:06:57","updated_at":"2020-06-14 13:06:57","tourist_id":3,"trans_no":"202006141592111217611","type":1,"trans_type":1,"wallet_record":10},{"id":4,"price":"0.00","created_at":"2020-06-14 13:08:47","updated_at":"2020-06-14 13:08:47","tourist_id":3,"trans_no":"202006141592111327976","type":1,"trans_type":1,"wallet_record":10},{"id":5,"price":"0.00","created_at":"2020-06-14 13:09:18","updated_at":"2020-06-14 13:09:18","tourist_id":3,"trans_no":"202006141592111358383","type":1,"trans_type":1,"wallet_record":10},{"id":6,"price":"0.00","created_at":"2020-06-14 23:38:57","updated_at":"2020-06-14 23:38:57","tourist_id":3,"trans_no":"202006141592149137994","type":1,"trans_type":1,"wallet_record":10},{"id":7,"price":"0.00","created_at":"2020-06-14 23:39:07","updated_at":"2020-06-14 23:39:07","tourist_id":3,"trans_no":"202006141592149147939","type":1,"trans_type":1,"wallet_record":10},{"id":8,"price":"0.00","created_at":"2020-06-14 23:39:29","updated_at":"2020-06-14 23:39:29","tourist_id":3,"trans_no":"202006141592149169919","type":1,"trans_type":1,"wallet_record":10},{"id":9,"price":"0.00","created_at":"2020-06-15 22:30:08","updated_at":"2020-06-15 22:30:08","tourist_id":3,"trans_no":"202006151592231408309","type":1,"trans_type":1,"wallet_record":10},{"id":10,"price":"0.00","created_at":"2020-06-15 22:30:26","updated_at":"2020-06-15 22:30:26","tourist_id":3,"trans_no":"202006151592231426613","type":1,"trans_type":1,"wallet_record":10},{"id":11,"price":"0.00","created_at":"2020-06-15 22:30:38","updated_at":"2020-06-15 22:30:38","tourist_id":3,"trans_no":"202006151592231438711","type":1,"trans_type":1,"wallet_record":10}],"first_page_url":"http://enjoy.fengyunguoyuan.com/api/personWallet/record?page=1","from":1,"last_page":4,"last_page_url":"http://enjoy.fengyunguoyuan.com/api/personWallet/record?page=4","next_page_url":"http://enjoy.fengyunguoyuan.com/api/personWallet/record?page=2","path":"http://enjoy.fengyunguoyuan.com/api/personWallet/record","per_page":"10","prev_page_url":null,"to":10,"total":39},"tourist":{"id":3,"name":"信用度","phone":"13521614827","avatar":"upload/20200606024929Gkda2.jpg","password":"$2y$10$CGTViSVDrLGfX1NsOhiHSeLhXdZxdidRYCd3U/b1c3U78y31EBjt6","remember_token":null,"created_at":"2020-05-30 12:08:57","updated_at":"2020-07-09 17:38:21","tourist_id":"52185256","sex":2,"cancel":1,"reg":"","desc":"这个可以有","addr":"北京市 西城区","like_notice":1,"comment_notice":2,"is_vip":2,"vip_expire":null,"wallet_token":8801,"fan_number":24,"follow_number":39,"level":1,"age":0,"yunxin_token":"4b7f19878a8b47e7253196a74a4d2b22","yunxin_accid":"52185256"}}
     */

    private int code;
    private String msg;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * record : {"current_page":1,"data":[{"id":2,"price":"0.00","created_at":"2020-06-14 13:05:49","updated_at":"2020-06-14 13:05:49","tourist_id":3,"trans_no":"202006141592111149541","type":1,"trans_type":1,"wallet_record":10},{"id":3,"price":"0.00","created_at":"2020-06-14 13:06:57","updated_at":"2020-06-14 13:06:57","tourist_id":3,"trans_no":"202006141592111217611","type":1,"trans_type":1,"wallet_record":10},{"id":4,"price":"0.00","created_at":"2020-06-14 13:08:47","updated_at":"2020-06-14 13:08:47","tourist_id":3,"trans_no":"202006141592111327976","type":1,"trans_type":1,"wallet_record":10},{"id":5,"price":"0.00","created_at":"2020-06-14 13:09:18","updated_at":"2020-06-14 13:09:18","tourist_id":3,"trans_no":"202006141592111358383","type":1,"trans_type":1,"wallet_record":10},{"id":6,"price":"0.00","created_at":"2020-06-14 23:38:57","updated_at":"2020-06-14 23:38:57","tourist_id":3,"trans_no":"202006141592149137994","type":1,"trans_type":1,"wallet_record":10},{"id":7,"price":"0.00","created_at":"2020-06-14 23:39:07","updated_at":"2020-06-14 23:39:07","tourist_id":3,"trans_no":"202006141592149147939","type":1,"trans_type":1,"wallet_record":10},{"id":8,"price":"0.00","created_at":"2020-06-14 23:39:29","updated_at":"2020-06-14 23:39:29","tourist_id":3,"trans_no":"202006141592149169919","type":1,"trans_type":1,"wallet_record":10},{"id":9,"price":"0.00","created_at":"2020-06-15 22:30:08","updated_at":"2020-06-15 22:30:08","tourist_id":3,"trans_no":"202006151592231408309","type":1,"trans_type":1,"wallet_record":10},{"id":10,"price":"0.00","created_at":"2020-06-15 22:30:26","updated_at":"2020-06-15 22:30:26","tourist_id":3,"trans_no":"202006151592231426613","type":1,"trans_type":1,"wallet_record":10},{"id":11,"price":"0.00","created_at":"2020-06-15 22:30:38","updated_at":"2020-06-15 22:30:38","tourist_id":3,"trans_no":"202006151592231438711","type":1,"trans_type":1,"wallet_record":10}],"first_page_url":"http://enjoy.fengyunguoyuan.com/api/personWallet/record?page=1","from":1,"last_page":4,"last_page_url":"http://enjoy.fengyunguoyuan.com/api/personWallet/record?page=4","next_page_url":"http://enjoy.fengyunguoyuan.com/api/personWallet/record?page=2","path":"http://enjoy.fengyunguoyuan.com/api/personWallet/record","per_page":"10","prev_page_url":null,"to":10,"total":39}
         * tourist : {"id":3,"name":"信用度","phone":"13521614827","avatar":"upload/20200606024929Gkda2.jpg","password":"$2y$10$CGTViSVDrLGfX1NsOhiHSeLhXdZxdidRYCd3U/b1c3U78y31EBjt6","remember_token":null,"created_at":"2020-05-30 12:08:57","updated_at":"2020-07-09 17:38:21","tourist_id":"52185256","sex":2,"cancel":1,"reg":"","desc":"这个可以有","addr":"北京市 西城区","like_notice":1,"comment_notice":2,"is_vip":2,"vip_expire":null,"wallet_token":8801,"fan_number":24,"follow_number":39,"level":1,"age":0,"yunxin_token":"4b7f19878a8b47e7253196a74a4d2b22","yunxin_accid":"52185256"}
         */

        private RecordBean record;
        private TouristBean tourist;

        public RecordBean getRecord() {
            return record;
        }

        public void setRecord(RecordBean record) {
            this.record = record;
        }

        public TouristBean getTourist() {
            return tourist;
        }

        public void setTourist(TouristBean tourist) {
            this.tourist = tourist;
        }

        public static class RecordBean {
            /**
             * current_page : 1
             * data : [{"id":2,"price":"0.00","created_at":"2020-06-14 13:05:49","updated_at":"2020-06-14 13:05:49","tourist_id":3,"trans_no":"202006141592111149541","type":1,"trans_type":1,"wallet_record":10},{"id":3,"price":"0.00","created_at":"2020-06-14 13:06:57","updated_at":"2020-06-14 13:06:57","tourist_id":3,"trans_no":"202006141592111217611","type":1,"trans_type":1,"wallet_record":10},{"id":4,"price":"0.00","created_at":"2020-06-14 13:08:47","updated_at":"2020-06-14 13:08:47","tourist_id":3,"trans_no":"202006141592111327976","type":1,"trans_type":1,"wallet_record":10},{"id":5,"price":"0.00","created_at":"2020-06-14 13:09:18","updated_at":"2020-06-14 13:09:18","tourist_id":3,"trans_no":"202006141592111358383","type":1,"trans_type":1,"wallet_record":10},{"id":6,"price":"0.00","created_at":"2020-06-14 23:38:57","updated_at":"2020-06-14 23:38:57","tourist_id":3,"trans_no":"202006141592149137994","type":1,"trans_type":1,"wallet_record":10},{"id":7,"price":"0.00","created_at":"2020-06-14 23:39:07","updated_at":"2020-06-14 23:39:07","tourist_id":3,"trans_no":"202006141592149147939","type":1,"trans_type":1,"wallet_record":10},{"id":8,"price":"0.00","created_at":"2020-06-14 23:39:29","updated_at":"2020-06-14 23:39:29","tourist_id":3,"trans_no":"202006141592149169919","type":1,"trans_type":1,"wallet_record":10},{"id":9,"price":"0.00","created_at":"2020-06-15 22:30:08","updated_at":"2020-06-15 22:30:08","tourist_id":3,"trans_no":"202006151592231408309","type":1,"trans_type":1,"wallet_record":10},{"id":10,"price":"0.00","created_at":"2020-06-15 22:30:26","updated_at":"2020-06-15 22:30:26","tourist_id":3,"trans_no":"202006151592231426613","type":1,"trans_type":1,"wallet_record":10},{"id":11,"price":"0.00","created_at":"2020-06-15 22:30:38","updated_at":"2020-06-15 22:30:38","tourist_id":3,"trans_no":"202006151592231438711","type":1,"trans_type":1,"wallet_record":10}]
             * first_page_url : http://enjoy.fengyunguoyuan.com/api/personWallet/record?page=1
             * from : 1
             * last_page : 4
             * last_page_url : http://enjoy.fengyunguoyuan.com/api/personWallet/record?page=4
             * next_page_url : http://enjoy.fengyunguoyuan.com/api/personWallet/record?page=2
             * path : http://enjoy.fengyunguoyuan.com/api/personWallet/record
             * per_page : 10
             * prev_page_url : null
             * to : 10
             * total : 39
             */

            private int current_page;
            private String first_page_url;
            private int from;
            private int last_page;
            private String last_page_url;
            private String next_page_url;
            private String path;
            private String per_page;
            private Object prev_page_url;
            private int to;
            private int total;
            private List<DataBean> data;

            public int getCurrent_page() {
                return current_page;
            }

            public void setCurrent_page(int current_page) {
                this.current_page = current_page;
            }

            public String getFirst_page_url() {
                return first_page_url;
            }

            public void setFirst_page_url(String first_page_url) {
                this.first_page_url = first_page_url;
            }

            public int getFrom() {
                return from;
            }

            public void setFrom(int from) {
                this.from = from;
            }

            public int getLast_page() {
                return last_page;
            }

            public void setLast_page(int last_page) {
                this.last_page = last_page;
            }

            public String getLast_page_url() {
                return last_page_url;
            }

            public void setLast_page_url(String last_page_url) {
                this.last_page_url = last_page_url;
            }

            public String getNext_page_url() {
                return next_page_url;
            }

            public void setNext_page_url(String next_page_url) {
                this.next_page_url = next_page_url;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getPer_page() {
                return per_page;
            }

            public void setPer_page(String per_page) {
                this.per_page = per_page;
            }

            public Object getPrev_page_url() {
                return prev_page_url;
            }

            public void setPrev_page_url(Object prev_page_url) {
                this.prev_page_url = prev_page_url;
            }

            public int getTo() {
                return to;
            }

            public void setTo(int to) {
                this.to = to;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public List<DataBean> getData() {
                return data;
            }

            public void setData(List<DataBean> data) {
                this.data = data;
            }

            public static class DataBean {
                /**
                 * id : 2
                 * price : 0.00
                 * created_at : 2020-06-14 13:05:49
                 * updated_at : 2020-06-14 13:05:49
                 * tourist_id : 3
                 * trans_no : 202006141592111149541
                 * type : 1
                 * trans_type : 1
                 * wallet_record : 10
                 */

                private int id;
                private String price;
                private String created_at;
                private String updated_at;
                private int tourist_id;
                private String trans_no;
                private int type;
                private int trans_type;
                private int wallet_record;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
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

                public int getTourist_id() {
                    return tourist_id;
                }

                public void setTourist_id(int tourist_id) {
                    this.tourist_id = tourist_id;
                }

                public String getTrans_no() {
                    return trans_no;
                }

                public void setTrans_no(String trans_no) {
                    this.trans_no = trans_no;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public int getTrans_type() {
                    return trans_type;
                }

                public void setTrans_type(int trans_type) {
                    this.trans_type = trans_type;
                }

                public int getWallet_record() {
                    return wallet_record;
                }

                public void setWallet_record(int wallet_record) {
                    this.wallet_record = wallet_record;
                }
            }
        }

        public static class TouristBean {
            /**
             * id : 3
             * name : 信用度
             * phone : 13521614827
             * avatar : upload/20200606024929Gkda2.jpg
             * password : $2y$10$CGTViSVDrLGfX1NsOhiHSeLhXdZxdidRYCd3U/b1c3U78y31EBjt6
             * remember_token : null
             * created_at : 2020-05-30 12:08:57
             * updated_at : 2020-07-09 17:38:21
             * tourist_id : 52185256
             * sex : 2
             * cancel : 1
             * reg :
             * desc : 这个可以有
             * addr : 北京市 西城区
             * like_notice : 1
             * comment_notice : 2
             * is_vip : 2
             * vip_expire : null
             * wallet_token : 8801
             * fan_number : 24
             * follow_number : 39
             * level : 1
             * age : 0
             * yunxin_token : 4b7f19878a8b47e7253196a74a4d2b22
             * yunxin_accid : 52185256
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
            private String yunxin_token;
            private String yunxin_accid;

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

            public String getYunxin_token() {
                return yunxin_token;
            }

            public void setYunxin_token(String yunxin_token) {
                this.yunxin_token = yunxin_token;
            }

            public String getYunxin_accid() {
                return yunxin_accid;
            }

            public void setYunxin_accid(String yunxin_accid) {
                this.yunxin_accid = yunxin_accid;
            }
        }
    }
}
