package com.shoppingcartdemo.inter;

import android.content.Context;

import com.shoppingcartdemo.BuildConfig;
import com.shoppingcartdemo.base.BaseBean;
import com.shoppingcartdemo.bean.ShopCartBean;
import com.shoppingcartdemo.bean.UserInfoBean;
import com.shoppingcartdemo.utils.Constants;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/11/30.
 */

public class AppClient {

    public static Retrofit mRetrofit = null;

    public static Retrofit getRetrofit(final Context mContext) {
        if (mRetrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            /**
             *设置缓存，代码略
             */

            //公共参数
//            Interceptor addQueryParameterInterceptor = new Interceptor() {
//                @Override
//                public Response intercept(Chain chain) throws IOException {
//                    Request originalRequest = chain.request();
//                    Request request;
//
//
//                    HttpUrl modifiedUrl = originalRequest.url().newBuilder()
            // Provide your custom parameter here
//                            .addQueryParameter("version", String.valueOf(VersionUtils.getLocalVersion(mContext)))
//                            .addQueryParameter("devices_type", "android")
//                            .build();
//                    request = originalRequest.newBuilder().url(modifiedUrl).build();
//                    return chain.proceed(request);
//                }
//            };
            //公共参数
//            builder.addInterceptor(addQueryParameterInterceptor);

            /**
             * 设置头，代码略
             */

            if (BuildConfig.DEBUG) {
                // Log信息拦截器
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                //设置 Debug Log 模式
                builder.addInterceptor(loggingInterceptor);
            }
            /**
             * 设置cookie，代码略
             */

            //设置超时和重连
            builder.connectTimeout(15, TimeUnit.SECONDS);
            builder.readTimeout(20, TimeUnit.SECONDS);
            builder.writeTimeout(20, TimeUnit.SECONDS);
            //错误重连
            builder.retryOnConnectionFailure(true);
            //以上设置结束，才能build(),不然设置白搭
            OkHttpClient okHttpClient = builder.build();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASEURL)
                    .client(okHttpClient)
//java.lang.IllegalArgumentException: Unable to create converter for class`  少了这句话 addConverterFactory(GsonConverterFactory.create())加这句话的时候，别忘记导包了。这个文件里面bulid.gradle 导compile “com.squareup.retrofit2:converter-gson:2.0.2”
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加rxjava转换器
                    .build();
        }
        return mRetrofit;
    }


    public interface ApiStores {
        @GET("User/login")
        Call<UserInfoBean> getLogin(@Query("username") String username, @Query("password") String password, @Query("sign_key") String sign_key, @Query("sign_time") String sign_time, @Query("sign") String sign
                , @Query("version") String version, @Query("devices_type") String devices_type
        );


        @GET("User/login")
        Observable<UserInfoBean> getLogin_(@Query("username") String username, @Query("password") String password, @Query("sign_key") String sign_key,
                                           @Query("sign_time") String sign_time, @Query("sign") String sign
                , @Query("version") String version, @Query("devices_type") String devices_type
        );


        @GET("User/login")
        Observable<BaseBean<UserInfoBean>> getLogin2(@QueryMap Map<String, String> maps);

        @GET("Goods/cart_list")
        Observable<BaseBean<List<ShopCartBean>>> cart_list(@QueryMap Map<String, String> maps);


    }


}
