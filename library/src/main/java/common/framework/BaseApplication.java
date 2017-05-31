package common.framework;

import android.app.Application;

/**
 * Created by Rays on 2017/3/29.
 */
public class BaseApplication extends Application {
    protected static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    protected void init() {
        instance = this;
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    public boolean isDebug() {
        return true;
    }
}
