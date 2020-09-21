package com.cookandroid.windowairfresh;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
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
    TextView tp_open, tp_close, mm_open, mm_close;
    LinearLayout suchi;
    ToggleButton tbtn;

    String sfName = "File";
    String state="";


    public ToggleButton getTbtn(){return tbtn;}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ModeSetting);

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

        suchi.setVisibility(View.INVISIBLE);

        SharedPreferences sf = getSharedPreferences(sfName, 0);
        state = sf.getString("state", ""); // 키값으로

        tp_open.setText( sf.getString("tp_open", ""));
        tp_close.setText(sf.getString("tp_close", ""));
        mm_open.setText( sf.getString("mm_open", ""));
        mm_close.setText(sf.getString("mm_close", ""));


        if(state=="auto"){
            tbtn.setChecked(true);
            suchi.setVisibility(View.VISIBLE);

        }else if(state=="nonauto"){
            tbtn.setChecked(false);
            suchi.setVisibility(View.INVISIBLE);

        }


        tbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tbtn.isChecked() == true) {
                    suchi.setVisibility(View.VISIBLE);
                    state="auto";
                    Toast.makeText(getApplicationContext(), "자동모드가 켜졌습니다.", Toast.LENGTH_SHORT).show();
                }
                else if(tbtn.isChecked() == false){
                    tbtn.setChecked(false);
                    suchi.setVisibility(View.INVISIBLE);
                    state="nonauto";
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

    protected void onStop(){
        super.onStop();

        SharedPreferences sf = getSharedPreferences(sfName, 0);
        SharedPreferences.Editor editor = sf.edit();//저장하려면 editor가 필요
        editor.clear();
        editor.putString("state", state); // 입력
        editor.putString("tp_open",tp_open.toString());
        editor.putString("tp_close",tp_close.toString());
        editor.putString("mm_open",mm_open.toString());
        editor.putString("mm_close",mm_close.toString());
        editor.commit(); // 파일에 최종 반영함

    }
}