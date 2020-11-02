package com.cookandroid.windowairfresh;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import me.relex.circleindicator.CircleIndicator3;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MainActivity_Fragment1.AutoWindowListener{


    //블루투스 관련 선언 시작(블투1)
    private static final String TAG = "bluetooth2";
    final int RECIEVE_MESSAGE = 1;        // Status  for Handler
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder sb = new StringBuilder();
    private static int flag = 0;
    private ConnectedThread ConnectedThread;
    public static Context mContext;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    //블루투스 하드코딩 유리:"90:D3:51:F9:26:E0" / 경원 "98:D3:51:F9:28:05"
    private static String address = "98:D3:51:F9:28:05";
    public ArrayList<WindowDetails> checklist = new ArrayList<>() ;
    String[] array;
    //블루투스 관련 선언 종료(블투1)

    private DatabaseManager databaseManager;
    ViewPager2 viewpager;
    CircleIndicator3 indicator;
    WindowListAdapter adapter;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Handler handler;
    SwipeRefreshLayout swipeRefreshLayout;
    FragmentStateAdapter slideadapter;
    float insidedust;
    float outsidedust;
    float outsidetemp;
    int outsiderain;
    static boolean btsocketstate=false;
    Handler autohandler;

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof MainActivity_Fragment1) {
            MainActivity_Fragment1 mainFragment1 = (MainActivity_Fragment1) fragment;
            mainFragment1.setListener(this);
        }
    }

    public void onAutoWindowSet(String temp, String dust, String rain) {
        outsidetemp=Float.parseFloat(temp);
        outsidedust=Float.parseFloat(dust);
        outsiderain=Integer.parseInt(rain);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseManager = DatabaseManager.getInstance(this);
        adapter = new WindowListAdapter();
        adapter.setDatabaseManager(databaseManager);
        adapter.initialiseList();
        mContext = this;

        //도움말
        ImageView question2;

        //fragment 관련
        viewpager = findViewById(R.id.viewpager);
        slideadapter = new Main_SlideAdapter(this, databaseManager);
        viewpager.setAdapter(slideadapter);
        indicator = findViewById(R.id.indicator);
        indicator.setViewPager(viewpager);


        //(블투2)
        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();
        //(블투2)

        //hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        //toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //navigation drawer menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        mContext=this;



        //자동시작
        autohandler  = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                Popup_automode popup = new Popup_automode(mContext);
                popup.setnumber(message.what);
                popup.callautomodepopup();
            }
        };

        Intent intent= new Intent(this,AutoService.class);
        startService(intent);
    }

    //menu
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {
        switch (menuitem.getItemId()) {
            case R.id.window:
                startActivity(new Intent(MainActivity.this, WindowlistActivity.class));
                drawerLayout.closeDrawers();
                break;

            case R.id.auto_set:
                Intent intent1 = new Intent(MainActivity.this, ModeSetActivity.class);
                startActivity(intent1);
                drawerLayout.closeDrawers();
                break;

            case R.id.alarm:
                Intent intent2 = new Intent(MainActivity.this, AlarmActivity.class);
                startActivity(intent2);
                drawerLayout.closeDrawers();
                break;

            case R.id.log_record:
                Intent intent3 = new Intent(MainActivity.this, TimelineActivity.class);
                startActivity(intent3);
                drawerLayout.closeDrawers();
                break;

            case R.id.contact:
                Intent intent4 = new Intent(MainActivity.this,InformationActivity.class);
                startActivity(intent4);
                drawerLayout.closeDrawers();
                break;


        }
        return true;
    }

    //(블투3)
    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
            try {
                final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[] { UUID.class });
                return (BluetoothSocket) m.invoke(device, MY_UUID);
            } catch (Exception e) {
                Log.e(TAG, "Could not create Insecure RFComm Connection",e);
            }

        return  device.createRfcommSocketToServiceRecord(MY_UUID);
    }
    private void checkBTState() {
        // Check for Bluetooth support and then check to make sure it is turned on
        // Emulator doesn't support Bluetooth and will return null
        if(btAdapter==null) {
            errorExit("Fatal Error", "Bluetooth not support");
        } else {
            if (btAdapter.isEnabled()) {
                Log.d(TAG, "...Bluetooth ON...");
            } else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }


    // fragment2 아두이노 측정값 송수신
    class ConnectedThread extends Thread {
        private final java.io.InputStream InputStream;
        private final java.io.OutputStream OutputStream;

        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

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

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    Thread.sleep(1000);
                    bytes = InputStream.read(buffer);        // Get number of bytes and message in "buffer"
                    handler.obtainMessage(RECIEVE_MESSAGE, bytes, -1, buffer).sendToTarget();     // Send to message queue Handler
                } catch (IOException | InterruptedException e) {
                    break;
                }
            }
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

