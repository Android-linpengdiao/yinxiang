package com.yinxiang.model;

import java.util.List;

public class FansUserData {

    /**
     * code : 200
     * msg : 成功
     * data : {"current_page":1,"data":[{"id":6,"name":"青春有你","phone":"18303032844","avatar":"upload/20200606055631F8mzM.jpg","password":"$2y$10$Bl9QrPD7oLzkCXSb9ZwcLOzIoW4NxGxOHqQLRckkZ.XYaWv0Aq2PC","remember_token":null,"created_at":"2020-06-05 16:02:31","updated_at":"2020-06-14 09:51:04","tourist_id":"27915679","sex":2,"cancel":1,"reg":"","desc":"","addr":"","like_notice":1,"comment_notice":2,"is_vip":2,"vip_expire":null,"wallet_token":0,"fan_number":6,"follow_number":0,"level":1,"age":0,"is_person_follow":false,"pivot":{"likeable_id":3,"liker_id":6,"likeable_type":"App\\Tourist","liker_type":"App\\Tourist","created_at":"2020-06-05T08:03:11.000000Z","updated_at":"2020-06-05T08:03:11.000000Z"}},{"id":3,"name":"信用度","phone":"13521614827","avatar":"upload/20200606024929Gkda2.jpg","password":"$2y$10$CGTViSVDrLGfX1NsOhiHSeLhXdZxdidRYCd3U/b1c3U78y31EBjt6","remember_token":null,"created_at":"2020-05-30 12:08:57","updated_at":"2020-06-14 09:51:04","tourist_id":"52185256","sex":1,"cancel":1,"reg":"","desc":"这个可以有","addr":"北京市 海淀区","like_notice":1,"comment_notice":1,"is_vip":2,"vip_expire":null,"wallet_token":0,"fan_number":17,"follow_number":23,"level":1,"age":0,"is_person_follow":false,"pivot":{"likeable_id":3,"liker_id":3,"likeable_type":"App\\Tourist","liker_type":"App\\Tourist","created_at":"2020-06-14T01:48:01.000000Z","updated_at":"2020-06-14T01:48:01.000000Z"}}],"first_page_url":"http://enjoy.fengyunguoyuan.com/api/personInform/fans?page=1","from":1,"last_page":1,"last_page_url":"http://enjoy.fengyunguoyuan.com/api/personInform/fans?page=1","next_page_url":null,"path":"http://enjoy.fengyunguoyuan.com/api/personInform/fans","per_page":"10","prev_page_url":null,"to":2,"total":2}
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
         * current_page : 1
         * data : [{"id":6,"name":"青春有你","phone":"18303032844","avatar":"upload/20200606055631F8mzM.jpg","password":"$2y$10$Bl9QrPD7oLzkCXSb9ZwcLOzIoW4NxGxOHqQLRckkZ.XYaWv0Aq2PC","remember_token":null,"created_at":"2020-06-05 16:02:31","updated_at":"2020-06-14 09:51:04","tourist_id":"27915679","sex":2,"cancel":1,"reg":"","desc":"","addr":"","like_notice":1,"comment_notice":2,"is_vip":2,"vip_expire":null,"wallet_token":0,"fan_number":6,"follow_number":0,"level":1,"age":0,"is_person_follow":false,"pivot":{"likeable_id":3,"liker_id":6,"likeable_type":"App\\Tourist","liker_type":"App\\Tourist","created_at":"2020-06-05T08:03:11.000000Z","updated_at":"2020-06-05T08:03:11.000000Z"}},{"id":3,"name":"信用度","phone":"13521614827","avatar":"upload/20200606024929Gkda2.jpg","password":"$2y$10$CGTViSVDrLGfX1NsOhiHSeLhXdZxdidRYCd3U/b1c3U78y31EBjt6","remember_token":null,"created_at":"2020-05-30 12:08:57","updated_at":"2020-06-14 09:51:04","tourist_id":"52185256","sex":1,"cancel":1,"reg":"","desc":"这个可以有","addr":"北京市 海淀区","like_notice":1,"comment_notice":1,"is_vip":2,"vip_expire":null,"wallet_token":0,"fan_number":17,"follow_number":23,"level":1,"age":0,"is_person_follow":false,"pivot":{"likeable_id":3,"liker_id":3,"likeable_type":"App\\Tourist","liker_type":"App\\Tourist","created_at":"2020-06-14T01:48:01.000000Z","updated_at":"2020-06-14T01:48:01.000000Z"}}]
         * first_page_url : http://enjoy.fengyunguoyuan.com/api/personInform/fans?page=1
         * from : 1
         * last_page : 1
         * last_page_url : http://enjoy.fengyunguoyuan.com/api/personInform/fans?page=1
         * next_page_url : null
         * path : http://enjoy.fengyunguoyuan.com/api/personInform/fans
         * per_page : 10
         * prev_page_url : null
         * to : 2
         * total : 2
         */

