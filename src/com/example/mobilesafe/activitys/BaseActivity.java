package com.example.mobilesafe.activitys;


import com.example.mobilesafe.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public abstract class BaseActivity extends Activity{
	
	private GestureDetectorCompat mDetector; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		 mDetector = new GestureDetectorCompat(this, new MyGestureListener());
		
	}
	
	 @Override 
	    public boolean onTouchEvent(MotionEvent event){ 
	        this.mDetector.onTouchEvent(event);
	        return super.onTouchEvent(event);
	    }
	
	 class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
	        private static final String DEBUG_TAG = "Gestures"; 
	        
	        @Override
	        public boolean onDown(MotionEvent event) { 
	            Log.d(DEBUG_TAG,"onDown: " + event.toString()); 
	            return true;
	        }

	        @Override
	        public boolean onFling(MotionEvent event1, MotionEvent event2, 
	                float velocityX, float velocityY) {
	            Log.d(DEBUG_TAG, "onFling: " + event1.toString()+event2.toString());
	            /**
	             * 如果event1的x坐标大于event2的x坐标,下一步
	             */
	            	float x1 = event1.getX();
	            	float x2 = event2.getX();
	            	
	            	if(x1>x2){
	            		nextStartActivity(null);
	            	}else{
	            		preStartActivity(null);
	            	}
	         
	            
	            return true;
	        }
	    }
	
	public void preStartActivity(View view){
		
		preActivity();
		finish();
		overridePendingTransition(R.anim.preanima_02, R.anim.preanima_01);
	}
	
	public void nextStartActivity(View view){
		nextActivity();
		finish();
		
		overridePendingTransition(R.anim.nextanima_02, R.anim.nextanima_01);
	}

	public  abstract void nextActivity();
		
	

	public  abstract void preActivity();
	
}
