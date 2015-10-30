package com.sxit.activity.register.adapter;

import java.util.List;
import java.util.regex.Pattern;

import com.sxit.customview.RadioButton;
import com.sxit.customview.RadioButtonCity;
import com.sxit.utils.EventCache;

import lnpdit.lntv.tradingtime.R;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint("DefaultLocale")
public class City_adapter extends BaseAdapter {
	private final String NAME = "name", SORT_KEY = "sort_key";
	private LayoutInflater inflater;
	private List<ContentValues> list;
	private RadioButtonCity temp;
	private Context context;
	public int state[] = { 0, R.drawable.right_icon };
	public static int locationPosition = -1;

	@SuppressLint("UseSparseArrays")
	public City_adapter(Context context, List<ContentValues> list) {
		this.inflater = LayoutInflater.from(context);
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

	/**
	 * 初始化personList
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// ViewHolder holder;
		// if (convertView == null) {
		// convertView = inflater.inflate(R.layout.activity_city_listview_item,
		// null);
		// holder = new ViewHolder();
		// holder.alpha = (TextView) convertView.findViewById(R.id.alpha);
		// holder.name = (TextView) convertView.findViewById(R.id.name);
		// convertView.setTag(holder);
		// } else {
		// holder = (ViewHolder) convertView.getTag();
		// }
		// ContentValues cv = list.get(position);
		// holder.name.setText(cv.getAsString(NAME));
		// String currentStr =
		// getAlpha(list.get(position).getAsString(SORT_KEY));
		// String previewStr = (position - 1) >= 0 ? getAlpha(list.get(position
		// - 1).getAsString(SORT_KEY)) : " ";
		// if (!previewStr.equals(currentStr)) {
		// holder.alpha.setVisibility(View.VISIBLE);
		// holder.alpha.setText(currentStr);
		// } else {
		// holder.alpha.setVisibility(View.GONE);
		// }

		final RadioButtonCity radioButton;
		if (convertView == null) {
			radioButton = new RadioButtonCity(context, R.layout.activity_city_listview_item_radio, state);
		} else {
			radioButton = (RadioButtonCity) convertView;
		}

		radioButton.setText(list.get(position).getAsString(NAME));
		String currentStr = getAlpha(list.get(position).getAsString(SORT_KEY));
		String previewStr = (position - 1) >= 0 ? getAlpha(list.get(position - 1).getAsString(SORT_KEY)) : " ";
		if (!previewStr.equals(currentStr)) {
			radioButton.alpha.setVisibility(View.VISIBLE);
			radioButton.alpha.setText(currentStr);
		} else {
			radioButton.alpha.setVisibility(View.GONE);
		}
		radioButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (temp != null) {
					temp.ChageImage();
				}
				temp = radioButton;
				radioButton.ChageImage();
				City_adapter.locationPosition = position;
				EventCache.opAnswerEvent.post(radioButton.getText());
			}
		});
		if(City_adapter.locationPosition != position){
			radioButton.defualtImage(0);
		}else{
			radioButton.defualtImage(1);
		}
		return radioButton;
	}

	/**
	 * 获得汉语拼音首字母
	 * 
	 * @param str
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	private String getAlpha(String str) {
		if (str == null) {
			return "#";
		}
		if (str.trim().length() == 0) {
			return "#";
		}
		char c = str.trim().substring(0, 1).charAt(0);
		// 正则表达式，判断首字母是否是英文字母
		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
		if (pattern.matcher(c + "").matches()) {
			return (c + "").toUpperCase();
		} else {
			return "#";
		}
	}

	/**
	 * listview页面控件类
	 * 
	 * @author Song
	 * 
	 */
	public class ViewHolder {
		TextView alpha;
		TextView name;
		RadioButton radioButton;
	}

}