package com.sxit.activity.adviser.th.item;

import java.util.ArrayList;
import java.util.List;

import lnpdit.lntv.tradingtime.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sxit.activity.adviser.chatmsg.Chat_Activity;
import com.sxit.activity.adviser.updateheartcount.UpdateHeartCount_Activity;
import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.news.News_Activity;
import com.sxit.activity.th.item.adapter.Information_Adapter;
import com.sxit.activity.th.item.bean.Information_ListBean;
import com.sxit.db.DBHelper;
import com.sxit.entity.eb.ChatMessageEB;
import com.sxit.utils.EventCache;

public class InteractionMsg_Activity extends BaseActivity {
	private PullToRefreshListView listView_info;
	private ListView listView;
	private Information_Adapter information_Adapter;
	private List<Information_ListBean> list;
	private DBHelper dbh;
//	private TextView tv_setting;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_interactionmsg);
		EventCache.msgNotiEvent.register(this);
		list = new ArrayList<Information_ListBean>();
		dbh = new DBHelper(this);
		initView();
	}

	public void initView() {
		listView_info = (PullToRefreshListView) findViewById(R.id.listView_info);
//		tv_setting = (TextView) findViewById(R.id.tv_setting);
		listView = listView_info.getRefreshableView();
		listView.setOnItemClickListener(oc);
		intiDate();
	}

	private void intiDate() {
		System.out.println("$$$  InteractionMsg_Activity initData");
//		tv_setting.setText("设置红心");
		list = dbh.getRecentAMsgList(dbh.queryLoginUserInfo());
		information_Adapter = new Information_Adapter(this, list);
		listView.setAdapter(information_Adapter);
//		tv_setting.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent();
//				intent.setClass(InteractionMsg_Activity.this, UpdateHeartCount_Activity.class);
//				InteractionMsg_Activity.this.startActivity(intent);
//			}
//		});
	}

	private OnItemClickListener oc = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			// System.out.println(">>>>"+ list.get(position-1).getTitle()+">>"+position);
			Intent i = new Intent();
			switch (list.get(position - 1).getNotifType()) {
			case 0:
//				System.out.println("@@@ normal chat");
				i.setClass(InteractionMsg_Activity.this, Chat_Activity.class);
				startActivity(i);
				break;
			case 1:
//				System.out.println("@@@ normal news");
				i.setClass(InteractionMsg_Activity.this, News_Activity.class);
				startActivity(i);
				break;
			case 2:
//				System.out.println("@@@ normal kaka");
				Bundle bundle = new Bundle();
				bundle.putSerializable("userInfo", list.get(position - 1).getUserinfo());
				i.putExtras(bundle);
				i.setClass(InteractionMsg_Activity.this, com.sxit.activity.adviser.chatmsg.Chat_Activity.class);
				startActivity(i);
				break;

			default:
				break;
			}
			// if (list.get(position - 1).getNotifType() == 1) {
			// intent.setClass(Information_Activity.this, News_Activity.class);
			// startActivity(intent);
			// } else {
			// intent.setClass(Information_Activity.this, Chat_Activity.class);
			// startActivity(intent);
			// }
		}
	};

	protected void onResume() {
		super.onResume();
		intiDate();
	};

	public void onEventMainThread(ChatMessageEB cmeb) {
		intiDate();
	}

}
