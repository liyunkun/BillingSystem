package com.liyunkun.billingsystem;

import java.util.List;

/**
 * Created by liyunkun on 2016/9/10 0010.
 * ExpandableListView的分组项
 */
public class GroupBean {

    private String data;
    private List<ChildBean> list;

    public GroupBean() {
    }

    public GroupBean(String data, List<ChildBean> list) {
        this.data = data;
        this.list = list;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<ChildBean> getList() {
        return list;
    }

    public void setList(List<ChildBean> list) {
        this.list = list;
    }
}
