package com.shoppingcartdemo.bean;

/**
 * Created by Administrator on 2018/1/3.
 * 商品活动
 */

public class GoodsActivity  {

    private String act_name;

    public String getAct_name() {
        return act_name;
    }

    public void setAct_name(String act_name) {
        this.act_name = act_name;
    }

    @Override
    public String toString() {
        return "GoodsActivity{" +
                "act_name='" + act_name + '\'' +
                '}';
    }
}
