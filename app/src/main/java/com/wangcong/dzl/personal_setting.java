package com.wangcong.dzl;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by HASEE on 2016/10/14.
 */

public class personal_setting extends AppCompatActivity {
    Button logout, change_password, collection, written, mine;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.personal_setting);
        ActivityCollector.activities.add(this);
        Button logout = (Button) findViewById(R.id.logout);
        Button change_password = (Button) findViewById(R.id.change_password);
        Button collection = (Button) findViewById(R.id.collection);
        Button written = (Button) findViewById(R.id.written);
        Button mine = (Button) findViewById(R.id.mine);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
                login.isLogout=true;
                Intent intent1 = new Intent(personal_setting.this, login.class);
                startActivity(intent1);
            }
        });
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(personal_setting.this, change.class);
                startActivity(intent);
            }
        });
        mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(personal_setting.this, test.class);
                startActivity(intent);
            }
        });
        written.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(personal_setting.this, continued.class);
                startActivity(intent);
            }
        });

        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(personal_setting.this, collection.class);
                startActivity(intent);
            }
        });
    }
}
