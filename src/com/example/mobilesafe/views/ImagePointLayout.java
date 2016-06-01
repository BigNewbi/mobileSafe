package com.example.mobilesafe.views;

import com.example.mobilesafe.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ImagePointLayout extends LinearLayout {

	public ImagePointLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}


	public ImagePointLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ImagePointLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImagePointLayout);
		int point = typedArray.getInteger(R.styleable.ImagePointLayout_point, 0);
		
		for(int i=0;i<5;i++){
			ImageView imageView = new ImageView(context);
			
			if(i==point){
				imageView.setImageResource(R.drawable.image_point_green);
			}else{
				imageView.setImageResource(R.drawable.image_point_gray);
			}
			
			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			imageView.setLayoutParams(params );
			params.leftMargin=5;
			addView(imageView);
			
		}
		
	}
		
	
}
