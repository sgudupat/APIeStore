package com.getshared.eStore.app;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.getshared.eStore.domain.Product;
import com.squareup.picasso.Picasso;


public class ProductListAdapter extends BaseAdapter {
	private Context context;
	private Activity mActivity;
	ArrayList<Product> dataList;
	ImageView view;

	public ProductListAdapter(Context context, ArrayList<Product> oslist) {
		this.context = context;
		this.dataList = oslist;
	}

	public ProductListAdapter(Activity activity, Context context, ArrayList<Product> dataList) {

		this.mActivity = (Activity) activity;
		this.context = context;
		this.dataList = dataList;

	}

	public void setObjects(ArrayList<Product> dataList) {
		this.dataList = dataList;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View gridView;
		if (convertView == null) {            
			gridView = new View(context);
			// get layout from mobile.xml
			gridView = inflater.inflate(R.layout.adapter_second, null);

		} else {
			gridView = (View) convertView;
		}
		String rupeeSymbol = (context).getResources().getString(R.string.rs);
		// set value into textview
		TextView textPrice = (TextView) gridView.findViewById(R.id.company);
		textPrice.setText(rupeeSymbol + " " + dataList.get(position).getPrice());
		TextView textcompany = (TextView) gridView.findViewById(R.id.add);
		textcompany.setText(dataList.get(position).getpCompany());
		TextView textView = (TextView) gridView.findViewById(R.id.decline);
		TextView title = (TextView) gridView.findViewById(R.id.name);
		title.setText(dataList.get(position).getName());
		ImageView imageView = (ImageView) gridView.findViewById(R.id.photo);
		imageView.setImageResource(R.drawable.cartdefault);
		textView.setOnClickListener(new LinkProduct(dataList.get(position).getLink()));
		SpannableString content = new SpannableString(dataList.get(position).getLink());
		content.setSpan(new UnderlineSpan(), 0, content.length(), 0);


		{


			Picasso.with(context).load(dataList.get(position).getImage()).placeholder(R.drawable.cartdefault).error(R.drawable.cartdefault).into(imageView);
			imageView.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {

					Intent intent = new Intent(context, ProductDetailActivity.class);
					Bitmap img1 = scaleDownBitmap(dataList.get(position).getTransformedImage(), 150, context);
					Bitmap img2 = scaleDownBitmap(null, 150, context);

					Log.i("img", ""+img1);
					intent.putExtra("bitmapImage1", img1);
					intent.putExtra("bitmapImage2", img2);
					intent.putExtra("bitmapImage3", dataList.get(position).getImage());
					Log.i("bitmapImage3", ""+dataList.get(position).getTransformedImage());
					intent.putExtra("price", dataList.get(position).getPrice());
					intent.putExtra("link", dataList.get(position).getLink());
					intent.putExtra("productInfo", dataList.get(position).getProductInfo());
					intent.putExtra("name", dataList.get(position).getName());
					intent.putExtra("company", dataList.get(position).getpCompany());
					intent.putExtra("category", dataList.get(position).getCategory());
					context.startActivity(intent);
				}
			});

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

	private Bitmap scaleDownBitmap(Bitmap photo, int newHeight, Context context) {
		if (photo != null) {
			final float densityMultiplier = context.getResources().getDisplayMetrics().density;
			int h = (int) (newHeight * densityMultiplier);
			int w = (int) (h * photo.getWidth() / ((double) photo.getHeight()));
			photo = Bitmap.createScaledBitmap(photo, w, h, true);
			return photo;
		}
		return null;
	}

	public class LinkProduct implements View.OnClickListener {
		private String link;


		public LinkProduct(String link) {
			this.link = link;

		}

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.addCategory(Intent.CATEGORY_BROWSABLE);
			intent.setData(Uri.parse(link));

			context.startActivity(intent);
		}
	}


}

