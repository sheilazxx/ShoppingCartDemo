package com.shoppingcartdemo.mode.shop;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shoppingcartdemo.adapter.ShopCartShopAdapter;
import com.shoppingcartdemo.bean.GoodsBean;
import com.shoppingcartdemo.bean.ShopCartBean;
import com.shoppingcartdemo.mode.IMode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangcheng on 18/1/5.
 */

public class ShopMode implements IMode<ShopCartBean> {
    private static final String TAG = ShopMode.class.getSimpleName();
    private Context context;

    public void setmAdapter(ShopCartShopAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    ShopCartShopAdapter mAdapter;
    private double price;
    private List<ShopCartBean> select_list = new ArrayList<>();//传到结算页面的商品数据

    private ShopLoaderListener listener;

    public ShopMode(Context context, ShopLoaderListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void loadList() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open("shopcartdata.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }

            String json = stringBuilder.toString();

            Gson gson = new Gson();
            List<ShopCartBean> list = gson.fromJson(json, new TypeToken<List<ShopCartBean>>() {
            }.getType());//对于不是类的情况，用这个参数给出
            listener.loadSuccess(list);
//            mAdapter.addData(list);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void numberReduce(int parent_position, int child_position) {
        List<ShopCartBean> list = mAdapter.getData();

        ShopCartBean bean = list.get(parent_position);
        List<GoodsBean> goodsList = bean.getGoods();
        GoodsBean goodsBean = goodsList.get(child_position);
        String goods_num = goodsBean.getGoods_number();
        int goodsNum = Integer.parseInt(goods_num);
        boolean canReduce = false;
        if (goodsNum > 1) {
            canReduce = true;
        }
        GoodsBean selectGoodsBean = goodsNumChange(2, parent_position, child_position);
        Log.d(TAG, "goodsBean.number:" + goodsBean.getGoods_number());
        if (selectGoodsBean.isCheck() && canReduce) {
            price -= Double.parseDouble(selectGoodsBean.getGoods_price());
            Log.d(TAG, "price:" + price);
            listener.onNumberReduce(price, select_list);
        }
    }

    public void itemChildClick(int position) {
        List<ShopCartBean> list = mAdapter.getData();

        ShopCartBean bean = list.get(position);
        boolean isSelected;
        boolean checkAll;
        if (bean.isCheck()) {
            isSelected = false;
        } else {
            isSelected = true;
        }

        //保存店铺点击状态
        bean.setCheck(isSelected);
        //通知全选CheckBox的选择状态
        if (allSelect() == list.size()) {
            checkAll = true;
        } else {
            checkAll = false;
        }
        if (isSelected) {
            for (int i = 0; i < bean.getGoods().size(); i++) {
                if (!bean.getGoods().get(i).isCheck()) {
                    bean.getGoods().get(i).setCheck(true);
                    price += Double.parseDouble(bean.getGoods().get(i).getGoods_number()) * Double.parseDouble(bean.getGoods().get(i).getGoods_price());
//                    tvMoney.setText(String.valueOf(price));
                }
            }
            select_list.add(bean);
        } else {
            // 解决点击取消选择商品时，
            // 店铺全选按钮取消选择状态，不会不变成全不选
            if (allChildSelect(position) == bean.getGoods().size()) {
                for (int i = 0; i < bean.getGoods().size(); i++) {
                    if (bean.getGoods().get(i).isCheck()) {
                        bean.getGoods().get(i).setCheck(false);
                        price -= Double.parseDouble(bean.getGoods().get(i).getGoods_number()) * Double.parseDouble(bean.getGoods().get(i).getGoods_price());
//                        tvMoney.setText(String.valueOf(price));
                    }
                }
                select_list.remove(bean);
            }
        }
        mAdapter.notifyItemChanged(position);
        listener.onItemChildClick(price, checkAll, select_list);
    }

    //计算店铺的选择数量
    private int allSelect() {
        List<ShopCartBean> list = mAdapter.getData();
        int sum = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isCheck()) {
                sum++;
            }
        }

        return sum;
    }

    //计算每个店铺商品的选择数量
    private int allChildSelect(int position) {
        List<ShopCartBean> list = mAdapter.getData();
        int sum = 0;
        for (int i = 0; i < list.get(position).getGoods().size(); i++) {
            if (list.get(position).getGoods().get(i).isCheck()) {
                sum++;
            }
        }
        return sum;
    }

