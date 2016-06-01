package com.example.mobilesafe.activitys;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.example.mobilesafe.R;
import com.example.mobilesafe.beans.ServerVersionInfo;
import com.example.mobilesafe.utils.Fileds;
import com.example.mobilesafe.utils.SpUtils;
import com.example.mobilesafe.utils.ZxpUtils;

public class SplashActivity extends Activity {
	
	protected static final String TAG = "SplashActivity";
	
	//获取版本的标记
	protected static final int SERVERVRESION = 100;
	
	//获取下载好的apk文件的标记
	private static final int DOWNFINISH = 101;

	private static final int REQUESTCODE = 1;
	
	private TextView mTv_splash_verison;
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SERVERVRESION:
				String serverVersionContent=(String) msg.obj;
				if(TextUtils.isEmpty(serverVersionContent)){
					
					go2main();
					
					return;
				}
				
				ServerVersionInfo info = parseJson(serverVersionContent);
				
				Log.d(TAG, info.serverVersionCode+"-----"+info.downUrl);
				
				int version= getLocalVersion();
				
				if(version != info.serverVersionCode){
					
					//去服务器下载apk
					showUpdateDialog("http://192.168.1.44:8080/MobileSafe.apk");
				}else{
					
					go2main();
				}
				break;
			
			case DOWNFINISH:
				
				File file=(File) msg.obj;
				
				if(file!=null){
					ZxpUtils.installAPK(SplashActivity.this,file,REQUESTCODE);
				}
				
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_activity);
		
		init();
		
		boolean imageState = SpUtils.getImageBoolean_true(this, Fileds.IMGSTATE);
		if(imageState){
			
			checkServerVersion();
			
		}else{
			go2main();
		}
	}
	
	
	
	
	/**
	 * 获取开启activity返回的数据
	 * @param url
	 */
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode==REQUESTCODE){
			if(resultCode==Activity.RESULT_CANCELED){
				go2main();
			}
		}
	}

	protected void showUpdateDialog(final String url) {
		
		AlertDialog.Builder builder= new Builder(this);
		builder.setTitle("版本更新提醒")
			   .setMessage("为我代言的版本")
			   .setNegativeButton("取消", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					go2main();
				}
			}).setPositiveButton("确定", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					//点击确定,去连接服务器去下载apk
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							downLoadApk(url);
							
						}
					}).start();
					
				}
			}).show();
	}

	protected void downLoadApk(String url) {
		try {
			
			Response response = ZxpUtils.getResponse(url);
			
			InputStream inputStream = response.body().byteStream();
			Log.d(TAG, "inputstream");
			//将流写入本地文件
			writeFile(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
			go2main();
		}
	}



	

	private void writeFile(InputStream inputstream) {
		
		//判断sd卡挂在好没
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			
			try {
				File file = new File(Environment.getExternalStorageDirectory(),"MobileSafe.apk");
				
				ZxpUtils.write(inputstream, file);
				
				//把数据通过handler发送给主线程
				Message msg = Message.obtain();
				msg.obj=file;
				msg.what=DOWNFINISH;
				
				mHandler.sendMessage(msg);
				
			} catch (Exception e) {
				e.printStackTrace();
				go2main();
			}
			
		}
		
	}
	
	
	

	/**
	 * 获取当地的版本
	 * @return
	 */
	protected int getLocalVersion() {
		PackageManager manager= getPackageManager();
		
		try {
			PackageInfo packageInfo = manager.getPackageInfo("com.example.mobilesafe", 0);
			Log.d(TAG, packageInfo.versionCode+"");
			return packageInfo.versionCode;
			
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return -1;
		}
		
	}
	
	/**
	 * 解析json数据
	 * @param json
	 * @return
	 */
	protected ServerVersionInfo parseJson(String json) {
		
		try {
			
			JSONObject jsonObject = new JSONObject(json);
			
			ServerVersionInfo serverVersionInfo = new ServerVersionInfo();
			
			/*{
				"versionCode":"2",
				"downUrl":"http://192.168.1.44:8080/serverVersion.json"
			}
			*/
			 serverVersionInfo.serverVersionCode = jsonObject.getInt("versionCode");
			 serverVersionInfo.downUrl = jsonObject.getString("downUrl");
			 return serverVersionInfo;
			 
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	protected void go2main() {
		
		Log.d(TAG, "进入主界面");
		startActivity(new Intent(getApplicationContext(), MainActivity.class));
	}

	private void init() {
		
		initViews();
		
		initData();
		
	}

	private void checkServerVersion() {
		/**
		 * 网络访问,耗时操作,在子线程中进行
		 */
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					String url = "http://192.168.1.44:8080/serverVersion.json";
					Response response = ZxpUtils.getResponse(url);
					
					//得到版本号
					String content = response.body().string();
					Log.d(TAG, "content="+content);
					
					Message msg = Message.obtain();
					msg.obj=content;
					msg.what=SERVERVRESION;
					
					mHandler.sendMessage(msg);
				} catch (IOException e) {
					e.printStackTrace();
					go2main();
				}
			}
		}).start();
	}
	
	/**
	 * 出事话数据
	 */
	private void initData() {
			
			String versionName = getLocalVersionName();
			mTv_splash_verison.setText("版本:"+versionName );
	}
	
	/**
	 * 获取本地的版本名称
	 * @return
	 */
	private String getLocalVersionName() {
		
		try {
			
			PackageManager manager = getPackageManager();
			PackageInfo packageInfo = manager.getPackageInfo("com.example.mobilesafe", 0);
			
			return packageInfo.versionName;
			
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	
	}

	
	
	/**
	 * 找出控件
	 */
	private void initViews() {
		mTv_splash_verison = (TextView) findViewById(R.id.tv_splash_version);
		
	}
}
