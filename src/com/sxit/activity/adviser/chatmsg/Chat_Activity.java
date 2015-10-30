package com.sxit.activity.adviser.chatmsg;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lnpdit.lntv.tradingtime.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sxit.activity.adviser.th.AMainTabHostActivity;
import com.sxit.activity.base.BaseActivity;
import com.sxit.db.DBHelper;
import com.sxit.entity.ChatMessage;
import com.sxit.entity.UserInfo;
import com.sxit.entity.eb.ChatMessageEB;
import com.sxit.http.SoapWebService;
import com.sxit.utils.EventCache;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.Utils;

// 1 是我发的 0 是接收的
public class Chat_Activity extends BaseActivity {
	private Button bt_send;
	private Button bt_img;
	private ListView chat_message_listview;
	private TextView tv_title;
	private com.sxit.activity.adviser.chatmsg.adapter.ChattingAdapter ca;
	private List<ChatMessage> messages;
	private EditText chat_msg_input_et_single;
	private ImageView img_back, img_info;
	private Object NULL = null;
	private UserInfo ui;
	private ChatMessage cm;
	private UserInfo userInfo;
	private DBHelper dbh;

	Context context;

	public static final int TO_SELECT_PHOTO = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		System.out.println(">>>>>");
		this.setContentView(R.layout.activity_achat_layout);
		context = this;
		EventCache.msgNotiEvent.register(this);
		this.isParentActivity = false;
		dbh = new DBHelper(this);
		intent = getIntent();
		userInfo = new UserInfo();
		userInfo = (UserInfo) intent.getSerializableExtra("userInfo");
		bt_send = (Button) findViewById(R.id.bt_send);
		bt_img = (Button) findViewById(R.id.chatting_setmode_biaoqing_btn);
		bt_img.isClickable();
		tv_title = (TextView) findViewById(R.id.tv_title);
		chat_message_listview = (ListView) findViewById(R.id.chat_message_listview);
		chat_msg_input_et_single = (EditText) findViewById(R.id.chat_msg_input_et_single);
		ui = new UserInfo();
		cm = new ChatMessage();
		img_back = (ImageView) findViewById(R.id.img_back);
		img_info = (ImageView) findViewById(R.id.img_info);
		img_back.setOnClickListener(this);
		img_info.setOnClickListener(this);
		bt_send.setOnClickListener(this);
		bt_img.setOnClickListener(this);
		messages = new ArrayList<ChatMessage>();
		// Intent intent = this.getIntent();
		if (userInfo == null) {
			finish();
		}
		tv_title.setText(userInfo.getUsername());
		// initTestData();
		initMsgData();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_send:
			sendTextMsg(chat_msg_input_et_single.getText().toString().trim());
			break;
		case R.id.chatting_setmode_biaoqing_btn:
			Intent intent1 = new Intent(context, SelectPicActivity.class);
			startActivityForResult(intent1, TO_SELECT_PHOTO);
			// startActivity(intent1);
			break;
		case R.id.img_back:
			goToMain();
			System.out.println("back");
			break;
		case R.id.img_info:

			break;

