package com.example.autosetting;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
Button tpopen, tpclose, mmopen, mmclose;
TextView tp_open, tp_close, mm_open, mm_close;
LinearLayout suchi;
ToggleButton tbtn, ntbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.automodesetting);

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
        ntbtn = findViewById(R.id.ntbtn);

        suchi.setVisibility(View.INVISIBLE);

        ntbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ntbtn.isChecked() == true) {
                    tbtn.setChecked(false);
                    suchi.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "수동모드가 켜졌습니다.", Toast.LENGTH_SHORT).show();




                }
                else if(ntbtn.isChecked() == false){
                    tbtn.setChecked(true);
                    suchi.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "자동모드가 켜졌습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tbtn.isChecked() == true){
                    ntbtn.setChecked(false);
                    suchi.setVisibility(View.VISIBLE);
                }
                else if(tbtn.isChecked() == false){
                    ntbtn.setChecked(true);
                    suchi.setVisibility(View.INVISIBLE);
                }

            }
        });


        tpopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                windowOpen();


            }
        });
        tpclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                windowClose();
            }
        });
        mmopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miseopen();
            }
        });
        mmclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miseclose();
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
}
