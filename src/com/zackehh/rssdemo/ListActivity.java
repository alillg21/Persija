package com.zackehh.rssdemo;

import com.zackehh.rssdemo.R;
import com.zackehh.rssdemo.parser.RSSFeed;
import com.zackehh.rssdemo.util.LoadRSSFeed;
import com.zackehh.rssdemo.util.WriteObjectFile;

import static com.zackehh.rssdemo.parser.RSSUtil.*;
import com.zackehh.rssdemo.util.GlobalVariable;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;


public class ListActivity extends Activity implements OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener{

	// Check if we refreshed
	private boolean isRefresh = false;
	// The adapter for the list
	private ListAdapter adapter;
	// The list to display it in
	private ListView list;
	// The RSSFeed of the site
	private RSSFeed feed;
	// The match_controls of the site
	private  RelativeLayout match_image_controls;
	// The match_controls of the site
	private  RelativeLayout match_image_control_item1;
	// The match_controls of the site
	private  RelativeLayout match_image_control_item2;
	// The match_controls of the site
	private  RelativeLayout content_controls_news;
	// The match_controls of the site
	private  RelativeLayout content_controls_media;
	// The match_controls of the site
	private  RelativeLayout content_controls_matches;
	// The match_controls of the site
	private  RelativeLayout content_controls_persija;
	// The match_controls of the site
	private  RelativeLayout content_controls_other;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		// Create a new ViewGroup for the fragment
		setContentView(R.layout.guipageone);
		// If we're above Honeycomb
		if(android.os.Build.VERSION.SDK_INT >= 11){
			// Remove the icon from the ActionBar
			getActionBar().setDisplayShowHomeEnabled(false);
		}
		
		final  ImageButton buttonMenuNews = (ImageButton) findViewById(R.id.news_button);
		buttonMenuNews.setOnClickListener(this);
		final  ImageButton buttonMenuEvent = (ImageButton) findViewById(R.id.media_button);
		buttonMenuEvent.setOnClickListener(this);
		final  ImageButton buttonMenuProduct = (ImageButton) findViewById(R.id.matches_button);
		buttonMenuProduct.setOnClickListener(this);
		final  ImageButton buttonMenuSocial = (ImageButton) findViewById(R.id.persija_button);
		buttonMenuSocial.setOnClickListener(this);
		final  Button buttonhide = (Button) findViewById(R.id.hide_button);
		buttonhide.setOnClickListener(this);
		final  Button table_button = (Button) findViewById(R.id.table_button);
		table_button.setOnClickListener(this);
		final  Button today_button = (Button) findViewById(R.id.today_button);
		today_button.setOnClickListener(this);
		final  Button next_button = (Button) findViewById(R.id.next_button);
		next_button.setOnClickListener(this);
		final  Button last_button = (Button) findViewById(R.id.last_button);
		last_button.setOnClickListener(this);
		
		// The match_controls of the site
		match_image_controls = (RelativeLayout) findViewById(R.id.match_image_controls);
		
		// The match_image_control_item1 of the site
		match_image_control_item1 = (RelativeLayout) findViewById(R.id.match_image_control_item1);
		
		// The match_image_control_item1 of the site
		match_image_control_item1 = (RelativeLayout) findViewById(R.id.match_image_control_item1);
				
		// The match_image_control_item1 of the site
		match_image_control_item2 = (RelativeLayout) findViewById(R.id.match_image_control_item2);
		match_image_control_item2.setVisibility(View.GONE);
		
		content_controls_news    = (RelativeLayout) findViewById(R.id.content_controls_news);
		content_controls_media   = (RelativeLayout) findViewById(R.id.content_controls_media);
		content_controls_matches = (RelativeLayout) findViewById(R.id.content_controls_matches);
		content_controls_persija = (RelativeLayout) findViewById(R.id.content_controls_persija);
		content_controls_other   = (RelativeLayout) findViewById(R.id.content_controls_other);
		
		switch(GlobalVariable.stateChecker)
		{
		
			case GlobalVariable.NEWS:
				// The match_image_control_item1 of the site
				content_controls_news.setVisibility(View.VISIBLE);
				content_controls_media.setVisibility(View.GONE);
				content_controls_matches.setVisibility(View.GONE);
				content_controls_persija.setVisibility(View.GONE);
				content_controls_other.setVisibility(View.GONE);
				break;
			
			case GlobalVariable.MEDIA:
				// The match_image_control_item1 of the site
				content_controls_news.setVisibility(View.GONE);
				content_controls_media.setVisibility(View.VISIBLE);
				content_controls_matches.setVisibility(View.GONE);
				content_controls_persija.setVisibility(View.GONE);
				content_controls_other.setVisibility(View.GONE);
				break;
			
			case GlobalVariable.MATCHES:
				// The match_image_control_item1 of the site
				content_controls_news.setVisibility(View.GONE);
				content_controls_media.setVisibility(View.GONE);
				content_controls_matches.setVisibility(View.VISIBLE);
				content_controls_persija.setVisibility(View.GONE);
				content_controls_other.setVisibility(View.GONE);
				break;
				
			case GlobalVariable.PERSIJA:
				// The match_image_control_item1 of the site
				content_controls_news.setVisibility(View.GONE);
				content_controls_persija.setVisibility(View.GONE);
				content_controls_matches.setVisibility(View.GONE);
				content_controls_persija.setVisibility(View.VISIBLE);
				content_controls_other.setVisibility(View.GONE);
				break;
			
			case GlobalVariable.OTHERS:
				// The match_image_control_item1 of the site
				content_controls_news.setVisibility(View.GONE);
				content_controls_other.setVisibility(View.GONE);
				content_controls_matches.setVisibility(View.GONE);
				content_controls_persija.setVisibility(View.GONE);
				content_controls_other.setVisibility(View.VISIBLE);
				break;
		}
		// Get feed from the passed bundle
		feed = (RSSFeed)new WriteObjectFile(this).readObject(getFeedName(""));

