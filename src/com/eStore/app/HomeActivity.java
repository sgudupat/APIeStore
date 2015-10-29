package com.eStore.app;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.eStore.app.common.SimpleHttpClient;

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

public class HomeActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
       final Context context= this;
	   // private Context context;
		ArrayList<Category> items = new ArrayList<Category>();
		int clientId=2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

    	/*ListView listView = (ListView) findViewById(R.id.categoryListview);
			// Adding items to list view
			// 1. pass context and data to the custom adapter        
			final CategoryAdapter adapter = new CategoryAdapter(generateData(clientId), this);
			Log.i("After adapter excecution", "excecution");
			listView.setAdapter(adapter);*/
        
    }
   

 
private List<Category> generateData(int clientId2) {
		// TODO Auto-generated method stub
		String cName= "Sachin";
		String cUrl="http://www.androidhive.info/2011/08/android-tab-layout-tutorial/";
		items.add(new Category(cName,cUrl));
		return items;
	}





    public void signinEstore(View view){
    	final EditText userName = (EditText) findViewById(R.id.editText1);
	
	 	final EditText password = (EditText) findViewById(R.id.editText2);
	 	 try {
             final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
             postParameters.add(new BasicNameValuePair("username", userName.getText().toString()));
             postParameters.add(new BasicNameValuePair("password", password.getText().toString()));
             String response = SimpleHttpClient.executeHttpPost("/loginUser", postParameters);
             Log.i("Response:", response);
            JSONObject jsonobject = new JSONObject(response);
             SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
             SharedPreferences.Editor editor = prefs.edit();
             Log.i("username", (String) jsonobject.get("user_name"));
             editor.putString("username", (String) jsonobject.get("user_name"));
             editor.putString("email", (String) jsonobject.get("email"));
             editor.putString("mobile", (String) jsonobject.get("mobile"));
             editor.commit();
             Intent intent= new Intent(this, StoreActivity.class);
    	startActivity(intent);
         } catch (Exception e) {
             Log.e("register", e.getMessage() + "");
             Toast.makeText(getApplicationContext(), "Login Failed, Please Retry !!!", Toast.LENGTH_LONG).show();
         }
    	
    }
    public void EstoreRegister(View view){
    	Intent intent = new Intent(this, RegisterActivity.class);
    	startActivity(intent);
    }
    public void EstoreForgotPassword(View view){
    	Intent intent =new Intent(this, ForgotPasswordActivity.class);
    	startActivity(intent);
    }
	


	

}