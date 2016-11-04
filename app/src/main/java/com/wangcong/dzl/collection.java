package com.wangcong.dzl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import static com.wangcong.dzl.login.user;

/**
 * Created by Wang Cong on 2016/10/15.
 */

public class collection extends AppCompatActivity {
    EditText cotext;
    Button coleft;
    Button coright, uncollect;
    static ArrayList<String> allcollection = new ArrayList<>();
    private int loop = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection);
        ActivityCollector.activities.add(this);

        coleft = (Button) findViewById(R.id.coleft);
        coright = (Button) findViewById(R.id.coright);
        cotext = (EditText) findViewById(R.id.cocontext);
        uncollect = (Button) findViewById(R.id.uncollect);
        getcollection();

        coleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allcollection.size() == 0) {
                    cotext.setText("你没有任何收藏的段子!快去收藏吧！✧٩(ˊωˋ*)و✧");
                    //Toast.makeText(collection.this, "你没有任何收藏的段子！", Toast.LENGTH_SHORT).show();
                } else {
                    if (loop == 0)
                        loop = allcollection.size() - 1;
                    else
                        loop = (loop - 1) % allcollection.size();
                    cotext.setText(allcollection.get(loop));
                }
            }
        });

        coright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allcollection.size() == 0) {
                    cotext.setText("你没有任何收藏的段子!快去收藏吧！✧٩(ˊωˋ*)و✧");
                    //Toast.makeText(collection.this, "你没有任何收藏的段子！", Toast.LENGTH_SHORT).show();
                } else {
                    loop = (loop + 1) % allcollection.size();
                    cotext.setText(allcollection.get(loop));
                }
            }
        });

        Bmob.initialize(this, "2f221c1babaeca6d42723047fba1289e");
        uncollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collection.allcollection.size() != 0) {
                    collection.allcollection.remove(loop);
                    BmobQuery<collector> query = new BmobQuery<collector>();
                    query.addWhereEqualTo("id", user);
                    query.setLimit(1);
                    query.findObjects(new FindListener<collector>() {
                        @Override
                        public void done(List<collector> list, BmobException e) {
                            if (e == null) {
                                if (list != null && list.size() != 0) {
                                    StringBuffer sb = new StringBuffer();
                                    if (collection.allcollection.size() != 0) {
                                        sb.append(collection.allcollection.get(0));
                                        for (int i = 1; i < collection.allcollection.size(); i++)
                                            sb.append(":.:" + collection.allcollection.get(i));
                                        collector anew = new collector();
                                        anew.setCollection(sb.toString());
                                        anew.update(list.get(0).getObjectId(), new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if (e == null) {
                                                    Toast.makeText(collection.this, "取消收藏成功！", Toast.LENGTH_SHORT).show();
                                                    if (collection.allcollection.size() != 0)
                                                        cotext.setText(allcollection.get(loop % collection.allcollection.size()));
                                                    else
                                                        cotext.setText("你没有任何收藏的段子!快去收藏吧！✧٩(ˊωˋ*)و✧");
                                                    //collection.allcollection.clear();
                                                    //getcollection();
                                                } else {
                                                    Toast.makeText(collection.this, "取消收藏失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(collection.this, main.class);
                                                    startActivity(intent);
                                                }
                                            }
                                        });
                                    } else {
                                        collector anew = new collector();
                                        anew.setObjectId(list.get(0).getObjectId());
                                        anew.delete(new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if (e == null) {
                                                    Toast.makeText(collection.this, "取消收藏成功！", Toast.LENGTH_SHORT).show();
                                                    if (collection.allcollection.size() != 0)
                                                        cotext.setText(allcollection.get(loop % collection.allcollection.size()));
                                                    else
                                                        cotext.setText("你没有任何收藏的段子!快去收藏吧！✧٩(ˊωˋ*)و✧");
                                                    //collection.allcollection.clear();
                                                    //getcollection();
                                                } else {
                                                    Toast.makeText(collection.this, "取消收藏失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(collection.this, main.class);
                                                    startActivity(intent);
                                                }
                                            }
                                        });
                                    }
                                } else {
                                    Toast.makeText(collection.this, "你没有任何收藏的段子!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(collection.this, "取消收藏失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(collection.this, main.class);
                                startActivity(intent);
                            }
                        }
                    });
                } else {
                    //Toast.makeText(collection.this, "你没有任何收藏的段子!", Toast.LENGTH_SHORT).show();
                    cotext.setText("你没有任何收藏的段子!快去收藏吧！✧٩(ˊωˋ*)و✧");
                }
            }
        });
    }

    public void getcollection() {
        Bmob.initialize(this, "2f221c1babaeca6d42723047fba1289e");
        BmobQuery<collector> query = new BmobQuery<collector>();
        query.addWhereEqualTo("id", user);
        query.setLimit(1);
        query.findObjects(new FindListener<collector>() {
            @Override
            public void done(List<collector> list, BmobException e) {
                if (e == null) {
                    if (list == null || list.size() == 0) {
                        Toast.makeText(collection.this, "你没有任何收藏的段子！", Toast.LENGTH_SHORT).show();
                    } else {
                        String temp[] = list.get(0).getCollection().split(":.:");
                        for (String item : temp)
                            collection.allcollection.add(item);
                        if (allcollection.size() != 0)
                            cotext.setText(allcollection.get(0));
                    }
                } else {
                    Toast.makeText(collection.this, "查询失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(collection.this, main.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        collection.allcollection.clear();
    }
}
