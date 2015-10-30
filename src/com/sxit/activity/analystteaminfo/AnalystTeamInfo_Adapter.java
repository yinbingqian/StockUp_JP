package com.sxit.activity.analystteaminfo;

import java.util.ArrayList;
import java.util.List;

import com.sxit.entity.AnalystVideo;
import com.sxit.instance.Instance;
import com.sxit.utils.AsyncImageLoader;
import com.sxit.utils.SOAP_UTILS;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import lnpdit.lntv.tradingtime.R;

public class AnalystTeamInfo_Adapter extends BaseAdapter {
	private class buttonViewHolder {
		TextView analyst_content;
		ImageView analyst_img;
	}

	private List<AnalystVideo> mAppList;
	private LayoutInflater mInflater;
	private Context mContext;
	private String[] keyString;
	private buttonViewHolder holder;
	String phonecall;
	ListView listview;
	private AsyncImageLoader asyncImageLoader;


	public AnalystTeamInfo_Adapter(Context c, List<AnalystVideo> appList) {
		mAppList = appList;
		mContext = c;

        mInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mAppList.size();
	}

	@Override
	public Object getItem(int position) {
		return mAppList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void removeItem(int positon) {
		mAppList.remove(positon);
		this.notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView != null) {
			holder = (buttonViewHolder) convertView.getTag();
		} else {
			convertView = mInflater.inflate(R.layout.list_in_analystvideo, null);
			holder = new buttonViewHolder();

			holder.analyst_content = (TextView) convertView.findViewById(R.id.analyst_content);
			holder.analyst_img = (ImageView) convertView.findViewById(R.id.analyst_img);
			convertView.setTag(holder);
		}
		AnalystVideo appInfo = mAppList.get(position);
		if (appInfo != null) {
			String Id = (String) appInfo.getId();
			String Title = (String) appInfo.getTitle();
			String Picture = (String) appInfo.getPicture();
			String Content = (String) appInfo.getContent();
			String Analystid = (String) appInfo.getAnalystid();
			String Crtime = (String) appInfo.getCrtime();
			
			String imageUrl = SOAP_UTILS.PIC_FILE + Picture;
			holder.analyst_content.setText(Title);
			Instance.imageLoader.displayImage(imageUrl, holder.analyst_img, Instance.user_options);
	        
		}
		return convertView;
	}

	public void addItem(ArrayList<AnalystVideo> item) {
		int count = item.size();
		for (int i = 0; i < count; i++) {
			mAppList.add(item.get(i));
		}
	}

}