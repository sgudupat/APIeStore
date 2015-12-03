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
        final Bitmap bitmap = (Bitmap) intent.getParcelableExtra("bitmapImage1");
        final Bitmap bitmap2 = (Bitmap) intent.getParcelableExtra("bitmapImage2");
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

        final ImageView productDetailImageView = (ImageView) findViewById(R.id.productdetail_image);
        productDetailImageView.setImageBitmap(bitmap);
        final ImageView second_button = (ImageView) findViewById(R.id.test_photo2);
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
        //final ImageView four_button = (ImageView)findViewById(R.id.test_photo3);
        //final ImageView five_button = (ImageView)findViewById(R.id.test_photo1);
        //final ImageView six_button = (ImageView)findViewById(R.id.imageView1);

        second_button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                productDetailImageView.setImageBitmap(bitmap);
            }
        });

       /*five_button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                productDetailImageView.setImageBitmap(bitmap);
         	 
            }
         });
      four_button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                productDetailImageView.setImageBitmap(bitmap);
         	  
            }
         });
      six_button.setOnClickListener(new OnClickListener() {
          public void onClick(View v) {
                productDetailImageView.setImageBitmap(bitmap);
          }
       });
			*/
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