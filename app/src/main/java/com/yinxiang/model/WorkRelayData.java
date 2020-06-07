package com.yinxiang.model;

import java.util.List;

public class WorkRelayData {

    /**
     * code : 200
     * msg : 成功
     * data : {"current_page":1,"data":[{"id":1,"followable_id":10,"followable_type":"App\\Content","follower_id":9,"follower_type":"App\\Content","created_at":"2020-06-07T14:37:18.000000Z","updated_at":"2020-06-07T14:37:18.000000Z","accepted":1,"follower":{"id":9,"created_at":"2020-06-05T02:26:28.000000Z","updated_at":"2020-06-05T02:26:28.000000Z","is_deleted":2,"tourist_id":3,"tourist_name":"XMhKfU","active_id":2,"active_name":"舞蹈大赛","video":"http://api.lgdama.com:10001/storage/video/f274e63c595449aea8b0da7c03e55fbf.mp4","img":"http://www.udiandou.com/upload/20200603124338REaoe.jpg","recommend":2,"pre_votes":0,"final_votes":0,"rematch_votes":0,"club_id":0,"club_name":"","assist_num":0,"comment_num":0,"name":"","tourist":{"id":3,"name":"信用度","avatar":"upload/20200606024929Gkda2.jpg"}},"followable":{"id":10,"created_at":"2020-06-05T04:06:00.000000Z","updated_at":"2020-06-05T04:06:00.000000Z","is_deleted":2,"tourist_id":3,"tourist_name":"XMhKfU","active_id":1,"active_name":"这就是街舞3","video":"http://api.lgdama.com:10001/storage/video/e31e0cd72f8a4f2786111e02a3275cf2.mp4","img":"http://www.udiandou.com/upload/20200603124337NLGcd.jpg","recommend":2,"pre_votes":0,"final_votes":0,"rematch_votes":0,"club_id":0,"club_name":"","assist_num":0,"comment_num":0,"name":"","tourist":{"id":3,"name":"信用度","avatar":"upload/20200606024929Gkda2.jpg"}}}],"first_page_url":"http://enjoy.fengyunguoyuan.com/api/homepageVideos/selfRelay?page=1","from":1,"last_page":1,"last_page_url":"http://enjoy.fengyunguoyuan.com/api/homepageVideos/selfRelay?page=1","next_page_url":null,"path":"http://enjoy.fengyunguoyuan.com/api/homepageVideos/selfRelay","per_page":"10","prev_page_url":null,"to":1,"total":1}
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
         * data : [{"id":1,"followable_id":10,"followable_type":"App\\Content","follower_id":9,"follower_type":"App\\Content","created_at":"2020-06-07T14:37:18.000000Z","updated_at":"2020-06-07T14:37:18.000000Z","accepted":1,"follower":{"id":9,"created_at":"2020-06-05T02:26:28.000000Z","updated_at":"2020-06-05T02:26:28.000000Z","is_deleted":2,"tourist_id":3,"tourist_name":"XMhKfU","active_id":2,"active_name":"舞蹈大赛","video":"http://api.lgdama.com:10001/storage/video/f274e63c595449aea8b0da7c03e55fbf.mp4","img":"http://www.udiandou.com/upload/20200603124338REaoe.jpg","recommend":2,"pre_votes":0,"final_votes":0,"rematch_votes":0,"club_id":0,"club_name":"","assist_num":0,"comment_num":0,"name":"","tourist":{"id":3,"name":"信用度","avatar":"upload/20200606024929Gkda2.jpg"}},"followable":{"id":10,"created_at":"2020-06-05T04:06:00.000000Z","updated_at":"2020-06-05T04:06:00.000000Z","is_deleted":2,"tourist_id":3,"tourist_name":"XMhKfU","active_id":1,"active_name":"这就是街舞3","video":"http://api.lgdama.com:10001/storage/video/e31e0cd72f8a4f2786111e02a3275cf2.mp4","img":"http://www.udiandou.com/upload/20200603124337NLGcd.jpg","recommend":2,"pre_votes":0,"final_votes":0,"rematch_votes":0,"club_id":0,"club_name":"","assist_num":0,"comment_num":0,"name":"","tourist":{"id":3,"name":"信用度","avatar":"upload/20200606024929Gkda2.jpg"}}}]
         * first_page_url : http://enjoy.fengyunguoyuan.com/api/homepageVideos/selfRelay?page=1
         * from : 1
         * last_page : 1
         * last_page_url : http://enjoy.fengyunguoyuan.com/api/homepageVideos/selfRelay?page=1
         * next_page_url : null
         * path : http://enjoy.fengyunguoyuan.com/api/homepageVideos/selfRelay
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
        private String next_page_url;
        private String path;
        private String per_page;
        private String prev_page_url;
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

        public String getPrev_page_url() {
            return prev_page_url;
        }

        public void setPrev_page_url(String prev_page_url) {
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
             * id : 1
             * followable_id : 10
             * followable_type : App\Content
             * follower_id : 9
             * follower_type : App\Content
             * created_at : 2020-06-07T14:37:18.000000Z
             * updated_at : 2020-06-07T14:37:18.000000Z
             * accepted : 1
             * follower : {"id":9,"created_at":"2020-06-05T02:26:28.000000Z","updated_at":"2020-06-05T02:26:28.000000Z","is_deleted":2,"tourist_id":3,"tourist_name":"XMhKfU","active_id":2,"active_name":"舞蹈大赛","video":"http://api.lgdama.com:10001/storage/video/f274e63c595449aea8b0da7c03e55fbf.mp4","img":"http://www.udiandou.com/upload/20200603124338REaoe.jpg","recommend":2,"pre_votes":0,"final_votes":0,"rematch_votes":0,"club_id":0,"club_name":"","assist_num":0,"comment_num":0,"name":"","tourist":{"id":3,"name":"信用度","avatar":"upload/20200606024929Gkda2.jpg"}}
             * followable : {"id":10,"created_at":"2020-06-05T04:06:00.000000Z","updated_at":"2020-06-05T04:06:00.000000Z","is_deleted":2,"tourist_id":3,"tourist_name":"XMhKfU","active_id":1,"active_name":"这就是街舞3","video":"http://api.lgdama.com:10001/storage/video/e31e0cd72f8a4f2786111e02a3275cf2.mp4","img":"http://www.udiandou.com/upload/20200603124337NLGcd.jpg","recommend":2,"pre_votes":0,"final_votes":0,"rematch_votes":0,"club_id":0,"club_name":"","assist_num":0,"comment_num":0,"name":"","tourist":{"id":3,"name":"信用度","avatar":"upload/20200606024929Gkda2.jpg"}}
             */

