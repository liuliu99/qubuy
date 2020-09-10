package com.qubuyer.utils;

import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * EditText工具类
 */
public class EditTextUtils {
    /**
     * 禁止EditText输入空格
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpace(final EditText editText) {
//        InputFilter filter = new InputFilter() {
//            @Override
//            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
//                if (source.equals(" ") || source.toString().contentEquals("\n")) {
//                    return "";
//                } else {
//                    return null;
//                }
//            }
//        };
//        editText.setFilters(new InputFilter[]{filter});


        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int editStart = editText.getSelectionStart();
                if (s.toString().contains(" ")) {
                    String[] str = s.toString().split(" ");
                    String str1 = "";
                    for (int i = 0; i < str.length; i++) {
                        str1 += str[i];
                    }
                    editText.setText(str1);
                    editText.setSelection(editStart > str1.length() ? editStart - 1 : editStart);
                }
            }
        });
    }

    /**
     * 禁止EditText输入特殊字符
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpeChat(EditText editText) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.find()) return "";
                else return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    /**
     * 用户输入小数点的时候 监听小数点后面的位数，只要大于两位就立马删掉
     *
     * @param editText
     */
    public static void setPricePoint(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

        });
    }

    /**
     * 设置EditText最大输入长度
     *
     * @param editText
     */
    public static void setEditTextMaxLength(final EditText editText, final int maxLength) {
//        InputFilter[] filters = {new InputFilter.LengthFilter(maxLength)};
//        editText.setFilters(filters);
        mExitText = editText;
        mMaxLength = maxLength;
        editText.addTextChangedListener(textWatcher);
    }

    private static EditText mExitText;
    private static int mMaxLength;

    private static TextWatcher textWatcher = new TextWatcher() {
        private int editStart;
        private int editEnd;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            editStart = mExitText.getSelectionStart();
            editEnd = mExitText.getSelectionEnd();

            // 先去掉监听器，否则会出现栈溢出
            mExitText.removeTextChangedListener(textWatcher);
            if (!TextUtils.isEmpty(mExitText.getText())) {
                String etstring = mExitText.getText().toString().trim();
                while (calculateLength(s.toString()) > mMaxLength) {
                    s.delete(editStart - 1, editEnd);
                    editStart--;
                    editEnd--;
                }
            }

            boolean isSpacing = false;
            String str1 = "";
            if (s.toString().contains(" ")) {
                String[] str = s.toString().split(" ");
                for (int i = 0; i < str.length; i++) {
                    str1 += str[i];
                }
                isSpacing = true;
            } else {
                str1 = s.toString();
            }

            mExitText.setText(str1);
            mExitText.setSelection(isSpacing ? editStart - 1 : editStart);
            // 恢复监听器
            mExitText.addTextChangedListener(textWatcher);
            // end by zyf --------------------------
        }

        private int calculateLength(String etstring) {
            char[] ch = etstring.toCharArray();
            int varlength = 0;
            for (int i = 0; i < ch.length; i++) {
                // changed by zyf 0825 , bug 6918，加入中文标点范围 ， TODO 标点范围有待具体化
                if ((ch[i] >= 0x2E80 && ch[i] <= 0xFE4F) || (ch[i] >= 0xA13F && ch[i] <= 0xAA40) || ch[i] >= 0x80) { // 中文字符范围0x4e00 0x9fbb
                    varlength++;
                } else {
                    varlength++;
                }
            }
            // 这里也可以使用getBytes,更准确嘛
            // varlength = etstring.getBytes(CharSet.forName("GBK")).lenght;// 编码根据自己的需求，注意u8中文占3个字节...
            return varlength;
        }
    };
}
