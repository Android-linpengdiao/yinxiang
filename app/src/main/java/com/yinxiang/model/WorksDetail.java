package com.yinxiang.model;

public class WorksDetail {

    /**
     * code : 200
     * msg : 成功
     * data : {"id":15,"created_at":"2020-06-13 12:49:37","updated_at":"2020-06-13 12:49:37","is_deleted":2,"tourist_id":6,"tourist_name":"青春有你","active_id":2,"active_name":"舞蹈大赛","video":"http://api.lgdama.com:10001/storage/video/ff8545ed50474b0ea68cc2d1622e8a08.mp4","img":"http://enjoy.fengyunguoyuan.com/upload/20200606052833CLfTm.jpg","recommend":2,"pre_votes":0,"final_votes":0,"rematch_votes":0,"club_id":8,"club_name":"青春有你","assist_num":0,"comment_num":0,"name":""}
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

    public static class DataBean {
        /**
         * id : 15
         * created_at : 2020-06-13 12:49:37
         * updated_at : 2020-06-13 12:49:37
         * is_deleted : 2
         * tourist_id : 6
         * tourist_name : 青春有你
         * active_id : 2
         * active_name : 舞蹈大赛
         * video : http://api.lgdama.com:10001/storage/video/ff8545ed50474b0ea68cc2d1622e8a08.mp4
         * img : http://enjoy.fengyunguoyuan.com/upload/20200606052833CLfTm.jpg
         * recommend : 2
         * pre_votes : 0
         * final_votes : 0
         * rematch_votes : 0
         * club_id : 8
         * club_name : 青春有你
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
    }
}
