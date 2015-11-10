package com.eStore.app;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.eStore.app.common.JsonParser;

public class CategoryActivity extends Activity implements Runnable {
    ArrayList<Category> items = new ArrayList<Category>();
    private static String url = "http://affiliate-feeds.snapdeal.com/feed/57185.json";
    private static String url1 = "https://affiliate-api.flipkart.net/affiliate/api/getshared.json";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buildListView();
    }

    private void buildListView() {


        GridView listView = (GridView) findViewById(R.id.gridView1);

        final CategoryAdapter adapter = new CategoryAdapter(
                CategoryActivity.this, generateData());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(CategoryActivity.this,
                		StoreActivity.class);
                intent.putExtra("producturl", adapter.getItem(position).getCategoryUrl());
                           
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
                    if (count > 10) {
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
                int i = list.length();

                Iterator<String> keysIterator = list.keys();
                String key;
                int count = 1;
                while (keysIterator.hasNext()) {
                    if (count > 10) {
                        break;
                    }
                    count++;
                    key = keysIterator.next();
                    String url = list.getString(key);

                    JSONObject jObj = new JSONObject(url);
                    JSONObject listing = jObj.getJSONObject("availableVariants");
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
    public void profileLoad(View view){
    	Intent intent = new Intent(this, ProfileActivity.class);
    	startActivity(intent);
    }
}
