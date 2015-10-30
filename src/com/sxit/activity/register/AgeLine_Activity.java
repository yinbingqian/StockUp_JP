package com.sxit.activity.register;

import lnpdit.lntv.tradingtime.R;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.register.adapter.Age_Adapter;
import com.sxit.http.SoapRes;
import com.sxit.utils.EventCache;
import com.sxit.utils.Utils;

/**
 * 股龄线路 Acy
 * 
 * @author huanyu 类名称：AgeLine_Activity 创建时间:2014-11-7 下午3:15:49
 */
public class AgeLine_Activity extends BaseActivity {
	private ImageView img_back;// back
	private ListView lv_ageline;
	private String line[] = new String[] { "长线", "短线", "中长" };
	private String age;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_ageline);
		this.isParentActivity = false;
		EventCache.opAnswerEvent.unregister(this);
		EventCache.opAnswerEvent.register(this);
		initView();
		setListeners();
	}

	private void initView() {
		img_back = (ImageView) findViewById(R.id.img_back);
		lv_ageline = (ListView) findViewById(R.id.lv_ageline);
		lv_ageline.setAdapter(new Age_Adapter(this, line));
	}

	private void setListeners() {
		img_back.setOnClickListener(this);
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
		EventCache.opAnswerEvent.unregister(this);
		EventCache.commandActivity.post(new String[] { this.getClass().getName(), age });
		super.onDestroy();
	}

	/**
	 * radio点击回调
	 * 
	 * @param time
	 *            选中时间
	 */
	public void onEvent(String age) {
		this.age = age;
		finish();
	}
}
