package com.shoppingcartdemo.mode;

import java.util.List;

/**
 * Created by xiangcheng on 18/1/5.
 */

public interface LoaderListener<T> {
    void loadSuccess(List<T> list);

    void loadSuccess(T t);

    void loadError(String message);

    void loadEmpty();
}
