package com.sxit.activity.setting;

import java.util.HashSet;
import java.util.Set;

import lnpdit.lntv.tradingtime.R;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.jpush.android.api.JPushInterface;

import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.base.MyApplication;
import com.sxit.activity.login.Login_Activity;
import com.sxit.activity.usersetting.Opinion_Activity;
import com.sxit.activity.usersetting.UserSetting_Activity;
import com.sxit.db.DBHelper;
import com.sxit.instance.Instance;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.Utils;

public class Setting_Activity extends BaseActivity {
	private ImageView img_back;// back
	private ImageView header;
	private LinearLayout edit_info, setting_btn1, setting_btn2, setting_btn3, setting_btn4, setting_btn5, setting_btn6;
	private TextView name, stock_age,setting_version;
	private DBHelper dbh;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;
		isParentActivity = false;
		this.setContentView(R.layout.activity_setting);
		img_back = (ImageView) findViewById(R.id.img_back);
		header = (ImageView) findViewById(R.id.header);
		edit_info = (LinearLayout) findViewById(R.id.edit_info);
		setting_btn1 = (LinearLayout) findViewById(R.id.setting_btn1);
		setting_btn2 = (LinearLayout) findViewById(R.id.setting_btn2);//分享
		setting_btn3 = (LinearLayout) findViewById(R.id.setting_btn3);
		setting_btn4 = (LinearLayout) findViewById(R.id.setting_btn4);
		setting_btn5 = (LinearLayout) findViewById(R.id.setting_btn5);
		setting_btn6 = (LinearLayout) findViewById(R.id.setting_btn6);//用户反馈
		name = (TextView) findViewById(R.id.name);
		stock_age = (TextView) findViewById(R.id.stock_age);
		setting_version = (TextView) findViewById(R.id.setting_version);
		dbh = new DBHelper(this);
		img_back.setOnClickListener(this);
		edit_info.setOnClickListener(this);
		setting_btn1.setOnClickListener(this);
		setting_btn2.setOnClickListener(this);
		setting_btn3.setOnClickListener(this);
		setting_btn4.setOnClickListener(this);
		setting_btn5.setOnClickListener(this);
		setting_btn6.setOnClickListener(this);
	}

	private void setUI() {
		Instance.imageLoader.displayImage(SOAP_UTILS.HEADER_URL + getLoginUser().getHeadpic(), header, Instance.user_options);
		name.setText(getLoginUser().getRealname());
		if(getLoginUser().getLoginType()==0){
			stock_age.setText(getLoginUser().getOrgname()+"  "+getLoginUser().getPtitle());
//			stock_age.setVisibility(View.INVISIBLE);
			edit_info.setEnabled(false);
		}else{
			stock_age.setText("");
//			stock_age.setText("股龄：" + getLoginUser().getStock_age());

		}
		setting_version.setText(Utils.getVersionName(Setting_Activity.this));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.edit_info:
			intent.setClass(Setting_Activity.this, UserSetting_Activity.class);
			startActivity(intent);
			break;
		case R.id.setting_btn5:
//			Toast.makeText(context, "即将退出", Toast.LENGTH_LONG).show();
			Set<String> set = new HashSet<String>();
			set.add("");// 用户
			JPushInterface.setAliasAndTags(this, "", set);
			MyApplication myApplication = MyApplication.getInstance();
			myApplication.exitApp();
			finish();
			dbh.cheanLoginUserData();
			System.exit(0);
			break;
		case R.id.setting_btn2:
//			ShareTool.getInstance(this,this).shareMsg(this);
			break;
		case R.id.setting_btn4:
			intent.setClass(this, Login_Activity.class);
			startActivity(intent);
//			ShareTool.getInstance(this,this).shareMsg(this);
			break;
		case R.id.setting_btn6:
			intent.setClass(this, Opinion_Activity.class);
			startActivity(intent);
//			ShareTool.getInstance(this,this).shareMsg(this);
			break;
		default:
			break;
		}
		super.onClick(v);
	}

	@Override
	protected void onResume() {
		setUI();
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
