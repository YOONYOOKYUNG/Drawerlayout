package com.cookandroid.windowairfresh;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TimelineAdapter extends BaseAdapter {
    public ArrayList<TimelineItem> timelineItemList = new ArrayList<>();
    public TimelineAdapter() { }

    private DatabaseManager databaseManager;

    public void setDatabaseManager(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
    }
    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴
    @Override
    public int getCount() {
        return timelineItemList.size();
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

        TextView txt_date = (TextView) convertView.findViewById(R.id.txt_date);
        TextView txt_window = (TextView) convertView.findViewById(R.id.txt_window);
        TextView txt_state = (TextView) convertView.findViewById(R.id.txt_state);
        TextView txt_cause = (TextView) convertView.findViewById(R.id.txt_cause);
        final ImageView item_openclose = (ImageView) convertView.findViewById(R.id.item_openclose);

        final TimelineItem timelineItem = timelineItemList.get(position);

        txt_date.setText(timelineItem.getDatetime());
        txt_window.setText(timelineItem.getWindow());
        txt_state.setText(timelineItem.getState());
        txt_cause.setText(timelineItem.getCause());

        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return timelineItemList;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public void addItem(String datetime, String window, String state, String cause) {
        TimelineItem item = new TimelineItem();
        item.setDatetime(datetime);
        item.setWindow(window);
        item.setState(state);
        item.setCause(cause);
        timelineItemList.add(item);

        if(databaseManager != null){
            ContentValues timeValues = new ContentValues();
            timeValues.put("Datetime", datetime);
            timeValues.put("Window", window);
            timeValues.put("State", state);
            timeValues.put("Cause", cause);

            databaseManager.timeline_insert(timeValues);
        }

    }

    //동기화
    public void renewItem() {
        if (databaseManager != null) {
            timelineItemList = databaseManager.timeline_select();
        }
    }
}
