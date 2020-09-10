package com.qubuyer.constant;

import android.os.Environment;
import android.util.LongSparseArray;

import com.qubuyer.BuildConfig;

/**
 * @author Susong
 * @date 创建时间:2018/12/19
 * @description 参数常量类
 * & @version
 */
public class AppConstant {
    //是否是测试环境
    public static final boolean DEBUG = BuildConfig.DEBUG;
    public static final int LOAD_TYPE_NORMAL = 0x1;
    // 上拉加载更多
    public static final int LOAD_TYPE_UP = 0x2;
    // 下拉刷新
    public static final int LOAD_TYPE_DOWN = 0x3;
    public static final int COUNT_MAX = 10;
    public static final int COUNT_SMAll = 5;
    // APP_ID
    public static final String WECHAT_APP_ID = "wxada57b82e3e68827";
    //activity跳转传递数据key
    public static final String INTENT_EXTRA_KEY = "intent_extra_key";
    //登录用户信息
    public static final String LOGIN_USER_INFO = "login_user_info";

    public static final String SHARE_FILE_NAME = "share_file_name";
    public static final String PICTURE_PATH = Environment.getExternalStorageDirectory() + "/qutrip/Pictures/";
    public static final String PICTURE_SUFFIX = "?imageView2/1/w/200/h/200/format/jpg/sharpen/1/q/100";
    public static int CODE_NO_LOGIN = -10;    // 未登录
    public static int CODE_NO_BIND = 101;    // 用户未绑定
    public static int CODE_GO_SET_PWD = 102;    //去设置密码
    public static int CODE_BIND_FAIL = 110;    //绑定失败

    public static int CODE_LOGIN_OVERTIME = 1002;  //登录过期
    public static int CODE_LOGIN = 1003;  //请登录
    public static int CODE_TOKEN_NO_EXIST = 1004;  //token不存在
    public static int CODE_TOKEN_OVERTIME = 1005;  //token已过期
    public static int CODE_TOKEN_INFO_UNUSUAL = 1006;  //token信息不正确
    public static int CODE_ACCOUNT_NO_EXIST = 1007;  //账号不存在
    public static final LongSparseArray NET_RESPONSE_CODE;

    public static final String WECHAT_OPEN_ID = "wechat_open_id";

    public static final String SPF_KEY_LOGIN_TOKEN = "spf_key_login_token";

    public static final String SP_IS_FIRST_SHOW_PRIVACY_AGREEMENT = "is_first_show_privacy_agreement";

    static {
        NET_RESPONSE_CODE = new LongSparseArray() {
            {
//                put(CODE_NO_LOGIN, CODE_NO_LOGIN);
//                put(CODE_NO_BIND, CODE_NO_BIND);
//                put(CODE_GO_SET_PWD, CODE_GO_SET_PWD);
//                put(CODE_BIND_FAIL, CODE_BIND_FAIL);
//                put(CODE_LOGIN_OVERTIME, CODE_LOGIN_OVERTIME);
//                put(CODE_LOGIN, CODE_LOGIN);
//                put(CODE_TOKEN_NO_EXIST, CODE_TOKEN_NO_EXIST);
//                put(CODE_TOKEN_OVERTIME, CODE_TOKEN_OVERTIME);
//                put(CODE_TOKEN_INFO_UNUSUAL, CODE_TOKEN_INFO_UNUSUAL);
//                put(CODE_ACCOUNT_NO_EXIST, CODE_ACCOUNT_NO_EXIST);
            }
        };
    }

    //缓存服务器时间
    public static final String KEY_SERVER_TIME_KEY = "server_time_key";
    //首页banner缓存
    public static final String KEY_HOME_BANNER_CACHE = "home_banner_cache";
    //首页icon缓存
    public static final String KEY_HOME_CATEGORY_CACHE = "home_category_cache";
    //首页销售总额缓存
    public static final String KEY_HOME_SALE_AMOUNT_CACHE = "home_sale_amount_cache";
    //首页销售排行榜缓存
    public static final String KEY_HOME_SALE_TOP_CACHE = "home_sale_top_cache";
    //首页销售排行榜用户金额缓存
    public static final String KEY_HOME_SALE_TOP_USER_MONEY_CACHE = "home_sale_top_user_money_cache";
    //首页会员精选缓存
    public static final String KEY_HOME_CHOICENESS_CACHE = "home_choiceness_cache";
    //首页每日限量缓存
    public static final String KEY_HOME_LIMIT_CACHE = "home_limit_cache";
    //首页热门推荐缓存
    public static final String KEY_HOME_SPECIAL_CACHE = "home_special_cache";
    //首页新品首发缓存
    public static final String KEY_HOME_FIRSTPUBLISH_CACHE = "home_firstpublish_cache";
    //首页超级返缓存
    public static final String KEY_HOME_SUPER_RETURN_CACHE = "home_super_return_cache";
    //首页每日折扣缓存
    public static final String KEY_HOME_DAILYDISCOUNT_CACHE = "home_dailydiscount_cache";
    //首页商品列表缓存
    public static final String KEY_HOME_GOODS_LIST_CACHE = "home_goods_list_cache";
    //首页搜索商品页搜索历史记录缓存
    public static final String KEY_HOME_SEARCH_GOOD_HISTORY_CACHE = "home_search_good_history_cache";
}
