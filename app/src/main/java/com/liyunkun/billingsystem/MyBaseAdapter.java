package com.liyunkun.billingsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by liyunkun on 2016/9/10 0010.
 * ListView的adapter（按月查询）
 */
public class MyBaseAdapter extends BaseAdapter{
    private List<Billing> list;
    private Context context;
    private LayoutInflater inflater;

    public MyBaseAdapter(List<Billing> list, Context context) {
        this.list = list;
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView=inflater.inflate(R.layout.child_items,parent,false);
            holder=new ViewHolder();
            holder.tvTime= (TextView) convertView.findViewById(R.id.tv_child_day);
            holder.tvThing= (TextView) convertView.findViewById(R.id.tv_child_thing);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        Billing billing = list.get(position);
        String createMouth = billing.getCreateMouth();
        String createDay = billing.getCreateDay();
        String data =createMouth+"月"+createDay+"日";
        String time=data+"\t"+billing.getCreateTime();
        String thing="金额： "+billing.getMoney()+"\n\n"+"去向： "+billing.getDirection();
        holder.tvTime.setText(time);
        holder.tvThing.setText(thing);
        return convertView;
    }
    private class ViewHolder{
        TextView tvTime;
        TextView tvThing;
    }
}
