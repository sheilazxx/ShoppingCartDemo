package com.shoppingcartdemo.mode.shop;

import android.database.DataSetObservable;
import android.database.DataSetObserver;

import com.shoppingcartdemo.adapter.ShopCartShopAdapter;
import com.shoppingcartdemo.bean.ShopCartBean;

import java.util.List;

/**
 * Created by xiangcheng on 18/1/11.
 */

//实际项目中该类是不存在的，本demo为了兼顾mvp的弱耦合性，才衍生的该类
//他只是一个工具类，只是存储数据源而已，不是mvp中的m层
public class ShopDataSource {

    public ShopDataSource(ShopCartShopAdapter shopCartShopAdapter) {
        this.shopCartShopAdapter = shopCartShopAdapter;
        MyDataSetObserver myDataSetObserver = new MyDataSetObserver();
        if (mDataSetObservable != null) {
            unRegisterDataSetObserver(myDataSetObserver);
        }
        registerDataSetObserver(myDataSetObserver);
    }

    private ShopCartShopAdapter shopCartShopAdapter;

    private final DataSetObservable mDataSetObservable = new DataSetObservable();
//    private List<ShopCartBean> dataSource = new ArrayList<>();

    public void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    public void unRegisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }

    public List<ShopCartBean> getDataSource() {
        return shopCartShopAdapter.getData();
    }

    private int position;
    private boolean itemChange;

    public void notifyItemChange(int position) {
        this.position = position;
        itemChange = true;
        mDataSetObservable.notifyChanged();
    }

    public void notifyDataSetChanged() {
        itemChange = false;
        mDataSetObservable.notifyChanged();
    }

    final class MyDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            super.onChanged();
            if (itemChange) {
                shopCartShopAdapter.notifyItemChanged(position);
            } else {
                shopCartShopAdapter.notifyDataSetChanged();
            }
        }
    }

}
