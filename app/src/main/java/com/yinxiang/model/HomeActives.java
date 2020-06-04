package com.yinxiang.model;

import java.io.Serializable;
import java.util.List;

public class HomeActives  implements Serializable {

    /**
     * code : 200
     * msg : 成功
     * data : [{"id":1,"created_at":"2020-05-25T07:21:02.000000Z","updated_at":null,"title":"这就是街舞3","desc":"这就是街舞描述","video":null,"pre_start_time":null,"rematch_start_time":null,"final_start_time":null,"status":2,"pre_end_time":null,"rematch_end_time":null,"final_end_time":null},{"id":2,"created_at":"2020-05-31T04:58:55.000000Z","updated_at":"2020-05-31T04:58:55.000000Z","title":"舞蹈大赛","desc":null,"video":"[{\"download_link\":\"active-categories\\/May2020\\/faBWzSrVaQwb59DlsTrs.mp4\",\"original_name\":\"test.mp4\"}]","pre_start_time":"2020-05-31 00:00:00","rematch_start_time":"2020-06-01 00:00:00","final_start_time":"2020-07-01 00:00:00","status":1,"pre_end_time":"2020-05-31 00:00:00","rematch_end_time":"2020-07-01 00:00:00","final_end_time":"2020-07-04 00:00:00"}]
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

    public static class DataBean implements Serializable {
        /**
         * id : 1
         * created_at : 2020-05-25T07:21:02.000000Z
         * updated_at : null
         * title : 这就是街舞3
         * desc : 这就是街舞描述
         * video : null
         * pre_start_time : null
         * rematch_start_time : null
         * final_start_time : null
         * status : 2
         * pre_end_time : null
         * rematch_end_time : null
         * final_end_time : null
         */

        private int id;
        private String created_at;
        private Object updated_at;
        private String title;
        private String desc;
        private Object video;
        private Object pre_start_time;
        private Object rematch_start_time;
        private Object final_start_time;
        private int status;
        private Object pre_end_time;
        private Object rematch_end_time;
        private Object final_end_time;

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

        public Object getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(Object updated_at) {
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

        public Object getVideo() {
            return video;
        }

        public void setVideo(Object video) {
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
    }
}
