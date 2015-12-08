package com.getshared.eStore.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.getshared.eStore.app.common.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

@SuppressLint({"DefaultLocale", "NewApi"})
public class CategoryActivity extends Activity implements Runnable {
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
    ArrayList<String> fURL = new ArrayList<String>();
    ArrayList<String> eURL = new ArrayList<String>();
    ArrayList<String> aURL = new ArrayList<String>();
    ArrayList<String> jURL = new ArrayList<String>();
    ArrayList<String> yURL = new ArrayList<String>();
    ArrayList<String> wURL = new ArrayList<String>();
   

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
         generateData();
        final CategoryAdapter adapter = new CategoryAdapter(CategoryActivity.this, finalData());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CategoryActivity.this, StoreActivity.class);
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
        categoryList.put("Apparels", aURL);
        categoryList.put("Jewellery", jURL);
        categoryList.put("EyeWear", yURL);
        categoryList.put("Fragrances", wURL);
        categoryList.put("Electronics", eURL);
        return categoryList;
       
    }

    private void generateData() {
        Thread t = new Thread(this);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
                    try {
                        String url = list.getString(f);
                        JSONObject jObj = new JSONObject(url);
                        JSONObject listing = jObj.getJSONObject("listingVersions");
                        JSONObject version = listing.getJSONObject("v1");
                        String get = version.getString("get");
                        Log.i("furniture get::", get);
                        //items.add(new Category(key, get));
                        fURL.add(get);
                    } catch (Exception e) {
                        Log.i("furniture error:", e.getMessage());
                    }
                }
                 //Apparels Section
                String[] apparels = getResources().getStringArray(R.array.apparel_section);
                for (String a : apparels) {
                	 try {
                    String url = list.getString(a);
                    JSONObject jObj = new JSONObject(url);
                    JSONObject listing = jObj.getJSONObject("listingVersions");
                    JSONObject version = listing.getJSONObject("v1");
                    String get = version.getString("get");
                    Log.i("apparels get", get);
                    //items.add(new Category(key, get));
                    aURL.add(get);
                	 } catch (Exception e) {
                         Log.i("apparels error:", e.getMessage());
                     }
                }
              //Electronics Section
                String[] electronics = getResources().getStringArray(R.array.electronics_section);
                for (String e : electronics) {
                	 try {
                    String url = list.getString(e);
                    JSONObject jObj = new JSONObject(url);
                    JSONObject listing = jObj.getJSONObject("listingVersions");
                    JSONObject version = listing.getJSONObject("v1");
                    String get = version.getString("get");
                    Log.i("electronics get", get);
                    //items.add(new Category(key, get));
                    eURL.add(get);
                	 } catch (Exception e3) {
                         Log.i("electronics error:", e3.getMessage());
                     }
                }
                //Jewellery Section
                String[] jewellery = getResources().getStringArray(R.array.jewellery_section);
                for (String e : jewellery) {
                	try {
                	Log.i("jewellery", e);
                    String url = list.getString(e);
                    JSONObject jObj = new JSONObject(url);
                    JSONObject listing = jObj.getJSONObject("listingVersions");
                    JSONObject version = listing.getJSONObject("v1");
                    String get = version.getString("get");
                    Log.i("get jewellery ::", get);
                    //items.add(new Category(key, get));
                    jURL.add(get);
                	 } catch (Exception e2) {
                         Log.i("jewellery error:", e2.getMessage());
                     }
                }
              //eye wear Section
                String[] eyewear = getResources().getStringArray(R.array.eyewear_section);
                for (String f : eyewear) {
                    try {
                        String url = list.getString(f);
                        JSONObject jObj = new JSONObject(url);
                        JSONObject listing = jObj.getJSONObject("listingVersions");
                        JSONObject version = listing.getJSONObject("v1");
                        String get = version.getString("get");
                        Log.i("get eyewear::", get);
                        //items.add(new Category(key, get));
                        yURL.add(get);
                    } catch (Exception e) {
                        Log.i("eyewear error:", e.getMessage());
                    }
                }
              //fragrance_section 
                String[] fragrances = getResources().getStringArray(R.array.fragrance_section);
                for (String e : fragrances) {
                	  try {
                	Log.i("fragrances", e);
                    String url = list.getString(e);
                    JSONObject jObj = new JSONObject(url);
                    JSONObject listing = jObj.getJSONObject("listingVersions");
                    JSONObject version = listing.getJSONObject("v1");
                    String get = version.getString("get");
                    Log.i("fragrances get", get);
                    //items.add(new Category(key, get));
                    wURL.add(get);
                	  } catch (Exception e1) {
                          Log.i("fragrances error:", e1.getMessage());
                      }
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
                String[] furniture = getResources().getStringArray(R.array.furniture_section);
                for (String f : furniture) {
                    try {
                        String url = list.getString(f);
                        JSONObject jObj = new JSONObject(url);
                        JSONObject listing = jObj.getJSONObject("availableVariants");
                        JSONObject version = listing.getJSONObject("v0.1.0");
                        String fGet = version.getString("get");
                        Log.i("f1 get::", fGet);
                        fURL.add(fGet);
                    } catch (Exception e) {
                        Log.i("Snap error:", e.getMessage());
                    }
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
