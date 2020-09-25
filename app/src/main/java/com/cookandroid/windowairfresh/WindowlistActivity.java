package com.cookandroid.windowairfresh;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class WindowlistActivity extends AppCompatActivity {

    private static final String TAG = "bluetooth2";

    final int RECIEVE_MESSAGE = 1;        // Status  for Handler
    private BluetoothSocket btSocket = null;
    private StringBuilder sb = new StringBuilder();

    private ConnectedThread ConnectedThread;

    // SPP UUID service
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    // MAC-address of Bluetooth module (you must edit this
    private static String address = "98:D3:51:F9:28:05";

    private ProgressDialog mProgressDlg; //로딩중 화면
    private ArrayList<BluetoothDevice> mDeviceList = new ArrayList<BluetoothDevice>(); //블루투스 주소를 여기에 저장
    private BluetoothAdapter mBluetoothAdapter; // 블루투스 어댑터

    ImageButton btn1;
    ImageView backarrow;
    ListView listview;

    WindowListAdapter adapter;
    TextView main_label;
    String sfName = "File";
    String state="";
    Switch switch1;


    //dhkim start ==============================================
    final int REQUESTCODE_DEVICELISTACTIVITY = 1111;

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == RESULT_OK && requestCode==REQUESTCODE_DEVICELISTACTIVITY) {
            String addedWindowName =  data.getStringExtra("new_window_name");
            String addedbtaddress =  data.getStringExtra("btaddress");
            Log.d("테스트", "WindowList로 창문이름 도착 : " + addedWindowName);
            Log.d("dhkim", "WindowList로 주소 도착 : " + addedbtaddress);
            if(adapter==null) {
                Log.d("dhkim", "adapter is null ");
            } else {
                adapter.addItem(addedWindowName, true,addedbtaddress); //세번째 블루투스어드레스는 주소값을 넣어주면됨
                adapter.notifyDataSetChanged();
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    //dhkim end ==============================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_windowlist);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        //GPS permission 허용


        btn1 = findViewById(R.id.btn1);
        adapter = new WindowListAdapter();

        // 커스텀 다이얼로그에서 입력한 메시지를 출력할 TextView 를 준비한다.
        main_label = (TextView) findViewById(R.id.main_label);
        //final Switch switch1 = findViewById(R.id.switch1);

        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);
        adapter.addItem("거실 창문1",false,"blueaddress"); ////세번째 블루투스어드레스는 주소값을 넣어주면됨
        SharedPreferences sf = getSharedPreferences(sfName, 0);
        state = sf.getString("state", ""); // 키값으로
        if (state=="auto"){
            listview.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    Intent intent = new Intent(WindowlistActivity.this, Popup_warning.class);
                    startActivity(intent);
                    return false;
                }
            });
        }

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

        btn1.setOnClickListener(new View.OnClickListener() {
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
                mBluetoothAdapter.startDiscovery();
            }
        });


        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                WindowListItem item =(WindowListItem)adapter.getItem(0);
                Toast.makeText(getApplicationContext(),item.name+"삭제되었습니다.",Toast.LENGTH_LONG).show();
                adapter.removeitem(0); //0번째가 삭제되게 임의로 설정
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        switch1 = findViewById(R.id.switch1);
        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WindowListItem item=(WindowListItem)adapter.getItem(0);
                address = item.getBlueaddress();


                if(item.getCheck()==true){
                    ConnectedThread.write("2");
                }
                else if(item.getCheck()==false){
                    ConnectedThread.write("3");
                }
                else{
                    Log.d("aa","오류");
                }

            }
        });




        for(int i=0;i<adapter.getCount();i++){
            WindowListItem item=(WindowListItem)adapter.getItem(i);

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
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            //GPS permission 허용
            if (mBluetoothAdapter.isDiscovering()) {
                //블루투스 기기를 발견했다면
                mBluetoothAdapter.cancelDiscovery();
                //블루투스 검색 취소
            }
        }
        try     {
            btSocket.close();
        } catch (IOException e2) {
            errorExit("Fatal Error", "In onPause() and failed to close socket." + e2.getMessage() + ".");
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
                Intent newIntent = new Intent(WindowlistActivity.this, DeviceListActivity.class);
                newIntent.putParcelableArrayListExtra("device.list", mDeviceList);
                Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
                ArrayList<BluetoothDevice> list = new ArrayList<BluetoothDevice>();
                list.addAll(pairedDevices);
                //연결된 디바이스 목록을 리스트에 모두 추가합니다.
                newIntent.putParcelableArrayListExtra("device.list2", list);
                //추가된 값 저장하기
                startActivityForResult(newIntent, REQUESTCODE_DEVICELISTACTIVITY);
                return;
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //블루투스 디바이스가 검색되었을 때(디바이스 검색 결과)
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //추가된 값 받아오기
                mDeviceList.add(device);

            }
        }
    };

    @Override
    public void onBackPressed(){
        finish();
        super.onBackPressed();
    }



    //블루투스
    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        if(Build.VERSION.SDK_INT >= 10){
            try {
                final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[] { UUID.class });
                return (BluetoothSocket) m.invoke(device, MY_UUID);
            } catch (Exception e) {
                Log.e(TAG, "Could not create Insecure RFComm Connection",e);
            }
        }
        return  device.createRfcommSocketToServiceRecord(MY_UUID);
    }

    private void errorExit(String title, String message){
        Toast.makeText(getBaseContext(), title + " - " + message, Toast.LENGTH_LONG).show();
        finish();
    }

    private class ConnectedThread extends Thread {
        private final java.io.InputStream InputStream;
        private final java.io.OutputStream OutputStream;

        public ConnectedThread(BluetoothSocket socket) {
            java.io.InputStream tmpIn = null;
            java.io.OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            InputStream = tmpIn;
            OutputStream = tmpOut;
        }
        public void run() {
            byte[] buffer = new byte[256];  // buffer store for the stream
            int bytes; // bytes returned from read()

        }
        public void write(String message) {
            Log.d(TAG, "...Data to send: " + message + "...");
            byte[] msgBuffer = message.getBytes();
            try {
                OutputStream.write(msgBuffer);
            } catch (IOException e) {
                Log.d(TAG, "...Error data send: " + e.getMessage() + "...");
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            errorExit("Fatal Error", "In onResume() and socket create failed: " + e.getMessage() + ".");
        }
        mBluetoothAdapter.cancelDiscovery();
        try {
            btSocket.connect();

        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {
                errorExit("Fatal Error", "In onResume() and unable to close socket during connection failure" + e2.getMessage() + ".");
            }
        }
        ConnectedThread = new ConnectedThread(btSocket);
        ConnectedThread.start();

    }



}
