package com.yinxiang.model;

import java.util.List;

public class WorkPKData {

    /**
     * code : 200
     * msg : 成功
     * data : {"current_page":1,"data":[{"id":9,"created_at":"2020-06-15 22:27:35","updated_at":"2020-06-15 22:27:35","tourist_id":3,"content_id":16,"compare_content_id":17,"vote_num":0,"compare_num":0,"content_video":"http://api.lgdama.com:10001/storage/video/ff8545ed50474b0ea68cc2d1622e8a08.mp4","compare_video":"http://api.lgdama.com:10001/storage/video/f274e63c595449aea8b0da7c03e55fbf.mp4","status":1,"active_id":1,"active_name":"这就是街舞3","compare_active_id":2,"compare_active_name":"舞蹈大赛","ended_at":"2020-06-15 22:47:35","content_name":"","content_img":"","compare_name":"","compare_img":""}],"first_page_url":"http://enjoy.fengyunguoyuan.com/api/homepageVideos/selfPk?page=1","from":1,"last_page":1,"last_page_url":"http://enjoy.fengyunguoyuan.com/api/homepageVideos/selfPk?page=1","next_page_url":null,"path":"http://enjoy.fengyunguoyuan.com/api/homepageVideos/selfPk","per_page":"10","prev_page_url":null,"to":1,"total":1}
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
         * data : [{"id":9,"created_at":"2020-06-15 22:27:35","updated_at":"2020-06-15 22:27:35","tourist_id":3,"content_id":16,"compare_content_id":17,"vote_num":0,"compare_num":0,"content_video":"http://api.lgdama.com:10001/storage/video/ff8545ed50474b0ea68cc2d1622e8a08.mp4","compare_video":"http://api.lgdama.com:10001/storage/video/f274e63c595449aea8b0da7c03e55fbf.mp4","status":1,"active_id":1,"active_name":"这就是街舞3","compare_active_id":2,"compare_active_name":"舞蹈大赛","ended_at":"2020-06-15 22:47:35","content_name":"","content_img":"","compare_name":"","compare_img":""}]
         * first_page_url : http://enjoy.fengyunguoyuan.com/api/homepageVideos/selfPk?page=1
         * from : 1
         * last_page : 1
         * last_page_url : http://enjoy.fengyunguoyuan.com/api/homepageVideos/selfPk?page=1
         * next_page_url : null
         * path : http://enjoy.fengyunguoyuan.com/api/homepageVideos/selfPk
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
             * id : 9
             * created_at : 2020-06-15 22:27:35
             * updated_at : 2020-06-15 22:27:35
             * tourist_id : 3
             * content_id : 16
             * compare_content_id : 17
             * vote_num : 0
             * compare_num : 0
             * content_video : http://api.lgdama.com:10001/storage/video/ff8545ed50474b0ea68cc2d1622e8a08.mp4
             * compare_video : http://api.lgdama.com:10001/storage/video/f274e63c595449aea8b0da7c03e55fbf.mp4
             * status : 1
             * active_id : 1
             * active_name : 这就是街舞3
             * compare_active_id : 2
             * compare_active_name : 舞蹈大赛
             * ended_at : 2020-06-15 22:47:35
             * content_name :
             * content_img :
             * compare_name :
             * compare_img :
             */

            private int id;
            private String created_at;
            private String updated_at;
            private int tourist_id;
            private int content_id;
            private int compare_content_id;
            private int vote_num;
            private int compare_num;
            private String content_video;
            private String compare_video;
            private int status;
            private int active_id;
            private String active_name;
            private int compare_active_id;
            private String compare_active_name;
            private String ended_at;
            private String content_name;
            private String content_img;
            private String compare_name;
            private String compare_img;

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

            public int getTourist_id() {
                return tourist_id;
            }

            public void setTourist_id(int tourist_id) {
                this.tourist_id = tourist_id;
            }

            public int getContent_id() {
                return content_id;
            }

            public void setContent_id(int content_id) {
                this.content_id = content_id;
            }

            public int getCompare_content_id() {
                return compare_content_id;
            }

            public void setCompare_content_id(int compare_content_id) {
                this.compare_content_id = compare_content_id;
            }

            public int getVote_num() {
                return vote_num;
            }

            public void setVote_num(int vote_num) {
                this.vote_num = vote_num;
            }

            public int getCompare_num() {
                return compare_num;
            }

            public void setCompare_num(int compare_num) {
                this.compare_num = compare_num;
            }

            public String getContent_video() {
                return content_video;
            }

            public void setContent_video(String content_video) {
                this.content_video = content_video;
            }

            public String getCompare_video() {
                return compare_video;
            }

            public void setCompare_video(String compare_video) {
                this.compare_video = compare_video;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
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

            public int getCompare_active_id() {
                return compare_active_id;
            }

            public void setCompare_active_id(int compare_active_id) {
                this.compare_active_id = compare_active_id;
            }

            public String getCompare_active_name() {
                return compare_active_name;
            }

            public void setCompare_active_name(String compare_active_name) {
                this.compare_active_name = compare_active_name;
            }

            public String getEnded_at() {
                return ended_at;
            }

            public void setEnded_at(String ended_at) {
                this.ended_at = ended_at;
            }

            public String getContent_name() {
                return content_name;
            }

            public void setContent_name(String content_name) {
                this.content_name = content_name;
            }

            public String getContent_img() {
                return content_img;
            }

            public void setContent_img(String content_img) {
                this.content_img = content_img;
            }

            public String getCompare_name() {
                return compare_name;
            }

            public void setCompare_name(String compare_name) {
                this.compare_name = compare_name;
            }

            public String getCompare_img() {
                return compare_img;
            }

            public void setCompare_img(String compare_img) {
                this.compare_img = compare_img;
            }
        }
    }
}
