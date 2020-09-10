package com.qubuyer.business.good.view;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.qubuyer.R;
import com.qubuyer.bean.good.GoodSKUAttrsModel;
import com.qubuyer.bean.home.HomeGoodEntity;
import com.qubuyer.business.good.adapter.GoodSkuAdapter;
import com.qubuyer.customview.AddSubUtils;
import com.qubuyer.customview.DividerItemDecoration;
import com.qubuyer.customview.ImageViewAutoLoad;
import com.qubuyer.customview.MaxHeightRecyclerView;
import com.qubuyer.utils.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 商品属性弹窗
 */
public class GoodSKUPopupWindow extends PopupWindow implements View.OnClickListener, GoodSkuAdapter.SKUInterface {
    //当前页面上下文
    private Context mContext;
    //商品规格List
    private List<HomeGoodEntity.GoodSKUKeyValue> mGoodSKUKeyValueList;
    //商品规格属性List
    private List<HomeGoodEntity.GoodSKUModel> mGoodSKUModelList;
    //添加商品临时数量
    private int mTempAddGoodCount;


    //商品图片ImageView
    private ImageViewAutoLoad goodImage;
    //商品名称TextView
    private TextView goodNameTextView;
    //商品价格TextView
    private TextView goodPriceTextView;
    //商品折扣价格TextView
    private TextView goodDisPriceTextView;
    //商品选择的属性描述TextView
    private TextView goodSelectedDescTextView;
    //加入购物车按钮TextView
    private TextView addShoppingCartTextView;
    //立即购买按钮TextView
    private TextView nowBuyTextView;
    //购买数量按钮
    private AddSubUtils asu_count;
    //确定按钮
    private TextView selectAffirmTextView;


    //商品SKU Adapter
    private GoodSkuAdapter goodSKUAdapter;

    //商品详情页操作商品属性Listener
    private OnGoodDetailOperationGoodSkuListener mOnGoodDetailOperationGoodSkuListener;
    //购物车列表页操作商品属性Listener
    private OnCartOperationGoodSkuListener mOnCartOperationGoodSkuListener;

    //选中的商品SKU模型
    private HomeGoodEntity.GoodSKUModel mSelectedGoodSKUModel;

    //默认商品信息
    private HomeGoodEntity mDefaultGoods;

    //选择的商品sku值
    private String[] mSelectedSkuValue;

    public GoodSKUPopupWindow(Context context) {
        this(context, null);
        initView(context);
    }

    public GoodSKUPopupWindow(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        View rootView = View.inflate(context, R.layout.good_sku_layout, null);
        goodImage = rootView.findViewById(R.id.goodImage);
        goodNameTextView = rootView.findViewById(R.id.goodNameTextView);
        goodPriceTextView = rootView.findViewById(R.id.goodPriceTextView);
        goodDisPriceTextView = rootView.findViewById(R.id.goodDisPriceTextView);
        goodSelectedDescTextView = rootView.findViewById(R.id.goodSelectedDescTextView);
        addShoppingCartTextView = rootView.findViewById(R.id.addShoppingCartTextView);
        addShoppingCartTextView.setOnClickListener(this);
        nowBuyTextView = rootView.findViewById(R.id.nowBuyTextView);
        nowBuyTextView.setOnClickListener(this);
        selectAffirmTextView = rootView.findViewById(R.id.selectAffirmTextView);
        selectAffirmTextView.setOnClickListener(this);

//        RelativeLayout goodListContainer = rootView.findViewById(R.id.goodListContainer);
//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        if (AvoidRiskUtil.getAvoidRisk()) {
//            layoutParams.setMargins(0, DensityUtil.dip2px(139), 0, DensityUtil.dip2px(100));
//        } else {
//            layoutParams.setMargins(0, DensityUtil.dip2px(158), 0, DensityUtil.dip2px(100));
//        }
//        goodListContainer.setLayoutParams(layoutParams);

        MaxHeightRecyclerView goodListRecyclerView = rootView.findViewById(R.id.goodListRecyclerView);
        int scrheight = ScreenUtils.getScreenHeight() / 3;
        goodListRecyclerView.setRecyclerViewHeight(scrheight);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        linearLayoutManager.setAutoMeasureEnabled(true);
        goodListRecyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration decoration = new DividerItemDecoration(mContext.getResources().getDrawable(R.drawable.shape_recyclerview_divider));
        goodListRecyclerView.addItemDecoration(decoration);
        goodListRecyclerView.setFocusable(false);
        goodSKUAdapter = new GoodSkuAdapter(mContext, this);
        goodListRecyclerView.setAdapter(goodSKUAdapter);

        asu_count = rootView.findViewById(R.id.asu_count);
        asu_count.setBuyMax(99)       // 最大购买数，默认为int的最大值
                .setInventory(99)       // 库存，默认为int的最大值
                .setCurrentNumber(1)    // 设置当前数，默认为1
                .setStep(1)             // 步长，默认为1
                .setBuyMin(1)           // 购买的最小值，默认为1
                .setOnWarnListener(new AddSubUtils.OnWarnListener() {
                    @Override
                    public void onWarningForInventory(EditText editText, int inventory) {
                        ToastUtils.showShort("库存不足");
                    }

                    @Override
                    public void onWarningForBuyMax(EditText editText, int max) {
                        ToastUtils.showShort("超过最大购买数");
                    }

                    @Override
                    public void onWarningForBuyMin(EditText editText, int min) {
                        ToastUtils.showShort("低于最小购买数");
                    }
                }).setOnChangeValueListener(new AddSubUtils.OnChangeValueListener() {
            @Override
            public void onChangeValue(int value, int position) {
                if (null != mOnCartOperationGoodSkuListener) {
                    mOnGoodDetailOperationGoodSkuListener.onCountAddMinus(value);
                }
            }
        });

        setContentView(rootView);
        setOutsideTouchable(true);
        setFocusable(true);
    }

