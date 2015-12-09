package com.getshared.eStore.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class HomeScreenActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        try {
            Thread.sleep(3000);
            Intent intent = new Intent(this, CategoryActivity.class);
            startActivity(intent);
        } catch (InterruptedException e) {
        }
    }
}