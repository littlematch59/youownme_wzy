package com.example.youownme.account;

import androidx.annotation.NonNull;

import com.example.youownme.dataprocess.DataBank;

import java.io.Serializable;

public class Record implements Serializable {//实现序列化接口

    Type type;
    Date date;
    double money;
    Reason reason;
    String name;


    public Record(Type type, Date date, double money, Reason reason, String name){
        this.type = type;
        this.date = date;
        this.money = money;
        this.reason = reason;
        this.name = name;
    }

    public Record(){
        this.type = Type.RECEIVE;
        this.date = DataBank.getSelectedDate();
        this.money = 0;
        this.reason = Reason.MARRY;
        this.name = "空";
    }

    public void assign(Record record){
        this.type = record.getType();
        this.date = record.getDate();
        this.reason = record.getReason();
        this.name = record.getName();
        this.money = record.getMoney();
    }

    @Override
    public boolean equals(Object o){
        if(this == o)return true;
        if(o==null || getClass() != o.getClass())
            return false;
        Record record = (Record) o;
        return this.toString().equals(record.toString());
    }

    @NonNull
    @Override
    public String toString() {
        return type.toString()+" "+date+" "+name+" "+money+" "+reason.toString();
    }

    public Type getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }

    public double getMoney() {
        return money;
    }

    public Reason getReason() {
        return reason;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

