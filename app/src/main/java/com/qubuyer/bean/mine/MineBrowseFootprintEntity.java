package com.qubuyer.bean.mine;

import com.qubuyer.bean.Entity;
import com.qubuyer.bean.home.HomeGoodEntity;

import java.util.List;

/**
 * @author Susong
 * @date 创建时间2019/4/2
 * @description 我的浏览足迹实体
 * @version
 */
public class MineBrowseFootprintEntity extends Entity {
    private String time;
    private List<ValueBean> value;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<ValueBean> getValue() {
        return value;
    }

    public void setValue(List<ValueBean> value) {
        this.value = value;
    }

    public static class ValueBean extends Entity {
        private int id;
        private String time;
        private int update_time;
        private HomeGoodEntity goods;

        public ValueBean() {
        }

        public ValueBean(String time) {
            this.time = time;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(int update_time) {
            this.update_time = update_time;
        }

        public HomeGoodEntity getGoods() {
            return goods;
        }

        public void setGoods(HomeGoodEntity goods) {
            this.goods = goods;
        }
    }
}
