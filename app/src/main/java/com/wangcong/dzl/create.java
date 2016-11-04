package com.wangcong.dzl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by HASEE on 2016/10/1.
 */

public class create extends AppCompatActivity {
    Button create;
    EditText content;
    String a;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create);
        ActivityCollector.activities.add(this);
        Bmob.initialize(this, "2f221c1babaeca6d42723047fba1289e");
        create = (Button) findViewById(R.id.create);
        content = (EditText) findViewById(R.id.content);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = content.getText().toString().trim();
                if (a.length() != 0)
                    write(a, login.user);
                else
                    Toast.makeText(create.this, "段子内容不能为空！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void write(String a, String b) {
        alldata p2 = new alldata();
        p2.setRoot(a + ":.:" + b + ":.:0");
        p2.setSecondData("");
        p2.setThirdData("");
        p2.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    Toast.makeText(create.this, "段子发布成功!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(create.this, main.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(create.this, "段子发布失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
