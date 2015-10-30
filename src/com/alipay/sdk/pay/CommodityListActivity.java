package com.alipay.sdk.pay;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import lnpdit.lntv.tradingtime.R;

import cn.jpush.android.api.JPushInterface;

import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.th.MainTabHostActivity;
import com.sxit.entity.LoginUser;
import com.sxit.http.SoapRes;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CommodityListActivity extends BaseActivity {
	ImageView img_back;
	// private Object[] property_va;
	Button commodity_1;
	Button commodity_2;
	Button commodity_3;
	Button commodity_4;
	// TextView product_subject = (TextView) findViewById(R.id.product_subject);
	// TextView product_subject_info = (TextView) findViewById(R.id.product_subject_info);
	// TextView product_subject_price = (TextView) findViewById(R.id.product_price);

	String product_subject;
	String product_subject_info;
	String product_subject_price;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_commodity);
		commodity_1 = (Button) findViewById(R.id.commodity_1);
		commodity_2 = (Button) findViewById(R.id.commodity_2);
		commodity_3 = (Button) findViewById(R.id.commodity_3);
		commodity_4 = (Button) findViewById(R.id.commodity_4);
		img_back = (ImageView) findViewById(R.id.img_back);
		commodity_1.setOnClickListener(this);
		commodity_2.setOnClickListener(this);
		commodity_3.setOnClickListener(this);
		commodity_4.setOnClickListener(this);
		img_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.commodity_2:
			product_subject = "10颗红心";
			product_subject_price = "50";
			product_subject_info = product_subject + "  " + product_subject_price + "元";
			Object[] property_va1 = { getOutTradeNo(), product_subject_price, product_subject };
			soapService.getOrderInfoService(property_va1);
			break;
		case R.id.commodity_3:
			product_subject = "26颗红心";
			product_subject_price = "128";
			product_subject_info = product_subject + "  " + product_subject_price + "元";
			Object[] property_va2 = { getOutTradeNo(), product_subject_price, product_subject };
			soapService.getOrderInfoService(property_va2);
			break;
		case R.id.commodity_4:
			product_subject = "46颗红心";
			product_subject_price = "168";
			product_subject_info = product_subject + "  " + product_subject_price + "元";
			Object[] property_va3 = { getOutTradeNo(), product_subject_price, product_subject };
			soapService.getOrderInfoService(property_va3);
			break;
		case R.id.commodity_1:
			product_subject = "5颗红心";
			product_subject_price = "25";
			product_subject_info = product_subject + "  " + product_subject_price + "元";
			Object[] property_va4 = { getOutTradeNo(), product_subject_price, product_subject };
			soapService.getOrderInfoService(property_va4);
			break;
		case R.id.img_back:
			finish();
			break;
		default:
			break;
		}
		super.onClick(v);
	}

	/**
	 * get the out_trade_no for an order. 获取外部订单号
	 * 
	 */
	public String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}

	// class GoiAT extends AsyncTask<Object, Object, Object>{
	//
	// @Override
	// protected Object doInBackground(Object... params) {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// }
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (isParentActivity == true) {
			if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//				if ((System.currentTimeMillis() - exitTime) > 2000) {
//					Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
//					exitTime = System.currentTimeMillis();
//				} else {
//					finish();
//					System.exit(0);
//				}
				finish();
				return true;
			}
		} else {
			return super.onKeyDown(keyCode, event);
		}

		return super.onKeyDown(keyCode, event);
	}

	public void onEvent(SoapRes res) {
		if (res.getCode().equals(SOAP_UTILS.METHOD.GETORDERINFO)) {
			if (res != null) {
				Object oi = res.getObj();
				if (oi != null) {
					// System.out.println("oi :" +oi);
					Utils.showTextToast(this, "获取订单信息成功");
					Intent intent = new Intent();
					intent.setClass(CommodityListActivity.this, PayDemoActivity.class);
					// intent.setClass(CommodityListActivity.this, PayDemoActivity.class);
					intent.putExtra("oi", "" + oi);
					intent.putExtra("product_subject", product_subject);
					intent.putExtra("product_subject_info", "" + product_subject_info);
					intent.putExtra("product_subject_price", "" + product_subject_price);
					// TextView product_subject = (TextView) findViewById(R.id.product_subject);
					// TextView product_subject_info = (TextView) findViewById(R.id.product_subject_info);
					// TextView product_subject_price = (TextView) findViewById(R.id.product_price);
					startActivity(intent);
				} else {
					Utils.showTextToast(this, "获取订单信息失败");
				}
			}

		}
	}

}
