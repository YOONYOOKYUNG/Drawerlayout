package com.cookandroid.windowairfresh;

public class TimelineItem {
    private String datetime, window, state, cause;

    public TimelineItem() { }

    public String getDatetime() { return this.datetime; }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getWindow() {
        return window;
    }

    public void setWindow(String window) {
        this.window = window;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
