package com.qubuyer.bean.category;

import com.qubuyer.bean.home.HomeGoodEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author Susong
 * @date 创建时间2019/3/11
 * @description 分类实体
 * @version
 */
public class SecondCategoryGoodEntity implements Serializable {
    private ListBean list;
    private List<CategorySecondEntity> cate;

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public List<CategorySecondEntity> getCate() {
        return cate;
    }

    public void setCate(List<CategorySecondEntity> cate) {
        this.cate = cate;
    }

    public static class ListBean implements Serializable {
        private int total;
        private int per_page;
        private int current_page;
        private int last_page;
        private List<HomeGoodEntity> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public List<HomeGoodEntity> getData() {
            return data;
        }

        public void setData(List<HomeGoodEntity> data) {
            this.data = data;
        }
    }
}
