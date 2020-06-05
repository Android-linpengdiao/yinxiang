package com.yinxiang.model;

import java.io.Serializable;
import java.util.List;

public class HomeActives  implements Serializable {

    /**
     * code : 200
     * msg : 成功
     * data : [{"id":2,"created_at":"2020-05-31T04:58:55.000000Z","updated_at":"2020-05-31T04:58:55.000000Z","title":"舞蹈大赛","desc":null,"video":"[{\"download_link\":\"active-categories\\/May2020\\/faBWzSrVaQwb59DlsTrs.mp4\",\"original_name\":\"test.mp4\"}]","pre_start_time":"2020-05-31 00:00:00","rematch_start_time":"2020-06-01 00:00:00","final_start_time":"2020-07-01 00:00:00","status":1,"pre_end_time":"2020-05-31 00:00:00","rematch_end_time":"2020-07-01 00:00:00","final_end_time":"2020-07-04 00:00:00"}]
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

    public static class DataBean implements Serializable{
        /**
         * id : 2
         * created_at : 2020-05-31T04:58:55.000000Z
         * updated_at : 2020-05-31T04:58:55.000000Z
         * title : 舞蹈大赛
         * desc : null
         * video : [{"download_link":"active-categories\/May2020\/faBWzSrVaQwb59DlsTrs.mp4","original_name":"test.mp4"}]
         * pre_start_time : 2020-05-31 00:00:00
         * rematch_start_time : 2020-06-01 00:00:00
         * final_start_time : 2020-07-01 00:00:00
         * status : 1
         * pre_end_time : 2020-05-31 00:00:00
         * rematch_end_time : 2020-07-01 00:00:00
         * final_end_time : 2020-07-04 00:00:00
         */

        private int id;
        private String created_at;
        private String updated_at;
        private String title;
        private Object desc;
        private String video;
        private String pre_start_time;
        private String rematch_start_time;
        private String final_start_time;
        private int status;
        private int selected;
        private String pre_end_time;
        private String rematch_end_time;
        private String final_end_time;

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

        public Object getDesc() {
            return desc;
        }

        public void setDesc(Object desc) {
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

        public int getSelected() {
            return selected;
        }

        public void setSelected(int selected) {
            this.selected = selected;
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
    }
}
