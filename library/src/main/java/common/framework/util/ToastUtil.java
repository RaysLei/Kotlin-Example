package common.framework.util;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Rays on 2017/1/12.
 */
public class ToastUtil {
    private static ToastUtil instance;
    private Toast toast;
    private Context context;
    private int gravity;

    public static ToastUtil getInstance() {
        return instance;
    }

    public static void init(Context context) {
        init(context, Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
    }

    public static void init(Context context, int gravity) {
        instance = new ToastUtil(context, gravity);
    }

    private ToastUtil(Context context, int gravity) {
        this.context = context.getApplicationContext();
        this.gravity = gravity;
    }

    public void showToast(@StringRes int resId) {
        showToast(context.getString(resId));
    }

    public void showToast(CharSequence text) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            showToastOnUiThread(text);
        } else {
            Looper.prepare();
            showToastOnUiThread(text);
            Looper.loop();
        }
    }

    private void showToastOnUiThread(CharSequence text) {
        if (toast == null || toast.getView() == null) {
            toast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_LONG);
        } else {
            toast.setText(text);
        }
        toast.setGravity(gravity, 0, 0);
        toast.show();
    }
}
