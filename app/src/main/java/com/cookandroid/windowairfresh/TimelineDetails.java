package com.cookandroid.windowairfresh;

public class TimelineDetails {
    //멤버 변수 지정
    private String date, time, content, state;

    public TimelineDetails() {
    }

    //외부에서 사용하기 위해 getter/setter 메소드 생성
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
