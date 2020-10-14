package com.cookandroid.windowairfresh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class BluetoothOnActivity extends AppCompatActivity {
    Button btnok, btnend;
    private ProgressDialog mProgressDlg; //로딩중 화면
    private ArrayList<BluetoothDevice> mDeviceList = new ArrayList<BluetoothDevice>(); //블루투스 주소를 여기에 저장
    private BluetoothAdapter mBluetoothAdapter; // 블루투스 어댑터

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        //GPS permission 허용
        setContentView(R.layout.activity_bluetoothon);
        setTitle("Bluetooth 연결");
        btnok = findViewById(R.id.btnok);
        btnend = findViewById(R.id.btnend);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //블루투스 통신을 위해 블루투스 어댑터를 가져옵니다

        btnok.setOnClickListener(new View.OnClickListener() {
                @Override
            public void onClick(View v) {
                if (mBluetoothAdapter != null) {
                    //블루투스 되는 기기이다.
                    //그렇다면 지금 현재 블루투스 기능이 켜져 있는지 체크 해야 한다.
                    if (!mBluetoothAdapter.isEnabled()) {
                        //false이면
                        //블루투스 꺼져있는상태 -> 간단한 인텐드 이용하여 블루투스 켬.
                        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivity(intent);
                    }
                    Intent nextintent = new Intent(BluetoothOnActivity.this,MainActivity.class);
                    startActivity(nextintent);
                }
            }
        });

        btnend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTaskToBack(true);                // 태스크를 백그라운드로 이동
                finishAndRemoveTask();                        // 액티비티 종료 + 태스크 리스트에서 지우기
                android.os.Process.killProcess(android.os.Process.myPid());
                finish();
            }
        });
    }


}