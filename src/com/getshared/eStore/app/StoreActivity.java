package com.getshared.eStore.app;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.getshared.eStore.app.common.JsonParser;
import com.getshared.eStore.domain.JsonParserF;
import com.getshared.eStore.domain.Product;

public class StoreActivity extends Activity implements Runnable {

    ArrayList<Product> product = new ArrayList<Product>();
    final Context context = this;
    ImageView view1;
    public Bitmap downloadedBitmap;
    ArrayList<Product> productList = new ArrayList<Product>();

    ArrayList<String> urllist = new ArrayList<String>();
    String name;
    String amount;
    String productInfo;
    String image;
    String imageLink;
    String imageDetail;
    String url = "";
    String company = "";
    String cname = "";
    String productName;
    String productUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store);
        Intent intent = getIntent();
        cname = intent.getStringExtra("category");
        this.setTitle(cname);
        urllist = intent.getStringArrayListExtra("producturl");
       
        for (String s : urllist) {
            url = s;
            if (url.contains("flipkart")) {
                try {
                    productList = generateData1();
                    Log.i("productList", ""+productList);
                   
                        
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } else {
                try {
                    productList = generateData();

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }
        }
        try {
            buildImageView(url);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public class flipkartTaskParseJson2 extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... arg0) {
            JsonParserF jParser = new JsonParserF();
            JSONObject json = null;
            try {
                json = jParser.getJSONFromUrl(url);
              //   Log.i("response",json.toString()); 
                JSONArray  dataJsonArr = json.getJSONArray("productInfoList");
                Log.i("array length ",""+dataJsonArr.length());
                for (int i = 0; i < dataJsonArr.length(); i++) {
                    
                	  JSONObject jsonobject = dataJsonArr.getJSONObject(i);
                      JSONObject productIdentifier = jsonobject.getJSONObject("productBaseInfo");
                      JSONObject category = productIdentifier.getJSONObject("productAttributes");

                      JSONObject imageUrl = category.getJSONObject("imageUrls");
                      imageLink = imageUrl.getString("400x400");
                      image = category.getString("productUrl");
                      productInfo = category.getString("productDescription");
                      JSONObject selling = category.getJSONObject("sellingPrice");
                      amount = selling.getString("amount");
                      name = category.getString("title");
                      company = "flipkart";
                      Log.i("imageLink", imageLink );
                      Log.i("image", image );
                      Log.i("productInfo", productInfo );
                      Log.i("amount", amount );
                      Log.i("name", name );
                    productList.add(new Product(imageLink, image, productInfo, amount, name, company));
                    
                }
                
                
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (JSONException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
   }
            return json.toString();
        }

        protected void onPostExecute(String json) {
        }
    }

    private void buildImageView(String url) throws JSONException {


        Thread t = new Thread(this);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        GridView gridView = (GridView) findViewById(R.id.product_gridView1);


        ProductListAdapter imageGridAdapter = new ProductListAdapter(this, productList);

        gridView.setAdapter(imageGridAdapter);


    }


    public void run() {
     /*   int i = 0;       
        for (i = 0; i < productList.size(); i++) {
            try {
                String image = productList.get(i).getImage();                
                URL location = new URL(image);
                InputStream input_s = location.openStream();
                downloadedBitmap = BitmapFactory.decodeStream(input_s);
                input_s.close();
                productList.get(i).setTransformedImage(downloadedBitmap);

            } catch (IOException e) {
                downloadedBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hs_bg2);
                productList.get(i).setTransformedImage(downloadedBitmap);
                continue;
            }
        }*/
    }

    private ArrayList<Product> generateData() throws JSONException {
        try {
            String result = new AsyncTaskParseJson().execute().get();
            JSONObject json = new JSONObject(result);
            JSONArray dataJsonArr = json.getJSONArray("products");
            int counter = 0;
            // loop through all users
            for (int i = 0; i < dataJsonArr.length(); i++) {
               /* if (counter > 5) {
                    break;
                }
                counter++;*/
                JSONObject c = dataJsonArr.getJSONObject(i);
                // Storing each json item in variable
                String imageLink = c.getString("imageLink");
                String imagedetail = c.getString("link");
                String title = c.getString("title");
                String description = c.getString("description");
                String mrp = c.getString("mrp");
                String offerPrice = c.getString("offerPrice");
                company = "snapdeal";
                product.add(new Product(imageLink, imagedetail, description, offerPrice, title, company));
            }
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e1) {
            e1.printStackTrace();
        }
        return product;
    }

    private ArrayList<Product> generateData1() throws JSONException {
        try {
            String result = new flipkartTaskParseJson().execute().get();
            JSONObject json = new JSONObject(result);
            int counter = 0;
            JSONArray dataJsonArr = json.getJSONArray("productInfoList");          
            for (int i = 0; i < dataJsonArr.length(); i++) {
              /*  if (counter > 5) {
                    break;
                }
                counter++;
*/                JSONObject jsonobject = dataJsonArr.getJSONObject(i);
                JSONObject productIdentifier = jsonobject.getJSONObject("productBaseInfo");
                JSONObject category = productIdentifier.getJSONObject("productAttributes");

                JSONObject imageUrl = category.getJSONObject("imageUrls");
                imageLink = imageUrl.getString("400x400");
                image = category.getString("productUrl");
                productInfo = category.getString("productDescription");
                JSONObject selling = category.getJSONObject("sellingPrice");
                amount = selling.getString("amount");
                name = category.getString("title");
                company = "flipkart";
                product.add(new Product(imageLink, image, productInfo, amount, name, company));
            }

        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e1) {
            e1.printStackTrace();
        }
        return product;
    }

    public class flipkartTaskParseJson extends AsyncTask<String, String, String> {
        JSONArray dataJsonArr = null;
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... arg0) {
            JsonParserF jParser = new JsonParserF();
            JSONObject json = null;
            try {
                json = jParser.getJSONFromUrl(url);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }         
            return json.toString();
        }

        protected void onPostExecute(String strFromDoInBg) {
        }
    }

    // you can make this class as another java file so it will be separated from your main activity.
    public class AsyncTaskParseJson extends AsyncTask<String, String, String> {


        JSONArray dataJsonArr = null;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... arg0) {
            // make HTTP request


            JsonParser jParser = new JsonParser();

            // Getting JSON from URL
            JSONObject json = null;
            try {
                json = jParser.getJSONFromUrl(url);


            } catch (URISyntaxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }           
            return json.toString();


        }


        protected void onPostExecute(String strFromDoInBg) {
        }
    }
    public void search(View view){
    	EditText searchValue=(EditText)findViewById(R.id.inputSearch);
    	String searchValueString=searchValue.getText().toString();
    	Log.i("searchValueString", searchValueString);
    	productList.clear();
    	 Uri.Builder builder = new Uri.Builder();
         builder.scheme("https")
             .authority("affiliate-api.flipkart.net")
             .appendPath("affiliate")
             .appendPath("search")
              .appendPath("json")
             .appendQueryParameter("query", searchValueString)
             .appendQueryParameter("resultCount", "10");
         url = builder.build().toString();
         String result="";
         GridView gridView = (GridView) findViewById(R.id.product_gridView1);


         ProductListAdapter imageGridAdapter = new ProductListAdapter(this, productList);

         gridView.setAdapter(imageGridAdapter);
         Log.i("Result Search", result);
         try {
          result= new flipkartTaskParseJson2().execute().get();
          Log.i("result :",result);
         } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
         } catch (ExecutionException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
         }
    }


}
