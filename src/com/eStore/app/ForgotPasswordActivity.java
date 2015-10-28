package com.eStore.app;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.client.vote.common.LongRunningGetIO;
import com.client.vote.common.SimpleHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPasswordActivity extends Activity {
public void onCreate(Bundle savedInstanceState){
 super.onCreate(savedInstanceState);
 setContentView(R.layout.forgot_password);
}
public void validateUser(View view)
{
 final EditText userName = (EditText) findViewById(R.id.fp_name);
 final EditText email = (EditText) findViewById(R.id.fp_email);  
 final EditText mobile = (EditText) findViewById(R.id.fp_Mobile);
  try {
         final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
         postParameters.add(new BasicNameValuePair("username", userName.getText().toString()));
    
         String response = SimpleHttpClient.executeHttpPost("/getUser", postParameters);
         Log.i("Response:", response);
         JSONObject jsonobject = new JSONObject(response);
         String uname=(String)jsonobject.getString("user_name");
         String umobile=(String)jsonobject.getString("mobile");
         String uemail=(String)jsonobject.getString("email");
         if (TextUtils.equals(userName.getText().toString(), uname) && TextUtils.equals(email.getText().toString(), uemail) && TextUtils.equals(mobile.getText().toString(), umobile)) {
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
                            if (aotp.equals(otp) ) {
                                try {
                                 String chars = "abcdefghijklmnopqrstuvwxyz" + "0123456789"
                                      + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                              final int PW_LENGTH = 16;
                              Random rnd = new SecureRandom();
                              StringBuilder pass = new StringBuilder();
                              for (int i = 0; i < PW_LENGTH; i++) {
                                  pass.append(chars.charAt(rnd.nextInt(chars.length())));
                              }
                              Log.i("link password", pass.toString());

                              String value = pass.toString();
                           try {
                            final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
                            postParameters.add(new BasicNameValuePair("username", userName.getText().toString()));
                         postParameters.add(new BasicNameValuePair("keyValue", value));
                       
                            String response = SimpleHttpClient.executeHttpPost("/updatePasswordKeyValue", postParameters);
                            Log.i("Response:", response);
                         if (response.contains("success")) {
                          Log.i("email sending", "email sending");
                          String link = "http://52.76.83.72:8080/eStore"
                            + "/password.jsp?key="
                                  + value;
                          Log.i("password link", link);
                          new LongRunningGetIO(email.getText().toString(), link)
                                  .execute();
                      }
                            
                         
                        } catch (Exception e) {
                            Log.e("register", e.getMessage() + "");
                            Toast.makeText(getApplicationContext(), "Update keyword failed !!!", Toast.LENGTH_LONG).show();
                        }
                                   
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
     
     } catch (Exception e) {
         Log.e("register", e.getMessage() + "");
         Toast.makeText(getApplicationContext(), "Username not available !!!", Toast.LENGTH_LONG).show();
     }
 
}
}