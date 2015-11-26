package com.getshared.eStore.app;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import android.widget.TextView;

import com.getshared.eStore.app.common.JsonParser;
import com.getshared.eStore.domain.NavDrawerItem;

@SuppressLint({ "DefaultLocale", "NewApi" }) public class CategoryActivity extends Activity implements Runnable {
	ArrayList<Category> items = new ArrayList<Category>();
	ArrayList<Category> fitems = new ArrayList<Category>();
	ArrayList<String> furls = new ArrayList<String>();
	ArrayList<String> eurls = new ArrayList<String>();
	ArrayList<String> elurls = new ArrayList<String>();
	ArrayList<String> jurls = new ArrayList<String>();

	ArrayList<Category> fList = new ArrayList<Category>();
	ArrayList<Category> cList = new ArrayList<Category>();
	ArrayList<Category> finalList = new ArrayList<Category>();
	HashMap<String, ArrayList<String>> categoryList = new HashMap<String, ArrayList<String>>();
	Category category = new Category();
	String keyName = "";
	String cName = "";
	private static String url = "http://affiliate-feeds.snapdeal.com/feed/57185.json";
	private static String url1 = "https://affiliate-api.flipkart.net/affiliate/api/getshared.json";

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

	@SuppressLint("NewApi")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		buildListView();
	
		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Page 1
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
				.getResourceId(0, -1)));
		// Page 2
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
				.getResourceId(1, -1)));
		// Page 3
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
				.getResourceId(2, -1)));
		// Page 4
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons
				.getResourceId(3, -1)));
		// Pages 5
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons
				.getResourceId(4, -1)));
		// Page 6
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons
				.getResourceId(5, -1)));

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
		
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
		

	}

	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Log.i("slide menu","item clicked"+position);
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Called when invalidateOptionsMenu() is triggered
	  * */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new Page_first();
			break;
		case 1:
			fragment = new Page_first();
			break;
		case 2:
			fragment = new Page_first();
			break;
		case 3:
			fragment = new Page_first();
			break;
		case 4:
			fragment = new Page_first();
			break;
		case 5:
			fragment = new Page_first();
			break;

		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();
			Log.i("inside fragment","fragment view loading");

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@SuppressLint("NewApi") @Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private void buildListView() {

		ListView listView = (ListView) findViewById(R.id.LinearLayout1);
		finalList = generateData();
		/*for(Category s: finalList){
			Log.i("list category name",s.categoryName);
			Log.i("list category url",s.categoryUrl);
		}*/

		final CategoryAdapter adapter = new CategoryAdapter(
				CategoryActivity.this, finalData());

		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(CategoryActivity.this,
						StoreActivity.class);
				// Log.i("producturl", ""+adapter.getItem(position));
				intent.putExtra("producturl", adapter.getItem(position));
				 TextView textView = (TextView) view.findViewById(R.id.categoryName);
		            String text = textView.getText().toString(); 
                   Log.i("clicked category name",text);
                   intent.putExtra("category", text);

				startActivity(intent);
			}
		});

	}

	@SuppressLint("DefaultLocale") private HashMap<String, ArrayList<String>> finalData() {
		// TODO Auto-generated method stub
		for (int i = 0; i < finalList.size(); i++) {
			// Log.i("inside for loop",""+items.size());
			keyName = finalList.get(i).categoryName;
			//Log.i("category name",keyName);
			if (keyName.toLowerCase().contains("furniture")) {
				// Log.i("category name",keyName);

				cName = finalList.get(i).categoryName;
				cName = cName.toLowerCase();
				furls.add(finalList.get(i).categoryUrl);
				categoryList.put(cName, furls);
				fList.add(new Category(cName, furls));

			}

			if (keyName.toLowerCase().contains("mens_clothing")
					|| keyName.toLowerCase().contains("womens")
					|| keyName.toLowerCase().contains("boys")) {
				// Log.i("category name",keyName);

				cName = finalList.get(i).categoryName;
				cName = cName.toLowerCase();
				cName = "Apparels";
				eurls.add(finalList.get(i).categoryUrl);
				categoryList.put(cName, eurls);
				fList.add(new Category(cName, eurls));
				// cList.add(new Category(categoryList));

			}
			if (keyName.toLowerCase().contains("tv_video_accessories")||keyName.contains("TV_Shop")
					|| keyName.toLowerCase().contains("computers_peripherals ")|| keyName.toLowerCase().contains("computer_peripherals")
					|| keyName.toLowerCase().contains("camera_accessories")||keyName.toLowerCase().contains("cameras_accessories")) {
				//Log.i("electronics category name",keyName);
				//Log.i("electronics category urls",finalList.get(i).categoryUrl);

				cName = finalList.get(i).categoryName;
				cName = cName.toLowerCase();
				cName = "electronics";
				elurls.add(finalList.get(i).categoryUrl);
				categoryList.put(cName, elurls);

			}
			if (keyName.toLowerCase().contains("jewellery")) {
				// Log.i("category name",keyName);

				cName = finalList.get(i).categoryName;
				cName = cName.toLowerCase();
				cName = "jewellery";
				jurls.add(finalList.get(i).categoryUrl);
				categoryList.put(cName, jurls);

			}
			if (keyName.toLowerCase().contains("jewellery")) {
				// Log.i("category name",keyName);

				cName = finalList.get(i).categoryName;
				cName = cName.toLowerCase();
				cName = "jewellery";
				jurls.add(finalList.get(i).categoryUrl);
				categoryList.put(cName, jurls);

			}

		}
		// cList.add(new Category(categoryList));
		return categoryList;
	}

	private ArrayList<Category> generateData() {

		Thread t = new Thread(this);
		t.start();
		try {
			t.join();

		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		return items;

	}

	public class AsyncTaskParseJson extends AsyncTask<String, String, String> {

		final String TAG = "AsyncTaskParseJson.java";

		JSONArray dataJsonArr = null;

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected String doInBackground(String... arg0) {

			JsonParser jParser = new JsonParser();

			JSONObject json = null;
			try {
				json = jParser.getJSONFromUrl(url);
				JSONObject api = json.getJSONObject("apiGroups");
				JSONObject affiliate = api.getJSONObject("Affiliate");
				JSONObject list = affiliate.getJSONObject("listingsAvailable");
				int i = list.length();

				Iterator<String> keysIterator = list.keys();
				String key;
				int count = 1;
				while (keysIterator.hasNext()) {
					if (count > 30) {
						break;
					}
					count++;
					key = keysIterator.next();
					String url = list.getString(key);

					JSONObject jObj = new JSONObject(url);
					JSONObject listing = jObj.getJSONObject("listingVersions");
					JSONObject version = listing.getJSONObject("v1");
					String get = version.getString("get");
                     Log.i("snap deal list key",""+key);
                     Log.i("snap deal list url",""+url);
					items.add(new Category(key, get));

				}

			} catch (URISyntaxException e) {

				e.printStackTrace();
			} catch (JSONException e) {

				e.printStackTrace();
			}

			return json.toString();

		}

		protected void onPostExecute(String json) {

		}
	}

	public class flipkartTaskParseJson extends
			AsyncTask<String, String, String> {

		final String TAG = "AsyncTaskParseJson.java";

		JSONArray dataJsonArr = null;

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected String doInBackground(String... arg0) {

			JsonParser jParser = new JsonParser();

			JSONObject json = null;
			try {
				json = jParser.getJSONFromUrl(url1);
				JSONObject api = json.getJSONObject("apiGroups");
				JSONObject affiliate = api.getJSONObject("affiliate");
				JSONObject list = affiliate.getJSONObject("apiListings");
				int i = list.length();

				Iterator<String> keysIterator = list.keys();
				String key;
				int count = 1;
				while (keysIterator.hasNext()) {
					if (count > 30) {
						break;
					}
					count++;
					key = keysIterator.next();
					String url = list.getString(key);

					JSONObject jObj = new JSONObject(url);
					JSONObject listing = jObj
							.getJSONObject("availableVariants");
					JSONObject version = listing.getJSONObject("v0.1.0");
					String fGet = version.getString("get");
					   Log.i("flipkart list key",""+key);
	                     Log.i("flipkart list url",""+fGet);

					items.add(new Category(key, fGet));

				}

			} catch (URISyntaxException e) {

				e.printStackTrace();
			} catch (JSONException e) {

				e.printStackTrace();
			}

			return json.toString();

		}

		protected void onPostExecute(String json) {

		}
	}

	@Override
	public void run() {

		try {
			String result = new AsyncTaskParseJson().execute().get();
			String result1 = new flipkartTaskParseJson().execute().get();

		} catch (InterruptedException e) {

			e.printStackTrace();
		} catch (ExecutionException e) {

			e.printStackTrace();
		}

	}

	public void profileLoad(View view) {
		Intent intent = new Intent(this, ProfileActivity.class);
		startActivity(intent);
	}
}
