package com.shoppingcartdemo.inter;

import android.database.Observable;

import com.shoppingcartdemo.bean.UserInfoBean;

import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2017/11/30.
 */

public interface LoginService {

    @POST("User/login")
    Observable<UserInfoBean> getLogin(@Path("username") String username, @Path("password") String password

            , @Path("sign_key") String sign_key, @Path("sign_time") String sign_time, @Path("sign") String sign
    );
}
