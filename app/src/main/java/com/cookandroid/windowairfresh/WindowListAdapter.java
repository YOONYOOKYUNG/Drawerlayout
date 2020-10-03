package com.cookandroid.windowairfresh;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class WindowListAdapter extends BaseAdapter {
    public  ArrayList<WindowListAdapter> listViewItemList = new ArrayList<>() ;
    public WindowListAdapter() { }
    private OnWindowButtonClickListener wListener;
    public void setListener(OnWindowButtonClickListener listener) { wListener = listener; }
    String name, address;
    Boolean state;

    //set
    public void setName(String name){
        this.name = name;
    }
    public void setAddress(String address){this.address=address;}
    public void setState(Boolean state){this.state=state;}
    //get
    public String getName() {
        return name;
    }
    public String getAddress(){return address;}
    public Boolean getState(){return state;}

    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }
    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        // "activity_windowlistitem" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_windowlistitem, parent, false);
        }
        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView titleTextView = (TextView) convertView.findViewById(R.id.main_label) ;
        final TextView addressTextView = (TextView) convertView.findViewById(R.id.address);
        final ImageButton windowstate = (ImageButton) convertView.findViewById(R.id.windowstate);
        final ImageButton windowdelete = (ImageButton) convertView.findViewById(R.id.windowdelete);
        final FrameLayout windowbtnback = (FrameLayout) convertView.findViewById(R.id.windowbtnback);

        //리스트뷰 기본 배경색 지정
        //convertView.setBackgroundColor(Color.parseColor("#B7DBF4"));
        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final WindowListAdapter listViewItem = listViewItemList.get(position);
        // 아이템 내 각 위젯에 데이터 반영
        titleTextView.setText(listViewItem.getName());
        addressTextView.setText(listViewItem.getAddress());
        state=listViewItem.getState();



        windowstate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wListener != null) {
                    wListener.onWindowButtonClick(pos);
                    Log.d("상태", "현재 창문 상태 : " + listViewItem.getState());
                if (state==true){
                    windowstate.setImageResource(R.drawable.windowopen);
                    windowbtnback.setBackgroundColor(Color.parseColor("#B7DBF4"));
                    notifyDataSetChanged();
                }else if (state==false){
                    windowstate.setImageResource(R.drawable.windowclose);
                    windowbtnback.setBackgroundColor(Color.parseColor("#B9BDBF"));
                    windowstate.setBackgroundColor(Color.parseColor("#B9BDBF"));
                    windowdelete.setBackgroundColor(Color.parseColor("#B9BDBF"));
                    notifyDataSetChanged();
                  }
                }
            }
        });


        windowdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeitem(position);
                notifyDataSetChanged();
            }
        });
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
    public void addItem(String name, String address, Boolean state) {
        WindowListAdapter item = new WindowListAdapter();
        item.setName(name);
        item.setAddress(address);
        item.setState(state);
        listViewItemList.add(item);
    }



    public void removeitem(int position){
        listViewItemList.remove(position);
    }


    public ArrayList<WindowListAdapter> getListViewItemList(){return listViewItemList;}


    public interface OnWindowButtonClickListener{
        void onWindowButtonClick(int pos);
    }
}

