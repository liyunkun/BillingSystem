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
import cn.bmob.v3.listener.UpdateListener;

public class ResetActivity extends AppCompatActivity {

    private EditText etUserName;//用户名
    private EditText etPassword;//密码
    private EditText etPassword2;//确认密码
    private Button btReset;//修改

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        //查找控件
        createFiled();
        //修改的监听
        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify();
            }
        });
    }

    private void verify() {
        //获取用户名
        String userName = etUserName.getText().toString();
        //获取密码
        final String password = etPassword.getText().toString();
        //获取确认密码
        String password2 = etPassword2.getText().toString();
        //判断用户是否输入数据
        if (printIsTrue(userName, password, password2)) {
            if (password.equals(password2)) {
                //查询数据，查看用户名是否存在
                BmobQuery<User> query = new BmobQuery<User>();
                query.addWhereEqualTo("userName", userName);
                query.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        //查询成功
                        if (e == null) {
                            //用户存在
                            if (list != null && list.size() > 0) {
                                final User user = list.get(0);
                                user.setValue("password", password);
                                user.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            Intent intent = new Intent(ResetActivity.this, TallyActivity.class);
                                            intent.putExtra("username", user.getUserName());
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(ResetActivity.this, "网络不可用", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                etUserName.setText("");
                                etPassword.setText("");
                                etPassword2.setText("");
                                Toast.makeText(ResetActivity.this, "用户名输入有误", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ResetActivity.this, "网络不可用", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else {
                etPassword.setText("");
                etPassword2.setText("");
                Toast.makeText(ResetActivity.this, "密码不匹配", Toast.LENGTH_SHORT).show();
            }
        } else {
            etUserName.setText("");
            etPassword.setText("");
            etPassword2.setText("");
            Toast.makeText(ResetActivity.this, "输入有误", Toast.LENGTH_SHORT).show();
        }
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
     * 初始化属性
     */
    private void createFiled() {
        etUserName = ((EditText) findViewById(R.id.et_username));
        etPassword = ((EditText) findViewById(R.id.et_password));
        etPassword2 = ((EditText) findViewById(R.id.et_password2));
        btReset = ((Button) findViewById(R.id.bt_reset));
    }
}
