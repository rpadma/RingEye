package com.computer.mode;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HowTo extends Activity {
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);  
	        setContentView(R.layout.howto);
	        Button b=(Button)findViewById(R.id.startb);
         Button b1=(Button)findViewById(R.id.startb1);
         Button b2=(Button)findViewById(R.id.startb2);
	        b.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent it=new Intent(HowTo.this,main.class);
					startActivity(it);
				}
			});


         b1.setOnClickListener(new OnClickListener() {

             @Override
             public void onClick(View v) {
                 Intent it1=new Intent(HowTo.this,BatteryEye.class);
                 startActivity(it1);
             }
         });

         b2.setOnClickListener( new OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent an=new Intent(HowTo.this,AboutUs.class);
                 startActivity(an);
             }
         });


	 }
}
