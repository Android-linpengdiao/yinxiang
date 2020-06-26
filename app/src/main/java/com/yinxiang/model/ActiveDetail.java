package com.yinxiang.model;

import java.util.List;

public class ActiveDetail {

    /**
     * code : 200
     * msg : 成功
     * data : [{"id":16,"created_at":"2020-06-13 22:32:16","updated_at":"2020-06-20 10:04:02","is_deleted":2,"tourist_id":3,"tourist_name":"信用度","active_id":1,"active_name":"这就是街舞3","video":"http://api.lgdama.com:10001/storage/video/ff8545ed50474b0ea68cc2d1622e8a08.mp4","img":"http://enjoy.fengyunguoyuan.com/upload/20200606052833CLfTm.jpg","recommend":2,"pre_votes":31,"final_votes":0,"rematch_votes":0,"club_id":6,"club_name":"这就是街舞","assist_num":2,"comment_num":3,"name":"作品双击66","play_num":0,"status":1,"rank":1,"rank_vote":0,"active_detail":{"id":1,"created_at":"2020-05-25 15:21:00","updated_at":"2020-06-14 10:25:58","title":"这就是街舞3","desc":"<p>《这！就是街舞》是优酷推出的街舞选拔类真人秀。节目通过&ldquo;明星导师+专业舞者真人秀&rdquo;的模式，采取个人选拔、团队作战的表演方式，在四位队长的带领下组成四支战队，进行团队间的群舞Battle，最终产生总冠军。<\/p>\r\n<p>《这！就是街舞》包括：《这！就是街舞第一季》、《这！就是街舞第二季》第一季由易烊千玺、罗志祥、韩庚、黄子韬担任明星队长，于2018年2月24日起每周六20:00在优酷首播，于2018年5月13日收官，易烊千玺战队成员韩宇获得总冠军。第二季由由易烊千玺、罗志祥、韩庚、吴建豪担任明星队长，于2019年5月18日起在优酷首播。<\/p>\r\n<p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;<\/p>","video":"[]","pre_start_time":null,"rematch_start_time":null,"final_start_time":null,"status":2,"pre_end_time":null,"rematch_end_time":null,"final_end_time":null,"img":null},"tourist":{"id":3,"name":"信用度","avatar":"upload/20200606024929Gkda2.jpg"}}]
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
         * id : 16
         * created_at : 2020-06-13 22:32:16
         * updated_at : 2020-06-20 10:04:02
         * is_deleted : 2
         * tourist_id : 3
         * tourist_name : 信用度
         * active_id : 1
         * active_name : 这就是街舞3
         * video : http://api.lgdama.com:10001/storage/video/ff8545ed50474b0ea68cc2d1622e8a08.mp4
         * img : http://enjoy.fengyunguoyuan.com/upload/20200606052833CLfTm.jpg
         * recommend : 2
         * pre_votes : 31
         * final_votes : 0
         * rematch_votes : 0
         * club_id : 6
         * club_name : 这就是街舞
         * assist_num : 2
         * comment_num : 3
         * name : 作品双击66
         * play_num : 0
         * status : 1
         * rank : 1
         * rank_vote : 0
         * active_detail : {"id":1,"created_at":"2020-05-25 15:21:00","updated_at":"2020-06-14 10:25:58","title":"这就是街舞3","desc":"<p>《这！就是街舞》是优酷推出的街舞选拔类真人秀。节目通过&ldquo;明星导师+专业舞者真人秀&rdquo;的模式，采取个人选拔、团队作战的表演方式，在四位队长的带领下组成四支战队，进行团队间的群舞Battle，最终产生总冠军。<\/p>\r\n<p>《这！就是街舞》包括：《这！就是街舞第一季》、《这！就是街舞第二季》第一季由易烊千玺、罗志祥、韩庚、黄子韬担任明星队长，于2018年2月24日起每周六20:00在优酷首播，于2018年5月13日收官，易烊千玺战队成员韩宇获得总冠军。第二季由由易烊千玺、罗志祥、韩庚、吴建豪担任明星队长，于2019年5月18日起在优酷首播。<\/p>\r\n<p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;<\/p>","video":"[]","pre_start_time":null,"rematch_start_time":null,"final_start_time":null,"status":2,"pre_end_time":null,"rematch_end_time":null,"final_end_time":null,"img":null}
         * tourist : {"id":3,"name":"信用度","avatar":"upload/20200606024929Gkda2.jpg"}
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
        private int rank;
        private int rank_vote;
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

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public int getRank_vote() {
            return rank_vote;
        }

        public void setRank_vote(int rank_vote) {
            this.rank_vote = rank_vote;
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
             * id : 1
             * created_at : 2020-05-25 15:21:00
             * updated_at : 2020-06-14 10:25:58
             * title : 这就是街舞3
             * desc : <p>《这！就是街舞》是优酷推出的街舞选拔类真人秀。节目通过&ldquo;明星导师+专业舞者真人秀&rdquo;的模式，采取个人选拔、团队作战的表演方式，在四位队长的带领下组成四支战队，进行团队间的群舞Battle，最终产生总冠军。</p>
             <p>《这！就是街舞》包括：《这！就是街舞第一季》、《这！就是街舞第二季》第一季由易烊千玺、罗志祥、韩庚、黄子韬担任明星队长，于2018年2月24日起每周六20:00在优酷首播，于2018年5月13日收官，易烊千玺战队成员韩宇获得总冠军。第二季由由易烊千玺、罗志祥、韩庚、吴建豪担任明星队长，于2019年5月18日起在优酷首播。</p>
             <p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</p>
             * video : []
             * pre_start_time : null
             * rematch_start_time : null
             * final_start_time : null
             * status : 2
             * pre_end_time : null
             * rematch_end_time : null
             * final_end_time : null
             * img : null
             */

            private int id;
            private String created_at;
            private String updated_at;
            private String title;
            private String desc;
            private String video;
            private Object pre_start_time;
            private Object rematch_start_time;
            private Object final_start_time;
            private int status;
            private Object pre_end_time;
            private Object rematch_end_time;
            private Object final_end_time;
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

            public Object getPre_start_time() {
                return pre_start_time;
            }

            public void setPre_start_time(Object pre_start_time) {
                this.pre_start_time = pre_start_time;
            }

            public Object getRematch_start_time() {
                return rematch_start_time;
            }

            public void setRematch_start_time(Object rematch_start_time) {
                this.rematch_start_time = rematch_start_time;
            }

            public Object getFinal_start_time() {
                return final_start_time;
            }

            public void setFinal_start_time(Object final_start_time) {
                this.final_start_time = final_start_time;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public Object getPre_end_time() {
                return pre_end_time;
            }

            public void setPre_end_time(Object pre_end_time) {
                this.pre_end_time = pre_end_time;
            }

            public Object getRematch_end_time() {
                return rematch_end_time;
            }

            public void setRematch_end_time(Object rematch_end_time) {
                this.rematch_end_time = rematch_end_time;
            }

            public Object getFinal_end_time() {
                return final_end_time;
            }

            public void setFinal_end_time(Object final_end_time) {
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
             * id : 3
             * name : 信用度
             * avatar : upload/20200606024929Gkda2.jpg
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
