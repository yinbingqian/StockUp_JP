package com.sxit.activity.news;

import lnpdit.lntv.tradingtime.R;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.widget.ImageView;

import com.sxit.activity.base.BaseActivity;
import com.sxit.customview.FocusedtrueTV;
import com.sxit.entity.news.FinNewsInfo;
import com.sxit.entity.news.FinNewsList;
import com.sxit.http.SoapRes;
import com.sxit.utils.SOAP_UTILS;

/**
 * 新闻详情
 * 
 * @author huanyu 类名称：NewsInfo_Activity 创建时间:2014-10-29 下午3:18:47
 */
@SuppressLint("NewApi")
public class NewsInfo_Activity extends BaseActivity {
	private ImageView img_back;
	private FocusedtrueTV tv_title;
	private WebView webView;
	private FinNewsList newsList;
	private FinNewsInfo newsInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_newsinfo);
		super.onCreate(savedInstanceState);
		this.isParentActivity = false;
		intent = getIntent();
		newsList = (FinNewsList) intent.getSerializableExtra("finnewslist");
		initView();
		setListeners();
		String[] property_va = new String[] { newsList.getId() };
		soapService.getNewsContent(property_va);
	}

	private void initView() {
		img_back = (ImageView) findViewById(R.id.img_back);
		webView = (WebView) findViewById(R.id.webView);
		tv_title = (FocusedtrueTV) findViewById(R.id.tv_title);
		tv_title.setText(newsList.getTitle());
	}

	private void setListeners() {
		img_back.setOnClickListener(this);
	}

	public void setUI() {
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setPluginState(PluginState.ON);
		webView.getSettings().setPluginsEnabled(true);//可以使用插件
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webView.getSettings().setAllowFileAccess(true);
		webView.getSettings().setDefaultTextEncodingName("UTF-8");
		webView.getSettings().setLoadWithOverviewMode(true);
//		webView.getSettings().setUseWideViewPort(true);
		webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webView.setVisibility(View.VISIBLE);
		
		String url = SOAP_UTILS.IP + "/" + "showNews.aspx?id=" + newsList.getId();
		webView.loadUrl(url);
//		webView.loadDataWithBaseURL(null, newsInfo.getContent(), "text/html", "utf-8", null);
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

	@SuppressLint("NewApi")
	@Override
	protected void onPause() {
		super.onPause();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {  
	         webView.onPause(); // 暂停网页中正在播放的视频  
	    }
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	public void onEvent(SoapRes obj) {
		super.onEvent(obj);
		if (obj.getCode().equals(SOAP_UTILS.METHOD.GETNEWSCONTENT)) {
			newsInfo = (FinNewsInfo) obj.getObj();
			if(newsInfo != null){
				setUI();
			}
		}
	}

	@Override
	public void onEventMainThread(String method) {
		if (method.equals(SOAP_UTILS.METHOD.GETNEWSCONTENT)) {
			String[] property_va = new String[] { newsList.getId() };
			soapService.getNewsContent(property_va);
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
}
