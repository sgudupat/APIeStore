package com.getshared.eStore.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductDetailActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);
        Intent intent = getIntent();
        final Bitmap bitmap = (Bitmap) intent.getParcelableExtra("bitmapImage");
        String name = intent.getStringExtra("name");
        String price = intent.getStringExtra("price");
        final String Link = intent.getStringExtra("link");
        String productInf = intent.getStringExtra("productInfo");
        TextView productName = (TextView) findViewById(R.id._pinfo);
        TextView productPrice = (TextView) findViewById(R.id._cinfo);
        TextView productInfo = (TextView) findViewById(R.id.productdetail_info);
        productName.setText(name);
        productPrice.setText(price);
        productInfo.setText(productInf);
    
     
        final ImageView Demo_button = (ImageView)findViewById(R.id.productdetail_image);
        Demo_button.setImageBitmap(bitmap);
        final ImageView second_button = (ImageView)findViewById(R.id.test_image1);
        second_button.setImageBitmap(bitmap);
        final ImageView third_button = (ImageView)findViewById(R.id.test_photo2);
        final ImageView four_button = (ImageView)findViewById(R.id.test_photo3);
        final ImageView five_button = (ImageView)findViewById(R.id.test_photo1);
        final ImageView six_button = (ImageView)findViewById(R.id.imageView1);

    

        second_button.setOnClickListener(new OnClickListener() {
           public void onClick(View v) {
        	   
        	   Demo_button.setImageBitmap(bitmap);
              
           }
        });

        third_button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
         	   Demo_button.setImageResource(R.drawable.image16);
         	 
            }
         });
       five_button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
         	   Demo_button.setImageResource(R.drawable.image4);
         	 
            }
         });
      four_button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
         	   Demo_button.setImageResource(R.drawable.image5);
         	  
            }
         });
      six_button.setOnClickListener(new OnClickListener() {
          public void onClick(View v) {
       	   Demo_button.setImageResource(R.drawable.image);
       	  
          }
       });
			
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
		// TODO Auto-generated method stub
		return null;
	}

	public void DestoreProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

}