		// Find the ListView we're using
		list = (ListView)findViewById(R.id.listViewControl);
		// Set the vertical edges to fade when scrolling
		list.setVerticalFadingEdgeEnabled(true);

		// Create a new adapter
		adapter = new ListAdapter(this, feed);
		// Set the adapter to the list
		list.setAdapter(adapter);

		// Set on item click listener to the ListView
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// Start the new activity and pass on the feed item
				GlobalVariable.itemChecker = 1;
				startActivity(new Intent(getBaseContext(), PostActivity.class).putExtra("pos", arg2));
			}
		});
	}

	@SuppressLint({ "InlinedApi", "NewApi" })
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		// Create a "change" option to change the feed URL
		MenuItem change = menu.add(Menu.NONE, 0, Menu.NONE, "CHANGE FEED");
		change.setVisible(false) ;
		// Create a "reload" menu option to reload the feed
		MenuItem reload = menu.add(Menu.NONE, 1, Menu.NONE, "RELOAD");
		reload.setVisible(false) ;
		// If we're above Honeycomb 
		if(android.os.Build.VERSION.SDK_INT >= 11){
			// Set the change button to show always
			change.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
			// Set the reload button to show always
			reload.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// We're refreshing
		isRefresh = true;
		// Depending on what's pressed
		switch (item.getItemId()) {
		case 0:
			// Change the URL
			changeFeed(isRefresh, this);
			return true;
		case 1:
			// Start parsing the feed again
			new LoadRSSFeed(this, RSSFEEDURL, GlobalVariable.NEWS).execute();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// Exit instead of going to splash screen
		adapter.notifyDataSetChanged();
	}

	@Override 
	public void onResume(){
		super.onResume();
		// This is awful, but don't change it until I work out another way
		if(isRefresh){
			feed = (RSSFeed)new WriteObjectFile(this).readObject(getFeedName(""));
			adapter = new ListAdapter(ListActivity.this, feed);
			list.setAdapter(adapter); 
		
			isRefresh = false;
		}
	}
	

	@Override
	public void onClick(View arg0) {
		int intID = arg0.getId();
	   	switch(intID){
	   		case 2131165213:
	   			RSSFEEDURL = "http://www.antara.co.id/rss/news.xml";
	   		   	new LoadRSSFeed(this, RSSFEEDURL, GlobalVariable.NEWS).execute();
	   			break;
	   			
	   		case 2131165214:
	   			RSSFEEDURL = "http://blog.zackehh.com/feed/";
	   		   	new LoadRSSFeed(this, RSSFEEDURL, GlobalVariable.MEDIA).execute();
	   			break;
	   			
	   		case 2131165215:
	   			RSSFEEDURL = "http://vote.langsungpilih.com/news/";
	   		   	new LoadRSSFeed(this, RSSFEEDURL, GlobalVariable.MATCHES).execute();
	   			break;
	   			
	   		case 2131165216:
	   			RSSFEEDURL = "http://vote.langsungpilih.com/catalog/RSS/";
	   		   	new LoadRSSFeed(this, RSSFEEDURL, GlobalVariable.PERSIJA).execute();
	   			break;
	   			
	   		case 2131165223: 
	   			if(GlobalVariable.openContainer == 1)
	   			{
					GlobalVariable.openContainer = 0;
					match_image_controls.setVisibility(View.GONE);
	   			}
	   			else
	   			{
					GlobalVariable.openContainer = 1;
					match_image_controls.setVisibility(View.VISIBLE);
	   			}
	   			break;
	   			
	   		case 2131165224:
	   			match_image_control_item1.setVisibility(View.VISIBLE);
	   			match_image_control_item2.setVisibility(View.GONE);
	   			GlobalVariable.openContainer = 1;
				match_image_controls.setVisibility(View.VISIBLE);
	   			break;
	   			
	   		case 2131165225:
	   			match_image_control_item1.setVisibility(View.VISIBLE);
	   			match_image_control_item2.setVisibility(View.GONE);
	   			GlobalVariable.openContainer = 1;
				match_image_controls.setVisibility(View.VISIBLE);
	   			break;
	   			
	   		case 2131165226:
	   			match_image_control_item1.setVisibility(View.VISIBLE);
	   			match_image_control_item2.setVisibility(View.GONE);
	   			GlobalVariable.openContainer = 1;
				match_image_controls.setVisibility(View.VISIBLE);
	   			break;
	   			
	   		case 2131165227:
	   			match_image_control_item1.setVisibility(View.GONE);
	   			match_image_control_item2.setVisibility(View.VISIBLE);
	   			GlobalVariable.openContainer = 1;
				match_image_controls.setVisibility(View.VISIBLE);
	   			break;
	   	}
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences arg0, String arg1) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
}