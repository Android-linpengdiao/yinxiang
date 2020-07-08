package com.yinxiang.model;

import java.util.List;

public class SpreadSetData {

    /**
     * code : 200
     * msg : 成功
     * data : [{"id":1,"created_at":"2020-07-05 21:45:21","updated_at":null,"title":"5000人+","wallet_token":100},{"id":2,"created_at":"2020-07-05 21:45:21","updated_at":"2020-07-05 21:45:44","title":"1W人+","wallet_token":200},{"id":3,"created_at":"2020-07-05 21:45:21","updated_at":"2020-07-05 21:45:44","title":"2W人+","wallet_token":500}]
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
         * id : 1
         * created_at : 2020-07-05 21:45:21
         * updated_at : null
         * title : 5000人+
         * wallet_token : 100
         */

        private int id;
        private String created_at;
        private Object updated_at;
        private String title;
        private int wallet_token;
        private int selected;

        public int getSelected() {
            return selected;
        }

        public void setSelected(int selected) {
            this.selected = selected;
        }

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

        public int getWallet_token() {
            return wallet_token;
        }

        public void setWallet_token(int wallet_token) {
            this.wallet_token = wallet_token;
        }
    }
}
