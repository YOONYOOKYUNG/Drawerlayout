package com.cookandroid.windowairfresh;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    Handler handler;
    Runnable runnable;
    private BluetoothAdapter mBluetoothAdapter; // 블루투스 어댑터

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
       handler = new Handler();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        runnable = new Runnable() {
            @Override
            public void run() {
                if (mBluetoothAdapter.isEnabled()) {
                    startActivity(new Intent(SplashActivity.this, AddressActivity.class));
                    finish();
                }
                else{
                    startActivity(new Intent(SplashActivity.this, BluetoothOnActivity.class));
                    finish();
                }
            }
        };

        handler.postDelayed(runnable, 1000);

    }
   @Override
    protected void onDestroy() {
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }
}
