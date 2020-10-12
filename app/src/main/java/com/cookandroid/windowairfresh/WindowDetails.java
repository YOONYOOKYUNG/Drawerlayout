package com.cookandroid.windowairfresh;

public class WindowDetails {
    private String name, address;
    private Boolean state;

    public WindowDetails() { }

    //set
    public void setName(String name){
        this.name = name;
    }
    public void setAddress(String address){this.address=address;}
    public void setState(Boolean state){this.state=state;}

    //get
    public String getName() { return name; }
    public String getAddress(){return address;}
    public Boolean getState(){return state;}
}
