package com.example.coverflow.producealmanac;

import java.util.ArrayList;
import java.util.Collections;

public class Filter {
	
	public String group;
	public ArrayList<Item> items; 
	
	public Filter(String groupName){
		this.group = groupName;
		this.items = new ArrayList<Item>();		
	}
	
	@SuppressWarnings("unchecked")
	public void sort(){
		Collections.sort(items);
	}
	

}
