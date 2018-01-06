package com.shoppingcartdemo.fragment;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.shoppingcartdemo.R;
import com.shoppingcartdemo.activity.LoginActivity;
import com.shoppingcartdemo.adapter.ShopCartShopAdapter;
import com.shoppingcartdemo.base.BaseBean;
import com.shoppingcartdemo.base.BaseFragment;
import com.shoppingcartdemo.bean.GoodsBean;
import com.shoppingcartdemo.bean.ShopCartBean;
import com.shoppingcartdemo.inter.AppClient;
import com.shoppingcartdemo.utils.Constants;
import com.shoppingcartdemo.utils.LogUtils;
import com.shoppingcartdemo.utils.SPUtils;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * Created by Administrator on 2017/12/19.
 */

public class ShopCartFragment extends BaseFragment {
    @BindView(R.id.recyclerView_fragment_shopcart)
    RecyclerView recyclerView;
    @BindView(R.id.cb_all_check_item_shopcart_bottom_balance)
    CheckBox cbAllCheck;
    @BindView(R.id.tv_money_item_shopcart_bottom_balance)
    TextView tvMoney;
    @BindView(R.id.tv_banlance_item_shopcart_bottom_balance)
    TextView tvBanlance;
    @BindView(R.id.layout_item_shopcart_bottom_balance)
    ConstraintLayout layout;

    private List<ShopCartBean> select_list = new ArrayList<>();//传到结算页面的商品数据

    private String token = "";

    private ShopCartShopAdapter mAdapter;

    private double price;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shopcart;
    }

    @Override
    protected void initView(View mRootView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);

        recyclerView.setLayoutManager(layoutManager);
        //解决单条刷新时图片闪烁问题
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mAdapter = new ShopCartShopAdapter(mContext, R.layout.item_shopcart_shop);
        recyclerView.setAdapter(mAdapter);


        /**
         * 商铺全选的点击事件监听
         */
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<ShopCartBean> list = mAdapter.getData();

                ShopCartBean bean = list.get(position);
                boolean isSelected;
                if (bean.isCheck()) {
                    isSelected = false;

                } else {
                    isSelected = true;
                }

                //保存店铺点击状态
                bean.setCheck(isSelected);
                //通知全选CheckBox的选择状态
                if (allSelect() == list.size()) {
                    cbAllCheck.setChecked(true);
                } else {
                    cbAllCheck.setChecked(false);
                }
                if (isSelected) {
                    for (int i = 0; i < bean.getGoods().size(); i++) {
                        if (!bean.getGoods().get(i).isCheck()) {
                            bean.getGoods().get(i).setCheck(true);
                            price += Double.parseDouble(bean.getGoods().get(i).getGoods_number()) * Double.parseDouble(bean.getGoods().get(i).getGoods_price());
                            tvMoney.setText(String.valueOf(price));
                        }
                    }
                } else {

                    // 店铺全选按钮取消选择状态
                    if (allChildSelect(position) == bean.getGoods().size()) {
                        for (int i = 0; i < bean.getGoods().size(); i++) {
                            if (bean.getGoods().get(i).isCheck()) {
                                bean.getGoods().get(i).setCheck(false);
                                price -= Double.parseDouble(bean.getGoods().get(i).getGoods_number()) * Double.parseDouble(bean.getGoods().get(i).getGoods_price());
                                tvMoney.setText(String.valueOf(price));
                            }
                        }
                    }
                }


                mAdapter.notifyItemChanged(position);


//                calculate();
            }
        });


        mAdapter.setOnChildCheckItemListener(new ShopCartShopAdapter.OnChildCheckItemListener() {
            @Override
            public void setOnChildCheckItemClick(int parent_position, int child_position) {

                List<ShopCartBean> list = mAdapter.getData();

                ShopCartBean bean = list.get(parent_position);

                List<GoodsBean> goodsList = bean.getGoods();
                GoodsBean goodsBean = goodsList.get(child_position);

                boolean isSelected;
                if (goodsBean.isCheck()) {
                    isSelected = false;
                    price -= Double.parseDouble(goodsBean.getGoods_number()) * Double.parseDouble(goodsBean.getGoods_price());
                    tvMoney.setText(String.valueOf(price));
                } else {
                    isSelected = true;
                    price += Double.parseDouble(goodsBean.getGoods_number()) * Double.parseDouble(goodsBean.getGoods_price());
                    tvMoney.setText(String.valueOf(price));
                }

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
                    cbAllCheck.setChecked(true);
                } else {
                    cbAllCheck.setChecked(false);
                }
                mAdapter.notifyItemChanged(parent_position);

//                calculate();
            }


        });

        cbAllCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                List<ShopCartBean> list = mAdapter.getData();
                if (isChecked) {
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
                                price += Double.parseDouble(shopCartBean.getGoods().get(j).getGoods_number()) * Double.parseDouble(shopCartBean.getGoods().get(j).getGoods_price());
                                tvMoney.setText(String.valueOf(price));
                            }
                        }
                    }
                } else {
                    //只有当点击全不选时才执行
                    // 解决点击取消选择店铺或商品时，
                    // 全选按钮取消选择状态，不会不变成全不选
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
                        price = 0;
                        tvMoney.setText(String.valueOf(price));
                    }
                }
                //更新
                mAdapter.notifyDataSetChanged();
