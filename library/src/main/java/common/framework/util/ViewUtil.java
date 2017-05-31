package common.framework.util;

import android.app.Activity;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.Build;
import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class ViewUtil {

    public static <T> T findViewById(View v, @IdRes int id) {
        //noinspection unchecked
        return (T) v.findViewById(id);
    }

    public static <T> T findViewById(Activity activity, @IdRes int id) {
        //noinspection unchecked
        return (T) activity.findViewById(id);
    }

    /**
     * 设置View显示或隐藏
     *
     * @param view
     * @param visibility
     */
    public static void setVisibility(View view, int visibility) {
        if (view.getVisibility() != visibility) {
            view.setVisibility(visibility);
        }
    }

    /**
     * 设置输入法直接触发点击事件
     *
     * @param editText
     * @param target
     */
    public static void setEditorActionDone(EditText editText, final View target) {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    target.performClick();
                }
                return false;
            }
        });
    }

    /**
     * 设置背景
     * @param view
     * @param drawable
     */
    public static void setBackground(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    /**
     * 设置密码输入框字体同其它文本输入框
     * @param editText
     */
    public static void setPasswordFontDefault(EditText editText) {
        editText.setTypeface(Typeface.DEFAULT);
        editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    /**
     * 限制输入的字符,只能文字,数字和空格,且不能以空格开头
     * @param editText
     */
    public static void setFiltersName(EditText editText) {
        editText.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    char c = source.charAt(i);
                    if (dstart == 0 && i == 0 && Character.isSpaceChar(c)) {
                        return "";
                    }
                    if (!Character.isLetterOrDigit(c) && !Character.isSpaceChar(c)) {
                        return "";
                    }
                }
                return null;
            }
        }});
    }

    /**
     * 限制输入字母和数字
     * @param editText
     */
    public static void setFiltersUsername(EditText editText) {
        editText.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    char c = source.charAt(i);
                    if (!Character.isLowerCase(c) && !Character.isUpperCase(c) && !Character.isDigit(c)) {
                        return "";
                    }
                }
                return null;
            }
        }});
    }

    /**
     * 限制输入的数字,且为phone number
     * @param editText
     */
    public static void setFiltersMobileNumber(final EditText editText) {
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    char c = source.charAt(i);
                    if (dstart == 0 && i == 0 && c != '1') {
                        return "";
                    }
                }
                return null;
            }
        }});
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 11) {
                    editText.setText(s.toString().substring(0, 11));
                    editText.setSelection(editText.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 限制输入字母 数字 . + - _ @
     * @param editText
     */
    public static void setFiltersEmail(EditText editText) {
        editText.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    char c = source.charAt(i);
                    if (!Character.isLowerCase(c) && !Character.isUpperCase(c) && !Character.isDigit(c)
                            && c != '.' && c != '+' && c != '-' && c != '_' && c != '@') {
                        return "";
                    }
                }
                return null;
            }
        }});
    }

    public static void setBackgroundLevelList(View view, int level) {
        try {
            LevelListDrawable drawable = (LevelListDrawable) view.getBackground();
            drawable.setLevel(level);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void verifyBtnAction(final TextView btn, final EditText... ets){
        btn.setEnabled(false);
        for (EditText et : ets) {
            et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    boolean clickable = true;
                    for (EditText et : ets) {
                        if (TextUtils.isEmpty(et.getText())) {
                            clickable = false;
                            break;
                        }
                    }
                    btn.setEnabled(clickable);
                }
            });
        }
    }
}
