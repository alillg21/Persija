package com.zackehh.rssdemo.parser;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

public class RSSFeed implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Create a new item count
	private int itemCount = 0;
	// Create a new item list
	private List<RSSItem> itemList;
	// Serializable ID
	//private static final long serialVersionUID = 1L;

	public RSSFeed() {
		// Initialize the item list
		itemList = new Vector<RSSItem>(0);
	}

	void addItem(RSSItem item) {
		// Add an item to the Vector
		itemList.add(item);
		// Increment the item count
		itemCount++;
	}

	public RSSItem getItem(int position) {
		// Return the item at the chosen position
		return itemList.get(position);
	}

	public int getItemCount() {
		// Return the number of items in the feed
		return itemCount;
	}

}
