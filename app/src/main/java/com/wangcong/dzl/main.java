package com.wangcong.dzl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Wang Cong on 2016/9/30.
 */

public class main extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ActivityCollector.activities.add(this);
        login.isPasswordChanged=false;
        login.isLogout=false;

        Button cretebutton = (Button) findViewById(R.id.create);
        cretebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main.this, create.class);
                startActivity(intent);
            }
        });

        Button goonbutton = (Button) findViewById(R.id.go_on);
        goonbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main.this, goOn.class);
                startActivity(intent);
            }
        });

        Button readbutton = (Button) findViewById(R.id.read);
        readbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main.this, read.class);
                startActivity(intent);
            }
        });

        Button godbutton = (Button) findViewById(R.id.god);
        godbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(main.this, "大神帮我来P图功能还在开发中，敬请期待。", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.copyright, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.copyright_info:
                Toast.makeText(this, "欢迎使用“段子楼”，本APP由“6得飞起”团队开发，团队成员：陈忠庆、王聪、张慰慈。请勿将本APP用于商业用途，谢谢合作。", Toast.LENGTH_LONG).show();
                break;
            case R.id.collection:

                break;
            case R.id.personal_settings:
                Intent intent2 = new Intent(main.this, personal_setting.class);
                startActivity(intent2);
                break;
            default:
        }
        return true;
    }

    public void onBackPressed() {
        super.onBackPressed();
        login.exitAll = true;
    }
}
