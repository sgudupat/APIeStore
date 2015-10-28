package com.eStore.app;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.client.vote.common.SimpleHttpClient;

public class RegisterActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
	}

	public void EstoreHomePage(View view) {
		// Intent intent = new Intent(this,HomeActivity.class);
		// startActivity(intent);
		final EditText userName = (EditText) findViewById(R.id.register_name);
		final EditText email = (EditText) findViewById(R.id.register_email);
	 	final EditText password = (EditText) findViewById(R.id.register_password);
		final EditText rpassword = (EditText) findViewById(R.id.register_confirmpassword);
		final EditText mobile = (EditText) findViewById(R.id.register_mobile);
		 final String deviceId = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
		  final Context context = this;
		 String chars = "0123456789";
	        final int PW_LENGTH = 5;
	        Random rnd = new SecureRandom();
	        StringBuilder pass = new StringBuilder();
	        for (int i = 0; i < PW_LENGTH; i++) {
	            pass.append(chars.charAt(rnd.nextInt(chars.length())));
	        }
	        Log.i("otp password", pass.toString());
	        final String otp = pass.toString();
	        try {
	            String phoneNo = mobile.getText().toString();
	            SmsManager smsManager = SmsManager.getDefault();
	            smsManager.sendTextMessage(phoneNo, null, otp, null, null);
	            Toast.makeText(getApplicationContext(), "SMS Sent!", Toast.LENGTH_LONG).show();
	        } catch (Exception e) {
	            Toast.makeText(getApplicationContext(), "SMS failed, please try again later!", Toast.LENGTH_LONG).show();
	        }
	        LayoutInflater li = LayoutInflater.from(context);
	        View promptsView = li.inflate(R.layout.prompts, null);
	        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
	        // set prompts.xml to alert dialog builder
	        alertDialogBuilder.setView(promptsView);
	        final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
	        // set dialog message
	        alertDialogBuilder.setCancelable(false).setPositiveButton("OK",
	                new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int id) {
	                        // get user input and set it to result edit text
	                        String aotp = userInput.getText().toString();
	                        Log.i("alert dialog otp", aotp);
	                        if (aotp.equals(otp) && (password.getText().toString().equals(rpassword.getText().toString()))) {
	                            try {
	                                final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	                                postParameters.add(new BasicNameValuePair("username", userName.getText().toString()));
	                                postParameters.add(new BasicNameValuePair("password", password.getText().toString()));
	                                postParameters.add(new BasicNameValuePair("mobile", mobile.getText().toString()));
	                                postParameters.add(new BasicNameValuePair("email", email.getText().toString()));
	                                postParameters.add(new BasicNameValuePair("deviceId", deviceId));
	                                String response = SimpleHttpClient.executeHttpPost("/registerUser", postParameters);
	                                Log.i("Response:", response);
	                                JSONObject jsonobject = new JSONObject(response);
	                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
	                                SharedPreferences.Editor editor = prefs.edit();
	                                Log.i("username", (String) jsonobject.get("user_name"));
	                                editor.putString("username", (String) jsonobject.get("user_name"));
	                                editor.putString("email", (String) jsonobject.get("email"));
	                                editor.putString("mobile", (String) jsonobject.get("mobile"));
	                                editor.commit();
	                            } catch (Exception e) {
	                                Log.e("register", e.getMessage() + "");
	                                Toast.makeText(getApplicationContext(), "Login Failed, Please Retry !!!", Toast.LENGTH_LONG).show();
	                            }
	                        }
	                    }
	                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int id) {
	                dialog.cancel();
	            }
	        });
	        // create alert dialog
	        AlertDialog alertDialog = alertDialogBuilder.create();
	        // show it
	        alertDialog.show();
	    }
	
	public void  checkAvailability(View view){
		final EditText userName = (EditText) findViewById(R.id.register_name);
	    try {
            final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("username", userName.getText().toString()));
       
            String response = SimpleHttpClient.executeHttpPost("/isAvailable", postParameters);
            Log.i("Response:", response);
            
            if(response.contains("success")){
            	 Toast.makeText(getApplicationContext(), "User name available !!!", Toast.LENGTH_LONG).show();
            }
            else
            	Toast.makeText(getApplicationContext(), "Username not available !!!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e("register", e.getMessage() + "");
            Toast.makeText(getApplicationContext(), "Username not available !!!", Toast.LENGTH_LONG).show();
        }
		
	}

	

}
