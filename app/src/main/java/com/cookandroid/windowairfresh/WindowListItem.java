package com.cookandroid.windowairfresh;

public class WindowListItem {
    String name;
    Boolean check;

    public String getName() {
        return name;
    }
    public static Boolean getCheck() {
        return true;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setCheck(Boolean check){
        this.check = check;
    }
}