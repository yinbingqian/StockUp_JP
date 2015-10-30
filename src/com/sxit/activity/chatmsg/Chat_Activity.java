package com.sxit.activity.chatmsg;

import java.util.ArrayList;
import java.util.List;

import lnpdit.lntv.tradingtime.R;

import org.ksoap2.serialization.SoapObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.chatmsg.adapter.ChattingAdapter;
import com.sxit.activity.th.MainTabHostActivity;
import com.sxit.db.DBHelper;
import com.sxit.entity.Adviser;
import com.sxit.entity.ChatMessage;
import com.sxit.entity.LoginUser;
import com.sxit.entity.UserInfo;
import com.sxit.entity.eb.ChatMessageEB;
import com.sxit.http.SoapWebService;
import com.sxit.utils.EventCache;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.Utils;

// 1 是我发的 0 是接收的
public class Chat_Activity extends BaseActivity {
	private Button bt_send;
	private ListView chat_message_listview;
	private TextView tv_title, heartcount, userheart;
	private ChattingAdapter ca;
	private List<ChatMessage> messages;
	private EditText chat_msg_input_et_single;
	private ImageView img_back, img_info;
	private Object NULL = null;
	private UserInfo ui;
	private ChatMessage cm;
	private Adviser analystInfo;
	private DBHelper dbh;
	private SharedPreferences sp;
	private String userheartStr = "";

	String Mark;
	String Paidmark;
	String Heartcount;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		System.out.println(">>>>> Chat_Activity onCreate");
		this.setContentView(R.layout.activity_chat_layout);
		EventCache.msgNotiEvent.register(this);
		this.isParentActivity = false;
		dbh = new DBHelper(this);
		intent = getIntent();
		analystInfo = (Adviser) intent.getSerializableExtra("analystInfo");
		if (analystInfo.getHeadpic() == null || analystInfo.getHeadpic().equals("")) {
			if (analystInfo.getAdv_user_id().equals("1")) {
				analystInfo = dbh.queryAdviserGz();
			} else {
				analystInfo = dbh.queryAdviserInfoById(analystInfo.getAdv_user_id());
			}
		}
		System.out.println("@@@ id : " + analystInfo.getAdv_user_id());
		System.out.println("@@@" + analystInfo.getHeadpic());
		System.out.println("@@@ mark" + analystInfo.getMark());
		System.out.println("@@@ paid mark" + analystInfo.getPaidmark());
		System.out.println("@@@" + analystInfo.getRewardmark());
		System.out.println("@@@ getHeartcount" + analystInfo.getHeartcount());
		sp = this.getSharedPreferences("loginInfo", Context.MODE_WORLD_READABLE);
		bt_send = (Button) findViewById(R.id.bt_send);
		
		if(analystInfo.getGroupid().equals("2")){
//			if(analystInfo.getAdv_user_id().equals("1")){
			LinearLayout edit_layout = (LinearLayout) this.findViewById(R.id.chat_fragment);
			LinearLayout toplayout = (LinearLayout) this.findViewById(R.id.toplayout);
			edit_layout.setVisibility(8);
			toplayout.setVisibility(8);
//			chat_msg_input_et_single.setVisibility(8);
//			bt_send.setVisibility(8);
		}
		
