package com.eStore.app;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.client.vote.common.LongRunningGetIO;
import com.client.vote.common.SimpleHttpClient;
import com.eStore.domain.Product;

public class StoreActivity extends Activity implements Runnable{
	ArrayList<Product> product = new ArrayList<Product>();
	final Context context=this;
	private Handler handler = new Handler();  
	private ProgressDialog dialog;  
	//String imageName="";
	//a string array for the file name  
	private String[] filepath; 
	ImageView view;
	//a variable to store the downloaded Bitmap  
	public Bitmap downloadedBitmap;  
	ArrayList<Product> productList;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.store);
		buildImageView();
		  
	}
	private void buildImageView() {
		// TODO Auto-generated method stub
		/*  ListView listView = (ListView) findViewById(R.id.image_view);
		  ProductAdapter adapter = new ProductAdapter(StoreActivity.this, generateData());
		  listView.setAdapter(adapter);*/

		productList=generateData();
		Log.i("Imagelist size",""+productList.size());
		//new Image(imageName,productList).execute();
		Thread t=new Thread(this);
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<productList.size();i++) {
			int resId = getResources().getIdentifier("viewImage" + i, "id", getPackageName());
			view = (ImageView)findViewById(resId);
			//imageName=productList.get(i).getImage();
			view.setImageBitmap(productList.get(i).getTransformedImage());  
			view.setTag(productList.get(i).getImage());
			view.setVisibility(View.VISIBLE); 
			Log.i("view id",""+view );
			// new Image(imageName,productList).execute();
			//view.setImageResource(R.drawable.ic_launcher);
		}
	}
	/*	  private Bitmap DownloadBMP(String url) throws IOException {  
	        //create a URL object from the passed string  
	        URL location = new URL(url);  
	        Get the name of the file and its path, and break it into different parts. 
	 *Store each of these parts as elements in the filepath array.  
	        //filepath = location.getFile().split("\u002F");  
	        //create a InputStream object, to read data from a remote location  
	        InputStream input_s = location.openStream();         
	        //use the BitmapFactory to decode the downloaded stream into a Bitmap  
	        Bitmap returnedBMP = BitmapFactory.decodeStream(input_s);  
	        //close the InputStream  
	        input_s.close();  
	        //returns the downloaded bitmap  
	        return returnedBMP;  
	    }  
	 */
	//this method must be overridden, as we are implementing the Runnable interface  
	public void run() {  
		//Download the image  
		try {  
			for(int i=0;i<productList.size();i++) {
				String imageName = productList.get(i).getImage();
			Log.i("image name",imageName);
			//downloadedBitmap = DownloadBMP("http://52.74.246.67:8080/images/"+imageName);  
			URL location = new URL("http://52.74.246.67:8080/images/"+imageName);  
			InputStream input_s = location.openStream();         
			downloadedBitmap = BitmapFactory.decodeStream(input_s);  
			input_s.close();
			productList.get(i).setTransformedImage(downloadedBitmap);
			}
		} catch (IOException e) {  
			//If the image couldn't be downloaded, use the standard 'image not found' bitmap  
			downloadedBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);  
		}  
	}  
	public void EstoreProfile(View view){
		
		Intent intent = new Intent(this,ProfileAddressActivity.class);
		startActivity(intent);
	}
	public void ProductDetails (View view){
		String img=(String) view.getTag();
		Log.i("onclick image name", img);
		Log.i("size",""+productList.size());
		for(int i=0;i<productList.size();i++) {
			if(productList.get(i).getImage().contains(img)){
				Log.i("code",productList.get(i).getCode());	
				Log.i("price",productList.get(i).getPrice());			
				Log.i("image",productList.get(i).getImage());
				Intent intent = new Intent(this,ProductDetailActivity.class);
				Bitmap img1 =scaleDownBitmap(productList.get(i).getTransformedImage(), 150, context);
				intent.putExtra("bitmapImage", img1);
				intent.putExtra("code",productList.get(i).getCode());
				intent.putExtra("price",productList.get(i).getPrice());
				intent.putExtra("category",productList.get(i).getCategory());
				intent.putExtra("name",productList.get(i).getName());
				intent.putExtra("specification",productList.get(i).getSpecification());				
				startActivity(intent);				
			}
		}
		/*Intent intent = new Intent(this,ProductDetailActivity.class);
		startActivity(intent);*/
	}
	private Bitmap scaleDownBitmap(Bitmap photo, int newHeight,
			Context context) {
		// TODO Auto-generated method stub
		
		final float densityMultiplier = context.getResources().getDisplayMetrics().density;        

		 int h= (int) (newHeight*densityMultiplier);
		 int w= (int) (h * photo.getWidth()/((double) photo.getHeight()));

		 photo=Bitmap.createScaledBitmap(photo, w, h, true);

		
		return photo;
	}
	private ArrayList<Product> generateData() {
		String response=productDetail();
		try {
			JSONArray  jsonArray = new JSONArray(response);

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonobject = jsonArray.getJSONObject(i);
				String category = jsonobject.getString("category");
				String price = jsonobject.getString("price");
				String name = jsonobject.getString("name");
				String specification = jsonobject.getString("specification");
				String image=jsonobject.getString("image");
				String code=jsonobject.getString("code");
				Log.i("product name", name);
				Log.i("code ", code);
				Log.i("price", price);
				product.add(new Product(category, price, name, specification,image,code));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return product;

	}


	public String productDetail() {
		try {
			final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();


			String response = SimpleHttpClient.executeHttpPost("/getProducts", postParameters);
			Log.i("Response:", response);

			return response;


		} catch (Exception e) {
			Log.e("register", e.getMessage() + "");
			Toast.makeText(getApplicationContext(), "Products not available !!!", Toast.LENGTH_LONG).show();
			return "fail";
		}

	}

}
