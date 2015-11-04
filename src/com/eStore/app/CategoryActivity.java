package com.eStore.app;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.eStore.app.common.JsonParser;
import com.eStore.domain.JsonParserF;



import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class CategoryActivity extends Activity implements Runnable {
	ArrayList<Category> items = new ArrayList<Category>();
	private static String url = "http://affiliate-feeds.snapdeal.com/feed/57185.json";
	private static String url1 = "https://affiliate-api.flipkart.net/affiliate/api/getshared.json";
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category);
		buildListView();
	}

	private void buildListView() {
		// TODO Auto-generated method stub

		ListView listView = (ListView) findViewById(R.id.categoryListview);
		// Adding items to list view
		// 1. pass context and data to the custom adapter
		final CategoryAdapter adapter = new CategoryAdapter(
				CategoryActivity.this, generateData());
		// 2. setListAdapter
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(CategoryActivity.this,
						StoreActivity.class);
				intent.putExtra("producturl",  adapter.getItem(position).getCategoryUrl());
			
				startActivity(intent);
			}
		});

	}


	private ArrayList<Category> generateData() {
		
		Thread t = new Thread(this);
		t.start();
		try {
			t.join();
		
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		 Log.i("list size",""+items.size());
		return items;
            
		
	}

	// you can make this class as another java file so it will be separated from
	// your main activity.
	public class AsyncTaskParseJson extends AsyncTask<String, String, String> {

		final String TAG = "AsyncTaskParseJson.java";

		// set your json string url here
		// String response =
		// "http://demo.codeofaninja.com/tutorials/json-example-with-php/index.php";

		// contacts JSONArray
		JSONArray dataJsonArr = null;

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected String doInBackground(String... arg0) {
			// make HTTP request

			JsonParser jParser = new JsonParser();
			Log.i("jparser object", jParser.toString());
			// Getting JSON from URL
			JSONObject json = null;
			try {
				json = jParser.getJSONFromUrl(url);
				JSONObject api = json.getJSONObject("apiGroups");
				JSONObject affiliate = api.getJSONObject("Affiliate");
				JSONObject list = affiliate.getJSONObject("listingsAvailable");
				int i = list.length();
				Log.d("list value", "Value: " + Integer.toString(i));
				Iterator<String> keysIterator = list.keys();
				String key;
				int count=1;
				while (keysIterator.hasNext()) {
					if(count>10){
						break;
					}
					count++;
					key = keysIterator.next();
					String url = list.getString(key);
					Log.i("key value", key);
					//Log.i("key product value", url);
					JSONObject jObj = new JSONObject(url);
					JSONObject listing = jObj.getJSONObject("listingVersions");
					JSONObject version = listing.getJSONObject("v1");
					String get = version.getString("get");
					Log.i("get value", get);
					items.add(new Category(key, get));

				}
				// Log.i("list object",list.toString());

			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Log.i("snapdeal response from server",json.toString());
			return json.toString();

		}

		protected void onPostExecute(String json) {

		}
	}
	public class flipkartTaskParseJson extends AsyncTask<String, String, String> {

		final String TAG = "AsyncTaskParseJson.java";

		// set your json string url here
		// String response =
		// "http://demo.codeofaninja.com/tutorials/json-example-with-php/index.php";

		// contacts JSONArray
		JSONArray dataJsonArr = null;

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected String doInBackground(String... arg0) {
			// make HTTP request

			JsonParser jParser = new JsonParser();
			Log.i("jparser object", jParser.toString());
			// Getting JSON from URL
			JSONObject json = null;
			try {
				json = jParser.getJSONFromUrl(url1);
				JSONObject api = json.getJSONObject("apiGroups");
				JSONObject affiliate = api.getJSONObject("affiliate");
				JSONObject list = affiliate.getJSONObject("apiListings");
				int i = list.length();
				Log.d("list value", "Value: " + Integer.toString(i));
				Iterator<String> keysIterator = list.keys();
				String key;
				int count=1;
				while (keysIterator.hasNext()) {
					if(count>10){
						break;
					}
					count++;
					key = keysIterator.next();
					String url = list.getString(key);
					Log.i("Flipkart Key value", key);
					//Log.i("key product value", url);
					JSONObject jObj = new JSONObject(url);
					JSONObject listing = jObj.getJSONObject("availableVariants");
					JSONObject version = listing.getJSONObject("v0.1.0");
					String fGet = version.getString("get");
					Log.i("Flipkart get value", fGet);
					items.add(new Category(key, fGet));
					

				}
				// Log.i("list object",list.toString());

			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Log.i("flipKart response from server",json.toString());
			return json.toString();

		}

		protected void onPostExecute(String json) {

		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			String result= new AsyncTaskParseJson().execute().get();
			String result1=new flipkartTaskParseJson().execute().get();
			Log.i("asynchromous result Snapdeal", result);
			Log.i("asynchromous result Flipkart", result1);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		} catch (ExecutionException e) {
			
			e.printStackTrace();
		}

	}
}
