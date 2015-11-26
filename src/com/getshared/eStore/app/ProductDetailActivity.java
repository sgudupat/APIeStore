package com.getshared.eStore.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
       /* final ImageView   v1 = (ImageView) findViewById(R.id.productdetail_image);
          final ImageView   v2 = (ImageView) findViewById(R.id.test_image1);
          final RelativeLayout  rl_main = (RelativeLayout) findViewById(R.id.product_relative);*/
      /* findViewById(R.id.test_image1).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				v1.bringToFront();
				 rl_main.invalidate();
				Log.i("test", "msg");
			}
		});
       findViewById(R.id.productdetail_image).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				v2.bringToFront();
				
				Log.i("test", "msg");
			}
		});*/
 /*v2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				v.bringToFront();
				
				
			}
		});*/
        	
       
           
        /*ImageView productView = (ImageView) findViewById(R.id.productdetail_image);
        productView.setImageBitmap(bitmap); */
       /*
        final ImageView v = (ImageView) findViewById(R.id.test_image1);
        v.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
            	 switch (arg1.getAction()) {
                 case MotionEvent.ACTION_DOWN: {
                    v.setImageResource(R.drawable.bg);
                     break;
                 }
                 case MotionEvent.ACTION_CANCEL:{
                     v.setImageResource(R.drawable.image10);
                     break;
                 }
                 }
                return true;
            }  });*/
     
        final ImageView Demo_button = (ImageView)findViewById(R.id.productdetail_image);
        Demo_button.setImageBitmap(bitmap);
        final ImageView second_button = (ImageView)findViewById(R.id.test_image1);
        second_button.setImageBitmap(bitmap);
        final ImageView third_button = (ImageView)findViewById(R.id.test_photo2);
        final ImageView four_button = (ImageView)findViewById(R.id.test_photo3);
        final ImageView five_button = (ImageView)findViewById(R.id.test_photo1);
        final ImageView six_button = (ImageView)findViewById(R.id.imageView1);

        // when you click this demo button
      /*  Demo_button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            Demo_button.setImageResource(R.drawable.image3);
           // second_button.setImageResource(R.drawable.bg);
           }
        });*/

        second_button.setOnClickListener(new OnClickListener() {
           public void onClick(View v) {
        	   
        	   Demo_button.setImageBitmap(bitmap);
               //second_button.setImageResource(R.drawable.image10);
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
