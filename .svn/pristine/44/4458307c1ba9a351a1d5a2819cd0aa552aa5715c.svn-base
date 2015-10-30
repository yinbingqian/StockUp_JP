package com.sxit.activity.askingquest.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lnpdit.lntv.tradingtime.R;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hp.hpl.sparta.Text;
import com.sxit.activity.wap.Wap_Activity;
import com.sxit.entity.ChatMessage;
import com.sxit.entity.anwser.Anwser;
import com.sxit.instance.Instance;
import com.sxit.utils.SOAP_UTILS;

public class AnswerQuestion_Adapter extends BaseAdapter {
	private Context context;
	private List<Anwser> list;
	public Map<Integer, Boolean> mapCk = new HashMap<Integer, Boolean>();

	public AnswerQuestion_Adapter(Context context, List<Anwser> list) {
		super();
		this.context = context;
		this.list = list;
		startCk();
	}

	/**
	 * 初始化checkbox
	 */
	public void startCk() {
		for (int i = 0; i < list.size(); i++) {
			if (i != 0) {
				mapCk.put(i, false);
			}
		}
	}

	/**
	 * 采纳状态
	 * 
	 * @return
	 */
	public int checkAsw() {
		for (int i = 0; i <= mapCk.size(); i++) {
			if (i != 0) {
				if (mapCk.get(i)) {
					return i;
				}
			}
		}
		return 0;
	}

	/**
	 * 
	 * @param position
	 */
	public void changCk(int position) {
		startCk();
		mapCk.put(position, true);
		notifyDataSetChanged();
	}

	public int getCount() {
		return list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		final Anwser anwser = list.get(position);
		holder = new ViewHolder();
		if (anwser.getMsg_direction() == Anwser.MESSAGE_O) {
			holder.direction = ChatMessage.MESSAGE_O;
			convertView = LayoutInflater.from(context).inflate(R.layout.answer_list_item_left, null);
		} else {
			holder.direction = ChatMessage.MESSAGE_T;
			convertView = LayoutInflater.from(context).inflate(R.layout.answer_list_item_right, null);
		}
		// left
		holder.tv_title_left = (TextView) convertView.findViewById(R.id.tv_title_left);
		holder.tv_date_left = (TextView) convertView.findViewById(R.id.tv_date_left);
		holder.img_content_url_left = (ImageView) convertView.findViewById(R.id.img_content_url_left);
		holder.tv_mark_left = (TextView) convertView.findViewById(R.id.tv_mark_left);
		holder.tv_crtime_left = (TextView) convertView.findViewById(R.id.tv_crtime_left);
		holder.img_head_left = (ImageView) convertView.findViewById(R.id.img_head_left);
		holder.tv_content_left = (TextView) convertView.findViewById(R.id.tv_content_left);
		// right
		holder.tv_title_right = (TextView) convertView.findViewById(R.id.tv_title_right);
		holder.tv_date_right = (TextView) convertView.findViewById(R.id.tv_date_right);
		holder.img_content_url_right = (ImageView) convertView.findViewById(R.id.img_content_url_right);
		holder.tv_content_right = (TextView) convertView.findViewById(R.id.tv_content_right);
		holder.checkbox_select = (CheckBox) convertView.findViewById(R.id.checkbox_select);
		holder.img_head_right = (ImageView) convertView.findViewById(R.id.img_head_right);
		holder.tv_realname_right = (TextView) convertView.findViewById(R.id.tv_realname_right);
		holder.anwser = anwser;
		convertView.setTag(holder);

		if (holder.checkbox_select != null) {
			holder.checkbox_select.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					startCk();
					mapCk.put(position, isChecked);
					notifyDataSetChanged();
				}
			});
		}
		if (holder.checkbox_select != null) {
			holder.checkbox_select.setChecked(mapCk.get(position));
		}
		if (anwser.getMsg_direction() == Anwser.MESSAGE_O) {
			holder.tv_title_left.setText(list.get(position).getTitle());
			holder.tv_date_left.setText(list.get(position).getDate());
			Instance.imageLoader.displayImage(list.get(position).getUser_img(), holder.img_head_left, Instance.user_options);
			if (list.get(position).getContent_img() != null && !list.get(position).getContent_img().equals(SOAP_UTILS.HEADER_URL + "anyType{}")) {
				Instance.imageLoader.displayImage(list.get(position).getContent_img(), holder.img_content_url_left, Instance.new_s_options);
			} else {
				holder.img_content_url_left.setVisibility(View.GONE);
			}
			holder.tv_mark_left.setText(list.get(position).getMark());
			holder.tv_crtime_left.setText(list.get(position).getCrtime());
			holder.tv_content_left.setText(list.get(position).getContent());
		} else {
			holder.tv_title_right.setText(list.get(position).getTitle());
			holder.tv_date_right.setText(list.get(position).getDate());
			Instance.imageLoader.displayImage(list.get(position).getUser_img(), holder.img_head_right, Instance.user_options);
			if (list.get(position).getContent_img() != null && !list.get(position).getContent_img().equals(SOAP_UTILS.HEADER_URL + "anyType{}")) {
				Instance.imageLoader.displayImage(list.get(position).getContent_img(), holder.img_content_url_right, Instance.new_s_options);
			} else {
				holder.img_content_url_right.setVisibility(View.GONE);
			}
			holder.tv_content_right.setText(list.get(position).getContent());
			holder.tv_realname_right.setText(list.get(position).getRealName());
		}

		return convertView;
	}

	static class ViewHolder {

		// left
		public TextView tv_title_left;// left 标题
		public TextView tv_date_left;// 时间
		public ImageView img_content_url_left;// img
		public ImageView img_head_left;// head
		public TextView tv_mark_left;// 悬赏值
		public TextView tv_crtime_left;// 发布时间
		public TextView tv_content_left;// 内容
		public TextView tv_realname_right;// 昵称
		// rigth
		public TextView tv_title_right;// left 标题
		public TextView tv_date_right;// 时间
		public ImageView img_content_url_right;// img
		public ImageView img_head_right;// head
		public TextView tv_content_right;// 内容
		public CheckBox checkbox_select;

		public int direction;
		public Anwser anwser;

	}

}
