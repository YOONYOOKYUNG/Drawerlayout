package com.cookandroid.windowairfresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TimelineAdapter extends BaseAdapter {
    public ArrayList<TimelineDetails> timelineDetailsList = new ArrayList<>();
    public TimelineAdapter() { }

    private DatabaseManager databaseManager;

    public void setDatabaseManager(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴
    @Override
    public int getCount() {
        return timelineDetailsList.size();
    }
    // "activity_timelineitem" Layout을 inflate하여 convertView 참조 획득.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_timelineitem, parent, false);
        }

        final ImageView item_openclose = (ImageView) convertView.findViewById(R.id.item_openclose);
        TextView txt_date = (TextView) convertView.findViewById(R.id.txt_date);
        TextView txt_time = (TextView) convertView.findViewById(R.id.txt_time);
        TextView txt_content = (TextView) convertView.findViewById(R.id.txt_content);



        final TimelineDetails timelineDetails = timelineDetailsList.get(position);
        // TextView에 띄우기
        txt_date.setText(timelineDetails.getDate());
        txt_time.setText(timelineDetails.getTime());
        txt_content.setText(timelineDetails.getContent());

        //창문 개폐 아이콘 변경
        if (timelineDetails.getState().equals("닫힘")){ //추후 아이콘 변경 예정
            item_openclose.setImageResource(R.drawable.timelineitem_close);
         } else if (timelineDetails.getState().equals("열림")){
            item_openclose.setImageResource(R.drawable.timelineitem_open);
        }

        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return timelineDetailsList;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    //동기화
    public void renewItem() {
        if (databaseManager != null) {
            timelineDetailsList = databaseManager.timeline_select();
        }
    }
}
