package com.computer.mode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Rohit on 6/5/2014.
 */
public class StopBatteryEye extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

      //  BatteryEye br=new BatteryEye();
 //br.demandDelete();
        int pid = android.os.Process.myPid();
        android.os.Process.killProcess(pid);
       //br.stopMediaPlayer();
      //  System.runFinalizersOnExit(true);

    }
}
