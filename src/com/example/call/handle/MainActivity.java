package com.example.call.handle;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.app.Activity;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Toggle Button Text
        final ToggleButton button = (ToggleButton)findViewById(R.id.toggleButton1);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TextView tv;
				tv = (TextView) findViewById(R.id.textView2);
				if(button.isChecked())
					tv.setText("App Running.");
				else
					tv.setText("App Not Running.");
				
			}
		});
		
		
		// Exit Button
		Button bt;
		bt = (Button) findViewById(R.id.button2);
		bt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				
			}
		});
		
		//Block List
		SQLiteDatabase database = this.openOrCreateDatabase("Lists", MODE_PRIVATE, null);
		
		//database.execSQL("DROP TABLE IF EXISTS BLACK_LIST;");
		
		database.execSQL("CREATE TABLE IF NOT EXISTS " +
                " BLACK_LIST " +
                " (USER_NAME VARCHAR , PHONE_NUMBER VARCHAR, BLOCK_LEVEL VARCHAR);");
		database.close();
		
		Button bt1;
		bt1 = (Button) findViewById(R.id.button1);
		bt1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent startNewActivityOpen = new Intent(MainActivity.this,Lists.class);
				startActivityForResult(startNewActivityOpen, 0);
				
			}
		});
		
		//End
		
    }
    
    protected void onResume() {
    	CallHandler chandler = new CallHandler();
    	registerReceiver(chandler,new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED));
    	super.onResume();
    	
    	//Toggle Button Text
		final ToggleButton button = (ToggleButton)findViewById(R.id.toggleButton1);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TextView tv;
				tv = (TextView) findViewById(R.id.textView2);
				if(button.isChecked())
					tv.setText("App Running.");
				else
					tv.setText("App Not Running.");
				
			}
		});
		
		
		
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public class CallHandler extends BroadcastReceiver {
    	
    	
    	private SQLiteDatabase database;
    	int prevPhoneState = -1;
    	
    	@Override
    	public void onReceive(Context context, Intent intent) {
    		AudioManager audiomanage = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
    		final int defaultPhoneState = 2;
    		
    		/*
    		 database = context.openOrCreateDatabase("Logs", 0, null);
    		
    	     database.execSQL("CREATE TABLE IF NOT EXISTS " +
    	                " SPEED_VIOLATIONS " +
    	                " (DATE VARCHAR, TIME VARCHAR," +
    	                " VIOLATION_TYPE VARCHAR );");

    		 
    		 Cursor cursor = database.rawQuery("SELECT * FROM SETTINGS;", null);
    		 cursor.moveToLast();
    		 int call = (int)cursor.getInt(cursor.getColumnIndex("CALL_HANDLE"));
    		 if(call==1)
    		 {
    		
    			 
    			 
    			 *
    			 */
    		/*
    		 * Broken on Android 2.2+
    		//Log.v(TAG, "Receving....");
    		  TelephonyManager telephony = (TelephonyManager) 
    		  context.getSystemService(Context.TELEPHONY_SERVICE);  
    		  try {
    		   Class c = Class.forName(telephony.getClass().getName());
    		   Method m = c.getDeclaredMethod("getITelephony");
    		   m.setAccessible(true);
    		   telephonyService = (ITelephony) m.invoke(telephony);
    		   //telephonyService.silenceRinger();
    		   telephonyService.endCall();
    		  } catch (Exception e) {
    		   e.printStackTrace();
    		  }
    		  */
    		
    		Bundle extras = intent.getExtras();
    		
    		ToggleButton button;
			button = (ToggleButton) findViewById(R.id.toggleButton1);

    		if (extras!=null  && button.isChecked())
    		{
    			String state = extras.getString(TelephonyManager.EXTRA_STATE);
    			    			
    			if(state.equals(TelephonyManager.EXTRA_STATE_RINGING))
    			{
    				String phoneNumber = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
    				
    				database = MainActivity.this.openOrCreateDatabase("Lists", MODE_PRIVATE, null);
    				
    				database.execSQL("CREATE TABLE IF NOT EXISTS " +
    		                " BLACK_LIST " +
    		                " (USER_NAME VARCHAR , PHONE_NUMBER VARCHAR, BLOCK_LEVEL VARCHAR);");
    				
    				String query = "SELECT * FROM BLACK_LIST WHERE PHONE_NUMBER = '" + phoneNumber + "' ;";
    				Cursor cursor = database.rawQuery(query, null);
    				int num_rows = cursor.getCount();
    				
    				if(num_rows > 0)
    				{
    				
    				prevPhoneState = audiomanage.getRingerMode();
    				audiomanage.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    				SmsManager smsManager = SmsManager.getDefault();
    				String smsString = "The person you are calling is currently Sleeping," +
    						" and will call you later.";
    				smsManager.sendTextMessage(phoneNumber, null, smsString, null, null);
    				}
    			}
    			/*
    			if(state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))
    			{
   
    				 SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                     String date = sdf.format(new Date());
                     sdf = new SimpleDateFormat("HH:mm:ss");
                     String timer = sdf.format(new Date());

    				database.execSQL("INSERT INTO " +
                            " SPEED_VIOLATIONS " + " Values(" + "'" + date + "','" + timer +
                            "'," +" 'Negligent Driving'" + ");");
    				
    			}*/
    			if(state.equals(TelephonyManager.EXTRA_STATE_IDLE))
    			{
    				if(prevPhoneState==-1)
    				{
    					audiomanage.setRingerMode(defaultPhoneState);
    				}
    				else
    				{
    					audiomanage.setRingerMode(prevPhoneState);
    				}
    			}
    			database.close();
    		}
    	}
    	//}
    }
}