package com.qubuyer.bean.mine;

import com.qubuyer.bean.Entity;
import com.qubuyer.bean.home.HomeGoodEntity;

import java.util.List;

/**
 * @author Susong
 * @date 创建时间2020/1/10
 * @description 我的评价实体
 * @version
 */
public class MineCommentEntity extends Entity {

    /**
     * comment :
     * add_time : 1566436812
     * goods : {"goods_id":1585,"goods_name":"晶弘 冰箱 对开门 BCD-595WEDC2/金拉丝 彩钢面板","original_img":"http://down.qubuyer.com/public/upload/images/20190808529090d3bdf57a3ae4020311b7d1694dfile5d4b966611dc5.jpg?imageView2/1/w/300/h/300/format/jpg/sharpen/1/q/100","shop_price":"999.99"}
     * user : {"head_pic":"http://down.qubuyer.com/public/upload/images/20190819609bc456df1b70334fba39a7b8d21d34file5d5a7fc314189.jpg?imageView2/1/w/300/h/300/format/jpg/sharpen/1/q/100","nickname":"小号"}
     * img_full : []
     */
    private String content;
    private Long add_time;
    private HomeGoodEntity goods;
    private UserBean user;
    private List<String> img_full;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Long add_time) {
        this.add_time = add_time;
    }

    public HomeGoodEntity getGoods() {
        return goods;
    }

    public void setGoods(HomeGoodEntity goods) {
        this.goods = goods;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public List<String> getImg_full() {
        return img_full;
    }

    public void setImg_full(List<String> img_full) {
        this.img_full = img_full;
    }

    public static class UserBean extends Entity {
        /**
         * head_pic : http://down.qubuyer.com/public/upload/images/20190819609bc456df1b70334fba39a7b8d21d34file5d5a7fc314189.jpg?imageView2/1/w/300/h/300/format/jpg/sharpen/1/q/100
         * nickname : 小号
         */
        private String head_pic;
        private String nickname;

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
