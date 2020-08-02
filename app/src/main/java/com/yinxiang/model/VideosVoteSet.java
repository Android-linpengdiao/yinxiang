package com.yinxiang.model;

import java.io.Serializable;

public class VideosVoteSet implements Serializable {

    /**
     * code : 200
     * msg : 成功
     * data : {"id":1,"created_at":"2020-07-28 16:59:00","updated_at":"2020-07-29 16:59:44","title":"付费投票","wallet_token":50,"votes":5}
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

    public static class DataBean implements Serializable{
        /**
         * id : 1
         * created_at : 2020-07-28 16:59:00
         * updated_at : 2020-07-29 16:59:44
         * title : 付费投票
         * wallet_token : 50
         * votes : 5
         */

        private int id;
        private String created_at;
        private String updated_at;
        private String title;
        private int wallet_token;
        private int votes;

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

        public int getWallet_token() {
            return wallet_token;
        }

        public void setWallet_token(int wallet_token) {
            this.wallet_token = wallet_token;
        }

        public int getVotes() {
            return votes;
        }

        public void setVotes(int votes) {
            this.votes = votes;
        }
    }
}
