package com.zackehh.rssdemo.parser;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.validator.routines.UrlValidator;

import com.zackehh.rssdemo.util.GlobalVariable;
import com.zackehh.rssdemo.util.LoadRSSFeed;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;

public class RSSUtil {

	// Put your RSS feed URL here
	public static String RSSFEEDURL = "http://www.antara.co.id/rss/news.xml";

	// Change to a given feed
	public static void changeFeed(boolean refresh, Context context){
		changeURL(refresh, context);
	}

	// Returns the filename of the stored feed
	@SuppressLint("NewApi")
	public static String getFeedName(String feedUrl){
		if(feedUrl.isEmpty())
			return getDomainName(RSSFEEDURL);
		else
			return getDomainName(feedUrl);
	}

	// Strips a URL to the domain
	private static String getDomainName(String url) {
		String domain = null;
		try {
			domain = new URI(url).getHost();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return domain.startsWith("www.") ? domain.substring(4) : domain;
	}

	// Takes a string URL as a new feed
	private static void changeURL(final boolean refresh, final Context context){
		// Set the content view
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		// Set an EditText view to get user input 
		final EditText input = new EditText(context);
		// Set the alert title
		builder.setTitle("Changing RSS Feed URL:")
		// Set the alert message
		.setMessage("Please enter a valid RSS URL:")
		// Set the view to the dialog
		.setView(input)
		// Can't exit via back button
		.setCancelable(false)
		// Set the positive button action
		.setPositiveButton("OK", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int whichButton){
				// Check for a valid URL
				if(new UrlValidator().isValid(input.getText().toString())){
					// Store the new URL we're working with
					RSSFEEDURL = input.getText().toString();
				}
				// Parse the new feed
				new LoadRSSFeed(context, RSSFEEDURL, GlobalVariable.NEWS).execute();
			}
		})
		// Set the negative button actions
		.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int whichButton){
				// If it's during initial loading
				if(!PreferenceManager.getDefaultSharedPreferences(context).getBoolean("isSetup", false)){
					// Exit the application
					((Activity)context).finish();
				} else {
					// Otherwise do nothing
					dialog.dismiss();
				}
			}
		});
		// Create dialog from builder
		AlertDialog alert = builder.create();
		// Don't exit the dialog when the screen is touched
		alert.setCanceledOnTouchOutside(false);
		// Show the alert
		alert.show();
		// Center the message
		((TextView)alert.findViewById(android.R.id.message)).setGravity(Gravity.CENTER);
		// Center the title of the dialog
		((TextView)alert.findViewById((context.getResources().getIdentifier("alertTitle", "id", "android")))).setGravity(Gravity.CENTER);
	}
}
