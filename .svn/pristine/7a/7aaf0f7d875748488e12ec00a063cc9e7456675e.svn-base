package com.sxit.activity.adviser.publishmsg;

import lnpdit.lntv.tradingtime.R;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sxit.activity.base.BaseActivity;
import com.sxit.customview.FocusedtrueTV;
import com.sxit.entity.PublishMessage;
import com.sxit.entity.PublishMessageInfo;
import com.sxit.http.SoapRes;
import com.sxit.instance.Instance;
import com.sxit.utils.SOAP_UTILS;

/**
 * 研报详情
 * 
 * @author huanyu 类名称：PublishMsgInfo_Acitivty 创建时间:2014-11-24 下午4:45:57
 */
public class PublishMsgInfo_Acitivty extends BaseActivity {
	private FocusedtrueTV tv_title;
	private ImageView img_back;
	private TextView tv_realname;//realname
	private TextView tv_crtime;//time
	private TextView tv_content;//contet
	private TextView tv_abstract;//摘要
	private ImageView img_icon;//head
	private ImageView img_photo;//photo
	
	PublishMessage message;
	PublishMessageInfo info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.setContentView(R.layout.activity_publistmsginfo_layout);
		super.onCreate(savedInstanceState);
		this.isParentActivity = false;
		intent = getIntent();
		message = (PublishMessage) intent.getSerializableExtra("publishMsg");
		initView();
		setListeners();
		String[] property_va = new String[] { message.getId() };
		soapService.getReportContent(property_va);
	}

	private void initView() {
		tv_title = (FocusedtrueTV) findViewById(R.id.tv_title);
		tv_title.setText(message.getBlogTitle());
		img_back = (ImageView) findViewById(R.id.img_back);
		tv_realname = (TextView) findViewById(R.id.tv_realname);
		tv_crtime = (TextView) findViewById(R.id.tv_crtime);
		tv_content = (TextView) findViewById(R.id.tv_content);
		tv_abstract = (TextView) findViewById(R.id.tv_abstract);
		img_icon = (ImageView) findViewById(R.id.img_icon);
		img_photo = (ImageView) findViewById(R.id.img_photo);
	}

	private void setListeners() {
		img_back.setOnClickListener(this);
	}

	private void setUI() {
		tv_crtime.setText(info.getCrtime());
		Instance.imageLoader.displayImage(SOAP_UTILS.HEADER_URL + getLoginUser().getHeadpic(), img_icon, Instance.user_options);
		tv_realname.setText(getLoginUser().getRealname());
		tv_content.setText(info.getContent());
		if(info.getPhoto().equals(SOAP_UTILS.PIC_YANBAO + "no.jpg")){//no image
			img_photo.setVisibility(View.GONE);
		}else{
			Instance.imageLoader.displayImage(info.getPhoto(), img_photo, Instance.new_s_options);
			img_photo.setVisibility(View.VISIBLE);
		}
		tv_abstract.setText(info.getBlogAbstract());
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

	public void onEvent(SoapRes obj) {
		super.onEvent(obj);
		if (obj.getCode().equals(SOAP_UTILS.METHOD.GETREPORTCONTENT)) {
			info = (PublishMessageInfo) obj.getObj();
			if (info != null) {
				setUI();
			}
		}
	}

	@Override
	public void onEventMainThread(String method) {
		if (method.equals(SOAP_UTILS.METHOD.GETNEWSCONTENT)) {
			String[] property_va = new String[] { message.getId() };
			soapService.getReportContent(property_va);
		}
	}
}
