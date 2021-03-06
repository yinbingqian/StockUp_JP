package com.sxit.activity.register;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import lnpdit.lntv.tradingtime.R;
import android.content.AsyncQueryHandler;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.register.adapter.City_adapter;
import com.sxit.customview.CityLetterListView;
import com.sxit.customview.CityLetterListView.OnTouchingLetterChangedListener;
import com.sxit.entity.city.City;
import com.sxit.utils.EventCache;
import com.sxit.utils.JsonUtils;
import com.sxit.utils.Utils;

/**
 * 城市 Acy（失效）
 * 
 * @author huanyu 类名称：City_Activity 创建时间:2014-11-7 下午3:05:37
 */
public class City_Activity extends BaseActivity {
	private ImageView img_back;
	private CityLetterListView letterListView;
	private ListView list_view;
	private City_adapter adapter;
	// 用户信息List
	List<ContentValues> userList = new ArrayList<ContentValues>();
	private AsyncQueryHandler asyncQuery;
	// 首字母提示显示时间Handler
	private Handler handler;
	// 字母提示Thread
	private OverlayThread overlayThread;
	// 首字母弹出提示Textview
	private TextView overlay;
	// 存放存在的汉语拼音首字母和与之对应的列表位置
	private HashMap<String, Integer> alphaIndexer;
	// 存放存在的汉语拼音首字母
	private String[] sections;
	// 管理器
	WindowManager windowManager;;
	private static final String NAME = "name", SORT_KEY = "sort_key";
	private String citys[];
	private String city;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_city);
		this.isParentActivity = false;
		EventCache.opAnswerEvent.unregister(this);
		EventCache.opAnswerEvent.register(this);
		citys = ((City) JsonUtils.jsonToBean(Utils.getObject(this, R.raw.city).toString(), City.class)).getCity();
		initView();
		setListeners();
	}

	private void initView() {
		img_back = (ImageView) findViewById(R.id.img_back);
		letterListView = (CityLetterListView) findViewById(R.id.letterListView);
		list_view = (ListView) findViewById(R.id.list_view);
	}

	private void setListeners() {
		img_back.setOnClickListener(this);
		for (String cityItem : citys) {
			ContentValues cv = new ContentValues();
			cv.put(NAME, cityItem);
			cv.put(SORT_KEY, "" + Utils.converterToFirstSpell(cityItem));
			userList.add(cv);
		}
		setAdapterToList(userList);
		// 初始化字母检索listview
		letterListView = (CityLetterListView) findViewById(R.id.letterListView);
		// 设置listview变更事件
		letterListView.setOnTouchingLetterChangedListener(new LetterListViewListener());
		// 初始化首字母提示时间Handler
		handler = new Handler();
		// 初始化字母提示Thread
		overlayThread = new OverlayThread();
		// 初始化首字母弹出提示框
		initOverlay();
	}

	private void setAdapterToList(List<ContentValues> userList) {
		alphaIndexer = new HashMap<String, Integer>();
		sections = new String[userList.size()];
		for (int i = 0; i < userList.size(); i++) {
			// 当前汉语拼音首字母
			String currentStr = getAlpha(userList.get(i).getAsString(SORT_KEY));
			// 上一个汉语拼音首字母，如果不存在为“ ”
			String previewStr = (i - 1) >= 0 ? getAlpha(userList.get(i - 1).getAsString(SORT_KEY)) : " ";
			if (!previewStr.equals(currentStr)) {
				String name = getAlpha(userList.get(i).getAsString(SORT_KEY));
				alphaIndexer.put(name, i);
				sections[i] = name;
			}
		}
		adapter = new City_adapter(this, userList);
		list_view.setAdapter(adapter);
	}

	/**
	 * 字母检索listview更改事件
	 * 
	 * @author Song
	 * 
	 */
	private class LetterListViewListener implements OnTouchingLetterChangedListener {

		@Override
		public void onTouchingLetterChanged(final String s) {
			if (alphaIndexer.get(s) != null) {
				int position = alphaIndexer.get(s);
				list_view.setSelection(position);
				overlay.setText(sections[position]);
				overlay.setVisibility(View.VISIBLE);
				handler.removeCallbacks(overlayThread);
				// 延迟一秒后执行，让overlay为不可见
				handler.postDelayed(overlayThread, 1000);
			}
		}

	}

	/**
	 * 设置overlay不可见Thread
	 * 
	 * @author Song
	 * 
	 */
	private class OverlayThread implements Runnable {

		@Override
		public void run() {
			overlay.setVisibility(View.GONE);
		}

	}

	/**
	 * 获得汉语拼音首字母
	 * 
	 * @param str
	 * @return
	 */
	private String getAlpha(String str) {
		if (str == null) {
			return "#";
		}
		if (str.trim().length() == 0) {
			return "#";
		}
		char c = str.trim().substring(0, 1).charAt(0);
		// 正则表达式，判断首字母是否是英文字母
		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
		if (pattern.matcher(c + "").matches()) {
			return (c + "").toUpperCase();
		} else {
			return "#";
		}
	}

	/**
	 * 初始化汉语拼音首字母弹出提示框
	 */
	private void initOverlay() {
		LayoutInflater inflater = LayoutInflater.from(this);
		overlay = (TextView) inflater.inflate(R.layout.activity_city_number_toast, null);
		overlay.setVisibility(View.INVISIBLE);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_APPLICATION, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, PixelFormat.TRANSLUCENT);
		windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		windowManager.addView(overlay, lp);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finish();
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
		super.onDestroy();
		EventCache.opAnswerEvent.unregister(this);
		EventCache.commandActivity.post(new String[] { this.getClass().getName(), city });
	}

	/**
	 * radio点击回调
	 * 
	 * @param time
	 *            选中时间
	 */
	public void onEvent(String city) {
		this.city = city;
	}

}
