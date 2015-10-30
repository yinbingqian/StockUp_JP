package com.sxit.activity.wap;

import lnpdit.lntv.tradingtime.R;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.widget.ImageView;

import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.base.MyApplication;
import com.sxit.activity.login.Login_Activity;
import com.sxit.activity.setting.Setting_Activity;
import com.sxit.activity.usersetting.UserSetting_Activity;
import com.sxit.customview.FocusedtrueTV;
import com.sxit.customview.ProgressWebView;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.ShareTool;

public class Wap_Activity extends BaseActivity {
	private ProgressWebView webview;
	private FocusedtrueTV tv_title;
	private ImageView img_back;
	private ImageView img_share;
	private String url;
	private String name;
	private String content;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wap_layout);
		context = this;
		this.isParentActivity = false;
		url = SOAP_UTILS.IP+"/"+this.getIntent().getStringExtra("wap_url");
		System.out.println(">>>> loading url "+ url);
		// url = "http://www.baidu.com";
		name = this.getIntent().getStringExtra("wap_name");
		content = this.getIntent().getStringExtra("wap_content");

		// ~~~ 绑定控件
		webview = (ProgressWebView) findViewById(R.id.webview);
		img_back = (ImageView) findViewById(R.id.img_back);
		img_share = (ImageView) findViewById(R.id.more_fun);
		img_back.setOnClickListener(this);
		img_share.setOnClickListener(this);
		tv_title = (FocusedtrueTV) findViewById(R.id.tv_title);
//		img_back.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				finish();
//			}
//		});
//		img_share.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				ShareTool.getInstance(context,Wap_Activity.class).shareMsg(context);
//			}
//		});
		// ~~~ 设置数据
		tv_title.setText(name);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.setDownloadListener(new DownloadListener() {
			@Override
			public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
				if (url != null && url.startsWith("http://"))
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
			}
		});

		// webview.setWebViewClient(new WebViewClient() {
		// public boolean shouldOverrideUrlLoading(WebView view, String url) {
		// view.loadUrl(url);
		// return true;
		// }
		// });

		webview.loadUrl(url);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.more_fun:
//			ShareTool.getInstance(this, this).TITLE = name;
//			ShareTool.getInstance(this, this).CONTENT = name;
//			ShareTool.getInstance(this, this).TARGETURL = url;
//			ShareTool.getInstance(this, this).setParser(name, name, url);
			ShareTool.getInstance(this,this, name, content, url).shareMsg(this);
			break;
		default:
			break;
		}
		super.onClick(v);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
			webview.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
