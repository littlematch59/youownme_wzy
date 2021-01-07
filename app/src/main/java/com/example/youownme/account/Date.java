package com.example.youownme.account;
import androidx.annotation.NonNull;

import java.io.Serializable;

public class Date implements  Serializable{
    private int year, month, day;

    public Date(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;
    }
    //接收str型数据并转换为年月日
    public Date(String str){
        String[] date = str.split("\\.");
        this.year = Integer.parseInt(date[0]);
        this.month = Integer.parseInt(date[1])-1;//在显示的时候比正确月份多1
        this.day = Integer.parseInt(date[2]);
    }


    @Override
    public boolean equals(Object o){//判断对象是否一样
        if(this == o )return true;
        if(o==null || getClass() != o.getClass())
            return false;
        Date date = (Date) o;
        return year == date.year && month == date.month && day == date.day;
    }
    @NonNull
    @Override
    public String toString() {
        return year + "." + (month+1) + "." + day;
    }

    public int getYear() {
        return year;
    }

}
