package com.eStore.app;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.client.vote.common.SimpleHttpClient;
import com.client.vote.domain.Address;


public class AddressAdapter extends BaseAdapter implements ListAdapter {
	
    private ArrayList<Address> list = new ArrayList<Address>();
    private  final Context context;
    	  
   

    public AddressAdapter(ArrayList<Address> list, Context context) {
        this.list = list;
        this.context = context;
    }
   

    public int getCount() {
    	Log.i("list size",""+list.size());
        return list.size();
    }

    public Object getItem(int pos) {
        return list.get(pos);
    }
   

    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
        	 Log.i("address list size",""+list.size());
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.address_item, null);
           
            
        }
        Log.i("text", "insideAdapter");
        TextView listItemText = (TextView) view.findViewById(R.id.address_info);  
        Button delete=(Button) view.findViewById(R.id.address_delete);
        Button edit=(Button) view.findViewById(R.id.address_edit);
       
        Log.i("delete", ""+delete);
        String address1 = list.get(position).getAddress1();
        Log.i("list1", address1);
        String address2 = list.get(position).getAddress2();
        Log.i("list2", address2);
        String city=list.get(position).getCity();
        Log.i("list3", city);
        String country=list.get(position).getCountry();
        Log.i("list4", country);
        String state=list.get(position).getState();
        Log.i("list5", state);
        String pinCode=list.get(position).getPinCode();
       Log.i("list6", pinCode);
       String addressId=list.get(position).getAddressId();
        String text=list.get(position).getAddress1()+","+ list.get(position).getAddress2()+","+list.get(position).getCity()+","+list.get(position).getState()+","+list.get(position).getCountry()+","+list.get(position).getPinCode()+","+list.get(position).getAddressId();
        Log.i("text", text);
        listItemText.setText(text);   
   
       
        
        
		
	
        //Handle TextView and display string from your address activity
	        edit.setOnClickListener(new AddressListener(address1, address2,city,country,state,pinCode,addressId ));
        
	        return view;
       
       
      
           
      
       // edit.setText((CharSequence) list);
        
      
     
       
		
        
      
       }	
   
	public class AddressListener implements View.OnClickListener {
        private String address1;
        private String address2;
        private String city;
        private String state;
        private String country;
        private String pinCode;
		private String addressId;
		

        public AddressListener(String address1, String address2, String city, String state, String country,String pinCode,String addressId) {
            this.address1 = address1;
            this.address2 = address2;
            this.city = city;
            this.state = state;
            this.country = country;
            this.pinCode = pinCode;
            this.addressId=addressId;
            
        }

        @Override
        public void onClick(View v) {
        	Log.i("onclick", address1);
        	

            //final Context context= this;
    		 LayoutInflater li = LayoutInflater.from(context);
    	        View addressView = li.inflate(R.layout.add_address, null);
    	        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
    	        // set prompts.xml to alert dialog builder
    	        alertDialogBuilder.setView(addressView);
    	        final EditText modifyAddressId = (EditText) addressView.findViewById(R.id.modify_addressid);
    	        final EditText modifyAddress1 = (EditText) addressView.findViewById(R.id.modify_address1);
    	        final EditText modifyAddress2 =(EditText) addressView.findViewById(R.id.modify_address2);
    	        final EditText modifyCity =(EditText) addressView.findViewById(R.id.modify_city);
    	        final EditText modifyState=(EditText) addressView.findViewById(R.id.modify_state);
    	        final  EditText modifyCountry=(EditText) addressView.findViewById(R.id.modify_country);
    	        final EditText modifyPincode=(EditText) addressView.findViewById(R.id.modify_pincode);
    	        modifyAddress1.setText(address1);
    	        modifyAddress2.setText(address2);
    	        modifyCity.setText(city);
    	        modifyState.setText(state);
    	        modifyCountry.setText(country);
    	        modifyPincode.setText(pinCode);
    	        modifyAddressId.setText(addressId);
    	     
    	        
    	      
    	        // set dialog message
    	        alertDialogBuilder.setCancelable(false).setPositiveButton("Save",
    	                new DialogInterface.OnClickListener() {
    	                    public void onClick(DialogInterface dialog, int id) {
    	                    	
    	                    	  final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
    	                    	  postParameters.add(new BasicNameValuePair("addressId",modifyAddressId.getText().toString()));
    	            			  postParameters.add(new BasicNameValuePair("address1",modifyAddress1.getText().toString()));
    	            			  postParameters.add(new BasicNameValuePair("address2",modifyAddress2.getText().toString()));
    	            			  postParameters.add(new BasicNameValuePair("city",modifyCity.getText().toString()));
    	            			  postParameters.add(new BasicNameValuePair("state",modifyState.getText().toString()));
    	            			  postParameters.add(new BasicNameValuePair("country",modifyCountry.getText().toString()));
    	            			  postParameters.add(new BasicNameValuePair("pinCode",modifyPincode.getText().toString()));
    	            			 
    	            			  
    	            			try {
    	            				Log.i("Inside Try Block", "Modify");
    	            				String response = SimpleHttpClient.executeHttpPost("/modifyAddress", postParameters);
    	            				
    	            				
    	            				 SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    	            	                SharedPreferences.Editor editor = prefs.edit();
    	            	                // JSONObject jsonObject = new JSONObject(response);
    	            	                editor.putString("addressId", addressId);
    	            	                editor.putString("address1", modifyAddress1.getText().toString());
    	            	                editor.putString("address2", modifyAddress2.getText().toString());
    	            	                editor.putString("city", modifyCity.getText().toString());
    	            	                editor.putString("state", modifyState.getText().toString());
    	            	                editor.putString("country", modifyCountry.getText().toString());
    	            	              editor.putString("pinCode", modifyPincode.getText().toString());
    	            	                Log.i("modifyAddress", address1);
    	            	                editor.commit();
    	            	                

    	            			} catch (Exception e) {
    	            				Log.i("Response 2:Error:", e.getMessage());
    	            	            Toast.makeText(getApplicationContext(), "Update Failed, Please Retry !!!", Toast.LENGTH_LONG).show();
    	            			}
    	            		
    	                    	
    	                        // get user input and set it to result edit text
    	                        //String aotp = userInput.getText().toString();
    	                        //Log.i("alert dialog otp", aotp);
    	                      
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
        	
            //Intent intent = new Intent(context, ProfileAddressActivity.class);
            //intent.putExtra("address1", address1);
            //intent.putExtra("address2", address2);
            //intent.putExtra("city", city);
            //intent.putExtra("state", state);
            //intent.putExtra("country", country);
            //intent.putExtra("pinCode", pinCode);
            //context.startActivity(intent);
        }
 
        
      

        public void replaceContentView(String id, Intent newIntent) {
            View view = ((ActivityGroup) context).getLocalActivityManager().startActivity(id, newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
            ((Activity) context).setContentView(view);
        }

		
		
	  
   
}private Context getApplicationContext() {
	// TODO Auto-generated method stub
	return null;
}


	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
}