package com.example.coverflow.producealmanac;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coverflow.CoverFlow;
import com.example.coverflow.R;
import com.example.coverflow.ReflectingImageAdapter;
import com.example.coverflow.ResourceImageAdapter;
import com.example.coverflow.producealmanac.MultiSpinner.MultiSpinnerListener;

/**
 * The Class CoverFlowTestingActivity.
 */
public class CoverFlowTestingActivity extends Activity {

	//currentItems will be the items currently in season, use a hashmap with key=time of year
	ArrayList<Item> currentItems;
	boolean populated=false;
	TextView textView;
	ResourceImageAdapter myAdapter;
	
	//search/filter terms
	public String searchTerms = "";
	public ArrayList<String> activeFilters;
	public Month currentMonth;
	public Month[] months;
	//public Store activeStore = null;
	
	
	//Static filter strings
	public final static String BERRIES = "Berries";
	public final static String ROOTS  = "Root Vegetables";
	public final static String LEAFY = "Leafy Greens";
	public final static String CITRUS = "Citrus Fruits";
	public final static String HERBS = "Fresh Herbs";
	
	public final static String[] FILTERS = {BERRIES,ROOTS,LEAFY,CITRUS,HERBS};
	

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {

    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.main);

		if(!populated){
			populated=true;
			populateMap();
		}
		
		
		currentItems = new ArrayList<Item>();
		
		//@TODO GET CURRENT MONTH
		int monthNum = 5;
		
		this.activeFilters = new ArrayList<String>();
		
		currentItems = new ArrayList<Item>();
		months = new Month[13];
		
		createAllItems();
		createAllMonths();
		
		this.currentMonth = months[monthNum];
		//getAllItems() returns copy of ArrayList, OK to modify
		currentItems = currentMonth.getAllItems();

		//set all filters as active by default
		this.activeFilters = new ArrayList<String>(Arrays.asList(FILTERS));
		
		//done initializing backend data
		
		
		showUpdatedItems();
		

