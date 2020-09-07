package com.cookandroid.windowairfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ConnpopupActivity extends AppCompatActivity {
    Button btnok, btnend;
    BluetoothAdapter myBluetoothAdapter;
    Intent btEnableIntent;
    int REQUEST_ENABLE_BT = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connpopup);
        setTitle("Bluetooth 연결");

        btnok = findViewById(R.id.btnok);
        btnend = findViewById(R.id.btnend);

        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); // 블루투스 어댑터를 디폴트 어댑터로 설정

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myBluetoothAdapter == null) {
                    //블루투스가 지원이 되지 않는 기기 ->나중에 어플 종료로 이어짐
                    Toast.makeText(getApplicationContext(), "블루투스를 지원하지 않는 기기입니다.", Toast.LENGTH_SHORT).show();
                } else {
                    //블루투스 되는 기기이다.
                    //그렇다면 지금 현재 블루투스 기능이 켜져 있는지 체크 해야 한다.
                    if (!myBluetoothAdapter.isEnabled()) {
                        //false이면
                        //블루투스 꺼져있는상태 -> 간단한 인텐드 이용하여 블루투스 켬.
                        btEnableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(btEnableIntent, REQUEST_ENABLE_BT);
                        //위 결과값을 받아서 처리 한다.
                    }
                }

                Intent intent = new Intent(ConnpopupActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        btnend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTaskToBack(true);				// 태스크를 백그라운드로 이동
                finishAndRemoveTask();						// 액티비티 종료 + 태스크 리스트에서 지우기
                android.os.Process.killProcess(android.os.Process.myPid());
                finish();
            }
        });
    }
}
