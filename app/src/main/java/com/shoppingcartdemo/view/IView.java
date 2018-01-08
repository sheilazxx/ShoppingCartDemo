package com.shoppingcartdemo.view;

import java.util.List;

/**
 * Created by xiangcheng on 18/1/5.
 */

public interface IView<T> {
    public void showSuccessPage(List<T> list);

    public void showSuccessPage(T t);

    public void showErrorPage();

    public void showEmptyPage();
}
