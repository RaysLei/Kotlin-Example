package common.framework.util;

import android.util.Log;

import common.framework.BaseApplication;

public class LogUtil {
	private static final String DEFAULT_TAG = "LogUtil";
	private static final boolean IS_PRINT = BaseApplication.getInstance().isDebug();

	public static void v(String msg) {
		if (IS_PRINT) {
			Log.v(DEFAULT_TAG, msg);
		}
	}

	public static void d(String msg) {
		if (IS_PRINT) {
			Log.d(DEFAULT_TAG, msg);
		}
	}

	public static void i(String msg) {
		if (IS_PRINT) {
			Log.i(DEFAULT_TAG, msg);
		}
	}

	public static void w(String msg) {
		if (IS_PRINT) {
			Log.w(DEFAULT_TAG, msg);
		}
	}

	public static void e(String msg) {
		if (IS_PRINT) {
			Log.e(DEFAULT_TAG, msg);
		}
	}
	
	public static void e(String msg, Throwable tr) {
		if (IS_PRINT) {
			Log.e(DEFAULT_TAG, msg, tr);
		}
	}

	public static void v(Object tag, String msg) {
		if (IS_PRINT) {
			Log.v(getTag(tag), msg);
		}
	}

	public static void d(Object tag, String msg) {
		if (IS_PRINT) {
			Log.d(getTag(tag), msg);
		}
	}

	public static void i(Object tag, String msg) {
		if (IS_PRINT) {
			Log.i(getTag(tag), msg);
		}
	}

	public static void w(Object tag, String msg) {
		if (IS_PRINT) {
			Log.w(getTag(tag), msg);
		}
	}

	public static void e(Object tag, String msg) {
		if (IS_PRINT) {
			Log.e(getTag(tag), msg);
		}
	}

	public static void e(Object tag, String msg, Throwable tr) {
		if (IS_PRINT) {
			Log.e(getTag(tag), msg, tr);
		}
	}

	private static String getTag(Object tag) {
		if (tag instanceof String) {
			return (String) tag;
		}
		return tag == null ? DEFAULT_TAG : tag.getClass().getSimpleName();
	}
}
