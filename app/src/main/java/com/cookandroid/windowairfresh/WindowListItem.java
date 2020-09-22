package com.cookandroid.windowairfresh;

public class WindowListItem {
    String name, blueaddress;
    Boolean check;

    public String getName() {
        return name;
    }
    public String getBlueaddress(){return blueaddress;}
    public static Boolean getCheck() {
        return true;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setBlueaddress(String blueaddress){this.blueaddress=blueaddress;}
    public void setCheck(Boolean check){
        this.check = check;
    }
}