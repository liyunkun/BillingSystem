package com.liyunkun.billingsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 首页
 */
public class TallyActivity extends AppCompatActivity {

    private Button btLogin;//首页
    private Button btAdd;//添加记录
    private Button btMouth;//每月明细
    private Button btWeek;//每周明细
    private ExpandableListView elLogin;
    public static String USERNAME;//用户名
    private TextView tvUserName;//用户名文本
    private List<GroupBean> list;//分组信息
    private List<String> data;//创建时间
    private ImageView ivBack;
    private MyAdapter adapter;
    private ExpandableListView.ExpandableListContextMenuInfo menuInfo;
    private List<ChildBean> childBeanListUpdate;
    private List<ChildBean> childBeanDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tally);
        //查找控件
        createField();
        //获取用户名
        getUserName();
        //设置data的数据
        intoData();
        //将首页的按钮设置为不可点击
        btLogin.setEnabled(false);
        //给ExpandableListView添加菜单
        registerForContextMenu(elLogin);
        //设置用户
        tvUserName.setText(USERNAME + "用户，你好！");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("saveName", MODE_PRIVATE);
                sp.edit().putBoolean("isSave", false).commit();
                startActivity(new Intent(TallyActivity.this, LoginActivity.class));
                finish();
            }
        });
        btMouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TallyActivity.this, CheckByMouthActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TallyActivity.this, AddActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TallyActivity.this, CheckByWeekActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * 添加数据到list集合
     */
    private void intoList() {
        for (int i = 0; i < data.size(); i++) {
            final List<ChildBean> childBeanlist = new ArrayList<>();
            String[] datas = data.get(i).split("/");
            BmobQuery<Billing> query = new BmobQuery<>();
            query.addWhereEqualTo("userName", USERNAME);
            query.addWhereEqualTo("createYear", datas[0]);
            query.addWhereEqualTo("createMouth", datas[1]);
            query.addWhereEqualTo("createDay", datas[2]);
            query.order("-createYear,-createMouth,-createDay");
            query.findObjects(new FindListener<Billing>() {
                @Override
                public void done(List<Billing> listBilling, BmobException e) {
                    if (e == null) {
                        if (listBilling != null && listBilling.size() > 0) {
                            for (Billing billing : listBilling) {
                                String createTime = billing.getCreateTime();
                                String money = billing.getMoney();
                                String direction = billing.getDirection();
                                childBeanlist.add(new ChildBean(money, direction, createTime));
                            }
                        }
                    } else {
                        Toast.makeText(TallyActivity.this, "网络不可用", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            list.add(new GroupBean(data.get(i), childBeanlist));
            //自定义Adapter，ExpandableListView
            adapter = new MyAdapter(list, this);
            //ExpandableListView设置适配器
            elLogin.setAdapter(adapter);
            elLogin.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    return false;
                }
            });
        }
    }

    /**
     * 添加数据到data集合
     */
    private void intoData() {
        BmobQuery<Billing> query = new BmobQuery<>();
        query.addWhereEqualTo("userName", USERNAME);
        query.findObjects(new FindListener<Billing>() {
            @Override
            public void done(List<Billing> list, BmobException e) {
                if (e == null) {
                    if (list != null && list.size() > 0) {
                        for (Billing billing : list) {
                            String createYear = billing.getCreateYear();
                            String createMouth = billing.getCreateMouth();
                            String createDay = billing.getCreateDay();
                            String createData = createYear + "/" + createMouth + "/" + createDay;
                            if (!data.contains(createData)) {
                                data.add(createData);
                            }
                        }
                    }
                    //设置list的数据
                    intoList();
                } else {
                    Toast.makeText(TallyActivity.this, "网络不可用", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 查找控件
     */
    private void createField() {
        list = new ArrayList<>();
        data = new ArrayList<>();
        btLogin = ((Button) findViewById(R.id.bt_login));
        btAdd = ((Button) findViewById(R.id.bt_add));
        btMouth = ((Button) findViewById(R.id.bt_mouth));
        btWeek = ((Button) findViewById(R.id.bt_week));
        elLogin = ((ExpandableListView) findViewById(R.id.el_login));
        tvUserName = ((TextView) findViewById(R.id.tv_userName));
        ivBack = ((ImageView) findViewById(R.id.back_iv));
    }

    /**
     * 获取用户名
     */
    private void getUserName() {
        Intent intent = getIntent();
        USERNAME = intent.getStringExtra("username");
    }

    //添加菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.billing_menu, menu);
        if (v == elLogin) {
            super.onCreateContextMenu(menu, v, menuInfo);
        }
    }

    //菜单的监听
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.updateData: {//修改数据
                menuInfo = ((ExpandableListView.ExpandableListContextMenuInfo) item.getMenuInfo());
                LayoutInflater inflater = LayoutInflater.from(TallyActivity.this);
                long packedPosition = menuInfo.packedPosition;
                //获取分组position
                final int group = ExpandableListView.getPackedPositionGroup(packedPosition);
                //获取小组position
                final int child = ExpandableListView.getPackedPositionChild(packedPosition);
                GroupBean groupBean = list.get(group);
                final String data = groupBean.getData();
                childBeanListUpdate = groupBean.getList();
                View view = inflater.inflate(R.layout.update_layout, null);
                final EditText updateMoney = (EditText) view.findViewById(R.id.et_money_update);
                final EditText updateDirection = (EditText) view.findViewById(R.id.et_direction_update);
                AlertDialog.Builder builder = new AlertDialog.Builder(TallyActivity.this);
                builder.setTitle("修改数据")
                        .setView(view)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final String money = updateMoney.getText().toString();
                                final String direction = updateDirection.getText().toString();
                                String[] dataArr = data.split("/");
                                ChildBean childBean = childBeanListUpdate.get(child);
                                final String time = childBean.getTime();
                                BmobQuery<Billing> query = new BmobQuery<Billing>();
                                query.addWhereEqualTo("userName", USERNAME);
                                query.addWhereEqualTo("createYear", dataArr[0]);
                                query.addWhereEqualTo("createMouth", dataArr[1]);
                                query.addWhereEqualTo("createDay", dataArr[2]);
                                query.addWhereEqualTo("createTime", time);
                                query.findObjects(new FindListener<Billing>() {
                                    @Override
                                    public void done(List<Billing> listB, BmobException e) {
                                        if (e == null) {
                                            if (listB != null && listB.size() > 0) {
                                                Billing bUpdate = listB.get(0);
                                                bUpdate.setValue("money", money);
                                                bUpdate.setValue("direction", direction);
                                                bUpdate.update(new UpdateListener() {
                                                    @Override
                                                    public void done(BmobException e) {
                                                        if (e == null) {
                                                            childBeanListUpdate.set(child, new ChildBean(money, direction, time));
                                                            list.set(group, new GroupBean(data, childBeanListUpdate));
                                                            adapter.notifyDataSetChanged();
                                                        } else {
                                                            Toast.makeText(TallyActivity.this, "修改失败，网络不好", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            }
                                        } else {
                                            Toast.makeText(TallyActivity.this, "查询失败，网络不好", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        })
                        .setNegativeButton("取消", null);
                builder.create().show();
            }
            break;
            case R.id.deleteData: {//删除数据
                menuInfo = ((ExpandableListView.ExpandableListContextMenuInfo) item.getMenuInfo());
                long packedPosition = menuInfo.packedPosition;
                //获取分组position
                final int group = ExpandableListView.getPackedPositionGroup(packedPosition);
                //获取小组position
                final int child = ExpandableListView.getPackedPositionChild(packedPosition);
                final GroupBean groupBean = list.get(group);
                final String data = groupBean.getData();
                childBeanDelete = groupBean.getList();
                String[] dataArr = data.split("/");
                ChildBean childBean = childBeanDelete.get(child);
                final String time = childBean.getTime();
                BmobQuery<Billing> query = new BmobQuery<Billing>();
                query.addWhereEqualTo("userName", USERNAME);
                query.addWhereEqualTo("createYear", dataArr[0]);
                query.addWhereEqualTo("createMouth", dataArr[1]);
                query.addWhereEqualTo("createDay", dataArr[2]);
                query.addWhereEqualTo("createTime", time);
                query.findObjects(new FindListener<Billing>() {
                    @Override
                    public void done(List<Billing> listB, BmobException e) {
                        if (e == null) {
                            if (listB != null && listB.size() > 0) {
                                Billing billingD = listB.get(0);
                                billingD.delete(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            if(childBeanDelete.size() == 0){
                                                list.remove(group);
                                                adapter.notifyDataSetChanged();
                                            }else{
                                                childBeanDelete.remove(child);
                                                list.set(group,new GroupBean(data,childBeanDelete));
                                                adapter.notifyDataSetChanged();
                                            }
                                        } else {
                                            Toast.makeText(TallyActivity.this, "删除失败，网络不好", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        } else {
                            Toast.makeText(TallyActivity.this, "查询失败，网络不好", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
            break;
        }
        return super.onContextItemSelected(item);
    }
}
