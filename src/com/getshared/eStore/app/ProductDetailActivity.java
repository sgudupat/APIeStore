package com.getshared.eStore.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class ProductDetailActivity extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery);
		
		Intent intent = getIntent();
		/*final Bitmap bitmap = (Bitmap) intent.getParcelableExtra("bitmapImage1");
		final Bitmap bitmap2 = (Bitmap) intent.getParcelableExtra("bitmapImage2");*/
		String name = intent.getStringExtra("name");
		String image = intent.getStringExtra("bitmapImage3");
		String price = intent.getStringExtra("price");
		String rupeeSymbol = getResources().getString(R.string.rs);
		final String Link = intent.getStringExtra("link");
		String productInf = intent.getStringExtra("productInfo");

		TextView productNameField = (TextView) findViewById(R.id.productName);
		productNameField.setText(name);

		TextView productPriceField = (TextView) findViewById(R.id.productPrice);
		productPriceField.setText(rupeeSymbol + " " + price);

		TextView productInfo = (TextView) findViewById(R.id.productdetail_info);
		productInfo.setText(productInf);
 
		  ImageView productDetailImageView = (ImageView) findViewById(R.id.productdetail_image);
		 if(image != null){
		 Picasso.with(ProductDetailActivity.this).load(image).into(productDetailImageView);
		 }
		 else
		 {
			 Picasso.with(ProductDetailActivity.this).load(R.drawable.cartdefault).into(productDetailImageView); 
		 }
		/*final ImageView second_button = (ImageView) findViewById(R.id.test_photo2);
		second_button.setImageBitmap(bitmap);
		if (bitmap2 != null) {
			final ImageView third_button = (ImageView) findViewById(R.id.test_photo3);
			third_button.setImageBitmap(bitmap2);
			third_button.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					productDetailImageView.setImageBitmap(bitmap2);
				}
			});
			} else {
			final ImageView third_button = (ImageView) findViewById(R.id.test_photo3);
			third_button.setVisibility(View.INVISIBLE);
		}
		second_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				productDetailImageView.setImageBitmap(bitmap);
			}
		});*/

		TextView newPage1 = (TextView) findViewById(R.id._business);
		newPage1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				intent.addCategory(Intent.CATEGORY_BROWSABLE);
				intent.setData(Uri.parse(Link));
				startActivity(intent);
			}
		});
	}

	private OnClickListener OnClickListener() {
		return null;
	}

	public void DestoreProfile(View view) {
		Intent intent = new Intent(this, ProfileActivity.class);
		startActivity(intent);
	}

}