		tv_title = (TextView) findViewById(R.id.tv_title);
		heartcount = (TextView) findViewById(R.id.heartcount);
		userheart = (TextView) findViewById(R.id.userheart);
		heartcount.setText(analystInfo.getHeartcount());
		userheartStr = getLoginUser().getMark();
		userheart.setText(getLoginUser().getMark());
		chat_message_listview = (ListView) findViewById(R.id.chat_message_listview);
		// getLoginUser
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
		if (analystInfo == null) {
			finish();
			System.out.println("intent null");
		} else {
			tv_title.setText(analystInfo.getRealname());

			// initTestData();
			initMsgData();
		}
		chat_message_listview.setOnItemClickListener(oic);

	}

	private OnItemClickListener oic = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			switch (messages.get(arg2).getMsg_type()) {
			case 0:
				
				break;
			case 1:

				break;
			case 2:

				break;

			default:
				break;
			}
			;

		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_send:
			//
			String heartString = "0";
			if (analystInfo.getHeartcount()==null) {
				heartString = "0";
			}else {
				heartString = analystInfo.getHeartcount();
			}
			if (Integer.parseInt(getLoginUser().getMark()) - Integer.parseInt(heartString) < 0) {
//				if (Integer.parseInt(getLoginUser().getMark()) - Integer.parseInt(analystInfo.getHeartcount()) < 0) {
				Utils.showRechargeDialog(Chat_Activity.this);
			} else {
				// Utils.showRechargeDialog(Chat_Activity.this);
				sendTextMsg(chat_msg_input_et_single.getText().toString().trim());
			}

			break;
		case R.id.img_back:
			goToMain();
			break;
		case R.id.img_info:

			break;

		default:
			break;
		}
		super.onClick(v);
	}

	// private void initTestData() {
	// ui.setUserheader(SOAP_UTILS.HEADER_URL + analystInfo.getHeadpic());
	// ui.setUserid("122001");
	// ui.setUsername("王大壮");
	// cm.setContent3_title("广日股份2014年前三季净增25% 新项目逐步发力");
	// cm.setContent3_img("http://img1.money.126.net/chart/hs/kline/day/90/0600894.png");
	// cm.setContent3_content("10月28日晚间，广日股份发布2014年三季度业绩报告。报告显示，截止2014年9月30日，公司今年以来实现营业收入33.66亿元，与去年同期相比增长15.07%；归属于上市公司股东的净利润5.05亿元，同比增长25.02%；每股收益为0.62元。");
	// cm.setContent3_url("http://money.163.com/14/1028/22/A9M6SIHB00253B0H.html");
	// // cm.setContent3_url("http://m.money.163.com/news/14/1028/22/A9M6SIHB00253B0H.html#1a01");
	// cm.setMsg_direction(0);
	// cm.setMsg_send_date("2014-10-28");
	// cm.setMsg_type(2);
	// cm.setLogin_id("122001");
	// cm.setStatus(1);
	// cm.setSender_id("134221");
	// cm.setUserinfo(ui);
	// messages.add(cm);
	// }

	public void initMsgData() {
		dbh.setMsgReaded(dbh.queryLoginUserInfo().getUserid(), analystInfo.getAdv_user_id());
		messages = dbh.queryMsgData(analystInfo, getLoginUser());
		ca = new ChattingAdapter(Chat_Activity.this, messages);
		chat_message_listview.setAdapter(ca);
	}

	private void sendTextMsg(String content) {

		if (content == null || content.equals("")) {
			Toast.makeText(this, "不能发送空信息", Toast.LENGTH_SHORT).show();
		} else {
			// Toast.makeText(this, "信息发送成功，请耐心等待回复", Toast.LENGTH_SHORT).show();
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
			cm.setSender_id(analystInfo.getAdv_user_id());
			cm.setUserinfo(ui);
			messages.add(cm);
			if (null == ca || ca.getCount() == 0) {
				ca = new ChattingAdapter(Chat_Activity.this, messages);
				chat_message_listview.setAdapter(ca);
			} else {
				ca.notifyDataSetChanged();
			}
			dbh.insMsgData(cm);
			// new SendAT().execute(NULL);
			String[] property_nm = { "fromUser", "toUser", "blogTitle", "blogAbstract", "content", "devType", "infoType", "infoDirection" };
			Object[] property_va = { analystInfo.getAdv_user_id(), getLoginUser().getUserid(), content, content, content, 1, 1, 1 };
			new SendAT().execute(property_nm, property_va);
		}
		chat_msg_input_et_single.setText("");
		InputMethodManager imm = (InputMethodManager) getSystemService(Chat_Activity.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(chat_msg_input_et_single.getWindowToken(), 0);
		new RefreshListAT().execute(NULL);
	}

	private void FreshList() {
//		ca.notifyDataSetChanged();
//		chat_message_listview.setSelection(chat_message_listview.getCount() - 1);
		initMsgData();
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

	class SendAT extends AsyncTask<Object, Object, Object> {
		@Override
		protected void onPreExecute() {
			bt_send.setEnabled(false);
			super.onPreExecute();
		}

		@Override
		protected Object doInBackground(Object... params) {
			System.out.println(">>>>>");

			Object res_obj = SoapWebService.data(SOAP_UTILS.METHOD.INTERACTIONSUBMIT, (String[]) params[0], (Object[]) params[1]);
			SoapObject so = (SoapObject) res_obj;
			String soapchilds = so.getProperty("InteractionSubmitResult").toString();
			if (soapchilds.equals("true")) {
				payForChat();
			} else {

			}
			System.out.println("####" + res_obj);
			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			bt_send.setEnabled(true);
			String userhearttempStr =	""+(Integer.parseInt(userheart.getText().toString().trim())-Integer.parseInt(analystInfo.getHeartcount().toString().trim()));
			userheart.setText(userhearttempStr);
			
			super.onPostExecute(result);
		}
	}

	private void payForChat() {
		LoginUser lu = getLoginUser();
		lu.setMark((Integer.parseInt(getLoginUser().getMark()) - Integer.parseInt(analystInfo.getHeartcount().toString().trim())) + "");
//		lu.setMark((Integer.parseInt(getLoginUser().getMark()) - 10) + "");
		setLoginUser(lu);
	}

	public void onEvent(ChatMessageEB cmeb) {
		if (!cmeb.getChatmessage().getSender_id().equals(analystInfo.getAdv_user_id()))
			return;
		// ChatMessage cm = cmeb.getChatmessage();
		// UserInfo ui = cm.getUserinfo();
		// ui.setUserheader(SOAP_UTILS.HEADER_URL + ui.getUserheader());
		// cm.setUserinfo(ui);
		// ChatMessage
		ChatMessage cmevnet = new ChatMessage();
		cmevnet = cmeb.getChatmessage();
		// cmevnet.setContent1(content1)
		ChatMessage cmevented = new ChatMessage();
		if (cmevnet.getMsg_type() == 2) {
			// TODO 研报
			cmevented.setContent3_content(cmevnet.getContent3_content());
			cmevented.setContent3_img(cmevnet.getContent3_img());
			cmevented.setContent3_title(cmevnet.getContent3_title());
			cmevented.setContent3_url("http://m.money.163.com/news/14/1028/22/A9M6SIHB00253B0H.html#1a01");
			cmevented.setMsg_type(2);
		} else if (cmevnet.getMsg_type() == 1) {
			// TODO 语音
			// cm.setContent2(content2);
			cmevented.setMsg_type(1);
		} else if (cmevnet.getMsg_type() == 0) {
			// TODO 文本
			cmevented.setContent1(cmevnet.getContent1());
			cmevented.setMsg_type(0);
		} else {
			cm.setContent1("异常消息");
		}
		cmevented.setLogin_id(dbh.queryLoginUserInfo().getUserid());
		cmevented.setMsg_direction(0);
		cmevented.setMsg_send_date(cmevnet.getMsg_send_date());
		cmevented.setSender_id(cmevnet.getSender_id());
		UserInfo ui = new UserInfo();
		ui.setUserheader(analystInfo.getHeadpic());
		ui.setUserid(analystInfo.getAdv_user_id());
		ui.setUsername(analystInfo.getRealname());
		cmevented.setUserinfo(ui);
		messages.add(cmevented);
		new RefreshListAT().execute(NULL);
		if (myApplication.sender_id.equals(analystInfo.getAdv_user_id())) {
			dbh.setMsgReaded(getLoginUser().getUserid(), analystInfo.getAdv_user_id());
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
			heartcount.setText(Heartcount);
			analystInfo.setHeartcount(Heartcount);
			
			super.onPostExecute(result);
		}
	}
	@Override 
	protected void onResume() {
		// TODO Auto-generated method stub
		myApplication.sender_id = analystInfo.getAdv_user_id();
		System.out.println("@@@ present chatting persion id ： " + myApplication.sender_id);
		String[] property_nm = { "analystid" };
//		Object[] property_va = { getLoginUser().getUserid() };
		Object[] property_va = { analystInfo.getAdv_user_id() };
		new GetAnalystHeart().execute(property_nm, property_va);
		super.onResume();

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		myApplication.sender_id = "";
		System.out.println("@@@ reset present chatting persion id " + myApplication.sender_id);
		super.onStop();

	}

	@Override
	protected void onNewIntent(Intent intent) {
		System.out.println("onNewIntent C ChatActivity");
		this.isParentActivity = false;
		dbh = new DBHelper(this);
		analystInfo = (Adviser) intent.getSerializableExtra("analystInfo");
		if (analystInfo != null) {
			// if (analystInfo.getAdv_user_id().equals("1")) {
			// analystInfo = dbh.queryAdviserGz();
			// }else{
			// analystInfo = dbh.queryAdviserInfoById(analystInfo.getAdv_user_id());
			// }
			if (analystInfo.getHeadpic() == null || analystInfo.getHeadpic().equals("")) {
				if (analystInfo.getAdv_user_id().equals("1")) {
					analystInfo = dbh.queryAdviserGz();
				} else {
					analystInfo = dbh.queryAdviserInfoById(analystInfo.getAdv_user_id());
				}
			}
		} else {
			finish();
		}

		bt_send = (Button) findViewById(R.id.bt_send);
		tv_title = (TextView) findViewById(R.id.tv_title);
		heartcount = (TextView) findViewById(R.id.heartcount);
		userheart = (TextView) findViewById(R.id.userheart);
		heartcount.setText(analystInfo.getHeartcount());
		// Toast.makeText(Chat_Activity.this, "" + analystInfo.getHeartcount(), Toast.LENGTH_SHORT).show();
		System.out.println("onNewIntent C ChatActivity AAA");
		userheartStr = getLoginUser().getMark();
		userheart.setText(getLoginUser().getMark());
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
		if (analystInfo == null) {
			finish();
		} else {
			tv_title.setText(analystInfo.getRealname());
			System.out.println("intent null");
			// initTestData();
			initMsgData();
		}
		super.onNewIntent(intent);
	}

	private void goToMain() {
		Intent intent = new Intent();
		intent.setClass(this, MainTabHostActivity.class);
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

}
