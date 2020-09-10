package com.qubuyer.business.good.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.blankj.utilcode.util.ConvertUtils;
import com.qubuyer.R;
import com.qubuyer.bean.good.GoodSKUAttrsModel;

import java.util.List;

import am.widget.wraplayout.WrapLayout;
import androidx.collection.SimpleArrayMap;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 拼手气计算详情列表Adapter
 */
public class GoodSkuAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private SKUInterface myInterface;
    private SimpleArrayMap<Integer, String> saveClick;
    private List<GoodSKUAttrsModel.AttributesModel> attributesList; //商品Sku属性集合
    private List<GoodSKUAttrsModel.StockGoodsModel> stockGoodsList;//商品Sku数据集合
    private String[] selectedValue;   //选中的属性
    private TextView[][] childrenViews;    //二维 装所有属性

    private final int SELECTED = 0x100;
    private final int CANCEL = 0x101;

    public GoodSkuAdapter(Context ctx, SKUInterface myInterface) {
        this.mContext = ctx;
        this.myInterface = myInterface;
        saveClick = new SimpleArrayMap<>();
    }

    public void setAttributesList(List<GoodSKUAttrsModel.AttributesModel> attributesList) {
        this.attributesList = attributesList;
        childrenViews = new TextView[attributesList.size()][0];
        selectedValue = new String[attributesList.size()];
        for (int i = 0; i < attributesList.size(); i++) {
            selectedValue[i] = "";
        }
    }

    public List<GoodSKUAttrsModel.AttributesModel> getAttributesList() {
        return attributesList;
    }

    public void setStockGoodsList(List<GoodSKUAttrsModel.StockGoodsModel> stockGoodsList) {
        this.stockGoodsList = stockGoodsList;
    }

    private int focusPositionG, focusPositionC;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.good_sku_list_item, viewGroup, false);
        return new GoodSKUViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (null == viewHolder) return;
        position = viewHolder.getAdapterPosition();
        if (position < 0 || position >= attributesList.size()) return;
        GoodSKUViewHolder goodSKUViewHolder = (GoodSKUViewHolder) viewHolder;
        GoodSKUAttrsModel.AttributesModel attributesModel = attributesList.get(position);
        goodSKUViewHolder.skuKeyTextView.setText(attributesModel.getKey());
        List<String> childrens = attributesModel.getValueList();
        int childrenSize = childrens.size();
        TextView[] textViews = new TextView[childrenSize];
        goodSKUViewHolder.skuValueListFlowLayout.removeAllViews();
        goodSKUViewHolder.skuValueListFlowLayout.setGravity(WrapLayout.GRAVITY_CENTER);
        goodSKUViewHolder.skuValueListFlowLayout.setHorizontalSpacing(ConvertUtils.dp2px(10));
        goodSKUViewHolder.skuValueListFlowLayout.setVerticalSpacing(ConvertUtils.dp2px(10));
        for (int i = 0; i < childrenSize; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(ConvertUtils.dp2px(0), ConvertUtils.dp2px(0), ConvertUtils.dp2px(10), ConvertUtils.dp2px(0));
            TextView textView = new TextView(mContext);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(ConvertUtils.dp2px(10), ConvertUtils.dp2px(5), ConvertUtils.dp2px(10), ConvertUtils.dp2px(5));
            textView.setLayoutParams(params);
            textView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_good_sku_value_normal_bg));
            textView.setText(childrens.get(i));
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.common_text_color1));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            textView.setMaxLines(1);
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textViews[i] = textView;
            goodSKUViewHolder.skuValueListFlowLayout.addView(textViews[i]);
        }
        childrenViews[position] = textViews;
        initOptions();
        canClickOptions();
        getSelected();
    }

    @Override
    public int getItemCount() {
        if (null != attributesList) {
            return attributesList.size();
        }
        return 0;
    }

    private class MyOnClickListener implements View.OnClickListener {
        //点击操作 选中SELECTED   取消CANCEL
        private int operation;

        private int positionG;

        private int positionC;

        private GoodSKUAttrsModel.StockGoodsModel stockGoodsBean;

        public MyOnClickListener(int operation, int positionG, int positionC, GoodSKUAttrsModel.StockGoodsModel stockGoodsBean) {
            this.operation = operation;
            this.positionG = positionG;
            this.positionC = positionC;
            this.stockGoodsBean = stockGoodsBean;
        }

        @Override
        public void onClick(View v) {
            focusPositionG = positionG;
            focusPositionC = positionC;
            String value = childrenViews[positionG][positionC].getText().toString();
            switch (operation) {
                case SELECTED:
                    saveClick.put(positionG, positionC + "");
                    selectedValue[positionG] = value;
                    myInterface.selectedAttribute(selectedValue, stockGoodsBean);
                    break;
                case CANCEL:
                    saveClick.put(positionG, "");
                    for (int l = 0; l < selectedValue.length; l++) {
                        if (selectedValue[l].equals(value)) {
                            selectedValue[l] = "";
                            break;
                        }
                    }
                    myInterface.uncheckAttribute(selectedValue);
                    break;
            }
            initOptions();
            canClickOptions();
            getSelected();
        }
    }

    class MyOnFocusChangeListener implements View.OnFocusChangeListener {
        private int positionG;
        private int positionC;

        public MyOnFocusChangeListener(int positionG, int positionC) {
            this.positionG = positionG;
            this.positionC = positionC;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            String clickpositionC = saveClick.get(positionG);
            if (hasFocus) {
                v.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_good_sku_value_selected_bg));
                if (TextUtils.isEmpty(clickpositionC)) {
                    ((TextView) v).setTextColor(ContextCompat.getColor(mContext, R.color.theme_text_color));
                } else if (clickpositionC.equals(positionC + "")) {

                } else {
                    ((TextView) v).setTextColor(ContextCompat.getColor(mContext, R.color.theme_text_color));
                }
            } else {
                v.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_good_sku_value_normal_bg));
                if (TextUtils.isEmpty(clickpositionC)) {
                    ((TextView) v).setTextColor(ContextCompat.getColor(mContext, R.color.common_text_color1));
                } else if (clickpositionC.equals(positionC + "")) {

                } else {
                    ((TextView) v).setTextColor(ContextCompat.getColor(mContext, R.color.common_text_color1));
                }
            }
        }

    }

    /**
     * 初始化选项（不可点击，焦点消失）
     */
    private void initOptions() {
        for (int y = 0; y < childrenViews.length; y++) {
            for (int z = 0; z < childrenViews[y].length; z++) {//循环所有属性
                TextView textView = childrenViews[y][z];
                textView.setEnabled(false);
                textView.setFocusable(false);
                textView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_good_sku_value_normal_bg));
                textView.setTextColor(ContextCompat.getColor(mContext, R.color.common_text_hint_color));//变灰
            }
        }
    }

    /**
     * 找到符合条件的选项变为可选
     */
    private void canClickOptions() {
        for (int i = 0; i < childrenViews.length; i++) {
            for (int j = 0; j < stockGoodsList.size(); j++) {
                boolean filter = false;
                List<GoodSKUAttrsModel.StockGoodsModel.GoodModel> goodsInfo = stockGoodsList.get(j).getGoodModelList();
                for (int k = 0; k < selectedValue.length; k++) {
                    if (i == k || TextUtils.isEmpty(selectedValue[k])) {
                        continue;
                    }
                    if (!selectedValue[k].equals(goodsInfo.get(k).getTabValue())) {
                        filter = true;
                        break;
                    }
                }
                if (!filter) {
                    for (int n = 0; n < childrenViews[i].length; n++) {
                        TextView textView = childrenViews[i][n];//拿到所有属性TextView
                        String name = textView.getText().toString();
                        //拿到属性名称
                        if (i < goodsInfo.size() && goodsInfo.get(i).getTabValue().equals(name)) {
                            textView.setEnabled(true);//符合就变成可点击
                            textView.setFocusable(true); //设置可以获取焦点
                            //不要让焦点乱跑
                            if (focusPositionG == i && focusPositionC == n) {
                                textView.setTextColor(ContextCompat.getColor(mContext, R.color.common_text_color1));
                                textView.requestFocus();
                            } else {
                                textView.setTextColor(ContextCompat.getColor(mContext, R.color.common_text_color1));
                            }
                            textView.setOnClickListener(new MyOnClickListener(SELECTED, i, n, stockGoodsList.get(j)) {
                            });
                            textView.setOnFocusChangeListener(new MyOnFocusChangeListener(i, n) {
                            });
                        }
                    }
                }
            }
        }
    }

    /**
     * 找到已经选中的选项，让其变红
     */
    private void getSelected() {
        for (int i = 0; i < childrenViews.length; i++) {
            for (int j = 0; j < childrenViews[i].length; j++) {//拿到每行属性Item
                TextView textView = childrenViews[i][j];//拿到所有属性TextView
                String value = textView.getText().toString();
                for (int m = 0; m < selectedValue.length; m++) {
                    if (selectedValue[m].equals(value)) {
                        textView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_good_sku_value_selected_bg));
                        textView.setTextColor(ContextCompat.getColor(mContext, R.color.theme_text_color));
                        textView.setOnClickListener(new MyOnClickListener(CANCEL, i, j, null) {});
                    }
                }
            }
        }
    }

    public void setSelected(String[] attr) {
        selectedValue = attr;
        initOptions();
        canClickOptions();
        getSelected();
    }

    class GoodSKUViewHolder extends RecyclerView.ViewHolder {
        TextView skuKeyTextView;
        WrapLayout skuValueListFlowLayout;

        GoodSKUViewHolder(View itemView) {
            super(itemView);
            skuKeyTextView = (TextView) itemView.findViewById(R.id.skuKeyTextView);
            skuValueListFlowLayout = (WrapLayout) itemView.findViewById(R.id.skuValueListFlowLayout);
        }
    }

    public interface SKUInterface {
        /**
         * 选中属性
         */
        void selectedAttribute(String[] attr, GoodSKUAttrsModel.StockGoodsModel stockGoodsBean);

        /**
         * 取消属性选择
         */
        void uncheckAttribute(String[] attr);
    }

}
