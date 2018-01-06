package com.shoppingcartdemo.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shoppingcartdemo.R;
import com.shoppingcartdemo.bean.GoodsBean;
import com.shoppingcartdemo.bean.ShopCartBean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/20.
 */

public class ShopCartShopAdapter extends BaseQuickAdapter<ShopCartBean, BaseViewHolder> {
    private Context mContext;

    public ShopCartShopAdapter(Context mContext, int layoutResId) {
        super(layoutResId);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, final ShopCartBean item) {
        helper.setText(R.id.tv_shopname_item_shopcart_shop, item.getSupplier_name());

        RecyclerView recyclerView = helper.getView(R.id.recyclerView_item_shopcart_shop);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);

        recyclerView.setLayoutManager(layoutManager);
        //解决单条刷新时图片闪烁问题
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        helper.addOnClickListener(R.id.iv_check_item_shopcart_shop);


        boolean isCheck = item.isCheck();
        if (isCheck) {
            helper.setImageResource(R.id.iv_check_item_shopcart_shop, R.mipmap.cart_check_delivery_select);
        } else {
            helper.setImageResource(R.id.iv_check_item_shopcart_shop, R.mipmap.cart_check_unselect);
        }

        final ShopCartGoodsAdapter myAdapter = new ShopCartGoodsAdapter(mContext, R.layout.item_shopcart_goods);
        recyclerView.setAdapter(myAdapter);

        final List<GoodsBean> list = item.getGoods();
        if (list != null) {
            myAdapter.addData(list);
        }

        final int parent_position = helper.getLayoutPosition();
        myAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int child_position) {

                switch (view.getId()) {
                    case R.id.iv_check_item_shopcart_goods:
                        if (onChildCheckItemListener != null) {
                            onChildCheckItemListener.setOnChildCheckItemClick(parent_position, child_position);
                        }
                        break;
                    case R.id.tv_add_item_goodsnum_add_reduce:
                        if (onGoodsNumAddListener != null) {
                            onGoodsNumAddListener.setOnGoodsNumAddClick(parent_position, child_position);
                        }
                        break;
                    case R.id.tv_reduce_item_goodsnum_add_reduce:
                        if (onGoodsNumReduceListener != null) {
                            onGoodsNumReduceListener.setOnGoodsNumReduceClick(parent_position, child_position);
                        }
                        break;
                }


            }
        });
    }


    /**
     * 选中
     */
    public interface OnChildCheckItemListener {
        void setOnChildCheckItemClick(int parent_position, int child_position);
    }

    private OnChildCheckItemListener onChildCheckItemListener;

    public void setOnChildCheckItemListener(OnChildCheckItemListener listener) {
        this.onChildCheckItemListener = listener;
    }


    /**
     * 数量加
     */
    public interface OnGoodsNumAddListener {
        void setOnGoodsNumAddClick(int parent_position, int child_position);
    }

    private OnGoodsNumAddListener onGoodsNumAddListener;

    public void setOnGoodsNumAddListener(OnGoodsNumAddListener onGoodsNumAddListener) {
        this.onGoodsNumAddListener = onGoodsNumAddListener;
    }

    /**
     * 数量减
     */
    public interface OnGoodsNumReduceListener {
        void setOnGoodsNumReduceClick(int parent_position, int child_position);
    }

    private OnGoodsNumReduceListener onGoodsNumReduceListener;

    public void setOnGoodsNumreduceListener(OnGoodsNumReduceListener onGoodsNumReduceListener) {
        this.onGoodsNumReduceListener = onGoodsNumReduceListener;
    }
}
