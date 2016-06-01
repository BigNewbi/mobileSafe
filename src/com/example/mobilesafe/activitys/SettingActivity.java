package com.example.mobilesafe.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.mobilesafe.R;
import com.example.mobilesafe.views.SettingItemView;

public class SettingActivity extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
	}

	public void change(View view){
		SettingItemView settingItemView=(SettingItemView) view;
		
		settingItemView.toggle();
	}
}
