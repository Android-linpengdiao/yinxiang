package com.yinxiang.model;

import java.util.List;

public class LikeData {

    /**
     * code : 200
     * msg : 成功
     * data : [{"id":2,"content_id":2,"tourist_id":5,"created_at":"2020-03-24 21:34:33","updated_at":"2020-03-24 21:34:33","tourist":{"id":5,"name":"8Zbu5V","phone":"18515692029","avatar":"users/default.png","password":"$2y$10$bjsecx2Rn4Jh9o8gJoUf3eO.jiZB6o5W1XAJOqFWDxUTw/FYZPSm.","remember_token":null,"settings":null,"created_at":"2020-03-24 20:32:27","updated_at":"2020-03-24 20:32:27","tourist_id":"92859738","sex":1,"birth":null,"openid":"oH48X03285sQPqnnRy2GYOKdYukc","headimgurl":null,"city":null,"province":null,"cancel":1,"autograph":null,"weibo":null,"reg":"","qq_id":null,"weibo_id":null,"liker":0,"followers":0,"comment":0},"content":{"id":2,"created_at":"2020-03-24 21:09:50","updated_at":"2020-04-08 15:14:40","status":1,"tourist_id":3,"nav_id":5,"nav_name":"故事","desc":"号","link":"[{\"download_link\":\"https:\\/\\/diandou-test.oss-cn-beijing.aliyuncs.com\\/3202032421947storageemulated0Androiddatacom.diandoufilesJCameravideo_1585055288786.mp4\",\"original_name\":\"3202032421947storageemulated0Androiddatacom.diandoufilesJCameravideo_1585055288786.mp4\"}]","tourist_name":"xu","addr":"","play_time":22,"assist":1,"img":"upload/20200324090949oXs7D.jpg","hot":2,"recommend":2}}]
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
         * id : 2
         * content_id : 2
         * tourist_id : 5
         * created_at : 2020-03-24 21:34:33
         * updated_at : 2020-03-24 21:34:33
         * tourist : {"id":5,"name":"8Zbu5V","phone":"18515692029","avatar":"users/default.png","password":"$2y$10$bjsecx2Rn4Jh9o8gJoUf3eO.jiZB6o5W1XAJOqFWDxUTw/FYZPSm.","remember_token":null,"settings":null,"created_at":"2020-03-24 20:32:27","updated_at":"2020-03-24 20:32:27","tourist_id":"92859738","sex":1,"birth":null,"openid":"oH48X03285sQPqnnRy2GYOKdYukc","headimgurl":null,"city":null,"province":null,"cancel":1,"autograph":null,"weibo":null,"reg":"","qq_id":null,"weibo_id":null,"liker":0,"followers":0,"comment":0}
         * content : {"id":2,"created_at":"2020-03-24 21:09:50","updated_at":"2020-04-08 15:14:40","status":1,"tourist_id":3,"nav_id":5,"nav_name":"故事","desc":"号","link":"[{\"download_link\":\"https:\\/\\/diandou-test.oss-cn-beijing.aliyuncs.com\\/3202032421947storageemulated0Androiddatacom.diandoufilesJCameravideo_1585055288786.mp4\",\"original_name\":\"3202032421947storageemulated0Androiddatacom.diandoufilesJCameravideo_1585055288786.mp4\"}]","tourist_name":"xu","addr":"","play_time":22,"assist":1,"img":"upload/20200324090949oXs7D.jpg","hot":2,"recommend":2}
         */

        private int id;
        private int content_id;
        private int tourist_id;
        private String created_at;
        private String updated_at;
        private TouristBean tourist;
        private ContentBean content;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getContent_id() {
            return content_id;
        }

        public void setContent_id(int content_id) {
            this.content_id = content_id;
        }

        public int getTourist_id() {
            return tourist_id;
        }

