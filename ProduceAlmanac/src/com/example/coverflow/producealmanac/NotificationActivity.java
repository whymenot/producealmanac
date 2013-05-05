package com.example.coverflow.producealmanac;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.coverflow.R;

public class NotificationActivity extends Activity{

	ArrayList<View> allViews= new ArrayList<View>();
	HashMap<View, View> subLayouts= new HashMap<View, View>();
	HashMap<View, Boolean> expanded = new HashMap<View, Boolean>();
	LinearLayout masterView;
	Button button1;
	Button button2;
	LinearLayout row1;
	LinearLayout row2;
	Button buttonAllStores;
	CheckBox allStoresFruit;
	CheckBox allStoresVegetable;
	SearchView searchAllStores;
	ListView listAllStores;
	ArrayList<String> personalAllStores= new ArrayList<String>();
	
	Button buttonBerkeleyBowl;
	CheckBox berkeleyBowlFruit;
	CheckBox berkeleyBowlVegetable;
	ArrayList<String> personalBerkeleyBowl;
	
	Button buttonSafeway;
	CheckBox safewayFruit;
	CheckBox safewayVegetable;	
	ArrayList<String> personalSafeway;
	
	Button buttonTraderJoes;
	CheckBox traderJoesFruit;
	CheckBox traderJoesVegetable;
	ArrayList<String> personalTraderJoes;
	
	Button buttonWholeFoods;
	CheckBox wholeFoodsFruit;
	CheckBox wholeFoodsVegetable;
	ArrayList<String> personalWholeFoods;
	
