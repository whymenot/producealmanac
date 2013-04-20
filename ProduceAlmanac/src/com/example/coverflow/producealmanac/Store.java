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
	private String storeName;
	private ArrayList<Item> active; 
	private String address;
	private ArrayList<Item> inactive;
	
	
	
	
	public Store(String storeName){
		this.storeName = storeName;
		this.active = new ArrayList<Item>();
		this.inactive = new ArrayList<Item>();
	}
	
	@SuppressWarnings("unchecked")
	public void sort(){
		Collections.sort(active);
		Collections.sort(inactive);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Item> getAllActiveItems(){
		this.sort();
		return (ArrayList<Item>) active.clone();
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Item> getAllInactiveItems(){
		this.sort();
		return (ArrayList<Item>) inactive.clone();
	}
	
	private void addActiveItem(String itemName){
		/**Adds Item with itemName to active if it 
		 * is not already in the list. 
		 */
		Item item = Item.itemMap.get(itemName);
		if ( ! this.active.contains(item)){
			this.active.add(item);
		}
	}
	
	
	private void addActiveItems(ArrayList<String> items){
		/** Adds a list of items to active using addActiveItem() **/
		for (String name : items){
			this.addActiveItem(name);
		}
	}
	
	private void addInactiveItem(String name){
		/**Adds Item with "name" to inactive if it 
		 * is not already in the list. 
		 */
		Item item = Item.itemMap.get(name);
		if ( ! this.inactive.contains(item)){
			this.inactive.add(item);
		}

	}
	
	private void addInactiveItems(ArrayList<String> items){
		/** Adds a list of items to inactive using addInactiveItem() **/
		for (String name : items){
			this.addInactiveItem(name);
		}
	}
	
	
	public void setActiveItems(ArrayList<String> items){
		/** Completely replaces current list of active with this list **/
		this.active = new ArrayList<Item>();
		addActiveItems(items);
		this.sort();
	}
			
	
	public void setInactiveItems(ArrayList<String> items){
		/** Completely replaces inactive list of items with this list **/
		this.inactive = new ArrayList<Item>();
		addInactiveItems(items);
		this.sort();
	}
	
	public void putInSeason(ArrayList<String> names){
		/**Puts the current list of items "in season" for this store.
		 * Calls the overridden putInSeason(String) method for each name.
		 */
		for (String name: names){
			putInSeason(name);
		}
	}
	
	public void putInSeason(String name){
		/**Puts the the item with "name" in season.
		 * If the item is already in the active list, no changes are made.
		 * If it is in the inactive list, it is moved to the active list.
		 * If it is in neither, it is added to the active list.
		 */
		Item item = Item.itemMap.get(name);
		if ( ! this.active.contains(item)){
			this.active.add(item);
		}
		this.inactive.remove(item);
	}
	
	public void takeOutOfSeason(ArrayList<String> names){
		/**Puts the current list of items in the inactive list for this store.
		 * Calls the overridden takeOutOfSeason(String) method for each name.
		 */
		for (String name: names){
			takeOutOfSeason(name);
		}
	}
	
	public void takeOutOfSeason(String name){
		/**Takes the item with "name" out of season.
		 * If the item is already in the inactive list, no changes are made.
		 * If it is in the active list, it is moved to the inactive list.
		 * If it is in neither, it is added to the inactive list.
		 */
		Item item = Item.itemMap.get(name);
		if ( ! this.inactive.contains(item)){
			this.inactive.add(item);
		}
		this.active.remove(item);
	}
	
	public void removeFromInventory(String name){
		/**Removes item with name from both active and inactie lists**/
		Item item = Item.itemMap.get(name);
		this.active.remove(item);
		this.inactive.remove(item);
	}
	
	public void removeFromInventory(ArrayList<String> names){
		/**Removes all items with name in "names" from both 
		 * active and inactive lists using removeFromInventory(name).
		 */
		for(String name : names){
			removeFromInventory(name);
		}
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
