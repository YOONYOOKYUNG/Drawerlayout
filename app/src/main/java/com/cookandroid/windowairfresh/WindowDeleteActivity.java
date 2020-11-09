package com.cookandroid.windowairfresh;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;


public class WindowDeleteActivity extends Activity {
    WindowListAdapter adapter = new WindowListAdapter();
    DatabaseManager databaseManager = DatabaseManager.getInstance(MainActivity.mContext);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter.setDatabaseManager(databaseManager);
        adapter.initialiseList();
        //커스텀 다이얼로그를 정의하기 위해 Dialog 클래스를 생성한다.
        final Dialog inputnameDlg = new Dialog(this);
        //타이틀바 숨김
        inputnameDlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        inputnameDlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //레이아웃 설정
        inputnameDlg.setContentView(R.layout.activity_windowdelete);
        //노출
        inputnameDlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final Button okButton = (Button) inputnameDlg.findViewById(R.id.okButton);
        final Button cancelButton = (Button) inputnameDlg.findViewById(R.id.cancelButton);


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                int pos = intent.getIntExtra("position", 0);
                Log.d("유이", "창문" + pos);
                if (databaseManager != null) {
                    databaseManager.delete(adapter.listViewItemList.get(pos).getName());
                }
                adapter.listViewItemList.remove(pos);
                adapter.notifyDataSetChanged();
                inputnameDlg.dismiss();
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                finish();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }

        });

    }
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()== MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }
}