package com.ztftrue.app;

import com.ztftrue.app.mold.BaseReturn;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.util.EndConsumerHelper;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

public abstract class MySingleObserver<T> implements SingleObserver<BaseReturn<T>>, Disposable {
    final AtomicReference<Disposable> upstream = new AtomicReference<>();

    @Override
    public final void onSubscribe(@NonNull Disposable d) {
        if (EndConsumerHelper.setOnce(this.upstream, d, getClass())) {
            onStart();
        }
    }

    /**
     * Called once the single upstream Disposable is set via onSubscribe.
     */
    protected void onStart() {
    }

    @Override
    public final boolean isDisposed() {
        return upstream.get() == DisposableHelper.DISPOSED;
    }

    @Override
    public final void dispose() {
        DisposableHelper.dispose(upstream);
    }

    @Override
    public void onSuccess(BaseReturn<T> o) {
        if (o.getCode() == null) {
            onFailure(new MyException("服务器出错"),"0", "服务器出错");
        } else if (o.getCode()!=200) {
            onFailure(new MyException(o.getMessage()),String.valueOf(o.getCode()), o.getMessage());
        }else {
            onResult(o.getData());
        }
    }


    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {
            Response<?> response = ((HttpException) e).response();
            int code =((HttpException) e).code();
            if (response != null) {
                ResponseBody body = response.errorBody();
                if (body != null) {
                    try {
                        onFailure(e,String.valueOf(code),body.string());
                    } catch (IOException ex) {
                        onFailure(e,String.valueOf(code),"未知错误");
                        ex.printStackTrace();
                    }
                }
            }
            return;
        }
        onFailure(e,"1001",e.getMessage());
    }

    public abstract void onResult(T result);
// 这里可以去掉 throwable, 我保留的原因是因为, 后台 code 不固定
    public abstract void onFailure(Throwable throwable,String code, String message);

}