//                calculate();
            }
        });

        mAdapter.setOnGoodsNumAddListener(new ShopCartShopAdapter.OnGoodsNumAddListener() {
            @Override
            public void setOnGoodsNumAddClick(int parent_position, int child_position) {

                GoodsBean goodsBean =  goodsNumChange(1, parent_position, child_position);
                if (goodsBean.isCheck()) {
                    price += Double.parseDouble(goodsBean.getGoods_price());
                    tvMoney.setText(String.valueOf(price));
                }
            }
        });
        mAdapter.setOnGoodsNumreduceListener(new ShopCartShopAdapter.OnGoodsNumReduceListener() {
            @Override
            public void setOnGoodsNumReduceClick(int parent_position, int child_position) {

                GoodsBean goodsBean =  goodsNumChange(2, parent_position, child_position);
                if (goodsBean.isCheck()) {
                    price -= Double.parseDouble(goodsBean.getGoods_price());
                    tvMoney.setText(String.valueOf(price));
                }
            }
        });


    }


    //商品数量的增减
    private GoodsBean goodsNumChange(int type, int parent_position, int child_position) {

        List<ShopCartBean> list = mAdapter.getData();

        ShopCartBean bean = list.get(parent_position);

        List<GoodsBean> goodsList = bean.getGoods();
        GoodsBean goodsBean = goodsList.get(child_position);

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
        mAdapter.notifyItemChanged(parent_position);

//        calculate();
        return goodsBean;
    }

    //计算商品总价格
    private void calculate() {
        select_list.clear();
        double price = 0.00;
        List<ShopCartBean> list = mAdapter.getData();
        for (ShopCartBean shopCartBean : list) {
            ShopCartBean shopCartBeanNew = new ShopCartBean();
            shopCartBeanNew.setSupplier_name(shopCartBean.getSupplier_name());
            shopCartBeanNew.setSupplier_id(shopCartBean.getSupplier_id());
            List<GoodsBean> goodsListNew = new ArrayList<>();

            List<GoodsBean> goodsBeanList = shopCartBean.getGoods();

            for (GoodsBean goodsBean : goodsBeanList) {

                if (goodsBean.isCheck()) {
                    goodsListNew.add(goodsBean);
                    String goods_price = goodsBean.getGoods_price();
                    double goodsPrice = Double.parseDouble(goods_price);
                    String goods_num = goodsBean.getGoods_number();
                    int goodsNum = Integer.parseInt(goods_num);
                    price += goodsPrice * goodsNum;
                }

            }
            if (goodsListNew.size() > 0) {
                shopCartBeanNew.setGoods(goodsListNew);
                select_list.add(shopCartBeanNew);
            }


        }

        tvMoney.setText(String.valueOf(price));
        LogUtils.e("select_list =" + select_list.toString());
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        token = SPUtils.getString(mContext, Constants.token);

//        getData(token);
//
        getJsonData();
    }

    private void getJsonData() {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = mContext.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open("shopcartdata.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }

            String json = stringBuilder.toString();

            Gson gson = new Gson();
            List<ShopCartBean> list = gson.fromJson(json, new TypeToken<List<ShopCartBean>>() {
            }.getType());//对于不是类的情况，用这个参数给出

            mAdapter.addData(list);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void getData(String token) {

        Map<String, String> map = new HashMap<>();
        map.put(Constants.token, token);

        AppClient.ApiStores apiStores = AppClient.getRetrofit(mContext).create(AppClient.ApiStores.class);
        Observable<BaseBean<List<ShopCartBean>>> observable = apiStores.cart_list(map);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean<List<ShopCartBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.d("onSubscribe");
                    }

                    @Override
                    public void onNext(BaseBean<List<ShopCartBean>> baseBean) {
                        LogUtils.d("onNext=" + baseBean.toString());


                        String code = baseBean.getCode();
                        if ("3002".equals(code)) {
                            openActivity(LoginActivity.class);
                            SPUtils.clearData(mContext, Constants.token);
                        } else {

                            List<ShopCartBean> list = baseBean.getData();

                            if (list != null && list.size() > 0) {
                                mAdapter.addData(list);
                                layout.setVisibility(View.VISIBLE);
                            } else {
                                layout.setVisibility(View.GONE);
                            }

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d("e=" + e.getMessage());


                        if (e instanceof HttpException) {     //   HTTP错误

                        } else if (e instanceof ConnectException || e instanceof UnknownHostException) {   //   连接错误

                        } else if (e instanceof InterruptedIOException) {   //  连接超时

                        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {   //  解析错误

                        } else {

                        }


                    }

                    @Override
                    public void onComplete() {
                        LogUtils.d("onCompleted");
                    }
                });


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

    @OnClick({R.id.tv_money_item_shopcart_bottom_balance, R.id.tv_banlance_item_shopcart_bottom_balance})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_money_item_shopcart_bottom_balance:

                break;
            case R.id.tv_banlance_item_shopcart_bottom_balance:


                break;
        }
    }


}
