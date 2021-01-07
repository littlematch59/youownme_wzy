package com.example.youownme.dataprocess;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.youownme.account.Date;
import com.example.youownme.account.Reason;
import com.example.youownme.account.Record;
import com.example.youownme.account.Type;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;

public class DataBank {
    private static final String DATA_FILE_NAME="data.txt";//数据持久化
    private static Date selectedDate;
    //用ArrayList来保存数组
    private static ArrayList<Record> dateRecords = new ArrayList<>();
    private static ArrayList<Integer> yearGroup = new ArrayList<>();
    private static ArrayList<ArrayList<Record>> yearChild = new ArrayList<>();
    private static ArrayList<Record> reasonRecords = new ArrayList<>();
    private static ArrayList<Record> allRecords = new ArrayList<>();
    private static Reason selectedReason = Reason.IMMIGRATION;

    //序列化操作
    public static void Save(Context context)
    {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(context.openFileOutput(DATA_FILE_NAME,Context.MODE_PRIVATE));
            oos.writeObject(allRecords);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //反序列化操作
    public static void Load(Context context)
    {
        ObjectInputStream ois = null;
        //arrayListBooks=new ArrayList<>();
        try {
            ois = new ObjectInputStream(context.openFileInput(DATA_FILE_NAME));
            allRecords = (ArrayList<Record>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @RequiresApi(api = Build.VERSION_CODES.N)//兼容低版本的SDK
    public static void Update(){
        //按年份排序
        allRecords.sort(new Comparator<Record>() {
            @Override
            public int compare(Record r1, Record r2) {
                return r1.getDate().getYear() - r2.getDate().getYear();
            }
        });

        // 更新home数组
        dateRecords.clear();
        for (Record record: allRecords){
            if(selectedDate.equals(record.getDate())){
                dateRecords.add(record);
            }
        }
        //更新收礼的数组
        reasonRecords.clear();
        for(Record record:allRecords){
            if(selectedReason == record.getReason() && record.getType() == Type.RECEIVE)
            {
                reasonRecords.add(record);
            }
        }
        //更新随礼的数组
        int index;
        Integer year;
        yearChild.clear();
        yearGroup.clear();

        for(Record record:allRecords){
            if(record.getType()!= Type.GIVE)
                continue;
            year = record.getDate().getYear();
            if(!yearGroup.contains(year)){
                yearGroup.add(year);
                yearChild.add(new ArrayList<Record>());
            }
            index = yearGroup.indexOf(year);
            yearChild.get(index).add(record);
        }


    }
    
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void setSelectedDate(Date date){
        selectedDate = date;
        Update();
    }
    static public Date getSelectedDate(){return selectedDate;}

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void add(Record record){
        allRecords.add(record);
        Update();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void remove(int position){
        Record record = dateRecords.get(position);
        for(Record record1: allRecords){
            if(record1.equals(record)){
                allRecords.remove(record1);
                break;
            }
        }
        Update();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void change(String postData, Record record){

        for(Record record1: allRecords){
            if(record1.toString().equals(postData)){
                record1.assign(record);
                break;
            }
        }
        Update();
    }

    public static ArrayList<Integer> getYearGroup(){return yearGroup; }

    public static ArrayList<ArrayList<Record>> getYearChild(){return yearChild; }

    public static ArrayList<Record> getDateRecords(){return dateRecords; }

    public static ArrayList<Record> getReasonRecords(){return reasonRecords; }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void setSelectedReason(Reason reason){
        selectedReason =reason;
        Update();
    }

    public static Reason getSelectedReason(){return selectedReason;}

}
