package com.ztftrue.app;


import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class AutoSubscribeObserve<T> {
    public static final int customStatusCodeUnauthorized = 401;

    @CheckReturnValue
    public Single<T> subscribeObserve(Single<T> observer) {
        return observer.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
        .doOnError(error -> {
            // 这里可以去掉
            if (error instanceof HttpException) {
                if (((HttpException) error).code() == customStatusCodeUnauthorized) {
                  // 自己处理
                }
            }
        });
    }
}
