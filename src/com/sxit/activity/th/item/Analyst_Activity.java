package com.sxit.activity.th.item;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import com.sxit.activity.analystteaminfo.AnalystTeamInfo_Activity;
import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.th.item.adapter.Analyst_Adapter;
import com.sxit.entity.Adviser;
import com.sxit.http.SoapWebService;
import com.sxit.utils.SOAP_UTILS;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import lnpdit.lntv.tradingtime.R;

/**
 * 顾问团 acy
 * 
 * @author huanyu 类名称：Analyst_Activity 创建时间:2014-10-26 下午8:13:07
 */
public class Analyst_Activity extends BaseActivity {
	private Analyst_Adapter adpter; // 绑定数据的adpter
	private ExpandableListView listView; // 控件
	private ArrayList<String> fatherList; // 放置父类数据
	private List<List<Adviser>> childList; // 子类数据
	private List<Adviser> list; // 中间数据保存量

	String res_str = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_analyst);
		listView = (ExpandableListView) findViewById(R.id.all); // 获取控件对象
		initDate(); // 初始化数据
	}

	private void setUI() {
		try {
			JSONArray ana_mode_array = new JSONArray(res_str);
			fatherList = new ArrayList<String>();
			childList = new ArrayList<List<Adviser>>();
			for (int i = 0; i < ana_mode_array.length(); i++) {
				JSONObject ana_mode_obj = ana_mode_array.getJSONObject(i);
				fatherList.add(ana_mode_obj.getString("ModeName"));

				list = new ArrayList<Adviser>();

				JSONArray ana_array = ana_mode_obj.getJSONArray("data");
				for (int j = 0; j < ana_array.length(); j++) {
					JSONObject ana_obj = ana_array.getJSONObject(j);

					String Id = ana_obj.getString("Id");
					String Name = ana_obj.getString("Name");
					String GroupId = ana_obj.getString("GroupId");
					String Mobilephone = ana_obj.getString("Mobilephone");
					String Email = ana_obj.getString("Email");
					String Mark = ana_obj.getString("Mark");
					String Sex = ana_obj.getString("Sex");
					String Orgid = ana_obj.getString("Orgid");
					String Resume = ana_obj.getString("Resume");
					String PTitle = ana_obj.getString("PTitle");
					String OrgName = ana_obj.getString("OrgName");
					String Heartcount = ana_obj.getString("Heartcount");
					String RealName = ana_obj.getString("RealName");
					String Paidmark = ana_obj.getString("Paidmark");
					String Specialty = ana_obj.getString("Specialty");
					String HeadPic = ana_obj.getString("HeadPic");
					String TagName = "";
					String TagCss = "";

					boolean isFirst = true;
					JSONArray ana_arraytag = ana_obj.getJSONArray("Tag");
					for (int k = 0; k < ana_arraytag.length(); k++) {
						JSONObject ana_objtag = ana_arraytag.getJSONObject(k);
						if (isFirst) {
							TagName = ana_objtag.getString("TagName");
							TagCss = ana_objtag.getString("TagCss");
							isFirst = false;
						} else {
							TagName = TagName + "%"
									+ ana_objtag.getString("TagName");
							TagCss = TagCss + "%"
									+ ana_objtag.getString("TagCss");
						}
					}

					Adviser ana_data = new Adviser();
					ana_data.setAdv_user_id(Id);
					ana_data.setName(Name);
					ana_data.setGroupid(GroupId);
					ana_data.setMobilephone(Mobilephone);
					ana_data.setEmail(Email);
					ana_data.setMark(Mark);
					ana_data.setSex(Sex);
					ana_data.setOrgid(Orgid);
					ana_data.setResume(Resume);
					ana_data.setPtitle(PTitle);
					ana_data.setOrgname(OrgName);
					ana_data.setHeartcount(Heartcount);
					ana_data.setRealname(RealName);
					ana_data.setPaidmark(Paidmark);
					ana_data.setSpecialty(Specialty);
					ana_data.setHeadpic(HeadPic);
					ana_data.setTagName(TagName);
					ana_data.setTagCss(TagCss);
					// Adviser ana_data = new Adviser(RealName, Paidmark,
					// Specialty, TagName, TagCss, HeadPic);
					if(!GroupId.equals("16")){
						
						list.add(ana_data);
					}
				}
				childList.add(list);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setAdpter();
	}

	private void initDate() {
		String[] property_nm = { "pagesize", "pageindex" };
		Object[] property_va = { 100, 1 };
		new AdvisterRefurbishAT().execute(property_nm, property_va);
	}

	private void initChild(int id) {

	}

	private void setAdpter() // 绑定数据
	{
		if (adpter == null) {
			adpter = new Analyst_Adapter(fatherList, childList, this);
			listView.setAdapter(adpter);
			
			for(int i = 0; i < adpter.getGroupCount(); i++){  
				listView.expandGroup(i);  
			}
			
			listView.setOnChildClickListener(new OnChildClickListener() {
				@Override
				public boolean onChildClick(ExpandableListView parent, View v,
						int groupPosition, int childPosition, long id) {
					// TODO
					Intent intent = new Intent();
					Bundle bundle = new Bundle();
					bundle.putSerializable("analystInfo", childList.get(groupPosition).get(childPosition));
					intent.putExtras(bundle);
					intent.setClass(Analyst_Activity.this, AnalystTeamInfo_Activity.class);
					startActivity(intent);
					return true;
				}
			});
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

	class AdvisterRefurbishAT extends AsyncTask<Object, Object, Object> {
		@Override
		protected void onPreExecute() {
			// data = new ArrayList<Adviser>();
			super.onPreExecute();
		}

		@Override
		protected Object doInBackground(Object... params) {
			String[] property_nm = { "pagesize", "pageindex" };
			Object[] property_va = { 1000, 1 };
			Object res_obj = SoapWebService.data(
					SOAP_UTILS.METHOD.GETANALYSTJSON, (String[]) params[0],
					(Object[]) params[1]);
			if (null != res_obj) {
				SoapObject so = (SoapObject) res_obj;
				SoapObject soapchilds = (SoapObject) so.getProperty(0);
				String soap_str = soapchilds.toString();
				String[] res_array = soap_str.split("=");
				res_str = res_array[1];
				System.out.println("-------------" + soapchilds);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			// Toast.makeText(Analyst_Activity.this, res_str,
			// Toast.LENGTH_SHORT).show();
			setUI();
			super.onPostExecute(result);
		}

	}
}
