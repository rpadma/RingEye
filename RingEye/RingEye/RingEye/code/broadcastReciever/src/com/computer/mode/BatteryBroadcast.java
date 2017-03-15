package com.computer.mode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Rohit on 6/5/2014.
 */
public class BatteryBroadcast extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i=new Intent(context,BatteryEye.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

    }
}
