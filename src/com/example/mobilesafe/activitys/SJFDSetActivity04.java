package com.example.mobilesafe.activitys;

import com.example.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SJFDSetActivity04 extends BaseActivity {
	
	@Override
	public  void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sjfd_set_activity_04);
	}

	@Override
	public void nextActivity() {
		startActivity(new Intent(this, SJFDSetActivity05.class));
		
	}

	@Override
	public  void preActivity() {
		startActivity(new Intent(this, SJFDSetActivity03.class));
		
	}
}
