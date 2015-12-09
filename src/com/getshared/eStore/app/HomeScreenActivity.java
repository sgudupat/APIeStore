package com.getshared.eStore.app;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class HomeScreenActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_screen);
		int delay = 3000; // delay for 1 sec. 		
		Timer timer = new Timer(); 
		
		timer.schedule(new TimerTask() 
		    { 
		        public void run() 
		        { 
		        	goToNext();  // display the data
		        } 
		    }, delay); 
		
	}

	private void goToNext() {
		// TODO Auto-generated method stub
		
		Intent intent = new Intent(this, CategoryActivity.class);
		startActivity(intent);   
		finish();
	
	}
}