package com.sxit.activity.register;

import lnpdit.lntv.tradingtime.R;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;

import com.sxit.activity.base.BaseActivity;
import com.sxit.http.SoapRes;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.Utils;

/**
 * 修改密码
 * 
 * @author huanyu 类名称：EditPassword_Activity 创建时间:2014-11-11 上午9:24:04
 */
public class EditPassword_Activity extends BaseActivity {
	private ImageView img_back;
	private AutoCompleteTextView actv_newpwd, actv_oldpwd, actv_name;
	private Button bt_submit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_editpassword);
		this.isParentActivity = false;
		initView();
		setListeners();
	}

	private void initView() {
		img_back = (ImageView) findViewById(R.id.img_back);
		actv_newpwd = (AutoCompleteTextView) findViewById(R.id.actv_newpwd);
		actv_oldpwd = (AutoCompleteTextView) findViewById(R.id.actv_oldpwd);
		actv_name = (AutoCompleteTextView) findViewById(R.id.actv_name);
		bt_submit = (Button) findViewById(R.id.bt_submit);
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
			// 校验
			if (actv_name.getText().toString().equals("")) {
				Utils.showTextToast(this, getString(R.string.name_empty));
				break;
			}
			if (actv_oldpwd.getText().toString().equals("") || actv_newpwd.getText().toString().equals("")) {
				Utils.showTextToast(this, getString(R.string.password_empty));
				break;
			}
			String [] property_va = new String[]{actv_name.getText().toString(),actv_oldpwd.getText().toString(),actv_newpwd.getText().toString()};
			soapService.userPasswordReset(property_va);
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

	public void onEvent(SoapRes res) {
		if (res.getCode().equals(SOAP_UTILS.METHOD.USERPASSWORDRESET)) {
			if(res.getObj().toString().equals("true")){
				Utils.showTextToast(this, getString(R.string.edit_password_success));
				finish();
			}else{
				Utils.showTextToast(this, getString(R.string.edit_password_fail));
			}
		}else {
			Utils.showTextToast(this, getString(R.string.edit_password_fail));
		}
	}
}
