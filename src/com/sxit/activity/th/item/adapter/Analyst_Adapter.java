package com.sxit.activity.th.item.adapter;

import java.util.List;

import lnpdit.lntv.tradingtime.R;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sxit.activity.analystteaminfo.AnalystTeamInfo_Activity;
import com.sxit.entity.Adviser;
import com.sxit.instance.Instance;
import com.sxit.utils.SOAP_UTILS;

/**
 * 分析师
 * 
 * @author huanyu 类名称：Analyst_Adapter 创建时间:2014-10-27 下午6:34:46
 */
	public class Analyst_Adapter extends BaseExpandableListAdapter {
	    public List<String> father;
		public List<List<Adviser>> chilerd;
		private Context context;

		public Analyst_Adapter(List<String> faList, List<List<Adviser>> chList,
				Context context) {  //初始化数据
			this.father = faList;
			this.chilerd = chList;
			this.context = context;
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return chilerd.get(groupPosition).get(childPosition);   //获取父类下面的每一个子类项
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;  //子类位置
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) { //显示子类数据的view
			View view = null;
			view = LayoutInflater.from(context).inflate(
					R.layout.all_expand_list_item, null);
			TextView textView = (TextView) view.findViewById(R.id.all_list_text_item_id);

			TextView tv_active = (TextView) view.findViewById(R.id.tv_active); 
			TextView tv_analyst1 = (TextView) view.findViewById(R.id.tv_analyst1); 
			TextView tv_analyst2 = (TextView) view.findViewById(R.id.tv_analyst2); 
			TextView tv_analyst3 = (TextView) view.findViewById(R.id.tv_analyst3); 
			TextView tv_analyst4 = (TextView) view.findViewById(R.id.tv_analyst4); 
			TextView tv_analyst5 = (TextView) view.findViewById(R.id.tv_analyst5); 
			ImageView img_icon = (ImageView) view.findViewById(R.id.img_icon); 
			textView.setText(chilerd.get(groupPosition).get(childPosition).getRealname());
			
			tv_active.setText("活跃度：" + chilerd.get(groupPosition).get(childPosition).getPaidmark());
			
			String[] tagname_array = chilerd.get(groupPosition).get(childPosition).getTagName().split("%");
			String[] tagcss_array = chilerd.get(groupPosition).get(childPosition).getTagCss().split("%");
			if(tagname_array.length > 0){
				tv_analyst1.setText(tagname_array[0]);
				tv_analyst1.setTextColor(Color.parseColor(tagcss_array[0]));
			}
			if(tagname_array.length > 1){
				tv_analyst2.setText(tagname_array[1]);
				tv_analyst2.setTextColor(Color.parseColor(tagcss_array[1]));
			}
			if(tagname_array.length > 2){
				tv_analyst3.setText(tagname_array[2]);
				tv_analyst3.setTextColor(Color.parseColor(tagcss_array[2]));
			}
			if(tagname_array.length > 3){
				tv_analyst4.setText(tagname_array[3]);
				tv_analyst4.setTextColor(Color.parseColor(tagcss_array[3]));
			}
			tv_analyst5.setText(chilerd.get(groupPosition).get(childPosition).getSpecialty());
			Instance.imageLoader.displayImage(SOAP_UTILS.HEADER_URL+chilerd.get(groupPosition).get(childPosition).getHeadpic(), img_icon, Instance.user_options);
			return view;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return chilerd.get(groupPosition).size();  //子类item的总数
		}

		@Override
		public Object getGroup(int groupPosition) {   //父类数据
			return father.get(groupPosition);
		}

		@Override
		public int getGroupCount() {
			return father.size();  ////父类item总数
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;   //父类位置
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			View view = LayoutInflater.from(context).inflate(
					R.layout.all_expand_list, null);
			TextView textView = (TextView) view.findViewById(R.id.all_list_text_id);
			textView.setText(father.get(groupPosition));
			return view;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {  //点击子类触发事件
//			Toast.makeText(context,
//					"第" + groupPosition + "大项，第" + childPosition + "小项被点击了",
//					Toast.LENGTH_LONG).show();
			
//			Intent intent = new Intent();
//			Bundle bundle = new Bundle();
//			bundle.putSerializable("analystInfo", chilerd.get(groupPosition).get(childPosition));
//			intent.putExtras(bundle);
//			intent.setClass(context, AnalystTeamInfo_Activity.class);
//			context.startActivity(intent);
			return true;

		}

	}