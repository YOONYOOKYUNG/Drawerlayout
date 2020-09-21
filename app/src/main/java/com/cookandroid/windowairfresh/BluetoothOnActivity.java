package com.cookandroid.windowairfresh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import java.util.Set;

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
        mProgressDlg = new ProgressDialog(this);
        mProgressDlg.setMessage("검색중...");
        mProgressDlg.setCancelable(false);
        //뒤로가기 버튼을 눌러도 false임으로 뒤로가지지 않습니다.
        mProgressDlg.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                mBluetoothAdapter.cancelDiscovery();
                //Cancel 버튼을 누르면 블루투스 찾기가 종료됩니다.
            }
        });
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
                                startActivityForResult(intent, 1000);
                                }
                        }

                        mBluetoothAdapter.startDiscovery(); }
                });

                btnend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        moveTaskToBack(true);                // 태스크를 백그라운드로 이동
                        finishAndRemoveTask();                        // 액티비티 종료 + 태스크 리스트에서 지우기
                        android.os.Process.killProcess(android.os.Process.myPid());
                        finish(); }});

                IntentFilter filter = new IntentFilter();
                //IntentFilter란 다음 작업이 명시되지 않은 상태에서 보내진 intent에 대해
                //어느 activity/sevice/broadcast가 받을 것인가를 찾는 Intent Resolution시
                //참조하는 정보이다

                filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
                //디바이스 연결상태 변경
                filter.addAction(BluetoothDevice.ACTION_FOUND);
                //블루투스 디바이스가 검색되었을 때(디바이스 검색 결과)
                filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
                //블루투스 디바이스 검색 시작
                filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
                //블루투스 디바이스 검색이 끝났을 때
                registerReceiver(mReceiver, filter);
                //receiver를 등록한다
            }

    @Override
    public void onPause() {
        if (mBluetoothAdapter != null) {
            //장치가 블루투스를 지원하는 경우
            if (mBluetoothAdapter.isDiscovering()) {
                //블루투스 기기를 발견했다면
                mBluetoothAdapter.cancelDiscovery();
                //블루투스 검색 취소
            }
        }

        super.onPause();
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        //블루투스 연결을 끊거나 앱 종료 시 반드시 리시버를 해지 해야 한다.
        super.onDestroy();
    }


    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //각 action에 따른 반응
            if  (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                //블루투스 디바이스 검색 시작
                mDeviceList = new ArrayList<BluetoothDevice>();
                //블루투스 기기 목록 갱신
                mProgressDlg.show();
                //로딩중화면 표시
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //블루투스 디바이스 검색이 끝났을 때
                mProgressDlg.dismiss();
                //로딩중 화면 사라짐
                Intent newIntent = new Intent(BluetoothOnActivity.this, DeviceListActivity.class);
                newIntent.putParcelableArrayListExtra("device.list", mDeviceList);
                Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
                ArrayList<BluetoothDevice> list = new ArrayList<BluetoothDevice>();
                list.addAll(pairedDevices);
                //연결된 디바이스 목록을 리스트에 모두 추가합니다.
                newIntent.putParcelableArrayListExtra("device.list2", list);
                //추가된 값 저장하기
                startActivity(newIntent);
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //블루투스 디바이스가 검색되었을 때(디바이스 검색 결과)
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //추가된 값 받아오기
                mDeviceList.add(device);

            }
        }
    };
}