    public void childClick(int parent_position, int child_position) {
        List<ShopCartBean> list = mAdapter.getData();

        ShopCartBean bean = list.get(parent_position);
        select_list.remove(bean);
        List<GoodsBean> goodsList = bean.getGoods();
        GoodsBean goodsBean = goodsList.get(child_position);
        bean.getGoods().clear();
        boolean isSelected;
        boolean checkAll;
        if (goodsBean.isCheck()) {
            isSelected = false;
            price -= Double.parseDouble(goodsBean.getGoods_number()) * Double.parseDouble(goodsBean.getGoods_price());
            bean.getGoods().remove(goodsBean);
        } else {
            isSelected = true;
            price += Double.parseDouble(goodsBean.getGoods_number()) * Double.parseDouble(goodsBean.getGoods_price());
            bean.getGoods().add(goodsBean);
        }
        select_list.add(bean);
        //保存商品点击状态
        goodsBean.setCheck(isSelected);
        //通知店铺选择的状态
        if (allChildSelect(parent_position) == goodsList.size()) {
            bean.setCheck(true);
        } else {
            bean.setCheck(false);
        }

        //通知全选CheckBox的选择状态
        if (allSelect() == list.size()) {
            checkAll = true;
        } else {
            checkAll = false;
        }
        mAdapter.notifyItemChanged(parent_position);
        listener.onItemChildClick(price, checkAll, select_list);
    }

    public void selectAll() {
        List<ShopCartBean> list = mAdapter.getData();
        price = 0;
        select_list.clear();
        for (int i = 0; i < list.size(); i++) {
            ShopCartBean shopCartBean = list.get(i);

            //选择店铺
            if (!shopCartBean.isCheck()) {
                shopCartBean.setCheck(true);
            }
            for (int j = 0; j < shopCartBean.getGoods().size(); j++) {
                //选择店铺的商品
                if (!shopCartBean.getGoods().get(j).isCheck()) {
                    shopCartBean.getGoods().get(j).setCheck(true);
                    Log.d(TAG, "数量:" + shopCartBean.getGoods().get(j).getGoods_number());
                }
                price += Double.parseDouble(shopCartBean.getGoods().get(j).getGoods_number()) * Double.parseDouble(shopCartBean.getGoods().get(j).getGoods_price());
            }
            select_list.add(shopCartBean);
        }
        //更新
        mAdapter.notifyDataSetChanged();
        listener.onSelctAll(price, select_list);
    }

    public void unSelectAll() {
        List<ShopCartBean> list = mAdapter.getData();
        if (allSelect() == list.size()) {
            for (int i = 0; i < list.size(); i++) {
                ShopCartBean shopCartBean = list.get(i);

                if (shopCartBean.isCheck()) {
                    shopCartBean.setCheck(false);
                }
                for (int j = 0; j < shopCartBean.getGoods().size(); j++) {
                    if (shopCartBean.getGoods().get(j).isCheck()) {
                        shopCartBean.getGoods().get(j).setCheck(false);
                    }
                }
            }
            select_list.clear();
            price = 0;
            listener.onUnSelectAll(price, select_list);
        }
        //更新
        mAdapter.notifyDataSetChanged();

    }

    public void numberAdd(int parent_position, int child_position) {
        GoodsBean goodsBean = goodsNumChange(1, parent_position, child_position);
        if (goodsBean.isCheck()) {
            price += Double.parseDouble(goodsBean.getGoods_price());
            listener.onNumberAdd(price, select_list);
        }
    }

    //商品数量的增减
    private GoodsBean goodsNumChange(int type, int parent_position, int child_position) {

        List<ShopCartBean> list = mAdapter.getData();

        ShopCartBean bean = list.get(parent_position);
        select_list.remove(bean);
        List<GoodsBean> goodsList = bean.getGoods();
        GoodsBean goodsBean = goodsList.get(child_position);
        goodsList.clear();
        String goods_num = goodsBean.getGoods_number();
        int goodsNum = Integer.parseInt(goods_num);

        if (type == 1) {
            goodsNum = goodsNum + 1;
        } else {

            if (goodsNum > 1) {
                goodsNum = goodsNum - 1;
            }
        }

        goodsBean.setGoods_number(String.valueOf(goodsNum));
        goodsList.add(goodsBean);
        bean.setGoods(goodsList);
        select_list.add(bean);
        mAdapter.notifyItemChanged(parent_position);
        return goodsBean;
    }
}
