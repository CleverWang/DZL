package com.wangcong.dzl;

import cn.bmob.v3.BmobObject;

/**
 * Created by Wang Cong on 2016/10/1.
 */

public class alldata extends BmobObject {
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
}
