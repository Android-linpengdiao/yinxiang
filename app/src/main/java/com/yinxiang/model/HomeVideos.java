package com.yinxiang.model;

import java.io.Serializable;
import java.util.List;

public class HomeVideos implements Serializable {

    /**
     * code : 200
     * msg : 成功
     * data : {"current_page":1,"data":[{"id":2,"created_at":"2020-05-25T09:17:54.000000Z","updated_at":"2020-05-25T09:17:54.000000Z","is_deleted":2,"tourist_id":1,"tourist_name":"测试","active_id":1,"active_name":"这就是街舞3","video":"upload/20200525120121QkKOt.jpg","img":null,"recommend":2,"pre_votes":0,"final_votes":0,"rematch_votes":0,"club_id":2,"club_name":"街舞社团","assist_num":0,"comment_num":0,"name":""}],"first_page_url":"http://enjoy.fengyunguoyuan.com/api/homepageVideos/active?page=1","from":1,"last_page":1,"last_page_url":"http://enjoy.fengyunguoyuan.com/api/homepageVideos/active?page=1","next_page_url":null,"path":"http://enjoy.fengyunguoyuan.com/api/homepageVideos/active","per_page":"10","prev_page_url":null,"to":1,"total":1}
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
         * data : [{"id":2,"created_at":"2020-05-25T09:17:54.000000Z","updated_at":"2020-05-25T09:17:54.000000Z","is_deleted":2,"tourist_id":1,"tourist_name":"测试","active_id":1,"active_name":"这就是街舞3","video":"upload/20200525120121QkKOt.jpg","img":null,"recommend":2,"pre_votes":0,"final_votes":0,"rematch_votes":0,"club_id":2,"club_name":"街舞社团","assist_num":0,"comment_num":0,"name":""}]
         * first_page_url : http://enjoy.fengyunguoyuan.com/api/homepageVideos/active?page=1
         * from : 1
         * last_page : 1
         * last_page_url : http://enjoy.fengyunguoyuan.com/api/homepageVideos/active?page=1
         * next_page_url : null
         * path : http://enjoy.fengyunguoyuan.com/api/homepageVideos/active
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
             * id : 2
             * created_at : 2020-05-25T09:17:54.000000Z
             * updated_at : 2020-05-25T09:17:54.000000Z
             * is_deleted : 2
             * tourist_id : 1
             * tourist_name : 测试
             * active_id : 1
             * active_name : 这就是街舞3
             * video : upload/20200525120121QkKOt.jpg
             * img : null
             * recommend : 2
             * pre_votes : 0
             * final_votes : 0
             * rematch_votes : 0
             * club_id : 2
             * club_name : 街舞社团
             * assist_num : 0
             * comment_num : 0
             * name :
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
            private Object img;
            private int recommend;
            private int pre_votes;
            private int final_votes;
            private int rematch_votes;
            private int club_id;
            private String club_name;
            private int assist_num;
            private int comment_num;
            private String name;

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

            public Object getImg() {
                return img;
            }

            public void setImg(Object img) {
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
        }
    }
}
