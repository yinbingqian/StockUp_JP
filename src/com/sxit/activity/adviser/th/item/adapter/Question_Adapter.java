package com.sxit.activity.adviser.th.item.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sxit.activity.adviser.th.item.bean.Question_ListBean;
import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.th.item.bean.AskQuestion_ListBean;
import com.sxit.instance.Instance;
import com.sxit.utils.SOAP_UTILS;

import lnpdit.lntv.tradingtime.R;

/**
 * 分析师
 * 
 * @author huanyu 类名称：Analyst_Adapter 创建时间:2014-10-27 下午6:34:46
 */
public class Question_Adapter extends BaseAdapter {
	private Context context;
	private List<Question_ListBean> list;

	public Question_Adapter(Context context, List<Question_ListBean> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_question_listview_item, null);
			viewHolder = new ViewHolder();
			viewHolder.img_icon = (ImageView) convertView.findViewById(R.id.img_icon);
			viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			viewHolder.tv_questionState = (TextView) convertView.findViewById(R.id.tv_questionState);
			viewHolder.tv_start = (TextView) convertView.findViewById(R.id.tv_start);
			viewHolder.tv_end = (TextView) convertView.findViewById(R.id.tv_end);
			viewHolder.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
			viewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Instance.imageLoader.displayImage(list.get(position).getHeadpic(), viewHolder.img_icon, Instance.user_options);//head
		viewHolder.tv_name.setText(list.get(position).getUserName());
		if(list.get(position).getStatus().equals("0")){//解答中
			viewHolder.tv_questionState.setText(context.getString(R.string.asking));
			viewHolder.tv_questionState.setTextColor(context.getResources().getColor(R.color.question_asking));
			viewHolder.tv_start.setText(list.get(position).getCrtime());
			viewHolder.tv_end.setText(list.get(position).getAging());
		}else{//已解答
			viewHolder.tv_questionState.setText(context.getString(R.string.answer));
			viewHolder.tv_questionState.setTextColor(context.getResources().getColor(R.color.question_answer));
			viewHolder.tv_start.setText(list.get(position).getAging());
		}
		viewHolder.tv_money.setText(list.get(position).getMark());
		viewHolder.tv_content.setText(list.get(position).getContent());
		return convertView;
	}

	static class ViewHolder {
		public ImageView img_icon;// icon
		public TextView tv_name;// 名称
		public TextView tv_questionState;// 状态
		public TextView tv_start;//
		public TextView tv_end;//
		public TextView tv_money;//
		public TextView tv_content;
	}
}
