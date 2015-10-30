package com.sxit.activity.analystteaminfo;

import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.chatmsg.Chat_Activity;
import com.sxit.activity.news.NewsList_Activity;
import com.sxit.activity.news.News_Activity;
import com.sxit.entity.Adviser;
import com.sxit.entity.AnalystVideo;
import com.sxit.http.RdaResultPack;
import com.sxit.http.SoapRes;
import com.sxit.instance.Instance;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.Utils;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.AdapterView.OnItemClickListener;
import lnpdit.lntv.tradingtime.R;

/**
 * 顾问团队信息 Acy
 * 
 * @author huanyu 类名称：AnalystTeamInfo_Activity 创建时间:2014-10-28 下午7:38:35
 */
public class AnalystTeamInfo_Activity extends BaseActivity implements OnClickListener {
	private TextView tv_title;// title
	private ImageView img_back;// back
	private Adviser analystInfo;
	private TextView tv_analystName;
	private TextView tv_analystSpecialty;
	private TextView tv_analystResume;
	private TextView tv_analystOrg_and_ptitle;
	private TextView tv_mark;
	private Button bt_ask;
	private ImageView img_analystImg;
	private ToggleButton tb_followed;
	private AnalystTeamInfo_Adapter adapter;

	View headerView = null;
	private List<AnalystVideo> list; // 分析师视频列表
	private PullToRefreshListView listView_analystvideolist;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_analystteaminfo);
		intent = getIntent();
		analystInfo = (Adviser) intent.getSerializableExtra("analystInfo");
		initView();
		setListeners();
		String[] property_va = new String[] { analystInfo.getAdv_user_id() };
		soapService.getAnalystVideo(property_va);
	}

	private void initView() {
		headerView = getLayoutInflater().inflate(R.layout.activity_analystvideoheader, null);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_analystOrg_and_ptitle = (TextView) headerView.findViewById(R.id.tv_analystOrg_and_ptitle);
		tv_analystResume = (TextView) headerView.findViewById(R.id.tv_analystResume);
		tv_analystSpecialty = (TextView) headerView.findViewById(R.id.tv_analystSpecialty);
		tv_mark = (TextView) headerView.findViewById(R.id.tv_mark);
		tv_title.setText(analystInfo.getRealname());
		tv_mark.setText("温馨提示：向该顾问请教需要消耗" + analystInfo.getHeartcount() + "红心");
		img_back = (ImageView) findViewById(R.id.img_back);
		img_analystImg = (ImageView) headerView.findViewById(R.id.img_analystImg);
		tv_analystName = (TextView) headerView.findViewById(R.id.tv_analystName);
		tv_analystName.setText(analystInfo.getRealname());
		bt_ask = (Button) headerView.findViewById(R.id.bt_ask);
		tv_analystOrg_and_ptitle.setText(analystInfo.getOrgname() + "  " + analystInfo.getPtitle());
		tv_analystSpecialty.setText("「" + analystInfo.getSpecialty() + "」");
		Instance.imageLoader.displayImage(SOAP_UTILS.HEADER_URL + analystInfo.getHeadpic(), img_analystImg,
				Instance.user_options);
		tv_analystResume.setText(analystInfo.getResume());
		tb_followed = (ToggleButton) headerView.findViewById(R.id.tb_followed);
		listView_analystvideolist = (PullToRefreshListView) findViewById(R.id.listView_analystvideolist);
		listView = listView_analystvideolist.getRefreshableView();
		listView.addHeaderView(headerView);
	}

	private void setListeners() {
		img_back.setOnClickListener(this);
		
		bt_ask.setOnClickListener(this);
		
		tb_followed.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					Utils.showTextToast(AnalystTeamInfo_Activity.this, "已关注");
				} else {
					Utils.showTextToast(AnalystTeamInfo_Activity.this, "已取消关注");
				}
			}
		});
		listView_analystvideolist.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				new GetDataTask().execute();
			}
		});
		// end of list
		listView_analystvideolist.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {

//				String[] property_va = new String[] { analystInfo.getAdv_user_id() };
//				soapService.getAnalystVideo(property_va);

			}
		});
		listView_analystvideolist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				intent.putExtra("Id", list.get(position-2).getId());
				intent.putExtra("Title", list.get(position-2).getTitle());
				intent.setClass(AnalystTeamInfo_Activity.this, AnalystVideoInfo.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.bt_ask:
			if(analystInfo.getGroupid().equals("2")){
				Toast.makeText(this, "该分析师未签约", Toast.LENGTH_SHORT).show();
				
			}else{
				
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putSerializable("analystInfo", analystInfo);
			intent.putExtras(bundle);
			intent.setClass(AnalystTeamInfo_Activity.this, Chat_Activity.class);
			startActivity(intent);
			}
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

			String[] property_va = new String[] { analystInfo.getAdv_user_id() };
			soapService.getAnalystVideo(property_va);

			super.onPostExecute(result);
		}
	}

	/**
	 * http回调SoapObject
	 * 
	 * @param obj
	 */
	public void onEvent(Object obj) {
		SoapRes res = (SoapRes) obj;
		// webservice result
		if (res.getCode().equals(SOAP_UTILS.METHOD.GETANALYSTVIDEO)) {
			list = (List<AnalystVideo>) res.getObj();
			try {
				if (list != null) {
					// if (list.size() != 0) {
					adapter = new AnalystTeamInfo_Adapter(this, list);
					listView.setAdapter(adapter);
					// }
				}
				listView_analystvideolist.onRefreshComplete();
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
	}

	protected void onEventMainThread(RdaResultPack http) {
		// TODO Auto-generated method stub
		if (http.equals(SOAP_UTILS.METHOD.GETANALYSTVIDEO)) {

			String[] property_va = new String[] { analystInfo.getAdv_user_id() };
			soapService.getAnalystVideo(property_va);

		}
	}
}
