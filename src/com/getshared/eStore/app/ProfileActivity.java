package com.getshared.eStore.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.getshared.eStore.app.common.SimpleHttpClient;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileActivity extends Activity {
    String username;
    String name;
    String mobile;
    String email;
    String response = "";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        username = preferences.getString("username", "");
        name = preferences.getString("username", "");
        mobile = preferences.getString("mobile", "");
        email = preferences.getString("email", "");

        TextView profileName = (TextView) findViewById(R.id.profile_name);
        TextView profileMobile = (TextView) findViewById(R.id.profile_mobile);
        TextView profileEmail = (TextView) findViewById(R.id.profile_Email);
        final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("username", username));

        try {
            response = SimpleHttpClient.executeHttpPost("/getUser", postParameters);
            JSONObject jsonobject = new JSONObject(response);
            String name = jsonobject.getString("user_name");
            String mobile = jsonobject.getString("mobile");
            String email = jsonobject.getString("email");
            profileName.setText(name);
            profileMobile.setText(mobile);
            profileEmail.setText(email);
        } catch (Exception e) {
        }
    }

    public void StoreActivity(View view) {
        Intent intent = new Intent(this, StoreActivity.class);
        startActivity(intent);
    }

    public void profile(View view) {

        final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("username", username));
        final Context context = this;
        try {
            String response = SimpleHttpClient.executeHttpPost("/addAddress", postParameters);
            Log.i("Response:", response);

            if (response.contains("success")) {
                Intent intent = new Intent(this, StoreActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Update Failed, Please Retry !!!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.i("Response 2:Error:", e.getMessage());
            Toast.makeText(getApplicationContext(), "Update Failed, Please Retry !!!", Toast.LENGTH_LONG).show();
        }
    }
}