            private int id;
            private int followable_id;
            private String followable_type;
            private int follower_id;
            private String follower_type;
            private String created_at;
            private String updated_at;
            private int accepted;
            private FollowerBean follower;
            private FollowableBean followable;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getFollowable_id() {
                return followable_id;
            }

            public void setFollowable_id(int followable_id) {
                this.followable_id = followable_id;
            }

            public String getFollowable_type() {
                return followable_type;
            }

            public void setFollowable_type(String followable_type) {
                this.followable_type = followable_type;
            }

            public int getFollower_id() {
                return follower_id;
            }

            public void setFollower_id(int follower_id) {
                this.follower_id = follower_id;
            }

            public String getFollower_type() {
                return follower_type;
            }

            public void setFollower_type(String follower_type) {
                this.follower_type = follower_type;
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

            public int getAccepted() {
                return accepted;
            }

            public void setAccepted(int accepted) {
                this.accepted = accepted;
            }

            public FollowerBean getFollower() {
                return follower;
            }

            public void setFollower(FollowerBean follower) {
                this.follower = follower;
            }

            public FollowableBean getFollowable() {
                return followable;
            }

            public void setFollowable(FollowableBean followable) {
                this.followable = followable;
            }

            public static class FollowerBean {
                /**
                 * id : 9
                 * created_at : 2020-06-05T02:26:28.000000Z
                 * updated_at : 2020-06-05T02:26:28.000000Z
                 * is_deleted : 2
                 * tourist_id : 3
                 * tourist_name : XMhKfU
                 * active_id : 2
                 * active_name : 舞蹈大赛
                 * video : http://api.lgdama.com:10001/storage/video/f274e63c595449aea8b0da7c03e55fbf.mp4
                 * img : http://www.udiandou.com/upload/20200603124338REaoe.jpg
                 * recommend : 2
                 * pre_votes : 0
                 * final_votes : 0
                 * rematch_votes : 0
                 * club_id : 0
                 * club_name :
                 * assist_num : 0
                 * comment_num : 0
                 * name :
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

                public TouristBean getTourist() {
                    return tourist;
                }

                public void setTourist(TouristBean tourist) {
                    this.tourist = tourist;
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

            public static class FollowableBean {
                /**
                 * id : 10
                 * created_at : 2020-06-05T04:06:00.000000Z
                 * updated_at : 2020-06-05T04:06:00.000000Z
                 * is_deleted : 2
                 * tourist_id : 3
                 * tourist_name : XMhKfU
                 * active_id : 1
                 * active_name : 这就是街舞3
                 * video : http://api.lgdama.com:10001/storage/video/e31e0cd72f8a4f2786111e02a3275cf2.mp4
                 * img : http://www.udiandou.com/upload/20200603124337NLGcd.jpg
                 * recommend : 2
                 * pre_votes : 0
                 * final_votes : 0
                 * rematch_votes : 0
                 * club_id : 0
                 * club_name :
                 * assist_num : 0
                 * comment_num : 0
                 * name :
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
                private TouristBeanX tourist;

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

                public TouristBeanX getTourist() {
                    return tourist;
                }

                public void setTourist(TouristBeanX tourist) {
                    this.tourist = tourist;
                }

                public static class TouristBeanX {
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
    }
}
