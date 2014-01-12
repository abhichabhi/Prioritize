package com.example.call.handle;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

public class DeleteList extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete_list);
		
		final SQLiteDatabase database = this.openOrCreateDatabase("Lists", MODE_PRIVATE, null);
		database.execSQL("CREATE TABLE IF NOT EXISTS " +
                " BLACK_LIST " +
				" (USER_NAME VARCHAR , PHONE_NUMBER VARCHAR, BLOCK_LEVEL VARCHAR);");
		
		
		int i = 0;
        Cursor cursor = database.rawQuery("SELECT * FROM BLACK_LIST;", null);
        int num_rows = cursor.getCount();
        final String results[] = new String[num_rows];
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
        
       final ListView listView1 = (ListView) findViewById(R.id.listView1);
       ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
               android.R.layout.simple_list_item_1, results);
       listView1.setAdapter(adapter);
       
       listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			   String s = results[arg2];
			   String delimiter = "  -  ";
			   String[] temp;
			   temp = s.split(delimiter);
			   final String query = "DELETE FROM BLACK_LIST WHERE PHONE_NUMBER='" + temp[1] + "' ;";
			   //Cursor cursor = database.rawQuery(query, null);
			   
			   DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
				        switch (which){
				        case DialogInterface.BUTTON_POSITIVE:
				        	database.execSQL(query);   
							finish();
							startActivity(getIntent());
				            break;

				        case DialogInterface.BUTTON_NEGATIVE:
				        	finish();
							startActivity(getIntent());
				            break;
				        }
				    }
				};

				AlertDialog.Builder builder = new AlertDialog.Builder(DeleteList.this);
				builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
				    .setNegativeButton("No", dialogClickListener).show();
		}
	});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.delete_list, menu);
		return true;
	}

}
