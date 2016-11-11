package com.liyunkun.billingsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 登录
 */
public class LoginActivity extends AppCompatActivity {

    private EditText etUserName;//用户名
    private EditText etPassword;//密码
    private Button btSubmit;//登录
    private Button btRegister;//注册
    private TextView tvReset;//忘记密码
    private CheckBox cb;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sp = getSharedPreferences("saveName", MODE_PRIVATE);
        boolean isSave = sp.getBoolean("isSave", false);
        if(isSave){
            String userName = sp.getString("userName", "");
            Intent intent=new Intent(this,TallyActivity.class);
            intent.putExtra("username",userName);
            startActivity(intent);
            finish();
            return;
        }
        //查找控件的方法
        findView();
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = etUserName.getText().toString();
                SharedPreferences.Editor edit = sp.edit();
                edit.putBoolean("isSave",true);
                edit.putString("userName",userName);
                edit.commit();
            }
        });
        //登录的监听
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //验证并Activity跳转的方法
                verify();
            }
        });
        //注册的监听
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        //忘记密码的监听
        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ResetActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 核对用户信息，验证该用户是否已存在，并登录
     */
    private void verify() {
        //获取用户输入的用户名
        String userName = etUserName.getText().toString();
        //获取用户输入的密码
        final String password = etPassword.getText().toString();
        //验证用户输入是否正确
        if (isRight(userName, password)) {
            //验证用户输入的字符是否在[4,12]之间
            if (userName.length() >= 4 && userName.length() <= 12) {
                //验证密码格式是否正确[4,12]之间
                if (password.length() >= 6 && password.length() <= 12) {
                    //BmobQuery建立查找
                    BmobQuery<User> query = new BmobQuery<>();
                    query.addWhereEqualTo("userName", userName);
                    query.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> list, BmobException e) {
                            //判断是否查找成功
                            if (e == null) {
                                //验证是否有数据
                                if (list != null && list.size() > 0) {
                                    if (password.equals(list.get(0).getPassword())) {
                                        Intent intent = new Intent(LoginActivity.this, TallyActivity.class);
                                        intent.putExtra("username", list.get(0).getUserName());
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        etPassword.setText("");
                                        Toast.makeText(LoginActivity.this, "密码不正确", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    etUserName.setText("");
                                    etPassword.setText("");
                                    Toast.makeText(LoginActivity.this, "用户名不存在", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "网络不可用", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    etUserName.setText("");
                    etPassword.setText("");
                    Toast.makeText(LoginActivity.this, "密码格式错误", Toast.LENGTH_SHORT).show();
                }
            } else {
                etUserName.setText("");
                etPassword.setText("");
                Toast.makeText(LoginActivity.this, "用户名格式错误", Toast.LENGTH_SHORT).show();
            }
        } else {
            etUserName.setText("");
            etPassword.setText("");
            Toast.makeText(LoginActivity.this, "输入有误", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 验证用户输入的信息是否正确
     *
     * @param userName
     * @param password
     * @return
     */
    private boolean isRight(String userName, String password) {
        if (userName != null && password != null && !userName.equals("") && !password.equals("")) {
            return true;
        }
        return false;
    }

    /**
     * 查找控件
     */
    private void findView() {
        etUserName = ((EditText) findViewById(R.id.et_username));
        etPassword = ((EditText) findViewById(R.id.et_password));
        btSubmit = ((Button) findViewById(R.id.bt_login_submit));
        btRegister = ((Button) findViewById(R.id.bt_login_register));
        tvReset = ((TextView) findViewById(R.id.tv_reset));
        cb = ((CheckBox) findViewById(R.id.login_cb));
    }
}
