package com.yinxiang.model;

public class SpreadData {

    /**
     * code : 200
     * msg : 成功
     * data : {"price":0,"tourist_id":"1","trans_no":"202006061591451254363","type":3,"trans_type":1,"wallet_record":"1","updated_at":"2020-06-06T13:47:34.000000Z","created_at":"2020-06-06T13:47:34.000000Z","id":6}
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
         * price : 0
         * tourist_id : 1
         * trans_no : 202006061591451254363
         * type : 3
         * trans_type : 1
         * wallet_record : 1
         * updated_at : 2020-06-06T13:47:34.000000Z
         * created_at : 2020-06-06T13:47:34.000000Z
         * id : 6
         */

        private int price;
        private String tourist_id;
        private String trans_no;
        private int type;
        private int trans_type;
        private String wallet_record;
        private String updated_at;
        private String created_at;
        private int id;

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getTourist_id() {
            return tourist_id;
        }

        public void setTourist_id(String tourist_id) {
            this.tourist_id = tourist_id;
        }

        public String getTrans_no() {
            return trans_no;
        }

        public void setTrans_no(String trans_no) {
            this.trans_no = trans_no;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getTrans_type() {
            return trans_type;
        }

        public void setTrans_type(int trans_type) {
            this.trans_type = trans_type;
        }

        public String getWallet_record() {
            return wallet_record;
        }

        public void setWallet_record(String wallet_record) {
            this.wallet_record = wallet_record;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
