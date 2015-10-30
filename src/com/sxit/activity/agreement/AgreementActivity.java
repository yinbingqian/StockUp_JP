package com.sxit.activity.agreement;

import lnpdit.lntv.tradingtime.R;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.sxit.activity.base.BaseActivity;

public class AgreementActivity extends BaseActivity {
	private ImageView img_back;
	private WebView web;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_agreement);
		img_back = (ImageView) findViewById(R.id.img_back);
		img_back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		web = (WebView) findViewById(R.id.web);

		web.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		web.loadUrl("http://guzhang.tvlicai.com/AD/agreement.html");
	}

}
