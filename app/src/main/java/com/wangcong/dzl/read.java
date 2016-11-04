package com.wangcong.dzl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import static com.wangcong.dzl.login.user;

/**
 * Created by Wang Cong on 2016/9/30.
 */

class collector extends BmobObject {
    /**
     * id : a
     * collection : a
     */
    private String id;
    private String collection;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }
}

public class read extends AppCompatActivity {
    private static final String TAG = "read";
    static ArrayList<String> all = new ArrayList<>();
    private int loop = 0;
    EditText text;
    Button left;
    Button right, collect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read);
        ActivityCollector.activities.add(this);
        readContext();

        left = (Button) findViewById(R.id.left);
        right = (Button) findViewById(R.id.right);
        text = (EditText) findViewById(R.id.context);
        collect = (Button) findViewById(R.id.collect);

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loop == 0)
                    loop = all.size() - 1;
                else
                    loop = (loop - 1) % all.size();
                text.setText(all.get(loop));
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loop = (loop + 1) % all.size();
                text.setText(all.get(loop));
            }
        });

        Bmob.initialize(this, "2f221c1babaeca6d42723047fba1289e");
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobQuery<collector> query = new BmobQuery<collector>();
                query.addWhereEqualTo("id", user);
                query.setLimit(1);
                query.findObjects(new FindListener<collector>() {
                    @Override
                    public void done(List<collector> list, BmobException e) {
                        String temp = all.get(loop);
                        String user = login.user;
                        boolean iscollected = false;

                        if (list != null && list.size() != 0) {
                            String[] allcollection = list.get(0).getCollection().split(":.:");
                            for (String item : allcollection) {
                                if (item.equals(temp)) {
                                    iscollected = true;
                                    Toast.makeText(read.this, "你已收藏！", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }
                        }

                        if (iscollected == false) {
                            if (list != null && list.size() != 0) {
                                collector anew = new collector();
                                anew.setCollection(list.get(0).getCollection() + ":.:" + temp);
                                anew.update(list.get(0).getObjectId(), new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            Toast.makeText(read.this, "收藏成功！", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(read.this, "收藏失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                collector anew = new collector();
                                anew.setId(user);
                                anew.setCollection(temp);
                                anew.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String objectId, BmobException e) {
                                        if (e == null) {
                                            Toast.makeText(read.this, "收藏成功！", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(read.this, "收藏失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    }
                });
            }
        });

    }

    public void readContext() {
        try {
            //InputStream is = getAssets().open("user-1.txt");
            /*bmob账号：387816868@qq.com
            密码：ZWcZWc12345687*/
            Bmob.initialize(this, "2f221c1babaeca6d42723047fba1289e");
            BmobQuery<alldata> bmobQuery = new BmobQuery<alldata>();
            bmobQuery.findObjects(new FindListener<alldata>() {
                @Override
                public void done(List<alldata> object, BmobException e) {
                    if (e == null) {
                        //Toast.makeText(read.this, "查询成功", Toast.LENGTH_SHORT).show();
                        for (alldata item : object) {
                            ArrayList<String> list = new ArrayList<String>();
                            list.add(item.getRoot());
                            if (item.getSecondData() != "" && item.getThirdData() != "") {
                                list.add(item.getSecondData());
                                list.add(item.getThirdData());
                            } else
                                continue;
                            read.generateAll(list);
                        }
                        if (read.all.size() == 0) {
                            Toast.makeText(read.this, "目前没有已完结的段子哦。", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } else {
                        Toast.makeText(read.this, "查询失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });
            /*bmobQuery.getObject("8c3a5c5348", new QueryListener<alldata>() {
                @Override
                public void done(alldata object, BmobException e) {
                    if (e == null) {
                        Toast.makeText(read.this, "查询成功", Toast.LENGTH_SHORT).show();
                        ArrayList<String> list = new ArrayList<String>();
                        list.add(object.getRoot());
                        list.add(object.getSecondData());
                        list.add(object.getThirdData());
                        read.generateAll(list);
                    } else {
                        Toast.makeText(read.this, "查询失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateAll(ArrayList<String> list) {
        /*if (list.size() == 1) {
            String rootdata[] = list.get(0).split(":.:");
            aPiece root = new aPiece(rootdata[0], rootdata[1], Integer.parseInt(rootdata[2]));
            String temp = new String(root.getUser() + ":" + root.getContext() + "\n");
            all.add(temp);
        } else if (list.size() == 2) {
            String rootdata[] = list.get(0).split(":.:");
            aPiece root = new aPiece(rootdata[0], rootdata[1], Integer.parseInt(rootdata[2]));
            String secondData[] = list.get(1).split(":!:");
            ArrayList<aPiece> secondList = read.generateLevel(secondData);
            for (aPiece item : secondList) {
                String temp = new String(root.getUser() + ":" + root.getContext() + "\n");
                temp = temp + item.getUser() + ":" + item.getContext();
                all.add(temp);
            }*/
        //} else if (list.size() == 3) {
        String rootdata[] = list.get(0).split(":.:");
        aPiece root = new aPiece(rootdata[0], rootdata[1], Integer.parseInt(rootdata[2]));
        String secondData[] = list.get(1).split(":!:");
        ArrayList<aPiece> secondList = read.generateLevel(secondData);
        String thirdData[] = list.get(2).split(":!:");
        ArrayList<aPiece> thirdList = read.generateLevel(thirdData);
        allDZ(root, secondList, thirdList);
        //}
        //Log.d(TAG, "generateAll: excuted");
    }

    public static ArrayList<aPiece> generateLevel(String[] linedata) {
        int i;
        ArrayList<aPiece> linepieces = new ArrayList<aPiece>();
        for (i = 0; i < linedata.length; i++) {
            String tempData[] = linedata[i].split(":.:");
            aPiece temp = new aPiece(tempData[0], tempData[1], Integer.parseInt(tempData[2]));
            linepieces.add(temp);
        }
        return linepieces;
    }

    /*private ArrayList<String> readAllText(InputStream is) throws Exception {
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer buffer = new StringBuffer("");
        String str;
        ArrayList<String> list = new ArrayList<String>();
        while ((str = bufferedReader.readLine()) != null) {
            list.add(str);
        }
        return list;
    }*/

    public static void allDZ(aPiece root, ArrayList<aPiece> secondList, ArrayList<aPiece> thirdList) {
        for (aPiece item : secondList) {
            for (aPiece item1 : thirdList) {
                if (item.getNext() == item1.getNext()) {
                    StringBuffer temp = new StringBuffer("1." + root.getContext() + "\n");
                    temp.append("2." + item.getContext() + "\n");
                    temp.append("3." + item1.getContext());
                    read.all.add(temp.toString());
                }
            }
        }
        //Log.d(TAG, "allDZ: excuted");
    }

    protected void onPause() {
        super.onPause();
        read.all.clear();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        readContext();
    }
}
