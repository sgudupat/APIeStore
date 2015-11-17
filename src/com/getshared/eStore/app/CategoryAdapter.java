package com.getshared.eStore.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends BaseAdapter implements ListAdapter {

    private List<Category> list = new ArrayList<Category>();
   
    private Context context;

    CategoryAdapter(Context context, List<Category> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {

        return list.size();

    }

    @Override
    public Category getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.category_item, null);

        }
        TextView catName = (TextView) view.findViewById(R.id.categoryName);
        TextView catUrl = (TextView) view.findViewById(R.id.category_url);
        ImageView imgView = (ImageView) view.findViewById(R.id.category_image);
        if(list.get(position).getCategoryName().toLowerCase().contains("furniture")){
        	imgView.setImageResource(R.drawable.furniture);
        }
        catName.setText(list.get(position).getCategoryName());
        ArrayList<String> urlList=list.get(position).getUrlList();
        catUrl.setText("");
        for(String s:urlList){
        	catUrl.append("url:\n" + s + "\n");
        }

        return view;
    }

}
