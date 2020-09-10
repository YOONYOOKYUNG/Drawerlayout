package com.cookandroid.windowairfresh;

import android.app.Dialog;
import android.content.Context;

public class CustomDialog_popup3 {
    private Context context;

    public CustomDialog_popup3(Context context){
        this.context = context;
    }

    //호출할 함수 정의
    public void callTemp(){
        //커스텀 Dialog 정의 위해 Dialog 클래스 생성
        final Dialog dlg3 = new Dialog(context);
    }
}
