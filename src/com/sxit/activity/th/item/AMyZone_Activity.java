package com.sxit.activity.th.item;

import org.ksoap2.serialization.SoapObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.login.Login_Activity;
import com.sxit.activity.register.Register_Activity;
import com.sxit.activity.setting.Setting_Activity;
import com.sxit.activity.th.item.MyZone_Activity.GetHeart;
import com.sxit.activity.usersetting.UserSetting_Activity;
import com.sxit.http.SoapWebService;
import com.sxit.instance.Instance;
import com.sxit.utils.SOAP_UTILS;

import lnpdit.lntv.tradingtime.R;

/**
 * 我的空间
 * 
 * @author huanyu 类名称：MyZone_Activity 创建时间:2014-10-26 下午8:30:59
 */
public class AMyZone_Activity extends BaseActivity {
	private ImageView img_setting;// back,编辑资料
	private ImageView img_loginUser;// 图像
	private TextView tv_name;// name
	private TextView tv_ps_contribute,tv_ps_active;
	String Mark;
	String Paidmark;
	String Heartcount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_amyzone);
		initView();
		setListeners();
	}

	private void initView() {
		// title
		img_setting = (ImageView) findViewById(R.id.img_setting);
		img_loginUser = (ImageView) findViewById(R.id.img_loginUser);
		tv_name = (TextView) findViewById(R.id.tv_name);
//		tv_ps_money = (TextView) findViewById(R.id.tv_ps_money);
		tv_ps_contribute = (TextView) findViewById(R.id.tv_ps_contribute);
		tv_ps_active = (TextView) findViewById(R.id.tv_ps_active);
	}

	public void setUI() {
		// set
		Instance.imageLoader.displayImage(SOAP_UTILS.HEADER_URL + getLoginUser().getHeadpic(), img_loginUser, Instance.user_options);
		tv_name.setText(getLoginUser().getRealname());
		tv_ps_contribute.setText(getLoginUser().getMark());
		tv_ps_active.setText(getLoginUser().getPaidmark());
//		tv_ps_money.setText(getLoginUser().getMark());
	}

	private void setListeners() {
		img_setting.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.img_setting:
			intent.setClass(AMyZone_Activity.this, Setting_Activity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
	class GetAnalystHeart extends AsyncTask<Object, Object, Object> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Object doInBackground(Object... params) {
			System.out.println(">>>>>");

			Object res_obj = SoapWebService.data(SOAP_UTILS.METHOD.GETANALYSTMARK, (String[]) params[0], (Object[]) params[1]);
			SoapObject so = (SoapObject) res_obj;
			SoapObject soapchilds = (SoapObject) so.getProperty("GetAnalystMarkResult");
			Mark = soapchilds.getProperty("Mark").toString();
			Paidmark = soapchilds.getProperty("Paidmark").toString();
			Heartcount = soapchilds.getProperty("Heartcount").toString();
//			if (soapchilds.equals("true")) {
//				payForChat();
//			} else {
//
//			}
			System.out.println("####" + res_obj);
			return soapchilds;
		}

		@Override
		protected void onPostExecute(Object result) {
			tv_ps_contribute.setText(Mark);
			tv_ps_active.setText(Paidmark);
			
			super.onPostExecute(result);
		}
	}
	@Override
	protected void onResume() {
		setUI();
		String[] property_nm = { "analystid" };
		Object[] property_va = { getLoginUser().getUserid() };
		new GetAnalystHeart().execute(property_nm, property_va);
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
