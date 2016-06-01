package com.example.mobilesafe.activitys;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilesafe.R;
import com.example.mobilesafe.beans.ModeItem;
import com.example.mobilesafe.utils.Fileds;
import com.example.mobilesafe.utils.SpUtils;
import com.nineoldandroids.animation.ObjectAnimator;


public class MainActivity extends Activity implements OnClickListener {

    private static final String TAG = "MainActivity";

	private ImageView mIv_main_icon;
    
	private ImageView mIv_main_seeting;

	private GridView mGv_main_item;
	
	private List<ModeItem> list = new ArrayList<ModeItem>();
	
	// 桌面图标
	int[] icons = { R.drawable.sjfd, R.drawable.srlj, R.drawable.rjgj,
			R.drawable.jcgl, R.drawable.lltj, R.drawable.sjsd, R.drawable.hcql,
			R.drawable.cygj };
			
	//桌面标题
		String[] mTitles = { "手机防盗", "骚扰拦截", "软件管家", "进程管理", "流量统计", "手机杀毒",
	"缓存管理", "常用工具" };

			
	//桌面内容
	String[] mContents = { "远程定位手机", "全面拦截骚扰", "管理您的软件", "管理运行进程", "流量一目了然",
			"病毒远处藏身", "系统快如火箭", "工具大全" };

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        init();
    }
	
	/**
	 * 初始化
	 */
	private void init() {
		
		initViews();
		
		initData();
		
		initEvent();
	}
	
	/**
	 * 初始化点击事件
	 */
	private void initEvent() {
		
		mIv_main_seeting.setOnClickListener(this);
		
		mGv_main_item.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				switch (position) {
				case 0:
					
					String sjfdPwd = SpUtils.getSJFDPwd(getApplicationContext(), Fileds.SJFDPWD);
					if(TextUtils.isEmpty(sjfdPwd)){
						
						showSetPwdDialog();
						
					}else{
						
						showCheckPwdDialog();
					}
					
					break;
				case 1:
					
					break;
				case 2:
					
					break;
				case 3:
					
					break;
				case 4:
					
					break;
				case 5:
					
					break;
				case 6:
					
					break;
				case 7:
					
					break;

				default:
					break;
				}
			}
		});
	}
	
	/**
	 * 验证密码对话框
	 */
	protected void showCheckPwdDialog() {
		
		AlertDialog.Builder builder = new Builder(MainActivity.this);
		
		View view = View.inflate(MainActivity.this, R.layout.check_pwd_dialog, null);
		builder.setView(view);
		final AlertDialog checkPwdDialog = builder.show();
		
		Button bt_pwdpwddialog_ok=(Button) view.findViewById(R.id.bt_pwdpwddialog_ok);
		Button bt_pwdpwddialog_cancel=(Button) view.findViewById(R.id.bt_pwdpwddialog_cancel);
		final EditText et_checkpwd_dialog= (EditText) view.findViewById(R.id.et_checkpwd_dialog);
		
		bt_pwdpwddialog_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String checkedPwd = et_checkpwd_dialog.getText().toString().trim();
				String sjfdPwd = SpUtils.getSJFDPwd(MainActivity.this, Fileds.SJFDPWD);
				
				if(TextUtils.equals(checkedPwd, sjfdPwd)){
					go2SJFDSetting();
					checkPwdDialog.dismiss();
					
				}else{
					Toast.makeText(MainActivity.this, "密码不一致",
							Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		bt_pwdpwddialog_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkPwdDialog.dismiss();
			}
		});
	}

	/**
	 * 设置密码对话框
	 */
	protected void showSetPwdDialog() {
		
		AlertDialog.Builder builder=new Builder(this);
		View view = View.inflate(this, R.layout.set_pwd_dialog, null);
		builder.setView(view);
		final AlertDialog setPwdDialog = builder.show();
		
		final EditText et_setpwd_01=(EditText) view.findViewById(R.id.et_setpwd_01);
		final EditText et_setpwd_02=(EditText) view.findViewById(R.id.et_setpwd_02);
		
		Button bt_setpwddialog_cancel=(Button) view.findViewById(R.id.bt_setpwddialog_cancel);
		Button bt_setpwddialog_ok=(Button) view.findViewById(R.id.bt_setpwddialog_ok);
		
		//点击确定按钮
		bt_setpwddialog_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String setPwd = et_setpwd_01.getText().toString().trim();
				String setPwd2 = et_setpwd_02.getText().toString().trim();
				
				if(TextUtils.isEmpty(setPwd)||TextUtils.isEmpty(setPwd2)){
					Toast.makeText(MainActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
				}else{
					if(TextUtils.equals(setPwd, setPwd2)){
						
						go2SJFDSetting();
						
						setPwdDialog.dismiss();
						
						savePwd(setPwd);
						
					}else{
						Toast.makeText(MainActivity.this, "密码不一致",Toast.LENGTH_SHORT).show();
								
					}
				}
			}
		});
		
		//点击取消按钮
		bt_setpwddialog_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				setPwdDialog.dismiss();
			}
		});
	}
	
	protected void savePwd(String value) {
		
		SpUtils.saveSJFDPwd(MainActivity.this, Fileds.SJFDPWD, value);
	}

	/**
	 * 手机防盗的设置界面
	 */
	protected void go2SJFDSetting() {
		//FIXME
		startActivity(new Intent(getApplicationContext(), SJFDSetActivity01.class));
	}

	/**
	 * 初始数据
	 */
	private void initData() {
		
		iconAnimation();
		
		//初始化adapter数据
		initAdapterData();
		
		initAdapter();
	}
	
	private void initAdapterData() {
			
		for(int i=0;i<icons.length;i++){
			
			ModeItem modeItem = new ModeItem();
			
			modeItem.icon=icons[i];
			modeItem.title=mTitles[i];
			modeItem.content=mContents[i];
			
			list.add(modeItem);
		}
		
	}

	/**
	 * 初始化适配器
	 */
	private void initAdapter() {
		
		mGv_main_item.setAdapter(new BaseAdapter() {
			ViewHolder holder = null;
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				
				if(convertView==null){
					
					convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.gride_item, parent, false);
					holder=new ViewHolder(convertView);
					
					convertView.setTag(holder);
				}else{
					holder = (ViewHolder) convertView.getTag();
				}
				
				holder.iv_item_icon.setImageResource(getItem(position).icon);
				holder.tv_item_title.setText(getItem(position).title);
				holder.tv_item_content.setText(getItem(position).content);
				
				return convertView;
			}
			
			@Override
			public long getItemId(int position) {
				
				return position;
			}
			
			@Override
			public ModeItem getItem(int position) {
				
				return list.get(position);
			}
			
			@Override
			public int getCount() {
				
				return list.size();
			}
			
			class ViewHolder{
				
				ImageView iv_item_icon;
				TextView tv_item_title;
				TextView tv_item_content;
				
				public ViewHolder(View view){
					iv_item_icon=(ImageView) view.findViewById(R.id.iv_item_icon);
					tv_item_title=(TextView) view.findViewById(R.id.tv_item_title);
					tv_item_content=(TextView) view.findViewById(R.id.tv_item_content);
				}
				
			}
		});
	}

	/**
	 * 初始化动画
	 */
	private void iconAnimation() {
		
		ObjectAnimator animator = ObjectAnimator.ofFloat(mIv_main_icon, "rotationY", 0,60f,120f,180f,240f,360f);
		
		animator.setDuration(3000);
		
		animator.setRepeatCount(ObjectAnimator.INFINITE);
		
		animator.setRepeatMode(ObjectAnimator.RESTART);
		
		animator.start();
		
	}

	/**
	 * 初始化控件
	 */
	private void initViews() {
		
		mIv_main_icon = (ImageView) findViewById(R.id.iv_main_icon);
		
		mIv_main_seeting = (ImageView) findViewById(R.id.iv_main_setting);

		mGv_main_item = (GridView) findViewById(R.id.gv_main_item);
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.iv_main_setting:
			startActivity(new Intent(getApplicationContext(), SettingActivity.class));
			
			break;

		default:
			break;
		}
	}
 
}
