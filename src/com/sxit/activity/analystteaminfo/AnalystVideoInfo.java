package com.sxit.activity.analystteaminfo;

import com.sxit.customview.FocusedtrueTV;
import com.sxit.http.SoapRes;
import com.sxit.utils.SOAP_UTILS;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.widget.ImageView;
import lnpdit.lntv.tradingtime.R;

/**
 * 视频详情
 * 
 */
public class AnalystVideoInfo extends Activity implements OnClickListener{
	private ImageView img_back;
	private FocusedtrueTV tv_title;
	private WebView webView;
//	private FinNewsList newsList;
//	private FinNewsInfo newsInfo;
	String Id; 
	String Title;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_videoinfo);
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		Id = intent.getStringExtra("Id");
		Title = intent.getStringExtra("Title");
//		newsList = (FinNewsList) intent.getSerializableExtra("finnewslist");
		initView();
		setListeners();
		setUI();
	}

	private void initView() {
		img_back = (ImageView) findViewById(R.id.img_back);
		webView = (WebView) findViewById(R.id.webView);
		tv_title = (FocusedtrueTV) findViewById(R.id.tv_title);
		tv_title.setText(Title);
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
		
		String url = SOAP_UTILS.IP + "/" + "analystvideo.aspx?id=" + Id;
		webView.loadUrl(url);
//		String url = SOAP_UTILS.IP + "/" + "showNews.aspx?id=" + newsList.getId();
//		webView.loadUrl(url);
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
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
}
