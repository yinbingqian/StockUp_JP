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

public class Opinion_Activity extends BaseActivity {
	private ImageView img_back;
	private String userid;
	private String realname;
	private TextView tv_send;
	private AutoCompleteTextView opinion_content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_opinion);
		intent = getIntent();
		userid = intent.getStringExtra("userid");
		realname = intent.getStringExtra("realname");
		initView();
		setListeners();
	}

	private void initView() {
		img_back = (ImageView) findViewById(R.id.img_back);
		tv_send = (TextView) findViewById(R.id.tv_send);
		opinion_content = (AutoCompleteTextView) findViewById(R.id.opinion_content);
		opinion_content.setEnabled(true);
	}

	private void setListeners() {
		img_back.setOnClickListener(this);
		tv_send.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.tv_send:
			if (opinion_content.getText().toString().trim().equals("") ) {
				Utils.showTextToast(Opinion_Activity.this, getString(R.string.opinion_empty));
				break;
			}
//			String opinioncontent = opinion_content.getText().toString().trim();
			String opinioncontent = MD5Plus.stringToMD5(opinion_content.getText().toString().trim());
			String[] property_va = new String[] {  getLoginUser().getUserid(), getLoginUser().getRealname(),opinioncontent};
			// String[] property_va = new String[] { getLoginUser().getName(), actv_password.getText().toString().trim(), actv_password_enable.getText().toString().trim() };
			soapService.opinion(property_va);
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
		if (res.getCode().equals(SOAP_UTILS.METHOD.OPINION)) {
			if (res.getObj() != null) {
				if (res.getObj().toString().equals("true")) {
					Utils.showTextToast(this, getString(R.string.send_success));
//					LoginUser user = getLoginUser();
//					user.setPassword(opinion_content.getText().toString().trim());
//					setLoginUser(user);
					EventCache.commandActivity.post(new String[] { this.getClass().getName(), opinion_content.getText().toString().trim() });
					finish();
				} else {
					Utils.showTextToast(this, getString(R.string.send_failed));
				}
			} else {
				Utils.showTextToast(this, getString(R.string.send_failed));
			}
		}
	}
}
