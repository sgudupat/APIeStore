package com.getshared.eStore.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.getshared.eStore.app.common.SimpleHttpClient;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeActivity extends Activity {
	final Context context = this;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	public void signinEstore(View view) {
		final EditText userName = (EditText) findViewById(R.id.editText1);
		final EditText password = (EditText) findViewById(R.id.editText2);
		try {
			final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("username", userName.getText().toString()));
			postParameters.add(new BasicNameValuePair("password", password.getText().toString()));
			String response = SimpleHttpClient.executeHttpPost("/loginUser", postParameters);            
			JSONObject jsonobject = new JSONObject(response);
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
			SharedPreferences.Editor editor = prefs.edit();           
			editor.putString("username", (String) jsonobject.get("user_name"));
			editor.putString("email", (String) jsonobject.get("email"));
			editor.putString("mobile", (String) jsonobject.get("mobile"));
			editor.commit();
			Intent intent = new Intent(this, MenuActivity.class);
			startActivity(intent);
		} catch (Exception e) {
			Log.e("register", e.getMessage() + "");
			Toast.makeText(getApplicationContext(), "Login Failed, Please Retry !!!", Toast.LENGTH_LONG).show();
			//This is temporary
			Intent intent = new Intent(this, MenuActivity.class);
			startActivity(intent);
		}
	}

	public void EstoreRegister(View view) {
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
	}
}