package com.cookandroid.drawerlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ConnpopupActivity extends AppCompatActivity {
    Button btnok, btnend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connpopup);
        setTitle("Bluetooth 연결");

        btnok = findViewById(R.id.btnok);
        btnend = findViewById(R.id.btnend);

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConnpopupActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
