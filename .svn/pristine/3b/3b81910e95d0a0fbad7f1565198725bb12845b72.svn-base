package com.sxit.activity.adviser.th.item.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.wap.Wap_Activity;
import com.sxit.entity.ChatMessage;
import com.sxit.entity.PublishMessage;
import com.sxit.instance.Instance;
import com.sxit.utils.SOAP_UTILS;

import lnpdit.lntv.tradingtime.R;

public class PublishMsg_Adapter  extends BaseAdapter {
	private Context context;
	private List<PublishMessage> publishMessages;

	public PublishMsg_Adapter(Context context, List<PublishMessage> publishMessages) {
		super();
		this.context = context;
		this.publishMessages = publishMessages;}

	public int getCount() {
		return publishMessages.size();
	}

	public Object getItem(int position) {
		return publishMessages.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.publishmsg_list_item, null);
			holder.tv_date_left = (TextView) convertView.findViewById(R.id.tv_date_left);
			holder.img_head_left = (ImageView) convertView.findViewById(R.id.img_head_left);
			holder.tv_title_left = (TextView) convertView.findViewById(R.id.tv_title_left);
			holder.tv_content_left = (TextView) convertView.findViewById(R.id.tv_content_left);
			holder.img_content_url_left = (ImageView) convertView.findViewById(R.id.img_content_url_left);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_date_left.setText(publishMessages.get(position).getCrtime());
		holder.tv_title_left.setText(publishMessages.get(position).getBlogTitle());
		holder.tv_content_left.setText(publishMessages.get(position).getBlogAbstract());
		Instance.imageLoader.displayImage(SOAP_UTILS.HEADER_URL + ((BaseActivity) context).getLoginUser().getHeadpic(), holder.img_head_left, Instance.user_options);
		if(publishMessages.get(position).getPhoto().equals(SOAP_UTILS.PIC_YANBAO + "no.jpg")){//no image
			holder.img_content_url_left.setVisibility(View.GONE);
		}else{
			Instance.imageLoader.displayImage(publishMessages.get(position).getPhoto(), holder.img_content_url_left, Instance.new_s_options);
			holder.img_content_url_left.setVisibility(View.VISIBLE);
		}
		return convertView;
	}

	static class ViewHolder {
		public ImageView img_head_left;
		public TextView tv_date_left;
		public TextView tv_title_left;
		public TextView tv_content_left;
		public ImageView img_content_url_left;
	}

}
