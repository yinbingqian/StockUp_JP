package com.sxit.activity.register;

import lnpdit.lntv.tradingtime.R;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.sxit.activity.askingquest.adapter.OpAnswer_Adapter;
import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.register.adapter.Age_Adapter;
import com.sxit.utils.EventCache;
import com.sxit.utils.Utils;

/**
 * 股龄 Acy
 * 
 * @author huanyu 类名称：Age_Activity 创建时间:2014-11-7 下午3:15:20
 */
public class Age_Activity extends BaseActivity {
	private ImageView img_back;// back
	private ListView lv_age;
	private String age[] = new String[] { "2年", "3年", "4年", "5年", "6年", "7年", "8年", "9年" };
	private String line;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_age);
		this.isParentActivity = false;
		EventCache.opAnswerEvent.unregister(this);
		EventCache.opAnswerEvent.register(this);
		initView();
		setListeners();
	}

	private void initView() {
		img_back = (ImageView) findViewById(R.id.img_back);
		lv_age = (ListView) findViewById(R.id.lv_age);
		lv_age.setAdapter(new Age_Adapter(this, age));
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
		super.onClick(v);
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
		EventCache.commandActivity.post(new String[] { this.getClass().getName(), line });
		super.onDestroy();
	}

	/**
	 * radio点击回调
	 * 
	 * @param time
	 *            选中时间
	 */
	public void onEvent(String age) {
		this.line = age;
		finish();
	}
}
