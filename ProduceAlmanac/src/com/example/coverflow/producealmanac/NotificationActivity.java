package com.example.coverflow.producealmanac;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.coverflow.R;

public class NotificationActivity extends Activity{

	ArrayList<View> allViews= new ArrayList<View>();
	HashMap<View, View> subLayouts= new HashMap<View, View>();
	HashMap<View, Boolean> expanded = new HashMap<View, Boolean>();
	LinearLayout masterView;
	Button button1;
	Button button2;
	LinearLayout row1;
	 protected void onCreate(final Bundle savedInstanceState) {

	    	super.onCreate(savedInstanceState);
	    	setContentView(R.layout.notifications);
	    	masterView = (LinearLayout) findViewById(R.id.master);
	    	initializeButtons();
	    	
	 }
	 
	 public void initializeButtons(){
		
		 button1 = (Button)findViewById(R.id.button1);
		 button1.setText("+");
		 row1 = (LinearLayout) findViewById(R.id.bowlRow);
		 LinearLayout s1 = new LinearLayout(this);
		 s1.setOrientation(LinearLayout.VERTICAL);
		 CheckBox checkBox1 = new CheckBox(this);
		 checkBox1.setText("since i'm coming home");
		 
		 CheckBox checkBox2 = new CheckBox(this);
		 checkBox2.setText("well my body's been a mess");
		//TODO add checkboxes		
		 s1.addView(checkBox1);
		 s1.addView(checkBox2);
		 subLayouts.put(button1, s1);
		 allViews.add(row1);
		 //allViews.add(s1);
		 button1.setOnClickListener(buttonListener);
		 expanded.put(button1, false);
		 
		 /**
		 button2 = (Button)findViewById(R.id.button2);
		 button2.setText("+");
		 LinearLayout s2 = new LinearLayout(this);		 
		//TODO add checkboxes
		 TextView t2 = new TextView(this);
		 t2.setText("well my body's been a mess");
		 s2.addView(t2);
		 subLayouts.put(button2, s2);
		 allViews.add(button2);
		 //allViews.add(s2);
		 button2.setOnClickListener(buttonListener);
		 expanded.put(button2, false);
		 resetMasterLayout();*/
		 
	 }
	 
	 
	 
		View.OnClickListener buttonListener = new View.OnClickListener(){
			
			public void onClick(View v){
				boolean expanding;
				if (expanded.get(v)){
					expanding = false;
					expanded.put(v, false);
				}
				else {
					expanded.put(v, true);
					expanding=true;
				}
				
				
				View subLayout = subLayouts.get(v);
				
				//if (allViews.contains(subLayout)){
					//masterView.removeView(subLayout);
				//}
				int i = 0;
				int position=0;
				ViewGroup currentViewGroup;
				for (View currentView : allViews){
					currentViewGroup = (ViewGroup) currentView;
					int numChildren = currentViewGroup.getChildCount();
					for (int j = 0; j < numChildren; j++){
						if (currentViewGroup.getChildAt(j)==v){
							position=i+1;
							break;
						}
					}
					i++;
				}
					
				LinearLayout nextRow = (LinearLayout) allViews.get(position-1);
				
				Log.i("debugging", "gets past j/i loop and position was: "+ position);
				if (expanding){
					Button nextButton = (Button) v;
					nextButton.setText("-");
					
					//REMOVE OLD BUTTON
					nextRow.removeViewAt(0);
					nextRow.addView(nextButton, 0);
					//TODO edge case end of list 
					if (position!=0){
						allViews.remove(position-1);
						allViews.add(position-1, nextRow);
						allViews.add(position, subLayout);
					}
				}
				else {
					//CONTRACTING
					Button nextButton = (Button) v;
					nextButton.setText("+");
					nextRow.removeViewAt(0);
					nextRow.addView(nextButton, 0);
					if (position!=0){
						allViews.remove(position-1);
						allViews.add(position-1, nextRow);
						allViews.remove(position);
						
					}
				}
				resetMasterLayout();
			
			}
		};
		
		public void resetMasterLayout(){
			masterView.removeAllViews();
			for (View v: allViews){
				Log.i("debuggingNew", "adding a view");
				masterView.addView(v);
			}
		}

}
