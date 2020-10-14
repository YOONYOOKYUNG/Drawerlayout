package com.cookandroid.windowairfresh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class AddressActivity extends AppCompatActivity {

    Spinner spinner1, spinner2;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        spinner1 = findViewById(R.id.spinner1);
        spinner2=findViewById(R.id.spinner2);
        btn = findViewById(R.id.btn_address_search);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pf = getSharedPreferences("address", MODE_PRIVATE);
                SharedPreferences.Editor editor =pf.edit();
                editor.putString("addr1", spinner1.getSelectedItem().toString());
                editor.putString("addr2", spinner2.getSelectedItem().toString());
                editor.commit();


                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

                finish();

            }
        });
    }
}