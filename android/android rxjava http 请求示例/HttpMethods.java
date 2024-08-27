package com.ztftrue.app;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.ztftrue.app.BuildConfig;
import com.ztftrue.app.MyApp;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpMethods {
    static Retrofit retrofit = null;
    private final static int REQUEST_TIMEOUT = 5;
    private static OkHttpClient okHttpClient;
    public static long lastSendMessage = 0;

    public static ApiService initAPI(String url) {
        if (TextUtils.isEmpty(url)) {
            if (okHttpClient == null) {
                okHttpClient = initOkHttp();
            }
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BuildConfig.BaseUrl)
                        .client(okHttpClient)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create(new Gson()))
                        .build();
            }
            return retrofit.create(ApiService.class);
        } else {
            if (okHttpClient == null) {
                okHttpClient = initOkHttp();
            }
            return new Retrofit.Builder()
                    .baseUrl(url)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .build().create(ApiService.class);
        }

    }

    private static OkHttpClient initOkHttp() {
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            // 这里 okhttp log 和 request 依赖包的版本必须相同
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.level(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
        }
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder();
            if (!TextUtils.isEmpty(token)) {
                requestBuilder.addHeader("Authorization", token);
            }
            requestBuilder.addHeader("locale", "zh_CN");
            Request request = requestBuilder.build();
            return chain.proceed(request);
        });
        return httpClient.build();
    }
}
