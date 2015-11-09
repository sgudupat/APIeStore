package com.eStore.app;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.eStore.app.common.JsonParser;
import com.eStore.app.common.SimpleHttpClient;
import com.eStore.domain.JsonParserF;
import com.eStore.domain.Product;

public class StoreActivity extends Activity implements Runnable {

    ArrayList<Product> product = new ArrayList<Product>();
    final Context context = this;
    ImageView view;
    public Bitmap downloadedBitmap;
    ArrayList<Product> productList;
    String url = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store);
        Intent intent = getIntent();
        url = intent.getStringExtra("producturl");

        try {
            buildImageView();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void buildImageView() throws JSONException {

        if (url.contains("flipkart")) {
            productList = generateData1();
            Log.i("flipkart", " msg");
        } else {
            productList = generateData();
        }
        Log.i("finish", "finish");

        Log.i("finish flipkart", "finish flipkart");
        Log.i("Imagelist size", "" + productList.size());

        Thread t = new Thread(this);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
        for (int i = 0; i < productList.size(); i++) {
            int resId = getResources().getIdentifier("viewImage" + i, "id", getPackageName());
            view = (ImageView) findViewById(resId);

            view.setImageBitmap(productList.get(i).getTransformedImage());
            view.setTag(productList.get(i).getImage());
            view.setVisibility(View.VISIBLE);


        }
    }


    public void run() {


        try {
            for (int i = 0; i < productList.size(); i++) {
                String image = productList.get(i).getImage();
                Log.i("image name", image);
                // downloadedBitmap = DownloadBMP("http://52.74.246.67:8080/images/"+imageName);
                URL location = new URL(image);
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


    public void ProductDetails(View view) {
        String img = (String) view.getTag();

        for (int i = 0; i < productList.size(); i++) {
            String purl = productList.get(i).getLink();
            Log.i("product detail link", purl);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse(purl));
            startActivity(intent);


        }

    }

    private Bitmap scaleDownBitmap(Bitmap photo, int newHeight,
                                   Context context) {
        // TODO Auto-generated method stub

        final float densityMultiplier = context.getResources().getDisplayMetrics().density;

        int h = (int) (newHeight * densityMultiplier);
        int w = (int) (h * photo.getWidth() / ((double) photo.getHeight()));

        photo = Bitmap.createScaledBitmap(photo, w, h, true);


        return photo;
    }

    private ArrayList<Product> generateData() throws JSONException {
        try {
            String result = new AsyncTaskParseJson().execute().get();
            JSONObject json = new JSONObject(result);
            JSONArray dataJsonArr = json.getJSONArray("products");

            // loop through all users
            for (int i = 0; i < 5; i++) {

                JSONObject c = dataJsonArr.getJSONObject(i);

                // Storing each json item in variable
                String imageLink = c.getString("imageLink");
                String imagedetail = c.getString("link");

                product.add(new Product(imageLink, imagedetail));


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
            JSONArray dataJsonArr = json.getJSONArray("productInfoList");
            for (int i = 0; i < 5; i++) {
                JSONObject jsonobject = dataJsonArr.getJSONObject(i);
                JSONObject productIdentifier = jsonobject.getJSONObject("productBaseInfo");
                JSONObject category = productIdentifier.getJSONObject("productAttributes");
                JSONObject imageUrl = category.getJSONObject("imageUrls");
                String imageLink = imageUrl.getString("400x400");
                String image = category.getString("productUrl");
                product.add(new Product(imageLink, image));
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
            Log.i("FlipKart response", json.toString());
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


    public String productDetail() {
        try {
            final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();


            String response = SimpleHttpClient.executeHttpPost("/getProducts", postParameters);


            return response;


        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), "Products not available !!!", Toast.LENGTH_LONG).show();
            return "fail";
        }

    }

}
