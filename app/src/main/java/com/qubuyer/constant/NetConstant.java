package com.qubuyer.constant;

/**
 * @author Susong
 * @date 创建时间:2018/12/19
 * @description 接口常量类
 * & @version
 */
public class NetConstant {
//    private static final String BASE_RC_URL = "http://qumaimai.chaofankeji.cn";//开发用测试环境
    private static final String BASE_RC_URL = "https://api.qubuyer.com";//开发用测试环境
    public static final String BASE_URL = BASE_RC_URL;

    //账号密码登录
    public static final String LOGIN_POST = BASE_URL + "/user/login/appDoLogin";
    //手机验证码登录
    public static final String LOGIN_PHONE_CODE_POST = BASE_URL + "/user/login/mobileDoLogin";
    //第三方登录
    public static final String WECHAT_OR_QQ_LOGIN_POST = BASE_URL + "/user/thirdLogin/login";
    //获取注册验证码
    public static final String GET_REGISTER_VERIFICATION_CODE_POST = BASE_URL + "/home/api/registerSms";
    //注册
    public static final String REGISTER_POST = BASE_URL + "/User/login/doRegister";
    //获取找回密码验证码
    public static final String GET_FIND_PWD_VERIFICATION_CODE_POST = BASE_URL + "/home/api/verifySms";
    //第三方登录绑定手机号
    public static final String WECHAT_OR_QQ_BIND_PHONE_POST = BASE_URL + "/user/thirdLogin/bind";
    //第三方登录设置密码
    public static final String WECHAT_OR_QQ_LOGIN_SET_PWD_POST = BASE_URL + "/user/thirdLogin/setPassword";
    //找回密码
    public static final String FIND_PWD_POST = BASE_URL + "/user/login/forgetPassword";
    //获取用户信息
    public static final String GET_USER_INFO_POST = BASE_URL + "/user/user/userInfo";
    //获取用户消息数量
    public static final String GET_USER_MESSAGE_COUNT_POST = BASE_URL + "/user/user/unSendNumber";
    //退出登录
    public static final String LOGIN_OUT_POST = BASE_URL + "/user/login/loginout";

    //获取首页banner
    public static final String GET_HOME_BANNER_POST = BASE_URL + "/home/index/banner";
    //获取首页分类
    public static final String GET_HOME_CATEGORY_POST = BASE_URL + "/home/index/cate";
    //获取首页销售排行榜
    public static final String GET_HOME_SALE_TOP_POST = BASE_URL + "/home/index/distribut";
    //获取首页超级返
    public static final String GET_HOME_SUPER_RETURN_POST = BASE_URL + "/home/index/returnGoods";
    //获取首页每日折扣
    public static final String GET_HOME_DAILYDISCOUNT_POST = BASE_URL + "/home/prom/discount";
    //获取首页限时购
    public static final String GET_HOME_LIMITED_BUY_POST = BASE_URL + "/home/index/buyLimit";
    //获取抢购活动商品
    public static final String GET_HOME_FLASH_SALE_POST = BASE_URL + "/home/prom/flashSale";
    //获取首页热门推荐
    public static final String GET_HOME_HOT_RECOMMEND_POST = BASE_URL + "/home/index/isHot";
    //获取首页推荐
    public static final String GET_HOME_SPECIAL_POST = BASE_URL + "/home/index/goodsState";
    //获取首页二级商品列表
    public static final String GET_HOME_SECOND_GOOD_LIST_POST = BASE_URL + "/home/goods/stateList";

    //获取分类列表
    public static final String GET_GOOD_CATEGORY_POST = BASE_URL + "/home/goods/cateList";
    //获取二级分类商品列表
    public static final String GET_SECOND_CATEGORY_GOOD_LIST_POST = BASE_URL + "/home/goods/goodsList";

    //获取购物车商品列表
    public static final String GET_SHOP_CART_GOOD_LIST_POST = BASE_URL + "/home/cart/lists";
    //获取购物车推荐商品列表
    public static final String GET_SHOP_CART_SPECIAL_GOOD_LIST_POST = BASE_URL + "/home/cart/recommendGoods";
    //购物车商品伤处
    public static final String SHOP_CART_DELETE_GOOD_POST = BASE_URL + "/home/cart/del";
    //购物车商品收藏
    public static final String SHOP_CART_COLLECT_GOOD_POST = BASE_URL + "/user/user/goodsCollectSave";
    //购物车商品数量调整
    public static final String SHOP_CART_GOOD_COUNT_CHANGE_POST = BASE_URL + "/Home/cart/changeNum";
    //购物车商品单次选中
    public static final String SHOP_CART_GOOD_CHECKED_POST = BASE_URL + "/home/cart/radio";
    //购物车商品取消单次选中
    public static final String SHOP_CART_GOOD_UNCHECK_POST = BASE_URL + "/home/cart/unradio";
    //购物车商品全选
    public static final String SHOP_CART_GOOD_ALLCHECK_POST = BASE_URL + "/home/cart/all";
    //购物车商品取消全选
    public static final String SHOP_CART_GOOD_ALLCHECK_NOT_POST = BASE_URL + "/home/cart/unall";
    //清空购物车失效商品
    public static final String SHOP_CART_GOOD_CLEAR_LOSE_EFFICACY_POST = BASE_URL + "/home/cart/delAll";

