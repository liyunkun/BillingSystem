package com.liyunkun.billingsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 添加记录
 */
public class AddActivity extends AppCompatActivity {

    private Button btAddData;//添加数据
    private Button btAddBelow;//添加记录
    private Button btMouth;//按月查询
    private Button btWeek;//按周查询
    private Button btLogin;//首页
    private EditText etDirection;//去向
    private EditText etMoney;//金额
    //用户名
    private static final String USERNAME = TallyActivity.USERNAME;
    private String year;//年
    private String mouth;//月
    private String day;//日
    private String time;//时间
    private Calendar calendar;//日历类
    private SimpleDateFormat  format;//时间格式化

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        createField();//查找控件和属性的初始化
        btAddBelow.setEnabled(false);//将添加记录设为不可以

        long timeInMillis = calendar.getTimeInMillis();
        String date = this.format.format(new Date(timeInMillis));
        String[] dates = date.split("/");
        year=dates[0];
        mouth=dates[1];
        day=dates[2];
        time=dates[3];
        //每周明细的监听
        btWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this, CheckByWeekActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //每月明细的监听
        btMouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this, CheckByMouthActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //首页的监听
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this, TallyActivity.class);
                intent.putExtra("username", USERNAME);
                startActivity(intent);
                finish();
            }
        });

        //添加数据的监听
        btAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String money = etMoney.getText().toString();//获取金额
                String direction = etDirection.getText().toString();//获取去向
                //如果用户输入数据
                if (inputIsTrue(year, mouth, day, time, money)) {
                    //插入数据到数据库
                    Billing billing = new Billing(year, mouth, day, time, money, direction, USERNAME);
                    billing.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                //跳回本页面
                                Toast.makeText(AddActivity.this, "添加成功", Toast.LENGTH_LONG).show();
                                etDirection.setText("");
                                etMoney.setText("");
                                Intent intent = new Intent(AddActivity.this, AddActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(AddActivity.this, "网络不可用", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else{
                    etDirection.setText("");
                    etMoney.setText("");
                    Toast.makeText(AddActivity.this, "输入有误，不能有空", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * @param year
     * @param mouth
     * @param day
     * @param time
     * @param money
     * @return
     */
    private boolean inputIsTrue(String year, String mouth, String day, String time, String money) {
        if (mouth != null && day != null && year != null && time != null && money != null
                && !mouth.equals("") && !day.equals("") && !year.equals("") && !time.equals("") && !money.equals("")) {
            return true;
        }
        return false;
    }

    /**
     * 查找控件和属性的初始化
     */
    private void createField() {
        calendar=Calendar.getInstance();
        format=new SimpleDateFormat("yyyy/MM/dd/HH:mm");

        btAddData = ((Button) findViewById(R.id.bt_add));
        etDirection = ((EditText) findViewById(R.id.et_direction));
        etMoney = ((EditText) findViewById(R.id.et_money));


        btAddBelow = ((Button) findViewById(R.id.bt_add_below));
        btMouth = ((Button) findViewById(R.id.bt_mouth));
        btWeek = ((Button) findViewById(R.id.bt_week));
        btLogin = ((Button) findViewById(R.id.bt_login));
    }
}
