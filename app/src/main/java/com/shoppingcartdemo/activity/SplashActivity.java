package com.shoppingcartdemo.activity;

import android.os.Bundle;
import android.text.TextUtils;

import com.shoppingcartdemo.R;
import com.shoppingcartdemo.base.BaseActivity;
import com.shoppingcartdemo.utils.Constants;
import com.shoppingcartdemo.utils.SPUtils;


/**
 * Created by Administrator on 2017/12/11.
 */

public class SplashActivity extends BaseActivity {

    private String token;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void afterOnCreate(Bundle savedInstanceState) {

        token = SPUtils.getString(mContext, Constants.token, "");



        if (TextUtils.isEmpty(token)){
            openActivity(MainActivity.class);
        }else {
            openActivity(MainActivity.class);
        }
        this.finish();
    }
}
