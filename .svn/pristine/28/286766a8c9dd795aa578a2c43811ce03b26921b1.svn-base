package com.sxit.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import lnpdit.lntv.tradingtime.R;

/**
 * 自定义 radioGroup
 * 
 * @author huanyu 类名称：RadioButton 创建时间:2014-10-28 下午12:19:52
 */
public class RadioButton extends LinearLayout {
	private Context context;
	private ImageView imageView;
	private TextView textView;

	private int index = 0;
	private int id = 0;// 判断是否选中

	private RadioButton tempRadioButton;// 模版用于保存上次点击的对象

	public int state[] = { R.drawable.radio_unchecked, R.drawable.radio_checked };

	/***
	 * 改变图片
	 */
	public void ChageImage() {

		index++;
		id = index % 2;// 获取图片id
		imageView.setImageResource(state[id]);
	}
	/**
	 * 默认
	 */
	public void defualtImage(int position){
		imageView.setImageResource(state[position]);
	}
	/***
	 * 设置文本
	 * 
	 * @param text
	 */
	public void setText(String text) {
		textView.setText(text);
	}

	public String getText() {
		return id == 0 ? "" : textView.getText().toString();
	}

	public RadioButton(Context context, int layout,int[] state) {
		this(context, null, layout,state);

	}

	public int[] getState() {
		return state;
	}

	public void setState(int[] state) {
		this.state = state;
	}

	public RadioButton(Context context, AttributeSet attrs, int layout,int[] state) {
		super(context, attrs);
		this.context = context;
		this.state = state;
		LayoutInflater.from(context).inflate(layout, this, true);
		imageView = (ImageView) findViewById(R.id.iv_item);
		textView = (TextView) findViewById(R.id.tv_item);

	}

}
