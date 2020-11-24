package com.cookandroid.windowairfresh;

import android.Manifest;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

public class BluetoothOnActivity extends AppCompatActivity {
    Button btnok, btnend;
    private ArrayList<BluetoothDevice> mDeviceList = new ArrayList<BluetoothDevice>(); //블루투스 주소를 여기에 저장
    private BluetoothAdapter mBluetoothAdapter; // 블루투스 어댑터

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //블루투스 사용을 위한 GPS permission 허용
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, 1);


        //커스텀 다이얼로그를 정의하기 위해 Dialog 클래스를 생성한다.
        final Dialog bluetoothDlg = new Dialog(this);
        //타이틀바 숨김
        bluetoothDlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        bluetoothDlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //레이아웃 설정
        bluetoothDlg.setContentView(R.layout.activity_bluetoothon);
        //노출
        bluetoothDlg.show();
        bluetoothDlg.setCancelable(false);
        setTitle("Bluetooth 연결");
        btnok = bluetoothDlg.findViewById(R.id.btnok);
        btnend = bluetoothDlg.findViewById(R.id.btnend);
        //블루투스 통신을 위해 블루투스 어댑터를 가져옴
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

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
                    Intent nextintent = new Intent(BluetoothOnActivity.this, AddressActivity.class);
                    startActivity(nextintent);
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
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
                bluetoothDlg.dismiss();
            }
        });
    }


}