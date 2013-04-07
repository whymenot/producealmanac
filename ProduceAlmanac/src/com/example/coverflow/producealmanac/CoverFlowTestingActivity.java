package com.example.coverflow.producealmanac;

import java.util.ArrayList;

import com.example.coverflow.CoverFlow;
import com.example.coverflow.R;
import com.example.coverflow.ReflectingImageAdapter;
import com.example.coverflow.ResourceImageAdapter;
import com.example.coverflow.producealmanac.Item;
import android.app.Activity;
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

/**
 * The Class CoverFlowTestingActivity.
 */
public class CoverFlowTestingActivity extends Activity {

	//currentItems will be the items currently in season, use a hashmap with key=time of year
	ArrayList<Item> currentItems;
	boolean populated=false;
	TextView textView;

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
		
		//add all current items to the ArrayList
		currentItems = new ArrayList<Item>();
		currentItems.add(new Item("apple"));
		currentItems.add(new Item("potato"));
		currentItems.add(new Item("strawberries"));
		//etc etc
		

		//CoverFlow code below

    	
//        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        textView = (TextView) findViewById(this.getResources()
                .getIdentifier("statusText", "id", "com.example.coverflow"));
        // note resources below are taken using getIdentifier to allow importing
        // this library as library.
        final CoverFlow reflectingCoverFlow = (CoverFlow) findViewById(this.getResources().getIdentifier(
                "coverflowReflect", "id", "com.example.coverflow"));
        setupCoverFlow(reflectingCoverFlow, true);
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
            coverImageAdapter = new ReflectingImageAdapter(new ResourceImageAdapter(this));
        } else {
            coverImageAdapter = new ResourceImageAdapter(this);
        }
        mCoverFlow.setAdapter(coverImageAdapter);
        mCoverFlow.setSelection(2, true);
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
                textView.setText("Item clicked! : " + id);
            }

        });
        mCoverFlow.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView< ? > parent, final View view, final int position, final long id) {
                textView.setText("Item selected! : " + id);
            }

            @Override
            public void onNothingSelected(final AdapterView< ? > parent) {
                textView.setText("Nothing clicked!");
            }
        });
    }
    
	public void populateMap(){
		
		Bitmap bitmapple = BitmapFactory.decodeResource(getResources(), R.drawable.apple);
		ImageView appleView =  new ImageView(this);
		appleView.setImageBitmap(bitmapple);
		Object[] info = {"apples grow on trees", "counter", bitmapple };
		Item.infoMap.put("apple",info);
		
		Bitmap bitmapotato = BitmapFactory.decodeResource(getResources(), R.drawable.potato);
		ImageView potatoView = new ImageView(this);
		potatoView.setImageBitmap(bitmapotato);		
		Object[] info2 = {"potatoes grow in the earth", "counter", bitmapotato};
		Item.infoMap.put("potato",info2);
		
		Bitmap bitmapberry = BitmapFactory.decodeResource(getResources(), R.drawable.strawberry);
		ImageView strawberryView = new ImageView(this);
		strawberryView.setImageBitmap(bitmapberry);		
		Object[] info3 = {"strawberries are good", "refrigerator", bitmapberry};
		Item.infoMap.put("strawberries", info3);
	}


}
