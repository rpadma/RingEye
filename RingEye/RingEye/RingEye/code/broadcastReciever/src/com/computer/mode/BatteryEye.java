package com.computer.mode;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Rohit on 6/2/2014.
 */
public class BatteryEye extends Activity {

    Dbhandlerbat dbbat;
    SeekBar sb;
    ProgressBar pb;
    public TextView Pbt;
    public TextView Sbt;
    ArrayList<String> aa;
    Button save;
    SQLiteDatabase Mydatabase;
    int n;int k;
    MediaPlayer mp;
    ToggleButton tb1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.newbattery
        );

         sb=(SeekBar)findViewById(R.id.seekBar2);
        pb=(ProgressBar)findViewById(R.id.progressBar2);
        save=(Button)findViewById(R.id.button);
        final Button btnstop=(Button)findViewById(R.id.button2);
Pbt=(TextView)findViewById(R.id.textView);
        Sbt=(TextView)findViewById(R.id.textView2);
this.dbbat=new Dbhandlerbat(this);
FetchingData();
        Mydatabase=dbbat.getReadableDatabase();
        aa=this.dbbat.getModes(Mydatabase);
        System.out.println("Value of Db"+aa);
        String s1=aa.toString();
        s1=s1.substring(1,s1.length()-1);

        String [] s=s1.split(",");
        n=Integer.parseInt(s[0]);


        sb.setProgress(Integer.parseInt(s[0]));
        Sbt.setText("Level:"+s[0]+"%");
        k=n;
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
           int pro=0;
            TextView tv1 =(TextView)findViewById(R.id.textView2);
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
pro=i;


                tv1.setText("Level:"+i+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
k=pro;
                System.out.println("final status:"+k);
            }
        });







        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View V) {
               dbbat=new Dbhandlerbat(BatteryEye.this);
                FetchingData();

                Mydatabase=dbbat.getReadableDatabase();
                if(k==n)
                {
                    Mydatabase.execSQL("update battery set text='"+n+"' where Mode=1");
                    //  System.out.println("value of n :"+n);

                }
                else {
                    Mydatabase.execSQL("update battery set text='" + k + "' where Mode=1");
                    //System.out.println("value of k :"+k);
                }

                System.out.println("^^^^^^^^^^^^^^^ inserted value is   "+k);

                Toast.makeText(getApplicationContext(), "Saved Successfully", 70).show();
            }
        }
        );




       btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);



            }
        });

        registerReceiver(brar,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    private void FetchingData() {

        // TODO Auto-generated method stub
        try {

           dbbat.onCreateDatabase();


        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }
        try {
           dbbat.openDataBase();
            Mydatabase = dbbat.getWritableDatabase();
            System.out.println("executed");





        }
        catch (Exception e)
        {

        }




        // TODO Auto-generated method stub

    }
    public BroadcastReceiver brar;
    {
        brar=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int level = intent.getIntExtra("level", 0);
                ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar2);
                pb.setProgress(level);
                System.out.println("level:"+level);
                Pbt.setText("BatteryStatus:"+Integer.toString(level)+"%");
                //  System.out.println("level"+level);
                SeekBar sbh = (SeekBar) findViewById(R.id.seekBar2);
                int phb = sbh.getProgress();
//boolean check = (BatteryManager.BATTERY_PLUGGED_AC);

              //  TextView tv2=(TextView)findViewById(R.id.textView);
                //tv2.setText(pb.getProgress());


                int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
                boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
                boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;



                if ((level== phb)&&(phb>0)&&(usbCharge||acCharge))  {
                    try {
                        AssetFileDescriptor afd = getAssets().openFd("Crazy.mp3");

                        mp=new MediaPlayer();
                        mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                        mp.setLooping(true);
                        mp.prepare();

                        mp.start();
                    }
                    catch(IOException ioe)
                    {

                    }
                }

            }


        };


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy()
    {
        dbbat.close();
     //  stopMediaPlayer();
        super.onDestroy();
        //finish();

       // int pid = android.os.Process.myPid();
        //android.os.Process.killProcess(pid);
      //unregisterReceiver(brar);
    }

    public void demandDelete() {
        dbbat.close();
         stopMediaPlayer();
        super.onDestroy();
        // int pid = android.os.Process.myPid();
         //android.os.Process.killProcess(pid);

    }


    public void stopMediaPlayer()
    {
        mp.pause();
mp.stop();

        super.onDestroy();


    }

}
