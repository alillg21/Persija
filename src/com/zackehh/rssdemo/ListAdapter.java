package com.zackehh.rssdemo;

import com.zackehh.rssdemo.R;
import com.zackehh.rssdemo.image.SmartImageView;
import com.zackehh.rssdemo.parser.RSSFeed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {

	// Create a new LayoutInflater
	private LayoutInflater layoutInflater;
	// Create a new RSSFeed
	private RSSFeed feed;

	public ListAdapter(Context c, RSSFeed rssFeed) {
		// Set the layout inflater
		layoutInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// Set the RSS feed
		feed = rssFeed;
	}

	@Override
	public int getCount() {
		// Set the total list item count
		return feed.getItemCount();
	}

	@Override
	public Object getItem(int position) {
		// Return the position
		return position;
	}

	@Override
	public long getItemId(int position) {
		// Return the position
		return position;
	}

	@Override
	public View getView(int position, View listItem, ViewGroup parent) {
		// If a list item is null
		if (listItem == null) {
			// Inflate a list item
			listItem = layoutInflater.inflate(R.layout.feed_list_item, null);
		}
		// Set the views in the layout
		((TextView)listItem.findViewById(R.id.title)).setText(feed.getItem(position).getTitle());
		// Set the views in the layout
		((TextView)listItem.findViewById(R.id.titleRian)).setText(feed.getItem(position).getDescription());
		// Bit of formatting for adding the author imageRss
		((TextView)listItem.findViewById(R.id.date)).setText(feed.getItem(position).getDate() + " - " + feed.getItem(position).getAuthor());
		// Bit of formatting for adding the author imageRss
		((SmartImageView)listItem.findViewById(R.id.imageRss)).setImageUrl(feed.getItem(position).getThumb());
		// Return the new list item
		return listItem;
	}
}