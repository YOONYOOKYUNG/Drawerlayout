package com.cookandroid.windowairfresh;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class WindowListAdapter extends BaseAdapter {
    public ArrayList<WindowDetails> listViewItemList = new ArrayList<>();

    public WindowListAdapter() {
    }

    private OnWindowButtonClickListener wListener;

    public void setListener(OnWindowButtonClickListener listener) {
        wListener = listener;
    }

    private DatabaseManager databaseManager;

    public void setDatabaseManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public int getCount() {
        return listViewItemList.size();
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
        TextView titleTextView = (TextView) convertView.findViewById(R.id.main_label);
        final TextView addressTextView = (TextView) convertView.findViewById(R.id.address);
        final ImageButton windowstate = (ImageButton) convertView.findViewById(R.id.windowstate);
        final ImageButton windowdelete = (ImageButton) convertView.findViewById(R.id.windowdelete);
        final FrameLayout windowbtnback = (FrameLayout) convertView.findViewById(R.id.windowbtnback);


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final WindowDetails listViewItem = listViewItemList.get(position);
        // 아이템 내 각 위젯에 데이터 반영
        titleTextView.setText(listViewItem.getName());
        addressTextView.setText(listViewItem.getAddress());
        boolean state = listViewItem.getState();
        if (state) {
            windowstate.setImageResource(R.drawable.window_open_wh);
            windowbtnback.setBackgroundResource(R.drawable.windowname_open);
            notifyDataSetChanged();
        } else if (!state) {
            windowstate.setImageResource(R.drawable.window_close_wh);
            windowbtnback.setBackgroundResource(R.drawable.windowname_close);
            notifyDataSetChanged();
        }
        //창문 열기|닫기 버튼 클릭 시 위젯 디자인 변경
        windowstate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wListener != null) {
                    wListener.onWindowButtonClick(pos);
                    boolean state = listViewItem.getState();
                    Log.d("상태", "현재 창문 상태 : " + listViewItem.getState());
                    if (state) {
                        windowstate.setImageResource(R.drawable.window_open_wh);
                        windowbtnback.setBackgroundResource(R.drawable.windowname_open);
                        notifyDataSetChanged();
                    } else if (!state) {
                        windowstate.setImageResource(R.drawable.window_close_wh);
                        windowbtnback.setBackgroundResource(R.drawable.windowname_close);
                        notifyDataSetChanged();
                    }
                }
            }
        });

        //그리드 뷰 x버튼 클릭 시 연결된 창문 삭제
        windowdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = (Activity) context;
                Intent intent = new Intent(activity, WindowDeleteActivity.class);
                Log.d("0000", String.valueOf(pos));
                intent.putExtra("position", pos);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.fadein, R.anim.fadeout);


            }
        });

        return convertView;
    }


    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    // 아이템 데이터 추가를 위한 함수
    public void addItem(String name, String address, Boolean state) {
        WindowDetails item = new WindowDetails();
        item.setName(name);
        item.setAddress(address);
        item.setState(state);
        listViewItemList.add(item);

        if (databaseManager != null) {
            ContentValues addRowValue = new ContentValues();

            addRowValue.put("name", name);
            addRowValue.put("address", address);
            addRowValue.put("state", state.toString());

            databaseManager.insert(addRowValue);
        }
    }

    public void initialiseList() {
        if (databaseManager != null) {
            listViewItemList = databaseManager.getAll();
        }
    }


    public interface OnWindowButtonClickListener {
        void onWindowButtonClick(int pos);
    }
}

