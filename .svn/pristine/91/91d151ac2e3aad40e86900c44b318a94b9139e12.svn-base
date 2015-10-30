package com.sxit.activity.news;

import java.util.List;

import lnpdit.lntv.tradingtime.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.news.adapter.NewsList_Adapter;
import com.sxit.activity.news.adapter.News_Adapter;
import com.sxit.entity.news.FinNews;
import com.sxit.entity.news.FinNewsList;
import com.sxit.http.SoapRes;
import com.sxit.utils.SOAP_UTILS;

/**
 * 新闻详情
 * 
 * @author huanyu 类名称：NewsInfo_Activity 创建时间:2014-10-29 下午3:18:47
 */
public class NewsList_Activity extends BaseActivity {
	private ImageView img_back;
	private TextView tv_title;
	private FinNews finNews;
	private PullToRefreshListView listView_newslist;
	private NewsList_Adapter adapter;
	private ListView listView;
	private List<FinNewsList> list;
	private int pageIndex = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_newslist);
		super.onCreate(savedInstanceState);
		this.isParentActivity = false;
		intent = getIntent();
		finNews = (FinNews) intent.getSerializableExtra("finnews");
		initView();
		setListeners();
		String[] property_va = new String[] { finNews.getId(), "10", pageIndex + "" };
		soapService.getNewsTitle(property_va, false);
	}

	private void initView() {
		img_back = (ImageView) findViewById(R.id.img_back);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText(finNews.getTitle());
		listView_newslist = (PullToRefreshListView) findViewById(R.id.listView_newslist);
		listView = listView_newslist.getRefreshableView();
	}

	private void setListeners() {
		img_back.setOnClickListener(this);
		listView_newslist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Bundle bundle = new Bundle();
				bundle.putSerializable("finnewslist", list.get(position - 1));
				intent.putExtras(bundle);
				intent.setClass(NewsList_Activity.this, NewsInfo_Activity.class);
				startActivity(intent);
			}
		});
		listView_newslist.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				new GetDataTask().execute();
			}
		});
		// end of list
		listView_newslist.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				String[] property_va = new String[] { finNews.getId(), "10", ++pageIndex + "" };
				soapService.getNewsTitle(property_va, true);

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
			String[] property_va = new String[] { finNews.getId(), "10", pageIndex + "" };
			soapService.getNewsTitle(property_va, false);
			super.onPostExecute(result);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
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

	public void onEvent(SoapRes obj) {
		super.onEvent(obj);
		if (obj.getCode().equals(SOAP_UTILS.METHOD.GETNEWSTITLE)) {
			if (obj.isPage()) {
				for (FinNewsList bean : (List<FinNewsList>) obj.getObj()) {
					list.add(bean);
				}
				adapter.notifyDataSetChanged();
			} else {
				list = (List<FinNewsList>) obj.getObj();
				if (list != null) {
					if (list.size() != 0) {
						adapter = new NewsList_Adapter(this, list);
						listView.setAdapter(adapter);
					}
				}
			}
			listView_newslist.onRefreshComplete();
		}
	}

	@Override
	public void onEventMainThread(String method) {
		if (method.equals(SOAP_UTILS.METHOD.GETNEWSTITLE)) {
			String[] property_va = new String[] { finNews.getId(), "10", pageIndex + "" };
			soapService.getNewsTitle(property_va, false);
		}
	}
}
