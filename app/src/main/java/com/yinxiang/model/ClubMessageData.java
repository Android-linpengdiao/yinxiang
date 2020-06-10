package com.yinxiang.model;

import java.io.Serializable;
import java.util.List;

public class ClubMessageData implements Serializable {

    /**
     * code : 200
     * msg : 成功
     * data : {"current_page":1,"data":[{"id":1,"created_at":"2020-06-10 22:39:23","updated_at":"2020-06-10 22:39:23","tourist_id":3,"club_id":8,"status":3,"club_tourist_id":6,"tourist":{"id":3,"name":"信用度","avatar":"upload/20200606024929Gkda2.jpg"},"club":{"id":8,"name":"青春有你","desc":"青春有你"}}],"first_page_url":"http://enjoy.fengyunguoyuan.com/api/friend/club?page=1","from":1,"last_page":1,"last_page_url":"http://enjoy.fengyunguoyuan.com/api/friend/club?page=1","next_page_url":null,"path":"http://enjoy.fengyunguoyuan.com/api/friend/club","per_page":"10","prev_page_url":null,"to":1,"total":1}
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

    public static class DataBeanX implements Serializable {
        /**
         * current_page : 1
         * data : [{"id":1,"created_at":"2020-06-10 22:39:23","updated_at":"2020-06-10 22:39:23","tourist_id":3,"club_id":8,"status":3,"club_tourist_id":6,"tourist":{"id":3,"name":"信用度","avatar":"upload/20200606024929Gkda2.jpg"},"club":{"id":8,"name":"青春有你","desc":"青春有你"}}]
         * first_page_url : http://enjoy.fengyunguoyuan.com/api/friend/club?page=1
         * from : 1
         * last_page : 1
         * last_page_url : http://enjoy.fengyunguoyuan.com/api/friend/club?page=1
         * next_page_url : null
         * path : http://enjoy.fengyunguoyuan.com/api/friend/club
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

        public static class DataBean implements Serializable {
            /**
             * id : 1
             * created_at : 2020-06-10 22:39:23
             * updated_at : 2020-06-10 22:39:23
             * tourist_id : 3
             * club_id : 8
             * status : 3
             * club_tourist_id : 6
             * tourist : {"id":3,"name":"信用度","avatar":"upload/20200606024929Gkda2.jpg"}
             * club : {"id":8,"name":"青春有你","desc":"青春有你"}
             */

            private int id;
            private String created_at;
            private String updated_at;
            private int tourist_id;
            private int club_id;
            private int status; //1审核通过 2审核未通过 3真正审核中
            private int club_tourist_id;
            private TouristBean tourist;
            private ClubBean club;

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

            public int getClub_id() {
                return club_id;
            }

            public void setClub_id(int club_id) {
                this.club_id = club_id;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getClub_tourist_id() {
                return club_tourist_id;
            }

            public void setClub_tourist_id(int club_tourist_id) {
                this.club_tourist_id = club_tourist_id;
            }

            public TouristBean getTourist() {
                return tourist;
            }

            public void setTourist(TouristBean tourist) {
                this.tourist = tourist;
            }

            public ClubBean getClub() {
                return club;
            }

            public void setClub(ClubBean club) {
                this.club = club;
            }

            public static class TouristBean implements Serializable {
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

            public static class ClubBean implements Serializable {
                /**
                 * id : 8
                 * name : 青春有你
                 * desc : 青春有你
                 */

                private int id;
                private String name;
                private String desc;

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

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }
            }
        }
    }
}
