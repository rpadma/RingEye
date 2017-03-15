package com.computer.mode;

import java.io.IOException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;



public class Broadcast extends BroadcastReceiver {
	// private ITelephony telephonyService;
	 private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
     private static final String TAG = "SMSBroadcastReceiver";
     Dbhandler myDbHelper;
     private AudioManager maudio;
 	SQLiteDatabase Mydatabase;
 	 SQLiteDatabase db;
 	Context context = null;
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		 Log.i(TAG, "Intent recieved: " + intent.getAction());
		 maudio=(AudioManager)context.getSystemService(context.AUDIO_SERVICE);
         if (intent.getAction().equals(SMS_RECEIVED)) {
             Bundle bundle = intent.getExtras();
             if (bundle != null) {
            	  Log.i(TAG, "Message recieved: ");
                 Object[] pdus = (Object[])bundle.get("pdus");
                 final SmsMessage[] messages = new SmsMessage[pdus.length];
                 for (int i = 0; i < pdus.length; i++) {
                     messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                     System.out.println("message is.."+messages[i]);
                     Log.i(TAG, "Message recieved: " + messages[0].getMessageBody());
                     String mode=messages[0].getMessageBody();
                     System.out.println("message body is.."+mode+"2");
                     String m_mode=getMode(mode.trim().toLowerCase(),context);
                     System.out.println("mode is...."+m_mode);
                     changemode(m_mode.trim());
                    
                 }
                 if (messages.length > -1) {
                     Log.i(TAG, "Message recieved: " + messages[0].getMessageBody());
                 }
             }
			}
         else
         {
        	  Log.i(TAG, "in else... ");
        	 
         }
	}

	private void changemode(String m_mode) {
		// TODO Auto-generated method stub
		 if(m_mode.equalsIgnoreCase("silent"))
         {
		
			 System.out.println("The phone state is changing to silent mode");
			 int n=maudio.getRingerMode();
			 System.out.println("The phone state is changing to silent mode "+n);
        	  maudio.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        	  Log.i(TAG, "Change to Silent");
         }
         else if(m_mode.equalsIgnoreCase("ring"))
         {
        	 System.out.println("The phone state is changing to ring mode");
        	   maudio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        	   Log.i(TAG, "Changed to Ring ");
         }
         
         else if(m_mode.equalsIgnoreCase("vibrate"))
         {
        	 System.out.println("The phone state is changing to vibrate mode");
        	  maudio.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        	  Log.i(TAG, "Changed to Vibrate");
         }
         
		
	}

	private String getMode(String mode,Context context) {
		// TODO Auto-generated method stub
		String m=null;
		this.myDbHelper=new Dbhandler(context);
		FetchingData();
		System.out.println("inside getmodde");
	   Mydatabase=myDbHelper.getReadableDatabase();
	   Cursor c=Mydatabase.rawQuery("SELECT Mode FROM mode where text='"+mode+"' ", null);
	   //System.out.println(" $$$$$$$$$$$$$$$$$$$$$$$ fetchdata completed @@@@@@@@@@@@@@@@@@@@@");
	   c.moveToFirst();
	 if(c!=null){
		   int i=c.getColumnIndex("Mode");
		   System.out.println("column index is.."+i);
		  m =c.getString(i).toString().toLowerCase();
		  System.out.println("mode in getmode.."+m);
	   }
		return m;
	}
	
	private void FetchingData() {
		// TODO Auto-generated method stub
		 try {  
			 
	     	myDbHelper.onCreateDataBase();
	     	       	
	     	
		} catch (IOException ioe) {

			throw new Error("Unable to create database");

		} 
		try {

			myDbHelper.openDataBase();
			db  = myDbHelper.getReadableDatabase();
			System.out.println("executed");
		
		}catch(SQLException sqle){

			throw sqle;

		}
		// TODO Auto-generated method stu
		
	}
}
