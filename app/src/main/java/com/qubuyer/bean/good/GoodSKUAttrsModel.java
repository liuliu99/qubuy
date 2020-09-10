package com.qubuyer.bean.good;

import java.util.List;

/**
 * 商品SKU属性模型
 */
public class GoodSKUAttrsModel {
    private List<AttributesModel> attributes;
    private List<StockGoodsModel> stockGoods;

    public List<AttributesModel> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributesModel> attributes) {
        this.attributes = attributes;
    }

    public List<StockGoodsModel> getStockGoods() {
        return stockGoods;
    }

    public void setStockGoods(List<StockGoodsModel> stockGoods) {
        this.stockGoods = stockGoods;
    }

    public static class AttributesModel {
        /**
         * tabID : 0
         * key : 颜色
         * valueList : ["白","蓝","黑"]
         */
        private int tabID;
        private String key;
        private List<String> valueList;

        public int getTabID() {
            return tabID;
        }

        public void setTabID(int tabID) {
            this.tabID = tabID;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public List<String> getValueList() {
            return valueList;
        }

        public void setValueList(List<String> valueList) {
            this.valueList = valueList;
        }
    }

    public static class StockGoodsModel {
        /**
         * skuId : 1
         * goodModelList : [{"tabID":0,"tabName":"颜色","tabValue":"白"},{"tabID":1,"tabName":"型号","tabValue":"X"},{"tabID":2,"tabName":"衣服","tabValue":"羽绒服"},{"tabID":3,"tabName":"大小","tabValue":"中"}]
         */
        //主图
        private String mainImage;
        //单价
        private double price;
        //销售数量
        private int salesNum;
        //skuId
        private long skuId;
        //库存
        private int stockNum;
        //不同sku属性 对应不同的图片
        private String iconImage;

        private List<GoodModel> goodModelList;

        public String getIconImage() {
            return iconImage;
        }

        public void setIconImage(String iconImage) {
            this.iconImage = iconImage;
        }

        public String getMainImage() {
            return mainImage;
        }

        public void setMainImage(String mainImage) {
            this.mainImage = mainImage;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getSalesNum() {
            return salesNum;
        }

        public void setSalesNum(int salesNum) {
            this.salesNum = salesNum;
        }

        public long getSkuId() {
            return skuId;
        }

        public void setSkuId(long skuId) {
            this.skuId = skuId;
        }

        public int getStockNum() {
            return stockNum;
        }

        public void setStockNum(int stockNum) {
            this.stockNum = stockNum;
        }

        public List<GoodModel> getGoodModelList() {
            return goodModelList;
        }

        public void setGoodModelList(List<GoodModel> goodModelList) {
            this.goodModelList = goodModelList;
        }

        public static class GoodModel {
            /**
             * tabName : 颜色
             * tabValue : 白
             */
            private String tabName;
            private String tabValue;

            public String getTabName() {
                return tabName;
            }

            public void setTabName(String tabName) {
                this.tabName = tabName;
            }

            public String getTabValue() {
                return tabValue;
            }

            public void setTabValue(String tabValue) {
                this.tabValue = tabValue;
            }
        }
    }
}
