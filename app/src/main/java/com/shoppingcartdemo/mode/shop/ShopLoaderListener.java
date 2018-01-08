package com.shoppingcartdemo.mode.shop;

import com.shoppingcartdemo.bean.ShopCartBean;
import com.shoppingcartdemo.mode.LoaderListener;

import java.util.List;

/**
 * Created by xiangcheng on 18/1/5.
 */

public interface ShopLoaderListener extends LoaderListener<ShopCartBean> {
    void loadCode3002();

    void onItemChildClick(double price, boolean isChecked, List<ShopCartBean> select_list);

    void onSelctAll(double price, List<ShopCartBean> select_list);

    void onUnSelectAll(double price, List<ShopCartBean> select_list);

    void onNumberAdd(double price, List<ShopCartBean> select_list);

    void onNumberReduce(double price, List<ShopCartBean> select_list);

}
