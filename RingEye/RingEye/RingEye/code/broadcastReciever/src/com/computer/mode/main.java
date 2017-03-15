package com.computer.mode;



import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class main extends Activity {
	Dbhandler myDbHelper;
	SQLiteDatabase Mydatabase;
	ArrayList<String> aa;
    EditText silent,ring,vibrate;
    Button save;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        silent=(EditText)findViewById(R.id.silent);
        ring=(EditText)findViewById(R.id.ring);
        vibrate=(EditText)findViewById(R.id.vibrate);
        save=(Button)findViewById(R.id.Save);
        this.myDbHelper=new Dbhandler(this);
		FetchingData();
		Mydatabase=myDbHelper.getReadableDatabase();
		aa=this.myDbHelper.getModes(Mydatabase);
		System.out.println("values of Db      "+aa);
		String s1= aa.toString();
		s1=s1.substring(1, s1.length()-1);
		//s1=s1.replace("", newChar)
		String[] s=s1.split(",");
        
        	silent.setText(s[0].trim());
            ring.setText(s[1].trim());
            vibrate.setText(s[2].trim());
           save.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String stxt=silent.getText().toString().trim().toLowerCase();
				String rtxt=ring.getText().toString().trim().toLowerCase();
				String vtxt=vibrate.getText().toString().trim().toLowerCase();
				if(stxt.equals("") || rtxt.equals("") || vtxt.equals(""))
				{
					
					 Toast.makeText(getApplicationContext(), "Please fill all fields", 60).show();
					
				}
				else
				{
					/*Context context = getApplicationContext();
	                CharSequence error = "Please enter a track name" +stxt;
	                int duration = Toast.LENGTH_LONG;

	                Toast toast = Toast.makeText(context, error, duration);
	                toast.show();*/
					 
					  
					  myDbHelper=new Dbhandler(main.this);
						 FetchingData();
						Mydatabase=myDbHelper.getReadableDatabase();
						Mydatabase.execSQL("update mode set text='"+stxt+"' where Mode='silent'");
						System.out.println("^^^^^^^^^^^^^^^ inserted value is   "+stxt);
						Mydatabase.execSQL("update mode set text='"+rtxt+"' where Mode='ring'");
						Mydatabase.execSQL("update mode set text='"+vtxt+"' where Mode='vibrate'");
						Toast.makeText(getApplicationContext(), "Saved Successfully", 70).show();
				}
				
			}
			});
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
	 		Mydatabase = myDbHelper.getWritableDatabase();
			System.out.println("executed");
	 	
	 	}catch(SQLException sqle){
	 
	 		throw sqle;
	 
	 	}
		// TODO Auto-generated method stub
			
	}

    @Override
    public void onDestroy()
    {
        myDbHelper.close();
super.onDestroy();
    }

}