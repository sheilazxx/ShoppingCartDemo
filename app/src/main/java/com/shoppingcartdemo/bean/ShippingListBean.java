package com.shoppingcartdemo.bean;

/**
 * Created by Administrator on 2017/12/20.
 */

public  class ShippingListBean {
    /**
     * shipping_id : 3843
     * shipping_name : 韵达快递
     * shipping_desc : 由商家选择合作快递为您配送：
     * is_default_show : 1
     */

    private String shipping_id;
    private String shipping_name;
    private String shipping_desc;
    private String is_default_show;

    public String getShipping_id() {
        return shipping_id;
    }

    public void setShipping_id(String shipping_id) {
        this.shipping_id = shipping_id;
    }

    public String getShipping_name() {
        return shipping_name;
    }

    public void setShipping_name(String shipping_name) {
        this.shipping_name = shipping_name;
    }

    public String getShipping_desc() {
        return shipping_desc;
    }

    public void setShipping_desc(String shipping_desc) {
        this.shipping_desc = shipping_desc;
    }

    public String getIs_default_show() {
        return is_default_show;
    }

    public void setIs_default_show(String is_default_show) {
        this.is_default_show = is_default_show;
    }

    @Override
    public String toString() {
        return "ShippingListBean{" +
                "shipping_id='" + shipping_id + '\'' +
                ", shipping_name='" + shipping_name + '\'' +
                ", shipping_desc='" + shipping_desc + '\'' +
                ", is_default_show='" + is_default_show + '\'' +
                '}';
    }
}