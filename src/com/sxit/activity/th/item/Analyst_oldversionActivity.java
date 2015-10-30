package com.sxit.activity.th.item;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.sxit.activity.analystteaminfo.AnalystTeamInfo_Activity;
import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.th.item.adapter.Analyst_Adapter;
import com.sxit.activity.th.item.bean.Analyst_ListBean;
import com.sxit.db.DBHelper;
import com.sxit.entity.Adviser;
import com.sxit.http.SoapWebService;
import com.sxit.utils.SOAP_UTILS;

import lnpdit.lntv.tradingtime.R;

/**
 * 顾问团 acy
 * 
 * @author huanyu 类名称：Analyst_Activity 创建时间:2014-10-26 下午8:13:07
 */
public class Analyst_oldversionActivity extends BaseActivity {
	private TextView tv_title;// title
	/** listView **/
	private PullToRefreshListView listView_analyst;
	private ListView listView;
	public Analyst_Adapter adapter;
	public List<Adviser> list;
	private DBHelper dbh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_analyst);
		initDB();
		initView();
		setListeners();
	}

	private void initDB() {
		dbh = new DBHelper(this);
	}

	private void initView() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText(getString(R.string.analyst_title));
		listView_analyst = (PullToRefreshListView) findViewById(R.id.listView_analyst);
		listView = listView_analyst.getRefreshableView();
		String[] property_nm = { "pagesize", "pageindex" };
		Object[] property_va = { 100, 1 };
		new AdvisterRefurbishAT().execute(property_nm, property_va);
		// test();
	}

	// private void test() {
	// list = new ArrayList<Adviser>();
	// for (int i = 0; i < 20; i++) {
	// Adviser bean = new Adviser();
	// if (i % 2 == 0) {
	// bean.setImgUrl(R.drawable.item_logo4);
	// bean.setName("王周俊");
	// bean.setAnalystTeam("东北证券	首席顾问");
	// } else {
	// bean.setImgUrl(R.drawable.item_logo3);
	// bean.setName("李明哲");
	// bean.setAnalystTeam("联讯证券	首席顾问");
	// }
	// list.add(bean);
	// }
	// adapter = new Analyst_Adapter(this, list);
	// listView.setAdapter(adapter);
	// }

	private void setListeners() {
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Bundle bundle = new Bundle();
				bundle.putSerializable("analystInfo", list.get(position - 1));
				intent.putExtras(bundle);
				intent.setClass(Analyst_oldversionActivity.this, AnalystTeamInfo_Activity.class);
				startActivity(intent);
			}
		});
		listView_analyst.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				new GetDataTask().execute();
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
			String[] property_nm = { "pagesize", "pageindex" };
			Object[] property_va = { 100, 1 };
			new AdvisterRefurbishAT().execute(property_nm, property_va);
			super.onPostExecute(result);
		}
	}

	@Override
	public void onClick(View v) {

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

	private List<Adviser> data;

	class AdvisterRefurbishAT extends AsyncTask<Object, Object, Object> {
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
						adviser = new Adviser();
						SoapObject soa = (SoapObject) soapchilds.getProperty(i);
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
						
						System.out.println("!!!!@@@"+adviser.getHeartcount());
						data.add(adviser);
					}
				}
				System.out.println("-------------" + soapchildss);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			dbh.clearAllAdviser();
			if (data != null) {
				dbh.insAdviserInfo(data);
//				Toast.makeText(Analyst_Activity.this, "成功读取" + data.size() + "条数据", Toast.LENGTH_SHORT).show();
				list = dbh.queryAdviserInfo();
//				adapter = new Analyst_Adapter(Analyst_oldversionActivity.this, list);
//				listView.setAdapter(adapter);
				listView_analyst.onRefreshComplete();
			} else {
				Toast.makeText(Analyst_oldversionActivity.this, "无数据", Toast.LENGTH_SHORT).show();
			}
			// intent.setClass(Welcome_Activity.this,
			// MainTabHostActivity.class);
			// intent.putExtra("locationCur", 1);
			// startActivity(intent);
			// finish();
			super.onPostExecute(result);
		}

	}
}
