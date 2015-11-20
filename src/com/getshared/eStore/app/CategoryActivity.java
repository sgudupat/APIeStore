package com.getshared.eStore.app;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.getshared.eStore.app.common.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class CategoryActivity extends Activity implements Runnable {
    ArrayList<Category> items = new ArrayList<Category>();
    ArrayList<Category> fitems = new ArrayList<Category>();
    ArrayList<String> furls = new ArrayList<String>();
    ArrayList<String> eurls = new ArrayList<String>();
    ArrayList<Category> fList=new ArrayList<Category>();
    ArrayList<Category> cList=new ArrayList<Category>();
    ArrayList<Category> finalList=new ArrayList<Category>();
    HashMap<String,ArrayList<String>> categoryList =new HashMap<String,ArrayList<String>>();
    Category category=new Category();
    String keyName="";
    String cName="";
    private static String url = "http://affiliate-feeds.snapdeal.com/feed/57185.json";
    private static String url1 = "https://affiliate-api.flipkart.net/affiliate/api/getshared.json";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buildListView();
        
   
    }

    private void buildListView() {


        ListView listView = (ListView) findViewById(R.id.LinearLayout1);
        finalList=generateData();
     
      

        final CategoryAdapter adapter = new CategoryAdapter(
                CategoryActivity.this, finalData());
      
        
       

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(CategoryActivity.this,
                        StoreActivity.class);
           //    Log.i("producturl", ""+adapter.getItem(position));
               intent.putExtra("producturl", adapter.getItem(position));

                startActivity(intent);
            }
        });

    }


    private HashMap<String, ArrayList<String>> finalData() {
		// TODO Auto-generated method stub
    	 for(int i=0;i<finalList.size();i++){
         	//Log.i("inside for loop",""+items.size());
         	keyName=finalList.get(i).categoryName;
         	if(keyName.toLowerCase().contains("furniture")){
         	//	Log.i("category name",keyName);
         		
         		Log.i("category url",finalList.get(i).categoryUrl);
         		Log.i("category desc",finalList.get(i).categoryName);
         		cName=finalList.get(i).categoryName;
         		cName=cName.toLowerCase();
         	   furls.add(finalList.get(i).categoryUrl);
         	   category.setCategoryName(finalList.get(i).categoryName);
         	   category.setUrlList(furls);
         	   categoryList.put(cName, furls );
         		  fList.add(new Category(cName,furls));
         		
         		  
         	   
         	 
         	 
         		
         	}
        //	keyName=finalList.get(i).categoryName;
         	if(keyName.toLowerCase().contains("eyewear")){
         	//	Log.i("category name",keyName);
         		
         		Log.i("category url",finalList.get(i).categoryUrl);
         		Log.i("category desc",finalList.get(i).categoryName);
         		cName=finalList.get(i).categoryName;
         		cName=cName.toLowerCase();
         	  eurls.add(finalList.get(i).categoryUrl);
         	   category.setCategoryName(finalList.get(i).categoryName);
         	   category.setUrlList(eurls);
         	  categoryList.put(cName, eurls );
         	  fList.add(new Category(cName,eurls));
          		  //cList.add(new Category(categoryList));
          	  
         	 
         		
         	}
         	
         	  
    	 }
    	 //cList.add(new Category(categoryList));
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

    public void profileLoad(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
}
