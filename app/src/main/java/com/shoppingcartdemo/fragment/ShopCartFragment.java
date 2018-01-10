package com.shoppingcartdemo.fragment;

import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shoppingcartdemo.R;
import com.shoppingcartdemo.adapter.ShopCartShopAdapter;
import com.shoppingcartdemo.base.BaseFragment;
import com.shoppingcartdemo.bean.GoodsBean;
import com.shoppingcartdemo.bean.ShopCartBean;
import com.shoppingcartdemo.presenter.shop.ShopCarPresenter;
import com.shoppingcartdemo.view.IView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/19.
 */

public class ShopCartFragment extends BaseFragment implements IView<ShopCartBean> {
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

    private List<ShopCartBean> select_list;//传到结算页面的商品数据

    private String token = "";

    private ShopCartShopAdapter mAdapter;
    private ShopCarPresenter shopCarPresenter;

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
        shopCarPresenter = new ShopCarPresenter(mContext, this);
        mAdapter = new ShopCartShopAdapter(mContext, R.layout.item_shopcart_shop, shopCarPresenter);
        recyclerView.setAdapter(mAdapter);
        shopCarPresenter.setmAdapter(mAdapter);
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

                   select_list.add(bean);
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


            }


        });


                shopCarPresenter.itemChildClick(position);
            }
        });


        cbAllCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    shopCarPresenter.selectAll();
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

                    shopCarPresenter.unSelectAll();

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
        shopCarPresenter.presenterList();
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

    @Override
    public void showSuccessPage(List<ShopCartBean> list) {
        LogUtils.d("showSuccessPage");
        mAdapter.addData(list);
        layout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSuccessPage(ShopCartBean shopCartBean) {

    }

    @Override
    public void showErrorPage() {

    }

    @Override
    public void showEmptyPage() {
        layout.setVisibility(View.GONE);
    }

    public void showCode3002() {
        openActivity(LoginActivity.class);
        SPUtils.clearData(mContext, Constants.token);
    }

    public void itemChildClick(double price, boolean isChecked, List<ShopCartBean> select_list) {
        tvMoney.setText(String.valueOf(price));
        cbAllCheck.setChecked(isChecked);
        this.select_list = select_list;
    }

    public void selectAll(double price, List<ShopCartBean> select_list) {
        tvMoney.setText(String.valueOf(price));
        this.select_list = select_list;
    }

    public void unSelectAll(double price, List<ShopCartBean> select_list) {
        tvMoney.setText(String.valueOf(price));
        this.select_list = select_list;
    }

    public void numberAdd(double price, List<ShopCartBean> select_list) {
        tvMoney.setText(String.valueOf(price));
        this.select_list = select_list;
    }

    public void numberReduce(double price, List<ShopCartBean> select_list) {
        tvMoney.setText(String.valueOf(price));
        this.select_list = select_list;
    }
}
