package com.wangcong.dzl;

/**
 * Created by Wang Cong on 2016/9/30.
 */

public class aPiece {

    private String context;
    private String user;
    private int next;

    public aPiece(String context, String user, int next) {
        this.context = context;
        this.user = user;
        this.next = next;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }
}
