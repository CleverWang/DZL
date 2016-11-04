package com.wangcong.dzl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by HASEE on 2016/10/14.
 */

public class change extends AppCompatActivity {
    EditText text1, text2, text3, text4;
    Button change1;
    String a, b, objID = "";
    int i;
    BmobQuery<Person> query;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.change);
        ActivityCollector.activities.add(this);
        Bmob.initialize(this, "2f221c1babaeca6d42723047fba1289e");
        change1 = (Button) findViewById(R.id.change1);
        //text1 = (EditText) findViewById(R.id.change_account);
        final String username=login.user;
        text2 = (EditText) findViewById(R.id.change_oldpassword);
        text3 = (EditText) findViewById(R.id.change_newpassword);
        text4 = (EditText) findViewById(R.id.confirm_newpassword);
        change1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text2.getText().toString().isEmpty() || text3.getText().toString().isEmpty()) {//判断用户密码、账号是否为空
                    Toast.makeText(change.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                a = username;
                b = text2.getText().toString().trim();
                if (text3.getText().toString().trim().equals(text4.getText().toString().trim())) {
                    query = new BmobQuery<Person>();
                    query.addWhereEqualTo("name", a);
                    query.addWhereEqualTo("address", b);
                    query.setLimit(1);
                    query.findObjects(new FindListener<Person>() {
                        //链接服务器数据库判断用户输入的密码、账号是否正确
                        @Override
                        public void done(List<Person> list, BmobException e) {
                            if (e == null) {
                                if (list.size() != 0) {
                                    objID = list.get(0).getObjectId();
                                    Person p2 = new Person();
                                    p2.setAddress(text3.getText().toString().trim());
                                    p2.update(objID, new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e == null) {
                                                Toast.makeText(change.this, "密码修改成功，请重新登陆！", Toast.LENGTH_SHORT).show();
                                                login.isPasswordChanged = true;
                                                Intent intent = new Intent(change.this, login.class);
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(change.this, "修改密码失败！", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else
                                    Toast.makeText(change.this, "旧密码输入错误！", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(change.this, "修改密码失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(change.this, "新密码两次输入不一致！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
