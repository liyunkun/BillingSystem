package com.liyunkun.billingsystem;

import java.util.List;

/**
 * Created by liyunkun on 2016/9/10 0010.
 * 每周明细的分组类
 */
public class WeekGroupBean {

    private String week;
    private List<String> dayList;

    public WeekGroupBean() {
    }

    public WeekGroupBean(String week, List<String> dayList) {
        this.week = week;
        this.dayList = dayList;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public List<String> getDayList() {
        return dayList;
    }

    public void setDayList(List<String> dayList) {
        this.dayList = dayList;
    }
}
