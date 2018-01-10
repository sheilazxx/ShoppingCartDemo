package com.shoppingcartdemo.presenter.shop;

import android.content.Context;

import com.shoppingcartdemo.adapter.ShopCartShopAdapter;
import com.shoppingcartdemo.bean.ShopCartBean;
import com.shoppingcartdemo.fragment.ShopCartFragment;
import com.shoppingcartdemo.mode.IMode;
import com.shoppingcartdemo.mode.shop.ShopLoaderListener;
import com.shoppingcartdemo.mode.shop.ShopMode;
import com.shoppingcartdemo.presenter.IPresenter;
import com.shoppingcartdemo.view.IView;

import java.util.List;

/**
 * Created by xiangcheng on 18/1/5.
 */

public class ShopCarPresenter implements IPresenter, ShopLoaderListener {
    IView view;
    IMode mode;

    public ShopCarPresenter(Context context, IView view) {
        this.view = view;
        this.mode = new ShopMode(context, this);
    }

    public void setmAdapter(ShopCartShopAdapter mAdapter) {
        if (mode instanceof ShopMode) {
            ((ShopMode) mode).setmAdapter(mAdapter);
        }
    }

    @Override
    public void presenterList() {
        mode.loadList();
    }

    public void selectAll() {
        if (mode instanceof ShopMode) {
            ((ShopMode) mode).selectAll();
        }
    }

    public void unSelectAll() {
        if (mode instanceof ShopMode) {
            ((ShopMode) mode).unSelectAll();
        }
    }

    public void itemChildClick(int position) {
        if (mode instanceof ShopMode) {
            ((ShopMode) mode).itemChildClick(position);
        }
    }

    public void childClick(int parent_position, int child_position) {
        if (mode instanceof ShopMode) {
            ((ShopMode) mode).childClick(parent_position, child_position);
        }
    }

    public void numberAdd(int parent_position, int child_position) {
        if (mode instanceof ShopMode) {
            ((ShopMode) mode).numberAdd(parent_position, child_position);
        }
    }

    public void numberReduce(int parent_position, int child_position) {
        if (mode instanceof ShopMode) {
            ((ShopMode) mode).numberReduce(parent_position, child_position);
        }
    }

    @Override
    public void loadCode3002() {
        if (view instanceof ShopCartFragment) {
            ((ShopCartFragment) view).showCode3002();
        }
    }

    @Override
    public void onItemChildClick(double price, boolean isChecked, List<ShopCartBean> select_list) {
        if (view instanceof ShopCartFragment) {
            ((ShopCartFragment) view).itemChildClick(price, isChecked, select_list);
        }
    }

    @Override
    public void onSelctAll(double price, List<ShopCartBean> select_list) {
        if (view instanceof ShopCartFragment) {
            ((ShopCartFragment) view).selectAll(price, select_list);
        }
    }

    @Override
    public void onUnSelectAll(double price, List<ShopCartBean> select_list) {
        if (view instanceof ShopCartFragment) {
            ((ShopCartFragment) view).unSelectAll(price, select_list);
        }
    }

    @Override
    public void onNumberAdd(double price, List<ShopCartBean> select_list) {
        if (view instanceof ShopCartFragment) {
            ((ShopCartFragment) view).numberAdd(price, select_list);
        }
    }

    @Override
    public void onNumberReduce(double price, List<ShopCartBean> select_list) {
        if (view instanceof ShopCartFragment) {
            ((ShopCartFragment) view).numberReduce(price, select_list);
        }
    }

    @Override
    public void loadSuccess(List<ShopCartBean> list) {
        view.showSuccessPage(list);
    }

    @Override
    public void loadSuccess(ShopCartBean shopCartBean) {
        view.showSuccessPage(shopCartBean);
    }

    @Override
    public void loadError(String message) {
        view.showErrorPage();
    }

    @Override
    public void loadEmpty() {
        view.showEmptyPage();
    }
}