		//CoverFlow code below
		//System.out.println("MAIN ACTIVITY STARTED, printing just before COVERFLOW setup");
    	
//        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        // note resources below are taken using getIdentifier to allow importing
        // this library as library.
        final CoverFlow reflectingCoverFlow = (CoverFlow) findViewById(this.getResources().getIdentifier(
                "coverflowReflect", "id", "com.example.coverflow"));
        setupCoverFlow(reflectingCoverFlow, true);
        
        
        final MultiSpinner multispinner = (MultiSpinner) findViewById(this.getResources().getIdentifier("SpinnerCollegues", "id", "com.example.coverflow"));
        List<String> spinnerItems = new ArrayList<String>();
        spinnerItems.add(BERRIES);
        spinnerItems.add(ROOTS);
        spinnerItems.add(LEAFY);
        spinnerItems.add(CITRUS);
        spinnerItems.add(HERBS);
        spinnerItems.add(HERBS);
        spinnerItems.add(HERBS);
        spinnerItems.add(HERBS);
        spinnerItems.add(HERBS);
        spinnerItems.add(HERBS);
        spinnerItems.add(HERBS);
        spinnerItems.add(HERBS);
        spinnerItems.add(HERBS);
        spinnerItems.add(HERBS);
        spinnerItems.add(HERBS);
        spinnerItems.add(HERBS);
        spinnerItems.add(HERBS);
        spinnerItems.add(HERBS);
        multispinner.setItems(spinnerItems, "FILTER", new MultiSpinnerListener() {
        	public void onItemsSelected(boolean[] selected) {
        		// what happens when selected.
        		System.out.println("What's selected ? ");
        		for (int i = 0; i < selected.length; i++) {
        			if (selected[i] == true) System.out.print(i + " , ");
        		}
        		System.out.println();
        	}
        });
        multispinner.setPrompt("FILTER");
    }

    public void showUpdatedItems() {
		myAdapter = new ResourceImageAdapter(this);
		
		int resourceList[] = new int[currentItems.size()];
		
		for (int i = 0; i < currentItems.size(); i++) {
			System.out.println(currentItems.get(i).name);
			resourceList[i] = getResources().getIdentifier(currentItems.get(i).name + "_coverflow", "drawable", getPackageName());
			System.out.println(resourceList[i]);
		}
		myAdapter.setResources(resourceList);
		
	}

	private void createAllMonths() {
    	/**Creates all Month instances...should only be called after
    	*  all Item instances have been created. Month Zero is "empty"
    	*  so 1->Jan, 2->Feb, ... 12->Dec
    	**/
    	for (int i=0; i < 13; i++){
    		months[i] = new Month(i);
    	}		
	}

	private void createAllItems() {
		//@TODO We should not be adding EVERYTHING to currentItems, we should
		//just be creating item instances and copy the currentMonth's ArrayList
		
		
		//FIX BELOW
		currentItems.add(new Item("artichoke"));
		currentItems.add(new Item("cabbage"));
		currentItems.add(new Item("celeriac"));
		currentItems.add(new Item("kale"));
		currentItems.add(new Item("leek"));
		currentItems.add(new Item("peas"));
		currentItems.add(new Item("turnip"));
		
	}

	/**
     * Setup cover flow.
     * 
     * @param mCoverFlow
     *            the m cover flow
     * @param reflect
     *            the reflect
     */
    private void setupCoverFlow(final CoverFlow mCoverFlow, final boolean reflect) {
        BaseAdapter coverImageAdapter;
        if (reflect) {
            coverImageAdapter = new ReflectingImageAdapter(myAdapter);
        } else {
            coverImageAdapter = myAdapter;
        }
        mCoverFlow.setAdapter(coverImageAdapter);
        mCoverFlow.setSelection(0, true);
        setupListeners(mCoverFlow);
    }

    /**
     * Sets the up listeners.
     * 
     * @param mCoverFlow
     *            the new up listeners
     */
    private void setupListeners(final CoverFlow mCoverFlow) {
        mCoverFlow.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView< ? > parent, final View view, final int position, final long id) {
                //textView.setText("Item clicked! : " + id);
            }

        });
        mCoverFlow.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView< ? > parent, final View view, final int position, final long id) {
                //textView.setText("Item selected! : " + id);
            }

            @Override
            public void onNothingSelected(final AdapterView< ? > parent) {
                //textView.setText("Nothing clicked!");
            }
        });
        
        mCoverFlow.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				System.out.println("CLICKED : " + arg2 + " , " + arg3);
				
				Intent intent = new Intent(CoverFlowTestingActivity.this, DetailActivity.class);
				intent.putExtra("name", currentItems.get(arg2).name);
				startActivity(intent);
			}
        	
        });
    }
    
	public void populateMap() {	
		
		
		this.activeFilters = new ArrayList<String>();
		//@TODO add all filter Strings to activeFilters by default
		
		//temporary... dummy group
		String group = "group";
		
		//@TODO include filter Strings as last argument to putEntry()
		putEntry(R.drawable.celeriac_detail, "artichokes are pretty", "anywhere", "red", "artichoke", group);
		putEntry(R.drawable.celeriac_detail, "cabbages are evil", "trashcan", "green", "cabbage", group);
		putEntry(R.drawable.celeriac_detail, "When peeled, celeriac's creamy white flesh resembles that of a turnip and tastes like a subtle blend of celery and parsley. This time of year, celeriac can be a perfect non-starch substitute for potatoes in a warming meal, and can be prepared in a similar way. It goes well with fresh green vegetables or salad and anything roasted or grilled.", "Celeriac can be stored for up to four months in the fridge.", "A ripe celeriac is firm with its peel intact.", "celeriac", group);
		putEntry(R.drawable.celeriac_detail, "kale is kind", "who knows", "green", "kale", group);
		putEntry(R.drawable.celeriac_detail, "leeks are...", "asasasd", "violet", "leek", group);
		putEntry(R.drawable.celeriac_detail, "peas are small", "asdasd", "invisible", "peas", group);
		putEntry(R.drawable.celeriac_detail, "turnips.. what are these?", "asdfasdf", "no idea", "turnip", group);
	}
	
	public void putEntry(int resID, String general, String storage, String ripe, String name, String group) {
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resID);
		ImageView imgView = new ImageView(this);
		imgView.setImageBitmap(bitmap);
		Object[] info = {general, storage, ripe, bitmap,group};
		Item.infoMap.put(name, info);
	}
	
	@SuppressWarnings("unchecked")
	public void setFilters(ArrayList<String> filters){
		this.activeFilters= (ArrayList<String>) filters.clone();
	}
	
	public void setMonth(int monthNum){
		this.currentMonth = this.months[monthNum];
	}
	
	public void setSearchString(String characters){
		this.searchTerms = characters;
	}
	
	public void updateList(){
		this.currentItems = this.currentMonth.getAllItems();
		for (Item i : this.currentItems){
			//if statement checks the filters, else checks search terms
			if (! this.activeFilters.contains(i.group)){
				if( ! this.currentItems.remove(i)){
					System.out.println("Trying to remove item not in list... ERROR, check item: " + i.toString());
				}
			}
			else{
				if (! i.name.contains(this.searchTerms)){
					if( ! this.currentItems.remove(i)){
						System.out.println("Trying to remove item not in list... ERROR, check item: " + i.toString());
					}
				}
					
			}
		}
		showUpdatedItems();
	}
	
}
