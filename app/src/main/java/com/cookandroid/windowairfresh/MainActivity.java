package com.cookandroid.windowairfresh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    //블루투스 관련 선언 시작(블투1)
    private static final String TAG = "bluetooth2";
    final int RECIEVE_MESSAGE = 1;        // Status  for Handler
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder sb = new StringBuilder();
    private static int flag = 0;
    private ConnectedThread ConnectedThread;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static String address = "90:D3:51:F9:26:E0";
    //블루투스 관련 선언 종료(블투1)

    private DatabaseManager databaseManager;
    ViewPager viewpager;
    WindowListAdapter adapter;
    TextView tvdate,thermometer,humid,micro;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Handler handler;
    SwipeRefreshLayout swipeRefreshLayout;
    final int WindowList_REQUEST = 2020;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if(resultCode==RESULT_OK && requestCode==WindowList_REQUEST){
            if(adapter.listViewItemList.isEmpty()) {
                address = "90:D3:51:F9:26:E0";
            }
            else
            {
                WindowDetails listViewItem = adapter.listViewItemList.get(0);
                address=listViewItem.getAddress();}
            setResult(RESULT_OK, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frag,new Main_Fragment1());
        fragmentTransaction.commit();*/





        viewpager = findViewById(R.id.viewpager);

        databaseManager = DatabaseManager.getInstance(this);
        adapter = new WindowListAdapter();
        adapter.setDatabaseManager(databaseManager);
        adapter.initialiseList();

        Main_SlideAdapter adapter = new Main_SlideAdapter(getSupportFragmentManager());
        viewpager.setAdapter(adapter);



        /*thermometer = findViewById(R.id.thermometer);
        micro = findViewById(R.id.micro);
        humid = findViewById(R.id.humid);*/


        handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case RECIEVE_MESSAGE:
                        sb.delete(0, sb.length());
                        byte[] readBuf = (byte[]) msg.obj;
                        String strIncom = new String(readBuf, 0, msg.arg1);
                        Log.d("a2",strIncom);
                        sb.append(strIncom);
                        Log.d("a2", String.valueOf(sb));

                        int endOfLineIndex = sb.indexOf("/");
                        Log.d("a3", String.valueOf(endOfLineIndex));
                        if (endOfLineIndex > 0) {
                            String sbprint = sb.substring(0, endOfLineIndex);
                            Log.d("a4",sbprint);
                            sb.delete(0, sb.length());

                            String[] array = sbprint.split("#");
                            Log.d("a5",array[0]);

                           /* thermometer.setText(array[0]);
                            micro.setText(array[1]);
                            humid.setText(array[2]);*/
                            Log.d("a6","값 띄움");

                            flag++;
                        }
                        break;
                }
            };
        };

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
                R.string.nav_drawer_open,R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

    }
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    //menu
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {

        switch (menuitem.getItemId()){
            case R.id.auto_set:
                Intent intent1 = new Intent(MainActivity.this, AutoSetActivity.class);
                startActivity(intent1);
                break;

            case R.id.alarm:
                Intent intent2 = new Intent(MainActivity.this,AlarmActivity.class);
                startActivity(intent2);
                break;

            case R.id.log_record:
                Intent intent3 = new Intent(MainActivity.this, ActlogActivity.class);
                startActivity(intent3);

            case R.id.window:
                Intent intent4 = new Intent(MainActivity.this, WindowlistActivity.class);
                startActivityForResult(intent4, WindowList_REQUEST);

        }

        return true;
    }


    //(블투3)
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

    private void errorExit(String title, String message){
        Toast.makeText(getBaseContext(), title + " - " + message, Toast.LENGTH_LONG).show();
        finish();
    }

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
                    bytes = InputStream.read(buffer);        // Get number of bytes and message in "buffer"
                    handler.obtainMessage(RECIEVE_MESSAGE, bytes, -1, buffer).sendToTarget();     // Send to message queue Handler
                } catch (IOException e) {
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

    @Override
    public void onResume() {
        super.onResume();
        BluetoothDevice device = btAdapter.getRemoteDevice(address);
        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            errorExit("Fatal Error", "In onResume() and socket create failed: " + e.getMessage() + ".");
        }
        btAdapter.cancelDiscovery();
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

    @Override
    public void onPause() {
        super.onPause();
        try     {
            btSocket.close();
        } catch (IOException e2) {
            errorExit("Fatal Error", "In onPause() and failed to close socket." + e2.getMessage() + ".");
        }
    }

}
