package com.example.coverflow.producealmanac;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coverflow.R;
import com.example.coverflow.ResourceImageAdapter;
import com.example.coverflow.producealmanac.MultiSpinner.MultiSpinnerListener;

/**
 * The Class CoverFlowTestingActivity.
 */
public class CoverFlowTestingActivity extends Activity {

	ArrayList<Item> activeItems; // is this needed?
	ArrayList<String> localNow; // used for searching, filters not relevant
	ArrayList<String> localOut; // used for searching, local out of season
	boolean populated=false;
	
	public ArrayList<ArrayList<Item>> itemsByFilter = new ArrayList<ArrayList<Item>>();
	
	//public String searchTerms = "";
	public ArrayList<String> activeFilters;
	public ArrayList<Store> activeStores = null;
	
	
	TextView textView;
	ResourceImageAdapter myAdapter;
	
	//search/filter terms

	
	
	//Static filter strings
	public final static String BERRIES = "Berries";
	public final static String ROOTS  = "Root Vegetables";
	public final static String LEAFY = "Leafy Greens";
	public final static String CITRUS = "Citrus Fruits";
	public final static String HERBS = "Fresh Herbs";
	
	public final static String[] FILTERS = {BERRIES,ROOTS,LEAFY,CITRUS,HERBS};
	
	
	
	// for gridview
	ImageAdapter myImageAdapter;

	// for search
    private ListView mListView;
    private SearchView searchView;

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {

    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.main);
    	
