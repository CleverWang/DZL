package com.wangcong.dzl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Wang Cong on 2016/10/2.
 */

public class goOnItem extends AppCompatActivity {
    private static final String TAG = "read";
    private int loop = 0;
    EditText text, tt;
    Button left;
    Button right, submit;
    ArrayList<possibility> list;
    String s;
    int sec = 0;

    class possibility {
        public String context;
        public int i, t;

        public possibility(String a, int b, int l) {
            context = a;
            i = b;
            t = l;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goonitem);
        ActivityCollector.activities.add(this);
        left = (Button) findViewById(R.id.left);
        right = (Button) findViewById(R.id.right);
        text = (EditText) findViewById(R.id.context);
        list = new ArrayList<possibility>();
        if (goOn.current.getSecondText() != "") {
            String secondData[] = goOn.current.getSecondText().split(":!:");
            ArrayList<aPiece> secondList = read.generateLevel(secondData);
            sec = secondList.size();
            if (goOn.current.getThirdText() != "") {
                String thirdData[] = goOn.current.getThirdText().split(":!:");
                ArrayList<aPiece> thirdList = read.generateLevel(thirdData);

                if (secondList.size() < 3) {
                    text.setText(goOn.current.getRootContext());
                    list.add(new possibility(goOn.current.getRootContext(), 1, -1));
                }
                int a = 0, b = 0, c = 0;
                for (int i = 0; i < thirdList.size(); i++) {
                    if (thirdList.get(i).getNext() == 1)
                        a++;
                    if (thirdList.get(i).getNext() == 2)
                        b++;
                    if (thirdList.get(i).getNext() == 3)
                        c++;
                }
                if (a < 3 && secondList.size() >= 1)
                    list.add(new possibility(goOn.current.getRootContext() + "\n" + secondList.get(0).getContext(), 2, 1));
                if (b < 3 && secondList.size() >= 2)
                    list.add(new possibility(goOn.current.getRootContext() + "\n" + secondList.get(1).getContext(), 2, 2));
                if (c < 3 && secondList.size() >= 3)
                    list.add(new possibility(goOn.current.getRootContext() + "\n" + secondList.get(2).getContext(), 2, 3));

            } else {
                if (secondList.size() < 3) {
                    text.setText(goOn.current.getRootContext());
                    list.add(new possibility(goOn.current.getRootContext(), 1, -1));
                }
                for (int y = 0; y < secondList.size(); y++)
                    list.add(new possibility(goOn.current.getRootContext() + "\n" + secondList.get(y).getContext(), 2, y + 1));
            }
        } else {
            text.setText(goOn.current.getRootContext());
            list.add(new possibility(goOn.current.getRootContext(), 1, -1));
        }
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loop == 0)
                    loop = list.size() - 1;
                else
                    loop = (loop - 1) % list.size();
                text.setText(list.get(loop).context);
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loop = (loop + 1) % list.size();
                text.setText(list.get(loop).context);
            }
        });
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tt = (EditText) findViewById(R.id.editText2);
                s = tt.getText().toString().trim();
                if (s.length()==0)
                    Toast.makeText(goOnItem.this, "续接内容不能为空！", Toast.LENGTH_SHORT).show();
                else {
                    alldata p2 = new alldata();
                    if (list.get(loop).i == 1 && goOn.current.getSecondText() != "") {
                        p2.setSecondData(goOn.current.getSecondText() + ":!:" + s + ":.:" + login.user + ":.:" + (sec + 1));
                    } else if (list.get(loop).i == 1)
                        p2.setSecondData(s + ":.:" + login.user + ":.:" + (sec + 1));
                    if (list.get(loop).i == 2 && goOn.current.getThirdText() != "") {
                        p2.setThirdData(goOn.current.getThirdText() + ":!:" + s + ":.:" + login.user + ":.:" + list.get(loop).t);
                    } else if (list.get(loop).i == 2)
                        p2.setThirdData(s + ":.:" + login.user + ":.:" + list.get(loop).t);
                    p2.update(goOn.current.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                text.setText(text.getText()+"\n"+s);
                                Toast.makeText(goOnItem.this, "添加成功！", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(goOnItem.this, goOn.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(goOnItem.this, "添加失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

    }


}