public void makesocket(){
        BluetoothDevice device = btAdapter.getRemoteDevice(adapter.listViewItemList.get(0).getAddress());
        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            errorExit("Fatal Error", "In onResume() and socket create failed: " + e.getMessage() + ".");
        }
    }

public void opensocket(){
    makesocket();
    btAdapter.cancelDiscovery();
    try {
        btSocket.connect();
        btsocketstate=true;
    } catch (IOException e) {
        try {
            btSocket.close();
            btsocketstate=false;
        } catch (IOException e2) {
            errorExit("Fatal Error", "In onResume() and unable to close socket during connection failure" + e2.getMessage() + ".");
        }
    }
    ConnectedThread = new ConnectedThread(btSocket);
    ConnectedThread.start();
}


    @Override
    public void onResume() {
        super.onResume();
        if (databaseManager != null){
            checklist = databaseManager.getAll();
        }
        if (checklist.isEmpty()) {
            //블루투스 하드코딩 유리:"90:D3:51:F9:26:E0" / 경원 "98:D3:51:F9:28:05"
            address = "98:D3:51:F9:28:05";
        } else {
            WindowDetails listViewItem = adapter.listViewItemList.get(0);
            address = listViewItem.getAddress();
        }
        onRefresh();
        arduinoRefresh();
            //handler.postDelayed(new Handler(),1000)
    }

    public void arduinoRefresh(){
        if(!checklist.isEmpty()){
            if(!btsocketstate){opensocket();}
            ConnectedThread.write("1");
        }
        handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case RECIEVE_MESSAGE:
                        byte[] readBuf = (byte[]) msg.obj;

                        String strIncom = new String(readBuf, 0, msg.arg1);
                        Log.d("a2", strIncom);
                        sb.append(strIncom);
                        Log.d("a3", String.valueOf(sb));

                        int endOfLineIndex = sb.indexOf("\r\n");
                        Log.d("a4", String.valueOf(endOfLineIndex));
                        if (endOfLineIndex > 0) {
                            String sbprint = sb.substring(0, endOfLineIndex);
                            Log.d("a5", sbprint);
                            sb.delete(0, sb.length());

                            array = sbprint.split("#");

                            Log.d("a6", array[0]);
                            Log.d("a6", array[1]);
                            Log.d("a6", array[2]);
                            Log.d("a6", array[3]);

                            SharedPreferences pf = getSharedPreferences("fragment2", MODE_PRIVATE);
                            SharedPreferences.Editor editor =pf.edit();
                            editor.putString("temp", array[1]);
                            editor.putString("dust", array[2]);
                            insidedust = Float.parseFloat(pf.getString("dust","0"));
                            editor.putString("humid", array[3]);
                            editor.commit();

                            flag++;
                        }

                        break;
                }
            }
        };
    }

    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (viewpager.getCurrentItem() == 0) {
                super.onBackPressed();}
            else{
                // Otherwise, select the previous step.
                viewpager.setCurrentItem(viewpager.getCurrentItem() - 1);
            }
        }
    }


    private void errorExit(String title, String message){
        Toast.makeText(getBaseContext(), title + " - " + message, Toast.LENGTH_LONG).show();
        finish();
    }

    //창문설정 - 열기
    public void openwindow(int pos){
            adapter.initialiseList();
            WindowDetails listViewItem = adapter.listViewItemList.get(pos);
            address=listViewItem.getAddress();
           if(!listViewItem.getState()){
               if (!btsocketstate)
               { opensocket();}
            ConnectedThread.write("2");}
    }
    //창문설정 - 닫기
    public void closewindow(int pos){
            adapter.initialiseList();
            WindowDetails listViewItem = adapter.listViewItemList.get(pos);
            address=listViewItem.getAddress();
           if(listViewItem.getState()){
               if (!btsocketstate)
               { opensocket();}
            ConnectedThread.write("3");}
    }

    //창문 db 닫기상태로 업데이트
    void dbcloseupdate(int i)
    {
        if (databaseManager != null) {
            ContentValues updateRowValue = new ContentValues();
            updateRowValue.put("state", "false");
            databaseManager.update(updateRowValue,adapter.listViewItemList.get(i).getName());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onRefresh(){
        slideadapter = new Main_SlideAdapter(this, databaseManager);
        slideadapter.notifyDataSetChanged();
        viewpager.setAdapter(slideadapter);
        ((MainActivity) MainActivity.mContext).arduinoRefresh();
    }
}
