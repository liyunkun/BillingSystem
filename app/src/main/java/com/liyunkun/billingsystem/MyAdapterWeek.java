package com.liyunkun.billingsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by liyunkun on 2016/9/10 0010.
 * CheckByWeekActivity中ExpandableListView的适配器
 */
public class MyAdapterWeek extends BaseExpandableListAdapter {
    private Context context;
    private List<WeekGroupBean> list;
    private LayoutInflater inflater;
    private int year;
    private int mouth;
    private static final String USERNAME = CheckByWeekActivity.USERNAME;
    private StringBuffer buffer;

    public MyAdapterWeek(Context context, List<WeekGroupBean> list, int year, int mouth) {
        this.context = context;
        this.list = list;
        this.year = year;
        this.mouth = mouth;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getDayList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getDayList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.week_group_items, parent, false);
            holder = new GroupHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.tv_week_group);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        holder.textView.setText(list.get(groupPosition).getWeek());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.week_child_items, parent, false);
            holder = new ChildHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.tv_week_child);
            holder.textView2 = (TextView) convertView.findViewById(R.id.tv2_week_child);
            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }
        String mouthString = "";
        if (mouth > 0 && mouth < 10) {
            mouthString = "0" + mouth;
        } else {
            mouthString = String.valueOf(mouth);
        }
        String text = list.get(groupPosition).getDayList().get(childPosition);
        holder.textView.setText(text);
        String day = text.substring(0, text.indexOf("号"));
        String yearString = String.valueOf(year);
        buffer = new StringBuffer();
        BmobQuery<Billing> query = new BmobQuery<>();
        query.addWhereEqualTo("userName", USERNAME);
        query.addWhereEqualTo("createYear", yearString);
        query.addWhereEqualTo("createMouth", mouthString);
        query.addWhereEqualTo("createDay", day);
        query.findObjects(new FindListener<Billing>() {
            @Override
            public void done(List<Billing> list, BmobException e) {
                if (e == null) {
                    if (list != null && list.size() > 0) {
                        buffer.delete(0,buffer.length());
                        for (Billing billing : list) {
                            String createTime = billing.getCreateTime();
                            String money = billing.getMoney();
                            String direction = billing.getDirection();
                            String result = createTime + "\n" + "金额： " + money + "\n" + "去向: " + direction+"\n\n";
                            buffer.append(result);
                            holder.textView2.setText(buffer.toString());
                        }
                    }else{
                        holder.textView2.setText("");
                    }
                } else {
                    Toast.makeText(context, "网络不可用", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private class GroupHolder {
        TextView textView;
    }

    private class ChildHolder {
        TextView textView;
        TextView textView2;
    }
}
