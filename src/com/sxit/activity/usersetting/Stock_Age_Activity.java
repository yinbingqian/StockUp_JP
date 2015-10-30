package com.sxit.activity.usersetting;

import lnpdit.lntv.tradingtime.R;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.register.adapter.Age_Adapter;
import com.sxit.entity.LoginUser;
import com.sxit.http.SoapRes;
import com.sxit.utils.EventCache;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.Utils;

/**
 * 股龄 Acy
 * 
 * @author huanyu 类名称：Age_Activity 创建时间:2014-11-7 下午3:15:20
 */
public class Stock_Age_Activity extends BaseActivity {
	private ImageView img_back;// back
	private ListView lv_age;
	private String ages[] = new String[] { "2年", "3年", "4年", "5年", "6年", "7年", "8年", "9年" };
	private String age;
	private TextView tv_save;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_setting_age);
		this.isParentActivity = false;
		EventCache.opAnswerEvent.unregister(this);
		EventCache.opAnswerEvent.register(this);
		initView();
		setListeners();
	}

	private void initView() {
		img_back = (ImageView) findViewById(R.id.img_back);
		tv_save = (TextView) findViewById(R.id.tv_save);
		lv_age = (ListView) findViewById(R.id.lv_age);
		lv_age.setAdapter(new Age_Adapter(this, ages));
	}

	private void setListeners() {
		img_back.setOnClickListener(this);
		tv_save.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.tv_save:
			if(age == null){
				Utils.showTextToast(this, "请选择股龄");
				break;
			}
			String[] property_va = new String[] { getLoginUser().getUserid(), "3", age };
			soapService.userEditor(property_va);
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
		EventCache.opAnswerEvent.unregister(this);
		super.onDestroy();
	}

	/**
	 * radio点击回调
	 * 
	 * @param time
	 *            选中时间
	 */
	public void onEvent(String age) {
		this.age = age;
	}

	public void onEvent(SoapRes res) {
		if (res.getCode().equals(SOAP_UTILS.METHOD.USEREDITOR)) {
			if (res.getObj() != null) {
				if (res.getObj().toString().equals("true")) {
					Utils.showTextToast(this, getString(R.string.edit_success));
					LoginUser user = getLoginUser();
					user.setStock_age(age);
					setLoginUser(user);
					EventCache.commandActivity.post(new String[] { this.getClass().getName(), age });
					finish();
				} else {
					Utils.showTextToast(this, getString(R.string.edit_password_fail));
				}
			} else {
				Utils.showTextToast(this, getString(R.string.edit_password_fail));
			}
		}
	}
}
