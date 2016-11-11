package com.liyunkun.billingsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by liyunkun on 2016/9/10 0010.
 * ExpandableListView的adapter
 */
public class MyAdapter extends BaseExpandableListAdapter{

    private List<GroupBean> list;
    private Context context;
    private LayoutInflater inflater;

    public MyAdapter(List<GroupBean> list, Context context) {
        this.list = list;
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getList().get(childPosition);
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
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder;
        if(convertView == null){
            convertView=inflater.inflate(R.layout.group_itens,parent,false);
            holder=new GroupHolder();
            holder.tvData= (TextView) convertView.findViewById(R.id.tv_group);
            convertView.setTag(holder);
        }else{
            holder= (GroupHolder) convertView.getTag();
        }
        holder.tvData.setText(list.get(groupPosition).getData());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder holder;
        if(convertView == null){
            convertView=inflater.inflate(R.layout.child_items,parent,false);
            holder=new ChildHolder();
            holder.tvTime= (TextView) convertView.findViewById(R.id.tv_child_day);
            holder.tvThing= (TextView) convertView.findViewById(R.id.tv_child_thing);
            convertView.setTag(holder);
        }else{
            holder= (ChildHolder) convertView.getTag();
        }
        List<ChildBean> listChild = this.list.get(groupPosition).getList();
        ChildBean childBean = listChild.get(childPosition);
        holder.tvTime.setText(childBean.getTime());
        String thing="金额： "+childBean.getMoney()+"\n\n去向： "+childBean.getDirection();
        holder.tvThing.setText(thing);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    private class GroupHolder{
        TextView tvData;
    }
    private class ChildHolder{
        TextView tvTime;
        TextView tvThing;
    }

}
