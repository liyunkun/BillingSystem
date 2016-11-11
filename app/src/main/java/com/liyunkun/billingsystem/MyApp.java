package com.liyunkun.billingsystem;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * Created by liyunkun on 2016/9/22 0022.
 */
public class MyApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "d069ee6eb5663b6067e7b2cde89f8db3");
    }
}
