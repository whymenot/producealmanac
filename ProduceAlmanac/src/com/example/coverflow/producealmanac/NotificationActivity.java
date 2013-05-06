package com.example.coverflow.producealmanac;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
import android.widget.Toast;

import com.example.coverflow.R;

public class NotificationActivity extends Activity{
	final int SEARCH = 14;
	ArrayList<View> allViews= new ArrayList<View>();
	HashMap<View, View> subLayouts= new HashMap<View, View>();
	HashMap<View, LinearLayout.LayoutParams> layoutParams = new HashMap<View, LinearLayout.LayoutParams>();
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
	Button save;
	
	SharedPreferences settings;
	// pastSaved_allNew,
	//	format : true false for each, real example -> "true:false"
	// pastSaved_searchAdded 
	//	format : strings concatenated with ":", real example -> "apples,pears"
	String pastSaved_allNew = "";
	String pastSaved_searchAdded = "";

	 protected void onCreate(final Bundle savedInstanceState) {

	    	super.onCreate(savedInstanceState);
	    	setContentView(R.layout.notifications);
	    	masterView = (LinearLayout) findViewById(R.id.master);
	    	save = (Button) findViewById(R.id.save);
/*	    	final ActionBar ab = getActionBar();
	        ab.setDisplayShowHomeEnabled(false);
	        ab.setDisplayShowTitleEnabled(false);     
	        final LayoutInflater inflater = (LayoutInflater)getSystemService("layout_inflater");
	        View view = inflater.inflate(R.layout.action_bar_edit_mode,null); 
	        ab.setCustomView(view);
	        ab.setDisplayShowCustomEnabled(true);
	        save = (Button) findViewById(R.id.save);
	        ab.setBackgroundDrawable(new ColorDrawable(Color.GRAY));
	        save.setBackgroundColor(Color.GRAY);
*/	    	loadSavedValues();
	    	initializeButtons();

	 }
	// this method should be called once in onCreate().
		// load saved values from internal storage.
		public void loadSavedValues() {
			settings = getApplicationContext().getSharedPreferences("producealmanac", MODE_PRIVATE);
			pastSaved_allNew = settings.getString("notification_saved_allNew", "");
			pastSaved_searchAdded = settings.getString("notification_saved_searchAdded", "");

			/* test purpose..
			pastSaved_allNew = "false:true";
			pastSaved_searchAdded = "apples:oranges:";
			*/
		}


