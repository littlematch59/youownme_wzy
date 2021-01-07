package com.example.youownme.account;

import androidx.annotation.NonNull;

public enum Type {
    RECEIVE, GIVE;

    @NonNull
    @Override
    public String toString() {
        String temp="";
        switch (this){
            case RECEIVE:
                temp="收礼";
                break;
            case GIVE:
                temp="随礼";
                break;
            default:
                break;
        }
        return temp;
    }
    public static Type getType(String str){
        Type temp = RECEIVE;
        switch (str){
            case "收礼":
                temp = RECEIVE;
                break;
            case "随礼":
                temp = GIVE;
                break;
            default:
                break;
        }
        return temp;
    }
}
