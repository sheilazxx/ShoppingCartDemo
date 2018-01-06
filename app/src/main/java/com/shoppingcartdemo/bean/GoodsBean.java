package com.shoppingcartdemo.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/20.
 */

public  class GoodsBean {
    /**
     * goods_id : 39420
     * market_price : 46.80
     * shop_price : 39.00
     * surplus_number : 100
     * is_on_sale : 1
     * is_shipping : 0
     * is_promote : 0
     * is_buy : 0
     * buymax : 0
     * goods_thumb : http://1.wu10086.cn/images/201706/thumb_img/39420_thumb_G_1498032426466.jpg
     * goods_name : 家得丽300ML多效电解水（每箱24瓶
     * goods_number : 1
     * goods_attr_id :
     * cart_id : 481644
     * goods_price : 39.00
     * goods_attr :
     * need_attr : 0
     * attrs : []
     */

    private boolean isCheck ;

    private String goods_id;
    private String market_price;
    private String shop_price;
    private String surplus_number;
    private String is_on_sale;
    private String is_shipping;
    private int is_promote;
    private int is_buy;
    private int buymax;
    private String goods_thumb;
    private String goods_name;
    private String goods_number;
    private String goods_attr_id;
    private String cart_id;
    private String goods_price;
    private String goods_attr;
    private int need_attr;
    private List<?> attrs;


    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    public String getShop_price() {
        return shop_price;
    }

    public void setShop_price(String shop_price) {
        this.shop_price = shop_price;
    }

    public String getSurplus_number() {
        return surplus_number;
    }

    public void setSurplus_number(String surplus_number) {
        this.surplus_number = surplus_number;
    }

    public String getIs_on_sale() {
        return is_on_sale;
    }

    public void setIs_on_sale(String is_on_sale) {
        this.is_on_sale = is_on_sale;
    }

    public String getIs_shipping() {
        return is_shipping;
    }

    public void setIs_shipping(String is_shipping) {
        this.is_shipping = is_shipping;
    }

    public int getIs_promote() {
        return is_promote;
    }

    public void setIs_promote(int is_promote) {
        this.is_promote = is_promote;
    }

    public int getIs_buy() {
        return is_buy;
    }

    public void setIs_buy(int is_buy) {
        this.is_buy = is_buy;
    }

    public int getBuymax() {
        return buymax;
    }

    public void setBuymax(int buymax) {
        this.buymax = buymax;
    }

    public String getGoods_thumb() {
        return goods_thumb;
    }

    public void setGoods_thumb(String goods_thumb) {
        this.goods_thumb = goods_thumb;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_number() {
        return goods_number;
    }

    public void setGoods_number(String goods_number) {
        this.goods_number = goods_number;
    }

    public String getGoods_attr_id() {
        return goods_attr_id;
    }

    public void setGoods_attr_id(String goods_attr_id) {
        this.goods_attr_id = goods_attr_id;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getGoods_attr() {
        return goods_attr;
    }

    public void setGoods_attr(String goods_attr) {
        this.goods_attr = goods_attr;
    }

    public int getNeed_attr() {
        return need_attr;
    }

    public void setNeed_attr(int need_attr) {
        this.need_attr = need_attr;
    }

    public List<?> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<?> attrs) {
        this.attrs = attrs;
    }

    @Override
    public String toString() {
        return "GoodsBean{" +
                "goods_id='" + goods_id + '\'' +
                ", market_price='" + market_price + '\'' +
                ", shop_price='" + shop_price + '\'' +
                ", surplus_number='" + surplus_number + '\'' +
                ", is_on_sale='" + is_on_sale + '\'' +
                ", is_shipping='" + is_shipping + '\'' +
                ", is_promote=" + is_promote +
                ", is_buy=" + is_buy +
                ", buymax=" + buymax +
                ", goods_thumb='" + goods_thumb + '\'' +
                ", goods_name='" + goods_name + '\'' +
                ", goods_number='" + goods_number + '\'' +
                ", goods_attr_id='" + goods_attr_id + '\'' +
                ", cart_id='" + cart_id + '\'' +
                ", goods_price='" + goods_price + '\'' +
                ", goods_attr='" + goods_attr + '\'' +
                ", need_attr=" + need_attr +
                ", attrs=" + attrs +
                '}';
    }
}
