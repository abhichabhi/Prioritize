package com.example.call.handle;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddList extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_list);
		
		
		final SQLiteDatabase database = this.openOrCreateDatabase("Lists", MODE_PRIVATE, null);
		database.execSQL("CREATE TABLE IF NOT EXISTS " +
                " BLACK_LIST " +
				" (USER_NAME VARCHAR , PHONE_NUMBER VARCHAR, BLOCK_LEVEL VARCHAR);");
		
		
		Button btAdd;
		btAdd = (Button) findViewById(R.id.button1);
		btAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					EditText tName;
					tName = (EditText) findViewById(R.id.editText1);
					final String user_name = tName.getText().toString();
					
					EditText tPhone;
					tPhone = (EditText) findViewById(R.id.editText2);
					final String phone_num = tPhone.getText().toString();
					
					EditText tLevel;
					tLevel = (EditText) findViewById(R.id.editText3);
					final String block_level = tLevel.getText().toString();
					
					if(user_name.length()!=0 &&
							phone_num.length() !=0 &&
							block_level.length() !=0
							)
					{
					
						String text = "INSERT INTO BLACK_LIST VALUES ( " +
							"'" + user_name + "'" +
							" , '" + phone_num + "'" +
							" , '" + block_level + "'" +
							" );";
						
						Toast msg = Toast.makeText(AddList.this, "Successfully Added.", Toast.LENGTH_LONG);
						msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2, msg.getYOffset() / 2);
						msg.show();
					
						database.execSQL(text);	
						finish();
					}
					else
					{
						Toast msg = Toast.makeText(AddList.this, "Fields Cannot be Empty.", Toast.LENGTH_LONG);
						msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2, msg.getYOffset() / 2);
						msg.show();
					}
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_list, menu);
		return true;
	}

}