    	// set search functionality
    	
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) findViewById(R.id.searchView);
        
        
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true); // Do not iconify the widget; expand it by default
        
        //final SearchView.OnQueryTextListener queryTextListener = ; 
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() { 
            @Override 
            public boolean onQueryTextChange(String newText) { 
                // Do something 
            	showResults(newText);
            	System.out.println("showResults DONE");
                return true; 
            } 

            @Override 
            public boolean onQueryTextSubmit(String query) { 
                // Do something 
            	searchView.setIconified(true); 
                return true; 
            } 
        });
        //searchView.setOnCloseListener(this);
 
        mListView = (ListView) findViewById(R.id.list);

		if(!populated){
			populated=true;
			populateMap();
		}
		
		createAllItems();
		
		
		getClosestStores();
				
		this.activeFilters = getActiveFilters();
		this.localNow = getLocalNow();
		this.localOut = getLocalOut();
		
		//activeItems = getItemsNow();
		
		this.itemsByFilter = getItemsByFilter();

		//done initializing backend data


		showUpdatedItems();

        
        final MultiSpinner multispinner = (MultiSpinner) findViewById(this.getResources().getIdentifier("SpinnerCollegues", "id", "com.example.coverflow"));
      
        List<String> spinnerItems = new ArrayList<String>();
        spinnerItems.add(BERRIES);
        spinnerItems.add(ROOTS);
        spinnerItems.add(LEAFY);
        spinnerItems.add(CITRUS);
        spinnerItems.add(HERBS);

        multispinner.setItems(spinnerItems, "filter by category", new MultiSpinnerListener() {
        	public void onItemsSelected(boolean[] selected) {
        		// what happens when selected.
        		System.out.println("What's selected ? ");
        		ArrayList<String> selectedFilter = new ArrayList<String>();
        		for (int i = 0; i < selected.length; i++) {
        			if (selected[i] == true) {
        				System.out.print(i + " , ");
        				selectedFilter.add(FILTERS[i]);
        			}
        		}
        		setFilters(selectedFilter);
        		updateItemsByFilter();
        		System.out.println();
        	}
        });
        multispinner.setPrompt("FILTER");
    }
    private ArrayList<ArrayList<Item>> getItemsByFilter() {
		/**Based on the active filters set, create a list of items for each
		 * filter. The outer list being returned should always map 1 to 1 with
		 * the FILTERS array, but the inner list of items can be any size (even empty).
		 */
    	ArrayList<ArrayList<Item>> filteredItems = new ArrayList<ArrayList<Item>>();
    	ArrayList<Item> items;
    	for (int i =0; i < FILTERS.length; i++){
    		items = getActiveFilterList(i);
    		filteredItems.add(items);    		
    	}
    	return filteredItems;
	}

	@SuppressWarnings("unchecked")
	private ArrayList<Item> getActiveFilterList(int filterIndex) {
		/**Check if filter index is active. If active, then 
		 * return a list of all items in both this group
		 * and localNow. if inactive, return an empty list.
		 */
		ArrayList<Item> items = new ArrayList<Item>();
		Item item;
		if (activeFilters.contains(FILTERS[filterIndex])){
			for (String name: localNow){
				item = Item.itemMap.get(name);
				if (item.group==FILTERS[filterIndex]){
					items.add(item);
				}
			}
			
		}
		Collections.sort(items);
		return items;
	}

	//don't think we need this method, keep for now though
	private ArrayList<Item> getItemsNow() {
		// TODO Auto-generated method stub
		return null;
	}

	private ArrayList<String> getLocalOut() {
		/**This method relies on this.localNow being up to date and
		 * returns a list of the names of items that are inactive in
		 * at least one store, and active in NO stores.
		 */
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<Item> items;
		for (Store store : this.activeStores){
			items = store.getAllInactiveItems();
			for (Item i : items){
				if((!names.contains(i.name))  && (! this.localNow.contains(i.name))){
					names.add(i.name);
				}
			}
		}
		return names;
	}

	private ArrayList<String> getLocalNow() {
		/**Iterate through each store in activeStores, append item name to
		 * list if it is not already in the list, then return names.
		 */
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<Item> items;
		for (Store store : this.activeStores){
			items = store.getAllActiveItems();
			for (Item i : items){
				if(! names.contains(i.name)){
					names.add(i.name);
				}
			}
		}
		return names;
	}

	private ArrayList<String> getActiveFilters() {
		//By default, return all filters
		return new ArrayList<String>(Arrays.asList(FILTERS));
	}

	private void getClosestStores() {
		/**Gets a list of the closest Stores and populates
		 * the store's active and inactive lists.
		 */
		
		// get data from GPS if possible
		
		this.activeStores = new ArrayList<Store>();
		activeStores.add(new Store("Berkeley Bowl"));
		activeStores.add(new Store("Yasai Market"));
		activeStores.add(new Store("Safeway - College Ave."));
		activeStores.add(new Store("Trader Joes - University Ave."));
		activeStores.add(new Store("Whole Foods - Telegraph Ave."));	
		Store.buildMaps();
	}
    
    

    public void showUpdatedItems() {
		LinearLayout gridLinearLayout = (LinearLayout) findViewById(R.id.grid_linearlayout);
		// delete previous views under this
		gridLinearLayout.removeAllViews();    	


		// create gridviews dynamically...
		for (int i = 0; i < FILTERS.length; i++) {
			final ArrayList<Item> items = itemsByFilter.get(i);

			// skip if size of items is zero.
			if (items.size() == 0) continue;

			TextView txtView = new TextView(this);
			//should be different per each one
			txtView.setText(FILTERS[i]);
			txtView.setTextColor(getResources().getColor(R.color.Brown));
			txtView.setTextSize(40);
			gridLinearLayout.addView(txtView);

			GridView gridview = new GridView(this);
			gridview.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, 230 * ((int) Math.ceil(items.size()/3.0))));
			gridview.setColumnWidth(220);
			gridview.setGravity(Gravity.CENTER);
			gridview.setNumColumns(GridView.AUTO_FIT);
			gridview.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);

	        myImageAdapter = new ImageAdapter(this);
	        gridview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {

					System.out.println("clicked item : " + arg2);
					Intent intent = new Intent(CoverFlowTestingActivity.this, DetailActivity.class);
					intent.putExtra("name", items.get(arg2).name);
					startActivity(intent);
				}

	        });

			gridLinearLayout.addView(gridview);
	        gridview.setAdapter(myImageAdapter);

			for(int j = 0; j < items.size(); j++) {
				//System.out.println(currentItems.get(i).name);
				myImageAdapter.add(getResources().getIdentifier(items.get(j).name + "_coverflow", "drawable", getPackageName()));
				//System.out.println(getResources().getIdentifier(currentItems.get(i).name + "_coverflow", "drawable", getPackageName()));
			}

		}
	}

	private void createAllMonths() {
    	/**Creates all Month instances...should only be called after
    	*  all Item instances have been created. Month Zero is "empty"
    	*  so 1->Jan, 2->Feb, ... 12->Dec
    	
    	for (int i=0; i < 13; i++){
    		months[i] = new Month(i);
    	}		
    	**/
	}

	private void createAllItems() {
		//@TODO We should not be adding EVERYTHING to currentItems, we should
		//just be creating item instances and copy the local items in another method
		
		
		//FIX BELOW
		new Item("artichoke");
		new Item("cabbage");
		new Item("celeriac");
		new Item("kale");
		new Item("leek");
		new Item("peas");
		new Item("turnip");

	}

	public void populateMap() {		
		
		
		//temporary... dummy group
		String group = "group";
		
		//@TODO include filter Strings as last argument to putEntry()
		putEntry(R.drawable.celeriac_detail, "artichokes are pretty", "anywhere", "red", "artichoke", LEAFY);
		putEntry(R.drawable.celeriac_detail, "cabbages are evil", "trashcan", "green", "cabbage", LEAFY);
		putEntry(R.drawable.celeriac_detail, "When peeled, celeriac's creamy white flesh resembles that of a turnip and tastes like a subtle blend of celery and parsley. This time of year, celeriac can be a perfect non-starch substitute for potatoes in a warming meal, and can be prepared in a similar way. It goes well with fresh green vegetables or salad and anything roasted or grilled.", "Celeriac can be stored for up to four months in the fridge.", "A ripe celeriac is firm with its peel intact.", "celeriac", ROOTS);
		putEntry(R.drawable.celeriac_detail, "kale is kind", "who knows", "green", "kale", LEAFY);
		putEntry(R.drawable.celeriac_detail, "leeks are...", "asasasd", "violet", "leek", ROOTS);
		putEntry(R.drawable.celeriac_detail, "peas are small", "asdasd", "invisible", "peas", BERRIES);
		putEntry(R.drawable.celeriac_detail, "turnips.. what are these?", "asdfasdf", "no idea", "turnip", CITRUS);
	}

	public void putEntry(int resID, String general, String storage, String ripe, String name, String group) {
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resID);
		ImageView imgView = new ImageView(this);
		imgView.setImageBitmap(bitmap);
		Object[] info = {general, storage, ripe, bitmap,group};
		Item.infoMap.put(name, info);
	}

	public void setFilters(ArrayList<String> filters){
		this.activeFilters= (ArrayList<String>) filters;
	}

	/**
	public void setMonth(int monthNum){
		this.currentMonth = this.months[monthNum];
	}
	**/



	public void updateItemsByFilter(){
		/** This method is called when a filter is toggled or when a store is selected / deselected.
		 * It updates this.itemsByFilter 
		 */
		this.itemsByFilter = getItemsByFilter();
		// UPDATE STORE INFO HERE
		// must update this.acvtiveStores
		showUpdatedItems();
	}

    public class ImageAdapter extends BaseAdapter {
        
        private Context mContext;
        ArrayList<Integer> itemList = new ArrayList<Integer>();
        
        public ImageAdapter(Context c) {
         mContext = c; 
        }
        
        void add(int resourceId){
         itemList.add(resourceId); 
        }

     @Override
     public int getCount() {
      return itemList.size();
     }

     @Override
     public Object getItem(int arg0) {
      // TODO Auto-generated method stub
      return null;
     }

     @Override
     public long getItemId(int position) {
      // TODO Auto-generated method stub
      return 0;
     }

     @Override
     public View getView(int position, View convertView, ViewGroup parent) {
      ImageView imageView;
            if (convertView == null) {  // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(220, 220));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            Bitmap bm = decodeSampledBitmapFromResource(itemList.get(position), 220, 220);

            imageView.setImageBitmap(bm);
            return imageView;
     }
     
     public Bitmap decodeSampledBitmapFromResource(int resourceId, int reqWidth, int reqHeight) {
      
      Bitmap bm = null;
      // First decode with inJustDecodeBounds=true to check dimensions
      final BitmapFactory.Options options = new BitmapFactory.Options();
      options.inJustDecodeBounds = true;
      BitmapFactory.decodeResource(getApplicationContext().getResources(), resourceId);
          
      // Calculate inSampleSize
      options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
          
      // Decode bitmap with inSampleSize set
      options.inJustDecodeBounds = false;
      bm = BitmapFactory.decodeResource(getApplicationContext().getResources(), resourceId); 
          
      return bm;   
     }
     
     public int calculateInSampleSize(
       
      BitmapFactory.Options options, int reqWidth, int reqHeight) {
      // Raw height and width of image
      final int height = options.outHeight;
      final int width = options.outWidth;
      int inSampleSize = 1;
      
      if (height > reqHeight || width > reqWidth) {
       if (width > height) {
        inSampleSize = Math.round((float)height / (float)reqHeight);    
       } else {
        inSampleSize = Math.round((float)width / (float)reqWidth);    
       }   
      }
      
      return inSampleSize;    
     }

    // methods for search
    }
    
    private void showResults(String newText) {
    	System.out.println("showResults is called!");
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
    	
    	if (!newText.equals("")) {
	    	for(int i = 0; i < localNow.size(); i++) {
	    		String tmp = localNow.get(i).toLowerCase();
	    		if (tmp.contains(newText.toLowerCase())) {
	    			result.add(tmp);
	    		}
	    	}
    	}
    	
    	ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, result);
    	mListView.setAdapter(myAdapter);


        mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				System.out.println("clicked item : " + arg2);
				Intent intent = new Intent(CoverFlowTestingActivity.this, DetailActivity.class);
				intent.putExtra("name", ((TextView) arg1).getText());
				startActivity(intent);
			}
        	
        });
    }
    /*
    public void showPopup(View v) {
        LayoutInflater inflater = (LayoutInflater) this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            PopupWindow pw = new PopupWindow(inflater.inflate(
                    R.layout.search, null, false), 300, 400, true);
            pw.showAtLocation(findViewById(R.id.searchView), Gravity.CENTER, 0,
                    0);
    }*/
}
