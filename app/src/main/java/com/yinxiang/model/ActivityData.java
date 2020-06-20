package com.yinxiang.model;

import java.io.Serializable;
import java.util.List;

public class ActivityData implements Serializable{

    /**
     * code : 200
     * msg : 成功
     * data : {"current_page":1,"data":[{"id":17,"created_at":"2020-06-13 22:42:14","updated_at":"2020-06-14 23:39:29","is_deleted":2,"tourist_id":6,"tourist_name":"青春有你","active_id":2,"active_name":"舞蹈大赛","video":"http://api.lgdama.com:10001/storage/video/f274e63c595449aea8b0da7c03e55fbf.mp4","img":"http://www.udiandou.com/upload/20200603124338REaoe.jpg","recommend":2,"pre_votes":11,"final_votes":0,"rematch_votes":0,"club_id":8,"club_name":"青春有你","assist_num":1,"comment_num":0,"name":"青春有你！","play_num":0,"status":1,"active_detail":{"id":2,"created_at":"2020-05-31 12:58:00","updated_at":"2020-06-14 11:24:54","title":"舞蹈大赛","desc":"<p>《这！就是街舞》是优酷推出的街舞选拔类真人秀。节目通过&ldquo;明星导师+专业舞者真人秀&rdquo;的模式，采取个人选拔、团队作战的表演方式，在四位队长的带领下组成四支战队，进行团队间的群舞Battle，最终产生总冠军。<\/p>\r\n<p>《这！就是街舞》包括：《这！就是街舞第一季》、《这！就是街舞第二季》第一季由易烊千玺、罗志祥、韩庚、黄子韬担任明星队长，于2018年2月24日起每周六20:00在优酷首播，于2018年5月13日收官，易烊千玺战队成员韩宇获得总冠军。第二季由由易烊千玺、罗志祥、韩庚、吴建豪担任明星队长，于2019年5月18日起在优酷首播。<\/p>","video":"[{\"download_link\":\"active-categories\\/May2020\\/faBWzSrVaQwb59DlsTrs.mp4\",\"original_name\":\"test.mp4\"}]","pre_start_time":"2020-05-31 00:00:00","rematch_start_time":"2020-06-01 00:00:00","final_start_time":"2020-07-01 00:00:00","status":1,"pre_end_time":"2020-05-31 00:00:00","rematch_end_time":"2020-07-01 00:00:00","final_end_time":"2020-07-04 00:00:00","img":null},"tourist":{"id":6,"name":"青春有你","avatar":"upload/20200606055631F8mzM.jpg"}}],"first_page_url":"http://enjoy.fengyunguoyuan.com/api/personInform/active?page=1","from":1,"last_page":1,"last_page_url":"http://enjoy.fengyunguoyuan.com/api/personInform/active?page=1","next_page_url":null,"path":"http://enjoy.fengyunguoyuan.com/api/personInform/active","per_page":10,"prev_page_url":null,"to":1,"total":1}
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

    public static class DataBeanX implements Serializable{
        /**
         * current_page : 1
         * data : [{"id":17,"created_at":"2020-06-13 22:42:14","updated_at":"2020-06-14 23:39:29","is_deleted":2,"tourist_id":6,"tourist_name":"青春有你","active_id":2,"active_name":"舞蹈大赛","video":"http://api.lgdama.com:10001/storage/video/f274e63c595449aea8b0da7c03e55fbf.mp4","img":"http://www.udiandou.com/upload/20200603124338REaoe.jpg","recommend":2,"pre_votes":11,"final_votes":0,"rematch_votes":0,"club_id":8,"club_name":"青春有你","assist_num":1,"comment_num":0,"name":"青春有你！","play_num":0,"status":1,"active_detail":{"id":2,"created_at":"2020-05-31 12:58:00","updated_at":"2020-06-14 11:24:54","title":"舞蹈大赛","desc":"<p>《这！就是街舞》是优酷推出的街舞选拔类真人秀。节目通过&ldquo;明星导师+专业舞者真人秀&rdquo;的模式，采取个人选拔、团队作战的表演方式，在四位队长的带领下组成四支战队，进行团队间的群舞Battle，最终产生总冠军。<\/p>\r\n<p>《这！就是街舞》包括：《这！就是街舞第一季》、《这！就是街舞第二季》第一季由易烊千玺、罗志祥、韩庚、黄子韬担任明星队长，于2018年2月24日起每周六20:00在优酷首播，于2018年5月13日收官，易烊千玺战队成员韩宇获得总冠军。第二季由由易烊千玺、罗志祥、韩庚、吴建豪担任明星队长，于2019年5月18日起在优酷首播。<\/p>","video":"[{\"download_link\":\"active-categories\\/May2020\\/faBWzSrVaQwb59DlsTrs.mp4\",\"original_name\":\"test.mp4\"}]","pre_start_time":"2020-05-31 00:00:00","rematch_start_time":"2020-06-01 00:00:00","final_start_time":"2020-07-01 00:00:00","status":1,"pre_end_time":"2020-05-31 00:00:00","rematch_end_time":"2020-07-01 00:00:00","final_end_time":"2020-07-04 00:00:00","img":null},"tourist":{"id":6,"name":"青春有你","avatar":"upload/20200606055631F8mzM.jpg"}}]
         * first_page_url : http://enjoy.fengyunguoyuan.com/api/personInform/active?page=1
         * from : 1
         * last_page : 1
         * last_page_url : http://enjoy.fengyunguoyuan.com/api/personInform/active?page=1
         * next_page_url : null
         * path : http://enjoy.fengyunguoyuan.com/api/personInform/active
         * per_page : 10
         * prev_page_url : null
         * to : 1
         * total : 1
         */

