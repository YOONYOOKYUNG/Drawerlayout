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
    private BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler = new Handler();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        runnable = new Runnable() {
            @Override
            public void run() {
                if (mBluetoothAdapter.isEnabled()) { //블루투스 어답터 활성화 시
                    //저장된 주소 확인
                    SharedPreferences pf = getSharedPreferences("address", MODE_PRIVATE);
                    String shared_address = pf.getString("addr0", "");

                    if (shared_address.isEmpty()) {//저장된 주소가 비어있을 시
                        startActivity(new Intent(SplashActivity.this, AddressActivity.class));
                        //액티비티 전환 시 애니메이션 효과
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        finish();
                    } else {//저장된 주소가 있을 시
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        finish();
                    }
                } else { //블루투스 어답터 비활성화 시
                    startActivity(new Intent(SplashActivity.this, BluetoothOnActivity.class));
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    finish();
                }
            }
        };

        //1초후 핸들러 실행
        handler.postDelayed(runnable, 1000);

    }

    @Override
    protected void onDestroy() {
        //핸들러 중지
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }
}
