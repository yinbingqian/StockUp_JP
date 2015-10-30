package com.sxit.activity.askingquest.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import lnpdit.lntv.tradingtime.R;

/**
 * 提问 adapter
 * @author huanyu	
 * 类名称：AskingQuestionInfo_Adapter   
 * 创建时间:2014-10-28 下午4:20:38
 */
public class AskingQuestionInfo_Adapter extends BaseAdapter {
	private String[] money;
	private Context context;

	public AskingQuestionInfo_Adapter(Context context, String[] money) {
		this.context = context;
		this.money = money;
	}

	@Override
	public int getCount() {
		return money.length;
	}

	@Override
	public Object getItem(int position) {
		return money[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder_Info viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_askingquestioninfo_item, null);
			viewHolder = new ViewHolder_Info();
			viewHolder.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder_Info) convertView.getTag();
		}
		viewHolder.tv_money.setText(money[position]);
		return convertView;
	}

	public static class ViewHolder_Info {
		public TextView tv_money;
	}
}
