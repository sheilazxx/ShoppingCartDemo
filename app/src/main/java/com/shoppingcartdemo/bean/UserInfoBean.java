package com.shoppingcartdemo.bean;

/**
 * Created by Administrator on 2017/11/30.
 */

public class UserInfoBean  {
    /**
     * token : b3f887ed2640bb0fc18f2e769354ac4f
     * user_name : 111111
     * mobile_phone : 13699247303
     * sex : 0
     * pay_points : 7
     * rank_points : 0
     * birthday : 2017-09-18
     * headimg : http://1.wu10086.cn/data/headimg/201711/5a13bfdf4f244.jpg
     * is_entrepreneur : 0
     * im_uid : 269562
     * is_agent : 0
     */

    private String token;
    private String user_name;
    private String mobile_phone;
    private String sex;
    private String pay_points;
    private String rank_points;
    private String birthday;
    private String headimg;
    private String is_entrepreneur;
    private String im_uid;
    private String is_agent;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMobile_phone() {
        return mobile_phone;
    }

    public void setMobile_phone(String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPay_points() {
        return pay_points;
    }

    public void setPay_points(String pay_points) {
        this.pay_points = pay_points;
    }

    public String getRank_points() {
        return rank_points;
    }

    public void setRank_points(String rank_points) {
        this.rank_points = rank_points;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getIs_entrepreneur() {
        return is_entrepreneur;
    }

    public void setIs_entrepreneur(String is_entrepreneur) {
        this.is_entrepreneur = is_entrepreneur;
    }

    public String getIm_uid() {
        return im_uid;
    }

    public void setIm_uid(String im_uid) {
        this.im_uid = im_uid;
    }

    public String getIs_agent() {
        return is_agent;
    }

    public void setIs_agent(String is_agent) {
        this.is_agent = is_agent;
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "token='" + token + '\'' +
                ", user_name='" + user_name + '\'' +
                ", mobile_phone='" + mobile_phone + '\'' +
                ", sex='" + sex + '\'' +
                ", pay_points='" + pay_points + '\'' +
                ", rank_points='" + rank_points + '\'' +
                ", birthday='" + birthday + '\'' +
                ", headimg='" + headimg + '\'' +
                ", is_entrepreneur='" + is_entrepreneur + '\'' +
                ", im_uid='" + im_uid + '\'' +
                ", is_agent='" + is_agent + '\'' +
                '}';
    }
}
