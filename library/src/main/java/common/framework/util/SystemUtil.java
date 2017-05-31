package common.framework.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Looper;
import android.provider.CalendarContract;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 *
 *
 * 获取手机系统信息 添加权限
 * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
 */
public class SystemUtil {

    private static boolean isTablet = false;

    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics outMetrics = getDisplayMetrics(context);
        return outMetrics.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics outMetrics = getDisplayMetrics(context);
        return outMetrics.heightPixels;
    }

    /**
     *
     * 获取应用签名Hash
     *
     * @param context
     * @return
     */
    public static String getSignatureHash(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                return Base64.encodeToString(md.digest(), Base64.NO_WRAP);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取应用名称
     *
     * @param context
     * @return
     */
    public static String getApplicationLabel(Context context) {
        return context.getPackageManager().getApplicationLabel(context.getApplicationInfo()).toString();
    }

    /**
     * 判断是否安装了某app
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean checkAppExist(Context context, String packageName) {
        // 获取所有已安装程序的包信息
        List<PackageInfo> pinfo = context.getPackageManager().getInstalledPackages(0);
        for (PackageInfo info : pinfo) {
            if (info.packageName.equalsIgnoreCase(packageName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 打开应用程序,如果应用未安装,将在应用商店下载页显示
     *
     * @param context
     * @param packageName
     */
    public static void openApp(Context context, String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent == null) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 显示在应用商店
     *
     * @param context
     * @param packageName
     */
    public static boolean showAppStore(Context context, String packageName) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取当前应用开发的版本号
     */
    public static int getVersionCode(Context mContext) {
        try {
            return mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
	}

    /**
     * 获取当前应用开发的版本名称
     */
    public static String getVersionName(Context mContext) {
        try {
            return mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 启动默认浏览器打开连接
     *
     * @param mContext
     * @param url
     */
    public static void openBrowser(Context mContext, String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    /**
     * 当前应用是否处于前台
     * @param context
     * @return
     */
    public static boolean isAppOnForeground(Context context) {
        if (context != null) {
            ActivityManager activityManager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> processes = activityManager.getRunningAppProcesses();
            if (processes != null) {
                for (ActivityManager.RunningAppProcessInfo processInfo: processes) {
                    if (processInfo.processName.equals(context.getPackageName())) {
                        return processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 是否为当前程序进程
     *
     * @param context
     * @return
     */
    public static boolean isCurProcess(Context context) {
        int pid = android.os.Process.myPid();
        String packgeName = context.getPackageName();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = mActivityManager.getRunningAppProcesses();
        if (runningAppProcesses != null) {
            for (ActivityManager.RunningAppProcessInfo appProcess : runningAppProcesses) {
                if (appProcess.pid == pid && TextUtils.equals(appProcess.processName, packgeName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 隐藏系统键盘
     *
     * @param a
     * @return
     */
    public static boolean hideSoftInput(Activity a) {
        try {
            View view = a.getCurrentFocus();
            return view == null || ((InputMethodManager) a.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 显示系统键盘
     *
     * @param editText
     * @param context
     */
    public static void showSoftInput(final EditText editText, final Context context) {
        editText.requestFocus();
        editText.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, 0);
            }
        }, 100);
    }

    /**
     * 判断是否为pad
     *
     * @param context 如果context == null, 默认返回上次调用时的判断
     * @return true为pad
     */
    public static boolean isTablet(Context context) {
        if(context == null){
            return isTablet;
        }
        isTablet = (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
        return isTablet;
    }

    /**
     * 当前调用是否在UI线程
     *
     * @return
     */
    public static boolean isUiThread(){
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    /**
     * 在UI线程执行一个任务
     *
     * @param activity
     * @param task
     */
    public static void runOnUiThread(Activity activity, Runnable task){
        if(isUiThread()){
            task.run();
        }else{
            activity.runOnUiThread(task);
        }
    }

    /**
     * 分享一段文本
     *
     * @param context
     * @param title
     * @param subject
     * @param text
     */
    public static void share(Context context, String title, String subject, String text){
        share(context, title, subject, text, null);
    }
    /**
     * 分享一段文本
     *
     * @param context
     * @param title
     * @param subject
     * @param text
     */
    public static void share(Context context, String title, String subject, String text, String[] receivers){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        if (receivers != null) {
            shareIntent.putExtra(Intent.EXTRA_EMAIL, receivers);
        }
        shareIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(shareIntent, title));

    }

    /**
     * 调用系统日历添加事件页面
     * @param context
     * @param title
     * @param description
     * @param beginTime
     * @param endTime
     */
    public static void insertCalendar(Context context, String title, String description, long beginTime, long endTime) {
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime)
                .putExtra(CalendarContract.Events.TITLE, title)
                .putExtra(CalendarContract.Events.DESCRIPTION, description)
//                .putExtra(CalendarContract.Events.EVENT_LOCATION, "The gym")
//                .putExtra(Intent.EXTRA_EMAIL, "rowan@example.com,trevor@example.com")
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
        context.startActivity(intent);
    }

    /**
     * 获取SHA1
     * @param context
     * @return
     */
    public static String getCertificateSHA1Fingerprint(Context context) {
        PackageManager pm = context.getPackageManager();
        String packageName = context.getPackageName();
        int flags = PackageManager.GET_SIGNATURES;
        PackageInfo packageInfo;
        try {
            packageInfo = pm.getPackageInfo(packageName, flags);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        Signature[] signatures = packageInfo.signatures;
        byte[] cert = signatures[0].toByteArray();
        InputStream input = new ByteArrayInputStream(cert);
        CertificateFactory cf;
        try {
            cf = CertificateFactory.getInstance("X509");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        X509Certificate c;
        try {
            c = (X509Certificate) cf.generateCertificate(input);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        String hexString = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(c.getEncoded());
            hexString = byte2HexFormatted(publicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hexString;
    }

    public static String byte2HexFormatted(byte[] arr) {
        final int length = arr.length;
        StringBuilder str = new StringBuilder(length * 2);
        for (int i = 0; i < length; i++) {
            String h = Integer.toHexString(arr[i]);
            int l = h.length();
            if (l == 1) h = "0" + h;
            if (l > 2) h = h.substring(l - 2, l);
            str.append(h.toUpperCase());
            if (i < (length - 1)) str.append(':');
        }
        return str.toString();
    }

    public static void upVolume(Context context, int streamType) {
        setVolume(context, streamType, true);

    }

    public static void downVolume(Context context, int streamType) {
        setVolume(context, streamType, false);
    }

    private static void setVolume(Context context, int streamType, boolean upVolume) {
        AudioManager mAudioManager = (AudioManager) context.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.adjustStreamVolume(streamType, upVolume ? AudioManager.ADJUST_RAISE : AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
    }

    /**
     * 系统方向是否自动旋转
     * @param context
     * @return
     */
    public static boolean isRotationAuto(Context context) {
        return getRotationStatus(context) == 1;
    }

    /**
     * 系统方向锁定，0为锁定，1为自动
     * @param context
     * @return
     */
    public static int getRotationStatus(Context context) {
        try {
            return Settings.System.getInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
