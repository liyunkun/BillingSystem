package com.liyunkun.billingsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class LoadingActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private Button btOpen;
    private List<ImageView> list;
    private int[] imgs=new int[]{R.drawable.loading_1,R.drawable.loading_2,R.drawable.loading_3,R.drawable.loading_4};
    private LinearLayout layout;
    private SharedPreferences sp;
    private int lastPosition=0;
    private Button btJumpLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        viewPager = ((ViewPager) findViewById(R.id.viewpager));
        btOpen = ((Button) findViewById(R.id.bt_open_loading));
        layout = ((LinearLayout) findViewById(R.id.layout));
        btJumpLoading = ((Button) findViewById(R.id.bt_jump_loading));
        sp = getSharedPreferences("fristInto",MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("isFrist",false);
        edit.commit();
        intoData();
        MyPagerAdapter adapter=new MyPagerAdapter(this,list);
        viewPager.setAdapter(adapter);
        btJumpLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoadingActivity.this,LoginActivity.class));
                finish();
            }
        });
        btOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoadingActivity.this,LoginActivity.class));
                finish();
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                layout.getChildAt(position).setEnabled(true);
                layout.getChildAt(lastPosition).setEnabled(false);
                lastPosition=position;
                if(position==list.size()-1){
                    btOpen.setVisibility(View.VISIBLE);
                }else{
                    btOpen.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void intoData() {
        list=new ArrayList<>();
        for (int i = 0; i < imgs.length; i++) {
            ImageView iv=new ImageView(this);
            iv.setImageResource(imgs[i]);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            list.add(iv);
            LinearLayout.LayoutParams lp= new LinearLayout.LayoutParams((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()),
                    (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
            lp.leftMargin=20;
            View view=new View(this);
            view.setLayoutParams(lp);
            view.setBackgroundResource(R.drawable.dot_bg);
            view.setEnabled(false);
            layout.addView(view);
        }
        layout.getChildAt(0).setEnabled(true);
    }
}
