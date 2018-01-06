package com.shoppingcartdemo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.shoppingcartdemo.R;
import com.shoppingcartdemo.base.BaseActivity;
import com.shoppingcartdemo.fragment.ShopCartFragment;
import com.zxx.statusbarlibrary.Eyes;

import butterknife.BindView;

public class MainActivity extends BaseActivity {


    @BindView(R.id.framelayout_activity_main)
    FrameLayout framelayout;

    private ShopCartFragment shopCartFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void afterOnCreate(Bundle savedInstanceState) {
        Eyes.setStatusBarLightMode(this, Color.WHITE);


        shopCartFragment = new ShopCartFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.framelayout_activity_main, shopCartFragment);
        transaction.commit();
    }





}
