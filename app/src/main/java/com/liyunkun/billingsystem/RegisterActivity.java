package com.liyunkun.billingsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * 注册
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText etUserName;//用户名
    private EditText etPassword;//密码
    private EditText etPassword2;//确认密码
    private Button btRegister;//注册

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //控件的查找
        createFiled();
        //注册的监听
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //用户名
                final String userName = etUserName.getText().toString();
                //密码
                final String password = etPassword.getText().toString();
                //确认密码
                String password2 = etPassword2.getText().toString();
                if (printIsTrue(userName, password, password2)) {
                    if (password.equals(password2)) {
                        BmobQuery<User> query = new BmobQuery<User>();
                        query.addWhereEqualTo("userName", userName);
                        query.findObjects(new FindListener<User>() {
                            @Override
                            public void done(List<User> list, BmobException e) {
                                if (e == null) {
                                    if (list != null && list.size() > 0) {
                                        etUserName.setText("");
                                        etPassword.setText("");
                                        etPassword2.setText("");
                                        Toast.makeText(RegisterActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();
                                    } else {
                                        final User user = new User(userName, password);
                                        user.save(new SaveListener<String>() {
                                            @Override
                                            public void done(String s, BmobException e) {
                                                if (e == null) {
                                                    Intent intent = new Intent(RegisterActivity.this, TallyActivity.class);
                                                    intent.putExtra("username", user.getUserName());
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    Toast.makeText(RegisterActivity.this, "网络不可用", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                } else {
                                    Toast.makeText(RegisterActivity.this, "网络不可用", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        etPassword.setText("");
                        etPassword2.setText("");
                        Toast.makeText(RegisterActivity.this, "密码不匹配", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    etUserName.setText("");
                    etPassword.setText("");
                    etPassword2.setText("");
                    Toast.makeText(RegisterActivity.this, "输入有误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 验证用户是否输入正确
     *
     * @param userName
     * @param password
     * @param password2
     * @return
     */
    private boolean printIsTrue(String userName, String password, String password2) {
        if (userName != null && password != null && password2 != null
                && !userName.equals("") && !password.equals("") && !password2.equals("")) {
            return true;
        }
        return false;
    }

    /**
     * 查找控件和初始化类属性
     */
    private void createFiled() {
        etUserName = ((EditText) findViewById(R.id.et_username));
        etPassword = ((EditText) findViewById(R.id.et_password));
        etPassword2 = ((EditText) findViewById(R.id.et_password2));
        btRegister = ((Button) findViewById(R.id.bt_register));
    }

}
