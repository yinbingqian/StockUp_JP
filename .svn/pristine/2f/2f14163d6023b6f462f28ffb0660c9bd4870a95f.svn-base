package com.sxit.activity.usersetting.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import lnpdit.lntv.tradingtime.R;
import com.sxit.customview.RadioButton;
import com.sxit.utils.EventCache;
/**
 * 性别 adapter
 * @author huanyu	
 * 类名称：UserSetting_Sex_Adapter   
 * 创建时间:2014-11-4 上午11:59:16
 */
public class UserSetting_Sex_Adapter extends BaseAdapter {
	private Context context;
	private RadioButton temp;
	private String[] sex;
	public int state[] = { 0, R.drawable.right_icon };
	private List<Boolean> list;

	public UserSetting_Sex_Adapter(Context context,String[] sex,List<Boolean> list) {
		super();
		this.context = context;
		this.sex = sex;
		this.list = list;
	}

	@Override
	public int getCount() {
		return sex.length;
	}

	@Override
	public Object getItem(int position) {
		return sex[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final RadioButton radioButton;
		if (convertView == null) {
			radioButton = new RadioButton(context,R.layout.radiobutton_item_sex,state);
		} else {
			radioButton = (RadioButton) convertView;
		}
		radioButton.setText(sex[position]);
		if(list.get(position)){
			if (temp != null) {
				temp.ChageImage();
			}
			temp = radioButton;
			radioButton.ChageImage();
		}
		radioButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (temp != null) {
					temp.ChageImage();
				}
				temp = radioButton;
				radioButton.ChageImage();
				EventCache.commandActivity.post(radioButton.getText().toString());
			}
		});

		return radioButton;
	}
}
