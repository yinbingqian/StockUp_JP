package com.alipay.sdk.pay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import lnpdit.lntv.tradingtime.R;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;

public class PayDemoActivity3 extends FragmentActivity {
	// 签约id
	public static final String PARTNER = "2088601304962087";
//	public static final String PARTNER = "2088411689175423";
	// 商家账户
	public static final String SELLER = "hao678@gmail.com";
	// 商户公私钥:由商户生成,商户私钥用于对商户发往支付宝的数据签名;商户公钥需要上传至支付宝,当支付宝收到商户发来的数据时用该公钥验证签名。
	 public static final String RSA_PRIVATE = ""+
"MIICXgIBAAKBgQCh+1YfT/X0YzWseephW/qV3Xwz9ivos/aJidkBa/6MWO4fGSxD"+
"Mkie0JwX6QHBxX86xe11dflanytMA+qE/p66NUkSvbvbQJHhUWN29W637uzuXGH6"+
"V7MpQNvhk5L32SQJ8nz0ToCF0BmLWw/4xo3yZx1ZRhibpqni/K4SiwbuLwIDAQAB"+
"AoGBAJmNFrEMLc4EFidwUv0EImHa2hiv7iPkJXtwT2SHqYAF9YVJaaoxiqHyg2Yv"+
"HdlAjUF7y4Sj10NOy859j92POYssrkZ+D7+z1u9VvPNOY0lWbqMvZxBIu+Hol0AT"+
"lZ5KPI/wegZeaMNQWxXwfGX2rk80uqm85THTmpQlMpbw3AKRAkEA152TwJHmkipS"+
"CjRaBjP/SwSd0gEO4CSBRMSHmWuOlX5mpUzVkq3zFE2A/AsF5RTbWihzAaXyIR6e"+
"kAlYT0KDDQJBAMBSGxxsFcez5wMotqVgYpw+P5aGd/DrDiAqu3fvJfMkcCS07wGl"+
"hOAU93nAtXQq1Lp8gAs9t42m3DjcOVM31ysCQQCmLU6I5mn8UR21u69+hGjIGqDA"+
"EAy0962hoI6Z0emskiTuVY9tYx+rkChPGPfMHQ0KKY6pwIoiuISgonMrcympAkB6"+
"XUNGqDaLY5meVu8u9c6IHssSmOgDb2sDaGzy+EQrGYoIsN5JK+kJMg3rd4N8MCw9"+
"oy1M+a2qAPqpauVGX4cVAkEAiBZx8cx9KqcRSp0tjsabKzlgB949Hvhp2KhE3dYw"+
"PIQT9rF5JWhS++2BwCRZEt3bKYieHJjoWxVs5binZA+h9Q";
//	public static final String RSA_PRIVATE = "" + "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANd+9aL6a5zvLoTg" + "DKyO3c0rVL/VI0uMa0LhZFC0UCjTIyHNreeJDi7s9+NZQmZb4IKRzrqhqjukfvf+" + "5ZskIx6W5NIu4zo8mow/2ZZmicq+h2Rg6+WC7xoyb37wjJZeXBgxHn+X/sslbyMb" + "HOmkpbPI7gsDLTtMUDJ65PVUL6UBAgMBAAECgYBUDUcN7JLwwTHpzeHjpE3K6q3B" + "rQZ5PynPCk6wU8Q4SDIZyzmbZ4/Kqc7+NCB8ADYPd4Oks83MOZr0a81ut6eiXVBl" + "6nocQbsmI3URGO5Re00p26atf9HFeyUTEEDIitaoKhGIncTI3P+Of9fPyHCWpfWx" + "5n/kNxaVKOU2dN9+iQJBAPbriipVZD1JZPFH9yrKdvDAwdO4cRLz/BeWFvL6TOAn" + "hkBCvtstFhgDVdN4PyB1U72TqCcHwmeLxZA4lMohjxsCQQDfa5lFB4ycJ9+ia9ci" + "6pZi7k1ggmFnecCoMYjyN1L4iPH/Bb+VNNP7AnNVFH7t3LM46tJscVFTOSf/5rCt" + "aHITAkAdNt0QCS5k+ewRiBw8WdwcvBGyxR3LykZHXCC2+tphowuFSuBd+kWk7bAX"
//			+ "5yBA7ODNi0zX4Vkk+yqS177y82mFAkEA1tKX2hbrLY7tajf08V7M4S0ve1eCHsOj" + "6lVOz09A2TacMVEuqGHWXak6ihkd/spSDa1ETP8cakAv5M/FG8esvwJBANrRFziz" + "xgu/A8lG0a4YGUO1OUqlLxj3c5rO23sgBxAYMqW42W9qQTdngYFqkuTq+M2ASSKS" + "rEdaKUIbctUmbWw=";
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
//	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	private static final int SDK_PAY_FLAG = 1;

