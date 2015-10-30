package com.sxit.customview;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
/**
 * 索引字母
 * @author huanyu	
 * 类名称：CityLetterListView   
 * 创建时间:2014-11-9 下午4:43:46
 */
public class CityLetterListView extends View {
	// 字母检索listview变更事件
	OnTouchingLetterChangedListener onTouchingLetterChangedListener;
	// 检索字母数组
	String[] b = { "#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
	// 选中状态
	int choose = -1;
	// 画笔
	Paint paint = new Paint();
	// 是否显示检索字母背景颜色
	boolean showBkg = false;

	public CityLetterListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CityLetterListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CityLetterListView(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (showBkg) {
			canvas.drawColor(Color.parseColor("#40000000"));
		}
		/** 绘制检索字母 **/
		int height = getHeight();
		int width = getWidth();
		int singleHeight = height / b.length;
		for (int i = 0; i < b.length; i++) {
			// 设置字体大小
			paint.setTextSize(15);
			// 设置字体颜色
			paint.setColor(Color.GRAY);
			// 设置字体样式
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			paint.setAntiAlias(true);
			if (i == choose) {
				// 设置选中后字体颜色
				paint.setColor(Color.parseColor("#3399ff"));
				paint.setFakeBoldText(true);
			}
			float xPos = width / 2 - paint.measureText(b[i]) / 2;
			float yPos = singleHeight * i + singleHeight;
			canvas.drawText(b[i], xPos, yPos, paint);
			paint.reset();
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		final float y = event.getY();
		final int oldChoose = choose;
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		final int c = (int) (y / getHeight() * b.length);

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			showBkg = true;
			if (oldChoose != c && listener != null) {
				if (c > 0 && c < b.length) {
					listener.onTouchingLetterChanged(b[c]);
					choose = c;
					invalidate();
				}
			}

			break;
		case MotionEvent.ACTION_MOVE:
			if (oldChoose != c && listener != null) {
				if (c > 0 && c < b.length) {
					listener.onTouchingLetterChanged(b[c]);
					choose = c;
					invalidate();
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			showBkg = false;
			choose = -1;
			invalidate();
			break;
		}
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

	public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}

	public interface OnTouchingLetterChangedListener {
		public void onTouchingLetterChanged(String s);
	}

}
