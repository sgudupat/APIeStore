package com.getshared.eStore.app;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.getshared.eStore.app.common.JsonParser;
import com.getshared.eStore.domain.NavDrawerItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

public class CategoryFragment extends Fragment implements Runnable {
	ArrayList<Category> items = new ArrayList<Category>();
	ArrayList<String> furls = new ArrayList<String>();
	ArrayList<String> eurls = new ArrayList<String>();
	ArrayList<String> elurls = new ArrayList<String>();
	ArrayList<String> jurls = new ArrayList<String>();
	ArrayList<String> turls = new ArrayList<String>();

	ArrayList<Category> fList = new ArrayList<Category>();
	ArrayList<Category> finalList = new ArrayList<Category>();

	ArrayList<Category> categoriesList = new ArrayList<Category>();
	ArrayList<String> fURL = new ArrayList<String>();
	ArrayList<String> eURL = new ArrayList<String>();
	ArrayList<String> aURL = new ArrayList<String>();
	ArrayList<String> jURL = new ArrayList<String>();
	ArrayList<String> tURL = new ArrayList<String>();


	HashMap<String, ArrayList<String>> categoryList = new HashMap<String, ArrayList<String>>();
	HashMap<String, ArrayList<String>> newCategoriesList = new HashMap<String, ArrayList<String>>();

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
	View rootView;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.activity_main, container, false);
		buildListView();
		return rootView;
	}

	private void buildListView() {
		ListView listView = (ListView) rootView.findViewById(R.id.LinearLayout1);
		finalList = generateData();
		final CategoryAdapter adapter = new CategoryAdapter(getActivity().getBaseContext(), finalData());
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getActivity(), StoreActivity.class);
				intent.putExtra("producturl", adapter.getItem(position));
				TextView textView = (TextView) view.findViewById(R.id.categoryName);
				String text = textView.getText().toString();
				intent.putExtra("category", text);
				startActivity(intent);
			}
		});

	}

	@SuppressLint("DefaultLocale")
	private HashMap<String, ArrayList<String>> finalData() {
		Log.i("fURL::", fURL.toString());
		categoryList.put("Furniture", fURL);
		categoryList.put("Apparels", fURL);
		categoryList.put("Jewellery", fURL);
		categoryList.put("Eyewear", fURL);
		categoryList.put("Electronics", fURL);
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

	//SnapDeal Data source
	public class AsyncTaskParseJson extends AsyncTask<String, String, String> {
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
				//Furniture Section
				String[] furniture = getResources().getStringArray(R.array.furniture_section);
				for (String f : furniture) {
						String url = list.getString(f);
						JSONObject jObj = new JSONObject(url);
						JSONObject listing = jObj.getJSONObject("listingVersions");
						JSONObject version = listing.getJSONObject("v1");
						String get = version.getString("get");
						Log.i("get::", get);
						//items.add(new Category(key, get));
						fURL.add(get);
				}
				/* //Apparels Section
                String[] apparels = getResources().getStringArray(R.array.apparel_section);
                for (String a : apparels) {
                    String url = list.getString(a);
                    JSONObject jObj = new JSONObject(url);
                    JSONObject listing = jObj.getJSONObject("listingVersions");
                    JSONObject version = listing.getJSONObject("v1");
                    String get = version.getString("get");
                    //items.add(new Category(key, get));
                    aURL.add(get);
                }
                //Electronics Section
                String[] electronics = getResources().getStringArray(R.array.electronics_section);
                for (String e : electronics) {
                    String url = list.getString(e);
                    JSONObject jObj = new JSONObject(url);
                    JSONObject listing = jObj.getJSONObject("listingVersions");
                    JSONObject version = listing.getJSONObject("v1");
                    String get = version.getString("get");
                    //items.add(new Category(key, get));
                    eURL.add(get);
                }*/

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


	public class flipkartTaskParseJson extends AsyncTask<String, String, String> {

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
				/*   int i = list.length();
                Iterator<String> keysIterator = list.keys();
                String key;
                int count = 1;*/
				/*   while (keysIterator.hasNext()) {
                    if (count > 30) {
                        break;
                    }
                    count++;
                    key = keysIterator.next();*/
				String[] furniture = getResources().getStringArray(R.array.furniture_section);
				for (String f : furniture) {
						String url = list.getString(f);
						JSONObject jObj = new JSONObject(url);
						JSONObject listing = jObj.getJSONObject("availableVariants");
						JSONObject version = listing.getJSONObject("v0.1.0");
						String fGet = version.getString("get");
						Log.i("f get::", fGet);
						fURL.add(fGet);
					// items.add(new Category(key, fGet));
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
		Intent intent = new Intent();
		startActivity(intent);
	}
}