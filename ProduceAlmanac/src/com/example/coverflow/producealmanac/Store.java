package com.example.coverflow.producealmanac;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Store {
	/**
	 * Each instance holds data for an individual grocery store or market.
	 * Holds information about the store's location and 
	 **/
	
	public static HashMap<String,Store> storeMap;
	public String storeName;
	public ArrayList<Item> items; 
	public String address;
	
	
	
	
	public Store(String storeName){
		this.storeName = storeName;
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
	
	public void addItem(String itemName){
		/**Adds Item with itemName to current items if it 
		 * is not already in the list. 
		 */
		Item item = Item.itemMap.get(itemName);
		if ( ! this.items.contains(item)){
			this.items.add(item);
		}
	}
	
	public void removeItem(String itemName){
		/**Removes Item with itemName from the current list
		 * of items it is present, otherwise no change.
		 */
		Item item = Item.itemMap.get(itemName);
		this.items.remove(item);
	}
	
	public void addItems(ArrayList<String> items){
		/** Adds a list of items to current items using addItem() **/
		for (String name : items){
			this.addItem(name);
		}
	}
	
	public void removeItems(ArrayList<String> items){
		/** Removes a list of items from current items using removeItem() **/
		for (String name: items){
			this.addItem(name);
		}
	}
	
	public void setItems(ArrayList<String> items){
		/** Completely replaces current list of items with this list **/
		this.items = new ArrayList<Item>();
		addItems(items);
	}
	
	public void setAddress(String address){
		this.address=address;
	}
	
	public String getAddress(){
		return this.address;
	}
	
	public void setName(String name){
		this.storeName=name;
	}
	
	public String getName(){
		return this.storeName;
	}
}
