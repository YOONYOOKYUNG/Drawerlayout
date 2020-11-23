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
    //Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    public ArrayList<TimelineDetails> timelineDetailsList = new ArrayList<>();

    //TimelindAdapter 생성자
    public TimelineAdapter() {
    }

    private DatabaseManager databaseManager;

    public void setDatabaseManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴
    @Override
    public int getCount() {
        return timelineDetailsList.size();
    }

    //"activity_timelineitem" Layout을 inflate하여 convertView 참조 획득.
    //convertView : 해당 리스트 항목의 뷰
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_timelineitem, parent, false);
        }

        //// 화면에 표시될 View(Layout이 inflate된)\로부터 위젯에 대한 참조 획득
        final ImageView item_openclose = (ImageView) convertView.findViewById(R.id.item_openclose);
        TextView txt_date = (TextView) convertView.findViewById(R.id.txt_date);
        TextView txt_time = (TextView) convertView.findViewById(R.id.txt_time);
        TextView txt_content = (TextView) convertView.findViewById(R.id.txt_content);

        //TimelineDetails에서 position에 위치한 데이터 참조 획득
        final TimelineDetails timelineDetails = timelineDetailsList.get(position);

        // TextView에 띄우기
        txt_date.setText(timelineDetails.getDate());
        txt_time.setText(timelineDetails.getTime());
        txt_content.setText(timelineDetails.getContent());

        //창문 개폐 아이콘 변경
        if (timelineDetails.getState().equals("닫힘")) {
            //창문이 닫혔을 경우
            item_openclose.setImageResource(R.drawable.timelineitem_close);
        } else if (timelineDetails.getState().equals("열림")) {
            //창문이 열렸을 경우
            item_openclose.setImageResource(R.drawable.timelineitem_open);
        }

        return convertView;
    }

    //지정한 위치(position)에 있는 데이터 리턴
    @Override
    public Object getItem(int position) {
        return timelineDetailsList;
    }

    //지정한 위치(position)에 있는 데이터와 관련된 아이템들의 id 리턴
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
