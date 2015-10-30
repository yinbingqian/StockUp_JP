package com.sxit.activity.welcome;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;

import com.sxit.activity.adviser.th.AMainTabHostActivity;
import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.login.Login_Activity;
import com.sxit.activity.register.Register_Activity;
import com.sxit.activity.th.MainTabHostActivity;
import com.sxit.db.DBHelper;
import com.sxit.entity.Adviser;
import com.sxit.entity.LoginUser;
import com.sxit.http.SoapRes;
import com.sxit.http.SoapWebService;
import com.sxit.md5.MD5;
import com.sxit.md5.MD5Plus;

import lnpdit.lntv.tradingtime.R;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.Utils;
import com.umeng.update.UmengUpdateAgent;

/**
 * 欢迎页
 * 
 * @author huanyu 类名称：Welcome_Activity 创建时间:2014-10-26 下午10:05:03
 */
public class Welcome_Activity extends BaseActivity {
	private TextView tv_go;
	private TextView tv_register;
	private TextView tv_login;
	private Object NULL = null;
	private DBHelper dbh;
	private String[] property_anm = { "name", "password" };
	private Object[] property_ava = new Object[2];

	// property_ava = { tempStr, tempStr };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		initDB();
		initView();
		setListeners();
		Utils.getVersionName(Welcome_Activity.this);
		UmengUpdateAgent.setUpdateOnlyWifi(true);
		UmengUpdateAgent.update(this);

	}

	private void initDB() {
		dbh = new DBHelper(this);
	}

	private void initView() {
		tv_go = (TextView) findViewById(R.id.tv_go);
		tv_go.setText(Html.fromHtml("<a href='http://www.google.com/'>" + getString(R.string.welcome_below) + "</a>"));
		tv_go.setMovementMethod(LinkMovementMethod.getInstance());
		CharSequence text = tv_go.getText();
		if (text instanceof Spannable) {
			int end = text.length();
			Spannable sp = (Spannable) tv_go.getText();
			URLSpan[] urls = sp.getSpans(0, end, URLSpan.class);

			SpannableStringBuilder style = new SpannableStringBuilder(text);
			style.clearSpans(); // should clear old spans
			for (URLSpan url : urls) {
				URLSpan myURLSpan = new URLSpan(url.getURL());
				style.setSpan(myURLSpan, sp.getSpanStart(url), sp.getSpanEnd(url), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				style.setSpan(new ForegroundColorSpan(Color.WHITE), sp.getSpanStart(url), sp.getSpanEnd(url), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);// 设置前景色为红色
			}
			tv_go.setText(style);
		}
		tv_register = (TextView) findViewById(R.id.tv_register);
		tv_login = (TextView) findViewById(R.id.tv_login);

		String tempStr = getResources().getString(R.string.app_pinyin_name);
		property_ava[0] = tempStr;
		property_ava[1] = tempStr;
		String[] property_nm = { "pagesize", "pageindex" };
		Object[] property_va = { 100, 1 };
		if (dbh.queryLoginUserInfo().getUserid() == null) {
			System.out.println(">>>未检测到用户");
			new CheckStatusAT().execute(property_nm, property_va);
		} else {
			System.out.println(">>>检测到用户 ：" + dbh.queryLoginUserInfo().getUserid());
			new CheckStatusAT2().execute(property_nm, property_va);
		}

	}

	private void setListeners() {
		tv_register.setOnClickListener(this);
		tv_login.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_login:
			intent.setClass(Welcome_Activity.this, Login_Activity.class);
			// intent.setClass(Welcome_Activity.this, MainTabHostActivity.class);
			// intent.putExtra("locationCur", 1);
			startActivity(intent);
			finish();
			break;
		case R.id.tv_register:
			intent.setClass(Welcome_Activity.this, Register_Activity.class);
			// intent.setClass(Welcome_Activity.this, MainTabHostActivity.class);
			// intent.putExtra("locationCur", 1);
			startActivity(intent);
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

	List<Adviser> data;

	class CheckStatusAT extends AsyncTask<Object, Object, Object> {
		@Override
		protected void onPreExecute() {
			data = new ArrayList<Adviser>();
			super.onPreExecute();
		}

		@Override
		protected Object doInBackground(Object... params) {
			// String[] property_nm = {"pagesize","pageindex"};
			// Object[] property_va = {10,1};
			Object res_obj = SoapWebService.data(SOAP_UTILS.METHOD.GETADMIN, (String[]) params[0], (Object[]) params[1]);
			if (null != res_obj) {
				SoapObject so = (SoapObject) res_obj;
				SoapObject soapchilds = (SoapObject) so.getProperty(0);
				SoapObject soapchildss = (SoapObject) soapchilds.getProperty(0);
				Adviser adviser;
				if (soapchilds != null) {
					for (int i = 0; i < soapchilds.getPropertyCount(); i++) {
						SoapObject soa = (SoapObject) soapchilds.getProperty(i);
						adviser = new Adviser();
						adviser.setAdv_user_id(soa.getProperty("Id").toString());
						adviser.setCrtime(soa.getProperty("CrTime").toString());
						adviser.setEmail(soa.getProperty("Email").toString());
						adviser.setGroupid(soa.getProperty("GroupId").toString());
						adviser.setHeadpic(soa.getProperty("HeadPic").toString());
						adviser.setSex(soa.getProperty("Sex").toString());
						adviser.setIslock(Boolean.parseBoolean(soa.getProperty("Islock").toString()));
						adviser.setLevel(soa.getProperty("Level").toString());
						adviser.setMark(soa.getProperty("Mark").toString());
						adviser.setMobilephone(soa.getProperty("Mobilephone").toString());
						adviser.setName(soa.getProperty("Name").toString());
						adviser.setOrgid(soa.getProperty("Orgid").toString());
						adviser.setOrgname(soa.getProperty("OrgName").toString());
						adviser.setPaidmark(soa.getProperty("Paidmark").toString());
						adviser.setPassword(soa.getProperty("Password").toString());
						adviser.setPtitle(soa.getProperty("PTitle").toString());
						adviser.setRealname(soa.getProperty("RealName").toString());
						adviser.setRewardmark(soa.getProperty("Rewardmark").toString());
						adviser.setResume(soa.getProperty("Resume").toString());
						adviser.setSpecialty(soa.getProperty("Specialty").toString());
						adviser.setStatus(soa.getProperty("Status").toString());
						adviser.setHeartcount(soa.getProperty("Heartcount").toString());
						data.add(adviser);
					}
				}
				dbh.clearAllAdviser();
				dbh.insAdviserInfo(data);
				System.out.println("-------------" + soapchildss);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			new CheckStatusAT3().execute(property_anm, property_ava);
			super.onPostExecute(result);
		}
	}

	class CheckStatusAT2 extends AsyncTask<Object, Object, Object> {
		@Override
		protected void onPreExecute() {
			data = new ArrayList<Adviser>();
			super.onPreExecute();
		}

		@Override
		protected Object doInBackground(Object... params) {
			// String[] property_nm = {"pagesize","pageindex"};
			// Object[] property_va = {10,1};
			Object res_obj = SoapWebService.data(SOAP_UTILS.METHOD.GETADMIN, (String[]) params[0], (Object[]) params[1]);
			if (null != res_obj) {
				SoapObject so = (SoapObject) res_obj;
				SoapObject soapchilds = (SoapObject) so.getProperty(0);
				SoapObject soapchildss = (SoapObject) soapchilds.getProperty(0);
				Adviser adviser;
				if (soapchilds != null) {
					for (int i = 0; i < soapchilds.getPropertyCount(); i++) {
						SoapObject soa = (SoapObject) soapchilds.getProperty(i);
						adviser = new Adviser();
						adviser.setAdv_user_id(soa.getProperty("Id").toString());
						adviser.setCrtime(soa.getProperty("CrTime").toString());
						adviser.setEmail(soa.getProperty("Email").toString());
						adviser.setGroupid(soa.getProperty("GroupId").toString());
						adviser.setHeadpic(soa.getProperty("HeadPic").toString());
						adviser.setSex(soa.getProperty("Sex").toString());
						adviser.setIslock(Boolean.parseBoolean(soa.getProperty("Islock").toString()));
						adviser.setLevel(soa.getProperty("Level").toString());
						adviser.setMark(soa.getProperty("Mark").toString());
						adviser.setMobilephone(soa.getProperty("Mobilephone").toString());
						adviser.setName(soa.getProperty("Name").toString());
						adviser.setOrgid(soa.getProperty("Orgid").toString());
						adviser.setOrgname(soa.getProperty("OrgName").toString());
						adviser.setPaidmark(soa.getProperty("Paidmark").toString());
						adviser.setPassword(soa.getProperty("Password").toString());
						adviser.setPtitle(soa.getProperty("PTitle").toString());
						adviser.setRealname(soa.getProperty("RealName").toString());
						adviser.setRewardmark(soa.getProperty("Rewardmark").toString());
						adviser.setResume(soa.getProperty("Resume").toString());
						adviser.setSpecialty(soa.getProperty("Specialty").toString());
						adviser.setStatus(soa.getProperty("Status").toString());
						adviser.setHeartcount(soa.getProperty("Heartcount").toString());
						data.add(adviser);
					}
				}
				dbh.clearAllAdviser();
				dbh.insAdviserInfo(data);

				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println("-------------" + soapchildss);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			LoginUser loginUser = dbh.queryLoginUserInfo();

			System.out.println(">>>!!!! username : " + loginUser.getName());
			System.out.println(">>>!!!! password : " + loginUser.getPassword());
			System.out.println(">>>!!!! logintype : " + loginUser.getLoginType());

			switch (loginUser.getLoginType()) {
			case 0:
				Object[] property_va = { loginUser.getName(), loginUser.getPassword() };
				System.out.println(">>> md5  "+MD5.value(loginUser.getPassword(),MD5.BIT32));

				soapService.adminLogin(property_va);
				break;
			case 1:
				Object[] property_vc = { loginUser.getName(), MD5Plus.stringToMD5(loginUser.getPassword()), Utils.getImei(Welcome_Activity.this) };
//				Object[] property_vc = { loginUser.getName(), loginUser.getPassword(), Utils.getImei(Welcome_Activity.this) };
//				System.out.println(">>> md5  "+MD5.value(loginUser.getPassword(),MD5.BIT32));
				soapService.userInfoLogin(property_vc);

				break;

			default:
				break;
			}

			super.onPostExecute(result);
		}
	}

	class CheckStatusAT3 extends AsyncTask<Object, Object, Object> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Object doInBackground(Object... params) {
			Object res_obj = SoapWebService.data(SOAP_UTILS.METHOD.ADMINLOGIN, (String[]) params[0], (Object[]) params[1]);

			if (null != res_obj) {
				SoapObject so = (SoapObject) res_obj;
				System.out.println("!!!!!"+res_obj);
				// SoapObject soapchilds = (SoapObject) so.getProperty(0);
				// SoapObject soapchildss = (SoapObject) soapchilds.getProperty(0);
				Adviser adviser;
				// if (soapchilds != null) {
				// for (int i = 0; i < soapchilds.getPropertyCount(); i++) {
				SoapObject soa = (SoapObject) so.getProperty(0);
				adviser = new Adviser();
//				adviser.setAdv_user_id(soa.getProperty("Id").toString());
				adviser.setAdv_user_id("1");
				adviser.setCrtime(soa.getProperty("CrTime").toString());
				adviser.setEmail(soa.getProperty("Email").toString());
				adviser.setGroupid(soa.getProperty("GroupId").toString());
				adviser.setHeadpic(soa.getProperty("HeadPic").toString());
				adviser.setSex(soa.getProperty("Sex").toString());
				adviser.setIslock(Boolean.parseBoolean(soa.getProperty("Islock").toString()));
				adviser.setLevel(soa.getProperty("Level").toString());
				adviser.setMark(soa.getProperty("Mark").toString());
				adviser.setMobilephone(soa.getProperty("Mobilephone").toString());
				adviser.setName(soa.getProperty("Name").toString());
				adviser.setOrgid(soa.getProperty("Orgid").toString());
				adviser.setOrgname(soa.getProperty("OrgName").toString());
				adviser.setPaidmark(soa.getProperty("Paidmark").toString());
				adviser.setPassword(soa.getProperty("Password").toString());
				adviser.setPtitle(soa.getProperty("PTitle").toString());
				adviser.setRealname("股涨大讲堂");
//				adviser.setRealname(soa.getProperty("RealName").toString());
				adviser.setRewardmark(soa.getProperty("Rewardmark").toString());
				adviser.setResume(soa.getProperty("Resume").toString());
				adviser.setSpecialty(soa.getProperty("Specialty").toString());
				adviser.setStatus(soa.getProperty("Status").toString());
				adviser.setHeartcount(soa.getProperty("Heartcount").toString());
				System.out.println("!!!!!!!!!!!!!@@@"+adviser.getHeartcount());
				dbh.insAdviserGz(adviser);
				dbh.insAdviserNs();
				dbh.queryAdviserGz();
				System.out.println("-------------" + so + " ### " + adviser.getRealname());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			dbh.queryAdviserInfo();
			intent.setClass(Welcome_Activity.this, Login_Activity.class);
			startActivity(intent);
			finish();
			super.onPostExecute(result);
		}
	}

	Set<String> set = new HashSet<String>();

	public void onEvent(SoapRes res) {
		super.onEvent(res);
		if (res.getCode().equals(SOAP_UTILS.METHOD.USERINFOLOGIN)) {
			LoginUser loginUser = (LoginUser) res.getObj();
			if (loginUser != null) {
				if (loginUser.getUserid().equals("0")) {
					Utils.showTextToast(this, getString(R.string.userlogin_fail));
				} else {
//					Utils.showTextToast(this, "用户登录id	" + loginUser.getUserid());
					loginUser.setLoginType(SOAP_UTILS.USER);
					set.add("TradingUser");// 用户
//					set.add("TradingDemo");// 用户
					JPushInterface.setAliasAndTags(this, loginUser.getName(), set);
					Object NULL = null;
					String wStr1 = getResources().getString(R.string.app_welcome_text1);
					String wStr2 = getResources().getString(R.string.app_welcome_text2);
					String[] wStr = { wStr1, wStr2 };
					if (!(dbh.queryGzAdminMsg(getLoginUser().getUserid()) > 0)) {
						dbh.initSysMsgData(getLoginUser().getUserid(), wStr);
					}
					intent.setClass(this, MainTabHostActivity.class);
					startActivity(intent);
					finish();
				}
			} else {
				Utils.showTextToast(this, getString(R.string.userlogin_fail));
			}
		} else if (res.getCode().equals(SOAP_UTILS.METHOD.ADMINLOGIN)) {
			LoginUser loginUser = (LoginUser) res.getObj();
			if (loginUser != null) {
				if (loginUser.getUserid().equals("0")) {
					Utils.showTextToast(this, getString(R.string.advlogin_fail));
				} else {
//					Utils.showTextToast(this, "顾问登录id	" + loginUser.getUserid());
					loginUser.setLoginType(SOAP_UTILS.ADMIN);
					set.add("TradingAnalyst");// 分析师
					JPushInterface.setAliasAndTags(this, "Analyst" + loginUser.getName(), set);
					intent.setClass(this, AMainTabHostActivity.class);
					startActivity(intent);
					finish();
				}
			} else {
				Utils.showTextToast(this, getString(R.string.advlogin_fail));
			}
		}
	}

	// class InitMsgAT extends AsyncTask<Object, Object, Object> {
	//
	// @Override
	// protected Object doInBackground(Object... params) {
	// try {
	// Thread.sleep(2000);
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// if (!(dbh.queryGzAdminMsg(getLoginUser().getUserid()) < 0)) {
	// dbh.initAMsgData(getLoginUser().getUserid(), getResources().getString(R.string.app_welcome_text));
	// }
	// return null;
	// }
	//
	// }

}
