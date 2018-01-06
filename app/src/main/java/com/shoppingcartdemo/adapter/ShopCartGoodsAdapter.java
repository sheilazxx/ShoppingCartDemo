package com.shoppingcartdemo.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shoppingcartdemo.R;
import com.shoppingcartdemo.bean.GoodsBean;
import com.shoppingcartdemo.utils.CustomAppGlideModule;

/**
 * Created by Administrator on 2017/12/20.
 */

public class ShopCartGoodsAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {
    private Context mContext;


    public ShopCartGoodsAdapter(Context mContext, int layoutResId) {
        super(layoutResId);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsBean item) {
        helper.setText(R.id.tv_name_item_shopcart_goods, item.getGoods_name());

        String goods_price  = item.getGoods_price() ;

        if (TextUtils.isEmpty(goods_price)){
            goods_price = "0.00";
        }

        String  price =   String.format( mContext.getString(R.string.price),goods_price);


        helper.setText(R.id.tv_price_item_shopcart_goods, price);
        helper.setText(R.id.tv_num_item_goodsnum_add_reduce, item.getGoods_number());
        helper.setText(R.id.tv_attr_item_shopcart_goods, item.getGoods_attr());

        ImageView iv_thumb = helper.getView(R.id.iv_thumb_item_shopcart_goods);
        CustomAppGlideModule.glideLoader(mContext, item.getGoods_thumb(), iv_thumb);



        boolean isCheck = item.isCheck();
        if (isCheck){
            helper.setImageResource(R.id.iv_check_item_shopcart_goods,R.mipmap.cart_check_delivery_select);
        }else {
            helper.setImageResource(R.id.iv_check_item_shopcart_goods,R.mipmap.cart_check_unselect);
        }


        int position = helper.getLayoutPosition();
        if (position == getItemCount() - 1) {
            helper.setVisible(R.id.view_item_shopcart_shop, false);

        } else {
            helper.setVisible(R.id.view_item_shopcart_shop, true);
        }

        helper.addOnClickListener(R.id.iv_check_item_shopcart_goods);
        helper.addOnClickListener(R.id.tv_add_item_goodsnum_add_reduce);
        helper.addOnClickListener(R.id.tv_reduce_item_goodsnum_add_reduce);
    }


}
