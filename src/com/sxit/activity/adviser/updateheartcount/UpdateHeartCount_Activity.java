package com.sxit.activity.adviser.updateheartcount;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.sxit.activity.adviser.th.item.adapter.PublishMsg_Adapter;
import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.base.MyApplication;
import com.sxit.activity.login.Login_Activity;
import com.sxit.activity.register.Register_Activity;
import com.sxit.entity.PublishMessage;
import com.sxit.http.SoapRes;
import com.sxit.utils.SOAP_UTILS;

import lnpdit.lntv.tradingtime.R;

public class UpdateHeartCount_Activity extends BaseActivity {
	ImageView img_back;
	AutoCompleteTextView heart_count;
	Button bt_submit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_updateheartcount);
		heart_count = (AutoCompleteTextView) findViewById(R.id.heart_count);
		img_back = (ImageView) findViewById(R.id.img_back);
		bt_submit = (Button) findViewById(R.id.bt_submit);
		img_back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		bt_submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String c = heart_count.getText().toString().trim();
				if (c.equals("")) {
					c = "0";
				}
				int count = Integer.parseInt(c);
				int uid = Integer.parseInt(getLoginUser().getUserid());
				Object[] property_va = new Object[] { uid, count };
				soapService.heartCountUpdate(property_va);
			}
		});
		heart_count.setText(MyApplication.getInstance().getA_heartcount());
	}

	public void onEvent(SoapRes obj) {
		super.onEvent(obj);
		if (obj.getCode().equals(SOAP_UTILS.METHOD.HEARTCOUNTUPDATE)) {
			System.out.println(">>>>>###$$" + obj.getObj());
			if (obj.getObj().toString().equals("true")) {
				Toast.makeText(this, "红心更新成功，当前红心设定为" +heart_count.getText().toString().trim() , Toast.LENGTH_SHORT).show();
				MyApplication.getInstance().setA_heartcount(heart_count.getText().toString().trim());
				finish();
			} else {
				Toast.makeText(this, "红心更新失败", Toast.LENGTH_SHORT).show();
			}

		}
	}

}