        private int current_page;
        private String first_page_url;
        private int from;
        private int last_page;
        private String last_page_url;
        private Object next_page_url;
        private String path;
        private int per_page;
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

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
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

        public static class DataBean implements Serializable {
            /**
             * id : 17
             * created_at : 2020-06-13 22:42:14
             * updated_at : 2020-06-14 23:39:29
             * is_deleted : 2
             * tourist_id : 6
             * tourist_name : 青春有你
             * active_id : 2
             * active_name : 舞蹈大赛
             * video : http://api.lgdama.com:10001/storage/video/f274e63c595449aea8b0da7c03e55fbf.mp4
             * img : http://www.udiandou.com/upload/20200603124338REaoe.jpg
             * recommend : 2
             * pre_votes : 11
             * final_votes : 0
             * rematch_votes : 0
             * club_id : 8
             * club_name : 青春有你
             * assist_num : 1
             * comment_num : 0
             * name : 青春有你！
             * play_num : 0
             * status : 1
             * active_detail : {"id":2,"created_at":"2020-05-31 12:58:00","updated_at":"2020-06-14 11:24:54","title":"舞蹈大赛","desc":"<p>《这！就是街舞》是优酷推出的街舞选拔类真人秀。节目通过&ldquo;明星导师+专业舞者真人秀&rdquo;的模式，采取个人选拔、团队作战的表演方式，在四位队长的带领下组成四支战队，进行团队间的群舞Battle，最终产生总冠军。<\/p>\r\n<p>《这！就是街舞》包括：《这！就是街舞第一季》、《这！就是街舞第二季》第一季由易烊千玺、罗志祥、韩庚、黄子韬担任明星队长，于2018年2月24日起每周六20:00在优酷首播，于2018年5月13日收官，易烊千玺战队成员韩宇获得总冠军。第二季由由易烊千玺、罗志祥、韩庚、吴建豪担任明星队长，于2019年5月18日起在优酷首播。<\/p>","video":"[{\"download_link\":\"active-categories\\/May2020\\/faBWzSrVaQwb59DlsTrs.mp4\",\"original_name\":\"test.mp4\"}]","pre_start_time":"2020-05-31 00:00:00","rematch_start_time":"2020-06-01 00:00:00","final_start_time":"2020-07-01 00:00:00","status":1,"pre_end_time":"2020-05-31 00:00:00","rematch_end_time":"2020-07-01 00:00:00","final_end_time":"2020-07-04 00:00:00","img":null}
             * tourist : {"id":6,"name":"青春有你","avatar":"upload/20200606055631F8mzM.jpg"}
             */

            private int id;
            private String created_at;
            private String updated_at;
            private int is_deleted;
            private int tourist_id;
            private String tourist_name;
            private int active_id;
            private String active_name;
            private String video;
            private String img;
            private int recommend;
            private int pre_votes;
            private int final_votes;
            private int rematch_votes;
            private int club_id;
            private String club_name;
            private int assist_num;
            private int comment_num;
            private String name;
            private int play_num;
            private int status;
            private ActiveDetailBean active_detail;
            private TouristBean tourist;

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

            public int getIs_deleted() {
                return is_deleted;
            }

