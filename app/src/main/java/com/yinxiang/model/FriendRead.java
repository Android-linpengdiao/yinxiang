package com.yinxiang.model;

public class FriendRead {

    /**
     * code : 200
     * msg : 成功
     * data : {"system":{"num":1,"type":1},"assist":{"num":1,"type":2},"comment":{"num":1,"type":3},"club":{"num":1,"type":4}}
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
         * system : {"num":1,"type":1}
         * assist : {"num":1,"type":2}
         * comment : {"num":1,"type":3}
         * club : {"num":1,"type":4}
         */

        private SystemBean system;
        private AssistBean assist;
        private CommentBean comment;
        private ClubBean club;

        public SystemBean getSystem() {
            return system;
        }

        public void setSystem(SystemBean system) {
            this.system = system;
        }

        public AssistBean getAssist() {
            return assist;
        }

        public void setAssist(AssistBean assist) {
            this.assist = assist;
        }

        public CommentBean getComment() {
            return comment;
        }

        public void setComment(CommentBean comment) {
            this.comment = comment;
        }

        public ClubBean getClub() {
            return club;
        }

        public void setClub(ClubBean club) {
            this.club = club;
        }

        public static class SystemBean {
            /**
             * num : 1
             * type : 1
             */

            private int num = 0;
            private int type;

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }

        public static class AssistBean {
            /**
             * num : 1
             * type : 2
             */

            private int num = 0;
            private int type;

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }

        public static class CommentBean {
            /**
             * num : 1
             * type : 3
             */

            private int num = 0;
            private int type;

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }

        public static class ClubBean {
            /**
             * num : 1
             * type : 4
             */

            private int num = 0;
            private int type;

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
