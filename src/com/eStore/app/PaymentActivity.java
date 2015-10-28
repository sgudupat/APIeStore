package com.eStore.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PaymentActivity extends Activity{
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.payment);
	}
	public void OrderSummary(View view)
	{
		Intent intent = new Intent (this,OrderSummaryActivity.class);
		startActivity(intent);
	}
	public void PestoreProfile(View view){
		Intent intent = new Intent(this,ProfileActivity.class);
		startActivity(intent);
	}

}
