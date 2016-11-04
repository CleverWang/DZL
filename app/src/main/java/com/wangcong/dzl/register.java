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
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Wang Cong on 2016/9/28.
 */
/*class alldata extends BmobObject {
    private String root;
    private String secondData;
    private String thirdData;

    public String getRoot() {
        return root;
    }

    public void setRoot(String name) {
        this.root = name;
    }

    public String getThirdData() {
        return thirdData;
    }

    public void setThirdData(String thirdData) {
        this.thirdData = thirdData;
    }

    public String getSecondData() {
        return secondData;
    }

    public void setSecondData(String address) {
        this.secondData = address;
    }
}*/

public class register extends AppCompatActivity {
    EditText text1, text2, text3;
    static Person p2 = new Person();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.register);
        ActivityCollector.activities.add(this);
        Bmob.initialize(this, "2f221c1babaeca6d42723047fba1289e");
        text1 = (EditText) findViewById(R.id.register_account);
        text2 = (EditText) findViewById(R.id.register_password);
        text3 = (EditText) findViewById(R.id.password_confirm);
        Button button = (Button) findViewById(R.id.register);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name = text1.getText().toString().trim();
                if (user_name.length() == 0) {
                    Toast.makeText(register.this, "请输入用户名！", Toast.LENGTH_SHORT).show();
                } else {

                    BmobQuery<Person> query = new BmobQuery<Person>();
                    query.addWhereEqualTo("name", user_name);
                    query.setLimit(1);
                    query.findObjects(new FindListener<Person>() {
                        @Override
                        public void done(List<Person> list, BmobException e) {
                            if (list != null && list.size() != 0) {
                                Toast.makeText(register.this, "用户名已被注册！", Toast.LENGTH_SHORT).show();
                                text1.setText("");
                            } else {
                                register.p2.setName(text1.getText().toString().trim());
                                String passwd = text2.getText().toString().trim();
                                if (passwd.length() == 0) {
                                    Toast.makeText(register.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (passwd.equals(text3.getText().toString().trim())) {
                                        register.p2.setAddress(passwd);
                                        register.p2.save(new SaveListener<String>() {
                                            @Override
                                            public void done(String objectId, BmobException e) {
                                                if (e == null) {
                                                    Toast.makeText(register.this, "注册成功！", Toast.LENGTH_SHORT).show();
                                                    String name = new String(text1.getText().toString().trim());
                                                    String password = new String(text2.getText().toString().trim());
                                                    Intent intent = new Intent(register.this, login.class);
                                                    intent.putExtra("username", name);
                                                    intent.putExtra("password", password);
                                                    startActivity(intent);
                                                } else {
                                                    Toast.makeText(register.this, "注册失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else
                                        Toast.makeText(register.this, "输入的两次密码不同！", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });


                }
            }
        });

        /*Bmob.initialize(this, "2f221c1babaeca6d42723047fba1289e");
        alldata p2 = new alldata();
        p2.setRoot("主内容:.:ID:.:0");
        p2.setSecondData("内容1:.:ID1:.:1:!:内容2:.:ID2:.:2:!:内容3:.:ID3:.:3");
        p2.setThirdData("内容1_1:.:ID4:.:1:!:内容1_2:.:ID5:.:1:!:内容1_3:.:ID6:.:1:!:内容2_1:.:ID7:.:2:!:内容2_2:.:ID8:.:2:!:内容2_3:.:ID9:.:2:!:内容3_1:.:ID10:.:3:!:内容3_2:.:ID11:.:3:!:内容3_3:.:ID12:.:3");
        p2.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    Toast.makeText(register.this, "添加数据成功，返回objectId为：" + objectId, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(register.this, "创建数据失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });*/
    }
}
