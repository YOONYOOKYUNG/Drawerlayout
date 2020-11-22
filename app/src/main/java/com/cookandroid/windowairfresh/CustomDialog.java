package com.cookandroid.windowairfresh;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2017-08-07.
 */


public class CustomDialog extends DeviceListActivity {
    private Context context;
    private ListView listview;
    private WindowListAdapter adapter;

    public void setAdapter(WindowListAdapter _adapter) {
        adapter = _adapter;
    }

    public CustomDialog(Context context) {
        this.context = context;
    }


    public void callFunction(final TextView main_label) {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정
        dlg.setContentView(R.layout.custom_dialog);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의
        final EditText message = (EditText) dlg.findViewById(R.id.messagse);
        final Button okButton = (Button) dlg.findViewById(R.id.okButton);
        final Button cancelButton = (Button) dlg.findViewById(R.id.cancelButton);
        //변수에 입력된 값을 넣어줌
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String inputText =  message.getText().toString();


            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "취소 했습니다.", Toast.LENGTH_SHORT).show();
                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });
    }



}


