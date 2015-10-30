package com.sxit.activity.askingquest.adapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import lnpdit.lntv.tradingtime.R;
import com.sxit.customview.RadioButton;
import com.sxit.utils.EventCache;

public class OpAnswer_Adapter extends BaseAdapter {
	private Context context;
	private RadioButton temp;
	private String[] time;
	public int state[] = { R.drawable.radio_unchecked, R.drawable.radio_checked };
	
	public OpAnswer_Adapter(Context context,String[] time) {
		super();
		this.context = context;
		this.time = time;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		final RadioButton radioButton;
		if (convertView == null) {
			radioButton = new RadioButton(context,R.layout.radiobutton_item,state);
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
				EventCache.opAnswerEvent.post(radioButton.getText());
			}
		});

		return radioButton;
	}
}
