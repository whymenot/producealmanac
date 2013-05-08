package com.example.coverflow.producealmanac;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

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
	LinearLayout row3;
	LinearLayout row4;
	LinearLayout row5;
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
	
	
	SharedPreferences settings;
	// pastSaved_allNew,
	//	format : true false for each, real example -> "true:false"
	// pastSaved_searchAdded 
	//	format : strings concatenated with ":", real example -> "apples,pears"
	String pastSaved_allNew = "";
	String pastSaved_berkeleyBowlNew = "";
	String pastSaved_yasaiNew = "";
	String pastSaved_traderJoesNew = "";
	String pastSaved_safewayNew = "";
	
	String pastSaved_searchAddedAll = "";
	String pastSaved_searchAddedBerkeleyBowl = "";
	String pastSaved_searchAddedYasai = "";
	String pastSaved_searchAddedTraderJoes = "";
	String pastSaved_searchAddedSafeway = "";

	 protected void onCreate(final Bundle savedInstanceState) {

	    	super.onCreate(savedInstanceState);
	    	setContentView(R.layout.notifications);
	    	masterView = (LinearLayout) findViewById(R.id.master);
	    	loadSavedValues();
	    	initializeButtons();

	 }
	// this method should be called once in onCreate().
		// load saved values from internal storage.
		public void loadSavedValues() {
			settings = getApplicationContext().getSharedPreferences("producealmanac", MODE_PRIVATE);
			pastSaved_allNew = settings.getString("notification_saved_allNew", "");
			pastSaved_berkeleyBowlNew = settings.getString("notification_saved_berkeleyBowlNew", "");
			pastSaved_yasaiNew = settings.getString("notification_saved_yasaiNew", "");
			pastSaved_traderJoesNew = settings.getString("notification_saved_traderJoesNew", "");
			pastSaved_safewayNew = settings.getString("notification_saved_safewayNew", "");
			
			pastSaved_searchAddedAll = settings.getString("notification_saved_searchAddedAll", "");
			pastSaved_searchAddedBerkeleyBowl = settings.getString("notification_saved_searchAddedBerkeleyBowl", "");
			pastSaved_searchAddedYasai = settings.getString("notification_saved_searchAddedYasai", "");
			pastSaved_searchAddedTraderJoes = settings.getString("notification_saved_searchAddedTraderJoes", "");
			pastSaved_searchAddedSafeway = settings.getString("notification_saved_searchAddedSafeway", "");

			/* test purpose..
			pastSaved_allNew = "false:true";
			pastSaved_searchAdded = "apples:oranges:";
			*/
		}


		// this method is to saved all check boxes and search result settings.
		// shuld be called when user clicks "save" or something.
		public void saveAllValues() {
			String toSave_allNew = "";
			String toSave_berkeleyBowlNew = "";
			String toSave_yasaiNew ="";
			String toSave_traderJoesNew = "";
			String toSave_safewayNew = "";
			
			String toSave_searchAddedAll = "";
			String toSave_searchAddedBerkeleyBowl = "";
			String toSave_searchAddedYasai = "";
			String toSave_searchAddedTraderJoes = "";
			String toSave_searchAddedSafeway = "";
			

			//save two check boxes (allStoresFruit, allStoresVegetable) status
			toSave_allNew += allStoresFruit.isChecked()?"true":"false";
			toSave_allNew += ":";
			toSave_allNew += allStoresVegetable.isChecked()?"true":"false";

			toSave_berkeleyBowlNew += berkeleyBowlFruit.isChecked()?"true":"false";
			toSave_berkeleyBowlNew += ":";
			toSave_berkeleyBowlNew += berkeleyBowlVegetable.isChecked()?"true":"false";

			toSave_yasaiNew += yasaiFruit.isChecked()?"true":"false";
			toSave_yasaiNew += ":";
			toSave_yasaiNew += yasaiVegetable.isChecked()?"true":"false";

			toSave_traderJoesNew += traderJoesFruit.isChecked()?"true":"false";
			toSave_traderJoesNew += ":";
			toSave_traderJoesNew += traderJoesVegetable.isChecked()?"true":"false";

			toSave_safewayNew += safewayFruit.isChecked()?"true":"false";
			toSave_safewayNew += ":";
			toSave_safewayNew += safewayVegetable.isChecked()?"true":"false";

			// starting with 2, since index 0and1 are for allStoresFruit, allStoresVegetable.
			for (int i = 2; i < s1.getChildCount()-2; i++) {
				toSave_searchAddedAll += ((TextView)((LinearLayout)s1.getChildAt(i)).getChildAt(0)).getText().toString().replace("new ", "");
				toSave_searchAddedAll += ":";
			}

			for (int i = 2; i < s2.getChildCount()-2; i++) {
				toSave_searchAddedBerkeleyBowl += ((TextView)((LinearLayout)s2.getChildAt(i)).getChildAt(0)).getText().toString().replace("new ", "");
				toSave_searchAddedBerkeleyBowl += ":";
			}

			for (int i = 2; i < s3.getChildCount()-2; i++) {
				toSave_searchAddedYasai += ((TextView)((LinearLayout)s3.getChildAt(i)).getChildAt(0)).getText().toString().replace("new ", "");
				toSave_searchAddedYasai += ":";
			}

			for (int i = 2; i < s4.getChildCount()-2; i++) {
				toSave_searchAddedTraderJoes += ((TextView)((LinearLayout)s4.getChildAt(i)).getChildAt(0)).getText().toString().replace("new ", "");
				toSave_searchAddedYasai += ":";
			}

			for (int i = 2; i < s5.getChildCount()-2; i++) {
				toSave_searchAddedSafeway += ((TextView)((LinearLayout)s5.getChildAt(i)).getChildAt(0)).getText().toString().replace("new ", "");
				toSave_searchAddedSafeway += ":";
			}

			// finally, save into internal storage.
			SharedPreferences.Editor editor = settings.edit();
	    	editor.putString("notification_saved_allNew", toSave_allNew);
	    	editor.putString("notification_saved_berkeleyBowlNew", toSave_berkeleyBowlNew);
	    	editor.putString("notification_saved_yasaiNew", toSave_yasaiNew);
	    	editor.putString("notification_saved_traderJoesNew", toSave_traderJoesNew);
	    	editor.putString("notification_saved_safewayNew", toSave_safewayNew);
	    	
	    	editor.putString("notification_saved_searchAddedAll", toSave_searchAddedAll);
	    	editor.putString("notification_saved_searchAddedBerkeleyBowl", toSave_searchAddedBerkeleyBowl);
	    	editor.putString("notification_saved_searchAddedYasai", toSave_searchAddedYasai);
	    	editor.putString("notification_saved_searchAddedTraderJoes", toSave_searchAddedTraderJoes);
	    	editor.putString("notification_saved_searchAddedSafeway", toSave_searchAddedSafeway);
	    	editor.commit();
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
	 LinearLayout s2;
	 LinearLayout s3;
	 LinearLayout s4;
	 LinearLayout s5;
	 LinearLayout s6;
	 

	 CheckBox allStoresCheckBox;
	 CheckBox berkeleyBowlCheckBox;
	 CheckBox yasaiCheckBox;
	 CheckBox traderJoesCheckBox;
	 CheckBox safewayCheckBox;
	 
	 SearchView searchBerkeleyBowl;
	 SearchView searchYasai;
	 SearchView searchTraderJoes;
	 SearchView searchSafeway;
	 
	 ListView listBerkeleyBowl;
	 ListView listYasai;
	 ListView listTraderJoes;
	 ListView listSafeway;
	 
	 public void initializeButtons(){
		//android:icon="@android:drawable/presence_offline"
		 //ALLSTORES
		 allStoresCheckBox = (CheckBox) findViewById(R.id.buttonAllStores);
		 row1 = (LinearLayout) findViewById(R.id.row_all_stores);
		 row1.setTag("all");
		 s1 = new LinearLayout(this);
		 allStoresFruit = new CheckBox(this);
		 allStoresVegetable = new CheckBox(this);
		 searchAllStores = new SearchView(this);
		 searchAllStores.setId(SEARCH);
		 setSearchViewIcon();
		 listAllStores = new ListView(this);
		 initializeCheckBoxes(s1, allStoresFruit, allStoresVegetable, row1, allStoresCheckBox, searchAllStores, listAllStores);
		
		 lists.put(searchAllStores, listAllStores);
		 initializeSearch(searchAllStores);

		 //BERKELEY BOWL
		 berkeleyBowlCheckBox = (CheckBox) findViewById(R.id.buttonBerkeleyBowl);
		 row2 = (LinearLayout) findViewById(R.id.row_berkeley_bowl);
		 row2.setTag("berkeleyBowl");
		 s2 = new LinearLayout(this);
		 berkeleyBowlFruit = new CheckBox(this);
		 berkeleyBowlVegetable = new CheckBox(this);
		 searchBerkeleyBowl = new SearchView(this);
		 listBerkeleyBowl = new ListView(this);
		 initializeCheckBoxes(s2, berkeleyBowlFruit, berkeleyBowlVegetable, row2, berkeleyBowlCheckBox, searchBerkeleyBowl, listBerkeleyBowl);
		
		 lists.put(searchBerkeleyBowl, listBerkeleyBowl);
		 initializeSearch(searchBerkeleyBowl);
		 //YASAI
		 yasaiCheckBox = (CheckBox) findViewById(R.id.buttonYasai);
		 row3 = (LinearLayout) findViewById(R.id.row_yasai);
		 row3.setTag("yasai");
		 s3 = new LinearLayout(this);
		 yasaiFruit = new CheckBox(this);
		 yasaiVegetable = new CheckBox(this);
		 searchYasai = new SearchView(this);
		 listYasai = new ListView(this);
		 initializeCheckBoxes(s3, yasaiFruit, yasaiVegetable, row3, yasaiCheckBox, searchYasai, listYasai);
		
		 lists.put(searchYasai, listYasai);
		 initializeSearch(searchYasai);
		 //TRADER JOES
		 traderJoesCheckBox = (CheckBox) findViewById(R.id.buttonTraderJoes);
		 row4 = (LinearLayout) findViewById(R.id.row_trader_joes);
		 row4.setTag("traderJoes");
		 s4 = new LinearLayout(this);
		 traderJoesFruit = new CheckBox(this);
		 traderJoesVegetable = new CheckBox(this);
		 searchTraderJoes = new SearchView(this);
		 listTraderJoes = new ListView(this);
		 initializeCheckBoxes(s4, traderJoesFruit, traderJoesVegetable, row4, traderJoesCheckBox, searchTraderJoes, listTraderJoes);
		 
		 lists.put(searchTraderJoes, listTraderJoes);
		 initializeSearch(searchTraderJoes);
		 //Safeway
		 safewayCheckBox = (CheckBox) findViewById(R.id.buttonSafeway);
		 row5 = (LinearLayout) findViewById(R.id.row_safeway);
		 row5.setTag("safeway");
		 s5 = new LinearLayout(this);
		 safewayFruit = new CheckBox(this);
		 safewayVegetable = new CheckBox(this);
		 searchSafeway = new SearchView(this);
		 listSafeway = new ListView(this);
		 initializeCheckBoxes(s5, safewayFruit, safewayVegetable, row5, safewayCheckBox, searchSafeway, listSafeway);
		 lists.put(searchSafeway, listSafeway);
		 initializeSearch(searchSafeway);
	 }
	 
	 public void initializeCheckBoxes(LinearLayout layout, CheckBox fruitCheckBox, CheckBox vegetableCheckBox, LinearLayout row, CheckBox button, SearchView search, ListView list){
		 //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
			     //LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

		 //layoutParams.setMargins(getResources().getDimensionPixelSize(R.dimen.width_of_button), 0, 0, 0);
		 button.setButtonDrawable(getResources().getDrawable(R.drawable.expand));
		 LinearLayout.LayoutParams subLayoutParams = new LinearLayout.LayoutParams(
			     LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

		 subLayoutParams.setMargins(20, 10, 20, 0);
		 
		 LinearLayout.LayoutParams titleLayoutParams = new LinearLayout.LayoutParams(
			     LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		
		 titleLayoutParams.setMargins(20, 20, 20, 0);
		 layout.setBackgroundColor(Color.WHITE);
		 layout.setOrientation(LinearLayout.VERTICAL);
		 fruitCheckBox.setText("all new fruits");
		 fruitCheckBox.setButtonDrawable(getResources().getDrawable(R.drawable.custom_checkbox));
		 fruitCheckBox.setTextSize(getResources().getDimension(R.dimen.check_box_text_size));
		 fruitCheckBox.setPadding(30, 30, 0, 0);
		 fruitCheckBox.setGravity(Gravity.BOTTOM);
		 fruitCheckBox.setTextColor(getResources().getColor(R.color.TextGrey));
		 
		 
		 
		 
		 vegetableCheckBox.setText("all new vegetables");
		 vegetableCheckBox.setButtonDrawable(getResources().getDrawable(R.drawable.custom_checkbox));
		 vegetableCheckBox.setTextSize(getResources().getDimension(R.dimen.check_box_text_size));
		 vegetableCheckBox.setPadding(30, 30, 0, 0);
		 vegetableCheckBox.setGravity(Gravity.BOTTOM);
		 vegetableCheckBox.setTextColor(getResources().getColor(R.color.TextGrey));
		 
		 // for saved notification data.
		 handleSavedCheckBox(row, fruitCheckBox, vegetableCheckBox);
		 
		 
		 layout.addView(fruitCheckBox);
		 layout.addView(vegetableCheckBox);
		 
		 // for saved notification data.
		 handleSavedSearch(row);
				 
		 search.setPadding(0,  20, 20, 20);
		 layout.addView(search);
		 layout.addView(list);
		 
		 subLayouts.put(button, layout);
		 allViews.add(row);
		 layoutParams.put(row, titleLayoutParams);
		 layoutParams.put(layout, subLayoutParams);
		 
		 button.setOnCheckedChangeListener(buttonListener);
		 expanded.put(button, false);
	 }
	 
	 public void handleSavedCheckBox(LinearLayout row, CheckBox fruitCheckBox, CheckBox vegetableCheckBox) {
		 
		 String savedString = null;
		 
		 String rowTag = (String)row.getTag();
		 
		 if (rowTag.equals("all")) savedString = pastSaved_allNew;
		 else if (rowTag.equals("berkeleyBowl")) savedString = pastSaved_berkeleyBowlNew;
		 else if (rowTag.equals("yasai")) savedString =pastSaved_yasaiNew;
		 else if (rowTag.equals("traderJoes")) savedString = pastSaved_traderJoesNew;
		 else if (rowTag.equals("safeway")) savedString = pastSaved_safewayNew;
		 
		 if (savedString == null) return;
		 
		 // added for handling saved check box settings.
		 String pastSaved_checkBox[] = savedString.split(":");
		 if (pastSaved_checkBox.length == 2) {
			 fruitCheckBox.setChecked(pastSaved_checkBox[0].equals("true")?true:false);
			 vegetableCheckBox.setChecked(pastSaved_checkBox[1].equals("true")?true:false);
		}
	 }
	 
	 public void handleSavedSearch(LinearLayout row) {
		 String savedString = null;
		 
		 String rowTag = (String)row.getTag();
		 
		 if (rowTag.equals("all")) savedString = pastSaved_searchAddedAll;
		 else if (rowTag.equals("berkeleyBowl")) savedString = pastSaved_searchAddedBerkeleyBowl;
		 else if (rowTag.equals("yasai")) savedString =pastSaved_searchAddedYasai;
		 else if (rowTag.equals("traderJoes")) savedString = pastSaved_searchAddedTraderJoes;
		 else if (rowTag.equals("safeway")) savedString = pastSaved_searchAddedSafeway;
		 
		 if (savedString == null) return;
		 
		 // added for handling previously added by search.
		 String pastSaved_search[] = pastSaved_searchAddedAll.split(":");
		 for (int i = 0; i < pastSaved_search.length; i++) {
			 if (!pastSaved_search[i].equals("")) addStore(pastSaved_search[i]);
		 }
		 
	 }
	 
	 
	 
	 
HashMap<SearchView, ListView> lists = new HashMap<SearchView, ListView>();	 
	 public void initializeSearch(final SearchView search){
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
	            	showResults(newText, lists.get(search));
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
		 
		 textRow.setWeightSum(1);
		 //textRow.setLayoutParams(layoutParams);
		 Log.i("debugging", "after assigning params");
		 textRow.setOrientation(LinearLayout.HORIZONTAL);
		 
		 LinearLayout.LayoutParams textRowParams = new LinearLayout.LayoutParams(
			     LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, .97f);
		
		 textRowParams.setMargins(115, 0, 0, 0);
		 TextView t = new TextView(this);
		 t.setTextSize(getResources().getDimension(R.dimen.check_box_text_size));
		 t.setTextColor(getResources().getColor(R.color.TextGrey));
		 t.setGravity(Gravity.LEFT);
		 t.setText("new " + newStore);

		 
		 LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
			     LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, .03f);
		

		 Button b = new Button(this);
		 b.setBackground(getResources().getDrawable(R.drawable.navigation_cancel));
		 b.setGravity(Gravity.RIGHT);
		 
		 textRow.setPadding(0, 20, 0, 0);
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
		    
	 private void showResults(String newText, ListView list) {
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
	    	list.setAdapter(myAdapter);
	    	list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					String currentItem =(String) ((TextView)arg1).getText();
			
					addStore(currentItem);						
	        }
	    	});
	 }

	 
	 
	 SearchView currentSearchView;
	 	CompoundButton.OnCheckedChangeListener buttonListener = new CompoundButton.OnCheckedChangeListener(){
			
			
	 		public void onCheckedChanged(CompoundButton v,
					boolean expanding) {
	 			Log.i("claire", "calling on check changed");
				
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
	              saveAllValues();
	              finish();
	              return true;
	       default:
	              return super.onOptionsItemSelected(item);
	        }
	      }
}
