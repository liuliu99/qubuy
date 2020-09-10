package com.qubuyer.business.good.operation;

import com.blankj.utilcode.util.CacheDoubleUtils;
import com.qubuyer.bean.home.HomeGoodEntity;
import com.qubuyer.constant.AppConstant;

import java.io.Serializable;
import java.util.List;
import java.util.Stack;

public class SearchGoodHistoryManager {
    private static List<HomeGoodEntity> mStack;
    private volatile static SearchGoodHistoryManager mInstance;
    private final int CITY_MAX_NUMBER = 8;

    public synchronized static SearchGoodHistoryManager getInstance() {
        if (mInstance == null) {
            synchronized (SearchGoodHistoryManager.class) {
                if (mInstance == null) {
                    mInstance = new SearchGoodHistoryManager();
                }
            }
        }
        return mInstance;
    }

    public SearchGoodHistoryManager() {
        Stack<HomeGoodEntity> list = (Stack<HomeGoodEntity>) CacheDoubleUtils.getInstance().getSerializable(AppConstant.KEY_HOME_SEARCH_GOOD_HISTORY_CACHE);
        if (null != list) {
            mStack = list;
        } else {
            mStack = new Stack<>();
        }
    }

    public void push(HomeGoodEntity goodEntity) {
        HomeGoodEntity bean = getCity(goodEntity);
        if (null != bean) {
            mStack.remove(bean);
        }
        if (mStack.size() >= CITY_MAX_NUMBER) {
            mStack.remove(mStack.size() - 1);
        }
        mStack.add(0, goodEntity);
    }

    public HomeGoodEntity getCity(HomeGoodEntity goodEntity) {
        for (HomeGoodEntity bean : mStack) {
            if (bean.getGoods_name().equalsIgnoreCase(goodEntity.getGoods_name())) {
                return bean;
            }
        }
        return null;
    }

    public void popCity(HomeGoodEntity goodEntity) {
        if (mStack == null) return;
        if (goodEntity != null) {
            mStack.remove(goodEntity);
            goodEntity = null;
        }
    }

    public void popAll() {
        if (!mStack.isEmpty()) {
            mStack.clear();
        }
    }

    public List<HomeGoodEntity> getAll() {
        return mStack;
    }

    public void saveAllToDisk() {
        CacheDoubleUtils.getInstance().put(AppConstant.KEY_HOME_SEARCH_GOOD_HISTORY_CACHE, (Serializable) mStack);
    }
}
