package com.wangcong.dzl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Wang Cong on 2016/10/2.
 */

public class goOn extends AppCompatActivity {
    static rootItem current = new rootItem(login.user, "", "", "");
    static List<rootItem> allRootItems = new ArrayList<rootItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.go_on_list);
        ActivityCollector.activities.add(this);
        initRootItem();
    }

    private void initRootItem() {
        Bmob.initialize(this, "2f221c1babaeca6d42723047fba1289e");
        BmobQuery<alldata> bmobQuery = new BmobQuery<alldata>();
        bmobQuery.findObjects(new FindListener<alldata>() {
            @Override
            public void done(List<alldata> object, BmobException e) {
                if (e == null) {
                    //Toast.makeText(goOn.this, "查询成功", Toast.LENGTH_SHORT).show();
                    for (alldata item : object) {
                        ArrayList<String> list = new ArrayList<String>();
                        if (item.getSecondData().split(":!:").length == 3 && item.getThirdData().split(":!:").length == 9)
                            continue;
                        list.add(item.getRoot());
                        String rootdata[] = list.get(0).split(":.:");
                        aPiece root = new aPiece(rootdata[0], rootdata[1], Integer.parseInt(rootdata[2]));
                        String context = new String(root.getContext());
                        String objectid = new String(item.getObjectId());
                        rootItem temp = new rootItem(context, objectid, item.getSecondData(), item.getThirdData());
                        goOn.allRootItems.add(temp);
                    }
                    if (goOn.allRootItems.size() == 0) {
                        Toast.makeText(goOn.this, "目前没有段子可接哦。", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        rootItemAdapter adapter = new rootItemAdapter(goOn.this, R.layout.go_on_item, allRootItems);
                        ListView listView = (ListView) findViewById(R.id.list_view);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                rootItem item = allRootItems.get(position);
                                goOn.current = item;
                                //Toast.makeText(goOn.this, item.getRootContext(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(goOn.this, goOnItem.class);
                                startActivity(intent);
                            }
                        });
                    }
                } else {
                    Toast.makeText(goOn.this, "查询失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    protected void onPause() {
        super.onPause();
        goOn.allRootItems.clear();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initRootItem();
    }
}
