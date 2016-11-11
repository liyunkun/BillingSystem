package com.liyunkun.billingsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private Button btJump;
    private SharedPreferences sp;
    private boolean isRunning= true;
    private long sleepTime=5*1000;
    private int timeTextView = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        tvWelcome = ((TextView) findViewById(R.id.tv_welcome));
        btJump = ((Button) findViewById(R.id.bt_jump_welcome));
        sp = getSharedPreferences("fristInto",MODE_PRIVATE);
        btJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sp.getBoolean("isFrist",true)){
                    startActivity(new Intent(WelcomeActivity.this,LoadingActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
                    finish();
                }
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(sleepTime);
//                for(long i=1000;i<=sleepTime;){
//                    tvWelcome.setText(i+"秒");
//                    timeTextView++;
//                    sleepTime+=1000;
//                }
                if(isRunning){
                    if(sp.getBoolean("isFrist",true)){
                        startActivity(new Intent(WelcomeActivity.this,LoadingActivity.class));
                        finish();
                    }else{
                        startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
                        finish();
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }
}