		// this method is to saved all check boxes and search result settings.
		// shuld be called when user clicks "save" or something.
		public void saveAllValues() {
			String toSave_allNew = "";
			String toSave_searchAdded = "";

			//save two check boxes (allStoresFruit, allStoresVegetable) status
			toSave_allNew += allStoresFruit.isChecked()?"true":"false";
			toSave_allNew += ":";
			toSave_allNew += allStoresVegetable.isChecked()?"true":"false";

			// starting with 2, since index 0and1 are for allStoresFruit, allStoresVegetable.
			for (int i = 2; i < s1.getChildCount()-2; i++) {
				toSave_searchAdded += ((TextView)((LinearLayout)s1.getChildAt(i)).getChildAt(0)).getText();
				toSave_searchAdded += ":";
			}

			// finally, save into internal storage.
			SharedPreferences.Editor editor = settings.edit();
	    	editor.putString("notification_saved_allNew", toSave_allNew);
	    	editor.putString("notification_saved_searchAdded", toSave_searchAdded);
	    	editor.commit();
            Toast.makeText(this, "Notification settings saved!",
                    Toast.LENGTH_SHORT).show();
		}
	 
	 
	 public void  setSearchViewIcon(){
	 SearchView mSearchView = ((SearchView)findViewById(SEARCH));
	 Log.i("debugging", "search view is: " + mSearchView);/*
	    try
	    {
	        Field searchField = SearchView.class.getDeclaredField("mSearchButton");
	        searchField.setAccessible(true);
	        ImageView searchBtn = (ImageView)searchField.get(mSearchView);
	        searchBtn.setImageResource(R.drawable.navigation_content_new);
	        searchField = SearchView.class.getDeclaredField("mSearchPlate");
	        searchField.setAccessible(true);
	       	    }
	    catch (NoSuchFieldException e)
	    {
	    } catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	   
	 }
	 
	 LinearLayout s1;	 
	 LinearLayout checkBoxRow1;
	 LinearLayout allStoresContainer;
	 public void initializeButtons(){
		//android:icon="@android:drawable/presence_offline"
		 //ALLSTORES
		 Button b = (Button) findViewById(R.id.buttonAllStores);
		 row1 = (LinearLayout) findViewById(R.id.row_all_stores);
		 s1 = new LinearLayout(this);
		 allStoresFruit = new CheckBox(this);
		 allStoresVegetable = new CheckBox(this);
		 searchAllStores = new SearchView(this);
		 searchAllStores.setId(SEARCH);
		 setSearchViewIcon();
		 
		 listAllStores = new ListView(this);
		 initializeCheckBoxes(s1, allStoresFruit, allStoresVegetable, row1, b, searchAllStores);
		 initializeSearch(searchAllStores);
		 
		 
		 
		 
		 
		 //BERKELEY BOWL
	 }
	 
	 public void initializeCheckBoxes(LinearLayout layout, CheckBox fruitCheckBox, CheckBox vegetableCheckBox, LinearLayout row, Button button, SearchView search){
		 //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
			     //LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

		 //layoutParams.setMargins(getResources().getDimensionPixelSize(R.dimen.width_of_button), 0, 0, 0);
	 
		 LinearLayout.LayoutParams subLayoutParams = new LinearLayout.LayoutParams(
			     LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

		 subLayoutParams.setMargins(getResources().getDimensionPixelSize(R.dimen.width_of_button), 10, 20, 0);
		 
		 LinearLayout.LayoutParams titleLayoutParams = new LinearLayout.LayoutParams(
			     LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		
		 titleLayoutParams.setMargins(20, 20, 20, 0);
		 layout.setBackgroundColor(Color.WHITE);
		 layout.setOrientation(LinearLayout.VERTICAL);
		 fruitCheckBox.setText("all new fruits");
		 fruitCheckBox.setButtonDrawable(getResources().getDrawable(R.drawable.custom_checkbox));
		 fruitCheckBox.setTextSize(30);
		 fruitCheckBox.setPadding(30, 30, 0, 0);
		 fruitCheckBox.setGravity(Gravity.BOTTOM);
		 fruitCheckBox.setTextColor(getResources().getColor(R.color.TextGrey));
		 
		 
		 
		 
		 vegetableCheckBox.setText("all new vegetables");
		 vegetableCheckBox.setButtonDrawable(getResources().getDrawable(R.drawable.custom_checkbox));
		 vegetableCheckBox.setTextSize(30);
		 vegetableCheckBox.setPadding(30, 30, 0, 0);
		 vegetableCheckBox.setGravity(Gravity.BOTTOM);
		 vegetableCheckBox.setTextColor(getResources().getColor(R.color.TextGrey));
		 
		 
		 // added for handling saved check box settings.
		 String pastSaved_checkBox[] = pastSaved_allNew.split(":");
		 if (pastSaved_checkBox.length == 2) {
			 fruitCheckBox.setChecked(pastSaved_checkBox[0].equals("true")?true:false);
			 vegetableCheckBox.setChecked(pastSaved_checkBox[1].equals("true")?true:false);
		}
		 
		 
		 layout.addView(fruitCheckBox);
		 layout.addView(vegetableCheckBox);
		 
		// added for handling previously added by search.
				 String pastSaved_search[] = pastSaved_searchAdded.split(":");
				 for (int i = 0; i < pastSaved_search.length; i++) {
					 if (!pastSaved_search[i].equals("")) addStore(pastSaved_search[i]);
				 }
		 layout.addView(search);
		 layout.addView(listAllStores);
		 
		 subLayouts.put(button, layout);
		 allViews.add(row);
		 layoutParams.put(row, titleLayoutParams);
		 layoutParams.put(layout, subLayoutParams);
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
					nextButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.navigation_collapse));
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
					nextButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.navigation_expand));
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
				masterView.addView(v, layoutParams.get(v));
			}
		}

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.notification, menu);
			return true;
		}
		
	    public boolean onOptionsItemSelected(MenuItem item) {
			
	        switch (item.getItemId()) { //basic structure borrowed from Kate's drawing app in section
	        case R.id.notification_button_save:
	              Toast.makeText(this, "Saving notification settings",
	                          Toast.LENGTH_SHORT).show();
	              saveAllValues();
	              finish();
	              return true;
	       default:
	              return super.onOptionsItemSelected(item);
	        }
	      }
}
