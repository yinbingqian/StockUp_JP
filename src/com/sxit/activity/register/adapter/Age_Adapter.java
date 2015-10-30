package com.sxit.activity.register.adapter;

import lnpdit.lntv.tradingtime.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;

import com.sxit.customview.RadioButton;
import com.sxit.utils.EventCache;
/**
 * 股龄 adapter
 * @author huanyu	
 * 类名称：Age_Adapter   
 * 创建时间:2014-11-7 下午3:45:35
 */
public class Age_Adapter extends BaseAdapter {
	private Context context;
	private RadioButton temp;
	private String[] time;
	public int state[] = { 0, R.drawable.right_icon };
	public static int locationPosition = -1;

	public Age_Adapter(Context context, String[] time) {
		super();
		this.context = context;
		this.time = time;
		locationPosition = -1;
	}

	@Override
	public int getCount() {
		return time.length;
	}

	@Override
	public Object getItem(int position) {
		return time[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final RadioButton radioButton;
		if (convertView == null) {
			radioButton = new RadioButton(context, R.layout.radiobutton_item_sex, state);
		} else {
			radioButton = (RadioButton) convertView;
		}

		radioButton.setText(time[position]);

		radioButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (temp != null) {
					temp.ChageImage();
				}
				temp = radioButton;
				radioButton.ChageImage();
				Age_Adapter.locationPosition = position;
				EventCache.opAnswerEvent.post(radioButton.getText());
			}
		});
		if(Age_Adapter.locationPosition != position){
			radioButton.defualtImage(0);
		}else{
			radioButton.defualtImage(1);
		}
		return radioButton;
	}
}
