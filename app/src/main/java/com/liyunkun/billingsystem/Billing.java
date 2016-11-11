package com.liyunkun.billingsystem;

import cn.bmob.v3.BmobObject;

/**
 * Created by liyunkun on 2016/9/9 0009.
 * 账单类
 */
public class Billing extends BmobObject{
    private String createYear;//创建年
    private String createMouth;//创建月
    private String createDay;//创建日
    private String createTime;//创建时间
    private String money;//金额
    private String direction;//去向
    private String userName;//用户名（与用户类相连）

    public Billing() {
    }

    public Billing(String createYear, String createMouth, String createDay, String createTime, String money, String direction, String userName) {
        this.createYear = createYear;
        this.createMouth = createMouth;
        this.createDay = createDay;
        this.createTime = createTime;
        this.money = money;
        this.direction = direction;
        this.userName = userName;
    }

    public String getCreateYear() {
        return createYear;
    }

    public void setCreateYear(String createYear) {
        this.createYear = createYear;
    }

    public String getCreateMouth() {
        return createMouth;
    }

    public void setCreateMouth(String createMouth) {
        this.createMouth = createMouth;
    }

    public String getCreateDay() {
        return createDay;
    }

    public void setCreateDay(String createDay) {
        this.createDay = createDay;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
