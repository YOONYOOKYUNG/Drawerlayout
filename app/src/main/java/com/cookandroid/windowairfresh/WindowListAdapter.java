package com.cookandroid.windowairfresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class WindowListAdapter extends BaseAdapter {
    public  ArrayList<WindowListItem> listViewItemList = new ArrayList<WindowListItem>() ;
    // ListViewAdapter의 생성자
    public WindowListAdapter() {
    }
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }
    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }
        else {

            // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
            TextView titleTextView = (TextView) convertView.findViewById(R.id.main_label) ;
            final Switch aSwitch = (Switch) convertView.findViewById(R.id.switch1);
            TextView blueTextView = (TextView) convertView.findViewById(R.id.blueaddress);

            //리스트뷰 기본 배경색 지정
            //convertView.setBackgroundColor(Color.parseColor("#B7DBF4"));
            // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
            final WindowListItem listViewItem = listViewItemList.get(position);

            // 아이템 내 각 위젯에 데이터 반영
            titleTextView.setText(listViewItem.getName());
            aSwitch.setChecked(false);
            blueTextView.setText(listViewItem.getBlueaddress());

            aSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listViewItemList.get(pos);
                }
            });


        }

        return convertView;
    }


    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String name, Boolean check,String blueaddress) {
        WindowListItem item = new WindowListItem();
        item.setName(name);
        item.setCheck(check);
        item.setBlueaddress(blueaddress);
        listViewItemList.add(item);
    }

    public void removeitem(int position){
        listViewItemList.remove(position);
    }

    public ArrayList<WindowListItem> getListViewItemList(){return listViewItemList;}

}