        public void setTourist_id(int tourist_id) {
            this.tourist_id = tourist_id;
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

        public TouristBean getTourist() {
            return tourist;
        }

        public void setTourist(TouristBean tourist) {
            this.tourist = tourist;
        }

        public ContentBean getContent() {
            return content;
        }

        public void setContent(ContentBean content) {
            this.content = content;
        }

        public static class TouristBean {
            /**
             * id : 5
             * name : 8Zbu5V
             * phone : 18515692029
             * avatar : users/default.png
             * password : $2y$10$bjsecx2Rn4Jh9o8gJoUf3eO.jiZB6o5W1XAJOqFWDxUTw/FYZPSm.
             * remember_token : null
             * settings : null
             * created_at : 2020-03-24 20:32:27
             * updated_at : 2020-03-24 20:32:27
             * tourist_id : 92859738
             * sex : 1
             * birth : null
             * openid : oH48X03285sQPqnnRy2GYOKdYukc
             * headimgurl : null
             * city : null
             * province : null
             * cancel : 1
             * autograph : null
             * weibo : null
             * reg :
             * qq_id : null
             * weibo_id : null
             * liker : 0
             * followers : 0
             * comment : 0
             */

            private int id;
            private String name;
            private String phone;
            private String avatar;
            private String password;
            private Object remember_token;
            private Object settings;
            private String created_at;
            private String updated_at;
            private String tourist_id;
            private int sex;
            private Object birth;
            private String openid;
            private Object headimgurl;
            private Object city;
            private Object province;
            private int cancel;
            private Object autograph;
            private Object weibo;
            private String reg;
            private Object qq_id;
            private Object weibo_id;
            private int liker;
            private int followers;
            private int comment;

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

            public Object getSettings() {
                return settings;
            }

            public void setSettings(Object settings) {
                this.settings = settings;
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

            public Object getBirth() {
                return birth;
            }

            public void setBirth(Object birth) {
                this.birth = birth;
            }

            public String getOpenid() {
                return openid;
            }

            public void setOpenid(String openid) {
                this.openid = openid;
            }

            public Object getHeadimgurl() {
                return headimgurl;
            }

            public void setHeadimgurl(Object headimgurl) {
                this.headimgurl = headimgurl;
            }

            public Object getCity() {
                return city;
            }

            public void setCity(Object city) {
                this.city = city;
            }

            public Object getProvince() {
                return province;
            }

            public void setProvince(Object province) {
                this.province = province;
            }

            public int getCancel() {
                return cancel;
            }

            public void setCancel(int cancel) {
                this.cancel = cancel;
            }

            public Object getAutograph() {
                return autograph;
            }

            public void setAutograph(Object autograph) {
                this.autograph = autograph;
            }

            public Object getWeibo() {
                return weibo;
            }

            public void setWeibo(Object weibo) {
                this.weibo = weibo;
            }

            public String getReg() {
                return reg;
            }

            public void setReg(String reg) {
                this.reg = reg;
            }

            public Object getQq_id() {
                return qq_id;
            }

            public void setQq_id(Object qq_id) {
                this.qq_id = qq_id;
            }

            public Object getWeibo_id() {
                return weibo_id;
            }

            public void setWeibo_id(Object weibo_id) {
                this.weibo_id = weibo_id;
            }

            public int getLiker() {
                return liker;
            }

            public void setLiker(int liker) {
                this.liker = liker;
            }

            public int getFollowers() {
                return followers;
            }

            public void setFollowers(int followers) {
                this.followers = followers;
            }

            public int getComment() {
                return comment;
            }

            public void setComment(int comment) {
                this.comment = comment;
            }
        }

        public static class ContentBean {
            /**
             * id : 2
             * created_at : 2020-03-24 21:09:50
             * updated_at : 2020-04-08 15:14:40
             * status : 1
             * tourist_id : 3
             * nav_id : 5
             * nav_name : 故事
             * desc : 号
             * link : [{"download_link":"https:\/\/diandou-test.oss-cn-beijing.aliyuncs.com\/3202032421947storageemulated0Androiddatacom.diandoufilesJCameravideo_1585055288786.mp4","original_name":"3202032421947storageemulated0Androiddatacom.diandoufilesJCameravideo_1585055288786.mp4"}]
             * tourist_name : xu
             * addr :
             * play_time : 22
             * assist : 1
             * img : upload/20200324090949oXs7D.jpg
             * hot : 2
             * recommend : 2
             */

            private int id;
            private String created_at;
            private String updated_at;
            private int status;
            private int tourist_id;
            private int nav_id;
            private String nav_name;
            private String desc;
            private String link;
            private String tourist_name;
            private String addr;
            private int play_time;
            private int assist;
            private String img;
            private int hot;
            private int recommend;

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

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getTourist_id() {
                return tourist_id;
            }

            public void setTourist_id(int tourist_id) {
                this.tourist_id = tourist_id;
            }

            public int getNav_id() {
                return nav_id;
            }

            public void setNav_id(int nav_id) {
                this.nav_id = nav_id;
            }

            public String getNav_name() {
                return nav_name;
            }

            public void setNav_name(String nav_name) {
                this.nav_name = nav_name;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getTourist_name() {
                return tourist_name;
            }

            public void setTourist_name(String tourist_name) {
                this.tourist_name = tourist_name;
            }

            public String getAddr() {
                return addr;
            }

            public void setAddr(String addr) {
                this.addr = addr;
            }

            public int getPlay_time() {
                return play_time;
            }

            public void setPlay_time(int play_time) {
                this.play_time = play_time;
            }

            public int getAssist() {
                return assist;
            }

            public void setAssist(int assist) {
                this.assist = assist;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getHot() {
                return hot;
            }

            public void setHot(int hot) {
                this.hot = hot;
            }

            public int getRecommend() {
                return recommend;
            }

            public void setRecommend(int recommend) {
                this.recommend = recommend;
            }
        }
    }
}
