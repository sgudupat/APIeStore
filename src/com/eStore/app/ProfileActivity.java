package com.eStore.app;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.client.vote.common.SimpleHttpClient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends Activity {
	String username;
	String name;
	String mobile;
	String email;
	String response = "";
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		
		SharedPreferences preferences =PreferenceManager.getDefaultSharedPreferences(this);
		username= preferences.getString("username", "");
		name=preferences.getString("username", "");
		mobile=preferences.getString("mobile", "");
		email=preferences.getString("email", "");
		/*mobile=preferences.getString("mobile", "");
		email=preferences.getString("email", "");*/
		
		
		String address1= preferences.getString("address1", "");
		String address2= preferences.getString("address2", "");
		String city=preferences.getString("city", "");
		String state=preferences.getString("state", "");
		String pincode=preferences.getString("pinCode", "");
		String country=preferences.getString("country", "");
		TextView profileName=(TextView) findViewById(R.id.profile_name);
		TextView profileMobile=(TextView) findViewById(R.id.profile_mobile);
		TextView profileEmail=(TextView) findViewById(R.id.profile_Email);
		EditText profileAddress1 =(EditText) findViewById(R.id.address1);
		EditText profileAddress2 =(EditText) findViewById(R.id.address2);
		EditText profileCity =(EditText) findViewById(R.id.profile_city);
		EditText profileState =(EditText) findViewById(R.id.state);
		EditText profileCountry=(EditText) findViewById(R.id.profile_country);
		EditText profilePincode=(EditText) findViewById(R.id.pincode);
		profileAddress1.setText(address1);
		profileAddress2.setText(address2);
		profileCity.setText(city);
		profileState.setText(state);
		profileCountry.setText(country);
		profilePincode.setText(pincode);
		final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("username", username));

		try{response = SimpleHttpClient.executeHttpPost("/getUser", postParameters);
			JSONObject jsonobject = new JSONObject(response);
        String name = jsonobject.getString("user_name");
        String mobile = jsonobject.getString("mobile");
        String email = jsonobject.getString("email");
        profileName.setText(name);
		profileMobile.setText(mobile);
		profileEmail.setText(email);}catch (Exception e){
			
		}
		
		
		
	}
	public void StoreActivity(View view)
	{
		Intent intent = new Intent(this, StoreActivity.class);
		startActivity(intent);
	}
	public void profile(View view){
		EditText profileAddress1 =(EditText) findViewById(R.id.address1);
		EditText profileAddress2 =(EditText) findViewById(R.id.address2);
		EditText profileCity =(EditText) findViewById(R.id.profile_city);
		EditText profileState =(EditText) findViewById(R.id.state);
		EditText profileCountry=(EditText) findViewById(R.id.profile_country);
		EditText profilePincode=(EditText) findViewById(R.id.pincode);
		 Log.i("address1", profileAddress1.getText().toString());
	        Log.i("address2", profileAddress2.getText().toString());
	        Log.i("city", profileCity.getText().toString());
	        Log.i("state", profileState.getText().toString());
	        Log.i("country", profileCountry.getText().toString());
	        Log.i("pinCode", profilePincode.getText().toString());
	        final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	        postParameters.add(new BasicNameValuePair("username", username));
	        postParameters.add(new BasicNameValuePair("address1", profileAddress1.getText().toString()));
	        postParameters.add(new BasicNameValuePair("address2", profileAddress2.getText().toString()));
	        postParameters.add(new BasicNameValuePair("city", profileCity.getText().toString()));
	        postParameters.add(new BasicNameValuePair("state", profileState.getText().toString()));
	        postParameters.add(new BasicNameValuePair("country", profileCountry.getText().toString()));
	        postParameters.add(new BasicNameValuePair("pinCode", profilePincode.getText().toString()));        
	        final Context context = this;
	        try {
	            String response = SimpleHttpClient.executeHttpPost("/addAddress", postParameters);
	            Log.i("Response:", response);

	            if (response.contains("success")) {
	                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

	                SharedPreferences.Editor editor = prefs.edit();

	                editor.putString("address1", profileAddress1.getText().toString());
	                editor.putString("address2", profileAddress2.getText().toString());
	                editor.putString("city", profileCity.getText().toString());
	                editor.putString("state", profileState.getText().toString());
	                editor.putString("country", profileCountry.getText().toString());
	                editor.putString("pinCode", profilePincode.getText().toString());
	                
	                editor.commit();
	                Intent intent = new Intent(this, StoreActivity.class);
	                startActivity(intent);
	            } else {
	                Toast.makeText(getApplicationContext(), "Update Failed, Please Retry !!!", 

Toast.LENGTH_LONG).show();
	            }
	        } catch (Exception e) {
	            Log.i("Response 2:Error:", e.getMessage());
	            Toast.makeText(getApplicationContext(), "Update Failed, Please Retry !!!", 

Toast.LENGTH_LONG).show();
	        }
	}

}
