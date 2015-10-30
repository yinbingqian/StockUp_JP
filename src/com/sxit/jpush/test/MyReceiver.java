package com.sxit.jpush.test;

import lnpdit.lntv.tradingtime.R;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.sxit.activity.base.MyApplication;
import com.sxit.activity.login.Login_Activity;
import com.sxit.db.DBHelper;
import com.sxit.entity.Adviser;
import com.sxit.entity.ChatMessage;
import com.sxit.entity.LoginUser;
import com.sxit.entity.UserInfo;
import com.sxit.entity.eb.ChatMessageEB;
import com.sxit.utils.EventCache;
import com.sxit.utils.SOAP_UTILS;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则： 1) 默认用户会打开主界面 2) 接收不到自定义消息
 */

public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";
	private LoginUser loginUser;
	public MyApplication myApplication;

	private DBHelper dbh;
	private int login_type = -1;
	private ChatMessage cm;
	private SharedPreferences sp;

	@Override
	public void onReceive(Context context, Intent intent) {
		dbh = new DBHelper(context);
		if (dbh.queryLoginUserInfo().getUserid() != null) {
			System.out.println("<<<[" + dbh.queryLoginUserInfo().getUserid() + "]");
			sp = context.getSharedPreferences("loginInfo", Context.MODE_WORLD_READABLE);

			Bundle bundle = intent.getExtras();
			Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

			if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
				String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
				Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
				// send the Registration Id to your server...

			} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
				Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
				processCustomMessage(context, bundle);
				Toast.makeText(context, "您有一条新信息", Toast.LENGTH_SHORT).show();

				String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
				String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
				// {"Crtime":"2014\/11\/7 15:13:49","AnalystName":"王元石","ID":"1144","AnalystID":"20","Content":"!!!!","InfoDirection":"1","Photo":"3.jpg","InfoType":"1"}
				// {"Crtime":"2014\/11\/8 14:09:37","AnalystName":"测试分析师1","ID":"1242","AnalystID":"27","Content":"测试研报3","InfoDirection":"2","Photo":"635510525770955617.png","InfoType":"2"}
				String ID = "";
				String InfoType = "";
				String InfoDirection = "";
				String Content = "";
				String Content3_title = "";
				String Content3_img = "";
				String Content3_url = "";
				String Content3_context = "";
				String Photo = "";
				String time = "";
				String AnalystID = "";
				String AnalystName = "";
				myApplication = MyApplication.getInstance();
				DBHelper dbh = new DBHelper(context);

				// loginUser = myApplication.getLoginUser();
				loginUser = dbh.queryLoginUserInfo();
				// if (loginUser == null) {
				// loginUser = new LoginUser();
				// if (SOAP_UTILS.METHOD.ADMINLOGIN.equals(sp.getString("USER_TYPE", ""))) {
				// loginUser.setLoginType(1);
				// } else if (SOAP_UTILS.METHOD.USERINFOLOGIN.equals(sp.getString("USER_TYPE", ""))) {
				// loginUser.setLoginType(0);
				// }
				//
				// }
				login_type = dbh.queryLoginUserInfo().getLoginType();

				try {
					JSONObject extrasJson = new JSONObject(extras);
					ID = extrasJson.getString("ID");
					InfoType = extrasJson.getString("InfoType");
					InfoDirection = extrasJson.getString("InfoDirection");
					
					if (InfoType.equals("2")) {
						JSONObject extrasJson2 = new JSONObject(extrasJson.getString("Content"));
						Content3_title = extrasJson2.getString("title");
						Content3_img = extrasJson2.getString("img");
						Content3_url = extrasJson2.getString("url");
						Content3_context = extrasJson2.getString("content");
						System.out.println(">>>>" + extrasJson2.getString("title"));
						System.out.println(">>>>" + extrasJson2.getString("content"));
						System.out.println(">>>>" + extrasJson2.getString("img"));
						System.out.println(">>>>" + extrasJson2.getString("url"));
					} else {
						Content = extrasJson.getString("Content");
					}

					Photo = extrasJson.getString("Photo");
					time = extrasJson.getString("Crtime");
					AnalystID = extrasJson.getString("AnalystID");
					AnalystName = extrasJson.getString("AnalystName");

					cm = new ChatMessage();
					if (InfoType.equals("2")) {
						// TODO 研报
						Content = extrasJson.getString("Content");
						JSONObject content_json = new JSONObject(Content);
						String contentString = content_json.getString("content");
						cm.setContent3_content(contentString);
						cm.setContent3_img(Content3_img);
						cm.setContent3_title(Content3_title);
						cm.setContent3_url(Content3_url);
						cm.setMsg_type(2);
					} else if (InfoType.equals("3")) {
						// TODO 图片
						// cm.setContent2(content2);
						cm.setContent1(Content);
						cm.setMsg_type(1);
					} else if (InfoType.equals("1")) {
						// TODO 文本
						cm.setContent1(Content);
						cm.setMsg_type(0);
					} else {
						cm.setContent1("异常消息");
					}
					time = time.replaceAll("\\/", "-");
					cm.setLogin_id(loginUser.getUserid());
					cm.setMsg_direction(0);
					cm.setMsg_send_date(time);
					cm.setSender_id(AnalystID);
					cm.setStatus(0);
					if (login_type == 0) {
						System.out.println(">>>  顾问数据操作");
						UserInfo userinfo = new UserInfo(AnalystID, AnalystName, Photo);
						cm.setUserinfo(userinfo);
						dbh.insAMsgData(cm);
					} else {
						System.out.println(">>>  投资人数据操作");
						String header = dbh.getAdviserHeader(AnalystID);
						UserInfo userinfo = new UserInfo(AnalystID, AnalystName, header);
						cm.setUserinfo(userinfo);
						dbh.insMsgData(cm);
					}

					ChatMessageEB cmeb = new ChatMessageEB("MyReceiver", cm);
					EventCache.msgNotiEvent.post(cmeb);
				} catch (Exception e) {
					Log.e("JPush Extras Error:", e.toString());
				}
				UserInfo ui = new UserInfo();

				showNotification(AnalystName + " 有一条未读消息", message, context);
				// }
				// }

			} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
				Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
				int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
				Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

			} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
				Log.d(TAG, "[MyReceiver] 用户点击打开了通知");

				// 打开自定义的Activity
				// Intent i = new Intent(context, TestActivity.class);
				Intent i = new Intent();
				Bundle bd = new Bundle();
				bundle.putSerializable("userinfo", cm.getUserinfo());
				i.putExtras(bd);
				i.setClass(context, com.sxit.activity.adviser.chatmsg.Chat_Activity.class);
				// i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				// i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
				if (myApplication.sender_id.equals(cm.getSender_id())) {

				} else {

				}
				context.startActivity(i);

			} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
				Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
				// 在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

			} else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
				boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
				Log.e(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
			} else {
				Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
			}
		}
	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			} else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}

	// send msg to MainActivity
	private void processCustomMessage(Context context, Bundle bundle) {
		if (MainActivity.isForeground) {
			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
			Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
			msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
			if (!ExampleUtil.isEmpty(extras)) {
				try {
					JSONObject extraJson = new JSONObject(extras);
					if (null != extraJson && extraJson.length() > 0) {
						msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
					}
				} catch (JSONException e) {

				}

			}
			context.sendBroadcast(msgIntent);
		}
	}

	public void showNotification(String _title, String _content, Context context) {
		String title = _content;
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);
		CharSequence text = title;
		long when = System.currentTimeMillis();
		Notification notification = new Notification(R.drawable.app_icon, text, when);

		notification.defaults = Notification.DEFAULT_SOUND;
		notification.flags = Notification.FLAG_AUTO_CANCEL;

		Intent intent = new Intent();
		intent.setAction("lnpdit.lntv.tradingtime.newmsg");
		context.sendBroadcast(intent);

		CharSequence contentTitle = _title;
		CharSequence contentText = text;

		if (login_type == 0) {
			System.out.println(">>>  投资人发来消息提示");
			// i.setClass(context, com.sxit.activity.adviser.chatmsg.Chat_Activity.class);
			Intent notificationIntent = new Intent(context, com.sxit.activity.adviser.chatmsg.Chat_Activity.class);
			Bundle bd = new Bundle();
			bd.putSerializable("userInfo", cm.getUserinfo());
			notificationIntent.putExtras(bd);
			notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_CANCEL_CURRENT);

			// PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_CANCEL_CURRENT);

			notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
			System.out.println("#########################");
			mNotificationManager.notify(1, notification);
			System.out.println("#########################");
		} else {
			System.out.println(">>>  顾问发来消息提示");
			// i.setClass(context, com.sxit.activity.adviser.chatmsg.Chat_Activity.class);
			Intent notificationIntent = new Intent(context, com.sxit.activity.chatmsg.Chat_Activity.class);
			Bundle bd = new Bundle();
			Adviser adviser = new Adviser();

			adviser.setAdv_user_id(cm.getUserinfo().getUserid());
			System.out.println("*******  ||  "+cm.getUserinfo().getUserheader());
			adviser.setHeadpic(cm.getUserinfo().getUserheader());
			adviser.setRealname(cm.getUserinfo().getUsername());
			bd.putSerializable("analystInfo", adviser);
			notificationIntent.putExtras(bd);
			notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_CANCEL_CURRENT);
			System.out.println("xxxxxx : " + cm.getUserinfo().getUserheader());
			// PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_CANCEL_CURRENT);
			// notification.set
			notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
			System.out.println("#########################");
			mNotificationManager.notify(1, notification);
			System.out.println("#########################");
		}

		//
		//
		// // i.setClass(context, com.sxit.activity.adviser.chatmsg.Chat_Activity.class);
		// Intent notificationIntent = new Intent(context, com.sxit.activity.chatmsg.Chat_Activity.class);
		//
		// Bundle bd = new Bundle();
		// bd.putSerializable("userInfo",cm.getUserinfo());
		// notificationIntent.putExtras(bd);
		// notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_CANCEL_CURRENT);
		//
		// // PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_CANCEL_CURRENT);
		//
		//
		// notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
		// System.out.println("#########################");
		// mNotificationManager.notify(1, notification);
		// System.out.println("#########################");
	}
}
