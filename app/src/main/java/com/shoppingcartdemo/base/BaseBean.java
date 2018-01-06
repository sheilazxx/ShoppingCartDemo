package com.shoppingcartdemo.base;

/**
 * Created by Administrator on 2017/11/30.
 */

public class BaseBean<T>  {

    private String sign;
    private int sign_key;
    private int sign_time;
    private String code;
    private String msg;

    private T data ;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getSign_key() {
        return sign_key;
    }

    public void setSign_key(int sign_key) {
        this.sign_key = sign_key;
    }

    public int getSign_time() {
        return sign_time;
    }

    public void setSign_time(int sign_time) {
        this.sign_time = sign_time;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "sign='" + sign + '\'' +
                ", sign_key=" + sign_key +
                ", sign_time=" + sign_time +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
