package com.eStore.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductDetailActivity extends Activity {
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);
		Intent intent = getIntent();
		Bitmap bitmap = (Bitmap) intent.getParcelableExtra("bitmapImage");
		String name=intent.getStringExtra("name");
		
		String price=intent.getStringExtra("price");
		final String Link=intent.getStringExtra("Link");
		String productInf=intent.getStringExtra("productInfo");
		 TextView productName = (TextView) findViewById(R.id.product_name);
		 TextView productPrice = (TextView) findViewById(R.id.product_price);
		 TextView productInfo = (TextView) findViewById(R.id.product_description);
		 productName.setText(name);
		 productPrice.setText(price);
		 productInfo.setText(productInf);
		 
		
		 
		ImageView view = (ImageView)findViewById(R.id.photo);
		view.setImageBitmap(bitmap);
		ImageView newPage1 = (ImageView)view.findViewById(R.id.photo);
        newPage1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
            	
                 Intent intent = new Intent();
                 intent.setAction(Intent.ACTION_VIEW);
                 intent.addCategory(Intent.CATEGORY_BROWSABLE);
                 intent.setData(Uri.parse(Link));
                 startActivity(intent);
            }
        });
	}
	/*public void buyNow(View view){
		 String purl = productList.get(i).getLink();
         Log.i("product detail link", purl);
         Intent intent = new Intent();
         intent.setAction(Intent.ACTION_VIEW);
         intent.addCategory(Intent.CATEGORY_BROWSABLE);
         intent.setData(Uri.parse(purl));
         startActivity(intent);
	}*/
	
	public void DestoreProfile(View view){
		Intent intent = new Intent(this,ProfileActivity.class);
		startActivity(intent);
	}

}