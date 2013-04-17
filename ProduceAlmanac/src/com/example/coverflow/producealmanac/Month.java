package com.example.coverflow.producealmanac;

import java.util.ArrayList;
import java.util.Collections;

public class Month {
	/** Each Month must have a list of Items that are in season
	 * for that Month. The Item instances must be created before
	 * the Month instance.
	 */
	
	
	public final String months[] = {"INDEX_ZERO_BAD_MONTH","January","February","March","April",
			"May","June","July","August","September","October","November","December"};
	public String month;
	public ArrayList<Item> items; 
	public int monthNumber;
	
	//itemNamesByMonth[][] 
	//array of string arrays created at bottom of class below
	
	
	
	public Month(int monthNumber){
		this.monthNumber = monthNumber;
		this.month = months[monthNumber]; 
		this.items = new ArrayList<Item>();
	}
	
	@SuppressWarnings("unchecked")
	public void sort(){
		Collections.sort(items);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Item> getAllItems(){
		return (ArrayList<Item>) items.clone();
	}
	
	
	String Zero[] = {"ZERO_BAD_MONTH_NO_DATA",""};
	String Jan[] = {"cabbage","celeriac", "leek", "turnip"};
	String Feb[] = {"cabbage","celeriac", "leek", "turnip"};
	String Mar[] = {"cabbage","celeriac", "leek", "turnip"};
	String Apr[] = {"artichoke", "cabbage","celeriac","kale","leek","peas","turnip"};
	String May[] = {"cabbage","celeriac", "leek", "turnip"};
	String Jun[] = {"cabbage","celeriac", "leek", "turnip"};
	String Jul[] = {"cabbage","celeriac", "leek", "turnip"};
	String Aug[] = {"cabbage","celeriac", "leek", "turnip"};
	String Sep[] = {"cabbage","celeriac", "leek", "turnip"};
	String Oct[] = {"cabbage","celeriac", "leek", "turnip"};
	String Nov[] = {"cabbage","celeriac", "leek", "turnip"};
	String Dec[] = {"cabbage","celeriac", "leek", "turnip"};
	
	public String itemNamesByMonth[][] = {Zero,Jan,Feb,Mar,Apr,May,Jun,Jul,Aug,Sep,Oct,Nov,Dec};
	
}
