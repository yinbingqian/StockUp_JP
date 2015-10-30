package com.sxit.activity.register;

import com.sxit.activity.agreement.AgreementActivity;
import com.sxit.activity.base.BaseActivity;
import com.sxit.http.SoapRes;
import com.sxit.md5.MD5Plus;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.Utils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import lnpdit.lntv.tradingtime.R;

/**
 * 注册新用户
 * 
 * @author huanyu 类名称：Register_Activity 创建时间:2014-11-7 下午2:51:16
 */
public class Register_Activity extends BaseActivity {
	private ImageView img_back;
	private AutoCompleteTextView actv_name, actv_admin, actv_password,
			actv_password_again, actv_phone, actv_checkCode;// 昵称，设置密码，再次输入，输入手机
			// private AutoCompleteTextView actv_name, actv_admin,
			// actv_password, actv_password_again, actv_phone, actv_checkCode;//
			// 昵称，设置密码，再次输入，输入手机
	private TextView tv_check, btn_getvcode, register_protocol_d_tv;// 城市，股龄，性别，协议
			// private TextView tv_city, tv_age, tv_age_bottom, tv_sex,
			// tv_check, btn_getvcode, register_protocol_d_tv;// 城市，股龄，性别，协议
	private Button bt_submit;// 提交
	private String city, province;
	CheckTimer checkTimer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_register);
		this.isParentActivity = false;
		initView();
		setListeners();
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

	@SuppressLint("NewApi")
	private void initView() {
		img_back = (ImageView) findViewById(R.id.img_back);
		actv_name = (AutoCompleteTextView) findViewById(R.id.actv_name);

		actv_admin = (AutoCompleteTextView) findViewById(R.id.actv_admin);
		actv_password = (AutoCompleteTextView) findViewById(R.id.actv_password);
		actv_password_again = (AutoCompleteTextView) findViewById(R.id.actv_password_again);
		actv_phone = (AutoCompleteTextView) findViewById(R.id.actv_phone);
		// actv_phone.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
		actv_checkCode = (AutoCompleteTextView) findViewById(R.id.actv_checkCode);
		// tv_city = (TextView) findViewById(R.id.tv_city);
		// tv_age = (TextView) findViewById(R.id.tv_age);
		register_protocol_d_tv = (TextView) findViewById(R.id.register_protocol_d_tv);
		// tv_age_bottom = (TextView) findViewById(R.id.tv_age_bottom);
		// tv_sex = (TextView) findViewById(R.id.tv_sex);
		bt_submit = (Button) findViewById(R.id.bt_submit);
		btn_getvcode = (Button) findViewById(R.id.btn_getvcode);
		tv_check = (TextView) findViewById(R.id.tv_check);
		actv_name.setNextFocusForwardId(R.id.actv_admin);
		actv_admin.setNextFocusForwardId(R.id.actv_password);
		actv_password.setNextFocusForwardId(R.id.actv_password_again);
		actv_password_again.setNextFocusForwardId(R.id.actv_phone);
	}

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

	private void setListeners() {
		img_back.setOnClickListener(this);
		bt_submit.setOnClickListener(this);
		// tv_city.setOnClickListener(this);
		// tv_age.setOnClickListener(this);
		// tv_age_bottom.setOnClickListener(this);
		// tv_sex.setOnClickListener(this);
		register_protocol_d_tv.setOnClickListener(this);
		btn_getvcode.setOnClickListener(this);
		actv_name.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {

				} else {
					if (actv_name.getText().toString().trim() != null) {
						String[] property_va = new String[] { actv_name
								.getText().toString() };
						soapService.userNameCheck(property_va);
					}
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_getvcode:
			String code = Utils.vcode();
			btn_getvcode.setEnabled(false);
			if (actv_name.getText() == null
					|| actv_name.getText().toString().trim().equals("")) {
				Toast.makeText(Register_Activity.this, "请输入手机号码",
						Toast.LENGTH_SHORT).show();
			} else {
				// actv_checkCode.setText(code);
				btn_getvcode.setText("发送中...");
				btn_getvcode.setEnabled(false);
				String[] property_va = new String[] { actv_name.getText()
						.toString() };
				soapService.getCode(property_va);

			}
			break;
		case R.id.img_back:
			finish();
			break;
		case R.id.register_protocol_d_tv:
			Intent intent1 = new Intent();
			intent1.setClass(this, AgreementActivity.class);
			startActivity(intent1);
			break;
		case R.id.bt_submit:
			// 账户校
			if (actv_name.getText().toString().trim().equals("")) {
				Utils.showTextToast(this, getString(R.string.admin_empty));
				break;
			}
			// 昵称校验
			if (actv_admin.getText().toString().trim().equals("")) {
				Utils.showTextToast(this, getString(R.string.admin_empty));
				break;
			}
			// 密码校验
			if (actv_password.getText().toString().trim().equals("")) {
				Utils.showTextToast(this, getString(R.string.password_empty));
				break;
			}
			// 密码二次校验
			if (actv_password_again.getText().toString().trim().equals("")) {
				Utils.showTextToast(this, getString(R.string.password_empty));
				break;
			}
			if (!actv_password_again.getText().toString().trim()
					.equals(actv_password.getText().toString().trim())) {
				Utils.showTextToast(this, getString(R.string.password_error));
				break;
			}
			// // 城市校验
			// if (tv_city.getText().equals("无")) {
			// Utils.showTextToast(this, getString(R.string.city_empty));
			// break;
			// }
			// // 股龄校验
			// if (tv_age.getText().equals("无")) {
			// Utils.showTextToast(this, getString(R.string.stockage));
			// break;
			// }
			// // 股龄线路校验
			// if (tv_age_bottom.getText().equals("无")) {
			// Utils.showTextToast(this, getString(R.string.stockline));
			// break;
			// }
			// 手机号校验
			if (actv_name.getText().length() != 11
					|| actv_name.getText().equals("")) {
				Utils.showTextToast(this, getString(R.string.phone_error));
				break;
			}
			// 验证码校验
			if (actv_checkCode.getText().equals("")) {
				Utils.showTextToast(this, getString(R.string.smscode));
				break;
			}

			// Object[] property_va = { actv_phone.getText().toString(),
			// actv_name.getText().toString(),
			// MD5Plus.stringToMD5(actv_password.getText().toString()),
			// actv_admin.getText().toString(), tv_sex.getText().toString() ==
			// "男" ? "0" : "1", Utils.getImei(this), "辽宁省",
			// tv_city.getText().toString(), tv_age.getText().toString(),
			// tv_age_bottom.getText().toString() };
			
//			Object[] property_va = { actv_phone.getText().toString(),
//					actv_name.getText().toString(),
//					MD5Plus.stringToMD5(actv_password.getText().toString()),
//					actv_admin.getText().toString(), Utils.getImei(this), "辽宁省" };
			

			Object[] property_va = { actv_phone.getText().toString(),
					actv_name.getText().toString(),
					MD5Plus.stringToMD5(actv_password.getText().toString()),
					actv_admin.getText().toString(), "0",Utils.getImei(this), "辽宁省","沈阳","stockAge","stockStyle",actv_checkCode.getText().toString() };
			// Object[] property_va = { actv_phone.getText().toString(),
			// actv_name.getText().toString(),
			// actv_password.getText().toString(),
			// actv_admin.getText().toString(), tv_sex.getText().toString() ==
			// "男" ? "0" : "1", Utils.getImei(this), "辽宁省",
			// tv_city.getText().toString(),
			// tv_age.getText().toString(), tv_age_bottom.getText().toString()
			// };
			soapService.userRegistered(property_va);
			break;
		// case R.id.tv_city:
		// intent.setClass(Register_Activity.this, Province_Activity.class);
		// startActivity(intent);
		// break;
		// case R.id.tv_age:
		// intent.setClass(Register_Activity.this, Age_Activity.class);
		// startActivity(intent);
		// break;
		// case R.id.tv_age_bottom:
		// intent.setClass(Register_Activity.this, AgeLine_Activity.class);
		// startActivity(intent);
		// break;
		// case R.id.tv_sex:
		// intent.setClass(Register_Activity.this, Sex_Activity.class);
		// startActivity(intent);
		// break;
		default:
			break;
		}
		super.onClick(v);
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
		if (res.getCode().equals(SOAP_UTILS.METHOD.USERREGISTEREDV3)) {
			if (res.getObj() != null) {
				if (res.getObj().toString().equals("true")) {
					Utils.showTextToast(this,
							getString(R.string.register_success));
					finish();
				} else if (res.getObj().toString().equals("false")){
					Utils.showTextToast(this, "用户注册失败");
				}else if (res.getObj().toString().equals("miscode")){
					Utils.showTextToast(this, "验证码错误");
				}else if (res.getObj().toString().equals("simexist")){
					Utils.showTextToast(this, "手机号已存在");
				}else if (res.getObj().toString().equals("timeout")){
					Utils.showTextToast(this, "验证码超时");
				}
			} else {
				Utils.showTextToast(this, getString(R.string.register_fail));
			}
		} else if (res.getCode().equals(SOAP_UTILS.METHOD.USERNAMECHECK)) {
			if (res.getObj() != null) {
				tv_check.setVisibility(View.VISIBLE);

		

				/* 只有用户名、密码不为空，并且用户名为11位手机号码才允许登陆 */

				if (res.getObj().toString().equals("true")) {
					boolean isTel = true; // 标记位：true-是手机号码；false-不是手机号码
					/* 判断输入的用户名是否是电话号码 */
					if (actv_name.getText().toString().length() == 11) {
						for (int i = 0; i < actv_name.getText().toString().length(); i++) {
							char c = actv_name.getText().toString().charAt(i);
							if (!Character.isDigit(c)) {
								isTel = false;
								break; // 只要有一位不符合要求退出循环
							}
						}
					} else {
						isTel = false;
					}
					if (actv_name.getText().toString().trim().equals("")) {
						tv_check.setTextColor(getResources().getColor(
								R.color.red));
						tv_check.setText("用户名不可为空");
					} else if (!isTel) {
						tv_check.setTextColor(getResources().getColor(
								R.color.red));
						tv_check.setText("用户名请输入11位手机号码！");
					} else {
						tv_check.setTextColor(getResources().getColor(
								R.color.green));
						tv_check.setText(getString(R.string.nameable));
					}
				} else {
					tv_check.setTextColor(getResources().getColor(R.color.red));
					tv_check.setText(getString(R.string.nameenable));
				}
			}
		} else if (res.getCode().equals(SOAP_UTILS.METHOD.GETCODE)) {
			if (res.getObj() != null) {
				tv_check.setVisibility(View.VISIBLE);
				if (res.getObj().toString().equals("0")) {
					checkTimer = new CheckTimer();
					Thread thread = new Thread(checkTimer);
					thread.start();
				} else {
					Toast.makeText(Register_Activity.this, "验证码请求失败",
							Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

	/**
	 * 
	 * @param res
	 */
	// public void onEvent(String... str) {
	// for (String name : str) {
	// if (name.equals(CityNew_Activity.class.getName())) {
	// if (str[1] != null && str[2] != null) {
	// tv_city.setText(str[1] + " " + str[2]);
	// province = str[1];
	// city = str[2];
	// }
	// }
	// if (name.equals(Age_Activity.class.getName())) {
	// if (str[1] != null) {
	// tv_age.setText(str[1]);
	// }
	// }
	// if (name.equals(AgeLine_Activity.class.getName())) {
	// if (str[1] != null) {
	// tv_age_bottom.setText(str[1]);
	// }
	// }
	// if (name.equals(Sex_Activity.class.getName())) {
	// if (str[1] != null) {
	// tv_sex.setText(str[1]);
	// }
	// }
	// }
	// }
}
