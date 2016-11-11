package com.liyunkun.billingsystem;

/**
 * Created by liyunkun on 2016/9/10 0010.
 * 日期的工具类
 */
public class DataUtils {
    /**
     * 根据年和月计算月的总天数
     *
     * @param year  年
     * @param mouth 月
     * @return
     */
    public static int getMouthsDay(int year, int mouth) {
        int day = 0;
        switch (mouth) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 11:
                day = 31;
                break;
            case 4:
            case 6:
            case 9:
                day = 30;
                break;
            case 2: {
                if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                    day = 29;
                } else {
                    day = 28;
                }
            }
            break;
        }
        return day;
    }
}
