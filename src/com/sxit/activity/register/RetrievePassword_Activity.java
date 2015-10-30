package com.sxit.activity.register;

import lnpdit.lntv.tradingtime.R;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.login.Login_Activity;
import com.sxit.http.SoapRes;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.Utils;

@SuppressLint("NewApi")
public class RetrievePassword_Activity extends BaseActivity {
	ImageView img_back;
	TextView getretrieve_tv;
	AutoCompleteTextView actv_phone;
	AutoCompleteTextView actv_checkCode;
	Button btn_getvcode, bt_submit;
	CheckTimer checkTimer;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_retrievepassword);
		this.isParentActivity = false;
		initView();
		setListeners();

	}

	private void initView() {
		getretrieve_tv = (TextView) findViewById(R.id.getretrieve_tv);
		actv_phone = (AutoCompleteTextView) findViewById(R.id.actv_phone);

		actv_checkCode = (AutoCompleteTextView) findViewById(R.id.actv_checkCode);
		btn_getvcode = (Button) findViewById(R.id.btn_getvcode);
		bt_submit = (Button) findViewById(R.id.bt_submit);
		img_back = (ImageView) findViewById(R.id.img_back);
		actv_phone.setNextFocusForwardId(R.id.actv_checkCode);

	}

	private void setListeners() {
		img_back.setOnClickListener(this);
		btn_getvcode.setOnClickListener(this);
		bt_submit.setOnClickListener(this);

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_getvcode:
			String code = Utils.vcode();
			btn_getvcode.setEnabled(false);
			if (actv_phone.getText().length() != 11
					|| actv_phone.getText() == null
					|| actv_phone.getText().toString().trim().equals("")) {
				Utils.showTextToast(this, getString(R.string.phone_error));

			} else {
//				String[] property_nm = { "sim" };
				String[] property_va = new String[] { actv_phone.getText().toString() };
				soapService.SimVerify(property_va);

//				// actv_checkCode.setText(code);
//				btn_getvcode.setText("发送中...");
//				btn_getvcode.setEnabled(false);
//				String[] property_va1 = new String[] { actv_phone.getText()
//						.toString() };
//				soapService.getCode(property_va1);		
			}
			break;
		case R.id.bt_submit:
			// 手机号校验
			if (actv_phone.getText().length() != 11
					|| actv_phone.getText().equals("")) {
				Utils.showTextToast(this, getString(R.string.phone_error));
				break;
			}
			// 验证码校验
			if (actv_checkCode.getText().equals("")) {
				Utils.showTextToast(this, getString(R.string.smscode));
				break;
			}
			
			Object[] property_va = { actv_phone.getText().toString(),
					actv_checkCode.getText().toString(), };
			soapService.CodeVerify(property_va);
//			intent.putExtra("sim", actv_phone.getText().toString());
//			intent.setClass(RetrievePassword_Activity.this, ResetPassword_Activity.class);
//			startActivity(intent);
			break;
		case R.id.img_back:
			finish();
			break;

		default:
			break;
		}
		super.onClick(v);
	}

	Handler checkHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				btn_getvcode.setText(msg.obj.toString());
			} else {
				btn_getvcode.setEnabled(true);
				btn_getvcode.setText("重新获取");
			}
		}
	};

	class CheckTimer implements Runnable {

		public boolean stop = false;
		private int time = 60;

		public void Restart() {
			this.stop = false;
			this.time = 60;
			new Thread(this).start();
		}

		@Override
		public void run() {
			Message mes = new Message();
			mes.what = 0;
			while (!stop && time != 0) {
				time--;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				mes = new Message();
				mes.obj = time + "(S)";
				checkHandler.sendMessage(mes);
			}
			if (!stop) {
				mes = new Message();
				mes.what = 1;
				checkHandler.sendMessage(mes);
			} else {
				mes = new Message();
				mes.what = 2;
				checkHandler.sendMessage(mes);
			}
		}

		public void end() {
			this.stop = true;
			checkHandler.sendEmptyMessage(2);
		}
	}

	@Override
	protected void onResume() {
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

	public void onEvent(SoapRes res) {
		if (res.getCode().equals(SOAP_UTILS.METHOD.SIMVERIFY)) {
			if (res.getObj().toString().equals("true")) {
				btn_getvcode.setText("发送中...");
				btn_getvcode.setEnabled(false);
				String[] property_va = new String[] { actv_phone.getText()
						.toString() };
				soapService.getCode(property_va);	
			} else {
//				Utils.showTextToast(this, "验证码请求失败");
				Toast.makeText(RetrievePassword_Activity.this, "您的手机号尚未注册", Toast.LENGTH_SHORT).show();
			}
		}
		if (res.getCode().equals(SOAP_UTILS.METHOD.GETCODE)) {
			if (res.getObj() != null) {
				if (res.getObj().toString().equals("0")) {
					checkTimer = new CheckTimer();
					Thread thread = new Thread(checkTimer);
					thread.start();
				} else {
					Toast.makeText(RetrievePassword_Activity.this, "验证码请求失败",
							Toast.LENGTH_SHORT).show();
//					Utils.showTextToast(this, "验证码请求失败");
				}
			}
		} else if (res.getCode().equals(SOAP_UTILS.METHOD.CODEVERIFY)) {
			if (res.getObj() != null) {
				if (res.getObj().toString().equals("true")) {
//					Utils.showTextToast(this,
//							getString(R.string.retrievepassword_success));
//					finish();
					intent.putExtra("sim", actv_phone.getText().toString());
					intent.setClass(RetrievePassword_Activity.this, ResetPassword_Activity.class);
					startActivity(intent);
					finish();
				} else {
				
					Utils.showTextToast(this, "找回密码失败，请检查信息后重新提交");

				}
			} else {
				Utils.showTextToast(this,
						getString(R.string.retrievepassword_fail));
			}
		}
	}
}
