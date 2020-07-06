package com.yinxiang.model;

import java.util.List;

public class NoticeData {

    /**
     * code : 200
     * msg : 成功
     * data : {"current_page":1,"data":[{"id":1,"created_at":"2020-07-05 16:28:51","updated_at":"2020-07-05 16:28:51","title":"引享App上线发布会在昆泰大酒店举行","desc":"<p>引享App上线发布会在昆泰大酒店举行<\/p>"}],"first_page_url":"http://enjoy.fengyunguoyuan.com/api/friend/system?page=1","from":1,"last_page":1,"last_page_url":"http://enjoy.fengyunguoyuan.com/api/friend/system?page=1","next_page_url":null,"path":"http://enjoy.fengyunguoyuan.com/api/friend/system","per_page":"10","prev_page_url":null,"to":1,"total":1}
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
         * data : [{"id":1,"created_at":"2020-07-05 16:28:51","updated_at":"2020-07-05 16:28:51","title":"引享App上线发布会在昆泰大酒店举行","desc":"<p>引享App上线发布会在昆泰大酒店举行<\/p>"}]
         * first_page_url : http://enjoy.fengyunguoyuan.com/api/friend/system?page=1
         * from : 1
         * last_page : 1
         * last_page_url : http://enjoy.fengyunguoyuan.com/api/friend/system?page=1
         * next_page_url : null
         * path : http://enjoy.fengyunguoyuan.com/api/friend/system
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
             * id : 1
             * created_at : 2020-07-05 16:28:51
             * updated_at : 2020-07-05 16:28:51
             * title : 引享App上线发布会在昆泰大酒店举行
             * desc : <p>引享App上线发布会在昆泰大酒店举行</p>
             */

            private int id;
            private String created_at;
            private String updated_at;
            private String title;
            private String desc;

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
        }
    }
}
