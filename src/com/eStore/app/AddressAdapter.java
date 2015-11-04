package com.eStore.app;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

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

import com.eStore.app.common.SimpleHttpClient;
import com.eStore.domain.Address;


public class AddressAdapter extends BaseAdapter implements ListAdapter {

	private ArrayList<Address> list = new ArrayList<Address>();
	private final Context context;


	public AddressAdapter(ArrayList<Address> list, Context context) {
		this.list = list;
		this.context = context;
	}


	public int getCount() {
		Log.i("list size", "" + list.size());
		return list.size();
	}

	public Object getItem(int pos) {
		return list.get(pos);
	}


	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {          
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.address_item, null);


		}       
		TextView listItemText = (TextView) view.findViewById(R.id.address_info);
		Button delete = (Button) view.findViewById(R.id.address_delete);
		Button edit = (Button) view.findViewById(R.id.address_edit);

		String address1 = list.get(position).getAddress1();        
		String address2 = list.get(position).getAddress2();       
		String city = list.get(position).getCity();       
		String country = list.get(position).getCountry();      
		String state = list.get(position).getState();      
		String pinCode = list.get(position).getPinCode();      
		String addressId = list.get(position).getAddressId();
		String text = list.get(position).getAddress1() + "," + list.get(position).getAddress2() + "," + list.get(position).getCity() + "," + list.get(position).getState() + "," + list.get(position).getCountry() + "," + list.get(position).getPinCode() + "," + list.get(position).getAddressId();       
		listItemText.setText(text);       
		edit.setOnClickListener(new AddressListener(address1, address2, city, country, state, pinCode, addressId));
		return view;
	}

	public class AddressListener implements View.OnClickListener {
		private String address1;
		private String address2;
		private String city;
		private String state;
		private String country;
		private String pinCode;
		private String addressId;


		public AddressListener(String address1, String address2, String city, String state, String country, String pinCode, String addressId) {
			this.address1 = address1;
			this.address2 = address2;
			this.city = city;
			this.state = state;
			this.country = country;
			this.pinCode = pinCode;
			this.addressId = addressId;

		}

		@Override
		public void onClick(View v) {          
			LayoutInflater li = LayoutInflater.from(context);
			View addressView = li.inflate(R.layout.add_address, null);
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);           
			alertDialogBuilder.setView(addressView);
			final EditText modifyAddressId = (EditText) addressView.findViewById(R.id.modify_addressid);
			final EditText modifyAddress1 = (EditText) addressView.findViewById(R.id.modify_address1);
			final EditText modifyAddress2 = (EditText) addressView.findViewById(R.id.modify_address2);
			final EditText modifyCity = (EditText) addressView.findViewById(R.id.modify_city);
			final EditText modifyState = (EditText) addressView.findViewById(R.id.modify_state);
			final EditText modifyCountry = (EditText) addressView.findViewById(R.id.modify_country);
			final EditText modifyPincode = (EditText) addressView.findViewById(R.id.modify_pincode);
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
					postParameters.add(new BasicNameValuePair("addressId", modifyAddressId.getText().toString()));
					postParameters.add(new BasicNameValuePair("address1", modifyAddress1.getText().toString()));
					postParameters.add(new BasicNameValuePair("address2", modifyAddress2.getText().toString()));
					postParameters.add(new BasicNameValuePair("city", modifyCity.getText().toString()));
					postParameters.add(new BasicNameValuePair("state", modifyState.getText().toString()));
					postParameters.add(new BasicNameValuePair("country", modifyCountry.getText().toString()));
					postParameters.add(new BasicNameValuePair("pinCode", modifyPincode.getText().toString()));


					try {
						Log.i("Inside Try Block", "Modify");
						String response = SimpleHttpClient.executeHttpPost("/modifyAddress", postParameters);


						SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
						SharedPreferences.Editor editor = prefs.edit();                              
						editor.putString("addressId", addressId);
						editor.putString("address1", modifyAddress1.getText().toString());
						editor.putString("address2", modifyAddress2.getText().toString());
						editor.putString("city", modifyCity.getText().toString());
						editor.putString("state", modifyState.getText().toString());
						editor.putString("country", modifyCountry.getText().toString());
						editor.putString("pinCode", modifyPincode.getText().toString());                               
						editor.commit();


					} catch (Exception e) {                              
						Toast.makeText(getApplicationContext(), "Update Failed, Please Retry !!!", Toast.LENGTH_LONG).show();
					}

				}


			}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}
			});

			AlertDialog alertDialog = alertDialogBuilder.create();            
			alertDialog.show();

		}


		public void replaceContentView(String id, Intent newIntent) {
			View view = ((ActivityGroup) context).getLocalActivityManager().startActivity(id, newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
			((Activity) context).setContentView(view);
		}


	}

	private Context getApplicationContext() {      
		return null;
	}


	@Override
	public long getItemId(int position) {       
		return 0;
	}
}