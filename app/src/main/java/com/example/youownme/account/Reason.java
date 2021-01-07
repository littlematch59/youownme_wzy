package com.example.youownme.account;

public enum Reason {
    MARRY,IMMIGRATION,SUCCESS;
    //分别表示"结婚大喜","新造华堂","金榜题名"
    @Override
    public String toString() {
        String temp = "";
        switch (this){
            case MARRY:
                temp = "结婚大喜";
                break;
            case IMMIGRATION:
                temp = "新造华堂";
                break;
            case SUCCESS:
                temp = "金榜题名";
                break;
            default:
                break;
        }
        return temp;
    }


    public static Reason getReason(String str){
        Reason temp = MARRY;//默认是结婚
        switch (str){
            case "结婚大喜":
                temp = MARRY;
                break;
            case "新造华堂":
                temp = IMMIGRATION;
                break;
            case "金榜题名":
                temp = SUCCESS;
                break;
            default:
                break;
        }
        return temp;
    }
}