		default:
			break;
		}
		super.onClick(v);
	}

	public void initMsgData() {
		dbh.setAMsgReaded(getLoginUser().getUserid(), userInfo.getUserid());
		messages = dbh.queryAMsgData(userInfo, getLoginUser());
		ca = new com.sxit.activity.adviser.chatmsg.adapter.ChattingAdapter(
				Chat_Activity.this, messages, getLoginUser());
		chat_message_listview.setAdapter(ca);
	}
	
	private void sendImgMsg(Bitmap bitmap) {
		
		Long tsLong = System.currentTimeMillis()/1000;
		String picNameString = tsLong.toString() + ".jpg";
		
		String picBaseStream = bitmapToBase64(bitmap);
		
		UserInfo ui = new UserInfo();
		ui.setUserheader(getLoginUser().getHeadpic());
		ui.setUserid(getLoginUser().getUserid());
		ui.setUsername(getLoginUser().getRealname());
		cm = new ChatMessage();
		cm.setContent1(picNameString);
		cm.setMsg_direction(1);
		cm.setMsg_send_date(Utils.getSystemDate());
		cm.setMsg_type(1);
		cm.setLogin_id(getLoginUser().getUserid());
		cm.setStatus(1);
		cm.setSender_id(userInfo.getUserid());
		cm.setUserinfo(userInfo);
		messages.add(cm);
		if (null == ca || ca.getCount() == 0) {
			ca = new com.sxit.activity.adviser.chatmsg.adapter.ChattingAdapter(
					Chat_Activity.this, messages, getLoginUser());
			chat_message_listview.setAdapter(ca);
		} else {
			ca.notifyDataSetChanged();
		}
		dbh.insAMsgData(cm);
		// new SendAT().execute(NULL);
		String[] property_nm = { "fromUser", "toUser", "devType", "images", "imageName" };
		Object[] property_va = { getLoginUser().getUserid(), userInfo.getUserid(), "1", picBaseStream, picNameString };
		new SendIMG().execute(property_nm, property_va);
		
		chat_msg_input_et_single.setText("");
		InputMethodManager imm = (InputMethodManager) getSystemService(Chat_Activity.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(chat_msg_input_et_single.getWindowToken(),
				0);
		new RefreshListAT().execute(NULL);
	}

	private void sendTextMsg(String content) {
		if (content == null || content.equals("")) {
			Toast.makeText(this, "不能发送空信息", Toast.LENGTH_SHORT).show();
		} else {
			// Toast.makeText(this, "信息发送成功，请耐心等待回复",
			// Toast.LENGTH_SHORT).show();
			UserInfo ui = new UserInfo();
			ui.setUserheader(getLoginUser().getHeadpic());
			ui.setUserid(getLoginUser().getUserid());
			ui.setUsername(getLoginUser().getRealname());
			cm = new ChatMessage();
			cm.setContent1(content);
			cm.setMsg_direction(1);
			cm.setMsg_send_date(Utils.getSystemDate());
			cm.setMsg_type(0);
			cm.setLogin_id(getLoginUser().getUserid());
			cm.setStatus(1);
			cm.setSender_id(userInfo.getUserid());
			cm.setUserinfo(userInfo);
			messages.add(cm);
			if (null == ca || ca.getCount() == 0) {
				ca = new com.sxit.activity.adviser.chatmsg.adapter.ChattingAdapter(
						Chat_Activity.this, messages, getLoginUser());
				chat_message_listview.setAdapter(ca);
			} else {
				ca.notifyDataSetChanged();
			}
			dbh.insAMsgData(cm);
			// new SendAT().execute(NULL);
			String[] property_nm = { "fromUser", "toUser", "blogTitle",
					"blogAbstract", "content", "devType", "infoType",
					"infoDirection" };
			Object[] property_va = { getLoginUser().getUserid(),
					userInfo.getUserid(), content, content, content, 1, 1, 2 };
			new SendAT().execute(property_nm, property_va);
		}
		chat_msg_input_et_single.setText("");
		InputMethodManager imm = (InputMethodManager) getSystemService(Chat_Activity.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(chat_msg_input_et_single.getWindowToken(),
				0);
		new RefreshListAT().execute(NULL);
	}

	private void FreshList() {
		ca.notifyDataSetChanged();
		chat_message_listview
				.setSelection(chat_message_listview.getCount() - 1);
	}

	class RefreshListAT extends AsyncTask<Object, Object, Object> {
		@Override
		protected void onPreExecute() {
			FreshList();
			super.onPreExecute();
		}

		@Override
		protected Object doInBackground(Object... params) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			FreshList();
			super.onPostExecute(result);
		}

	}
	
	class SendIMG extends AsyncTask<Object, Object, Object> {
		@Override
		protected void onPreExecute() {
			bt_img.setEnabled(false);
			super.onPreExecute();
		}

		@Override
		protected Object doInBackground(Object... params) {
			System.out.println(">>>>>");
			Object res_obj = SoapWebService.data(
					SOAP_UTILS.METHOD.PICSUBMIT, (String[]) params[0],
					(Object[]) params[1]);
			System.out.println("####" + res_obj);
			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			bt_img.setEnabled(true);
			super.onPostExecute(result);
		}

	}

	class SendAT extends AsyncTask<Object, Object, Object> {
		@Override
		protected void onPreExecute() {
			bt_send.setEnabled(false);
			super.onPreExecute();
		}

		@Override
		protected Object doInBackground(Object... params) {
			System.out.println(">>>>>");
			Object res_obj = SoapWebService.data(
					SOAP_UTILS.METHOD.INTERACTIONSUBMIT, (String[]) params[0],
					(Object[]) params[1]);
			System.out.println("####" + res_obj);
			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			bt_send.setEnabled(true);
			super.onPostExecute(result);
		}

	}

	public void onEvent(ChatMessageEB cmeb) {
		if (!cmeb.getChatmessage().getSender_id().equals(userInfo.getUserid()))
			return;
		messages.add(cmeb.getChatmessage());
		new RefreshListAT().execute(NULL);
		if (myApplication.sender_id.equals(userInfo.getUserid())) {
			dbh.setAMsgReaded(getLoginUser().getUserid(), userInfo.getUserid());
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		myApplication.sender_id = userInfo.getUserid();
		System.out.println("@@@ present chatting persion id ： ["
				+ myApplication.sender_id + "]");
		super.onResume();

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		myApplication.sender_id = "";
		System.out.println("@@@ reset present chatting persion id "
				+ myApplication.sender_id);
		super.onStop();

	}

	@Override
	protected void onNewIntent(Intent intent) {
		System.out.println("onNewIntent A ChatActivity");
		userInfo = new UserInfo();
		userInfo = (UserInfo) intent.getSerializableExtra("userInfo");
		bt_send = (Button) findViewById(R.id.bt_send);
		tv_title = (TextView) findViewById(R.id.tv_title);
		chat_message_listview = (ListView) findViewById(R.id.chat_message_listview);
		chat_msg_input_et_single = (EditText) findViewById(R.id.chat_msg_input_et_single);
		ui = new UserInfo();
		cm = new ChatMessage();
		img_back = (ImageView) findViewById(R.id.img_back);
		img_info = (ImageView) findViewById(R.id.img_info);
		img_back.setOnClickListener(this);
		img_info.setOnClickListener(this);
		bt_send.setOnClickListener(this);
		messages = new ArrayList<ChatMessage>();
		// Intent intent = this.getIntent();
		if (userInfo == null) {
			System.out.println("intent null");
			finish();
		} else {
			tv_title.setText(userInfo.getUsername());
			// System.out.println("intent null");
			// initTestData();
			initMsgData();
		}
		super.onNewIntent(intent);
	}

	private void goToMain() {
		Intent intent = new Intent();
		intent.setClass(this, AMainTabHostActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			goToMain();
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}
	
	public static String bitmapToBase64(Bitmap bitmap) {  
		  
	    String result = null;  
	    ByteArrayOutputStream baos = null;  
	    try {  
	        if (bitmap != null) {  
	            baos = new ByteArrayOutputStream();  
	            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
	  
	            baos.flush();  
	            baos.close();  
	  
	            byte[] bitmapBytes = baos.toByteArray();  
	            result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);  
	        }  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } finally {  
	        try {  
	            if (baos != null) {  
	                baos.flush();  
	                baos.close();  
	            }  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	    }  
	    return result;  
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_PHOTO) {
			// imageView不设null, 第一次上传成功后，第二次在选择上传的时候会报错。
			// bt_img.setImageBitmap(null);
			// // picPath =
			// data.getStringExtra(SelectPicActivity.KEY_PHOTO_PATH);
			// // Log.i(TAG, "最终选择的图片=" + picPath);
			// // txt.setText("文件路径" + picPath);
			// // String[] str = picPath.split("/");
			// // String strr = str[str.length - 1];
			// // title_edit.setText(picPath);
			// // picName = strr;
			// Options ops = new Options();
			// // ops.in
			// ops.inPreferredConfig = Bitmap.Config.RGB_565;
			//
			// ops.inPurgeable = true;
			//
			// ops.inInputShareable = true;
			// // Bitmap bm = BitmapFactory.decodeFile(picPath, ops);
			//
			// // bt_img.setImageBitmap(bm);// 将图片显示在ImageView里

//			bt_img.setImageBitmap(null);
			String picPath = data.getStringExtra(SelectPicActivity.KEY_PHOTO_PATH);
			// txt.setText("文件路径" + picPath);
			Log.d("Pic path = ", picPath);
		
			Options ops = new Options();
			// ops.in
			ops.inPreferredConfig = Bitmap.Config.RGB_565;

			ops.inPurgeable = true;

			ops.inInputShareable = true;
			Bitmap bm = BitmapFactory.decodeFile(picPath, ops);
			sendImgMsg(bm);
//			 bt_img.setImageBitmap(bm);// 将图片显示在ImageView里
		}
		super.onActivityResult(requestCode, resultCode, data);

	}
}
