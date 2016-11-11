package com.liyunkun.billingsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 按月查询
 */
public class CheckByMouthActivity extends AppCompatActivity {

    private Button btAdd;//添加记录
    private Button btLogin;//首页
    private Button btMouth;//按月查询
    private Button btWeek;//按周查询
    private Toolbar toolBar;
    private ListView lvMouth;//ListView对象;
    private TextView tvYear;
    private static final  String USERNAME=TallyActivity.USERNAME;//获取用户名
    private List<Billing> list;//查询返回结果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_by_mouth);
        createField();//查找控件
        toolBar.setTitle("搜索");
        setSupportActionBar(toolBar);//设置ActionBar
        btMouth.setEnabled(false);//设置当前按钮不可用

        //添加数据的监听
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CheckByMouthActivity.this,AddActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //首页的监听
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CheckByMouthActivity.this,TallyActivity.class);
                intent.putExtra("username",USERNAME);
                startActivity(intent);
                finish();
            }
        });

        //按周查询的监听
        btWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CheckByMouthActivity.this,CheckByWeekActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * 添加菜单
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        //
        initSearchView(menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 搜索菜单的设置
     * @param menu
     */
    private void initSearchView(Menu menu) {
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                tvYear.setText(query+"年");
                BmobQuery<Billing> query1=new BmobQuery<Billing>();
                query1.addWhereEqualTo("userName",USERNAME);
                query1.addWhereEqualTo("createYear",query);
                query1.findObjects(new FindListener<Billing>() {
                    @Override
                    public void done(List<Billing> listBilling, BmobException e) {
                        if(e == null){
                            if (listBilling != null && listBilling.size() > 0){
                                list.addAll(listBilling);
                                MyBaseAdapter adapter=new MyBaseAdapter(list,CheckByMouthActivity.this);
                                lvMouth.setAdapter(adapter);
                            }
                        }else{
                            Toast.makeText(CheckByMouthActivity.this, "网络不可用", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    /**
     * 查找的控件
     */
    private void createField() {
        list=new ArrayList<>();
        btAdd = ((Button) findViewById(R.id.bt_add_below));
        btLogin = ((Button) findViewById(R.id.bt_login));
        btMouth = ((Button) findViewById(R.id.bt_mouth));
        btWeek = ((Button) findViewById(R.id.bt_week));
        toolBar = ((Toolbar) findViewById(R.id.toobar));
        lvMouth = ((ListView) findViewById(R.id.lv_mouth));
        tvYear = ((TextView) findViewById(R.id.tv_year));
    }
}
