package com.getshared.eStore.app;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.getshared.eStore.domain.Product;

public class ProductListAdapter extends BaseAdapter {
	private Context context;
	ArrayList<Product> dataList;
	ImageView view;

	public ProductListAdapter(Context context, ArrayList<Product> oslist) {
	    this.context = context;
	    this.dataList = oslist;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

	    LayoutInflater inflater = (LayoutInflater) context
	            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	    View gridView;
	

	    if (convertView == null) {
Log.i("adapter", "adapter");
	        gridView = new View(context);

	        // get layout from mobile.xml
	        gridView = inflater.inflate(R.layout.adapter_second, null);

	        // set value into textview
	        TextView textView = (TextView) gridView
	                .findViewById(R.id.name);
	       // textView.setText(dataList.get(position).getName());
	        // set image based on selected text
	        ImageView imageView = (ImageView) gridView
	                .findViewById(R.id.photo_image);


	       {
	            imageView.setImageBitmap(dataList.get(position).getTransformedImage());
	    		
	        }

	    } else {
	        gridView = (View) convertView;
	    }

	    return gridView;
	}

	@Override
	public int getCount() {
	    return dataList.size();
	}

	@Override
	public Product getItem(int position) {
	    return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
	    return 0;
	}

	}