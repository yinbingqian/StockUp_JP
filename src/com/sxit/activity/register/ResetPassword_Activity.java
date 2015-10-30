package com.sxit.activity.register;

import lnpdit.lntv.tradingtime.R;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.register.Register_Activity.CheckTimer;
import com.sxit.http.SoapRes;
import com.sxit.md5.MD5Plus;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.Utils;

public class ResetPassword_Activity extends BaseActivity {
	private ImageView img_back;
	private AutoCompleteTextView actv_password, actv_password_again;
	private Button bt_submit;// 提交
	String sim = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_resetpassword);
		this.isParentActivity = false;
		Intent intent = this.getIntent();
		sim = intent.getStringExtra("sim");
		initView();
		setListeners();
	}

	@SuppressLint({ "NewApi", "NewApi" })
	private void initView() {
		img_back = (ImageView) findViewById(R.id.img_back);
		actv_password = (AutoCompleteTextView) findViewById(R.id.actv_password);
		actv_password_again = (AutoCompleteTextView) findViewById(R.id.actv_password_again);
		bt_submit = (Button) findViewById(R.id.bt_submit);
		actv_password.setNextFocusForwardId(R.id.actv_password_again);
	}

	private void setListeners() {
		img_back.setOnClickListener(this);
		bt_submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.bt_submit:
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

			Object[] property_va = {sim,MD5Plus.stringToMD5(actv_password.getText().toString())};
			soapService.UserPasswordFind(property_va);
			break;
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
		if (res.getCode().equals(SOAP_UTILS.METHOD.USERPASSWORDFIND)) {
			if (res.getObj() != null) {
				if (res.getObj().toString().equals("true")) {
					Utils.showTextToast(this,
							getString(R.string.resetpassword_success));
					finish();
				} else {
					Utils.showTextToast(this, "重置密码失败，请检查信息后重新提交");
				}
			} else {
				Utils.showTextToast(this, getString(R.string.resetpassword_fail));
			}
		}
	}

}
