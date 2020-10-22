package com.cookandroid.windowairfresh;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AutoAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
            Intent in = new Intent(context, AutoRestartService.class);
            context.startForegroundService(in);

    }

}
