package com.example.youownme.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.youownme.R;
import com.example.youownme.dataprocess.DataBank;
import com.example.youownme.account.Date;
import com.example.youownme.account.Record;
import com.example.youownme.account.Reason;
import com.example.youownme.account.Type;
import com.example.youownme.ui.Receive.ReceiveFragment;
import com.example.youownme.ui.give.GiveFragment;
import com.example.youownme.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    Context context;

    private final int REQUEST_CODE_ADD_RECORD = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_give, R.id.navigation_home, R.id.navigation_receive)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
       // NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        getSupportActionBar().hide();//隐藏标题栏

        //悬浮加号响应函数
        FloatingActionButton button = findViewById(R.id.floatingButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog(DataBank.getSelectedDate(),-1);
            }
        });
        context = this;

    }
    private Type type;
    private Reason reason;
    private void showEditDialog(final Date date,int position) {

        //设置布局和标题
        AlertDialog.Builder editDialog = new AlertDialog.Builder(MainActivity.this);
        final View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog, null);
        editDialog.setTitle(date.toString());
        editDialog.setView(dialogView);

        //获取组件(Name/Money/Reason/Type)
        final EditText editTextName = dialogView.findViewById(R.id.edit_text_name);
        final EditText editTextMoney = dialogView.findViewById(R.id.edit_text_money);
        Spinner spinnerReason = dialogView.findViewById(R.id.spinner_reason);
        Spinner spinnerType = dialogView.findViewById(R.id.spinner_type);


        //修改视图
        Record record;
        if (position < 0) {
            record = new Record();
        } else {
            record = DataBank.getDateRecords().get(position);
        }
        type = record.getType();
        reason = record.getReason();
        editTextName.setText(record.getName());
        editTextMoney.setText(record.getMoney() + "");

        SpinnerAdapter sAdapter = spinnerReason.getAdapter();
        int len = sAdapter.getCount();
        String str = record.getReason().toString();
        for (int i = 0; i < len; ++i) {
            if (str.equals(sAdapter.getItem(i).toString())) {
                spinnerReason.setSelection(i, true);
                break;
            }
        }

        sAdapter = spinnerType.getAdapter();
        len = sAdapter.getCount();
        str = record.getType().toString();
        for (int i = 0; i < len; ++i) {
            if (str.equals(sAdapter.getItem(i).toString())) {
                spinnerType.setSelection(i, true);
                break;
            }
        }

        //随礼、收礼列表选择
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String info = parent.getItemAtPosition(position).toString();    // 获取选中的文本
                type = Type.getType(info);  // 转换为type
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //原因列表选择
        spinnerReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String info = parent.getItemAtPosition(position).toString();    // 获取选中的文本
                reason = Reason.getReason(info);  // 转换为type
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //按钮响应函数
        editDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataBank.add(
                                new Record(type, date,
                                        Double.parseDouble(editTextMoney.getText().toString()),
                                        reason, editTextName.getText().toString()));
                        DataBank.Save(context);
                        HomeFragment.notifyDataSetChanged();
                        GiveFragment.notifyDataSetChanged();
                        ReceiveFragment.notifyDataSetChanged();

                    }
                });
        editDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }
        );

    editDialog.show();
    }
}