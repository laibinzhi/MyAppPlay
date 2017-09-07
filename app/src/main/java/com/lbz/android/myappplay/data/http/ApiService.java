package com.lbz.android.myappplay.data.http;

import com.lbz.android.myappplay.bean.AppInfo;
import com.lbz.android.myappplay.bean.AppMiBean;
import com.lbz.android.myappplay.bean.BaseHttpResultBean;
import com.lbz.android.myappplay.bean.IndexBean;
import com.lbz.android.myappplay.bean.LoginBean;
import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.bean.PageMiBean;
import com.lbz.android.myappplay.bean.requestbean.LoginRequestBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by elitemc on 2017/7/12.
 */
public interface ApiService {

    public static final String BASE_URL = "http://112.124.22.238:8081/course_api/cniaoplay/";

    public static final String BASE_URL_MI = "http://app.market.xiaomi.com/";

    public static final String REQUEST_HEADER_FLAG = "headerFlag";

//    @GET("featured")
//    Call<PageBean<AppInfo>> getApps(@Query("p") String parms);

    @GET("featured2")
    Observable<BaseHttpResultBean<PageBean<AppInfo>>> getApps(@Query("p") String parms);


    @GET("index")
    Observable<BaseHttpResultBean<IndexBean>> index();

    @GET("toplist")
    public Observable<BaseHttpResultBean<PageBean<AppInfo>>> topList(@Query("page") int page);


    @POST("login")
    Observable<BaseHttpResultBean<LoginBean>> login(@Body LoginRequestBean requestBean);

    @Headers({"headerFlag:mi"})
    @GET("apm/category")
    Observable<PageMiBean> getCategory();

    @Headers({"headerFlag:mi"})
    @GET("/apm/featured/{category_id}")
    Observable<PageMiBean> getFeaturedAppsByCategory(@Path("category_id") int category_id, @Query("page") int page);

    @Headers({"headerFlag:mi"})
    @GET("/apm/toplist/category/{category_id}")
    Observable<PageMiBean> getTopListAppsByCategory(@Path("category_id") int category_id, @Query("page") int page);

    @Headers({"headerFlag:mi"})
    @GET("/apm/updatelist/category/{category_id}")
    Observable<PageMiBean> getNewListAppsByCategory(@Path("category_id") int category_id, @Query("page") int page);
}
