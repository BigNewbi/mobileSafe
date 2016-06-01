package com.example.mobilesafe.views;

import com.example.mobilesafe.R;
import com.example.mobilesafe.utils.Fileds;
import com.example.mobilesafe.utils.SpUtils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingItemView  extends RelativeLayout{

	private TextView textview;
	private ImageView iv;
	private boolean isopen=false;

	public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}


	public SettingItemView(Context context) {
		super(context);
	}
	
	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		View view = View.inflate(context, R.layout.setting_item, null);
		
		addView(view);
		
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SettingItemView);
		CharSequence text = typedArray.getText(R.styleable.SettingItemView_title);
		
		isopen = typedArray.getBoolean(R.styleable.SettingItemView_isopen, false);
		
		textview = (TextView) view.findViewById(R.id.settingitemview_tv);
		textview.setText(text);
		
		 iv = (ImageView) view.findViewById(R.id.settingitemview_iv);
		 
		 
		 boolean isopen = SpUtils.getImageBoolean_true(getContext(), Fileds.IMGSTATE);
		 
		 setImageFlag(isopen);
	}
	
	public void setImageFlag(boolean isopen){
		
		if(isopen){
			iv.setImageResource(R.drawable.on);
		}else{
			iv.setImageResource(R.drawable.off);
		}
		
		SpUtils.saveImageState(getContext(), Fileds.IMGSTATE, isopen);
	}
	
	public void toggle(){
		
		isopen=!isopen;
		setImageFlag(isopen);
	}
	
}
