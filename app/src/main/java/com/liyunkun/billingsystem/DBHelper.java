package com.liyunkun.billingsystem;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by liyunkun on 2016/9/9 0009.
 * 数据库的创建
 */
public class DBHelper extends SQLiteOpenHelper{
    private static final String DBNAME="billingSystem.db";
    private static final int COUNTVERSION=1;
    public static final String USERTABLE="user";
    public static final String BILLINGTABLE="billing";

    public DBHelper(Context context) {
        super(context,DBNAME,null,COUNTVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "
                +USERTABLE+" (_id INTEGER PRIMARY KEY,USERNAME VARCHAR,PASSWORD VARCHAR,TYPE INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS "
                +BILLINGTABLE+" (_id INTEGER PRIMARY KEY,CREATE_YEAR VARCHAR,CREATE_MOUTH VARCHAR,CREATE_DAY VARCHAR,CREATE_TIME VARCHAR,MONEY VARCHAR,DIRECTION VARCHAR,USERNAME VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