            public void setIs_deleted(int is_deleted) {
                this.is_deleted = is_deleted;
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

            public int getActive_id() {
                return active_id;
            }

            public void setActive_id(int active_id) {
                this.active_id = active_id;
            }

            public String getActive_name() {
                return active_name;
            }

            public void setActive_name(String active_name) {
                this.active_name = active_name;
            }

            public String getVideo() {
                return video;
            }

            public void setVideo(String video) {
                this.video = video;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getRecommend() {
                return recommend;
            }

            public void setRecommend(int recommend) {
                this.recommend = recommend;
            }

            public int getPre_votes() {
                return pre_votes;
            }

            public void setPre_votes(int pre_votes) {
                this.pre_votes = pre_votes;
            }

            public int getFinal_votes() {
                return final_votes;
            }

            public void setFinal_votes(int final_votes) {
                this.final_votes = final_votes;
            }

            public int getRematch_votes() {
                return rematch_votes;
            }

            public void setRematch_votes(int rematch_votes) {
                this.rematch_votes = rematch_votes;
            }

            public int getClub_id() {
                return club_id;
            }

            public void setClub_id(int club_id) {
                this.club_id = club_id;
            }

            public String getClub_name() {
                return club_name;
            }

            public void setClub_name(String club_name) {
                this.club_name = club_name;
            }

            public int getAssist_num() {
                return assist_num;
            }

            public void setAssist_num(int assist_num) {
                this.assist_num = assist_num;
            }

            public int getComment_num() {
                return comment_num;
            }

            public void setComment_num(int comment_num) {
                this.comment_num = comment_num;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getPlay_num() {
                return play_num;
            }

            public void setPlay_num(int play_num) {
                this.play_num = play_num;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public ActiveDetailBean getActive_detail() {
                return active_detail;
            }

            public void setActive_detail(ActiveDetailBean active_detail) {
                this.active_detail = active_detail;
            }

            public TouristBean getTourist() {
                return tourist;
            }

            public void setTourist(TouristBean tourist) {
                this.tourist = tourist;
            }

            public static class ActiveDetailBean {
                /**
                 * id : 2
                 * created_at : 2020-05-31 12:58:00
                 * updated_at : 2020-06-14 11:24:54
                 * title : 舞蹈大赛
                 * desc : <p>《这！就是街舞》是优酷推出的街舞选拔类真人秀。节目通过&ldquo;明星导师+专业舞者真人秀&rdquo;的模式，采取个人选拔、团队作战的表演方式，在四位队长的带领下组成四支战队，进行团队间的群舞Battle，最终产生总冠军。</p>
                 <p>《这！就是街舞》包括：《这！就是街舞第一季》、《这！就是街舞第二季》第一季由易烊千玺、罗志祥、韩庚、黄子韬担任明星队长，于2018年2月24日起每周六20:00在优酷首播，于2018年5月13日收官，易烊千玺战队成员韩宇获得总冠军。第二季由由易烊千玺、罗志祥、韩庚、吴建豪担任明星队长，于2019年5月18日起在优酷首播。</p>
                 * video : [{"download_link":"active-categories\/May2020\/faBWzSrVaQwb59DlsTrs.mp4","original_name":"test.mp4"}]
                 * pre_start_time : 2020-05-31 00:00:00
                 * rematch_start_time : 2020-06-01 00:00:00
                 * final_start_time : 2020-07-01 00:00:00
                 * status : 1
                 * pre_end_time : 2020-05-31 00:00:00
                 * rematch_end_time : 2020-07-01 00:00:00
                 * final_end_time : 2020-07-04 00:00:00
                 * img : null
                 */

                private int id;
                private String created_at;
                private String updated_at;
                private String title;
                private String desc;
                private String video;
                private String pre_start_time;
                private String rematch_start_time;
                private String final_start_time;
                private int status;
                private String pre_end_time;
                private String rematch_end_time;
                private String final_end_time;
                private Object img;

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

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }

                public String getVideo() {
                    return video;
                }

                public void setVideo(String video) {
                    this.video = video;
                }

                public String getPre_start_time() {
                    return pre_start_time;
                }

                public void setPre_start_time(String pre_start_time) {
                    this.pre_start_time = pre_start_time;
                }

                public String getRematch_start_time() {
                    return rematch_start_time;
                }

                public void setRematch_start_time(String rematch_start_time) {
                    this.rematch_start_time = rematch_start_time;
                }

                public String getFinal_start_time() {
                    return final_start_time;
                }

                public void setFinal_start_time(String final_start_time) {
                    this.final_start_time = final_start_time;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getPre_end_time() {
                    return pre_end_time;
                }

                public void setPre_end_time(String pre_end_time) {
                    this.pre_end_time = pre_end_time;
                }

                public String getRematch_end_time() {
                    return rematch_end_time;
                }

                public void setRematch_end_time(String rematch_end_time) {
                    this.rematch_end_time = rematch_end_time;
                }

                public String getFinal_end_time() {
                    return final_end_time;
                }

                public void setFinal_end_time(String final_end_time) {
                    this.final_end_time = final_end_time;
                }

                public Object getImg() {
                    return img;
                }

                public void setImg(Object img) {
                    this.img = img;
                }
            }

            public static class TouristBean {
                /**
                 * id : 6
                 * name : 青春有你
                 * avatar : upload/20200606055631F8mzM.jpg
                 */

                private int id;
                private String name;
                private String avatar;

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

                public String getAvatar() {
                    return avatar;
                }

                public void setAvatar(String avatar) {
                    this.avatar = avatar;
                }
            }
        }
    }
}
