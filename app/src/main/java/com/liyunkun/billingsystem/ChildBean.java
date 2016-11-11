package com.liyunkun.billingsystem;

/**
 * Created by liyunkun on 2016/9/10 0010.
 * ExpandableListView的子项
 */
public class ChildBean {
    private String money;
    private String direction;
    private String time;

    public ChildBean() {
    }

    public ChildBean(String money, String direction, String time) {
        this.money = money;
        this.direction = direction;
        this.time = time;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
