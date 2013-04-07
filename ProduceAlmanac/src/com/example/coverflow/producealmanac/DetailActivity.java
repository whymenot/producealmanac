package com.example.coverflow.producealmanac;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.example.coverflow.R;

public class DetailActivity extends Activity {
	
	Item currentItem;
	Intent intent;
	String name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		intent = getIntent();
		name = intent.getStringExtra("name");
		currentItem = Item.itemMap.get(name);
		
		
		setFields();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail, menu);
		return true;
	}

	public void setFields(){
		TextView itemName = (TextView)findViewById(R.id.item_name);
		TextView itemGeneral = (TextView)findViewById(R.id.item_general);
		TextView itemRipe = (TextView)findViewById(R.id.item_ripe);
		TextView itemStorage = (TextView)findViewById(R.id.item_storage);
		itemName.setText("");
		itemGeneral.setText("");
		itemRipe.setText("");
		itemStorage.setText("");
	}
}
