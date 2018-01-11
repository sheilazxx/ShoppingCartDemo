package com.shoppingcartdemo.fragment;

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
import com.shoppingcartdemo.R;
import com.shoppingcartdemo.activity.LoginActivity;
import com.shoppingcartdemo.adapter.ShopCartShopAdapter;
import com.shoppingcartdemo.base.BaseFragment;
import com.shoppingcartdemo.bean.ShopCartBean;
import com.shoppingcartdemo.presenter.shop.ShopCarPresenter;
import com.shoppingcartdemo.utils.Constants;
import com.shoppingcartdemo.utils.LogUtils;
import com.shoppingcartdemo.utils.SPUtils;
import com.shoppingcartdemo.view.IView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/19.
 * 购物车页面
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
    private String price = "";

    private ShopCartShopAdapter mAdapter;
    private ShopCarPresenter shopCarPresenter;

    //向成添加的内容
    private int commit1;

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
                shopCarPresenter.itemChildClick(position);
            }
        });

        cbAllCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                double goodsPrice = 0.00;
                int number = 0;

                if (isChecked) {
                    shopCarPresenter.selectAll();
                } else {
                    //只有当点击全不选时才执行
                    // 解决点击取消选择店铺或商品时，
                    // 全选按钮取消选择状态，不会不变成全不选
                    shopCarPresenter.unSelectAll();
                }
            }
        });

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
                commit1 += 2;
                LogUtils.e("commit1 =" + commit1);
                break;
            case R.id.tv_banlance_item_shopcart_bottom_balance:
                LogUtils.e("select_list =" + select_list.toString());
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
