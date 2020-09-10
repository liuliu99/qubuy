package com.qubuyer.bean.home;

import java.io.Serializable;
import java.util.List;

public class HomeSaleTopEntity implements Serializable {
    //总销售额
    private float total;
    //昨天销售额
    private float to_total;
    private UserBeanXXX user;

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getTo_total() {
        return to_total;
    }

    public void setTo_total(float to_total) {
        this.to_total = to_total;
    }

    public UserBeanXXX getUser() {
        return user;
    }

    public void setUser(UserBeanXXX user) {
        this.user = user;
    }

    public static class UserBeanXXX implements Serializable {
        //周榜
        private List<WeekMonthYearBean> week;
        //月榜
        private List<WeekMonthYearBean> month;
        //季榜
        private List<WeekMonthYearBean> season;
        //年榜
        private List<WeekMonthYearBean> year;

        public List<WeekMonthYearBean> getSeason() {
            return season;
        }

        public void setSeason(List<WeekMonthYearBean> season) {
            this.season = season;
        }

        public List<WeekMonthYearBean> getWeek() {
            return week;
        }

        public void setWeek(List<WeekMonthYearBean> week) {
            this.week = week;
        }

        public List<WeekMonthYearBean> getMonth() {
            return month;
        }

        public void setMonth(List<WeekMonthYearBean> month) {
            this.month = month;
        }

        public List<WeekMonthYearBean> getYear() {
            return year;
        }

        public void setYear(List<WeekMonthYearBean> year) {
            this.year = year;
        }

        public static class WeekMonthYearBean implements Serializable {
            private UserBean user;
            //销售额
            private float money;
            //奖金
            private float bonus;

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public float getMoney() {
                return money;
            }

            public void setMoney(float money) {
                this.money = money;
            }

            public float getBonus() {
                return bonus;
            }

            public void setBonus(float bonus) {
                this.bonus = bonus;
            }

            public static class UserBean implements Serializable {
                //用户ID
                private int user_id;
                //用户头像
                private String head_pic;
                //用户名
                private String nickname;

                public int getUser_id() {
                    return user_id;
                }

                public void setUser_id(int user_id) {
                    this.user_id = user_id;
                }

                public String getHead_pic() {
                    return head_pic;
                }

                public void setHead_pic(String head_pic) {
                    this.head_pic = head_pic;
                }

                public String getNickname() {
                    return nickname;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }
            }
        }
    }
}
