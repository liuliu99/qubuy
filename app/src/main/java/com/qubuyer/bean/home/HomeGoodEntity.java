package com.qubuyer.bean.home;

import com.qubuyer.bean.category.CategoryFirstEntity;

import java.io.Serializable;
import java.util.List;

public class HomeGoodEntity implements Serializable {
    //商品ID
    private int goods_id;
    //商品号
    private String goods_sn;
    //商品名
    private String goods_name;
    //点击数
    private int click_count;
    //库存
    private int store_count;
    //市场价
    private String market_price;
    //本店价
    private String shop_price;
    //关键字
    private String keywords;
    //商品简单描述
    private String goods_remark;
    //商品内容
    private String goods_content;
    //商品图
    private String original_img;
    //商品图片
    private List<GoodImg> goods_images;

    //商品SKU模型
    private List<GoodSKUModel> goods_sku_model;
    private List<GoodSKUKeyValue> key_value_list;

    //销售量
    private int sales_sum;
    //收藏量
    private int collect_sumn;
    //是否收藏 0否1是
    private String is_collect;
    //商品分类信息
    private CategoryFirstEntity goods_category;

    //规格ID
    private int item_id;

    private float price;
    //商品数量
    private int goods_num;
    //每人限购
    private int buy_limit;
    //开始时间
    private long start_time;
    //结束时间
    private long end_time;


    //折让金
    private String restrore_price;
    //出手价
    private double diff_price;
    //商品预算折让金
    private String restore;
    //折扣比率
    private float dis_rebate;
    //是否上架
    private int is_on_sale;
    //商品视频地址
    private String video_full_path;
    //商品折扣点数
    private int discount_rebate;

    public int getDiscount_rebate() {
        return discount_rebate;
    }

    public void setDiscount_rebate(int discount_rebate) {
        this.discount_rebate = discount_rebate;
    }

    public String getVideo_full_path() {
        return video_full_path;
    }

    public void setVideo_full_path(String video_full_path) {
        this.video_full_path = video_full_path;
    }

    public int getIs_on_sale() {
        return is_on_sale;
    }

    public void setIs_on_sale(int is_on_sale) {
        this.is_on_sale = is_on_sale;
    }

    public float getDis_rebate() {
        return dis_rebate;
    }

    public void setDis_rebate(float dis_rebate) {
        this.dis_rebate = dis_rebate;
    }

    public HomeGoodEntity() {
    }

    public HomeGoodEntity(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getRestore() {
        return restore;
    }

    public void setRestore(String restore) {
        this.restore = restore;
    }

    public int getStore_count() {
        return store_count;
    }

    public void setStore_count(int store_count) {
        this.store_count = store_count;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(int goods_num) {
        this.goods_num = goods_num;
    }

    public int getBuy_limit() {
        return buy_limit;
    }

    public void setBuy_limit(int buy_limit) {
        this.buy_limit = buy_limit;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getOriginal_img() {
        return original_img;
    }

    public void setOriginal_img(String original_img) {
        this.original_img = original_img;
    }

    public String getShop_price() {
        return shop_price;
    }

    public void setShop_price(String shop_price) {
        this.shop_price = shop_price;
    }

    public String getRestrore_price() {
        return restrore_price;
    }

    public void setRestrore_price(String restrore_price) {
        this.restrore_price = restrore_price;
    }

    public double getDiff_price() {
        return diff_price;
    }

    public void setDiff_price(double diff_price) {
        this.diff_price = diff_price;
    }

    public String getGoods_sn() {
        return goods_sn;
    }

    public void setGoods_sn(String goods_sn) {
        this.goods_sn = goods_sn;
    }

    public int getClick_count() {
        return click_count;
    }

    public void setClick_count(int click_count) {
        this.click_count = click_count;
    }

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getGoods_remark() {
        return goods_remark;
    }

    public void setGoods_remark(String goods_remark) {
        this.goods_remark = goods_remark;
    }

    public String getGoods_content() {
        return goods_content;
    }

    public void setGoods_content(String goods_content) {
        this.goods_content = goods_content;
    }

    public List<GoodImg> getGoods_images() {
        return goods_images;
    }

    public void setGoods_images(List<GoodImg> goods_images) {
        this.goods_images = goods_images;
    }

    public List<GoodSKUModel> getGoods_sku_model() {
        return goods_sku_model;
    }

    public void setGoods_sku_model(List<GoodSKUModel> goods_sku_model) {
        this.goods_sku_model = goods_sku_model;
    }

    public List<GoodSKUKeyValue> getKey_value_list() {
        return key_value_list;
    }

    public void setKey_value_list(List<GoodSKUKeyValue> key_value_list) {
        this.key_value_list = key_value_list;
    }

    public int getSales_sum() {
        return sales_sum;
    }

    public void setSales_sum(int sales_sum) {
        this.sales_sum = sales_sum;
    }

    public int getCollect_sumn() {
        return collect_sumn;
    }

    public void setCollect_sumn(int collect_sumn) {
        this.collect_sumn = collect_sumn;
    }

    public String getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(String is_collect) {
        this.is_collect = is_collect;
    }

    public CategoryFirstEntity getGoods_category() {
        return goods_category;
    }

    public void setGoods_category(CategoryFirstEntity goods_category) {
        this.goods_category = goods_category;
    }

    public static class GoodImg implements Serializable {
        private String image_url;

        public GoodImg() {
        }

        public GoodImg(String image_url) {
            this.image_url = image_url;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }
    }

    /**
     * 商品SKU数据模型
     */
    public class GoodSKUModel implements Serializable {
        /**
         * specTitle : 选择种类,版本,种类,场景
         * specPropertyValue : 选择5斤装,版本1,礼品盒装,送女友
         * price : 100.00
         * stockNum : 100
         * skuId : 422
         */
        private String specTitle;
        private String specPropertyValue;
        private String price;
        private int stockNum;
        private int skuId;
        private String restrore_price;

        public String getRestrore_price() {
            return restrore_price;
        }

        public void setRestrore_price(String restrore_price) {
            this.restrore_price = restrore_price;
        }

        public String getSpecTitle() {
            return specTitle;
        }

        public void setSpecTitle(String specTitle) {
            this.specTitle = specTitle;
        }

        public String getSpecPropertyValue() {
            return specPropertyValue;
        }

        public void setSpecPropertyValue(String specPropertyValue) {
            this.specPropertyValue = specPropertyValue;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getStockNum() {
            return stockNum;
        }

        public void setStockNum(int stockNum) {
            this.stockNum = stockNum;
        }

        public int getSkuId() {
            return skuId;
        }

        public void setSkuId(int skuId) {
            this.skuId = skuId;
        }
    }

    /**
     * 商品SDK KeyValue列表模型
     */
    public class GoodSKUKeyValue implements Serializable {
        private String key;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
