package com.example.call.handle;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Lists extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lists);
		
		//Show Lists
		Button btShow;
		btShow = (Button) findViewById(R.id.button1);
		btShow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent startNewActivityOpen = new Intent(Lists.this,ShowList.class);
				startActivityForResult(startNewActivityOpen, 0);
				
			}
		});
		
		//Add Names To List
		Button btAdd;
		btAdd = (Button) findViewById(R.id.button2);
		btAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent startNewActivityOpen = new Intent(Lists.this,AddList.class);
				startActivityForResult(startNewActivityOpen, 0);
				
			}
		});
		
		//Delete Names
		
		Button btDel;
		btDel = (Button) findViewById(R.id.button3);
		btDel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent startNewActivityOpen = new Intent(Lists.this,DeleteList.class);
				startActivityForResult(startNewActivityOpen, 0);
				
			}
		});
		
		//End
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lists, menu);
		return true;
	}

}
