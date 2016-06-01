package com.example.mobilesafe.utils;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 *一些方法,安装APK,写入本地文件,关流,获取okhttpclient访问网络的返回体
 * @author ZXP
 *
 */
public class ZxpUtils {
	
	/**
	 * 安装apk的方法
	 * @param file
	 */
	public static void installAPK(Activity context,File file,int REQUESTCODE) {
		
		 /*<activity android:name=".PackageInstallerActivity"
	                android:configChanges="orientation|keyboardHidden"
	                android:theme="@style/TallTitleBarTheme">
	            <intent-filter>
	                <action android:name="android.intent.action.VIEW" />
	                <category android:name="android.intent.category.DEFAULT" />
	                <data android:scheme="content" />
	                <data android:scheme="file" />
	                <data android:mimeType="application/vnd.android.package-archive" />
	            </intent-filter>
	        </activity>*/
		
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		Uri data = Uri.fromFile(file);
		intent.setDataAndType(data, "application/vnd.android.package-archive");
		context.startActivityForResult(intent, REQUESTCODE);
		
	}
	
	public static void write(InputStream inputstream,File file){
		try {
			FileOutputStream fos = new FileOutputStream(file);
			
			byte[] buffer = new byte[1024];
			
			int len = -1;
			
			while((len=inputstream.read(buffer))!=-1){
				fos.write(buffer, 0, len);
			}
			
			
			close(inputstream);
			close(fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 关流的方法
	 * @param stream
	 * @throws IOException
	 */
	public static void close(Closeable stream) throws IOException {
		
		if(stream!=null){
			stream.close();
			stream=null;
		}
	}
	
	/**
	 * 访问网络,获取返回体
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static Response getResponse(String url) throws IOException {
		
		okhttp3.OkHttpClient.Builder builder = new OkHttpClient.Builder();
		builder.connectTimeout(5, TimeUnit.SECONDS);
		
		OkHttpClient client = builder.build();
		
		Request request = new Request.Builder()
									 .url(url)
									 .build();
		
		Response response = client.newCall(request).execute();
		return response;
	}

	
}