    /**
     * 设置商品数量
     *
     * @param mTempAddGoodCount
     */
    public void setTempAddGoodCount(int mTempAddGoodCount) {
        this.mTempAddGoodCount = mTempAddGoodCount;
        asu_count.setStep(mTempAddGoodCount);
    }

    /**
     * 设置商品信息
     */
    public void setGoods(HomeGoodEntity goods) {
        mDefaultGoods = goods;
        setGoodDetailPageGoodInfo();
    }

    /**
     * 设置商品详情页商品信息
     */
    public void setGoodDetailPageGoodInfo() {
        goodImage.setUriRoundCornerImage(mContext, mDefaultGoods.getOriginal_img(), 10);
        goodNameTextView.setText(mDefaultGoods.getGoods_name());
        goodPriceTextView.setText("¥" + Html.fromHtml(toHtml(mDefaultGoods.getShop_price())));
        goodDisPriceTextView.setText("折让" + mDefaultGoods.getRestore());
        goodSelectedDescTextView.setText("已选:");
    }

    /**
     * 设置商品详情页商品信息
     */
    public void setGoodPropertyToView(HomeGoodEntity.GoodSKUModel goodSKUModel, String[] selectedSkuValues) {
        mSelectedGoodSKUModel = goodSKUModel;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; null != selectedSkuValues && i < selectedSkuValues.length; i++) {
            if (!TextUtils.isEmpty(selectedSkuValues[i])) {
                sb.append(selectedSkuValues[i]).append(",");
            }
        }
        if (null != goodSKUModel) {
//            goodImage.setUriRoundCornerImage(goodSKUModel.getIconImage(), 10);
            goodSelectedDescTextView.setText("已选: " + goodSKUModel.getSpecPropertyValue());
        } else if (sb.length() > 0) {
            String tempString = sb.toString();
            if (tempString.length() > 0 && tempString.substring(tempString.length() - 1).equals(",")) {
                tempString = tempString.substring(0, tempString.length() - 1);
            }
            goodSelectedDescTextView.setText("已选: " + tempString);
        } else {
            setGoodDetailPageGoodInfo();
        }
        setGoodPriceToView();
    }

    private void setGoodPriceToView() {
        String goodPrice;
        String restrorePrice;
        if (null != mSelectedGoodSKUModel) {
            goodPrice = mSelectedGoodSKUModel.getPrice();
            restrorePrice = mSelectedGoodSKUModel.getRestrore_price();
        } else {
            goodPrice = mDefaultGoods.getShop_price();
            restrorePrice = mDefaultGoods.getRestore();
        }
        goodPriceTextView.setText("¥" + StringUtil.formatAmount(goodPrice, 2));
        goodDisPriceTextView.setText("¥" + StringUtil.formatAmount(restrorePrice, 2));
    }

    private String toHtml(String money) {
        String[] arr = money.split("[.]");
        if (arr.length > 1) {
            return "<font color=\"#f3596e\"><big>" + arr[0] + "</big>" + "<small>." + arr[1] + "</small></font>";
        } else {
            return "<font color=\"#f3596e\"><big>" + money + "</big>" + "<small>.00</small></font>";
        }
    }

    /**
     * 设置商品SKU信息
     *
     * @param mGoodSKUKeyValueList
     */
    public void setGoodSKUInfo(List<HomeGoodEntity.GoodSKUKeyValue> mGoodSKUKeyValueList, List<HomeGoodEntity.GoodSKUModel> mGoodSKUModelList) {
        this.mGoodSKUKeyValueList = mGoodSKUKeyValueList;
        this.mGoodSKUModelList = mGoodSKUModelList;
        ArrayList<GoodSKUAttrsModel.AttributesModel> attributes = new ArrayList<>();
        GoodSKUAttrsModel.AttributesModel attributesModel;
        for (int i = 0; i < mGoodSKUKeyValueList.size(); i++) {
            attributesModel = new GoodSKUAttrsModel.AttributesModel();
            attributesModel.setTabID(i);
            attributesModel.setKey(mGoodSKUKeyValueList.get(i).getKey());
            attributesModel.setValueList(Arrays.asList(mGoodSKUKeyValueList.get(i).getValue().split(",")));
            attributes.add(attributesModel);
        }
        ArrayList<GoodSKUAttrsModel.StockGoodsModel> stockGoods = new ArrayList<>();
        GoodSKUAttrsModel.StockGoodsModel stockGoodsModel;
        GoodSKUAttrsModel.StockGoodsModel.GoodModel goodModel;
        for (HomeGoodEntity.GoodSKUModel goodSkuModel : mGoodSKUModelList) {
            if (goodSkuModel.getStockNum() <= 0) continue;
            stockGoodsModel = new GoodSKUAttrsModel.StockGoodsModel();
            stockGoodsModel.setSkuId(goodSkuModel.getSkuId());
//            stockGoodsModel.setMainImage(goodSkuModel.getMainImage());
            stockGoodsModel.setPrice(!TextUtils.isEmpty(goodSkuModel.getPrice()) ? Double.parseDouble(goodSkuModel.getPrice()) : 0);
//            stockGoodsModel.setSalesNum(goodSkuModel.getSalesNum());
            stockGoodsModel.setStockNum(goodSkuModel.getStockNum());
//            stockGoodsModel.setIconImage(goodSkuModel.getIconImage());
            List<GoodSKUAttrsModel.StockGoodsModel.GoodModel> goodModelList = new ArrayList<>();
            String[] specTitleArray = goodSkuModel.getSpecTitle().split(",");
            String[] specPropertyValue = goodSkuModel.getSpecPropertyValue().split(",");
            for (int i = 0; i < specPropertyValue.length; i++) {
                goodModel = new GoodSKUAttrsModel.StockGoodsModel.GoodModel();
                goodModel.setTabName(specTitleArray[i]);
                goodModel.setTabValue(specPropertyValue[i]);
                goodModelList.add(goodModel);
            }
            stockGoodsModel.setGoodModelList(goodModelList);
            stockGoods.add(stockGoodsModel);
        }
        goodSKUAdapter.setAttributesList(attributes);
        goodSKUAdapter.setStockGoodsList(stockGoods);
        goodSKUAdapter.notifyDataSetChanged();
    }

    /**
     * 设置已选择商品sku
     */
    public void setSelectedGoodSku(String[] selectedSkuValues) {
        mSelectedSkuValue = selectedSkuValues;
        if (null != selectedSkuValues) {
            goodSKUAdapter.setSelected(selectedSkuValues);
        }
        setGoodPropertyToView(null, selectedSkuValues);
    }

    /**
     * 设置商品详情页操作商品属性Listener
     */
    public void setOnAddOrMinusGoodCountListener(OnGoodDetailOperationGoodSkuListener mOnGoodDetailOperationGoodSkuListener) {
        this.mOnGoodDetailOperationGoodSkuListener = mOnGoodDetailOperationGoodSkuListener;
    }

    /**
     * 设置购物车列表页页操作商品属性Listener
     */
    public void setOnCartOperationGoodSkuListener(OnCartOperationGoodSkuListener mOnCartOperationGoodSkuListener) {
        this.mOnCartOperationGoodSkuListener = mOnCartOperationGoodSkuListener;
    }

    /**
     * 设置调用弹框类型 0:从商品详情页调用 1:从购物车编辑调用
     *
     * @param type
     */
    public void setCallType(int type) {
        switch (type) {
            case 0:
                addShoppingCartTextView.setVisibility(View.VISIBLE);
                nowBuyTextView.setVisibility(View.VISIBLE);
                selectAffirmTextView.setVisibility(View.GONE);
                break;
            case 1:
                addShoppingCartTextView.setVisibility(View.GONE);
                nowBuyTextView.setVisibility(View.GONE);
                selectAffirmTextView.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addShoppingCartTextView:
                if (!checkSelectedSku()) return;
                if (null != mOnGoodDetailOperationGoodSkuListener) {
                    mOnGoodDetailOperationGoodSkuListener.onAddShoppingCart();
                }
                break;
            case R.id.nowBuyTextView:
                if (!checkSelectedSku()) return;
                if (null != mOnGoodDetailOperationGoodSkuListener) {
                    mOnGoodDetailOperationGoodSkuListener.onBuyNow();
                }
                break;
        }
    }

    /**
     * 检查选择的sku信息是否完整
     */
    private boolean checkSelectedSku() {
        boolean isFull = true;
        StringBuilder sb = new StringBuilder("请选择:");
        if (null != mSelectedSkuValue) {
            for (int i = 0; i < mSelectedSkuValue.length; i++) {
                if (TextUtils.isEmpty(mSelectedSkuValue[i])) {
                    isFull = false;
                    sb.append(mGoodSKUKeyValueList.get(i).getKey() + " ");
                }
            }
        } else {
            isFull = false;
            for (HomeGoodEntity.GoodSKUKeyValue goodSkuKeyValue : mGoodSKUKeyValueList) {
                sb.append(goodSkuKeyValue.getKey() + " ");
            }
        }
        if (!isFull) {
            ToastUtils.showShort(sb.toString());
            return false;
        }
        return true;
    }

    @Override
    public void selectedAttribute(String[] attr, GoodSKUAttrsModel.StockGoodsModel stockGoodsBean) {
        mSelectedSkuValue = attr;
        int count = 0;
        for (int i = 0; i < attr.length; i++) {
            if (!TextUtils.isEmpty(attr[i])) {
                count++;
            }
        }
        HomeGoodEntity.GoodSKUModel goodSkuModelTemp = null;
        if (count == goodSKUAdapter.getAttributesList().size()) {
            for (HomeGoodEntity.GoodSKUModel goodSkuModel : mGoodSKUModelList) {
                if (goodSkuModel.getSkuId() == stockGoodsBean.getSkuId()) {
                    goodSkuModelTemp = goodSkuModel;
                }
            }
        }
        setGoodPropertyToView(goodSkuModelTemp, mSelectedSkuValue);
        if (null != mOnGoodDetailOperationGoodSkuListener) {
            mOnGoodDetailOperationGoodSkuListener.onSetSelectedSkuGoodInfo(goodSkuModelTemp, attr);
        }
    }

    @Override
    public void uncheckAttribute(String[] attr) {
        mSelectedSkuValue = attr;
        setGoodPropertyToView(null, mSelectedSkuValue);
        if (null != mOnGoodDetailOperationGoodSkuListener) {
            mOnGoodDetailOperationGoodSkuListener.onSetSelectedSkuGoodInfo(null, attr);
        }
    }

    public interface OnGoodDetailOperationGoodSkuListener {
        void onCountAddMinus(int count);

        void onAddShoppingCart();

        void onBuyNow();

        void onSetSelectedSkuGoodInfo(HomeGoodEntity.GoodSKUModel goodSKUModel, String[] selectedGoodSkuValues);
    }

    public interface OnCartOperationGoodSkuListener {
        void onSelectedGoodSku(HomeGoodEntity.GoodSKUModel goodSKUModel, int selectedGoodCount);
    }
}
