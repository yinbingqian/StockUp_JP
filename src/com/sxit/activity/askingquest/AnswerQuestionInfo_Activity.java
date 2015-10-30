package com.sxit.activity.askingquest;

import java.util.ArrayList;
import java.util.List;

import lnpdit.lntv.tradingtime.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sxit.activity.base.BaseActivity;
import com.sxit.entity.anwser.Anwser;
import com.sxit.entity.anwser.AnwserInfo;
import com.sxit.http.SoapRes;
import com.sxit.instance.Instance;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.Utils;

/**
 * 解答详情 Acy
 * 
 * @author huanyu 类名称：AnswerQuestion_Activity 创建时间:2014-10-29 下午4:56:35
 */
public class AnswerQuestionInfo_Activity extends BaseActivity {
	private ImageView img_back, answerInfo_img;
	private boolean isCheck;// 采纳状态
	private Button bt_check;
	private String answer;
	private String id;
	private AnwserInfo info;
	//
	private ImageView img_icon;// headPic
	private TextView tv_realname;
	private TextView tv_orgName;// 中心证券
	private TextView tv_pTitle;// 高级咨询师
	private TextView tv_crtime;// 回复时间
	private TextView tv_content;// 回复内容
	// private WebView webView;
	String content;
	String mode;
	private List<Anwser> list = new ArrayList<Anwser>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_answerquestioninfo);
		super.onCreate(savedInstanceState);
		this.isParentActivity = false;
		intent = getIntent();
		// content = intent.getStringExtra("content");
		isCheck = intent.getBooleanExtra("isCheck", false);
		answer = intent.getStringExtra("answer");
		id = intent.getStringExtra("id");
		mode = intent.getStringExtra("mode");
		initView();
		setListeners();
		String[] property_va = new String[] { id };
		if (mode.equals("0")) {
			soapService.getCommunReplyByID(property_va);
		} else if (mode.equals("1")) {
			soapService.getBestAnswer(property_va);
		}
	}

	private void initView() {
		img_back = (ImageView) findViewById(R.id.img_back);
		answerInfo_img = (ImageView) findViewById(R.id.answerInfo_img);
		Instance.imageLoader.displayImage(
				"http://img1.money.126.net/chart/hs/time/540x360/0000001.png",
				answerInfo_img, Instance.new_s_options);
		answerInfo_img.setVisibility(View.GONE);
		bt_check = (Button) findViewById(R.id.bt_check);
		if (isCheck) {
			bt_check.setText(getString(R.string.useanswer));
		}
		//
		img_icon = (ImageView) findViewById(R.id.img_icon);
		tv_realname = (TextView) findViewById(R.id.tv_realname);
		tv_orgName = (TextView) findViewById(R.id.tv_orgName);
		tv_pTitle = (TextView) findViewById(R.id.tv_pTitle);
		tv_crtime = (TextView) findViewById(R.id.tv_crtime);
		tv_content = (TextView) findViewById(R.id.tv_content);
		// webView = (WebView) findViewById(R.id.webView);
	}

	private void setListeners() {
		img_back.setOnClickListener(this);
		bt_check.setOnClickListener(this);
	}

	private void setUI() {
		Instance.imageLoader.displayImage(info.getHeadPic(), img_icon,
				Instance.user_options);
		tv_realname.setText(info.getRealName());
		tv_orgName.setText(info.getOrgName());
		tv_pTitle.setText(info.getpTitle());
		tv_crtime.setText(info.getCrtime());
		tv_content.setText(info.getReplyAbstract());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.bt_check:
			if (isCheck) {
				Utils.showTextToast(AnswerQuestionInfo_Activity.this, "问题已采纳");
			} else {
				// String[] property_va = new String[] { answer };
				String[] property_va = new String[] { id };
				soapService.updataBestAnswer(property_va);
			}
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
		super.onDestroy();
	}

	public void onEvent(SoapRes obj) {
		super.onEvent(obj);
		if (obj.getCode().equals(SOAP_UTILS.METHOD.GETBESTANSWER)) {
			// if (obj.getCode().equals(SOAP_UTILS.METHOD.GETCOMMUNREPLY)) {
			if (obj.getObj().toString().equals("")) {
				info = null;
			} else {
				info = (AnwserInfo) obj.getObj();
			}
			if (info != null) {

				if (info.toString().equals("")) {
//					Toast.makeText(AnswerQuestionInfo_Activity.this, "该问题已关闭",
//							Toast.LENGTH_SHORT).show();
					tv_content.setText("该问题已关闭");
					bt_check.setVisibility(8);
//					finish();
				} else {
					setUI();
				}
			} else {
				tv_content.setText("该问题已关闭");
				bt_check.setVisibility(8);
//				finish();
			}
		}
		if (obj.getCode().equals(SOAP_UTILS.METHOD.GETCOMMUNREPLYBYID)) {
			info = (AnwserInfo) obj.getObj();
			if (info != null) {
				setUI();
			}
		}
		if (obj.getCode().equals(SOAP_UTILS.METHOD.UPDATABESTANSWER)) {
			if (obj.getObj() != null) {
				if (obj.getObj().toString().equals("true")) {
					finish();
				} else {
					Utils.showTextToast(this, getString(R.string.check_fail));
				}
			} else {
				Utils.showTextToast(this, getString(R.string.check_fail));
			}
		}
	}

	public void onEventMainThread(String method) {
		if (method.equals(SOAP_UTILS.METHOD.GETBESTANSWER) && mode.equals("1")) {
			String[] property_va = new String[] { id };
			soapService.getBestAnswer(property_va);
		}
		if (method.equals(SOAP_UTILS.METHOD.GETCOMMUNREPLYBYID)
				&& mode.equals("0")) {
			String[] property_va = new String[] { id };
			soapService.getCommunReplyByID(property_va);
		}
	}

}