	Button buttonYasai;
	CheckBox yasaiFruit;
	CheckBox yasaiVegetable;
	ArrayList<String> personalYasai;
	
	
	 protected void onCreate(final Bundle savedInstanceState) {

	    	super.onCreate(savedInstanceState);
	    	setContentView(R.layout.notifications);
	    	masterView = (LinearLayout) findViewById(R.id.master);
	    	initializeButtons();
	    	
	 }
	 LinearLayout s1;	 
	 LinearLayout checkBoxRow1;
	 public void initializeButtons(){
		//android:icon="@android:drawable/presence_offline"
		 //ALLSTORES
		 Button b = (Button) findViewById(R.id.buttonAllStores);
		 row1 = (LinearLayout) findViewById(R.id.row_all_stores);
		 
		 s1 = new LinearLayout(this);
		 allStoresFruit = new CheckBox(this);
		 allStoresVegetable = new CheckBox(this);
		 searchAllStores = new SearchView(this);
		 listAllStores = new ListView(this);
		 initializeCheckBoxes(s1, allStoresFruit, allStoresVegetable, row1, b, searchAllStores);
		 initializeSearch(searchAllStores);
		 
		 
		 
		 Button bowlButton = (Button) findViewById(R.id.buttonBerkeleyBowl);
		 row2 = (LinearLayout) findViewById(R.id.row_berkeley_bowl);
		 LinearLayout s2 = new LinearLayout(this);
		 berkeleyBowlFruit = new CheckBox(this);
		 berkeleyBowlVegetable = new CheckBox(this);
		 //initializeCheckBoxes(s2, berkeleyBowlFruit, berkeleyBowlVegetable, row2, bowlButton, searchBerkeleyBowl);

		 //BERKELEYBOWL
		 /**row2 = (LinearLayout) findViewById(R.id.row_berkeley_bowl);
		 LinearLayout s2 = new LinearLayout(this);
		 berkeleyBowlFruit = new CheckBox(this);
		 berkeleyBowlVegetable = new CheckBox(this);
		 initializeCheckBoxes(s2, berkeleyBowlFruit, berkeleyBowlVegetable, row2);
		 resetMasterLayout();
		
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
	 
	 public void initializeCheckBoxes(LinearLayout layout, CheckBox fruitCheckBox, CheckBox vegetableCheckBox, LinearLayout row, Button button, SearchView search){
		 LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
			     LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		 Log.i("debugging", "width of button is: "+ R.dimen.width_of_button);
		 layoutParams.setMargins(getResources().getDimensionPixelSize(R.dimen.width_of_button), 0, 0, 0);
	 
		 button.setText("+");
		 layout.setOrientation(LinearLayout.VERTICAL);
		 fruitCheckBox.setText("all new fruits");
		 
		 vegetableCheckBox.setText("all new vegetables");
		
		 layout.addView(fruitCheckBox, layoutParams);
		 layout.addView(vegetableCheckBox, layoutParams);
		 layout.addView(search, layoutParams);
		 layout.addView(listAllStores, layoutParams);
		 subLayouts.put(button, layout);
		 allViews.add(row);
		 button.setOnClickListener(buttonListener);
		 expanded.put(button, false);
	 }
	 
	 
	 
	 
	 
	 
	 public void initializeSearch(SearchView search){
	        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    
	        currentSearchView=search;
	        
	        // Assumes current activity is the searchable activity
	        search.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	        search.setIconifiedByDefault(true); // Do not iconify the widget; expand it by default
	        
	        //final SearchView.OnQueryTextListener queryTextListener = ; 
	        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() { 
	            @Override 
	            public boolean onQueryTextChange(String newText) { 
	                // Do something 
	            	Log.i("debugging", "on query text changed");
	            	showResults(newText);
	                return true; 
	            } 

	            @Override 
	            public boolean onQueryTextSubmit(String query) { 
	                // Do something 
	            	currentSearchView.setIconified(true); 
	                return true; 
	            } 
	        });
	 }

	 public void removeStore(Button storeButton){
		 Log.i("debugging", "in removeStore");
		 personalAllStores.remove(buttons.get(storeButton));
		 s1.removeView(textRows.get(storeButton));
	 }
	 public void addStore(String newStore){
		 for (String s : personalAllStores){
			 if (s.equals(newStore)){
				 //TODO: Toast that this item has already been added 
				 //TODO: take things out of list
				 return;
			 }
			 
		 }
		 personalAllStores.add(newStore);
		 LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		 LinearLayout textRow = new LinearLayout(this);
		 //textRow.setLayoutParams(layoutParams);
		 Log.i("debugging", "after assigning params");
		 textRow.setOrientation(LinearLayout.HORIZONTAL);
		 
		 LinearLayout.LayoutParams textRowParams = new LinearLayout.LayoutParams(
			     LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		 textRowParams.setMargins(getResources().getDimensionPixelSize(R.dimen.width_of_button), 0, 0, 0);
		 TextView t = new TextView(this);
		 t.setText(newStore);
		 Button b = new Button(this);
		 b.setBackground(getResources().getDrawable(R.drawable.clear));
		 b.setGravity(Gravity.RIGHT);
		 
		 LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		 buttonParams.gravity=Gravity.RIGHT;
		 b.setLayoutParams(buttonParams);
		 b.setOnClickListener(deleteFromPersonalListener);
		 buttons.put(b, newStore);
		
		 //TODO: add button listener
		 textRow.addView(t, textRowParams);
		 textRow.addView(b, buttonParams);
		 
		 textRows.put(b, textRow);
		 s1.addView(textRow, 1+personalAllStores.size());
		 
	
		 
	 }
	 
	 HashMap<Button, String> buttons= new HashMap<Button, String>();
	 HashMap<Button, LinearLayout> textRows = new HashMap<Button, LinearLayout>();
 	 View.OnClickListener deleteFromPersonalListener = new View.OnClickListener() {
		    public void onClick(View v) {
		    	removeStore((Button)v);
		    	Log.i("debugging", "current button is" + buttons.get(v));
		    	
		    }
	 };
		    
	 private void showResults(String newText) {
		 Log.i("debugging", "showresults called");
	/*
	        // add some dummy stuff for localNow to test
	        ArrayList<String> localNow = new ArrayList<String>();
	    	localNow.add("apple");
	    	localNow.add("cabbage");
	    	localNow.add("bananas"); 
	    	localNow.add("cantaloupe");
	    	localNow.add("melon");
	*/    	
	    	ArrayList<String> result = new ArrayList<String>();
	    	ArrayList<String> allFood = new ArrayList<String>();
	    	String[] allFoodArray={"apples", "oranges", "pears"};
	    	for (String s: allFoodArray){
	    		allFood.add(s);
	    	}
	    	if (!newText.equals("")) {
		    	for(int i = 0; i < allFood.size(); i++) {
		    		String tmp = allFood.get(i).toLowerCase();
		    		if (tmp.contains(newText.toLowerCase())) {
		    			Log.i("debugging", "temp contains new text loop");
		    			result.add(tmp);
		    		}
		    	}
	    	}
	       	ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, result);
	    	listAllStores.setAdapter(myAdapter);


	    	listAllStores.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					String currentItem =(String) ((TextView)arg1).getText();
			
					addStore(currentItem);						
	        }
	    	});
	 }

	 
	 
	 SearchView currentSearchView;
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
					//nextButton.setBackgroundColor(getResources().getColor(R.color.ButtonRed));
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
					//nextButton.setBackgroundColor(getResources().getColor(R.color.ButtonGreen));
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
