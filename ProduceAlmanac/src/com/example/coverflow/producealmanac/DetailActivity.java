package com.example.coverflow.producealmanac;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.example.coverflow.R;

public class DetailActivity extends Activity {
	
	Item currentItem;
	Intent intent;
	String name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		intent = getIntent();
		name = intent.getStringExtra("name");
		currentItem = Item.itemMap.get(name);
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail, menu);
		return true;
	}

}
