package com.wangcong.dzl;

/**
 * Created by Wang Cong on 2016/10/2.
 */

public class rootItem {


    private String rootContext;
    private String objectId;
    private String secondText;
    private String thirdText;

    public rootItem(String context, String id, String second, String third) {
        setRootContext(context);
        setObjectId(id);
        setSecondText(second);
        setThirdText(third);
    }

    public String getRootContext() {
        return rootContext;
    }

    public void setRootContext(String rootContext) {
        this.rootContext = rootContext;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getSecondText() {
        return secondText;
    }

    public void setSecondText(String secondText) {
        this.secondText = secondText;
    }

    public String getThirdText() {
        return thirdText;
    }

    public void setThirdText(String thirdText) {
        this.thirdText = thirdText;
    }
}
