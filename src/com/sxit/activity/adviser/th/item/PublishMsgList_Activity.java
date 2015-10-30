package com.sxit.activity.adviser.th.item;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.sxit.activity.adviser.publishmsg.PublishMsgInfo_Acitivty;
import com.sxit.activity.adviser.publishmsg.PublishMsg_Acitivty;
import com.sxit.activity.adviser.th.item.adapter.PublishMsg_Adapter;
import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.chatmsg.Chat_Activity;
import com.sxit.activity.news.NewsList_Activity;
import com.sxit.activity.news.News_Activity;
import com.sxit.activity.news.adapter.News_Adapter;
import com.sxit.activity.th.item.Information_Activity;
import com.sxit.activity.th.item.adapter.Analyst_Adapter;
import com.sxit.activity.th.item.adapter.AskQuestion_Adapter;
import com.sxit.activity.th.item.adapter.Information_Adapter;
import com.sxit.activity.th.item.bean.Analyst_ListBean;
import com.sxit.activity.th.item.bean.AskQuestion_ListBean;
import com.sxit.activity.th.item.bean.Information_ListBean;
import com.sxit.entity.PublishMessage;
import com.sxit.entity.news.FinNews;
import com.sxit.http.SoapRes;
import com.sxit.utils.SOAP_UTILS;

import lnpdit.lntv.tradingtime.R;

/**
 * 发布消息
 * 
 * @author huanyu 类名称：PublishMsgList_Activity 创建时间:2014-11-24 下午2:03:41
 */
public class PublishMsgList_Activity extends BaseActivity {
	private PullToRefreshListView listView_analyst;
	private ListView listView;
	private Button bt_publish_msg;
	public PublishMsg_Adapter adapter;
	public List<PublishMessage> list;
	private int pageIndex = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_publishmsg_list_layout);
		super.onCreate(savedInstanceState);
		initView();
		setLieteners();
		String[] property_va = new String[] { getLoginUser().getUserid(), "10", pageIndex + "" };
		soapService.getReportList(property_va, false);
	}

	private void initView() {
		listView_analyst = (PullToRefreshListView) findViewById(R.id.listView_analyst);
		bt_publish_msg = (Button) findViewById(R.id.bt_publish_msg);
		listView = listView_analyst.getRefreshableView();
		bt_publish_msg.setOnClickListener(this);
	}

	private void setLieteners() {
		listView_analyst.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				 Bundle bundle = new Bundle();
				 bundle.putSerializable("publishMsg", list.get(position - 1));
				 intent.putExtras(bundle);
				 intent.setClass(PublishMsgList_Activity.this, PublishMsgInfo_Acitivty.class);
				 startActivity(intent);
			}
		});
		listView_analyst.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				new GetDataTask().execute();
			}
		});
		// end of list
		listView_analyst.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				String[] property_va = new String[] { getLoginUser().getUserid(), "10", ++pageIndex + "" };
				soapService.getReportList(property_va, true);
			}
		});
	}

	/**
	 * 列表刷新
	 * 
	 * @author why
	 * 
	 */
	private class GetDataTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
			}
			return "";
		}

		@Override
		protected void onPostExecute(String result) {
			pageIndex = 1;
			String[] property_va = new String[] { getLoginUser().getUserid(), "10", pageIndex + "" };
			soapService.getReportList(property_va, false);
			super.onPostExecute(result);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_publish_msg:
			Intent intent = new Intent();
			intent.setClass(PublishMsgList_Activity.this, PublishMsg_Acitivty.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	public void onEvent(SoapRes obj) {
		super.onEvent(obj);
		if (obj.getCode().equals(SOAP_UTILS.METHOD.GETREPORTLIST)) {
			if (obj.isPage()) {
				for (PublishMessage bean : (List<PublishMessage>) obj.getObj()) {
					list.add(bean);
				}
				adapter.notifyDataSetChanged();
			} else {
				list = (List<PublishMessage>) obj.getObj();
				if (list != null) {
					if (list.size() != 0) {
						adapter = new PublishMsg_Adapter(this, list);
						listView.setAdapter(adapter);
					}
				}
			}
			listView_analyst.onRefreshComplete();
		}
	}

	@Override
	public void onEventMainThread(String method) {
		if (method.equals(SOAP_UTILS.METHOD.GETREPORTLIST)) {
			String[] property_va = new String[] { getLoginUser().getUserid(), "10", pageIndex + "" };
			soapService.getReportList(property_va, false);
		}
	}

	/**
	 * 发布信息成功刷新
	 * 
	 * @param str class
	 */
	public void onEvent(String str) {
		if (str.equals(PublishMsg_Acitivty.class.getName())) {
			pageIndex = 1;
			String[] property_va = new String[] { getLoginUser().getUserid(), "10", pageIndex + "" };
			soapService.getReportList(property_va, false);
		}
	}
}
