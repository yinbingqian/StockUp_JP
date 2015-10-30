package com.sxit.activity.usersetting;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sxit.activity.base.BaseActivity;
import com.sxit.entity.LoginUser;
import com.sxit.http.SoapRes;
import com.sxit.md5.MD5Plus;

import lnpdit.lntv.tradingtime.R;
import com.sxit.utils.EventCache;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.Utils;

/**
 * 密码
 * 
 * @author huanyu 类名称：UserSetting_Password_Activity 创建时间:2014-11-3 上午11:54:35
 */
public class UserSetting_Password_Activity extends BaseActivity {
	private ImageView img_back;
	private String password;
	private TextView tv_save, tv_edit;
	private AutoCompleteTextView actv_password_enable, actv_password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usersetting_password);
		intent = getIntent();
		password = intent.getStringExtra("password");
		initView();
		setListeners();
	}

	private void initView() {
		img_back = (ImageView) findViewById(R.id.img_back);
		tv_edit = (TextView) findViewById(R.id.tv_edit);
		tv_save = (TextView) findViewById(R.id.tv_save);
		actv_password_enable = (AutoCompleteTextView) findViewById(R.id.actv_password_enable);
		actv_password = (AutoCompleteTextView) findViewById(R.id.actv_password);
		actv_password.setEnabled(true);
		actv_password_enable.setEnabled(true);
	}

	private void setListeners() {
		img_back.setOnClickListener(this);
		tv_edit.setOnClickListener(this);
		tv_save.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.tv_edit:
			finish();
			break;
		case R.id.tv_save:
			if (actv_password.getText().toString().trim().equals("") || actv_password_enable.getText().toString().trim().equals("")) {
				Utils.showTextToast(UserSetting_Password_Activity.this, getString(R.string.password_empty));
				break;
			}
			String oldpwd = MD5Plus.stringToMD5(actv_password.getText().toString().trim());
			String newpwd = MD5Plus.stringToMD5(actv_password_enable.getText().toString().trim());
			String[] property_va = new String[] { getLoginUser().getName(), oldpwd, newpwd };
			// String[] property_va = new String[] { getLoginUser().getName(), actv_password.getText().toString().trim(), actv_password_enable.getText().toString().trim() };
			soapService.userPasswordReset(property_va);

			finish();
			break;
		default:
			break;
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		}
		return true;
	}

	public void onEvent(SoapRes res) {
		if (res.getCode().equals(SOAP_UTILS.METHOD.USERPASSWORDRESET)) {
			if (res.getObj() != null) {
				if (res.getObj().toString().equals("true")) {
					Utils.showTextToast(this, getString(R.string.edit_success));
					LoginUser user = getLoginUser();
					user.setPassword(actv_password_enable.getText().toString().trim());
					setLoginUser(user);
					EventCache.commandActivity.post(new String[] { this.getClass().getName(), actv_password_enable.getText().toString().trim() });
				} else {
					Utils.showTextToast(this, getString(R.string.edit_password_fail));
				}
			} else {
				Utils.showTextToast(this, getString(R.string.edit_password_fail));
			}
		}
	}
}
