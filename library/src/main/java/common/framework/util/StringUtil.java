package common.framework.util;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;

public class StringUtil {

	/**
	 * 用符号隔开
	 * @param source 
	 * @param mark 符号
	 * @param num 间隔数
	 * @return
	 */
	public static String space(String source, String mark, int num) {
        if (source == null || mark == null || num <= 0) {
            return source;
        }
        source = source.replace(mark, "");
        StringBuilder sb = new StringBuilder();
		for(int i = 0, length = source.length(); i < length; i += num) {
			if (i > 0) {
				sb.append(mark);
			}
			if (i + num >= length) {
				sb.append(source.subSequence(i, length));
			} else {
				sb.append(source.subSequence(i, i + num));
			}
		}
		return sb.toString();
	}

    public static String arrayToString(String[] array, String mark) {
        if (array == null || array.length == 0 || TextUtils.isEmpty(mark)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean isAppend = false;
        for (String s : array) {
            if (isAppend) {
                sb.append(mark);
            }
            sb.append(s);
            isAppend = true;
        }
        return sb.toString();
    }

    public static Spanned fromHtml(Context context, int resId, Object... formatArgs) {
        String source = context.getString(resId, formatArgs);
        return fromHtml(source);
    }

    public static Spanned fromHtml(String source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(source);
        }
    }

    public static String substring(String s, int endIndex) {
        if (s != null) {
            return s.substring(0, (endIndex > s.length() ? s.length() : endIndex)).toUpperCase();
        }
        return "";
    }

    public static boolean equalsIgnoreCase(String a, String b) {
        return a != null && a.equalsIgnoreCase(b);
    }
}
