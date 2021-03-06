package com.qubuyer.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.qubuyer.R;


/**
 * @author Susong
 * @date 创建时间:2019/1/23
 * @description 类似购物车功能的增加和加减
 * & @version
 */
public class AddSubUtils extends LinearLayout implements View.OnClickListener, TextWatcher {

    private int mBuyMax = Integer.MAX_VALUE;  // 最大购买数量，默认最大值
    private int inputValue = 1; //购买数量
    private int inventory = Integer.MAX_VALUE; //商品库存，默认最大值
    private int mBuyMin = 1;// 商品最小购买数量，默认值为1
    private int mStep = 1; // 步长--每次增加的个数，默认是1
    private int mPosition = 0; // 设置改变的位置，默认是0; //集合数据中会用到

    private OnWarnListener mOnWarnListener;
    private OnChangeValueListener mOnChangeValueListener;

    private EditText etInput;
    private ImageViewAutoLoad icPlus;
    private ImageViewAutoLoad icMinus;

    public AddSubUtils(Context context) {
        this(context, null);
    }

    public AddSubUtils(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddSubUtils(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        //得到属性
        if (attrs != null) {
            TypedArray typeArray = getContext().obtainStyledAttributes(attrs, R.styleable.AddSubUtils);
            boolean editable = typeArray.getBoolean(R.styleable.AddSubUtils_editable, true);
            String location = typeArray.getString(R.styleable.AddSubUtils_location);
            // 左右两面的宽度
            int ImageWidth = typeArray.getDimensionPixelSize(R.styleable.AddSubUtils_ImageWidth, -1);
            // 左右两面的高度
            int ImageHeight = typeArray.getDimensionPixelSize(R.styleable.AddSubUtils_ImageHeight, -1);
            // 中间内容框的宽度
            int contentWidth = typeArray.getDimensionPixelSize(R.styleable.AddSubUtils_contentWidth, -1);
            // 中间字体的大小
            int contentTextSize = typeArray.getDimensionPixelSize(R.styleable.AddSubUtils_contentTextSize, -1);
            // 中间字体的颜色
            int contentTextColor = typeArray.getColor(R.styleable.AddSubUtils_contentTextColor, 0xff000000);
            // 整个控件的background
            Drawable background = typeArray.getDrawable(R.styleable.AddSubUtils_all_background);
            // 左面控件的背景
            Drawable leftBackground = typeArray.getDrawable(R.styleable.AddSubUtils_leftBackground);
            // 右面控件的背景
            Drawable rightBackground = typeArray.getDrawable(R.styleable.AddSubUtils_rightBackground);
            // 中间控件的背景
            Drawable contentBackground = typeArray.getDrawable(R.styleable.AddSubUtils_contentBackground);
            // 左面控件的资源
            int leftResources = typeArray.getResourceId(R.styleable.AddSubUtils_leftResources, R.drawable.addsubutils_ic_minus);
            // 右面控件的资源
            int rightResources = typeArray.getResourceId(R.styleable.AddSubUtils_rightResources, R.drawable.addsubutils_ic_plus);
            // 资源回收
            typeArray.recycle();

            if ("start".equals(location)) {
                //把布局和当前类形成整体
                LayoutInflater.from(context).inflate(R.layout.add_sub_start_layout, this);
            } else if ("end".equals(location)) {
                //把布局和当前类形成整体
                LayoutInflater.from(context).inflate(R.layout.add_sub_end_layout, this);
            } else {
                //把布局和当前类形成整体
                LayoutInflater.from(context).inflate(R.layout.add_sub_layout, this);
            }

            icPlus = findViewById(R.id.ic_plus);
            icMinus = findViewById(R.id.ic_minus);
            etInput = findViewById(R.id.et_input);

            icPlus.setOnClickListener(this);
            icMinus.setOnClickListener(this);
            etInput.addTextChangedListener(this);

            setEditable(editable);
            etInput.setTextColor(contentTextColor);

            LayoutParams textParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            // 设置两边按钮的宽度
            if (ImageWidth > 0) {
                textParams.width = ImageWidth;
            }
            if (ImageHeight > 0) {
                textParams.height = ImageHeight;
            }
            icPlus.setLayoutParams(textParams);
            icMinus.setLayoutParams(textParams);

            // 设置中间输入框的宽度
            if (contentWidth > 0) {
                LayoutParams textParams1 = new LayoutParams(contentWidth, ImageHeight);
                etInput.setLayoutParams(textParams1);
            }
            if (contentTextColor > 0) {
                etInput.setTextSize(contentTextColor);
            }
            if (contentTextSize > 0) {
                etInput.setTextSize(contentTextSize);
            }
            if (background != null) {
                setBackgroundDrawable(background);
            } else {
                setBackgroundResource(R.drawable.addsubutils_add_sub_bg);
            }

            if (contentBackground != null) {
                etInput.setBackground(contentBackground);
            }

            if (leftBackground != null) {
                icMinus.setBackground(leftBackground);
            }

            if (rightBackground != null) {
                icPlus.setBackground(rightBackground);
            }
            if (leftResources > 0) {
//                icMinus.setImageResource(leftResources);
                icMinus.setDrawableId(context, leftResources);
            }
            if (rightResources > 0) {
//                icPlus.setImageResource(rightResources);
                icPlus.setDrawableId(context, rightResources);
            }
        }
    }

    private void setEditable(boolean editable) {
        if (editable) {
            etInput.setFocusable(true);
            etInput.setKeyListener(new DigitsKeyListener());
        } else {
            etInput.setFocusable(false);
            etInput.setKeyListener(null);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ic_plus) {
            etInput.clearFocus();
            // 加
            if (inputValue < Math.min(mBuyMax, inventory)) {
                inputValue += mStep;
                //正常添加
                etInput.setText("" + inputValue);
                etInput.setSelection(etInput.getText().length());
            } else if (inventory < mBuyMax) {
                //库存不足
                warningForInventory();
            } else {
                //超过最大购买数
                warningForBuyMax();
            }
        } else if (id == R.id.ic_minus) {
            etInput.clearFocus();
            // 减
            if (inputValue > mBuyMin) {
                inputValue -= mStep;
                etInput.setText(inputValue + "");
                etInput.setSelection(etInput.getText().length());
            } else {
                // 低于最小购买数
                warningForBuyMin();
            }
        } else if (id == R.id.et_input) {
            // 输入框
            etInput.setSelection(etInput.getText().toString().length());
            etInput.requestFocus();
        }
    }

    /**
     * 低于最小购买数
     * Warning for buy min.
     */
    private void warningForBuyMin() {
        inputValue = 1;
        etInput.setText(inputValue + "");
        etInput.setSelection(etInput.getText().length());
        if (mOnWarnListener != null) mOnWarnListener.onWarningForBuyMin(etInput, mBuyMin);
    }

    /**
     * 超过的最大购买数限制
     * Warning for buy max.
     */
    private void warningForBuyMax() {
        inputValue = 1;
        etInput.setText(inputValue + "");
        etInput.setSelection(etInput.getText().length());
        if (mOnWarnListener != null) mOnWarnListener.onWarningForBuyMax(etInput, mBuyMax);
    }

    /**
     * 超过的库存限制
     * Warning for inventory.
     */
    private void warningForInventory() {
        inputValue = 1;
        etInput.setText(inputValue + "");
        etInput.setSelection(etInput.getText().length());
        if (mOnWarnListener != null) mOnWarnListener.onWarningForInventory(etInput, inventory);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        onNumberInput();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * 监听输入的数据变化
     */
    private void onNumberInput() {
        //当前数量
        int count = getNumber();
        if (count < mBuyMin) {
            //手动输入
            inputValue = mBuyMin;
            etInput.setText(inputValue + "");
            etInput.setSelection(etInput.getText().length());
            if (mOnChangeValueListener != null) {
                mOnChangeValueListener.onChangeValue(inputValue, mPosition);
            }
            return;
        }
        int limit = Math.min(mBuyMax, inventory);
        if (count > limit) {
            if (inventory < mBuyMax) {
                //库存不足
                warningForInventory();
            } else {
                //超过最大购买数
                warningForBuyMax();
            }
        } else {
            inputValue = count;
            if (mOnChangeValueListener != null) {
                mOnChangeValueListener.onChangeValue(inputValue, mPosition);
            }
        }
    }

    public AddSubUtils setCurrentNumber(int currentNumber) {
        if (currentNumber < mBuyMin) {
            inputValue = mBuyMin;
        } else {
            inputValue = Math.min(Math.min(mBuyMax, inventory), currentNumber);
        }
        etInput.setText(inputValue + "");
        return this;
    }

    public int getInventory() {
        return inventory;
    }

    public AddSubUtils setInventory(int inventory) {
        this.inventory = inventory;
        return this;
    }

    public AddSubUtils setBean(Object bean) {
        this.inventory = inventory;
        return this;
    }

    public int getBuyMax() {
        return mBuyMax;
    }

    public AddSubUtils setBuyMax(int buyMax) {
        mBuyMax = buyMax;
        return this;
    }

    public AddSubUtils setPosition(int position) {
        mPosition = position;
        return this;
    }

    public int getPosition() {
        return mPosition;
    }

    public AddSubUtils setBuyMin(int buyMin) {
        mBuyMin = buyMin;
        return this;
    }

    public AddSubUtils setOnWarnListener(OnWarnListener onWarnListener) {
        mOnWarnListener = onWarnListener;
        return this;
    }

    public AddSubUtils setOnChangeValueListener(OnChangeValueListener onChangeValueListener) {
        mOnChangeValueListener = onChangeValueListener;
        return this;
    }

    public int getStep() {
        return mStep;
    }

    public AddSubUtils setStep(int step) {
        mStep = step;
        return this;
    }

    /**
     * 得到输入框的数量
     *
     * @return
     */
    public int getNumber() {
        try {
            return Integer.parseInt(etInput.getText().toString());
        } catch (NumberFormatException e) {

        }
        etInput.setText(mBuyMin + "");
        return mBuyMin;
    }

    public interface OnWarnListener {

        void onWarningForInventory(EditText editText, int inventory);

        void onWarningForBuyMax(EditText editText, int max);

        void onWarningForBuyMin(EditText editText, int min);
    }

    public interface OnChangeValueListener {

        void onChangeValue(int value, int position);
    }
}
