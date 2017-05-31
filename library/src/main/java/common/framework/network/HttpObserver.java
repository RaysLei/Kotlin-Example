package common.framework.network;

import android.support.annotation.NonNull;

import com.rays.library.R;

import java.io.IOException;

import common.framework.BaseApplication;
import common.framework.util.LogUtil;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by Rays on 2017/3/30.
 */
public abstract class HttpObserver<T> implements Observer<T> {
    public static final int CODE_UNKNOWN_EXCEPTION = -500;
    public static final int CODE_TIME_OUT = -501;

    @Override
    public void onSubscribe(Disposable d) {
        if (!d.isDisposed()) {
            onResponseStart();
        }
    }

    @Override
    public void onNext(T t) {
        onResponseSucсess(t);
    }

    @Override
    public void onError(Throwable e) {
        int code = CODE_UNKNOWN_EXCEPTION;
        String message = null;
        String errorBody = null;
        if (e instanceof HttpException) {
            try {
                HttpException httpException = (HttpException) e;
                code = httpException.code();
                message = httpException.message();
                errorBody = httpException.response().errorBody().string();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else if (e instanceof IOException) {
            code = CODE_TIME_OUT;
            message = getTimeOutMessage();
        } else {
            code = CODE_UNKNOWN_EXCEPTION;
            message = e.getMessage();
        }
        onResponseFail(e, code, message == null ? "" : message, errorBody == null ? "" : errorBody);
        onResponseFinish();
    }

    @Override
    public void onComplete() {
        onResponseFinish();
    }

    public void onResponseStart() {

    }

    public abstract void onResponseSucсess(T t);

    public void onResponseFail(Throwable e, int code, String message, String errorBody) {
        LogUtil.i(this, "code: " + code + " message: " + message + " errorBody: " + errorBody);
        e.printStackTrace();
    }

    public void onResponseFinish() {

    }

    @NonNull
    protected String getTimeOutMessage() {
        return BaseApplication.getInstance().getResources().getString(R.string.network_request_timeout);
    }
}
