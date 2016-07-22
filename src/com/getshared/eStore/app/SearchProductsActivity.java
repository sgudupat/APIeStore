package com.getshared.eStore.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SearchProductsActivity extends Activity {
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_item);
		Intent intent= getIntent();
		String name=intent.getStringExtra("searchProductName");
		String url=intent.getStringExtra("searchProductUrl");
	}

}
