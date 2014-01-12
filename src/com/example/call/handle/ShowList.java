package com.example.call.handle;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ShowList extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_list);
		
		final SQLiteDatabase database = this.openOrCreateDatabase("Lists", MODE_PRIVATE, null);
		database.execSQL("CREATE TABLE IF NOT EXISTS " +
                " BLACK_LIST " +
				" (USER_NAME VARCHAR , PHONE_NUMBER VARCHAR, BLOCK_LEVEL VARCHAR);");
		
		int i = 0;
        Cursor cursor = database.rawQuery("SELECT * FROM BLACK_LIST;", null);
        int num_rows = cursor.getCount();
        String results[] = new String[num_rows];
        if(cursor !=null){
    	   if(cursor.moveToFirst()){
    		   do{
    		   String user_name = cursor.getString(cursor.getColumnIndex("USER_NAME"));
    		   String phone_number = cursor.getString(cursor.getColumnIndex("PHONE_NUMBER"));
    		   String block_level = cursor.getString(cursor.getColumnIndex("BLOCK_LEVEL"));
    		   String text =  user_name + "  -  " + phone_number + "  -  " + block_level;
    		   results[i++] = text;
    		   }while(cursor.moveToNext());
    	   }
       }
        
       ListView listView1 = (ListView) findViewById(R.id.listView1);
       ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
               android.R.layout.simple_list_item_1, results);
       listView1.setAdapter(adapter);
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_list, menu);
		return true;
	}

}
