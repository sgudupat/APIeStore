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
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.getshared.eStore.app.common.JsonParser;

@SuppressLint({ "DefaultLocale", "NewApi" })
public class CategoryActivity extends MenuActivity implements Runnable {
	ArrayList<Category> items = new ArrayList<Category>();

	ArrayList<String> furls = new ArrayList<String>();
	ArrayList<String> eurls = new ArrayList<String>();
	ArrayList<String> elurls = new ArrayList<String>();
	ArrayList<String> jurls = new ArrayList<String>();

	ArrayList<Category> finalList = new ArrayList<Category>();
	HashMap<String, ArrayList<String>> categoryList = new HashMap<String, ArrayList<String>>();
	Category category = new Category();
	String keyName = "";
	String cName = "";
	private static String url = "http://affiliate-feeds.snapdeal.com/feed/57185.json";
	private static String url1 = "https://affiliate-api.flipkart.net/affiliate/api/getshared.json";


	@SuppressLint("NewApi")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		buildListView();

	}



	private void buildListView() {

		ListView listView = (ListView) findViewById(R.id.LinearLayout1);
		finalList = generateData();		
		final CategoryAdapter adapter = new CategoryAdapter(
				CategoryActivity.this, finalData());

		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(CategoryActivity.this,
						StoreActivity.class);				
				intent.putExtra("producturl", adapter.getItem(position));
				TextView textView = (TextView) view
						.findViewById(R.id.categoryName);
				String text = textView.getText().toString();				
				intent.putExtra("category", text);

				startActivity(intent);
			}
		});

	}

	@SuppressLint("DefaultLocale")
	private HashMap<String, ArrayList<String>> finalData() {		
		for (int i = 0; i < finalList.size(); i++) {		
			keyName = finalList.get(i).categoryName;			
			if (keyName.toLowerCase().contains("furniture")) {				
				cName = finalList.get(i).categoryName;
				cName = cName.toLowerCase();
				furls.add(finalList.get(i).categoryUrl);
				categoryList.put(cName, furls);


			}

			if (keyName.toLowerCase().contains("mens_clothing")
					|| keyName.toLowerCase().contains("womens")
					|| keyName.toLowerCase().contains("boys")) {
				cName = finalList.get(i).categoryName;
				cName = cName.toLowerCase();
				cName = "Apparels";
				eurls.add(finalList.get(i).categoryUrl);
				categoryList.put(cName, eurls);


			}
			if (keyName.toLowerCase().contains("tv_video_accessories")
					|| keyName.contains("TV_Shop")
					|| keyName.toLowerCase().contains("computers_peripherals ")
					|| keyName.toLowerCase().contains("computer_peripherals")
					|| keyName.toLowerCase().contains("camera_accessories")
					|| keyName.toLowerCase().contains("cameras_accessories")) {			

				cName = finalList.get(i).categoryName;
				cName = cName.toLowerCase();
				cName = "electronics";
				elurls.add(finalList.get(i).categoryUrl);
				categoryList.put(cName, elurls);

			}
			if (keyName.toLowerCase().contains("jewellery")) {   
				cName = finalList.get(i).categoryName;
				cName = cName.toLowerCase();
				cName = "jewellery";
				jurls.add(finalList.get(i).categoryUrl);
				categoryList.put(cName, jurls);

			}
			if (keyName.toLowerCase().contains("jewellery")) {  
				cName = finalList.get(i).categoryName;
				cName = cName.toLowerCase();
				cName = "jewellery";
				jurls.add(finalList.get(i).categoryUrl);
				categoryList.put(cName, jurls);

			}

		}		
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
