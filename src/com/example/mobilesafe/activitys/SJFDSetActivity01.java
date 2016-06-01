package com.example.mobilesafe.activitys;

import com.example.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SJFDSetActivity01 extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sjfd_set_activity_01);
	}
	
	public void preStartActivity(View view){
		
		preActivity();
		overridePendingTransition(R.anim.preanima_02, R.anim.preanima_01);
	}
	
	@Override
	public void nextActivity() {
		startActivity(new Intent(getApplicationContext(), SJFDSetActivity02.class));
	}

	@Override
	public  void preActivity() {
		
	}
	
	
}
