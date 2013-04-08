package com.example.coverflow.producealmanac;

import java.util.HashMap;

import android.graphics.Bitmap;

public class Item {
	
	static HashMap<String,Object[]> infoMap = new HashMap<String, Object[]>();
	static HashMap<String,Item> itemMap = new HashMap<String, Item>();
	
	public String name;
	public String description;
	public String ripeness;
	public String storage;
	public Bitmap picture;
	/*
	HOW TO LOAD A PICTURE FROM THE DRAWABLE FOLDER
	Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.name_of_file);
	someImageView.setImageBitmap(bitmap);
	*/
	
	public Item(String name){
		this.name = name;
		this.description = (String) infoMap.get(name)[0];
		this.storage = (String) infoMap.get(name)[1];
		this.ripeness = (String) infoMap.get(name)[2];
		this.picture = (Bitmap) infoMap.get(name)[3];
		Item.itemMap.put(name, this);
	}
	

		
}
