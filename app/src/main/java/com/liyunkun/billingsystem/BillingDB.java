package com.liyunkun.billingsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyunkun on 2016/9/9 0009.
 * 操作账单数据库的类
 */
public class BillingDB {
    private Context context;//上下文
    private SQLiteDatabase db;//SQLiteDatabase类
    private final String TABLE=DBHelper.BILLINGTABLE;

    public BillingDB(Context context) {
        this.context = context;
        this.db=new DBHelper(context).getReadableDatabase();
    }

    //插入数据
    public int addData(Billing billing){
        if (billing != null) {
            ContentValues values=new ContentValues();
            values.put("create_year",billing.getCreateYear());
            values.put("create_mouth",billing.getCreateMouth());
            values.put("create_day",billing.getCreateDay());
            values.put("create_time",billing.getCreateTime());
            values.put("money",billing.getMoney());
            values.put("direction",billing.getDirection());
            values.put("username",billing.getUserName());
            db.insert(TABLE,null,values);
            return 1;
        }
        return -1;
    }

    //插入数据
    public int addData(List<Billing> list){
        if (list != null && list.size() >= 0) {
            db.beginTransaction();
            try{
                for (Billing billing : list) {
                    addData(billing);
                }
                db.setTransactionSuccessful();
                return list.size();
            }finally {
                db.endTransaction();
            }
        }
        return -1;
    }

    //删除数据
    public int delete(String selection,String[] selectionArgs){
        int delete = db.delete(TABLE, selection, selectionArgs);
        return delete;
    }

    //修改数据
    public int update(ContentValues values,String selection,String[] selectionArgs){
        int update = db.update(TABLE, values, selection, selectionArgs);
        return update;
    }

    //按照用户名查找信息
    public List<Billing> selectFromUserName(String userName){
        List<Billing> list=new ArrayList<>();
        if(userName != null){
            Cursor cursor = db.rawQuery("select * from " + TABLE + " where username=?", new String[]{userName});
            while (cursor.moveToNext()){
                String create_year = cursor.getString(cursor.getColumnIndex("CREATE_YEAR"));
                String create_mouth = cursor.getString(cursor.getColumnIndex("CREATE_MOUTH"));
                String create_day = cursor.getString(cursor.getColumnIndex("CREATE_DAY"));
                String create_time = cursor.getString(cursor.getColumnIndex("CREATE_TIME"));
                String money = cursor.getString(cursor.getColumnIndex("MONEY"));
                String direction = cursor.getString(cursor.getColumnIndex("DIRECTION"));
                String username = cursor.getString(cursor.getColumnIndex("USERNAME"));
                list.add(new Billing(create_year,create_mouth,create_day,create_time,money,direction,userName));
            }
        }
        return list;
    }

    /**
     *
     * @param userName
     * @param year
     * @return
     */
    public List<Billing> selectFromUserName(String userName,String year){
        List<Billing> list=new ArrayList<>();
        if(userName != null){
            Cursor cursor = db.rawQuery("select * from " + TABLE + " where username=? and create_year=?", new String[]{userName,year});
            while (cursor.moveToNext()){
                String create_year = cursor.getString(cursor.getColumnIndex("CREATE_YEAR"));
                String create_mouth = cursor.getString(cursor.getColumnIndex("CREATE_MOUTH"));
                String create_day = cursor.getString(cursor.getColumnIndex("CREATE_DAY"));
                String create_time = cursor.getString(cursor.getColumnIndex("CREATE_TIME"));
                String money = cursor.getString(cursor.getColumnIndex("MONEY"));
                String direction = cursor.getString(cursor.getColumnIndex("DIRECTION"));
                String username = cursor.getString(cursor.getColumnIndex("USERNAME"));
                list.add(new Billing(create_year,create_mouth,create_day,create_time,money,direction,userName));
            }
        }
        return list;
    }
    public List<Billing> selectFromUserName(String userName,String year,String mouth){
        List<Billing> list=new ArrayList<>();
        if(userName != null){
            Cursor cursor = db.rawQuery("select * from " + TABLE + " where username=? and create_year=? and create_mouth=?", new String[]{userName,year,mouth});
            while (cursor.moveToNext()){
                String create_year = cursor.getString(cursor.getColumnIndex("CREATE_YEAR"));
                String create_mouth = cursor.getString(cursor.getColumnIndex("CREATE_MOUTH"));
                String create_day = cursor.getString(cursor.getColumnIndex("CREATE_DAY"));
                String create_time = cursor.getString(cursor.getColumnIndex("CREATE_TIME"));
                String money = cursor.getString(cursor.getColumnIndex("MONEY"));
                String direction = cursor.getString(cursor.getColumnIndex("DIRECTION"));
                String username = cursor.getString(cursor.getColumnIndex("USERNAME"));
                list.add(new Billing(create_year,create_mouth,create_day,create_time,money,direction,userName));
            }
        }
        return list;
    }
    public List<Billing> selectFromUserName(String userName,String year,String mouth,String day){
        List<Billing> list=new ArrayList<>();
        if(userName != null){
            Cursor cursor = db.rawQuery("select * from " + TABLE + " where username=? and create_year=? and create_mouth=? and create_day=?",
                    new String[]{userName,year,mouth,day});
            while (cursor.moveToNext()){
                String create_year = cursor.getString(cursor.getColumnIndex("CREATE_YEAR"));
                String create_mouth = cursor.getString(cursor.getColumnIndex("CREATE_MOUTH"));
                String create_day = cursor.getString(cursor.getColumnIndex("CREATE_DAY"));
                String create_time = cursor.getString(cursor.getColumnIndex("CREATE_TIME"));
                String money = cursor.getString(cursor.getColumnIndex("MONEY"));
                String direction = cursor.getString(cursor.getColumnIndex("DIRECTION"));
                String username = cursor.getString(cursor.getColumnIndex("USERNAME"));
                list.add(new Billing(create_year,create_mouth,create_day,create_time,money,direction,userName));
            }
        }
        return list;
    }
}
