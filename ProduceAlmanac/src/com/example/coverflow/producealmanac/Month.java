package com.example.coverflow.producealmanac;

import java.util.ArrayList;
import java.util.Collections;

public class Month {
	/** Each Month must have a list of Items that are in season
	 * for that Month. The Item instances must be created before
	 * the Month instance.
	 */
	
	
	public final String months[] = {"ZERO BAD","January","February","March","April",
			"May","June","July","August","September","October","November","December"};
	public String month;
	public ArrayList<Item> items; 
	public int monthNumber;
	
	public Month(int monthNumber){
		this.monthNumber = monthNumber;
		this.month = months[monthNumber]; 
		this.items = new ArrayList<Item>();
	}
	
	@SuppressWarnings("unchecked")
	public void sort(){
		Collections.sort(items);
	}
}
