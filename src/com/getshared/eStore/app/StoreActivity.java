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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.getshared.eStore.app.common.JsonParser;
import com.getshared.eStore.domain.JsonParserF;
import com.getshared.eStore.domain.Product;

public class StoreActivity extends Activity implements Runnable {

	ArrayList<Product> product = new ArrayList<Product>();
	final Context context = this;
	ImageView view1;
	public Bitmap downloadedBitmap;
	ArrayList<Product> productList;
	

	ArrayList<String> urllist = new ArrayList<String>();
	String name;
	String amount;
	String productInfo;
	String image;
	String imageLink;
	String imageDetail;
	String url = "";
	String company="";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.store);

		
		Intent intent = getIntent();
		urllist = intent.getStringArrayListExtra("producturl");
		for(String s: urllist){
			Log.i("productUrl of", s);
			url=s;
			if (url.contains("flipkart")) {
				  try {
					productList = generateData1();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} else {
				try {
					productList = generateData();
					//Log.i("productList",""+ productList);

				
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    
			    
			
			}}
		try {
			buildImageView(url);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private void buildImageView(String url) throws JSONException {



		//Log.i("Imagelist size", "" + productList.size());
		Thread t = new Thread(this);
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		GridView gridView = (GridView) findViewById(R.id.product_gridView1);
		//Log.i("grid","grid");
		
		ProductListAdapter imageGridAdapter = new  ProductListAdapter(this, productList);
		//Log.i("STORAGE", "adapter");
		gridView.setAdapter(imageGridAdapter);
		
		/*for (int i = 0; i < productList.size(); i++) {			
			int image = getResources().getIdentifier("photo_image" + i, "id", getPackageName());
			Log.i("tag", ""+image);
			view1 = (ImageView) findViewById(image);
			view1.setImageBitmap(productList.get(i).getTransformedImage());
			view1.setTag(productList.get(i).getImage());
			view1.setVisibility(View.VISIBLE);
		}*/
	}


	public void run() {
		int i =0;
		Log.i("product list size", ""+productList.size());
		for (i = 0; i < productList.size(); i++) {
			try {
			String image = productList.get(i).getImage();
			Log.i("image name", image);
			URL location = new URL(image);
			InputStream input_s = location.openStream();
			downloadedBitmap = BitmapFactory.decodeStream(input_s);
			input_s.close();
			productList.get(i).setTransformedImage(downloadedBitmap);
			//Log.i("productList22", "" + productList);
		}
	 catch (IOException e) {
		// If the image couldn't be downloaded, use the standard 'image not
		// found' bitmap
	//Log.i("download error","error in downloading image");
		downloadedBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_launcher);
		productList.get(i).setTransformedImage(downloadedBitmap);
		continue;
	}
		}
	}



	private ArrayList<Product> generateData() throws JSONException {
		try {
			String result = new AsyncTaskParseJson().execute().get();
			JSONObject json = new JSONObject(result);
			JSONArray dataJsonArr = json.getJSONArray("products");
			int counter=0;
			// loop through all users
			for (int i = 0; i < dataJsonArr.length(); i++) {
				if(counter>5){
					break;
				}
				counter++;

				JSONObject c = dataJsonArr.getJSONObject(i);

				// Storing each json item in variable
				String imageLink = c.getString("imageLink");
				String imagedetail = c.getString("link");
				String title = c.getString("title");
				String description = c.getString("description");
				String mrp = c.getString("mrp");
				company="snapdeal";
				/*Log.i("mrp", mrp);
				Log.i("title", title);
				Log.i("description", description);*/
				product.add(new Product(imageLink, imagedetail, description, mrp, title,company));


			}


		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		return product;

	}

	private ArrayList<Product> generateData1() throws JSONException {
		try {
			String result = new flipkartTaskParseJson().execute().get();
			JSONObject json = new JSONObject(result);
			int counter=0;
			JSONArray dataJsonArr = json.getJSONArray("productInfoList");
			Log.i("json array length",""+dataJsonArr.length());
			for (int i = 0; i < dataJsonArr.length(); i++) {
				if(counter>5){
					break;
				}
				counter++;
				JSONObject jsonobject = dataJsonArr.getJSONObject(i);
				JSONObject productIdentifier = jsonobject.getJSONObject("productBaseInfo");
				JSONObject category = productIdentifier.getJSONObject("productAttributes");
				//JSONObject productDescript= productIdentifier.getJSONObject("productDescription");
				JSONObject imageUrl = category.getJSONObject("imageUrls");
				imageLink = imageUrl.getString("400x400");
				image = category.getString("productUrl");
				productInfo = category.getString("productDescription");
				JSONObject selling = category.getJSONObject("sellingPrice");
				amount = selling.getString("amount");
				name = category.getString("title");
				company="flipkart";
				/*Log.i("name", name);
				Log.i("product selling", amount);
				Log.i("product Desc", productInfo);*/
				product.add(new Product(imageLink, image, productInfo, amount, name,company));

			}

		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
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
			//Log.i("FlipKart response", json.toString());
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


	
	
}
