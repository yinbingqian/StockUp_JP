package com.sxit.activity.usersetting;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sxit.activity.base.BaseActivity;
import com.sxit.entity.LoginUser;
import com.sxit.http.SoapRes;

import lnpdit.lntv.tradingtime.R;
import com.sxit.utils.EventCache;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.Utils;

/**
 * 昵称
 * 
 * @author huanyu 类名称：UserSetting_Name_Activity 创建时间:2014-11-3 上午11:54:23
 */
public class UserSetting_Name_Activity extends BaseActivity {
	private ImageView img_back;
	private AutoCompleteTextView actv_name;
	private TextView tv_save, tv_edit,tv_num;
	private String name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usersetting_name);
		intent = getIntent();
		name = intent.getStringExtra("name");
		initView();
		setListeners();
	}

	private void initView() {
		img_back = (ImageView) findViewById(R.id.img_back);
		actv_name = (AutoCompleteTextView) findViewById(R.id.actv_name);
		tv_edit = (TextView) findViewById(R.id.tv_edit);
		tv_save = (TextView) findViewById(R.id.tv_save);
		tv_num = (TextView) findViewById(R.id.tv_num);
		actv_name.setText(name);
		actv_name.setEnabled(true);
		
		final int num = 7; 
		
		actv_name.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;
            private int selectionStart;
            private int selectionEnd;

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {
                temp = s;
                System.out.println("s="+s);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int number = num - s.length();
                tv_num.setText("还剩" + number + "个字可输入");
                selectionStart = actv_name.getSelectionStart();
                selectionEnd = actv_name.getSelectionEnd();
                //System.out.println("start="+selectionStart+",end="+selectionEnd);
                if (temp.length() > num) {
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionStart;
                    actv_name.setText(s);
                    actv_name.setSelection(tempSelection);//设置光标在最后
                }
            }
        });
	}

	private void setListeners() {
		img_back.setOnClickListener(this);
		tv_edit.setOnClickListener(this);
		tv_save.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.tv_edit:
			finish();
			// actv_name.setEnabled(true);
			break;
		case R.id.tv_save:
			if (actv_name.getText().toString().trim().equals("")) {
				Utils.showTextToast(UserSetting_Name_Activity.this, getString(R.string.alter_empty));
			} else {
				Utils.showTextToast(UserSetting_Name_Activity.this, getString(R.string.alter_success));
				String[] property_va = new String[] { getLoginUser().getUserid(), "1", actv_name.getText().toString().trim() };
				soapService.userEditor(property_va);
				finish();
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		}
		return true;
	}

	public void onEvent(SoapRes res) {
		if (res.getCode().equals(SOAP_UTILS.METHOD.USEREDITOR)) {
			if(res.getObj() != null){
				if (res.getObj().toString().equals("true")) {
					Utils.showTextToast(this, getString(R.string.edit_success));
					LoginUser user =getLoginUser();
					user.setRealname(actv_name.getText().toString().trim());
					setLoginUser(user);
					actv_name.setEnabled(false);
					EventCache.commandActivity.post(new String[] { this.getClass().getName(), actv_name.getText().toString().trim() });
				} else {
					Utils.showTextToast(this, getString(R.string.realnameedit_fail));
				}
			}else{
				Utils.showTextToast(this, getString(R.string.realnameedit_fail));
			}
		}
	}

}
