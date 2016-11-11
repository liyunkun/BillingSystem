package com.liyunkun.billingsystem;

import cn.bmob.v3.BmobObject;

/**
 * Created by liyunkun on 2016/9/9 0009.
 * 用户类
 */
public class User extends BmobObject{
    private String userName;//用户名
    private String password;//密码
    public User() {
    }

    public User(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
