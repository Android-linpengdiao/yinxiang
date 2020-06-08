package com.yinxiang.model;

import java.util.List;

public class MessageData {

    /**
     * code : 200
     * msg : 成功
     * data : {"current_page":1,"data":[{"id":15,"created_at":"2020-06-08 14:06:02","updated_at":"2020-06-08 14:06:02","body":"这个作品可以","tourist_id":3,"content_id":12,"content_tourist_id":6,"tourist":{"id":3,"name":"信用度","avatar":"upload/20200606024929Gkda2.jpg"},"content":{"id":12,"name":"","video":"http://api.lgdama.com:10001/storage/video/ff8545ed50474b0ea68cc2d1622e8a08.mp4","img":"http://enjoy.fengyunguoyuan.com/upload/20200606052833CLfTm.jpg"}}],"first_page_url":"http://enjoy.fengyunguoyuan.com/api/friend/comment?page=1","from":1,"last_page":1,"last_page_url":"http://enjoy.fengyunguoyuan.com/api/friend/comment?page=1","next_page_url":null,"path":"http://enjoy.fengyunguoyuan.com/api/friend/comment","per_page":"10","prev_page_url":null,"to":1,"total":1}
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
         * data : [{"id":15,"created_at":"2020-06-08 14:06:02","updated_at":"2020-06-08 14:06:02","body":"这个作品可以","tourist_id":3,"content_id":12,"content_tourist_id":6,"tourist":{"id":3,"name":"信用度","avatar":"upload/20200606024929Gkda2.jpg"},"content":{"id":12,"name":"","video":"http://api.lgdama.com:10001/storage/video/ff8545ed50474b0ea68cc2d1622e8a08.mp4","img":"http://enjoy.fengyunguoyuan.com/upload/20200606052833CLfTm.jpg"}}]
         * first_page_url : http://enjoy.fengyunguoyuan.com/api/friend/comment?page=1
         * from : 1
         * last_page : 1
         * last_page_url : http://enjoy.fengyunguoyuan.com/api/friend/comment?page=1
         * next_page_url : null
         * path : http://enjoy.fengyunguoyuan.com/api/friend/comment
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
             * id : 15
             * created_at : 2020-06-08 14:06:02
             * updated_at : 2020-06-08 14:06:02
             * body : 这个作品可以
             * tourist_id : 3
             * content_id : 12
             * content_tourist_id : 6
             * tourist : {"id":3,"name":"信用度","avatar":"upload/20200606024929Gkda2.jpg"}
             * content : {"id":12,"name":"","video":"http://api.lgdama.com:10001/storage/video/ff8545ed50474b0ea68cc2d1622e8a08.mp4","img":"http://enjoy.fengyunguoyuan.com/upload/20200606052833CLfTm.jpg"}
             */

            private int id;
            private String created_at;
            private String updated_at;
            private String body;
            private int tourist_id;
            private int content_id;
            private int content_tourist_id;
            private TouristBean tourist;
            private ContentBean content;

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

            public String getBody() {
                return body;
            }

            public void setBody(String body) {
                this.body = body;
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

            public int getContent_tourist_id() {
                return content_tourist_id;
            }

            public void setContent_tourist_id(int content_tourist_id) {
                this.content_tourist_id = content_tourist_id;
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

            public static class ContentBean {
                /**
                 * id : 12
                 * name :
                 * video : http://api.lgdama.com:10001/storage/video/ff8545ed50474b0ea68cc2d1622e8a08.mp4
                 * img : http://enjoy.fengyunguoyuan.com/upload/20200606052833CLfTm.jpg
                 */

                private int id;
                private String name;
                private String video;
                private String img;

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
            }
        }
    }
}
