package com.cookandroid.windowairfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class WindowlistActivity extends AppCompatActivity {
    ImageButton btn1;
    ImageView backarrow;
    ToggleButton tbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_windowlist);

        final ListView listview;
        AutoSetActivity autotb = new AutoSetActivity();
        tbtn=autotb.getTbtn();

        btn1 = findViewById(R.id.btn1);
        final ListViewAdapter adapter;
        adapter = new ListViewAdapter();

        // 커스텀 다이얼로그에서 입력한 메시지를 출력할 TextView 를 준비한다.
        final TextView main_label = (TextView) findViewById(R.id.main_label);
        final Switch switch1 = findViewById(R.id.switch1);

        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);
        adapter.addItem("방1",true);

        listview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tbtn.isChecked()==true){
                    CustomDialog_popup4 customDialog_popup4 = new CustomDialog_popup4(WindowlistActivity.this);
                    customDialog_popup4.callFunction();
                }
            }
        });




        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog customDialog=new CustomDialog(WindowlistActivity.this);
                customDialog.setAdapter(adapter);
                customDialog.callFunction(main_label);
            }

        });


        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                ListViewItem item =(ListViewItem)adapter.getItem(0);
                Toast.makeText(getApplicationContext(),item.name+"삭제되었습니다.",Toast.LENGTH_LONG).show();
                adapter.removeitem(0); //0번째가 삭제되게 임의로 설정
                adapter.notifyDataSetChanged();
                return true;
            }
        });


        for(int i=0;i<adapter.getCount();i++){
            ListViewItem item=(ListViewItem)adapter.getItem(i);

            if (item.getCheck()){
                listview.setBackgroundColor(Color.parseColor("#ffffff"));
                adapter.notifyDataSetChanged();
            }
            else {
                listview.setBackgroundColor(Color.parseColor("#B7DBF4"));
                adapter.notifyDataSetChanged();
            }

        }

        backarrow = findViewById(R.id.backarrow);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
