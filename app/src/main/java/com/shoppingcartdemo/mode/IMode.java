package com.shoppingcartdemo.mode;

/**
 * Created by xiangcheng on 18/1/5.
 */
//需要传入数据的类型，该类承担着从网络或本地获取数据的部分
public interface IMode<T> {
    void loadList();
}
