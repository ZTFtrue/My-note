package com.ztftrue.app;


import com.ztftrue.app.mold.BaseReturn;
import com.ztftrue.app.mold.BulletinItem;
import com.ztftrue.app.mold.ImageCode;
import com.ztftrue.app.mold.MallLogin;
import com.ztftrue.app.mold.ResourceVersionData;
import com.ztftrue.app.mold.UserData;
import com.ztftrue.app.mold.UserLoginData;
import com.ztftrue.app.mold.VersionData;
import com.ztftrue.app.mold.WechatMallLogin;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

 
    @Headers("Content-Type: application/json")
    @POST("post-example")
    Observable<BaseReturn<Data>> postRequest(@Body Map<String, String> map);
 
    @Headers("Content-Type: application/json")
    @GET("get/example")
    Single<BaseReturn<Data>> getRequest(@Query("id") int id);
}
