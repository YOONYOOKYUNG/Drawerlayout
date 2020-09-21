package com.cookandroid.windowairfresh;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AutoSetActivity extends AppCompatActivity {
    Button tpopen, tpclose, mmopen, mmclose;
    TextView tp_open, tp_close, mm_open, mm_close,thermometer,humid,micro;
    LinearLayout suchi;
    ToggleButton tbtn;
    Handler handler;
    public ToggleButton getTbtn(){return tbtn;}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_set);

        tpopen = findViewById(R.id.tpopen);
        tpclose = findViewById(R.id.tpclose);
        mmopen = findViewById(R.id.mmopen);
        mmclose = findViewById(R.id.mmclose);
        tp_open = findViewById(R.id.tp_open);
        tp_close = findViewById(R.id.tp_close);
        mm_open = findViewById(R.id.mm_open);
        mm_close = findViewById(R.id.mm_close);
        suchi = findViewById(R.id.suchi);
        tbtn = findViewById(R.id.tbtn);
        thermometer = findViewById(R.id.thermometer);


        suchi.setVisibility(View.INVISIBLE);

        tbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tbtn.isChecked() == true) {
                    suchi.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "자동모드가 켜졌습니다.", Toast.LENGTH_SHORT).show();
                }
                else if(tbtn.isChecked() == false){
                    tbtn.setChecked(false);
                    suchi.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "수동모드가 켜졌습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        tp_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                windowOpen();


            }
        });
        tp_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                windowClose();
            }
        });
        mm_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miseopen();
            }
        });
        mm_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miseclose();
            }
        });

        ImageView backarrow;
        backarrow = findViewById(R.id.backarrow);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    void windowOpen()
    {
        final EditText edittext = new EditText(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("열기");
        builder.setMessage("창문을 열 기준치를 적어주세요.");
        builder.setView(edittext);
        builder.setPositiveButton("입력",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),edittext.getText().toString() , Toast.LENGTH_LONG).show();
                        tp_open.setText(edittext.getText()+" ℃");
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }

    void windowClose()
    {
        final EditText edittext = new EditText(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("닫기");
        builder.setMessage("창문을 닫을 기준치를 적어주세요.");
        builder.setView(edittext);
        builder.setPositiveButton("입력",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),edittext.getText().toString() , Toast.LENGTH_LONG).show();
                        tp_close.setText(edittext.getText()+" ℃");
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }
    void miseopen()
    {
        final EditText edittext = new EditText(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("열기");
        builder.setMessage("창문을 열 기준치를 적어주세요.");
        builder.setView(edittext);
        builder.setPositiveButton("입력",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),edittext.getText().toString() , Toast.LENGTH_LONG).show();
                        mm_open.setText(edittext.getText()+"pm");
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }
    void miseclose()
    {
        final EditText edittext = new EditText(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("열기");
        builder.setMessage("창문을 열 기준치를 적어주세요.");
        builder.setView(edittext);
        builder.setPositiveButton("입력",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),edittext.getText().toString() , Toast.LENGTH_LONG).show();
                        mm_close.setText(edittext.getText()+"pm");
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}