    //获取商品详情
    public static final String GET_GOOD_DETAIL_POST = BASE_URL + "/home/goods/goodsInfo";
    //添加商品到购物车
    public static final String ADD_GOOD_TO_CART_POST = BASE_URL + "/home/cart/add";
    //获取商品评论列表
    public static final String GET_GOOD_COMMMENT_LIST_POST = BASE_URL + "/home/goods/goodsComment";
    //获取我的评论列表
    public static final String GET_MINE_COMMMENT_LIST_POST = BASE_URL + "/user/user/comment";
    //获取商品评论评率
    public static final String GET_GOOD_ASSESS_POST = BASE_URL + "/home/goods/goodsAssess";
    //订单金额计算
    public static final String CALC_ORDER_PRICE_POST = BASE_URL + "/user/cart/priceCalculation";
    //立即购买
    public static final String BUY_NOW_POST = BASE_URL + "/User/cart/byNow";
    //购物车购买
    public static final String SHOP_CART_BUY_NOW_POST = BASE_URL + "/User/cart/detail";
    //立即购买下单
    public static final String BUY_NOW_SO_POST = BASE_URL + "/user/cart/buyNowPlace";
    //购物车购买
    public static final String SHOP_CART_BUY_SO_POST = BASE_URL + "/user/cart/cartPlace";

    //收货地址列表
    public static final String ADDRESS_LIST_URL = BASE_URL + "/user/user/addressList";
    //删除地址
    public static final String ADDRESS_DEL_URL = BASE_URL + "/user/user/addressDel";
    //设置默认地址
    public static final String ADDRESS_DEFULT_URL = BASE_URL + "/user/user/addressDefault";
    //新增地址
    public static final String ADDRESS_ADD_URL = BASE_URL + "/user/user/addressSave";
    //编辑地址
    public static final String ADDRESS_EDIT_URL = BASE_URL + "/api/user/address/update";
    //地址详情
    public static final String ADDRESS_INFO_URL = BASE_URL + "/api/user/address/view";
    public static final String ADDRESS_SELECT_LIST_URL = BASE_URL + "/home/api/regionAppAll";
    //获取我的收藏列表
    public static final String GET_MINE_COLLECT_LIST_URL = BASE_URL + "/user/user/goodsCollect";
    //获取我的粉丝列表
    public static final String GET_MINE_FANS_LIST_URL = BASE_URL + "/user/user/distributFans";
    //获取我的浏览足迹
    public static final String GET_MINE_BROWSEFOOTPRINT_LIST_URL = BASE_URL + "/user/user/browseLog";
    //删除我的浏览足迹
    public static final String GET_MINE_DELETE_BROWSEFOOTPRINT_URL = BASE_URL + "/user/user/browseLogDel";
    //清空我的浏览足迹
    public static final String GET_MINE_CLEAR_BROWSEFOOTPRINT_URL = BASE_URL + "/user/user/browseLogDelAll";

    //获取微信支付参数
    public static final String GET_WECHAT_PAY_PARAM_URL = BASE_URL + "/user/payment/wechatApp";
    //获取支付宝支付参数
    public static final String GET_ALIPAY_PAY_PARAM_URL = BASE_URL + "/user/payment/alipay";
    //查询订单状态
    public static final String GET_ORDER_STATUS_URL = BASE_URL + "/user/payment/find";
    //获取订单列表
    public static final String GET_ORDER_LIST_URL = BASE_URL + "/user/order/index";
    //获取订单详情
    public static final String GET_ORDER_DETAIL_URL = BASE_URL + "/user/order/detail";
    //获取退款原因
    public static final String GET_ORDER_REFUND_REASON_URL = BASE_URL + "/user/order/getReturnGoodsReason";
    //申请退款
    public static final String GET_ORDER_REFUND_URL = BASE_URL + "/User/order/returnGoodsApply";
    //获取退款列表
    public static final String GET_ORDER_REFUND_LIST_URL = BASE_URL + "/user/order/returnGoodsList";
    //获取退款详情
    public static final String GET_ORDER_REFUND_DETAIL_URL = BASE_URL + "/user/order/returnGoodsInfo";
    //取消退款
    public static final String CANCEL_REFUND_URL = BASE_URL + "/User/order/returnGoodsCancel";
    //订单添加评论
    public static final String ORDER_SUBMIT_COMMENT_URL = BASE_URL + "/User/order/commentAdd";

