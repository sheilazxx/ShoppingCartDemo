package com.shoppingcartdemo.activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.shoppingcartdemo.R;
import com.shoppingcartdemo.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/12/19.
 */

public class ShopCartActivity extends BaseActivity {
    @BindView(R.id.framelayout_activity_shopcart)
    FrameLayout framelayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shopcart;
    }

    @Override
    protected void afterOnCreate(Bundle savedInstanceState) {

    }


}
