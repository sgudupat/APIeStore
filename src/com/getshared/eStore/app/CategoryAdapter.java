package com.getshared.eStore.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class CategoryAdapter extends BaseAdapter implements ListAdapter {

	private List<Category> list = new ArrayList<Category>();
	private List<Category> mData = new ArrayList<Category>();
	HashMap<String,ArrayList<String>> cList =new HashMap<String,ArrayList<String>>();
	private String[] cKeys;

	private Context context;

	CategoryAdapter(Context context, List<Category> list) {
		this.list = list;
		this.context = context;
	}
	CategoryAdapter(Context context,  HashMap<String,ArrayList<String>> clist) {
		this.cList = clist;
		this.context = context;
		cKeys = cList.keySet().toArray(new String[clist.size()]);
	}

	@Override
	public int getCount() {
		return cList.size();
	}

	@Override
	public ArrayList<String> getItem(int position) {       
		return cList.get(cKeys[position]);

	}

	@Override
	public long getItemId(int position) {      
		return 0;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.category_item, null);
		}

		TextView catName = (TextView) view.findViewById(R.id.name_producttext);        
		ImageView imgView = (ImageView) view.findViewById(R.id.imageView1);
		String key = cKeys[position];
		ArrayList<String> Value = getItem(position);    
		if(key.toLowerCase().contains("furniture")){
			catName.setText("Furniture");        	
			imgView.setImageResource(R.drawable.furnituresample);
		}
		if(key.toLowerCase().contains("eyewear")){
			catName.setText("Eyewear");        	
			imgView.setImageResource(R.drawable.eyewearnormal);
		}
		if(key.toLowerCase().contains("apparels")){
			catName.setText("Apparels");        	
			imgView.setImageResource(R.drawable.apprel_normal);
		}
		if(key.toLowerCase().contains("electronics")){
			catName.setText("Electronics");        	
			imgView.setImageResource(R.drawable.electronicsnormal);
		}
		if(key.toLowerCase().contains("jewellery")){
			catName.setText("Jewellery");        	
			imgView.setImageResource(R.drawable.jewellerynormal);
		}
		if(key.toLowerCase().contains("fragrances")){
			catName.setText("Fragrances");        	
			imgView.setImageResource(R.drawable.fragrance_normal);
		}
		return view;
	}
}