        private int current_page;
        private String first_page_url;
        private int from;
        private int last_page;
        private String last_page_url;
        private Object next_page_url;
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

        public Object getNext_page_url() {
            return next_page_url;
        }

        public void setNext_page_url(Object next_page_url) {
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
             * id : 6
             * name : 青春有你
             * phone : 18303032844
             * avatar : upload/20200606055631F8mzM.jpg
             * password : $2y$10$Bl9QrPD7oLzkCXSb9ZwcLOzIoW4NxGxOHqQLRckkZ.XYaWv0Aq2PC
             * remember_token : null
             * created_at : 2020-06-05 16:02:31
             * updated_at : 2020-06-14 09:51:04
             * tourist_id : 27915679
             * sex : 2
             * cancel : 1
             * reg :
             * desc :
             * addr :
             * like_notice : 1
             * comment_notice : 2
             * is_vip : 2
             * vip_expire : null
             * wallet_token : 0
             * fan_number : 6
             * follow_number : 0
             * level : 1
             * age : 0
             * is_person_follow : false
             * pivot : {"likeable_id":3,"liker_id":6,"likeable_type":"App\\Tourist","liker_type":"App\\Tourist","created_at":"2020-06-05T08:03:11.000000Z","updated_at":"2020-06-05T08:03:11.000000Z"}
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
            private boolean is_person_follow;
            private PivotBean pivot;
            private String yunxin_token;
            private String yunxin_accid;

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

            public boolean isIs_person_follow() {
                return is_person_follow;
            }

            public void setIs_person_follow(boolean is_person_follow) {
                this.is_person_follow = is_person_follow;
            }

            public PivotBean getPivot() {
                return pivot;
            }

            public void setPivot(PivotBean pivot) {
                this.pivot = pivot;
            }

            public static class PivotBean {
                /**
                 * likeable_id : 3
                 * liker_id : 6
                 * likeable_type : App\Tourist
                 * liker_type : App\Tourist
                 * created_at : 2020-06-05T08:03:11.000000Z
                 * updated_at : 2020-06-05T08:03:11.000000Z
                 */

                private int likeable_id;
                private int liker_id;
                private String likeable_type;
                private String liker_type;
                private String created_at;
                private String updated_at;

                public int getLikeable_id() {
                    return likeable_id;
                }

                public void setLikeable_id(int likeable_id) {
                    this.likeable_id = likeable_id;
                }

                public int getLiker_id() {
                    return liker_id;
                }

                public void setLiker_id(int liker_id) {
                    this.liker_id = liker_id;
                }

                public String getLikeable_type() {
                    return likeable_type;
                }

                public void setLikeable_type(String likeable_type) {
                    this.likeable_type = likeable_type;
                }

                public String getLiker_type() {
                    return liker_type;
                }

                public void setLiker_type(String liker_type) {
                    this.liker_type = liker_type;
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
            }
        }
    }
}
