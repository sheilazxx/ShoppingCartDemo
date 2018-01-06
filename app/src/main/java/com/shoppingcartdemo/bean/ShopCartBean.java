package com.shoppingcartdemo.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/20.
 */

public class ShopCartBean {


    /**
     * supplier_id : 1799
     * supplier_name : 军营日用百货商店
     * goods : [{"goods_id":"39420","market_price":"46.80","shop_price":"39.00","surplus_number":"100","is_on_sale":"1","is_shipping":"0","is_promote":0,"is_buy":0,"buymax":0,"goods_thumb":"http://1.wu10086.cn/images/201706/thumb_img/39420_thumb_G_1498032426466.jpg","goods_name":"家得丽300ML多效电解水（每箱24瓶","goods_number":"1","goods_attr_id":"","cart_id":"481644","goods_price":"39.00","goods_attr":"","need_attr":0,"attrs":[]}]
     * shipping_list : [{"shipping_id":"3843","shipping_name":"韵达快递","shipping_desc":"由商家选择合作快递为您配送：","is_default_show":"1"},{"shipping_id":"3485","shipping_name":"门店自提","shipping_desc":"您可以选择离您最近的自提点上门提货：","is_default_show":0},{"shipping_id":"3357","shipping_name":"申通快递","shipping_desc":"由商家选择合作快递为您配送：","is_default_show":"0"},{"shipping_id":"3324","shipping_name":"中通速递","shipping_desc":"由商家选择合作快递为您配送：","is_default_show":"0"}]
     * activity : []
     */

    private boolean isCheck ;

    private String supplier_id;
    private String supplier_name;
    private List<GoodsBean> goods;
    private List<ShippingListBean> shipping_list;
    private List<GoodsActivity> activity;


    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(String supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public List<ShippingListBean> getShipping_list() {
        return shipping_list;
    }

    public void setShipping_list(List<ShippingListBean> shipping_list) {
        this.shipping_list = shipping_list;
    }

    public List<GoodsActivity> getActivity() {
        return activity;
    }

    public void setActivity(List<GoodsActivity> activity) {
        this.activity = activity;
    }


    @Override
    public String toString() {
        return "ShopCartBean{" +
                "supplier_id='" + supplier_id + '\'' +
                ", supplier_name='" + supplier_name + '\'' +
                ", goods=" + goods +
                ", shipping_list=" + shipping_list +
                ", activity=" + activity +
                '}';
    }
}
