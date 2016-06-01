package com.example.mobilesafe.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SpUtils {
	
	/**
	 * 保存图片开关的状态
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveImageState(Context context,String key,boolean value){
		
		SharedPreferences preferences = context.getSharedPreferences("imgState_sp", Context.MODE_PRIVATE);
		Editor edit = preferences.edit();
		edit.putBoolean(key, value);
		edit.commit();
	}
	
	//获取图片开关的状态
	public static boolean getImageBoolean_true(Context context,String key){
		
		SharedPreferences preferences = context.getSharedPreferences("imgState_sp", Context.MODE_PRIVATE);
		return preferences.getBoolean(key, true);
		
	}
	
	//获取图片开关的状态
	public static boolean getImageBoolean_false(Context context,String key){
		
		SharedPreferences preferences = context.getSharedPreferences("imgState_sp", Context.MODE_PRIVATE);
		return preferences.getBoolean(key, false);
		
	}
	
	public static void saveSJFDPwd(Context context,String key,String value){
		
		SharedPreferences preferences = context.getSharedPreferences("imgState_sp", Context.MODE_PRIVATE);
		Editor edit = preferences.edit();
		edit.putString(key, value);
		edit.commit();
	}
	
	public static String  getSJFDPwd(Context context,String key){
		SharedPreferences preferences = context.getSharedPreferences("imgState_sp", Context.MODE_PRIVATE);
		return preferences.getString(key, "");
	}
	
}
