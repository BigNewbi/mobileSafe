package com.example.mobilesafe.activitys;

import com.example.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SJFDSetActivity05 extends BaseActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sjfd_set_activity_05);
	}
	
	public void nextStartActivity(View view){
		nextActivity();
		overridePendingTransition(R.anim.nextanima_02, R.anim.nextanima_01);
	}
	
	@Override
	public void nextActivity() {
		
	}

	@Override
	public  void preActivity() {
		startActivity(new Intent(this, SJFDSetActivity04.class));
		
	}
}