    //获取钱包信息
    public static final String GET_WALLET_INFO_URL = BASE_URL + "/user/wallet/index";

    //获取销售总额列表
    public static final String GET_SALE_AMOUNT_LIST_URL = BASE_URL + "/user/wallet/distribut";
    //获取折让订单列表
    public static final String GET_REBATE_ORDER_LIST_URL = BASE_URL + "/user/wallet/restore";
    //获取折让订单详情
    public static final String GET_REBATE_ORDER_DETAIL_URL = BASE_URL + "/user/wallet/restoreInfo";
    //折让订单结算
    public static final String REBATE_CLOSE_URL = BASE_URL + "/user/wallet/restoreBalance";
    //获取收支明细列表
    public static final String GET_IN_COME_LIST_URL = BASE_URL + "/user/user/detailed";

    //搜索商品
    public static final String SEARCH_GOOD_URL = BASE_URL + "/home/goods/goodsSeach";

    //取消订单
    public static final String CANCEL_ORDER_URL = BASE_URL + "/user/order/cancel";
    //删除订单
    public static final String DEL_ORDER_URL = BASE_URL + "/user/order/del";
    //订单收货
    public static final String ORDER_CONFIRM_GOOD_URL = BASE_URL + "/user/order/confirm";
    //延长收货
    public static final String ORDER_EXTEND_RECEIVING_URL = BASE_URL + "/user/order/extendGoodsTime";
    //申请发票
    public static final String ORDER_APPLY_INVOICE_URL = BASE_URL + "/user/order/invoiceApply";
    //获取用户二维码信息
    public static final String GET_QR_CODE_URL = BASE_URL + "/user/user/distributinfo";
    //获取用户海报信息
    public static final String GET_POSTER_URL = BASE_URL + "/user/user/getPoster";

    //更新用户头像
    public static final String UPDATE_USER_HEAD_IMG_URL = BASE_URL + "/user/user/headImage";
    //更新用户昵称
    public static final String UPDATE_USER_NAME_URL = BASE_URL + "/user/user/userInfoEdit";
    //更新用户手机第一步
    public static final String UPDATE_USER_PHONE_ONE_URL = BASE_URL + "/user/user/setMobileOne";
    //更新用户手机第二步
    public static final String UPDATE_USER_PHONE_TWO_URL = BASE_URL + "/user/user/setMobileTwo";
    //获得支付宝授权链接
    public static final String GET_ALIPAY_AUTH_URL = BASE_URL + "/User/user/alipayInitApp";
    //提现申请
    public static final String WITHDRAW_URL = BASE_URL + "/user/user/withdrawalApply";
    //绑定三方账户
    public static final String BIND_THIRD_ACCOUNT_URL = BASE_URL + "/user/user/codeBind";

    //获取订单物流信息
    public static final String GET_ORDER_LOGISTICS_URL = BASE_URL + "/home/api/dist";

    //获取活动消息列表
    public static final String GET_ACTIVITY_MESSAGE_LIST_URL = BASE_URL + "/user/user/messageLinstActivity";
    //获取活动消息详情
    public static final String GET_ACTIVITY_MESSAGE_DETAIL_URL = BASE_URL + "/user/user/messageLinstActivityInfo";
    //获取系统消息列表
    public static final String GET_SYSTEM_MESSAGE_LIST_URL = BASE_URL + "/User/user/messageLinstSystem";
    //在线客服url
    public static final String ONLINE_SERVICE_URL = "https://www.sobot.com/chat/h5/index.html?sysNum=0ba0e184c3b046d5a8b6a891e45eff1b&source=2";
    //获得启动广告
    public static final String GET_SPLASH_AD_URL = BASE_URL + "/home/api/getStartAd";
    //获取启动页隐私协议文本内容
    public static final String GET_PRIVACY_AGREEMENT_URL = BASE_URL + "/home/html/helpCenterDetail";

    //获取我的奖品列表
    public static final String GET_MINE_LOTTERYRECORD_LIST_POST = BASE_URL + "/user/turntable/drawlist";
    //领取奖品
    public static final String GET_MINE_LOTTERY_POST = BASE_URL + "/user/turntable/draw";
}