	private static final int SDK_CHECK_FLAG = 2;

//	private static final String temp_oinfo = "partner="2088601304962087"
//&out_trade_no="050809380618412"
//&subject="订单支付,测试红心"
//&body="订单支付,测试红心"
//&total_fee="0.01"
//&notify_url="http://218.60.13.9:8082/api/order/AlipayResult"
//&service="mobile.secunitypay.pay"
//&_input_charset="UTF-8"&return_url="http://m.alipay.com"
//&payment_type="1"
//&seller_id="hao678@gmail.com"
//&it_b_pay="1m"
//&sign=",iNLvyBtPc%3d"
//&sign_type="RSA"";
	
	
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				Result resultObj = new Result((String) msg.obj);
				String resultStatus = resultObj.resultStatus;

				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(PayDemoActivity3.this, "支付成功", Toast.LENGTH_SHORT).show();
					PayDemoActivity3.this.finish();
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”
					// 代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(PayDemoActivity3.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
						PayDemoActivity3.this.finish();

					} else {
						System.out.println("AAAAAAAAAA   "+resultStatus);
						Toast.makeText(PayDemoActivity3.this, "支付失败", Toast.LENGTH_SHORT).show();
//						PayDemoActivity.this.finish();

					}
				}
				break;
			}
			case SDK_CHECK_FLAG: {
				Toast.makeText(PayDemoActivity3.this, "检查结果为：" + msg.obj, Toast.LENGTH_SHORT).show();
				break;
			}
			default:
				break;
			}
		};
	};
String oi = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_main);
		Intent intent = this.getIntent();

		oi = intent.getStringExtra("oi");
		System.out.println(">>>##>> " + oi);
		
		TextView product_subject = (TextView) findViewById(R.id.product_subject);
		TextView product_subject_info = (TextView) findViewById(R.id.product_subject_info);
		TextView product_subject_price = (TextView) findViewById(R.id.product_price);
		product_subject.setText(intent.getStringExtra("product_subject"));
		product_subject_info.setText(intent.getStringExtra("product_subject_info"));
		product_subject_price.setText( intent.getStringExtra("product_subject_price"));
	}

	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public void pay(View v) {
		String orderInfo = getOrderInfo("测试红心", "此商品为测试红心", "0.01");
//		String sign = sign(orderInfo);
//		try {
			// 仅需对sign 做URL编码
//			sign = URLEncoder.encode(sign, "UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		System.out.println(">>>##>>>" + TestPay.orderInfo());
//		final String payInfo = oi;
		final String payInfo = oi;
//		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();
		System.out.println(">>>>> payInfo : " + payInfo);
		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(PayDemoActivity3.this);
				// 调用支付接口
				String result = alipay.pay(payInfo);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * check whether the device has authentication alipay account. 查询终端设备是否存在支付宝认证账户
	 * 
	 */
	public void check(View v) {
		Runnable checkRunnable = new Runnable() {

			@Override
			public void run() {
				PayTask payTask = new PayTask(PayDemoActivity3.this);
				boolean isExist = payTask.checkAccountIfExist();

				Message msg = new Message();
				msg.what = SDK_CHECK_FLAG;
				msg.obj = isExist;
				mHandler.sendMessage(msg);
			}
		};

		Thread checkThread = new Thread(checkRunnable);
		checkThread.start();

	}

	/**
	 * get the sdk version. 获取SDK版本号
	 * 
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(this);
		String version = payTask.getVersion();
		Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
	}

	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	public String getOrderInfo(String subject, String body, String price) {
		// 合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

		// 接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
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

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	public String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}

}
