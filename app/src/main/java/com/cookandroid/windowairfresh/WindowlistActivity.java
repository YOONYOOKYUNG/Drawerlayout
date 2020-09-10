package com.cookandroid.windowairfresh;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class WindowlistActivity extends AppCompatActivity {

    static final int REQUEST_ENABLE_BT = 10; // 블루투스 활성화 상태
    BluetoothAdapter bluetoothAdapter; // 블루투스 어댑터
    Set<BluetoothDevice> devices; // 블루투스 디바이스 데이터 셋
    BluetoothDevice bluetoothDevice; // 블루투스 디바이스
    BluetoothSocket bluetoothSocket = null; // 블루투스 소켓


    OutputStream outputStream = null; // 블루투스에 데이터를 출력하기 위한 출력 스트림
    InputStream inputStream = null; // 블루투스에 데이터를 입력하기 위한 입력 스트림

    Thread workerThread = null; // 문자열 수신에 사용되는 쓰레드
    byte[] readBuffer; // 수신 된 문자열을 저장하기 위한 버퍼
    int readBufferPosition; // 버퍼 내 문자 저장 위치

    ImageButton btn1;
    ImageView backarrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_windowlist);

        final ListView listview;

        btn1 = findViewById(R.id.btn1);
        final ListViewAdapter adapter;
        adapter = new ListViewAdapter();

        // 커스텀 다이얼로그에서 입력한 메시지를 출력할 TextView 를 준비한다.
        final TextView main_label = (TextView) findViewById(R.id.main_label);


        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog customDialog=new CustomDialog(WindowlistActivity.this);
                customDialog.setAdapter(adapter);
                customDialog.callFunction(main_label);
            }

        });

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





    //블루투스
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ENABLE_BT :
                if(requestCode == RESULT_OK) { // '사용'을 눌렀을 때
                    selectBluetoothDevice(); // 블루투스 디바이스 선택 함수 호출
                }
                else { // '취소'를 눌렀을 때
                    finish();
                }
                break;
        }
    }
    void checkBluetooth(){
        // 블루투스 활성화하기
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); // 블루투스 어댑터를 디폴트 어댑터로 설정
        if (bluetoothAdapter == null) { // 디바이스가 블루투스를 지원하지 않을 때
            finish();
        } else { // 디바이스가 블루투스를 지원 할 때
            if (bluetoothAdapter.isEnabled()) { // 블루투스가 활성화 상태 (기기에 블루투스가 켜져있음)
                selectBluetoothDevice(); // 블루투스 디바이스 선택 함수 호출
            } else { // 블루투스가 비 활성화 상태 (기기에 블루투스가 꺼져있음)
                // 블루투스를 활성화 하기 위한 다이얼로그 출력
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                // 선택한 값이 onActivityResult 함수에서 콜백된다.
                startActivityForResult(intent, REQUEST_ENABLE_BT);
            }
        }
    }
    void selectBluetoothDevice() {
        // 이미 페어링 되어있는 블루투스 기기를 찾습니다.
        devices = bluetoothAdapter.getBondedDevices();
        // 페어링 된 디바이스의 크기를 저장
        int pariedDeviceCount = devices.size();
        // 페어링 되어있는 장치가 없는 경우
        if(pariedDeviceCount == 0) {
            finish();// 페어링 장치가 없는 경우
        }
        // 페어링 되어있는 장치가 있는 경우
        else {
            // 디바이스를 선택하기 위한 다이얼로그 생성
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("페어링 되어있는 블루투스 디바이스 목록");
            // 페어링 된 각각의 디바이스의 이름과 주소를 저장
            List<String> list = new ArrayList<>();
            // 모든 디바이스의 이름을 리스트에 추가
            for(BluetoothDevice bluetoothDevice : devices) {
                list.add(bluetoothDevice.getName());
            }
            list.add("취소");

            // List를 CharSequence 배열로 변경
            final CharSequence[] charSequences = list.toArray(new CharSequence[list.size()]);
            list.toArray(new CharSequence[list.size()]);
            // 해당 아이템을 눌렀을 때 호출 되는 이벤트 리스너
            builder.setItems(charSequences, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // 해당 디바이스와 연결하는 함수 호출
                    connectDevice(charSequences[which].toString());
                }
            });

            // 뒤로가기 버튼 누를 때 창이 안닫히도록 설정
            builder.setCancelable(false);
            // 다이얼로그 생성
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
    BluetoothDevice getDeviceFromBondedList(String name){
        BluetoothDevice selectedDevice = null;
        for (BluetoothDevice tempDevice : devices) {
            // 사용자가 선택한 이름과 같은 디바이스로 설정하고 반복문 종료
            if (name.equals(tempDevice.getName())) {
                selectedDevice = tempDevice;
                break;
            }
        }
        return selectedDevice;
    }

    void connectDevice(String deviceName) {
        bluetoothDevice = getDeviceFromBondedList(deviceName);
        // UUID 생성
        UUID uuid = java.util.UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
        // Rfcomm 채널을 통해 블루투스 디바이스와 통신하는 소켓 생성
        try {
            bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
            bluetoothSocket.connect();
            // 데이터 송,수신 스트림을 얻어옵니다.
            outputStream = bluetoothSocket.getOutputStream();
            inputStream = bluetoothSocket.getInputStream();


            // 데이터 수신 함수 호출
            final Handler handler = new Handler();

            // 데이터를 수신하기 위한 버퍼를 생성
            readBufferPosition = 0;
            readBuffer = new byte[1024];

            // 데이터를 수신하기 위한 쓰레드 생성
            workerThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(Thread.currentThread().isInterrupted()) {
                        try {
                            // 데이터를 수신했는지 확인합니다.
                            int byteAvailable = inputStream.available();
                            // 데이터가 수신 된 경우
                            if(byteAvailable > 0) {
                                // 입력 스트림에서 바이트 단위로 읽어 옵니다.
                                byte[] bytes = new byte[byteAvailable];
                                inputStream.read(bytes);
                                // 입력 트림 바이트를 한 바이트씩 읽어 옵니다.
                                for(int i = 0; i < byteAvailable; i++) {
                                    byte tempByte = bytes[i];
                                    // 개행문자를 기준으로 받음(한줄)
                                    if(tempByte == '\n') {
                                        // readBuffer 배열을 encodedBytes로 복사
                                        byte[] encodedBytes = new byte[readBufferPosition];
                                        System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                        // 인코딩 된 바이트 배열을 문자열로 변환
                                        final String text = new String(encodedBytes, "UTF-8");
                                        readBufferPosition = 0;
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                // 텍스트 뷰에 출력

                                            }
                                        });
                                    } // 개행 문자가 아닐 경우
                                    else {
                                        readBuffer[readBufferPosition++] = tempByte;
                                    }
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            workerThread.start();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void onDestroy() {
        try{
            workerThread.interrupt();
            inputStream.close();
            outputStream.close();
            bluetoothSocket.close();


        }catch (Exception e){ }
        super.onDestroy();
    }





}
