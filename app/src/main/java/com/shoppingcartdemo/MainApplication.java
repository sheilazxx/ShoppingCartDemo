package com.shoppingcartdemo;

import android.app.Application;

/**
 * Created by Administrator on 2017/11/30.
 */

public class MainApplication extends Application {

    private MainApplication mainApplication ;

    @Override
    public void onCreate() {
        super.onCreate();
        mainApplication = this ;
    }



    public  MainApplication getInstance() {
        if (mainApplication == null)
            mainApplication = new MainApplication();
        return mainApplication;
    }
}
