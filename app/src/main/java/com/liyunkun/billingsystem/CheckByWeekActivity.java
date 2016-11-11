package com.liyunkun.billingsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * 按周查询
 */
public class CheckByWeekActivity extends AppCompatActivity {

    private Spinner SpinnerYear;//年的Spinner
    private Spinner SpinnerMouth;//月的Spinner
    private ExpandableListView evWeek;
    private Button btWeek;//按周查询
    private Button btMouth;//每月明细
    private Button btAdd;//添加数据
    private Button btLogin;//首页
    private int year;//年
    private int mouth;//月
    private List<Integer> listYear;//年的List
    private List<Integer> listMouth;//月的List
    private List<WeekGroupBean> list;//分组类的List
    public static final String USERNAME = TallyActivity.USERNAME;//用户名
    private Button btSelect;//查询按钮
    private MyAdapterWeek adapterWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_by_week);
        //控件的初始化
        createField();
        //为月的list和年的List添加数据
        intoData();
        //定义年spinner的适配器
        ArrayAdapter adapterYear = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listYear);
        SpinnerYear.setAdapter(adapterYear);
        //定义月spinner的适配器
        ArrayAdapter adapterMouth = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listMouth);
        SpinnerMouth.setAdapter(adapterMouth);
        //年的Spinner的点击item事件的监听
        SpinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //记录用户所点击的年
                year = listYear.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //月Spinner的点击item事件的监听
        SpinnerMouth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //记录用户选择的月
                mouth = listMouth.get(position);
                //根据年和月计算月的总天数
                int mouthsDay = DataUtils.getMouthsDay(year, mouth);
                //清除list的数据
                list.clear();
                //填充数据到有关WeekGroupBean的list
                setList(mouthsDay);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //设置当前页面的点击事件不可用
        btWeek.setEnabled(false);
        //每周明细的点击事件
        btMouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckByWeekActivity.this, CheckByMouthActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //首页的点击事件
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckByWeekActivity.this, TallyActivity.class);
                intent.putExtra("username", USERNAME);
                startActivity(intent);
                finish();
            }
        });
        //添加数据的点击事件
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckByWeekActivity.this, AddActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //查询按钮的点击事件
        btSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Week中ExpandableListView的适配器
                adapterWeek = new MyAdapterWeek(CheckByWeekActivity.this, list, year, mouth);
                adapterWeek.notifyDataSetChanged();
                evWeek.setAdapter(adapterWeek);
            }
        });

    }

    /**
     * 根据月的总天数填充list
     *
     * @param mouthsDay 月的总天数
     */
    private void setList(int mouthsDay) {
        int count = (mouthsDay / 7) + 1;
        for (int i = 1; i <= count; i++) {
            List<String> listDays = new ArrayList<>();
            if (i == count) {
                for (int j = 7 * (count - 1); j <= mouthsDay; j++) {
                    listDays.add(j + "号");
                }
            } else {
                int j = ((i - 1) * 7) + 1;
                for (; j <= (i * 7); j++) {
                    if(j< 10 && j > 0){
                        listDays.add("0"+j + "号");
                    }else{
                        listDays.add(j + "号");
                    }
                }
            }
            list.add(new WeekGroupBean("第" + i + "周", listDays));
        }
    }

    private void intoData() {
        for (int i = 1; i < 13; i++) {
            listMouth.add(i);
        }
        for (int i = 2013; i < 2017; i++) {
            listYear.add(i);
        }
    }

    private void createField() {
        listYear = new ArrayList<>();
        listMouth = new ArrayList<>();
        list = new ArrayList<>();
        SpinnerYear = ((Spinner) findViewById(R.id.spinner_year));
        SpinnerMouth = ((Spinner) findViewById(R.id.spinner_mouth));
        evWeek = ((ExpandableListView) findViewById(R.id.ev_week));
        btWeek = ((Button) findViewById(R.id.bt_week));
        btMouth = ((Button) findViewById(R.id.bt_mouth));
        btAdd = ((Button) findViewById(R.id.bt_add_below));
        btLogin = ((Button) findViewById(R.id.bt_login));
        btSelect = ((Button) findViewById(R.id.bt_select));
    }
}
