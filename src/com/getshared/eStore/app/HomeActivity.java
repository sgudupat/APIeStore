package com.getshared.eStore.app;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.getshared.eStore.app.common.SimpleHttpClient;



public class HomeActivity extends Activity  {
   final Context context=this;
	public static int count=0;
	int[] drawablearray=new int[]{R.drawable.bg,R.drawable.image6,R.drawable.image1,R.drawable.image2,R.drawable.image3,R.drawable.image4,R.drawable.image5,R.drawable.image16,R.drawable.image7,R.drawable.image8,R.drawable.image9,R.drawable.image10,R.drawable.image11,R.drawable.image12,R.drawable.image13,R.drawable.image14};
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
   
        
     
    
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
             Intent intent= new Intent(this, CategoryActivity.class);
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