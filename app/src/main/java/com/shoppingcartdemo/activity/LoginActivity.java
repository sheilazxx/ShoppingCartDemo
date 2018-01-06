package com.shoppingcartdemo.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonParseException;
import com.shoppingcartdemo.inter.AppClient;
import com.shoppingcartdemo.R;
import com.shoppingcartdemo.base.BaseActivity;
import com.shoppingcartdemo.base.BaseBean;
import com.shoppingcartdemo.bean.UserInfoBean;
import com.shoppingcartdemo.utils.Constants;
import com.shoppingcartdemo.utils.LogUtils;
import com.shoppingcartdemo.utils.MD5Utils;
import com.shoppingcartdemo.utils.SPUtils;
import com.shoppingcartdemo.utils.SignUtils;
import com.shoppingcartdemo.utils.ToastUtil;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.HashMap;
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
 * Created by Administrator on 2017/11/30.
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.edit_username_activity_login)
    EditText editUsername;
    @BindView(R.id.edit_password_activity_login)
    EditText editPassword;
    @BindView(R.id.tv_login_activity_login)
    TextView tvLogin;
    private String userName;
    private String passWord;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void afterOnCreate(Bundle savedInstanceState) {


    }


    @OnClick(R.id.tv_login_activity_login)
    public void onViewClicked() {
        userName = editUsername.getText().toString().trim();
        String pwd = editPassword.getText().toString().trim();
        passWord = MD5Utils.getStringMD5(pwd);

        if (TextUtils.isEmpty(userName)) {
            ToastUtil.showShort(mContext, "请输入手机号码");
            return;
        }
        if (TextUtils.isEmpty(passWord)) {
            ToastUtil.showShort(mContext, "请输入密码");
            return;
        }
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(passWord)) {
            login(userName, passWord);
        }


    }


    private void login(String userName, String passWord) {


        Map<String, String> map = new HashMap<>();

        map.put("username", userName);
        map.put("password", passWord);
        map.put("version", Constants.version);
        map.put("devices_type", Constants.devices_type);


        long sign_time_long = System.currentTimeMillis() / 1000;
        int sign_key_int = SignUtils.getRandom(10000, 99999);

        String sign_key = String.valueOf(sign_key_int);
        String sign_time = String.valueOf(sign_time_long);

        String sign = SignUtils.get_sign(map, sign_key, sign_time);

        map.put("sign_key", sign_key);
        map.put("sign_time", sign_time);
        map.put("sign", sign);
        getLoginData3(map);


    }


    private void getLoginData3(Map<String, String> map) {

        AppClient.ApiStores apiStores = AppClient.getRetrofit(mContext).create(AppClient.ApiStores.class);
        Observable<BaseBean<UserInfoBean>> observable = apiStores.getLogin2(map);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean<UserInfoBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.d("onSubscribe");
                    }

                    @Override
                    public void onNext(BaseBean<UserInfoBean> baseBean) {
                        UserInfoBean userInfoBean = baseBean.getData();
                        LogUtils.d("onNext=" + userInfoBean.toString());

                        String token = userInfoBean.getToken();
                        SPUtils.putString(mContext, Constants.token, token);

                        openActivity(MainActivity.class);

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


}
