package com.eStore.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class OrderSummaryActivity extends Activity {
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_summary);
	}
	public void StoreProduct(View view){
		Intent intent= new Intent(this,StoreActivity.class);
		startActivity(intent);
	}
	public void OestoreProfile(View view){
		Intent intent = new Intent(this,ProfileActivity.class);
		startActivity(intent);
	}

}
