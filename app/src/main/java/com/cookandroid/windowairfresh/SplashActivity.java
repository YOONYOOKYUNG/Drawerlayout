package com.cookandroid.windowairfresh;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
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
        setContentView(R.layout.activity_splash);
       handler = new Handler();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        runnable = new Runnable() {
            @Override
            public void run() {
                if (mBluetoothAdapter.isEnabled()) {

                    SharedPreferences pf =getSharedPreferences("address",MODE_PRIVATE);
                    String shared_address=pf.getString("addr0","");
                    if(shared_address.isEmpty()) {
                        startActivity(new Intent(SplashActivity.this, AddressActivity.class));
                        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                        finish();
                    }
                    else{
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                    }
                }
                else{
                    startActivity(new Intent(SplashActivity.this, BluetoothOnActivity.class));
                    overridePendingTransition(R.anim.fadein,R.anim.fadeout);
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
