package com.eStore.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductDetailActivity extends Activity {
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_detail);
		Intent intent = getIntent();
		Bitmap bitmap = (Bitmap) intent.getParcelableExtra("bitmapImage");
		String name=intent.getStringExtra("name");
		String info=intent.getStringExtra("specification");
		String price=intent.getStringExtra("price");
		 TextView textprice = (TextView) findViewById(R.id.pd_productprice);
		 TextView textinfo = (TextView) findViewById(R.id.pd_productinfo);
		 TextView textname = (TextView) findViewById(R.id.pd_productname);
		 textprice.setText(price);
		 textinfo.setText(info);
		 textname.setText(name);
		
		 
		ImageView view = (ImageView)findViewById(R.id.viewImage);
		view.setImageBitmap(bitmap);
	}
	public void PaymentPage(View view){
		Intent intent = new Intent(this,PaymentActivity.class);
		startActivity(intent);
	}
	public void DestoreProfile(View view){
		Intent intent = new Intent(this,ProfileActivity.class);
		startActivity(intent);
	}

}
