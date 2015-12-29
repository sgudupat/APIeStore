package com.getshared.eStore.app;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class HomeScreenActivity extends Activity {

    final Context context = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        isNetworkAvailable(context);
        int delay = 3000; // delay for 1 sec.
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                goToNext();  // display the data
            }
        }, delay);
    }

    private void goToNext() {
        Intent intent = new Intent(this, CategoryActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean isNetworkAvailable(Context context) {
        Log.i("network connection", "network connectivity");
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        Log.w("INTERNET:", String.valueOf(i));
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            Log.w("INTERNET:", "connected!");
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e("register", e.getMessage() + "");
            Toast.makeText(getApplicationContext(), "No Network Connectivity, Please check again!!!", Toast.LENGTH_LONG).show();
        }
        return false;
    }
}