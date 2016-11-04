package com.wangcong.dzl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by HASEE on 2016/10/19.
 */

public class testitem extends AppCompatActivity {
    private int loop = 0;//浏览当前未完成段子可能性搭配的页码
    EditText text;
    Button left;
    Button right;
    ArrayList<context> list;
    class context {//可接段子的各个能够续接的可能
        public String context;
        public context(String a, String b, String c) {
            context = a+"\n"+b+"\n"+c;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personcenter);
        ActivityCollector.activities.add(this);
        left = (Button) findViewById(R.id.left);
        right = (Button) findViewById(R.id.right);
        text = (EditText) findViewById(R.id.context);
        list=new ArrayList<context>();
        String secondData[] = test.current.getSecondText().split(":!:");
        ArrayList<aPiece> secondList = read.generateLevel(secondData);
        String thirdData[] = test.current.getThirdText().split(":!:");
        ArrayList<aPiece> thirdList = read.generateLevel(thirdData);
        for(int i=0;i<thirdList.size();i++)
        {
            for (int j=0;j<secondList.size();j++)
            {
                if(secondList.get(j).getNext()==thirdList.get(i).getNext())
                    list.add(new context(login.user+":"+test.current.getRootContext(),secondList.get(j).getUser()+":"+secondList.get(j).getContext(),thirdList.get(i).getUser()+":"+thirdList.get(i).getContext()));
            }
        }
        left.setOnClickListener(new View.OnClickListener() {//翻页功能的实现
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
    }
}
